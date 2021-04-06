package com.wxcz.carpenter.service.impl;

import com.wxcz.carpenter.dao.EcmVipAuthorityDao;
import com.wxcz.carpenter.pojo.dto.PageDTO;
import com.wxcz.carpenter.pojo.dto.ResponseDTO;
import com.wxcz.carpenter.pojo.entity.EcmVipAuthority;
import com.wxcz.carpenter.pojo.entity.EcmVipRoleAuthority;
import com.wxcz.carpenter.pojo.query.EcmVipAuthorityQuery;
import com.wxcz.carpenter.pojo.query.EcmVipRoleAuthorityQuery;
import com.wxcz.carpenter.service.AuthorityService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author by SJ
 * @Classname AuthorityServiceImpl
 * @Description TODO
 * @Date 2021/03/16 17:30
 */
@Service
public class AuthorityServiceImpl implements AuthorityService {
    @Resource
    EcmVipAuthorityDao ecmVipAuthorityDao;

    @Override
    public PageDTO ajaxAuthorityList(EcmVipAuthorityQuery ecmVipAuthorityQuery) {
        List<EcmVipAuthority> list = ecmVipAuthorityDao.selectListByEcmVipAuthorityQuery(ecmVipAuthorityQuery);
        Integer count = ecmVipAuthorityDao.selectCountByEcmVipAuthorityQuery(ecmVipAuthorityQuery);
        return PageDTO.setPageData(count,list);
    }

    @Override
    public ResponseDTO addAuthority(EcmVipAuthorityQuery ecmVipAuthorityQuery) {
        EcmVipAuthority ecmVipAuthority = new EcmVipAuthority();
        ecmVipAuthority.setVipAuthorityDescribe(ecmVipAuthorityQuery.getVipAuthorityDescribe());
        ecmVipAuthority.setCreateTime(new Date());
        ecmVipAuthority.setUpdateTime(new Date());
        return ResponseDTO.get(1 ==  ecmVipAuthorityDao.insertSelective(ecmVipAuthority));
    }
}
