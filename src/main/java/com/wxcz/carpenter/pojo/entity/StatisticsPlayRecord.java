package com.wxcz.carpenter.pojo.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * statistics_play_record
 * @author
 */
@Data
public class StatisticsPlayRecord implements Serializable {
    /**
     * 有效观看记录统计,以空间换时间
     */
    private Integer pkPlayRecordId;

    /**
     * 作品id
     */
    private Integer fkArtworkId;

    /**
     * 作品【详情】id
     */
    private Integer fkArtworkDetailId;

    /**
     * userId
     */
    private Integer fkUserId;

    /**
     * 作品观看时间
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;
}
