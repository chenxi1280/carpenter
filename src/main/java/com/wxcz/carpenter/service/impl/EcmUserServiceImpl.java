package com.wxcz.carpenter.service.impl;

import com.hazelcast.util.StringUtil;
import com.wxcz.carpenter.common.SecretKeyConstants;
import com.wxcz.carpenter.dao.*;
import com.wxcz.carpenter.pojo.dto.PageDTO;
import com.wxcz.carpenter.pojo.dto.ResponseDTO;
import com.wxcz.carpenter.pojo.entity.EcmArtwork;
import com.wxcz.carpenter.pojo.entity.EcmUser;
import com.wxcz.carpenter.pojo.entity.EcmUserRoles;
import com.wxcz.carpenter.pojo.query.EcmUserQuery;
import com.wxcz.carpenter.pojo.vo.EcmTemplateVo;
import com.wxcz.carpenter.pojo.vo.EcmUserAcessVO;
import com.wxcz.carpenter.pojo.vo.EcmUserRolesVO;
import com.wxcz.carpenter.pojo.vo.EcmUserVO;
import com.wxcz.carpenter.service.BaseService;
import com.wxcz.carpenter.service.EcmMessageService;
import com.wxcz.carpenter.service.EcmUserService;
import com.wxcz.carpenter.util.EncryptUtil;
import com.wxcz.carpenter.util.MD5Utils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
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
    EcmMessageService ecmMessageService;


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
            ecmUserQuery.setPhone("");
        }

        List<EcmUserVO> list = ecmUserDao.selectListByQuery(ecmUserQuery);

//        list.stream().filter( ecmUserVO ->  ecmUserVO.getRoles())

        list.forEach( ecmUserVO ->  {
            try {
                ecmUserVO.setMobile(EncryptUtil.aesDecrypt(  ecmUserVO.getMobile(), SecretKeyConstants.secretKey));
            } catch (Exception e) {
                e.printStackTrace();
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


        Integer count = list.size();
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
        ecmUserDao.updateByPrimaryKeySelective(ecmUserVO);

        return ResponseDTO.ok(msg);
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
