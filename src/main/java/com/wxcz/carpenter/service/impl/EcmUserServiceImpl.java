package com.wxcz.carpenter.service.impl;

import com.hazelcast.util.StringUtil;
import com.wxcz.carpenter.dao.EcmUserAcessDao;
import com.wxcz.carpenter.dao.EcmUserDao;
import com.wxcz.carpenter.dao.EcmUserRolesDao;
import com.wxcz.carpenter.pojo.dto.PageDTO;
import com.wxcz.carpenter.pojo.dto.ResponseDTO;
import com.wxcz.carpenter.pojo.entity.EcmUser;
import com.wxcz.carpenter.pojo.entity.EcmUserRoles;
import com.wxcz.carpenter.pojo.query.EcmUserQuery;
import com.wxcz.carpenter.pojo.vo.EcmUserAcessVO;
import com.wxcz.carpenter.pojo.vo.EcmUserRolesVO;
import com.wxcz.carpenter.pojo.vo.EcmUserVO;
import com.wxcz.carpenter.service.EcmUserService;
import com.wxcz.carpenter.util.MD5Utils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;
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
public class EcmUserServiceImpl implements EcmUserService {

    @Resource
    EcmUserDao ecmUserDao;

    @Resource
    EcmUserRolesDao ecmUserRolesDao;

    @Resource
    EcmUserAcessDao ecmUserAcessDao;


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

        if (StringUtils.isEmpty(ecmUserQuery.getPhone())) {
            ecmUserQuery.setPhone("1");
        }

        List<EcmUserVO> list = ecmUserDao.selectListByQuery(ecmUserQuery);

        Integer count = ecmUserDao.selectCountByQuery(ecmUserQuery);
        list.forEach( ecmUserVO ->  {
            String[] split = ecmUserVO.getRoles().split(",");
            for (String s : split) {
                if (s.equals("2")){
                    ecmUserVO.setRoleName("超级管理员");
                    break;
                }
                if (s.equals("3")){
                    ecmUserVO.setRoleName("普通管理员");
                    break;
                }
                if (s.equals("1")){
                    ecmUserVO.setRoleName("普通用户");
                }
            }
        });

        return PageDTO.setPageData(count,list);
    }

    @Override
    public ResponseDTO upDataUser(EcmUserVO ecmUserVO) {
        ecmUserVO.setCount(ecmUserVO.getCount()+1);
        ecmUserVO.setLastLoginTime(new Date());
        return ResponseDTO.get( 1 == ecmUserDao.updateByPrimaryKeySelective(ecmUserVO));
    }

    @Override
    public ResponseDTO chengUser(EcmUserVO ecmUserVO) {
        EcmUser ecmUser = ecmUserDao.selectByPrimaryKey(ecmUserVO.getPkUserId());

        List<EcmUserRolesVO> userRoleList = ecmUserRolesDao.selectByRoles(ecmUser.getRoles());
        userRoleList.sort( Comparator.comparing(EcmUserRoles::getGrade).reversed() );

        //需要对当前用户的 最高权限判断获取
        Subject subject = SecurityUtils.getSubject();
        PrincipalCollection principals = subject.getPrincipals();

        if (!StringUtils.isEmpty(ecmUserVO.getRoles())) {
            ecmUserVO.setRoles(null);
            if (subject.hasRole("superadmin")) {
                return ResponseDTO.get( 1 == ecmUserDao.updateByPrimaryKeySelective(ecmUserVO));
            }
            return ResponseDTO.fail("无权修改");
        }



        ecmUserVO.setLastLoginTime(new Date());
        return ResponseDTO.get( 1 == ecmUserDao.updateByPrimaryKeySelective(ecmUserVO));
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
