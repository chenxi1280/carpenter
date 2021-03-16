package com.wxcz.carpenter.dao;

import com.wxcz.carpenter.pojo.entity.EcmVipRoleAuthority;

public interface EcmVipRoleAuthorityDao {
    int deleteByPrimaryKey(Integer pkId);

    int insert(EcmVipRoleAuthority record);

    int insertSelective(EcmVipRoleAuthority record);

    EcmVipRoleAuthority selectByPrimaryKey(Integer pkId);

    int updateByPrimaryKeySelective(EcmVipRoleAuthority record);

    int updateByPrimaryKey(EcmVipRoleAuthority record);
}