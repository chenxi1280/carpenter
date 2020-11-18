package com.wxcz.carpenter.service;

import com.wxcz.carpenter.pojo.dto.PageDTO;
import com.wxcz.carpenter.pojo.dto.ResponseDTO;
import com.wxcz.carpenter.pojo.query.EcmLightManagementQuery;
import com.wxcz.carpenter.pojo.vo.EcmUserLightEventVO;
import com.wxcz.carpenter.pojo.vo.EcmUserLightRewardVO;
import com.wxcz.carpenter.pojo.vo.EcmUserLightVipVO;

/**
 * @author by cxd
 * @Classname EcmLightManagementService
 * @Description TODO
 * @Date 2020/11/16 17:44
 */
public interface EcmLightManagementService {

    PageDTO ajaxLightVipList(EcmLightManagementQuery ecmTemplateQuery);

    PageDTO ajaxLightEventList(EcmLightManagementQuery ecmTemplateQuery);

    PageDTO ajaxLightRewardList(EcmLightManagementQuery ecmTemplateQuery);

    ResponseDTO addLightVip(EcmUserLightVipVO ecmUserLightVipVO);

    ResponseDTO addLightEvent(EcmUserLightEventVO ecmUserLightEventVO);

    ResponseDTO updataLightVip(EcmUserLightVipVO ecmUserLightVipVO);

    ResponseDTO updataLightEvent(EcmUserLightEventVO ecmUserLightEventVO);

    PageDTO ajaxLightVipListAndLightEventList(EcmLightManagementQuery ecmLightManagementQuery);

    ResponseDTO addLightReward(EcmUserLightRewardVO ecmUserLightRewardVO);

    ResponseDTO updataLightReward(EcmUserLightRewardVO ecmUserLightRewardVO);

    ResponseDTO delLightReward(EcmUserLightRewardVO ecmUserLightRewardVO);

}
