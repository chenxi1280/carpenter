package com.wxcz.carpenter.service.impl;

import com.alibaba.fastjson.JSON;
import com.wxcz.carpenter.common.SecretKeyConstants;
import com.wxcz.carpenter.dao.*;
import com.wxcz.carpenter.pojo.dto.PageDTO;
import com.wxcz.carpenter.pojo.dto.ResponseDTO;
import com.wxcz.carpenter.pojo.entity.*;
import com.wxcz.carpenter.pojo.query.EcmUserQuery;
import com.wxcz.carpenter.pojo.vo.*;
import com.wxcz.carpenter.service.BaseService;
import com.wxcz.carpenter.service.EcmDownLinkFlowService;
import com.wxcz.carpenter.service.EcmMessageService;
import com.wxcz.carpenter.service.EcmUserService;
import com.wxcz.carpenter.util.EncryptUtil;
import com.wxcz.carpenter.util.HttpUtils;
import com.wxcz.carpenter.util.MD5Utils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author by cxd
 * @Classname EcmUserServiceImpl
 * @Description TODO
 * @Date 2020/8/6 10:42
 */
@Service
public class EcmUserServiceImpl implements EcmUserService , BaseService {


    @Resource
    EcmMessageService ecmMessageService;

    @Resource
    EcmDownLinkFlowService ecmDownLinkFlowService;

    @Resource
    EcmUserDao ecmUserDao;

    @Resource
    EcmUserRolesDao ecmUserRolesDao;

    @Resource
    EcmUserAcessDao ecmUserAcessDao;

    @Resource
    EcmArtworkDao ecmArtworkDao;

    @Resource
    EcmInnerMessageDao ecmInnerMessageDao;

    @Resource
    EcmUserFlowDao ecmUserFlowDao;

    @Resource
    EcmDownlinkFlowDao ecmDownlinkFlowDao;

    @Resource
    EcmUserNoticeRecordDao ecmUserNoticeRecordDao;

    @Resource
    EcmDownlinkFlowUpdateHistoryDao ecmDownlinkFlowUpdateHistoryDao;






    @Override
    public EcmUserVO login(EcmUserQuery query) {

        return  ecmUserDao.login(query);
    }

    @Override
    public List<EcmUserRolesVO> selectUserRolesByPhone(String mobile) {
        return this.selectUserRolesByUser(ecmUserDao.selectByPhone(mobile));
    }

    @Override
    public List<EcmUserRolesVO> selectUserRolesByUser(EcmUserVO ecmUserVO) {
        //查询角色集合
        List<EcmUserRolesVO>  rolesVOList = ecmUserRolesDao.selectByRoles(ecmUserVO.getRoles());


        if (!CollectionUtils.isEmpty(rolesVOList)) {
            //角色排序
            rolesVOList.sort( Comparator.comparing(EcmUserRolesVO::getGrade).reversed() );
            //查询权限集合
            List<EcmUserAcessVO> acessVOList = this.selectUSerAcessByRoles(rolesVOList);
            //把权限集合设置到角色集合中
            setRolesAcess(rolesVOList,acessVOList);
        }
        return rolesVOList;
    }

    @Override
    public List<EcmUserAcessVO> selectUSerAcessByRoles(List<EcmUserRolesVO> rolesVOList) {

        List<EcmUserAcessVO> list = new ArrayList<>();

        Set<String> set = new HashSet<String>();
        //拆分权限串，并将权限id 存入 set
        rolesVOList.forEach( v -> {
            if (!StringUtils.isEmpty(v.getAcess())) {
                Collections.addAll(set, v.getAcess().split(","));
            }
        });
        if (!CollectionUtils.isEmpty(set)) {
            //通过权限id set<String> 查询
            list = ecmUserAcessDao.selectUSerAcessByRoles(set);

        }

        return list;
    }

