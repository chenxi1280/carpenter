package com.wxcz.carpenter.service.impl;

import com.wxcz.carpenter.dao.EcmVipRoleAuthorityDao;
import com.wxcz.carpenter.pojo.dto.PageDTO;
import com.wxcz.carpenter.pojo.dto.ResponseDTO;
import com.wxcz.carpenter.pojo.entity.EcmVipRoleAuthority;
import com.wxcz.carpenter.pojo.query.EcmOrderQuery;
import com.wxcz.carpenter.pojo.query.EcmVipRoleAuthorityQuery;
import com.wxcz.carpenter.service.VipAuthorityService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author by SJ
 * @Classname VipAuthorityServiceImpl
 * @Description TODO
 * @Date 2021/03/16 17:30
 */
@Service
public class VipAuthorityServiceImpl implements VipAuthorityService {
    @Resource
    EcmVipRoleAuthorityDao ecmVipRoleAuthorityDao;

    @Override
    public PageDTO ajaxVipAuthorityList(EcmVipRoleAuthorityQuery ecmVipRoleAuthorityQuery) {
        Integer count = ecmVipRoleAuthorityDao.selectCountByEcmVipAuthorityQuery(ecmVipRoleAuthorityQuery);
        List<EcmVipRoleAuthority> list = ecmVipRoleAuthorityDao.selectListByEcmVipAuthorityQuery(ecmVipRoleAuthorityQuery);
        return PageDTO.setPageData(count,list);
    }

    @Override
    public ResponseDTO addVipRoleAuthority(EcmVipRoleAuthorityQuery ecmVipRoleAuthorityQuery) {
        EcmVipRoleAuthority ecmVipRoleAuthority = new EcmVipRoleAuthority();
        ecmVipRoleAuthority.setFkVipRoleId(ecmVipRoleAuthorityQuery.getFkVipRoleId());
        ecmVipRoleAuthority.setFkVipAuthorityId(ecmVipRoleAuthorityQuery.getFkVipAuthorityId());
        ecmVipRoleAuthority.setVipAuthorityDescribe(ecmVipRoleAuthorityQuery.getVipAuthorityDescribe());
        ecmVipRoleAuthority.setVipRoleDescribe(ecmVipRoleAuthorityQuery.getVipRoleDescribe());
        ecmVipRoleAuthority.setCreateTime(new Date());
        ecmVipRoleAuthority.setUpdateTime(new Date());
        return ResponseDTO.get(1 ==  ecmVipRoleAuthorityDao.insertSelective(ecmVipRoleAuthority));
    }

}
