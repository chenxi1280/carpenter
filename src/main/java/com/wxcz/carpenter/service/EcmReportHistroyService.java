package com.wxcz.carpenter.service;

import com.wxcz.carpenter.pojo.dto.PageDTO;
import com.wxcz.carpenter.pojo.dto.ResponseDTO;
import com.wxcz.carpenter.pojo.entity.EcmArtwork;
import com.wxcz.carpenter.pojo.entity.EcmReportHistroy;
import com.wxcz.carpenter.pojo.query.EcmArtworkQuery;
import com.wxcz.carpenter.pojo.query.ReportArtWorkQuery;
import com.wxcz.carpenter.pojo.vo.EcmReportHistroyVO;

/**
 * @author by cxd
 * @Classname ReportArtWorkService
 * @Description TODO
 * @Date 2020/8/18 16:54
 */
public interface EcmReportHistroyService {
    PageDTO ajaxList(ReportArtWorkQuery reportArtWorkQuery);


    ResponseDTO artWorkAudit(EcmReportHistroy ecmReportHistroy);

    ResponseDTO getArtWorkNoteS(EcmArtworkQuery ecmArtworkVO);

    EcmReportHistroyVO getReportIdByArtWorkId(Integer pkArtworkId);

    EcmReportHistroyVO getReportHistoryVOByEcmReportHistroy(EcmReportHistroy ecmReportHistroy);
}
