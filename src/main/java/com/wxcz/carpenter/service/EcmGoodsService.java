package com.wxcz.carpenter.service;

import com.wxcz.carpenter.pojo.dto.PageDTO;
import com.wxcz.carpenter.pojo.dto.ResponseDTO;
import com.wxcz.carpenter.pojo.query.EcmGoodsCategoryQuery;
import com.wxcz.carpenter.pojo.query.EcmGoodsQuery;
import com.wxcz.carpenter.pojo.vo.EcmGoodsCategoryVO;
import com.wxcz.carpenter.pojo.vo.EcmGoodsVO;

/**
 * @author by cxd
 * @Classname EcmGoodsService
 * @Description TODO
 * @Date 2021/3/8 10:57
 */
public interface EcmGoodsService {

    PageDTO ajaxGoodsList(EcmGoodsQuery ecmGoodsQuery);

    PageDTO ajaxGoodsCategoryList(EcmGoodsCategoryQuery ecmGoodsCategoryQuery);

    ResponseDTO addGoodsCategory(EcmGoodsCategoryVO ecmGoodsCategoryVO);

    ResponseDTO addGoods(EcmGoodsVO ecmGoodsVO);

    ResponseDTO updateGoods(EcmGoodsVO ecmGoodsVO);

    ResponseDTO updateGoodsCategory(EcmGoodsCategoryVO ecmGoodsCategoryVO);
}
