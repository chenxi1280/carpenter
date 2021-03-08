package com.wxcz.carpenter.dao;

import com.wxcz.carpenter.pojo.entity.EcmGoods;
import com.wxcz.carpenter.pojo.query.EcmGoodsQuery;
import com.wxcz.carpenter.pojo.vo.EcmGoodsVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EcmGoodsDao {
    int deleteByPrimaryKey(Integer pkGoodsId);

    int insert(EcmGoods record);

    int insertSelective(EcmGoods record);

    EcmGoods selectByPrimaryKey(Integer pkGoodsId);

    int updateByPrimaryKeySelective(EcmGoods record);

    int updateByPrimaryKey(EcmGoods record);

    List<EcmGoodsVO> selectListByEcmGoodsQuery(EcmGoodsQuery ecmGoodsQuery);

    Integer selectCountByEcmGoodsQuery(EcmGoodsQuery ecmGoodsQuery);
}
