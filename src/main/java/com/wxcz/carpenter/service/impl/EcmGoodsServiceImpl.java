package com.wxcz.carpenter.service.impl;

import com.fasterxml.jackson.databind.ser.std.StdArraySerializers;
import com.wxcz.carpenter.dao.EcmGoodsCategoryDao;
import com.wxcz.carpenter.dao.EcmGoodsDao;
import com.wxcz.carpenter.pojo.dto.PageDTO;
import com.wxcz.carpenter.pojo.dto.ResponseDTO;
import com.wxcz.carpenter.pojo.entity.EcmGoodsCategory;
import com.wxcz.carpenter.pojo.query.EcmGoodsCategoryQuery;
import com.wxcz.carpenter.pojo.query.EcmGoodsQuery;
import com.wxcz.carpenter.pojo.vo.EcmGoodsCategoryVO;
import com.wxcz.carpenter.pojo.vo.EcmGoodsVO;
import com.wxcz.carpenter.service.EcmGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author by cxd
 * @Classname EcmGoodsServiceImpl
 * @Description TODO
 * @Date 2021/3/8 10:57
 */
@Service
public class EcmGoodsServiceImpl implements EcmGoodsService {

    final
    EcmGoodsDao ecmGoodsDao;
    final
    EcmGoodsCategoryDao ecmGoodsCategoryDao;

    public EcmGoodsServiceImpl(EcmGoodsCategoryDao ecmGoodsCategoryDao, EcmGoodsDao ecmGoodsDao) {
        this.ecmGoodsCategoryDao = ecmGoodsCategoryDao;
        this.ecmGoodsDao = ecmGoodsDao;
    }

    @Override
    public PageDTO ajaxGoodsList(EcmGoodsQuery ecmGoodsQuery) {

        List<EcmGoodsVO> list = ecmGoodsDao.selectListByEcmGoodsQuery(ecmGoodsQuery);
        Integer count = ecmGoodsDao.selectCountByEcmGoodsQuery(ecmGoodsQuery);

        return PageDTO.setPageData(count,list);
    }

    @Override
    public PageDTO ajaxGoodsCategoryList(EcmGoodsCategoryQuery ecmGoodsCategoryQuery) {

        List<EcmGoodsCategoryVO> list = ecmGoodsCategoryDao.selectListByEcmGoodsCategoryQuery(ecmGoodsCategoryQuery);
        Integer count = ecmGoodsCategoryDao.selectCountByEcmGoodsCategoryQuery(ecmGoodsCategoryQuery);

        return PageDTO.setPageData(count,list);
    }

    @Override
    public ResponseDTO addGoodsCategory(EcmGoodsCategoryVO ecmGoodsCategoryVO) {
        ecmGoodsCategoryVO.setCreateTime(new Date());
        return ResponseDTO.get(1 ==  ecmGoodsCategoryDao.insertSelective(ecmGoodsCategoryVO));
    }

    @Override
    public ResponseDTO addGoods(EcmGoodsVO ecmGoodsVO) {
        ecmGoodsVO.setCreateTime( new Date());
        return ResponseDTO.get(1 == ecmGoodsDao.insertSelective(ecmGoodsVO));
    }

    @Override
    public ResponseDTO updateGoods(EcmGoodsVO ecmGoodsVO) {
        ecmGoodsVO.setUpdateTime(new Date());
        return  ResponseDTO.get(1 == ecmGoodsDao.updateByPrimaryKeySelective(ecmGoodsVO));
    }

    @Override
    public ResponseDTO updateGoodsCategory(EcmGoodsCategoryVO ecmGoodsCategoryVO) {
        ecmGoodsCategoryVO.setUpdateTime(new Date());
        return ResponseDTO.get(1 == ecmGoodsCategoryDao.updateByPrimaryKeySelective(ecmGoodsCategoryVO));
    }
}
