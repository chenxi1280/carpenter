package com.wxcz.carpenter.controller.back;

import com.wxcz.carpenter.pojo.dto.PageDTO;
import com.wxcz.carpenter.pojo.dto.ResponseDTO;
import com.wxcz.carpenter.pojo.entity.EcmAdSettings;
import com.wxcz.carpenter.pojo.query.EcmAdSettingsQuery;
import com.wxcz.carpenter.pojo.query.EcmArtworkQuery;
import com.wxcz.carpenter.pojo.vo.EcmAdSettingsVO;
import com.wxcz.carpenter.pojo.vo.EcmUserLightEventVO;
import com.wxcz.carpenter.service.AdvertisingService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @author by cxd
 * @Classname AdvertisingController
 * @Description TODO
 * @Date 2020/12/28 13:04
 */
@Controller
@RequestMapping("/back/advertising")
public class AdvertisingController {

    @Resource
    AdvertisingService advertisingService;

    @RequestMapping("adSettingsPage")
    public String adSettingsPage() {
        return "back/advertising/adSettings";
    }

    @RequestMapping("ajaxList")
    @ResponseBody
    public PageDTO ajaxList(EcmAdSettingsQuery ecmAdSettingsQuery) {
        return advertisingService.ajaxList(ecmAdSettingsQuery);
    }

    @RequestMapping("updateAdSettings")
    @ResponseBody
    public ResponseDTO updateAdSettings(EcmAdSettingsVO ecmAdSettingsVO){
        return advertisingService.updateAdSettings(ecmAdSettingsVO);
    }


}
