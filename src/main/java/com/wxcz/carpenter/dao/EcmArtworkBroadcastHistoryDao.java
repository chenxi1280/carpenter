package com.wxcz.carpenter.dao;

import com.wxcz.carpenter.pojo.entity.EcmArtworkBroadcastHistory;
import com.wxcz.carpenter.pojo.vo.EcmArtworkBroadcastHistoryVO;

import java.util.List;

public interface EcmArtworkBroadcastHistoryDao {
    int deleteByPrimaryKey(Integer pkBroadcastId);

    int insert(EcmArtworkBroadcastHistory record);

    int insertSelective(EcmArtworkBroadcastHistory record);

    EcmArtworkBroadcastHistory selectByPrimaryKey(Integer pkBroadcastId);

    int updateByPrimaryKeySelective(EcmArtworkBroadcastHistory record);

    int updateByPrimaryKey(EcmArtworkBroadcastHistory record);

    List<EcmArtworkBroadcastHistoryVO> selectAll();

    List<EcmArtworkBroadcastHistoryVO> selectByOneDay();

    List<EcmArtworkBroadcastHistoryVO> selectHotByOneDay();

}
