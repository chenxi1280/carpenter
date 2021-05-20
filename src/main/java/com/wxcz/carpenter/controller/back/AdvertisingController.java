package com.wxcz.carpenter.controller.back;

import com.wxcz.carpenter.pojo.dto.PageDTO;
import com.wxcz.carpenter.pojo.dto.ResponseDTO;
import com.wxcz.carpenter.pojo.entity.EcmAdSettings;
import com.wxcz.carpenter.pojo.entity.EcmBanner;
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

    /**
     * @param: []
     * @return: java.lang.String
     * @author: cxd
     * @Date: 2020/12/29
     * 描述 : 广告配置页面跳转
     */
    @RequestMapping("adSettingsPage")
    public String adSettingsPage() {
        return "back/advertising/adSettings";
    }

    /**
     * @param: [ecmAdSettingsQuery]
     * @return: com.wxcz.carpenter.pojo.dto.PageDTO
     * @author: cxd
     * @Date: 2020/12/29
     * 描述 : 获取当前广告配置list
     *       成功: status 200  msg "success”   date:
     *       失败: status 500  msg "error“
     */
    @RequestMapping("ajaxList")
    @ResponseBody
    public PageDTO ajaxList(EcmAdSettingsQuery ecmAdSettingsQuery) {
        return advertisingService.ajaxList(ecmAdSettingsQuery);
    }

    /**
     * @param: [ecmAdSettingsVO]
     * @return: com.wxcz.carpenter.pojo.dto.ResponseDTO
     * @author: cxd
     * @Date: 2020/12/29
     * 描述 : 更新广告设置
     *       成功: status 200  msg "success”
     *       失败: status 500  msg "error“
     */
    @RequestMapping("updateAdSettings")
    @ResponseBody
    public ResponseDTO updateAdSettings(EcmAdSettingsVO ecmAdSettingsVO){
        return advertisingService.updateAdSettings(ecmAdSettingsVO);
    }

    @RequestMapping("bannerAdSettingsPage")
    public String BannerAdSettingsPage() {
        return "back/advertising/bannerAdSetting";
    }

    @RequestMapping("updateBannerAdSettings")
    @ResponseBody
    public ResponseDTO updateBannerAdSettings(EcmBanner ecmBanner){
        return advertisingService.updateBannerAdSettings(ecmBanner);
    }

    @RequestMapping("getBannerAd")
    @ResponseBody
    public ResponseDTO getBannerAd(EcmBanner ecmBanner){
        ecmBanner.setEcmBannerId(1);
        return advertisingService.getBannerAd(ecmBanner);
    }

    @RequestMapping("bannerAdSettingsPage")
    public String BannerAdSettingsPage() {
        return "back/advertising/bannerAdSetting";
    }

    @RequestMapping("updateBannerAdSettings")
    @ResponseBody
    public ResponseDTO updateBannerAdSettings(EcmBanner ecmBanner){
        return advertisingService.updateBannerAdSettings(ecmBanner);
    }

    @RequestMapping("getBannerAd")
    @ResponseBody
    public ResponseDTO getBannerAd(EcmBanner ecmBanner){
        ecmBanner.setEcmBannerId(1);
        return advertisingService.getBannerAd(ecmBanner);
    }
}
