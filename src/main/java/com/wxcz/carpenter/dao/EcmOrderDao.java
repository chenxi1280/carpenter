package com.wxcz.carpenter.dao;

import com.wxcz.carpenter.pojo.entity.EcmOrder;
import com.wxcz.carpenter.pojo.query.EcmOrderQuery;
import com.wxcz.carpenter.pojo.vo.EcmOrderVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EcmOrderDao {
    int deleteByPrimaryKey(Integer pkOrderId);

    int insert(EcmOrder record);

    int insertSelective(EcmOrder record);

    EcmOrder selectByPrimaryKey(Integer pkOrderId);

    int updateByPrimaryKeySelective(EcmOrder record);

    int updateByPrimaryKey(EcmOrder record);

    List<EcmOrderVO> selectListByEcmOrderQuery(EcmOrderQuery ecmOrderQuery);

    Integer selectCountByEcmOrderQuery(EcmOrderQuery ecmOrderQuery);
}
