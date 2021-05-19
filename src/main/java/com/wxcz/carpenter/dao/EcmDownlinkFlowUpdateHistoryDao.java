package com.wxcz.carpenter.dao;

import com.wxcz.carpenter.pojo.entity.EcmDownlinkFlowUpdateHistory;

public interface EcmDownlinkFlowUpdateHistoryDao {
    int deleteByPrimaryKey(Integer pkId);

    int insert(EcmDownlinkFlowUpdateHistory record);

    int insertSelective(EcmDownlinkFlowUpdateHistory record);

    EcmDownlinkFlowUpdateHistory selectByPrimaryKey(Integer pkId);

    int updateByPrimaryKeySelective(EcmDownlinkFlowUpdateHistory record);

    int updateByPrimaryKey(EcmDownlinkFlowUpdateHistory record);
}