    @Override
    public PageDTO ajaxList(EcmUserQuery ecmUserQuery) {

        if (StringUtils.isEmpty(ecmUserQuery.getPhone())){
            ecmUserQuery.setPhone("1");
        }

        List<EcmUserVO> list = ecmUserDao.selectListByQuery(ecmUserQuery);

        List<EcmUserFlowVO> ecmUserFlowVOS = ecmUserFlowDao.selectByUserList(list);


//        list.stream().filter( ecmUserVO ->  ecmUserVO.getRoles())

        list.forEach( ecmUserVO ->  {
            if (ecmUserVO.getSubTotalFlow() != null && ecmUserVO.getSubUsedFlow() != null) {
                ecmUserVO.setSurplusFlow(ecmUserVO.getSubTotalFlow() - ecmUserVO.getSubUsedFlow());
            }

            try {
                ecmUserVO.setMobile(EncryptUtil.aesDecrypt(  ecmUserVO.getMobile(), SecretKeyConstants.SECRET_KEY));
            } catch (Exception e) {
//                e.printStackTrace();
                System.out.println("手机号码转码错误！" + ecmUserVO.getMobile());
            }
            ecmUserFlowVOS.forEach( flow -> {
               if (flow.getUserId().equals(ecmUserVO.getPkUserId())){
                   ecmUserVO.setUpFlow(String.valueOf((flow.getTotalFlow() - flow.getTotalFlow()) / 1204) +"MB" );
               }
            });
            if (ecmUserVO.getUpFlow() == null){
                ecmUserVO.setUpFlow("500MB");
            }
            String[] split = ecmUserVO.getRoles().split(",");
            for (String s : split) {
                if ("2".equals(s)){
                    ecmUserVO.setRoleName("超级管理员");
                    break;
                }
                if ("3".equals(s)){
                    ecmUserVO.setRoleName("普通管理员");
                    break;
                }
                if ("1".equals(s)){
                    ecmUserVO.setRoleName("普通用户");
                }
            }
        });


        Integer count = ecmUserDao.selectCountByQuery(ecmUserQuery);
        return PageDTO.setPageData(count,list);
    }

    @Override
    public ResponseDTO upDataUser(EcmUserVO ecmUserVO) {
//        ecmUserVO.setCount(ecmUserVO.getCount()+1);
        ecmUserVO.setLastLoginTime(new Date());
        return ResponseDTO.get( 1 == ecmUserDao.updateByPrimaryKeySelective(ecmUserVO));
    }

    @Override
//    @Transactional(rollbackFor)
    public ResponseDTO chengUser(EcmUserVO ecmUserVO) {

        //需要对当前用户的 最高权限判断获取
        Subject subject = SecurityUtils.getSubject();
        List<EcmUserRolesVO>  hisroles = (List<EcmUserRolesVO>) subject.getSession().getAttribute("hisRoles");
        EcmUserRolesVO ecmUserRolesVO1 = hisroles.get(0);

        if (ecmUserRolesVO1.getGrade() < 90  && !StringUtils.isEmpty(ecmUserVO.getRoles())){
            return ResponseDTO.fail("无权限");
        }


        Integer userId = (Integer)getRequstSession().getAttribute("userId");

        if (ecmUserVO.getPkUserId().equals(userId)){

            if (!StringUtils.isEmpty(ecmUserVO.getRoles())) {

                List<EcmUserRolesVO> userRoleList = ecmUserRolesDao.selectByRoles(ecmUserVO.getRoles());
                userRoleList.sort(Comparator.comparing(EcmUserRoles::getGrade).reversed());
                EcmUserRolesVO ecmUserRolesVO = userRoleList.get(0);

                if (ecmUserRolesVO1.getGrade() >= ecmUserRolesVO.getGrade()) {
                    ecmUserVO.setLastLoginTime(new Date());
                    //用户封禁下架所有作品
                    if ("N".equals(ecmUserVO.getIsValid())) {
                        ecmArtworkDao.selectByUserId(ecmUserVO.getPkUserId()).equals(ecmArtworkDao.downArtWorkByUserId(ecmUserVO.getPkUserId()));
                    }

                    return ResponseDTO.get(1 == ecmUserDao.updateByPrimaryKeySelective(ecmUserVO));
                }

                return ResponseDTO.fail("无权限");

            }

        }


        EcmUser ecmUser = ecmUserDao.selectByPrimaryKey(ecmUserVO.getPkUserId());

        //角色排序
        List<EcmUserRolesVO> userRoleList = ecmUserRolesDao.selectByRoles(ecmUser.getRoles());
        userRoleList.sort( Comparator.comparing(EcmUserRoles::getGrade).reversed() );
        EcmUserRolesVO ecmUserRolesVO = userRoleList.get(0);
        //角色等级比较
        if (ecmUserRolesVO1.getGrade() >= ecmUserRolesVO.getGrade()){
            ecmUserVO.setLastLoginTime(new Date());
            //用户封禁下架所有作品
            if ( "N".equals(ecmUserVO.getIsValid()) ){
                ecmArtworkDao.selectByUserId(ecmUserVO.getPkUserId()).equals(ecmArtworkDao.downArtWorkByUserId(ecmUserVO.getPkUserId()));
            }
            return ResponseDTO.get( 1 == ecmUserDao.updateByPrimaryKeySelective(ecmUserVO));
        }
        return ResponseDTO.fail("无权修改");
    }

