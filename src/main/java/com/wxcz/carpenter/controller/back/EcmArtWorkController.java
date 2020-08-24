package com.wxcz.carpenter.controller.back;

import com.wxcz.carpenter.controller.BaseController;
import com.wxcz.carpenter.pojo.dto.PageDTO;
import com.wxcz.carpenter.pojo.dto.ResponseDTO;
import com.wxcz.carpenter.pojo.entity.EcmArtwork;
import com.wxcz.carpenter.pojo.entity.EcmArtworkNodes;
import com.wxcz.carpenter.pojo.entity.EcmReportHistroy;
import com.wxcz.carpenter.pojo.query.EcmArtworkQuery;
import com.wxcz.carpenter.pojo.vo.EcmArtworkVO;
import com.wxcz.carpenter.pojo.vo.EcmReportHistroyVO;
import com.wxcz.carpenter.service.EcmArtworkService;
import com.wxcz.carpenter.service.EcmReportHistroyService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * @author by cxd
 * @Classname EcmArtWorkController
 * @Description TODO
 * @Date 2020/8/7 10:52
 */
@Controller
@RequestMapping("/back/artWork")
public class EcmArtWorkController extends BaseController {
    @Resource
    EcmArtworkService ecmArtworkService;

    @Resource
    EcmReportHistroyService ecmReportHistroyService;

    /**
     * @param: []
     * @return: java.lang.String
     * @author: cxd
     * @Date: 2020/8/7
     * 描述 : 跳转页面
     */

    @RequestMapping("artWorkPage")
    public String artWorkPage() {
        return "back/artWork/artWork-list";
    }

    /**
     * @param: []
     * @return: java.lang.String
     * @author: cxd
     * @Date: 2020/8/12
     * 描述 : 跳转页面
     */

    @RequestMapping("checkArtWorkPage")
    public String checkArtWorkPage() {
        return "back/artWork/checkArtWork-list";
    }

    /**
     * @param: [pkArtworkId, model] pkArtworkId 当前作品id
     * @return: java.lang.String
     * @author: cxd
     * @Date: 2020/8/12
     * 描述 : 跳转页面
     * 通过当前作品id和session中的 userid 来判断 用户 是否 有该作品的权限
     */

    @RequestMapping("artWorkNodePage")
    public String artWorkNodePage(Integer pkArtworkId ,Model model) {
        // 同时把作品id 传回前端
        model.addAttribute("pkArtworkId", pkArtworkId);
        EcmReportHistroyVO ecmReportHistroyVO = ecmReportHistroyService.getReportIdByArtWorkId(pkArtworkId);

        if (ecmReportHistroyVO != null) {
            model.addAttribute("reportId", ecmReportHistroyVO.getReportId());
        }
        //通过session 拿到当前用户的id
        EcmArtwork ecmArtwork = new EcmArtwork();
        //         暂定 节点审核人 id
        ecmArtwork.setFkAuditId((Integer) getRequstSession().getAttribute("userId"));
        ecmArtwork.setPkArtworkId(pkArtworkId);
        //查询当前作品的审核人是否为 当前用户
        ResponseDTO responseDTO = ecmArtworkService.artWorkAudit(ecmArtwork);
        //是返回正常页面
        if (responseDTO.getStatus() == 200) {
            return "back/artWork/artWorkNode";
        }
        // 不是返回 错误页面
        return "error/error-403";
    }

    /**
     * @param: [ecmArtworkQuery] 查询条件
     * @return: com.wxcz.carpenter.pojo.dto.PageDTO
     * @author: cxd
     * @Date: 2020/8/7
     * 描述 :
     * 按条件查询 作品
     * 保存成功: status 0  msg "success” data数据
     * 保存失败: status 500  msg "error“
     */
    @RequestMapping("ajaxList")
    @ResponseBody
    public PageDTO ajaxList(EcmArtworkQuery ecmArtworkQuery) {
        return ecmArtworkService.ajaxList(ecmArtworkQuery);
    }

    /**
     * @param: [ecmArtworkVO]  需要修改的作品
     * @return: com.wxcz.carpenter.pojo.dto.ResponseDTO
     * @author: cxd
     * @Date: 2020/8/7
     * 描述 :
     * 验证权限并 对作品 进行修改
     * 保存成功: status 200  msg "success”
     * 保存失败: status 500  msg "error“
     */

    @RequestMapping("chengArtWork")
    @ResponseBody
    public ResponseDTO chengArtWork(EcmArtworkVO ecmArtworkVO) {

        Subject subject = SecurityUtils.getSubject();
        if (!subject.hasRole("superadmin")){
            return ResponseDTO.fail("无权限");
        }

        return ecmArtworkService.chengArtWork(ecmArtworkVO);
    }

    /**
     * @param: [ecmArtworkQuery] 待审核 作品的 查询条件
     * @return: com.wxcz.carpenter.pojo.dto.PageDTO
     * @author: cxd
     * @Date: 2020/8/12
     * 描述 :  查询 待审核视频的集合
     * 保存成功: status 0    msg "success”  data 数据
     * 保存失败: status 500  msg "error“    data 无
     */
    @RequestMapping("ajaxCheckList")
    @ResponseBody
    public PageDTO ajaxCheckList(EcmArtworkQuery ecmArtworkQuery) {
        return ecmArtworkService.ajaxCheckList(ecmArtworkQuery);
    }

    /**
     * @param: [ecmArtworkVO] 作品id
     * @return: com.wxcz.carpenter.pojo.dto.ResponseDTO
     * @author: cxd
     * @Date: 2020/8/12
     * 描述 : 根据作品id 查询 所以的 作品节点
     * 保存成功: status 200  msg "success” data 作品节点集合
     * 保存失败: status 500  msg "error“  data 无
     */
    @RequestMapping("getArtWorkNoteS")
    @ResponseBody
    public ResponseDTO getArtWorkNoteS(EcmArtworkQuery ecmArtworkVO) {
        return ecmArtworkService.getArtWorkNoteS(ecmArtworkVO);
    }

    /**
     * @param: [ecmArtworkNodes] 更新后的及节点，（必须包含节点id）
     * @return: com.wxcz.carpenter.pojo.dto.ResponseDTO
     * @author: cxd
     * @Date: 2020/8/12
     * 描述 : 通过传回的 节点进行更新操作
     * 保存成功: status 200  msg "success”
     * 保存失败: status 500  msg "error“
     */
    @RequiresRoles("admin")
    @RequestMapping("upDataNode")
    @ResponseBody
    public ResponseDTO upDataNode(EcmArtworkNodes ecmArtworkNodes) {
        return ecmArtworkService.upDataNode(ecmArtworkNodes);
    }

    /**
     * @param: [ecmArtworkQuery] 需要审核检查作品的id
     * @return: com.wxcz.carpenter.pojo.dto.ResponseDTO
     * @author: cxd
     * @Date: 2020/8/12
     * 描述 : 返回作品的 状态
     * 保存成功: status 200  msg “作品未通过审核” “作通过审核”
     * 保存失败: status 500  msg “作品有节点未审核”
     */
    @RequiresRoles("admin")
    @RequestMapping("checkArtWork")
    @ResponseBody
    public ResponseDTO checkArtWork(EcmArtworkQuery ecmArtworkQuery) {

        return ecmArtworkService.checkArtWork(ecmArtworkQuery);
    }

    @RequiresRoles("admin")
    @RequestMapping("reCheckArtWork")
    @ResponseBody
    public ResponseDTO reCheckArtWork(EcmArtworkQuery ecmArtworkQuery) {

        return ecmArtworkService.reCheckArtWork(ecmArtworkQuery);
    }

}
