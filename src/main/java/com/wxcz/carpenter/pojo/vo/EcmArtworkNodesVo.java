package com.wxcz.carpenter.pojo.vo;

import com.wxcz.carpenter.pojo.entity.EcmArtworkNodes;
import lombok.Data;

import java.util.List;

/**
 * @author by cxd
 * @Classname EcmArtworkNodesVo
 * @Description TODO
 * @Date 2020/8/10 10:43
 */
@Data
public class EcmArtworkNodesVo extends EcmArtworkNodes {

    /**
     * 储存 自己的 子节点集合
     */
    private List<EcmArtworkNodesVo> nodesVos;

    /**
     * 投诉类型：1侵权，2违规，3其他
     */
    private Short reportStatue;

    /**
     * 投诉内容
     */
    private String content;

    /**
     * 是否为投诉节点
     */
    private int  isReport;
    /**
     * 截图地址
     */
    private String reportImgUrl;





}
