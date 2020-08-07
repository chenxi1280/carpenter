package com.wxcz.carpenter.dao;

import com.wxcz.carpenter.pojo.entity.EcmUserRoles;
import com.wxcz.carpenter.pojo.vo.EcmUserRolesVO;
import com.wxcz.carpenter.pojo.vo.EcmUserVO;

import java.util.List;

public interface EcmUserRolesDao {
    int deleteByPrimaryKey(Integer pkRoleId);

    int insert(EcmUserRoles record);

    int insertSelective(EcmUserRoles record);

    EcmUserRoles selectByPrimaryKey(Integer pkRoleId);

    int updateByPrimaryKeySelective(EcmUserRoles record);

    int updateByPrimaryKey(EcmUserRoles record);



    List<EcmUserRolesVO> selectByRoles(String roles);
}