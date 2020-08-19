package com.wxcz.carpenter.dao;

import com.wxcz.carpenter.pojo.entity.EcmReportHistroy;
import com.wxcz.carpenter.pojo.query.ReportArtWorkQuery;
import com.wxcz.carpenter.pojo.vo.EcmReportHistroyVO;

import java.util.List;

public interface EcmReportHistroyDao {
    int deleteByPrimaryKey(Integer reportId);

    int insert(EcmReportHistroy record);

    int insertSelective(EcmReportHistroy record);

    EcmReportHistroy selectByPrimaryKey(Integer reportId);

    int updateByPrimaryKeySelective(EcmReportHistroy record);

    int updateByPrimaryKey(EcmReportHistroy record);

    List<EcmReportHistroyVO> selectAjaxList(ReportArtWorkQuery reportArtWorkQuery);

    Integer selectAjaxCount(ReportArtWorkQuery reportArtWorkQuery);

    Integer updateStateSuccessByPrimaryKey(Integer reportId);

    EcmReportHistroyVO selectByArtWorkId(Integer pkArtworkId);
}