package com.wxcz.carpenter.dao;

import com.wxcz.carpenter.pojo.entity.EcmBanner;

public interface EcmBannerDao {
    int deleteByPrimaryKey(Integer ecmBannerId);

    int insert(EcmBanner record);

    int insertSelective(EcmBanner record);

    EcmBanner selectByPrimaryKey(Integer ecmBannerId);

    int updateByPrimaryKeySelective(EcmBanner record);

    int updateByPrimaryKey(EcmBanner record);
}