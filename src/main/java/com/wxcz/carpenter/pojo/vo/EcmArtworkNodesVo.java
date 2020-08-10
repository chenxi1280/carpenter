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




}
