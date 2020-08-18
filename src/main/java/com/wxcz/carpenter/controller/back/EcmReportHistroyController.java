package com.wxcz.carpenter.controller.back;

import com.wxcz.carpenter.pojo.dto.PageDTO;
import com.wxcz.carpenter.pojo.query.ReportArtWorkQuery;
import com.wxcz.carpenter.service.EcmReportHistroyService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @author by cxd
 * @Classname ReportArtWorkController
 * @Description TODO
 * @Date 2020/8/18 16:54
 */
@Controller
@RequestMapping("back/reportArtWork")
public class EcmReportHistroyController {

    @Resource
    EcmReportHistroyService ecmReportHistroyService;

    @RequestMapping("reportPage")
    public String reportPage(){
        return "back/artWork/report-list";
    }


    @RequestMapping("ajaxList")
    @ResponseBody
    public PageDTO ajaxList(ReportArtWorkQuery reportArtWorkQuery){
        return ecmReportHistroyService.ajaxList(reportArtWorkQuery);
    }
}
