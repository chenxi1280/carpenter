package com.wxcz.carpenter.pojo.query;

import lombok.Data;

/**
 * @author by cxd
 * @Classname EcmArtworkUnAdQuery
 * @Description TODO
 * @Date 2021/5/17 16:49
 */
@Data
public class EcmArtworkFreeAdQuery extends PageQuery{
    /**
     * 作品表
     */
    private Integer pkArtworkId;
    private Integer fkArtworkId;

    /**
     * 作者
     */
    private Integer fkUserid;

    private String artworkName;

    private String artworkStatus;

}
