package com.wxcz.carpenter.service.impl;

import com.wxcz.carpenter.dao.EcmAdSettingsDao;
import com.wxcz.carpenter.dao.EcmBannerDao;
import com.wxcz.carpenter.pojo.dto.PageDTO;
import com.wxcz.carpenter.pojo.dto.ResponseDTO;
import com.wxcz.carpenter.pojo.entity.EcmBanner;
import com.wxcz.carpenter.pojo.query.EcmAdSettingsQuery;
import com.wxcz.carpenter.pojo.vo.EcmAdSettingsVO;
import com.wxcz.carpenter.service.AdvertisingService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author by cxd
 * @Classname AdvertisingServiceImpl
 * @Description TODO
 * @Date 2020/12/28 13:22
 */
@Service
public class AdvertisingServiceImpl implements AdvertisingService {
    @Resource
    EcmAdSettingsDao ecmAdSettingsDao;
    @Resource
    EcmBannerDao ecmBannerDao;


    @Override
    public PageDTO ajaxList(EcmAdSettingsQuery ecmAdSettingsQuery) {
        List<EcmAdSettingsVO> list =  ecmAdSettingsDao.selectajaxList(ecmAdSettingsQuery);
        Integer count = ecmAdSettingsDao.selectajaxListCount(ecmAdSettingsQuery);
        return PageDTO.setPageData(count,list);
    }

    @Override
    public ResponseDTO updateAdSettings(EcmAdSettingsVO ecmAdSettingsVO) {
        ecmAdSettingsDao.updateByPrimaryKeySelective(ecmAdSettingsVO);
        return ResponseDTO.ok();
    }

    @Override
    public ResponseDTO updateBannerAdSettings(EcmBanner ecmBanner) {
        ecmBanner.setEcmBannerId(1);
        return ResponseDTO.get(  ecmBannerDao.updateByPrimaryKeySelective(ecmBanner) == 1);
    }

    @Override
    public ResponseDTO getBannerAd(EcmBanner ecmBanner) {
        return ResponseDTO.ok( ecmBannerDao.selectByPrimaryKey(ecmBanner.getEcmBannerId()) );
    }

}
