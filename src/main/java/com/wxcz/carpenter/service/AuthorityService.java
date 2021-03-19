package com.wxcz.carpenter.service;

import com.wxcz.carpenter.pojo.dto.PageDTO;
import com.wxcz.carpenter.pojo.query.EcmOrderQuery;
import com.wxcz.carpenter.pojo.query.EcmVipAuthorityQuery;

/**
 * @author by SJ
 * @Classname AuthorityService
 * @Description TODO
 * @Date 2021/03/16 17:30
 */
public interface AuthorityService {

    PageDTO ajaxAuthorityList(EcmVipAuthorityQuery ecmVipAuthorityQuery);

}
