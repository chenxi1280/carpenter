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
}