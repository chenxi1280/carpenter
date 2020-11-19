package com.wxcz.carpenter.controller.back;

import com.wxcz.carpenter.pojo.dto.ResponseDTO;
import com.wxcz.carpenter.service.StatisticsService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author by cxd
 * @Classname StatisticsController
 * @Description TODO
 * @Date 2020/11/19 10:21
 */
@Controller
public class StatisticsController {

    @Autowired
    StatisticsService statisticsService;

    @RequestMapping("/getDailyUsers")
    @ResponseBody
    public ResponseDTO getDailyUsers() {
        Subject subject = SecurityUtils.getSubject();
        if (!subject.hasRole("superadmin")){
            return ResponseDTO.fail("无权限");
        }

        return statisticsService.getDailyUsers();
    }

}