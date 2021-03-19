package com.wxcz.carpenter.dao;

import com.wxcz.carpenter.pojo.entity.EcmVipAuthority;
import com.wxcz.carpenter.pojo.entity.EcmVipRoleAuthority;
import com.wxcz.carpenter.pojo.query.EcmVipAuthorityQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EcmVipAuthorityDao {
    int deleteByPrimaryKey(Integer pkAuthorityId);

    int insert(EcmVipAuthority record);

    int insertSelective(EcmVipAuthority record);

    EcmVipAuthority selectByPrimaryKey(Integer pkAuthorityId);

    List<EcmVipAuthority> selectListByEcmVipAuthorityQuery(EcmVipAuthorityQuery ecmVipAuthorityQuery);

    Integer selectCountByEcmVipAuthorityQuery(EcmVipAuthorityQuery ecmVipAuthorityQuery);

    int updateByPrimaryKeySelective(EcmVipAuthority record);

    int updateByPrimaryKey(EcmVipAuthority record);
}