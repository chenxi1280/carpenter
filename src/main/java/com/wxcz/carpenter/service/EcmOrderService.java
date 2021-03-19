package com.wxcz.carpenter.service;

import com.wxcz.carpenter.pojo.dto.PageDTO;
import com.wxcz.carpenter.pojo.query.EcmOrderQuery;

/**
 * @author by cxd
 * @Classname EcmOrderService
 * @Description TODO
 * @Date 2021/3/8 13:18
 */
public interface EcmOrderService {
    PageDTO ajaxOrderList(EcmOrderQuery ecmOrderQuery);
}
