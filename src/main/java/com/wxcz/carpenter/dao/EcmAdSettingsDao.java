package com.wxcz.carpenter.dao;

import com.wxcz.carpenter.pojo.entity.EcmAdSettings;
import com.wxcz.carpenter.pojo.query.EcmAdSettingsQuery;
import com.wxcz.carpenter.pojo.vo.EcmAdSettingsVO;

import java.util.List;

public interface EcmAdSettingsDao {
    int deleteByPrimaryKey(Integer ecmAdSettingsId);

    int insert(EcmAdSettings record);

    int insertSelective(EcmAdSettings record);

    EcmAdSettings selectByPrimaryKey(Integer ecmAdSettingsId);

    int updateByPrimaryKeySelective(EcmAdSettings record);

    int updateByPrimaryKey(EcmAdSettings record);

    List<EcmAdSettingsVO> selectajaxList(EcmAdSettingsQuery ecmAdSettingsQuery);

    Integer selectajaxListCount(EcmAdSettingsQuery ecmAdSettingsQuery);
}
