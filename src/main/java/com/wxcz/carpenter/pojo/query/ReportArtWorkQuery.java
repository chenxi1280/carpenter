package com.wxcz.carpenter.pojo.query;

import lombok.Data;

/**
 * @author by cxd
 * @Classname ReportArtWorkQuery
 * @Description TODO
 * @Date 2020/8/18 17:40
 */
@Data
public class ReportArtWorkQuery extends PageQuery {
    /**
     * 投诉id
     */
    private Integer reportId;
    private Integer  fkArtworkId;;
    private Integer  fkUserid;
    /**
     * 节点id
     */
    private Integer fkArtworkNodeId;

    /**
     * 投诉 状态 1.未处理 2。已处理
     */
    private Short reState;

    /**
     * 投诉类型：1侵权，2违规，3其他
     */
    private Short reportStatue;

}
