package com.wxcz.carpenter.config.exception;

import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author by cxd
 * @Classname LoginException
 * @Description TODO
 * @Date 2020/8/21 15:57
 */
@ControllerAdvice
public class AuthException {

//    @ExceptionHandler(value = AuthorizationException.class)
    public String exceptionHandler(AuthorizationException e){
        System.out.println("未知异常！原因是:"+e);

        return "error/error-403";
    }

}
