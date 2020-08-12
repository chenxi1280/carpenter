package com.wxcz.carpenter.dao;

import com.wxcz.carpenter.pojo.entity.EcmArtwork;
import com.wxcz.carpenter.pojo.query.EcmArtworkQuery;
import com.wxcz.carpenter.pojo.vo.EcmArtworkVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EcmArtworkDao {
    int deleteByPrimaryKey(Integer pkArtworkId);

    int insert(EcmArtwork record);

    int insertSelective(EcmArtwork record);

    EcmArtwork selectByPrimaryKey(Integer pkArtworkId);

    int updateByPrimaryKeySelective(EcmArtwork record);

    int updateByPrimaryKey(EcmArtwork record);

    List<EcmArtworkVO> selectajaxList(EcmArtworkQuery ecmArtworkQuery);

    Integer selectCountByQuery(EcmArtworkQuery ecmArtworkQuery);

    List<EcmArtworkVO> selectajaxCheckList(EcmArtworkQuery ecmArtworkQuery);

    Integer selectCountByCheckList(EcmArtworkQuery ecmArtworkQuery);

    List<EcmArtworkVO> selectajaxListByQuery(EcmArtworkQuery ecmArtworkQuery);
}