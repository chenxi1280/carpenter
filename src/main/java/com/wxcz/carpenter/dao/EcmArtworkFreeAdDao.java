package com.wxcz.carpenter.dao;

import com.wxcz.carpenter.pojo.entity.EcmArtworkFreeAd;
import com.wxcz.carpenter.pojo.query.EcmArtworkFreeAdQuery;
import com.wxcz.carpenter.pojo.vo.EcmArtworkVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Repository
public interface EcmArtworkFreeAdDao {
    int deleteByPrimaryKey(Integer pkEcmArtworkFreeAdId);

    int insert(EcmArtworkFreeAd record);

    int insertSelective(EcmArtworkFreeAd record);

    EcmArtworkFreeAd selectByPrimaryKey(Integer pkEcmArtworkFreeAdId);

    int updateByPrimaryKeySelective(EcmArtworkFreeAd record);

    int updateByPrimaryKey(EcmArtworkFreeAd record);

    List<EcmArtworkVO> selectListByEcmArtworkFreeAdQuery(EcmArtworkFreeAdQuery ecmArtworkFreeAdQuery);

    Integer selectCountByEcmArtworkFreeAdQuery(EcmArtworkFreeAdQuery ecmArtworkFreeAdQuery);

    List<EcmArtworkFreeAd> selectList();

    int deleteByEcmArtworkIdList(@Param("list") List<Integer> unFkArtworkIdList);

    List<EcmArtworkFreeAd> selectListByEcmArtworkIdList(@Param("list")  List<Integer> fkArtworkIdList);

    int insertArtWorkFreeAdList(@Param("list") List<Integer> fkArtworkIdList);

    int insertArtWorkFreeAdEcmArtworkVOList(@Param("list") Collection<EcmArtworkVO> collect);

}
