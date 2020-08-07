package com.wxcz.carpenter.controller.back;

import com.wxcz.carpenter.pojo.dto.PageDTO;
import com.wxcz.carpenter.pojo.dto.ResponseDTO;
import com.wxcz.carpenter.pojo.entity.EcmArtwork;
import com.wxcz.carpenter.pojo.query.EcmArtworkQuery;
import com.wxcz.carpenter.pojo.query.EcmUserQuery;
import com.wxcz.carpenter.pojo.vo.EcmArtworkVO;
import com.wxcz.carpenter.pojo.vo.EcmUserVO;
import com.wxcz.carpenter.service.EcmArtworkService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @author by cxd
 * @Classname EcmArtWorkController
 * @Description TODO
 * @Date 2020/8/7 10:52
 */
@Component
@RequestMapping("/back/artWork")
public class EcmArtWorkController {
    @Resource
    EcmArtworkService ecmArtworkService;

    /**
     * @param: []
     * @return: java.lang.String
     * @author: cxd
     * @Date: 2020/8/7
     * 描述 : 跳转页面
     */
    @RequestMapping("artWorkPage")
    public String artWorkPage(){
        return "back/artWork/artWork-list";
    }

    /**
     * @param: [ecmArtworkQuery] 查询条件
     * @return: com.wxcz.carpenter.pojo.dto.PageDTO
     * @author: cxd
     * @Date: 2020/8/7
     * 描述 :
     *        按条件查询 作品
     *       保存成功: status 0  msg "success” data数据
     *       保存失败: status 500  msg "error“
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
     *        验证权限并 对作品 进行修改
     *       保存成功: status 200  msg "success”
     *       保存失败: status 500  msg "error“
     */
    @RequiresRoles("admin")
    @RequestMapping("chengArtWork")
    @ResponseBody
    public ResponseDTO chengArtWork(EcmArtworkVO ecmArtworkVO) {
        return ecmArtworkService.chengArtWork(ecmArtworkVO);
    }
}
