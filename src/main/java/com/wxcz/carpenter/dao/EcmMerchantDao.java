package com.wxcz.carpenter.dao;

import com.wxcz.carpenter.pojo.entity.EcmMerchant;
import com.wxcz.carpenter.pojo.query.EcmMerchantQuery;
import com.wxcz.carpenter.pojo.vo.EcmMerchantVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EcmMerchantDao {
    int deleteByPrimaryKey(Integer pkMerchantId);

    int insert(EcmMerchant record);

    int insertSelective(EcmMerchant record);

    EcmMerchant selectByPrimaryKey(Integer pkMerchantId);

    int updateByPrimaryKeySelective(EcmMerchant record);

    int updateByPrimaryKey(EcmMerchant record);

    List<EcmMerchantVO> selectByMerchantQuery(EcmMerchantQuery ecmMerchantQuery);

    Integer selectByMerchantQueryCount(EcmMerchantQuery ecmMerchantQuery);
}
