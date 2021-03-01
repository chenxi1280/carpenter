package com.wxcz.carpenter.service.impl;

import com.wxcz.carpenter.common.SecretKeyConstants;
import com.wxcz.carpenter.dao.EcmMerchantDao;
import com.wxcz.carpenter.dao.EcmUserDao;
import com.wxcz.carpenter.pojo.dto.PageDTO;
import com.wxcz.carpenter.pojo.dto.ResponseDTO;
import com.wxcz.carpenter.pojo.entity.EcmUser;
import com.wxcz.carpenter.pojo.query.EcmMerchantQuery;
import com.wxcz.carpenter.pojo.vo.EcmMerchantVO;
import com.wxcz.carpenter.service.MerchantService;
import com.wxcz.carpenter.util.EncryptUtil;
import com.wxcz.carpenter.util.JWTUtil;
import com.wxcz.carpenter.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

import static com.wxcz.carpenter.common.CommomConfig.H5_BASE_URL;

/**
 * @author by cxd
 * @Classname MerchantServiceImpl
 * @Description TODO
 * @Date 2021/2/22 10:56
 */
@Service
public class MerchantServiceImpl implements MerchantService {

//    /**
//     * 构造函数的依赖注入
//     */
//    private EcmMerchantDao ecmMerchantDao;
//
//    @Autowired
//    public MerchantServiceImpl (EcmMerchantDao ecmMerchantDao ) {
//        this.ecmMerchantDao = ecmMerchantDao;
//    }


    @Resource
    EcmMerchantDao ecmMerchantDao;

    @Resource
    EcmUserDao ecmUserDao;

    @Override
    public PageDTO ajaxList(EcmMerchantQuery ecmMerchantQuery) {


        List<EcmMerchantVO> list =ecmMerchantDao.selectByMerchantQuery(ecmMerchantQuery);

        Integer count = ecmMerchantDao.selectByMerchantQueryCount(ecmMerchantQuery);

        if (!CollectionUtils.isEmpty(list)) {
            list.forEach( ecmMerchantVO -> {
                ecmMerchantVO.setH5Url(ecmMerchantVO.getH5Url() + "?token=" + JWTUtil.sign(String.valueOf(ecmMerchantVO.getFkUserId()),
                        ecmMerchantVO.getUsername(), SecretKeyConstants.JWT_SECRET_KEY) );
            });
        }

        return PageDTO.setPageData(count,list);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseDTO addMerchant(EcmMerchantVO ecmMerchantVO) {
        EcmUser ecmUser = new EcmUser();
        ecmUser.setEmail(ecmMerchantVO.getEmail());
        ecmUser.setPassword(MD5Utils.encrypt(ecmMerchantVO.getMobilePhone()));
        ecmUser.setCreateTime(new Date());
        ecmUser.setLastLoginTime(new Date());
        ecmUser.setUsername(ecmMerchantVO.getMerchantName());
        ecmUser.setMobile(ecmMerchantVO.getMobilePhone());
        ecmUser.setUserLogoStatus((byte) 1);
        ecmUser.setRoles("1");
        try{
            ecmUserDao.insertSelective(ecmUser);
            ecmMerchantVO.setFkUserId(ecmUser.getPkUserId());
            ecmMerchantVO.setH5Url(H5_BASE_URL);
            ecmMerchantDao.insertSelective(ecmMerchantVO);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.fail();
        }
        return ResponseDTO.ok();
    }
}
