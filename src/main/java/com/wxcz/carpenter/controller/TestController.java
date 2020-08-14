package com.wxcz.carpenter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author by cxd
 * @Classname TestController
 * @Description TODO
 * @Date 2020/8/14 14:45
 */
@Controller
public class TestController {
    @RequestMapping("/test")
    public String login() {
        return "login";
    }
}
