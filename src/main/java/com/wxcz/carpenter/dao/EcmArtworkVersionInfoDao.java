package com.wxcz.carpenter.dao;

import com.wxcz.carpenter.pojo.entity.EcmArtworkVersionInfo;
import com.wxcz.carpenter.pojo.query.EcmArtworkVersionInfoQuery;
import com.wxcz.carpenter.pojo.vo.EcmArtworkVersionInfoVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EcmArtworkVersionInfoDao {
    int deleteByPrimaryKey(Integer pkArtworkVersionInfoId);

    int insert(EcmArtworkVersionInfo record);

    int insertSelective(EcmArtworkVersionInfo record);

    EcmArtworkVersionInfo selectByPrimaryKey(Integer pkArtworkVersionInfoId);

    int updateByPrimaryKeySelective(EcmArtworkVersionInfo record);

    int updateByPrimaryKey(EcmArtworkVersionInfo record);

    List<EcmArtworkVersionInfoVO> selectListByEcmArtworkVersionInfoQuery(EcmArtworkVersionInfoQuery ecmArtworkVersionInfoQuery);

    Integer selectListCountByEcmArtworkVersionInfoQuery(EcmArtworkVersionInfoQuery ecmArtworkVersionInfoQuery);

    List<EcmArtworkVersionInfoVO> selectListByEcmArtworkVersionId(String versionId);

    Integer insertArtWorkVersionList( @Param("ecmArtworkVersionInfo") EcmArtworkVersionInfo ecmArtworkVersionInfo, @Param("list") List<Integer> fkArtworkIdList);

    EcmArtworkVersionInfo selectOneByVersionId(String versionId);

    List<EcmArtworkVersionInfoVO> selectListByEcmArtworkIdList(@Param("list") List<Integer> unFkArtworkIdList);

    Integer deleteByEcmArtworkVersionList(@Param("versionId")String versionId , @Param("list") List<Integer>  unFkArtworkIdList);
}
