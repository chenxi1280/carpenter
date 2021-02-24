package com.wxcz.carpenter.service;

import com.wxcz.carpenter.pojo.dto.PageDTO;
import com.wxcz.carpenter.pojo.dto.ResponseDTO;
import com.wxcz.carpenter.pojo.query.EcmMerchantQuery;
import com.wxcz.carpenter.pojo.vo.EcmMerchantVO;
import org.springframework.stereotype.Service;

/**
 * @author by cxd
 * @Classname MerchantService
 * @Description TODO
 * @Date 2021/2/22 10:56
 */
@Service
public interface MerchantService {
    PageDTO ajaxList(EcmMerchantQuery ecmMerchantQuery);

    ResponseDTO addMerchant(EcmMerchantVO ecmMerchantVO);
}
