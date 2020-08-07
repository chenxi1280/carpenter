package com.wxcz.carpenter.dao;

import com.wxcz.carpenter.pojo.entity.EcmUser;
import com.wxcz.carpenter.pojo.query.EcmUserQuery;
import com.wxcz.carpenter.pojo.vo.EcmUserVO;

import java.util.List;

public interface EcmUserDao {
    int deleteByPrimaryKey(Integer pkUserId);

    int insert(EcmUser record);

    int insertSelective(EcmUser record);

    EcmUser selectByPrimaryKey(Integer pkUserId);

    int updateByPrimaryKeySelective(EcmUser record);

    int updateByPrimaryKey(EcmUser record);

    EcmUserVO login(EcmUserQuery query);

    EcmUserVO selectByPhone(String mobile);

    List<EcmUserVO> selectListByQuery(EcmUserQuery ecmUserQuery);

    Integer selectCountByQuery(EcmUserQuery ecmUserQuery);
}