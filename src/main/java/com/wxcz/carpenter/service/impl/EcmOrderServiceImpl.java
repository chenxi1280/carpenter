package com.wxcz.carpenter.service.impl;

import com.wxcz.carpenter.dao.EcmOrderDao;
import com.wxcz.carpenter.pojo.dto.PageDTO;
import com.wxcz.carpenter.pojo.query.EcmOrderQuery;
import com.wxcz.carpenter.pojo.vo.EcmGoodsVO;
import com.wxcz.carpenter.pojo.vo.EcmOrderVO;
import com.wxcz.carpenter.service.EcmOrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Classname EcmOrderServiceImpl
 * @Description TODO
 * @Date 2021/3/8 13:18
 * @author by cxd
 */
@Service
public class EcmOrderServiceImpl implements EcmOrderService {
    @Resource
    EcmOrderDao ecmOrderDao;


    @Override
    public PageDTO ajaxOrderList(EcmOrderQuery ecmOrderQuery) {

        List<EcmOrderVO> list = ecmOrderDao.selectListByEcmOrderQuery(ecmOrderQuery);
        Integer count = ecmOrderDao.selectCountByEcmOrderQuery(ecmOrderQuery);

        return PageDTO.setPageData(count,list);
    }
}
