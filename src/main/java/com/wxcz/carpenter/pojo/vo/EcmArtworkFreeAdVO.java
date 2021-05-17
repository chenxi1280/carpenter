package com.wxcz.carpenter.pojo.vo;

import com.wxcz.carpenter.pojo.entity.EcmArtworkFreeAd;
import lombok.Data;

import java.util.List;

/**
 * @author by cxd
 * @Classname EcmArtworkFreeAdVO
 * @Description TODO
 * @Date 2021/5/17 17:56
 */
@Data
public class EcmArtworkFreeAdVO extends EcmArtworkFreeAd {
    private List<Integer> fkArtworkIdList;
    private List<Integer> unFkArtworkIdList;
}
