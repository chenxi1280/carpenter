package com.wxcz.carpenter.dao;

import com.wxcz.carpenter.pojo.entity.EcmVipRoleAuthority;
import com.wxcz.carpenter.pojo.query.EcmVipRoleAuthorityQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EcmVipRoleAuthorityDao {
    int deleteByPrimaryKey(Integer pkId);

    int insert(EcmVipRoleAuthority record);

    int insertSelective(EcmVipRoleAuthority record);

    EcmVipRoleAuthority selectByPrimaryKey(Integer pkId);

    List<EcmVipRoleAuthority> selectListByEcmVipAuthorityQuery(EcmVipRoleAuthorityQuery ecmVipRoleAuthorityQuery);

    Integer selectCountByEcmVipAuthorityQuery(EcmVipRoleAuthorityQuery ecmVipRoleAuthorityQuery);

    int updateByPrimaryKeySelective(EcmVipRoleAuthority record);

    int updateByPrimaryKey(EcmVipRoleAuthority record);
}