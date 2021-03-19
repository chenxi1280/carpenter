package com.wxcz.carpenter.controller.back;

import com.wxcz.carpenter.pojo.dto.PageDTO;
import com.wxcz.carpenter.pojo.dto.ResponseDTO;
import com.wxcz.carpenter.pojo.entity.EcmGoods;
import com.wxcz.carpenter.pojo.entity.EcmGoodsCategory;
import com.wxcz.carpenter.pojo.query.EcmGoodsCategoryQuery;
import com.wxcz.carpenter.pojo.query.EcmGoodsQuery;
import com.wxcz.carpenter.pojo.query.EcmMerchantQuery;
import com.wxcz.carpenter.pojo.vo.EcmGoodsCategoryVO;
import com.wxcz.carpenter.pojo.vo.EcmGoodsVO;
import com.wxcz.carpenter.service.EcmGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author by cxd
 * @Classname EcmGoodsController
 * @Description TODO
 * @Date 2021/3/8 10:56
 */
@Controller
@RequestMapping("back/goods")
public class EcmGoodsController {

    final
    EcmGoodsService ecmGoodsService;

    public EcmGoodsController(EcmGoodsService ecmGoodsService) {
        this.ecmGoodsService = ecmGoodsService;
    }

    @RequestMapping("goodsPage")
    public String goodsPage() {
        return "back/goods/goodsPage";
    }

    @RequestMapping("goodsCategoryPage")
    public String goodsCategoryPage() {
        return "back/goods/goodsCategoryPage";
    }

    @RequestMapping("ajaxGoodsList")
    @ResponseBody
    public PageDTO ajaxGoodsList(EcmGoodsQuery ecmGoodsQuery) {
        return ecmGoodsService.ajaxGoodsList(ecmGoodsQuery);
    }

    @RequestMapping("ajaxGoodsCategoryList")
    @ResponseBody
    public PageDTO ajaxGoodsCategoryList(EcmGoodsCategoryQuery ecmGoodsCategoryQuery) {
        return ecmGoodsService.ajaxGoodsCategoryList(ecmGoodsCategoryQuery);
    }

    @RequestMapping("addGoodsCategory")
    @ResponseBody
    public ResponseDTO addGoodsCategory(EcmGoodsCategoryVO ecmGoodsCategoryVO) {
        return ecmGoodsService.addGoodsCategory(ecmGoodsCategoryVO);
    }

    @RequestMapping("addGoods")
    @ResponseBody
    public ResponseDTO addGoods(EcmGoodsVO ecmGoodsVO) {
        return ecmGoodsService.addGoods(ecmGoodsVO);
    }


    @RequestMapping("updateGoods")
    @ResponseBody
    public ResponseDTO updateGoods(EcmGoodsVO ecmGoodsVO) {
        return ecmGoodsService.updateGoods(ecmGoodsVO);
    }


    @RequestMapping("updateGoodsCategory")
    @ResponseBody
    public ResponseDTO updateGoodsCategory(EcmGoodsCategoryVO ecmGoodsCategoryVO) {
        return ecmGoodsService.updateGoodsCategory(ecmGoodsCategoryVO);
    }

}
