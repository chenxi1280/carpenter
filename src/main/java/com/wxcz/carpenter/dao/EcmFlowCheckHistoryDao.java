package com.wxcz.carpenter.dao;

import com.wxcz.carpenter.pojo.entity.EcmFlowCheckHistory;
import com.wxcz.carpenter.pojo.entity.EcmUserFlow;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EcmFlowCheckHistoryDao {
    int deleteByPrimaryKey(Integer flowCheckId);

    int insert(EcmFlowCheckHistory record);

    int insertSelective(EcmFlowCheckHistory record);

    EcmFlowCheckHistory selectByPrimaryKey(Integer flowCheckId);

    int updateByPrimaryKeySelective(EcmFlowCheckHistory record);

    int updateByPrimaryKey(EcmFlowCheckHistory record);


    Integer insertUserFlowByCheck(@Param("list") List<EcmUserFlow> ecmUserFlows);
}
