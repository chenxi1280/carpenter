package com.wxcz.carpenter.service.impl;

import com.wxcz.carpenter.dao.EcmUserAcessDao;
import com.wxcz.carpenter.dao.EcmUserDao;
import com.wxcz.carpenter.dao.EcmUserRolesDao;
import com.wxcz.carpenter.pojo.dto.PageDTO;
import com.wxcz.carpenter.pojo.dto.ResponseDTO;
import com.wxcz.carpenter.pojo.query.EcmUserQuery;
import com.wxcz.carpenter.pojo.vo.EcmUserAcessVO;
import com.wxcz.carpenter.pojo.vo.EcmUserRolesVO;
import com.wxcz.carpenter.pojo.vo.EcmUserVO;
import com.wxcz.carpenter.service.EcmUserService;
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


        List<EcmUserRolesVO>  rolesVOList = ecmUserRolesDao.selectByRoles(ecmUserVO.getRoles());
        if (!CollectionUtils.isEmpty(rolesVOList)) {
            List<EcmUserAcessVO> acessVOList = this.selectUSerAcessByRoles(rolesVOList);
            setRolesAcess(rolesVOList,acessVOList);
        }
        return rolesVOList;
    }

    @Override
    public List<EcmUserAcessVO> selectUSerAcessByRoles(List<EcmUserRolesVO> rolesVOList) {

        List<EcmUserAcessVO> list = new ArrayList<>();

        Set<String> set = new HashSet<String>();

        rolesVOList.forEach( v -> {

            if (!StringUtils.isEmpty(v.getAcess())) {
                Collections.addAll(set, v.getAcess().split(","));
            }

        });
        if (!CollectionUtils.isEmpty(set)) {
            list = ecmUserAcessDao.selectUSerAcessByRoles(set);

        }

        return list;
    }

    @Override
    public PageDTO ajaxList(EcmUserQuery ecmUserQuery) {

        List<EcmUserVO> list = ecmUserDao.selectListByQuery(ecmUserQuery);
        Integer count = ecmUserDao.selectCountByQuery(ecmUserQuery);

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

        return ResponseDTO.get( 1 == ecmUserDao.updateByPrimaryKeySelective(ecmUserVO));
    }




    /**
     * @param: [rolesVOList, acessVOList] 角色集合 和 权限集合
     * @return: void 无返回，完成后 rolesVOList 中 的 role 中的AcessVOList 会有对应的 权限
     * @author: cxd
     * @Date: 2020/8/6
     * 描述 :     将多个权限设置到多个对应角色
     *
     */
    private void setRolesAcess(List<EcmUserRolesVO> rolesVOList, List<EcmUserAcessVO> acessVOList) {

        Map<Integer, List<EcmUserAcessVO>> collect = acessVOList.stream().collect(Collectors.groupingBy(EcmUserAcessVO::getPkAcessId));
        rolesVOList.forEach( role -> {
            String[] split = role.getAcess().split(",");
            ArrayList<EcmUserAcessVO> ecmUserAcessVOS = new ArrayList<>();
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
