package com.wxcz.carpenter.service.impl;

import com.hazelcast.util.StringUtil;
import com.wxcz.carpenter.common.SecretKeyConstants;
import com.wxcz.carpenter.dao.*;
import com.wxcz.carpenter.pojo.dto.PageDTO;
import com.wxcz.carpenter.pojo.dto.ResponseDTO;
import com.wxcz.carpenter.pojo.entity.EcmArtwork;
import com.wxcz.carpenter.pojo.entity.EcmUser;
import com.wxcz.carpenter.pojo.entity.EcmUserFlow;
import com.wxcz.carpenter.pojo.entity.EcmUserRoles;
import com.wxcz.carpenter.pojo.query.EcmUserQuery;
import com.wxcz.carpenter.pojo.vo.*;
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

    @Resource
    EcmUserFlowDao ecmUserFlowDao;

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
        //??????????????????
        List<EcmUserRolesVO>  rolesVOList = ecmUserRolesDao.selectByRoles(ecmUserVO.getRoles());


        if (!CollectionUtils.isEmpty(rolesVOList)) {
            //????????????
            rolesVOList.sort( Comparator.comparing(EcmUserRolesVO::getGrade).reversed() );
            //??????????????????
            List<EcmUserAcessVO> acessVOList = this.selectUSerAcessByRoles(rolesVOList);
            //???????????????????????????????????????
            setRolesAcess(rolesVOList,acessVOList);
        }
        return rolesVOList;
    }

    @Override
    public List<EcmUserAcessVO> selectUSerAcessByRoles(List<EcmUserRolesVO> rolesVOList) {

        List<EcmUserAcessVO> list = new ArrayList<>();

        Set<String> set = new HashSet<String>();
        //??????????????????????????????id ?????? set
        rolesVOList.forEach( v -> {
            if (!StringUtils.isEmpty(v.getAcess())) {
                Collections.addAll(set, v.getAcess().split(","));
            }
        });
        if (!CollectionUtils.isEmpty(set)) {
            //????????????id set<String> ??????
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

        List<EcmUserFlowVO> ecmUserFlowVOS = ecmUserFlowDao.selectByUserList(list);


//        list.stream().filter( ecmUserVO ->  ecmUserVO.getRoles())

        list.forEach( ecmUserVO ->  {
            try {
                ecmUserVO.setMobile(EncryptUtil.aesDecrypt(  ecmUserVO.getMobile(), SecretKeyConstants.SECRET_KEY));
            } catch (Exception e) {
//                e.printStackTrace();
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
                    ecmUserVO.setRoleName("???????????????");
                    break;
                }
                if ("3".equals(s)){
                    ecmUserVO.setRoleName("???????????????");
                    break;
                }
                if ("1".equals(s)){
                    ecmUserVO.setRoleName("????????????");
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

        //???????????????????????? ????????????????????????
        Subject subject = SecurityUtils.getSubject();
        List<EcmUserRolesVO>  hisroles = (List<EcmUserRolesVO>) subject.getSession().getAttribute("hisRoles");
        EcmUserRolesVO ecmUserRolesVO1 = hisroles.get(0);

        if (ecmUserRolesVO1.getGrade() < 90  && !StringUtils.isEmpty(ecmUserVO.getRoles())){
            return ResponseDTO.fail("?????????");
        }


        Integer userId = (Integer)getRequstSession().getAttribute("userId");

        if (ecmUserVO.getPkUserId().equals(userId)){

            if (!StringUtils.isEmpty(ecmUserVO.getRoles())) {

                List<EcmUserRolesVO> userRoleList = ecmUserRolesDao.selectByRoles(ecmUserVO.getRoles());
                userRoleList.sort(Comparator.comparing(EcmUserRoles::getGrade).reversed());
                EcmUserRolesVO ecmUserRolesVO = userRoleList.get(0);

                if (ecmUserRolesVO1.getGrade() >= ecmUserRolesVO.getGrade()) {
                    ecmUserVO.setLastLoginTime(new Date());
                    //??????????????????????????????
                    if ("N".equals(ecmUserVO.getIsValid())) {
                        ecmArtworkDao.selectByUserId(ecmUserVO.getPkUserId()).equals(ecmArtworkDao.downArtWorkByUserId(ecmUserVO.getPkUserId()));
                    }

                    return ResponseDTO.get(1 == ecmUserDao.updateByPrimaryKeySelective(ecmUserVO));
                }

                return ResponseDTO.fail("?????????");

            }

        }


        EcmUser ecmUser = ecmUserDao.selectByPrimaryKey(ecmUserVO.getPkUserId());

        //????????????
        List<EcmUserRolesVO> userRoleList = ecmUserRolesDao.selectByRoles(ecmUser.getRoles());
        userRoleList.sort( Comparator.comparing(EcmUserRoles::getGrade).reversed() );
        EcmUserRolesVO ecmUserRolesVO = userRoleList.get(0);
        //??????????????????
        if (ecmUserRolesVO1.getGrade() >= ecmUserRolesVO.getGrade()){
            ecmUserVO.setLastLoginTime(new Date());
            //??????????????????????????????
            if ( "N".equals(ecmUserVO.getIsValid()) ){
                ecmArtworkDao.selectByUserId(ecmUserVO.getPkUserId()).equals(ecmArtworkDao.downArtWorkByUserId(ecmUserVO.getPkUserId()));
            }
            return ResponseDTO.get( 1 == ecmUserDao.updateByPrimaryKeySelective(ecmUserVO));
        }
        return ResponseDTO.fail("????????????");
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
            return ResponseDTO.fail("???????????????");
        }
        return ResponseDTO.fail("???????????????");

    }

    @Override
    public ResponseDTO updataUserLogoStatus(EcmUserVO ecmUserVO) {

        if (ecmUserVO.getUserLogoStatus() == null){
            return ResponseDTO.fail("????????????");
        }
        String msg = "???????????????";
        if (ecmUserVO.getUserLogoStatus() ==2 ){

            ecmUserVO.setUserLogoUrl("https://sike-1259692143.cos.ap-chongqing.myqcloud.com/img/1599099032542img.png");
            msg = "???????????????";
            // ???????????????
            EcmTemplateVo ecmTemplateVo = new EcmTemplateVo();
            //  15 ??????????????? ??????
            ecmTemplateVo.setPkTemplateId(15);
            ecmTemplateVo.setIds(new Integer[] {ecmUserVO.getPkUserId()});
            ecmMessageService.addMsgPart(ecmTemplateVo);

        }
        ecmUserVO.setUpdateTime(new Date());
        ecmUserDao.updataUserLogoStatus(ecmUserVO);

        return ResponseDTO.ok(msg);
    }


    /**
     * @param: [rolesVOList, acessVOList] ???????????? ??? ????????????
     * @return: void ????????????????????? rolesVOList ??? ??? role ??????AcessVOList ??????????????? ??????
     * @author: cxd
     * @Date: 2020/8/6
     * ?????? :     ??????????????????????????????????????????
     */
    private void setRolesAcess(List<EcmUserRolesVO> rolesVOList, List<EcmUserAcessVO> acessVOList) {
        // ????????????id ?????? key??? ??????id value ?????? ??????list
        Map<Integer, List<EcmUserAcessVO>> collect = acessVOList.stream().collect(Collectors.groupingBy(EcmUserAcessVO::getPkAcessId));
        //?????? ??????
        rolesVOList.forEach( role -> {
            //??????????????? ??????????????? ??? ????????? string[]
            String[] split = role.getAcess().split(",");
            ArrayList<EcmUserAcessVO> ecmUserAcessVOS = new ArrayList<>();
            //???????????? ?????? ?????? ????????????
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
