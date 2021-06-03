package com.wxcz.carpenter.controller.back;

import com.wxcz.carpenter.controller.BaseController;
import com.wxcz.carpenter.pojo.dto.PageDTO;
import com.wxcz.carpenter.pojo.dto.ResponseDTO;
import com.wxcz.carpenter.pojo.query.EcmWorkOrderQuery;
import com.wxcz.carpenter.pojo.vo.EcmWorkOrderVO;
import com.wxcz.carpenter.service.EcmWorkOrderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @author by cxd
 * @Classname EcmWorkOrderController
 * @Description TODO
 * @Date 2021/6/1 11:13
 */
@Controller
@RequestMapping("back/workOrder")
public class EcmWorkOrderController extends BaseController {

    @Resource
    EcmWorkOrderService ecmWorkOrderService;

    @RequestMapping("workOrderPage")
    public String orderPage(){
        return  "back/workOrder/work-order-page";
    }

    @RequestMapping("ajaxWorkOrderList")
    @ResponseBody
    public PageDTO ajaxWorkOrderList(EcmWorkOrderQuery ecmWorkOrderQuery) {
        return ecmWorkOrderService.ajaxWorkOrderList(ecmWorkOrderQuery);
    }


    @RequestMapping("updateWorkOrder")
    @ResponseBody
    public ResponseDTO updateWorkOrder(EcmWorkOrderVO ecmWorkOrderVO) {
        ecmWorkOrderVO.setFkHandlerId((Integer) getRequstSession().getAttribute("userId"));
        return ecmWorkOrderService.updateWorkOrder(ecmWorkOrderVO);
    }
}
