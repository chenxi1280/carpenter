package com.wxcz.carpenter.service.impl;

import com.wxcz.carpenter.dao.EcmUserLightEventDao;
import com.wxcz.carpenter.dao.EcmUserLightRewardDao;
import com.wxcz.carpenter.dao.EcmUserLightVipDao;
import com.wxcz.carpenter.pojo.dto.PageDTO;
import com.wxcz.carpenter.pojo.dto.ResponseDTO;
import com.wxcz.carpenter.pojo.entity.EcmUserLightEvent;
import com.wxcz.carpenter.pojo.entity.EcmUserLightReward;
import com.wxcz.carpenter.pojo.entity.EcmUserLightVip;
import com.wxcz.carpenter.pojo.query.EcmLightManagementQuery;
import com.wxcz.carpenter.pojo.vo.EcmUserLightEventVO;
import com.wxcz.carpenter.pojo.vo.EcmUserLightRewardVO;
import com.wxcz.carpenter.pojo.vo.EcmUserLightVipVO;
import com.wxcz.carpenter.service.EcmLightManagementService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author by cxd
 * @Classname EcmLightManagementServiceImpl
 * @Description TODO
 * @Date 2020/11/16 17:44
 */
@Service
public class EcmLightManagementServiceImpl implements EcmLightManagementService {

    @Resource
    EcmUserLightEventDao ecmUserLightEventDao;

    @Resource
    EcmUserLightRewardDao ecmUserLightRewardDao;

    @Resource
    EcmUserLightVipDao ecmUserLightVipDao;


    @Override
    public PageDTO ajaxLightVipList(EcmLightManagementQuery ecmTemplateQuery) {

        List<EcmUserLightVipVO> list = ecmUserLightVipDao.ajaxLightVipList(ecmTemplateQuery);
        Integer count = ecmUserLightVipDao.ajaxLightVipListCount(ecmTemplateQuery);
        return PageDTO.setPageData(count,list);

    }

    @Override
    public PageDTO ajaxLightEventList(EcmLightManagementQuery ecmTemplateQuery) {

        List<EcmUserLightEventVO> list = ecmUserLightEventDao.ajaxLightEventList(ecmTemplateQuery);
        Integer count = ecmUserLightEventDao.ajaxLightEventListCount(ecmTemplateQuery);
        return PageDTO.setPageData(count,list);
    }

    @Override
    public PageDTO ajaxLightRewardList(EcmLightManagementQuery ecmTemplateQuery) {

        List<EcmUserLightRewardVO> list = ecmUserLightRewardDao.ajaxLightRewardList(ecmTemplateQuery);
        Integer count = ecmUserLightRewardDao.ajaxLightRewardListCount(ecmTemplateQuery);
        if (!CollectionUtils.isEmpty(list)) {
            List<EcmUserLightEventVO> lightEventVOS =  ecmUserLightEventDao.selectByEcmUserLightRewardVOList(list);
            List<EcmUserLightVipVO> lightVipVOS =  ecmUserLightVipDao.selectByEcmUserLightRewardVOList(list);
            list.forEach(ecmUserLightRewardVO ->  {
                lightEventVOS.forEach( ecmUserLightEventVO ->  {
                    if (ecmUserLightRewardVO.getFkEcmUserLightEventId().equals(ecmUserLightEventVO.getEcmUserLightEventId())) {
                        ecmUserLightRewardVO.setEcmUserLightEventName(ecmUserLightEventVO.getEcmUserLightEventName());
                    }
                });
                lightVipVOS.forEach( ecmUserLightVipVO ->  {
                    if (ecmUserLightRewardVO.getFkEcmUserLightEventId().equals(ecmUserLightVipVO.getEcmUserLightVipId())) {
                        ecmUserLightRewardVO.setEcmUserLightVipName(ecmUserLightVipVO.getEcmUserLightVipName());
                    }
                });
            } );

        }
        return PageDTO.setPageData(count,list);
    }

    @Override
    public ResponseDTO addLightVip(EcmUserLightVipVO ecmUserLightVipVO) {
        return ResponseDTO.get(1 == ecmUserLightVipDao.insertSelective(ecmUserLightVipVO));
    }

    @Override
    public ResponseDTO addLightEvent(EcmUserLightEventVO ecmUserLightEventVO) {
        return ResponseDTO.get(1 == ecmUserLightEventDao.insertSelective(ecmUserLightEventVO));
    }

    @Override
    public ResponseDTO updataLightVip(EcmUserLightVipVO ecmUserLightVipVO) {
        return ResponseDTO.get(1 == ecmUserLightVipDao.updateByPrimaryKeySelective(ecmUserLightVipVO));
    }

    @Override
    public ResponseDTO updataLightEvent(EcmUserLightEventVO ecmUserLightEventVO) {
        return  ResponseDTO.get(1 == ecmUserLightEventDao.updateByPrimaryKeySelective(ecmUserLightEventVO));
    }


}
