package com.wxcz.carpenter.dao;

import com.wxcz.carpenter.pojo.entity.EcmReportHistroy;
import com.wxcz.carpenter.pojo.query.ReportArtWorkQuery;
import com.wxcz.carpenter.pojo.vo.EcmReportHistroyVO;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * @author cxd
 * @Date: 2020/8/25
 */
@Repository
public interface EcmReportHistroyDao {
    int deleteByPrimaryKey(Integer reportId);

    int insert(EcmReportHistroy record);

    int insertSelective(EcmReportHistroy record);

    EcmReportHistroy selectByPrimaryKey(Integer reportId);

    int updateByPrimaryKeySelective(EcmReportHistroy record);

    int updateByPrimaryKey(EcmReportHistroy record);


    /**
     * @param: [reportArtWorkQuery] 查询条件 自带分页
     * @return: java.util.List<com.wxcz.carpenter.pojo.vo.EcmReportHistroyVO>
     * @author: cxd
     * @Date: 2020/8/25
     * 描述 :  查询 投诉信息集合
     */
    List<EcmReportHistroyVO> selectAjaxList(ReportArtWorkQuery reportArtWorkQuery);

    /**
     * @param: [reportArtWorkQuery] 查询条件
     * @return: java.lang.Integer
     * @author: cxd
     * @Date: 2020/8/25
     * 描述 : 查询符合条件的 行数
     */
    Integer selectAjaxCount(ReportArtWorkQuery reportArtWorkQuery);

    /**
     * @param: [reportId] 投诉id
     * @return: java.lang.Integer
     * @author: cxd
     * @Date: 2020/8/25
     * 描述 :  根据投诉id 更新 投诉 状态和 处理人
     */
    Integer updateStateSuccessByPrimaryKey(Integer reportId);

    /**
     * @param: [pkArtworkId]
     * @return: com.wxcz.carpenter.pojo.vo.EcmReportHistroyVO
     * @author: cxd
     * @Date: 2020/8/25
     * 描述 : 根据 作品id 查询投诉信息
     */
    List<EcmReportHistroyVO> selectByArtWorkId(Integer pkArtworkId);

    Integer updateStateSuccessByArtWorkId(Integer pkArtworkId);

    Integer updateReportHistroySByAtrworkId(Integer fkArtworkId);
}
