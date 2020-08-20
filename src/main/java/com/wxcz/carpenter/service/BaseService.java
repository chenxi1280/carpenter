package com.wxcz.carpenter.service;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author by cxd
 * @Classname BaseService
 * @Description TODO
 * @Date 2020/8/20 15:50
 */
public interface BaseService {

     default HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    /**
     * @param: []
     * @return: javax.servlet.http.HttpSession
     * @author: cxd
     * @Date: 2020/8/7
     * 描述 : 获取HttpSession
     * @param userId
     */
    /**
     * @param: []
     * @return: javax.servlet.http.HttpSession
     * @author: cxd
     * @Date: 2020/8/7
     * 描述 : 获取HttpSession
     */
    default HttpSession getRequstSession() {// 获取shiro自己的session
        return getRequest().getSession();
    }
}
