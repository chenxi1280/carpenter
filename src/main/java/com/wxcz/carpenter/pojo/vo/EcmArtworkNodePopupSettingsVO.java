package com.wxcz.carpenter.pojo.vo;

import com.wxcz.carpenter.pojo.entity.EcmArtworkNodePopupSettings;
import lombok.Data;

/**
 * @author by cxd
 * @Classname EcmArtworkNodePopupSettingsVO
 * @Description TODO
 * @Date 2021/1/4 12:36
 */
@Data
public class EcmArtworkNodePopupSettingsVO extends EcmArtworkNodePopupSettings {
    private int fkUserId;

    /**
     * 弹窗是否启用 0 null 为未启用 1 启用
     */
    private Integer popupState;

    /**
     * 0 为正常 1位多结局
     */
    private Integer endingPopup;

}
