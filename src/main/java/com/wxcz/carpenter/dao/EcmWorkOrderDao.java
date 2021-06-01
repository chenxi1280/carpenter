package com.wxcz.carpenter.dao;

import com.wxcz.carpenter.pojo.entity.EcmWorkOrder;

public interface EcmWorkOrderDao {
    int deleteByPrimaryKey(Integer pkWorkOrderId);

    int insert(EcmWorkOrder record);

    int insertSelective(EcmWorkOrder record);

    EcmWorkOrder selectByPrimaryKey(Integer pkWorkOrderId);

    int updateByPrimaryKeySelective(EcmWorkOrder record);

    int updateByPrimaryKey(EcmWorkOrder record);
}