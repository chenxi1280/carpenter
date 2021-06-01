package com.wxcz.carpenter.service.impl;

import com.wxcz.carpenter.dao.EcmWorkOrderDao;
import com.wxcz.carpenter.pojo.dto.PageDTO;
import com.wxcz.carpenter.pojo.dto.ResponseDTO;
import com.wxcz.carpenter.pojo.query.EcmWorkOrderQuery;
import com.wxcz.carpenter.pojo.vo.EcmWorkOrderVO;
import com.wxcz.carpenter.service.EcmWorkOrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author by cxd
 * @Classname EcmWorkOrderServiceImpl
 * @Description TODO
 * @Date 2021/6/1 11:15
 */
@Service
public class EcmWorkOrderServiceImpl implements EcmWorkOrderService {
    @Resource
    EcmWorkOrderDao ecmWorkOrderDao;

    @Override
    public PageDTO ajaxWorkOrderList(EcmWorkOrderQuery ecmWorkOrderQuery) {
        List<EcmWorkOrderVO> list = ecmWorkOrderDao.selectListByEcmWorkOrderQuery(ecmWorkOrderQuery);
        Integer count = ecmWorkOrderDao.selectCountByEcmWorkOrderQuery(ecmWorkOrderQuery);
        return PageDTO.setPageData(count,list);
    }

    @Override
    public ResponseDTO updateWorkOrder(EcmWorkOrderVO ecmWorkOrderVO) {
        ecmWorkOrderVO.setUpdateTime(new Date());
        ecmWorkOrderDao.updateByPrimaryKeySelective(ecmWorkOrderVO);
        return ResponseDTO.ok();
    }
}
