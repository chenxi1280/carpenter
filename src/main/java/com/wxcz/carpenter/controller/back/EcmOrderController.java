package com.wxcz.carpenter.controller.back;

import com.wxcz.carpenter.pojo.dto.PageDTO;
import com.wxcz.carpenter.pojo.entity.EcmOrder;
import com.wxcz.carpenter.pojo.query.EcmGoodsQuery;
import com.wxcz.carpenter.pojo.query.EcmOrderQuery;
import com.wxcz.carpenter.service.EcmOrderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @author by cxd
 * @Classname EcmOrderController
 * @Description TODO
 * @Date 2021/3/8 13:17
 */
@Controller
@RequestMapping("back/order")
public class EcmOrderController {
    @Resource
    EcmOrderService ecmOrderService;

    @RequestMapping("orderPage")
    public String orderPage(){
       return  "back/order/orderPage";
    }

    @RequestMapping("ajaxOrderList")
    @ResponseBody
    public PageDTO ajaxGoodsList(EcmOrderQuery ecmOrderQuery) {
        return ecmOrderService.ajaxOrderList(ecmOrderQuery);
    }


}
