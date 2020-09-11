package com.wxcz.carpenter.dao;

import com.wxcz.carpenter.pojo.entity.EcmUserHistoryFlow;
import com.wxcz.carpenter.pojo.vo.EcmUserHistoryFlowVO;

import java.util.List;

public interface EcmUserHistoryFlowDao {
    int deleteByPrimaryKey(Integer flowHistoryId);

    int insert(EcmUserHistoryFlow record);

    int insertSelective(EcmUserHistoryFlow record);

    EcmUserHistoryFlow selectByPrimaryKey(Integer flowHistoryId);

    int updateByPrimaryKeySelective(EcmUserHistoryFlow record);

    int updateByPrimaryKey(EcmUserHistoryFlow record);


    List<EcmUserHistoryFlowVO> selectUserFlowAll();

}