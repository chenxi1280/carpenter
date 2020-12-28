package com.wxcz.carpenter.service;

import com.wxcz.carpenter.pojo.dto.PageDTO;
import com.wxcz.carpenter.pojo.dto.ResponseDTO;
import com.wxcz.carpenter.pojo.query.EcmAdSettingsQuery;
import com.wxcz.carpenter.pojo.vo.EcmAdSettingsVO;

/**
 * @author by cxd
 * @Classname AdvertisingService
 * @Description TODO
 * @Date 2020/12/28 13:21
 */
public interface AdvertisingService {
    PageDTO ajaxList(EcmAdSettingsQuery ecmAdSettingsQuery);

    ResponseDTO updateAdSettings(EcmAdSettingsVO ecmAdSettingsVO);

}
