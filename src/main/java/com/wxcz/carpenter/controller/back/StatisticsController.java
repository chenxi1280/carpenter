package com.wxcz.carpenter.controller.back;

import com.wxcz.carpenter.pojo.dto.ResponseDTO;
import com.wxcz.carpenter.pojo.query.UsersRetentionQuery;
import com.wxcz.carpenter.service.StatisticsService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
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
    public ResponseDTO getDailyUsers(@RequestBody UsersRetentionQuery usersRetentionQuery) {
//        Subject subject = SecurityUtils.getSubject();
//        if (!subject.hasRole("superadmin")){
//            return ResponseDTO.fail("无权限");
//        }

        return statisticsService.getDailyUsers( usersRetentionQuery);
    }

    @RequestMapping("/getUsersRetention")
    @ResponseBody
    public ResponseDTO getUsersRetention(@RequestBody UsersRetentionQuery usersRetentionQuery) {
//        Subject subject = SecurityUtils.getSubject();
//        if (!subject.hasRole("superadmin")){
//            return ResponseDTO.fail("无权限");
//        }
        return statisticsService.getUsersRetention(usersRetentionQuery);
    }
//    观看总数/有效用户 人均观看作品数
    @RequestMapping("/getViewedPerCapita")
    @ResponseBody
    public ResponseDTO getViewedPerCapita(@RequestBody UsersRetentionQuery usersRetentionQuery) {
//        Subject subject = SecurityUtils.getSubject();
//        if (!subject.hasRole("superadmin")){
//            return ResponseDTO.fail("无权限");
//        }
        return statisticsService.getViewedPerCapita(usersRetentionQuery);

    }


    //平均完播率
    @RequestMapping("/getAverageCompletionRate")
    @ResponseBody
    public ResponseDTO getAverageCompletionRate(@RequestBody UsersRetentionQuery usersRetentionQuery) {
//        Subject subject = SecurityUtils.getSubject();
//        if (!subject.hasRole("superadmin")){
//            return ResponseDTO.fail("无权限");
//        }
        return statisticsService.getAverageCompletionRate(usersRetentionQuery);

    }

}
