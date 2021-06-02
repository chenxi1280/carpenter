package com.wxcz.carpenter.dao;

import com.wxcz.carpenter.pojo.entity.EcmWorkOrder;
import com.wxcz.carpenter.pojo.query.EcmWorkOrderQuery;
import com.wxcz.carpenter.pojo.vo.EcmWorkOrderVO;

import java.util.List;

public interface EcmWorkOrderDao {
    int deleteByPrimaryKey(Integer pkWorkOrderId);

    int insert(EcmWorkOrder record);

    int insertSelective(EcmWorkOrder record);

    EcmWorkOrder selectByPrimaryKey(Integer pkWorkOrderId);

    int updateByPrimaryKeySelective(EcmWorkOrder record);

    int updateByPrimaryKey(EcmWorkOrder record);

    List<EcmWorkOrderVO> selectListByEcmWorkOrderQuery(EcmWorkOrderQuery ecmWorkOrderQuery);

    Integer selectCountByEcmWorkOrderQuery(EcmWorkOrderQuery ecmWorkOrderQuery);
}