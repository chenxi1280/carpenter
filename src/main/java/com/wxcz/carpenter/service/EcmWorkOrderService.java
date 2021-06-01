package com.wxcz.carpenter.service;

import com.wxcz.carpenter.pojo.dto.PageDTO;
import com.wxcz.carpenter.pojo.dto.ResponseDTO;
import com.wxcz.carpenter.pojo.query.EcmWorkOrderQuery;
import com.wxcz.carpenter.pojo.vo.EcmWorkOrderVO;

/**
 * @author by cxd
 * @Classname EcmmWorkOrderService
 * @Description TODO
 * @Date 2021/6/1 11:14
 */
public interface EcmWorkOrderService {

    PageDTO ajaxWorkOrderList(EcmWorkOrderQuery ecmWorkOrderQuery);

    ResponseDTO updateWorkOrder(EcmWorkOrderVO ecmWorkOrderVO);
}
