package com.wxcz.carpenter.dao;

import com.wxcz.carpenter.pojo.entity.EcmUserHistoryFlow;
import com.wxcz.carpenter.pojo.vo.EcmUserHistoryFlowVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Repository
public interface EcmUserHistoryFlowDao {
    int deleteByPrimaryKey(Integer flowHistoryId);

    int insert(EcmUserHistoryFlow record);

    int insertSelective(EcmUserHistoryFlow record);

    EcmUserHistoryFlow selectByPrimaryKey(Integer flowHistoryId);

    int updateByPrimaryKeySelective(EcmUserHistoryFlow record);

    int updateByPrimaryKey(EcmUserHistoryFlow record);


    List<EcmUserHistoryFlowVO> selectUserFlowAll();

    List<EcmUserHistoryFlowVO> selectUserFlowByOneDay();

    List<EcmUserHistoryFlowVO> selectUserFlowByUserIds(@Param("ids") Collection userIds);
}
