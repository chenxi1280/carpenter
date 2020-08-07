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

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/ajaxlogin")
    @ResponseBody
    public ResponseDTO ajaxlogin(EcmUser user){
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername() , user.getPassword());
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
        }catch (AuthenticationException e){
            return ResponseDTO.fail(e.getMessage());
        }

        return ResponseDTO.ok("登录成功");
    }


}
