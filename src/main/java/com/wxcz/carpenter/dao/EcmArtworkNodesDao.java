package com.wxcz.carpenter.dao;

import com.wxcz.carpenter.pojo.entity.EcmArtworkNodes;
import com.wxcz.carpenter.pojo.vo.EcmArtworkNodesVo;

import java.util.List;

/**
 * @author cxd
 * @Date: 2020/8/14
 */
public interface EcmArtworkNodesDao {
    int deleteByPrimaryKey(Integer pkDetailId);

    int insert(EcmArtworkNodes record);

    int insertSelective(EcmArtworkNodes record);

    EcmArtworkNodes selectByPrimaryKey(Integer pkDetailId);

    int updateByPrimaryKeySelective(EcmArtworkNodes record);

    int updateByPrimaryKey(EcmArtworkNodes record);

    /**
     * @param: [pkArtworkId] 查询作品的id
     * @return: java.util.List<com.wxcz.carpenter.pojo.vo.EcmArtworkNodesVo> 作品详情 集合
     * @author: cxd
     * @Date: 2020/8/14
     * 描述 :  作品的id 查询作品详情 集合
     */
    List<EcmArtworkNodesVo> selectByArtWorkId(Integer pkArtworkId);

    /**
     * @param: [fkArtworkId]
     * @return: java.lang.Integer
     * @author: cxd
     * @Date: 2020/8/25
     * 描述 : 根据作品id 更新 投诉 节点状态 为被投诉状态
     */
    Integer updateByReportHistroy(Integer fkArtworkId);

    /**
     * @param: [pkArtworkId]
     * @return: java.lang.Integer
     * @author: cxd
     * @Date: 2020/8/25
     * 描述 : 根据 作品id ，修改节点状态 为审核通过状态
     */
    Integer updateStateFailByArtWorkId(Integer pkArtworkId);

    /**
     * @param: [fkArtworkNodeId]
     * @return: java.lang.Integer
     * @author: cxd
     * @Date: 2020/8/25
     * 描述 : 根据作品id 更新 投诉 节点状态 为被投诉状态
     */
    Integer updateByReportHistroyNode(Integer fkArtworkNodeId);
}