    @Override
    public ResponseDTO setPassWord(EcmUserVO ecmUserVO) {

        EcmUser ecmUser = ecmUserDao.selectByPrimaryKey(ecmUserVO.getPkUserId());

        if (!StringUtils.isEmpty(ecmUserVO.getPassword())  && !StringUtils.isEmpty(ecmUserVO.getOldPassWord()) ) {
            if (MD5Utils.encrypt(ecmUserVO.getOldPassWord()).equals(ecmUser.getPassword())) {
                ecmUserVO.setPassword(MD5Utils.encrypt(ecmUserVO.getPassword()));
                ecmUserVO.setLastLoginTime(new Date());
                return ResponseDTO.get(1 == ecmUserDao.updateByPrimaryKeySelective(ecmUserVO));
            }
            return ResponseDTO.fail("原密码错误");
        }
        return ResponseDTO.fail("请输入密码");

    }

    @Override
    public ResponseDTO updataUserLogoStatus(EcmUserVO ecmUserVO) {

        if (ecmUserVO.getUserLogoStatus() == null){
            return ResponseDTO.fail("网络错误");
        }
        String msg = "审核已通过";
        if (ecmUserVO.getUserLogoStatus() ==2 ){

            ecmUserVO.setUserLogoUrl("https://sike-1259692143.cos.ap-chongqing.myqcloud.com/img/1599099032542img.png");
            msg = "审核不通过";
            // 站内信发送
            EcmTemplateVo ecmTemplateVo = new EcmTemplateVo();
            //  15 为头像违规 模板
            ecmTemplateVo.setPkTemplateId(15);
            ecmTemplateVo.setIds(new Integer[] {ecmUserVO.getPkUserId()});
            ecmMessageService.addMsgPart(ecmTemplateVo);

        }
        ecmUserVO.setUpdateTime(new Date());
        ecmUserDao.updataUserLogoStatus(ecmUserVO);

        return ResponseDTO.ok(msg);
    }

