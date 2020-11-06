package com.wxcz.carpenter.service;

import com.wxcz.carpenter.pojo.dto.PageDTO;
import com.wxcz.carpenter.pojo.dto.ResponseDTO;
import com.wxcz.carpenter.pojo.entity.EcmArtwork;
import com.wxcz.carpenter.pojo.entity.EcmReportHistroy;
import com.wxcz.carpenter.pojo.query.EcmArtworkQuery;
import com.wxcz.carpenter.pojo.query.ReportArtWorkQuery;
import com.wxcz.carpenter.pojo.vo.EcmReportHistroyVO;

import java.util.List;

/**
 * @author by cxd
 * @Classname ReportArtWorkService
 * @Description TODO
 * @Date 2020/8/18 16:54
 */
public interface EcmReportHistroyService {

    /**
     * @param: [reportArtWorkQuery] 查询条件 自带分页
     * @return: com.wxcz.carpenter.pojo.dto.PageDTO
     * @author: cxd
     * @Date: 2020/8/25
     * 描述 : layui 获取 投书作品列表 集合
     */
    PageDTO ajaxList(ReportArtWorkQuery reportArtWorkQuery);

    /**
     * @param: [ecmReportHistroy]  查询的  投诉作品id
     * @return: com.wxcz.carpenter.pojo.dto.ResponseDTO
     * @author: cxd
     * @Date: 2020/8/25
     * 描述 : 查询当前作品的审核人是否为 当前用户
     *         是 返回正常 200
     *         不是 返回 500 msg = ”无权限“
     */
    ResponseDTO artWorkAudit(EcmReportHistroy ecmReportHistroy);

    /**
     * @param: [ecmArtworkVO]  作品id
     * @return: com.wxcz.carpenter.pojo.dto.ResponseDTO
     * @author: cxd
     * @Date: 2020/8/25
     * 描述 : 获取作品 下 的 作品节点 信息
     *       保存成功: status 200  msg "success”
     *       保存失败: status 500  msg "error“
     */
    ResponseDTO getArtWorkNoteS(EcmArtworkQuery ecmArtworkVO);

    /**
     * @param: [pkArtworkId] 作品id
     * @return: com.wxcz.carpenter.pojo.vo.EcmReportHistroyVO
     * @author: cxd
     * @Date: 2020/8/25
     * 描述 : 根据作品id 获取 投诉 详情
     */
    List<EcmReportHistroyVO> getReportIdByArtWorkId(Integer pkArtworkId);

    /**
     * @param: [ecmReportHistroy]
     * @return: com.wxcz.carpenter.pojo.vo.EcmReportHistroyVO
     * @author: cxd
     * @Date: 2020/8/25
     * 描述 : 根据 ReportHistoryVO 获取  EcmReportHistroy
     */
    EcmReportHistroyVO getReportHistoryVOByEcmReportHistroy(EcmReportHistroy ecmReportHistroy);
}
