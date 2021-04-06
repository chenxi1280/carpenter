package com.wxcz.carpenter.dao;

import com.wxcz.carpenter.pojo.entity.EcmArtworkNodeBuoy;
import com.wxcz.carpenter.pojo.vo.EcmArtworkNodesVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EcmArtworkNodeBuoyDao {
    int deleteByPrimaryKey(Integer pkBuoyId);

    int insert(EcmArtworkNodeBuoy record);

    int insertSelective(EcmArtworkNodeBuoy record);

    EcmArtworkNodeBuoy selectByPrimaryKey(Integer pkBuoyId);

    int updateByPrimaryKeySelective(EcmArtworkNodeBuoy record);

    int updateByPrimaryKey(EcmArtworkNodeBuoy record);

    List<EcmArtworkNodeBuoy> selectByEcmArtworkNodesList(@Param("list") List<EcmArtworkNodesVo> y);

    List<EcmArtworkNodeBuoy> selectByEcmArtworkId(Integer pkArtworkId);
}