    @Override
    public PageDTO ajaxUserDownFlowList(EcmUserQuery ecmUserQuery) {
        if (StringUtils.isEmpty(ecmUserQuery.getPhone())){
            ecmUserQuery.setPhone("1");
        }

        List<EcmUserVO> list = ecmUserDao.selectUserDownFlowListByQuery(ecmUserQuery);



//        list.stream().filter( ecmUserVO ->  ecmUserVO.getRoles())

        list.forEach( ecmUserVO ->  {
            if (ecmUserVO.getSubTotalFlow() != null && ecmUserVO.getSubUsedFlow() != null) {
                ecmUserVO.setSurplusFlow(ecmUserVO.getSubTotalFlow() - ecmUserVO.getSubUsedFlow());
            }

            try {
                ecmUserVO.setMobile(EncryptUtil.aesDecrypt(  ecmUserVO.getMobile(), SecretKeyConstants.SECRET_KEY));
            } catch (Exception e) {
//                e.printStackTrace();
                System.out.println("手机号码转码错误！"+ ecmUserVO.getMobile());
            }

        });


        Integer count = ecmUserDao.selectUserDownFlowCountByQuery(ecmUserQuery);
        return PageDTO.setPageData(count,list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO addDownFlowUser(EcmDownlinkFlowVO ecmDownlinkFlowVO) {
        ecmDownlinkFlowVO.setCreateTime(new Date());
        ecmDownlinkFlowVO.setSubUsedFlow(0L);
        ecmDownlinkFlowVO.setSubFlowStatus(0);
        ecmDownlinkFlowVO.setSubAppId(Integer.valueOf(String.valueOf(ecmDownLinkFlowService.createSubAppId( String.valueOf(ecmDownlinkFlowVO.getFkUserId())  ))));
        ecmDownlinkFlowVO.setUpdateTime(new Date());

        // 查询带上短信状态码 开通类的短信为1
        EcmUserNoticeRecord ecmUserNoticeRecord = new EcmUserNoticeRecord();
        ecmUserNoticeRecord.setNoticeStatus(1);
        ecmUserNoticeRecord.setFkUserId(ecmDownlinkFlowVO.getFkUserId());
        EcmUserNoticeRecord ecmUserNotice = ecmUserNoticeRecordDao.selectByRecord(ecmUserNoticeRecord);
        try {
            if (ecmUserNotice == null) {
                ecmUserNoticeRecord.setCreateTime(new Date());
                ecmUserNoticeRecord.setFkUserId(ecmDownlinkFlowVO.getFkUserId());
                ecmUserNoticeRecordDao.insertSelective(ecmUserNoticeRecord);
            }else {
                ecmUserNotice.setNoticeStatus(1);
                ecmUserNoticeRecordDao.updateByPrimaryKeySelective(ecmUserNotice);
            }

            ecmDownlinkFlowDao.insertSelective(ecmDownlinkFlowVO);
            EcmUser ecmUser = ecmUserDao.selectByPrimaryKey(ecmDownlinkFlowVO.getFkUserId());
            ecmUser.setMobile(EncryptUtil.aesDecrypt(  ecmUser.getMobile(), SecretKeyConstants.SECRET_KEY));

            //发送短信
            SendNoticeVO sendNoticeVO = new SendNoticeVO();
            sendNoticeVO.setTemplateId("960224");
            sendNoticeVO.setPhoneNumber(ecmUser.getMobile());
            HttpUtils.post(HttpUtils.SEND_NOTICE_URL, JSON.toJSONString(sendNoticeVO));
        }catch (IOException e){
            e.printStackTrace();
            return ResponseDTO.fail("短信发送错误");
        }catch (Exception e){
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponseDTO.fail("网络错误");
        }

        return ResponseDTO.ok();

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO addUserDownFlow(EcmDownlinkFlowVO ecmDownlinkFlowVO) {

        EcmDownlinkFlowVO byUserId = ecmDownlinkFlowDao.selectByUserId(ecmDownlinkFlowVO.getFkUserId());

        if (byUserId == null) {
            return ResponseDTO.fail("错误用户");
        }
        byUserId.setSubTotalFlow(byUserId.getSubTotalFlow()+ ecmDownlinkFlowVO.getSubTotalFlow());
        byUserId.setUpdateTime( new Date());

        // 添加流量后会重新发送只剩余1gb下行流量的短信通知 状态码是3
        EcmUserNoticeRecord ecmUserNoticeRecord = new EcmUserNoticeRecord();
        ecmUserNoticeRecord.setNoticeStatus(3);
        ecmUserNoticeRecord.setFkUserId(ecmDownlinkFlowVO.getFkUserId());
        EcmUserNoticeRecord ecmUserNotice = ecmUserNoticeRecordDao.selectByRecord(ecmUserNoticeRecord);
        try{
            if (ecmUserNotice != null) {
                // 删除1gb的短信通知记录 这样用户在流量剩余1gb时 才会受到短信
                ecmUserNoticeRecordDao.deleteByPrimaryKey(ecmUserNotice.getPkId());
            }

            EcmDownlinkFlowUpdateHistory ecmDownlinkFlowUpdateHistory = new EcmDownlinkFlowUpdateHistory();
            ecmDownlinkFlowUpdateHistory.setCreateTime(new Date());
            ecmDownlinkFlowUpdateHistory.setFkUserId(ecmDownlinkFlowVO.getFkUserId());
            ecmDownlinkFlowUpdateHistory.setSubFlow(ecmDownlinkFlowVO.getSubTotalFlow());
            ecmDownlinkFlowUpdateHistory.setSubAppId(byUserId.getSubAppId());
            ecmDownlinkFlowUpdateHistoryDao.insertSelective(ecmDownlinkFlowUpdateHistory);
            ecmDownLinkFlowService.modifySubAppStatus("On",Long.valueOf(byUserId.getSubAppId()));
            ecmDownlinkFlowDao.updateByPrimaryKeySelective(byUserId);

            // 24周任务改动 用户成功添加流量后 发送下行流量开通成功的短信提醒
            EcmUser ecmUser = ecmUserDao.selectByPrimaryKey(ecmDownlinkFlowVO.getFkUserId());
            ecmUser.setMobile(EncryptUtil.aesDecrypt(  ecmUser.getMobile(), SecretKeyConstants.SECRET_KEY));
            SendNoticeVO sendNoticeVO = new SendNoticeVO();
            sendNoticeVO.setTemplateId("960224");
            sendNoticeVO.setPhoneNumber(ecmUser.getMobile());
            HttpUtils.post(HttpUtils.SEND_NOTICE_URL, JSON.toJSONString(sendNoticeVO));
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseDTO.fail();
        }
        return ResponseDTO.ok();
    }


    /**
     * @param: [rolesVOList, acessVOList] 角色集合 和 权限集合
     * @return: void 无返回，完成后 rolesVOList 中 的 role 中的AcessVOList 会有对应的 权限
     * @author: cxd
     * @Date: 2020/8/6
     * 描述 :     将多个权限设置到多个对应角色
     */
    private void setRolesAcess(List<EcmUserRolesVO> rolesVOList, List<EcmUserAcessVO> acessVOList) {
        // 通过权限id 分组 key为 权限id value 围殴 权限list
        Map<Integer, List<EcmUserAcessVO>> collect = acessVOList.stream().collect(Collectors.groupingBy(EcmUserAcessVO::getPkAcessId));
        //循环 角色
        rolesVOList.forEach( role -> {
            //取出对应的 权限字符串 并 才分成 string[]
            String[] split = role.getAcess().split(",");
            ArrayList<EcmUserAcessVO> ecmUserAcessVOS = new ArrayList<>();
            //循环设置 角色 中的 权限集合
            for (String s : split) {
                if (!CollectionUtils.isEmpty(acessVOList)) {
                    List<EcmUserAcessVO> ecmUserAcessVOList = collect.get(Integer.valueOf(s));
                    EcmUserAcessVO ecmUserAcessVO = ecmUserAcessVOList.get(0);
                    ecmUserAcessVOS.add(ecmUserAcessVO);
                }
            }
            role.setAcessVOS(ecmUserAcessVOS);
        });


    }
}
