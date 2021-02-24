package com.wxcz.carpenter.dao;

import com.wxcz.carpenter.pojo.entity.EcmArtworkEndings;
import com.wxcz.carpenter.pojo.vo.EcmArtworkEndingsVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EcmArtworkEndingsDao {
    int deleteByPrimaryKey(Integer pkEndingsId);

    int insert(EcmArtworkEndings record);

    int insertSelective(EcmArtworkEndings record);

    EcmArtworkEndings selectByPrimaryKey(Integer pkEndingsId);

    int updateByPrimaryKeySelective(EcmArtworkEndings record);

    int updateByPrimaryKey(EcmArtworkEndings record);

    List<EcmArtworkEndingsVO> selectByArtwId(Integer pkArtworkId);
}
