package com.wxcz.carpenter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author by cxd
 * @Classname TestContoller
 * @Description TODO
 * @Date 2020/8/14 16:56
 */
@Controller
public class TestContoller {
    @RequestMapping("test")
    String test(){

        return "login";
    }
}
