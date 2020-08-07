package com.wxcz.carpenter.controller;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author by cxd
 * @Classname BaseController
 * @Description TODO
 * @Date 2020/8/7 10:32
 */
@Component
public class BaseController {


    /**
     * @param: []
     * @return: javax.servlet.http.HttpServletRequest
     * @author: cxd
     * @Date: 2020/8/7
     * 描述 : 获取 HttpServletRequest 对象
     */
    public HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    /**
     * @param: []
     * @return: javax.servlet.http.HttpServletResponse
     * @author: cxd
     * @Date: 2020/8/7
     * 描述 : 获取HttpServletResponse
     */
    public HttpServletResponse getResponse() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
    }

    /**
     * @param: []
     * @return: javax.servlet.http.HttpSession
     * @author: cxd
     * @Date: 2020/8/7
     * 描述 : 获取HttpSession
     */
    public HttpSession getRequstSession() {// 获取shiro自己的session
        return getRequest().getSession();
    }

}
