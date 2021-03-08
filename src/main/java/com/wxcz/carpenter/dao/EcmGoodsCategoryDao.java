package com.wxcz.carpenter.dao;

import com.wxcz.carpenter.pojo.entity.EcmGoodsCategory;
import com.wxcz.carpenter.pojo.query.EcmGoodsCategoryQuery;
import com.wxcz.carpenter.pojo.vo.EcmGoodsCategoryVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EcmGoodsCategoryDao {
    int deleteByPrimaryKey(Integer pkCategoryId);

    int insert(EcmGoodsCategory record);

    int insertSelective(EcmGoodsCategory record);

    EcmGoodsCategory selectByPrimaryKey(Integer pkCategoryId);

    int updateByPrimaryKeySelective(EcmGoodsCategory record);

    int updateByPrimaryKey(EcmGoodsCategory record);

    List<EcmGoodsCategoryVO> selectListByEcmGoodsCategoryQuery(EcmGoodsCategoryQuery ecmGoodsCategoryQuery);

    Integer selectCountByEcmGoodsCategoryQuery(EcmGoodsCategoryQuery ecmGoodsCategoryQuery);
}
