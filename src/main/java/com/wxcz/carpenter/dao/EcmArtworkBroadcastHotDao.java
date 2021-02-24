package com.wxcz.carpenter.dao;

import com.wxcz.carpenter.pojo.entity.EcmArtworkBroadcastHot;
import com.wxcz.carpenter.pojo.vo.EcmArtworkBroadcastHistoryVO;
import com.wxcz.carpenter.pojo.vo.EcmArtworkBroadcastHotVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Repository
public interface EcmArtworkBroadcastHotDao {
    int deleteByPrimaryKey(Integer pkBroadcastHotId);

    int insert(EcmArtworkBroadcastHot record);

    int insertSelective(EcmArtworkBroadcastHot record);

    EcmArtworkBroadcastHot selectByPrimaryKey(Integer pkBroadcastHotId);

    int updateByPrimaryKeySelective(EcmArtworkBroadcastHot record);

    int updateByPrimaryKey(EcmArtworkBroadcastHot record);

    List<EcmArtworkBroadcastHotVO> selectByArtworkIds(@Param("ids") Collection<Integer> integers);

    int updateByNewBroadcastHot(@Param("list") List<EcmArtworkBroadcastHotVO> ecmArtworkBroadcastHotVOList);

    EcmArtworkBroadcastHotVO selectByArtworkId(Integer pkArtworkId);

    List<EcmArtworkBroadcastHotVO> selectByEcmArtworkBroadcastHistoryVOs(@Param("list")  List<EcmArtworkBroadcastHistoryVO> ecmArtworkBroadcastHistoryVOS);
}
