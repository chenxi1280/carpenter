package com.wxcz.carpenter.dao;

import com.wxcz.carpenter.pojo.entity.EcmVipAuthority;

public interface EcmVipAuthorityDao {
    int deleteByPrimaryKey(Integer pkAuthorityId);

    int insert(EcmVipAuthority record);

    int insertSelective(EcmVipAuthority record);

    EcmVipAuthority selectByPrimaryKey(Integer pkAuthorityId);

    int updateByPrimaryKeySelective(EcmVipAuthority record);

    int updateByPrimaryKey(EcmVipAuthority record);
}