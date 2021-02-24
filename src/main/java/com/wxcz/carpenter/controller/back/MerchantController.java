package com.wxcz.carpenter.controller.back;

import com.wxcz.carpenter.pojo.dto.PageDTO;
import com.wxcz.carpenter.pojo.dto.ResponseDTO;
import com.wxcz.carpenter.pojo.entity.EcmMerchant;
import com.wxcz.carpenter.pojo.query.EcmMerchantQuery;
import com.wxcz.carpenter.pojo.query.EcmUserQuery;
import com.wxcz.carpenter.pojo.vo.EcmMerchantVO;
import com.wxcz.carpenter.pojo.vo.EcmUserVO;
import com.wxcz.carpenter.service.MerchantService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @author by cxd
 * @Classname MerchantController
 * @Description TODO
 * @Date 2021/2/22 10:54
 */
@Controller
@RequestMapping("/back/merchant")
public class MerchantController {

    @Resource
    MerchantService merchantService;


    @RequestMapping("/merchantPage")
    public String merchantPage() {
        return "back/merchant/merchantPage";
    }


    @RequestMapping("ajaxList")
    @ResponseBody
    public PageDTO ajaxList(EcmMerchantQuery ecmMerchantQuery) {
        return merchantService.ajaxList(ecmMerchantQuery);
    }

    @RequestMapping("addMerchant")
    @ResponseBody
    public ResponseDTO addMerchant(EcmMerchantVO ecmMerchantVO) {
        return merchantService.addMerchant(ecmMerchantVO);
    }

}
