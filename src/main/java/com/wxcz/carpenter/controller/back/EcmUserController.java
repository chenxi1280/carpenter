package com.wxcz.carpenter.controller.back;

import com.wxcz.carpenter.controller.BaseController;
import com.wxcz.carpenter.pojo.dto.PageDTO;
import com.wxcz.carpenter.pojo.dto.ResponseDTO;
import com.wxcz.carpenter.pojo.entity.EcmDownlinkFlow;
import com.wxcz.carpenter.pojo.query.EcmUserQuery;
import com.wxcz.carpenter.pojo.vo.EcmDownlinkFlowVO;
import com.wxcz.carpenter.pojo.vo.EcmUserVO;
import com.wxcz.carpenter.service.EcmUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;


/**
 * @author by cxd
 * @Classname EcmUserController
 * @Description TODO
 * @Date 2020/8/6 14:14
 */
@Controller
@RequestMapping("/back/user")
public class EcmUserController extends BaseController {

    @Resource
    EcmUserService ecmUserService;

    /**
     * @param: []
     * @return: java.lang.String
     * @author: cxd
     * @Date: 2020/8/7
     * 描述 : 页面跳转
     */
    @RequestMapping("/userPage")
    public String userPage() {
        return "back/user/user-list";
    }

    /**
     * @param: []
     * @return: java.lang.String
     * @author: cxd
     * @Date: 2020/8/25
     * 描述 : 页面跳转 用户 数据 表
     */
    @RequestMapping("/userDataPage")
    public String userDataPage() {
        return "back/user/user-data-list";
    }

    @RequestMapping("/userLogoListPage")
    public String userLogoListPage() {
        return "back/user/user-logo-list";
    }

    /**
     * @param: []
     * @return: java.lang.String
     * @author: cxd
     * @Date: 2020/8/7
     * 描述 : 页面跳转
     */
    @RequestMapping("/setPassWordPage")
    public String setPassWordPage() {
        return "back/user/password";
    }

    /**
     * @param: [ecmUserQuery]  查询条件类
     * @return: com.wxcz.carpenter.pojo.dto.PageDTO
     * @author: cxd
     * @Date: 2020/8/7
     * 描述 : 按条件查询用户
     * 保存成功: status 0  msg "success” 数据 data
     * 保存失败: status 500  msg "error“
     */
    @RequestMapping("ajaxList")
    @ResponseBody
    public PageDTO ajaxList(EcmUserQuery ecmUserQuery) {
        return ecmUserService.ajaxList(ecmUserQuery);
    }

    /**
     * @param: [ecmUserVO]
     * @return: com.wxcz.carpenter.pojo.dto.ResponseDTO
     * @author: cxd
     * @Date: 2020/8/7
     * 描述 : 对用户状态进行修改
     * 保存成功: status 200  msg "success”
     * 保存失败: status 500  msg "error“
     */
    @RequestMapping("chengUser")
    @ResponseBody
    public ResponseDTO chengUser(EcmUserVO ecmUserVO) {
        return ecmUserService.chengUser(ecmUserVO);
    }

    /**
     * @param: [ecmUserVO]
     * @return: com.wxcz.carpenter.pojo.dto.ResponseDTO
     * @author: cxd
     * @Date: 2020/8/7
     * 描述 :
     * 用户自己在个人中心设置密码接口
     * 保存成功: status 200  msg "success”
     * 保存失败: status 500  msg "error“
     */
    @RequestMapping("setPassWord")
    @ResponseBody
    public ResponseDTO setPassWord(EcmUserVO ecmUserVO) {
        //通过session 拿到当前用户的id
        ecmUserVO.setPkUserId((Integer) getRequstSession().getAttribute("userId"));
        return ecmUserService.setPassWord(ecmUserVO);
    }

    @RequestMapping("updataUserLogoStatus")
    @ResponseBody
    public ResponseDTO updataUserLogoStatus(EcmUserVO ecmUserVO) {
        return ecmUserService.updataUserLogoStatus(ecmUserVO);
    }

    @RequestMapping("addDownFlowUser")
    @ResponseBody
    public ResponseDTO addDownFlowUser(EcmDownlinkFlowVO ecmDownlinkFlowVO) {
        return ecmUserService.addDownFlowUser(ecmDownlinkFlowVO);
    }

    @RequestMapping("addUserDownFlow")
    @ResponseBody
    public ResponseDTO addUserDownFlow(EcmDownlinkFlowVO ecmDownlinkFlowVO) {
        return ecmUserService.addUserDownFlow(ecmDownlinkFlowVO);
    }
}
