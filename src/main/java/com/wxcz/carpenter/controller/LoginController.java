package com.wxcz.carpenter.controller;

import com.wxcz.carpenter.pojo.dto.ResponseDTO;
import com.wxcz.carpenter.pojo.entity.EcmUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author by cxd
 * @Classname LoginController
 * @Description TODO
 * @Date 2020/8/6 10:03
 */
@Controller
public class LoginController {

    /**
     * @param: []
     * @return: java.lang.String
     * @author: cxd
     * @Date: 2020/8/7
     * 描述 : 跳转页面接口
     */
    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * @param: [user] 登录 账号密码接收类
     * @return: com.wxcz.carpenter.pojo.dto.ResponseDTO
     * @author: cxd
     * @Date: 2020/8/7
     * 描述 ： 登录接口
     * 保存成功: status 200  msg "success”
     * 保存失败: status 500  msg "登录失败“
     */

    @RequestMapping("/ajaxlogin")
    @ResponseBody
    public ResponseDTO ajaxlogin(EcmUser user) {
        //存入token对象
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
        Subject subject = SecurityUtils.getSubject();
        try {
            //调用shiro 的认证方法 UserRealm 类中认证方法
            // 捕获异常 返回登陆失败消息
            subject.login(token);
        } catch (AuthenticationException e) {
            return ResponseDTO.fail(e.getMessage());
        }
        return ResponseDTO.ok("登录成功");
    }


    /**
     * @param: []
     * @return: java.lang.String
     * @author: cxd
     * @Date: 2020/8/12
     * 描述 : 退出
     */

    @RequestMapping("/logout")
    String logoutApp() {
        SecurityUtils.getSubject().logout();
        return "/login";
    }


    /**
     * @param: []
     * @return: java.lang.String
     * @author: cxd
     * @Date: 2020/8/12
     * 描述 : 退出页面
     */
    @RequestMapping("logoutPage")
    public String logout() {
        SecurityUtils.getSubject().logout();
        return "login";
    }

}
