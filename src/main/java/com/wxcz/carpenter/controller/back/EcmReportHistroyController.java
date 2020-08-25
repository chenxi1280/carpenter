package com.wxcz.carpenter.controller.back;

import com.wxcz.carpenter.controller.BaseController;
import com.wxcz.carpenter.pojo.dto.PageDTO;
import com.wxcz.carpenter.pojo.dto.ResponseDTO;
import com.wxcz.carpenter.pojo.entity.EcmArtwork;
import com.wxcz.carpenter.pojo.entity.EcmReportHistroy;
import com.wxcz.carpenter.pojo.query.EcmArtworkQuery;
import com.wxcz.carpenter.pojo.query.ReportArtWorkQuery;
import com.wxcz.carpenter.service.EcmReportHistroyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
public class EcmReportHistroyController extends BaseController {

    @Resource
    EcmReportHistroyService ecmReportHistroyService;

    /**
     * @param: []
     * @return: java.lang.String
     * @author: cxd
     * @Date: 2020/8/25
     * 描述 : 跳转 投书作品 页面
     */
    @RequestMapping("reportPage")
    public String reportPage(){
        return "back/artWork/report-list";
    }

    /**
     * @param: [pkArtworkId （作品id）, reportId（处理人id）, model]
     * @return: java.lang.String
     * @author: cxd
     * @Date: 2020/8/25
     * 描述 :  投诉作品 节点详情查看页面跳转
     */
    @RequestMapping("artWorkNodePage")
    public String artWorkNodePage(Integer pkArtworkId, Integer reportId , Model model) {
        // 同时把作品id 传回前端
        model.addAttribute("pkArtworkId", pkArtworkId);
        model.addAttribute("reportId", reportId);
        //通过session 拿到当前用户的id
        EcmReportHistroy ecmReportHistroy = new EcmReportHistroy();
        //         暂定 节点审核人 id
        ecmReportHistroy.setFkArtworkId(pkArtworkId);
        ecmReportHistroy.setReportId(reportId);
        ecmReportHistroy.setFkChUserid((Integer) getRequstSession().getAttribute("userId"));
        //查询当前作品的审核人是否为 当前用户
        ResponseDTO responseDTO = ecmReportHistroyService.artWorkAudit(ecmReportHistroy);
        //是返回正常页面
        if (responseDTO.getStatus() == 200) {
            return "back/artWork/artWorkNode";
        }
        // 不是返回 错误页面
        return "error/error-403";
    }


    /**
     * @param: [reportArtWorkQuery] 获取投诉作品额 条件 自带分页
     * @return: com.wxcz.carpenter.pojo.dto.PageDTO
     * @author: cxd
     * @Date: 2020/8/25
     * 描述 : 获取 投诉作品列表 （layui）
     *       保存成功: status 200  msg "success”
     *       保存失败: status 500  msg "error“
     */
    @RequestMapping("ajaxList")
    @ResponseBody
    public PageDTO ajaxList(ReportArtWorkQuery reportArtWorkQuery){
        return ecmReportHistroyService.ajaxList(reportArtWorkQuery);
    }


    /**
     * @param: [ecmArtworkVO] 作品id
     * @return: com.wxcz.carpenter.pojo.dto.ResponseDTO
     * @author: cxd
     * @Date: 2020/8/25
     * 描述 : 根据 投诉作品id 获取 作品节点信息
     *       保存成功: status 200  msg "success”
     *       保存失败: status 500  msg "error“
     */
    @RequestMapping("getArtWorkNoteS")
    @ResponseBody
    public ResponseDTO getArtWorkNoteS(EcmArtworkQuery ecmArtworkVO) {
        return ecmReportHistroyService.getArtWorkNoteS(ecmArtworkVO);
    }
}
