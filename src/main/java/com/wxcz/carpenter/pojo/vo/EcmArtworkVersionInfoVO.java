package com.wxcz.carpenter.pojo.vo;

import com.wxcz.carpenter.pojo.entity.EcmArtworkVersionInfo;
import lombok.Data;

import java.util.List;

/**
 * @author by cxd
 * @Classname EcmArtworkVersionInfoVO
 * @Description TODO
 * @Date 2021/4/6 11:27
 */
@Data
public class EcmArtworkVersionInfoVO extends EcmArtworkVersionInfo {
    private List<Integer> fkArtworkIdList;

}
