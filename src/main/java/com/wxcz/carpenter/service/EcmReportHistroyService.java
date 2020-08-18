package com.wxcz.carpenter.service;

import com.wxcz.carpenter.pojo.dto.PageDTO;
import com.wxcz.carpenter.pojo.query.ReportArtWorkQuery;

/**
 * @author by cxd
 * @Classname ReportArtWorkService
 * @Description TODO
 * @Date 2020/8/18 16:54
 */
public interface EcmReportHistroyService {
    PageDTO ajaxList(ReportArtWorkQuery reportArtWorkQuery);
}
