package com.wxcz.carpenter.service;

import com.wxcz.carpenter.pojo.dto.PageDTO;
import com.wxcz.carpenter.pojo.dto.ResponseDTO;
import com.wxcz.carpenter.pojo.query.EcmOrderQuery;
import com.wxcz.carpenter.pojo.query.EcmVipRoleAuthorityQuery;

/**
 * @author by SJ
 * @Classname VipAuthorityService
 * @Description TODO
 * @Date 2021/03/16 17:30
 */
public interface VipAuthorityService {

    PageDTO ajaxVipAuthorityList(EcmVipRoleAuthorityQuery ecmVipRoleAuthorityQuery);

    ResponseDTO addVipRoleAuthority(EcmVipRoleAuthorityQuery ecmVipRoleAuthorityQuery);

    ResponseDTO updateVipRoleAuthority(EcmVipRoleAuthorityQuery ecmVipRoleAuthorityQuery);
}
