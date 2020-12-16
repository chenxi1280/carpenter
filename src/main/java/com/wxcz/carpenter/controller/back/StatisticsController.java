package com.wxcz.carpenter.controller.back;

import com.wxcz.carpenter.pojo.dto.ResponseDTO;
import com.wxcz.carpenter.pojo.query.UsersRetentionQuery;
import com.wxcz.carpenter.service.StatisticsService;
import com.wxcz.carpenter.util.DateUtil;
import org.apache.poi.util.IOUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

import static com.wxcz.carpenter.common.CommomConfig.LINUX_CONFIG_UPLOAD_PATH;
import static com.wxcz.carpenter.common.CommomConfig.WINDOW_CONFIG_UPLOAD_PATH;

/**
 * @author by cxd
 * @Classname StatisticsController
 * @Description TODO
 * @Date 2020/11/19 10:21
 */
@Controller
@RequestMapping("/back/statistics")
public class StatisticsController {

    @Autowired
    StatisticsService statisticsService;

    @RequestMapping("/getDailyUsers")
    @ResponseBody
    public ResponseDTO getDailyUsers(  UsersRetentionQuery usersRetentionQuery) {
//        Subject subject = SecurityUtils.getSubject();
//        if (!subject.hasRole("superadmin")){
//            return ResponseDTO.fail("无权限");
//        }

        return statisticsService.getDailyUsers( usersRetentionQuery);
    }

    @RequestMapping("/getAddDailyUsers")
    @ResponseBody
    public ResponseDTO getAddDailyUsers(  UsersRetentionQuery usersRetentionQuery) {
//        Subject subject = SecurityUtils.getSubject();
//        if (!subject.hasRole("superadmin")){
//            return ResponseDTO.fail("无权限");
//        }

        return statisticsService.getAddDailyUsers( usersRetentionQuery);
    }
    @RequestMapping("/getUsersRetention")
    @ResponseBody
    public ResponseDTO getUsersRetention( UsersRetentionQuery usersRetentionQuery) {
//        Subject subject = SecurityUtils.getSubject();
//        if (!subject.hasRole("superadmin")){
//            return ResponseDTO.fail("无权限");
//        }
//        usersRetentionQuery.setQueryDateTime(DateUtil.getStringDateShort(usersRetentionQuery.getQueryTime()));
        return statisticsService.getUsersRetention(usersRetentionQuery);
    }
//    观看总数/有效用户 人均观看作品数
    @RequestMapping("/getViewedPerCapita")
    @ResponseBody
    public ResponseDTO getViewedPerCapita( UsersRetentionQuery usersRetentionQuery) {
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


    @RequestMapping("/getStatisticsUserExcl")
    @ResponseBody
    public ResponseDTO getStatisticsUserExcl(UsersRetentionQuery usersRetentionQuery) {
//        Subject subject = SecurityUtils.getSubject();
//        if (!subject.hasRole("superadmin")){
//            return ResponseDTO.fail("无权限");
//        }
        return statisticsService.getStatisticsUserExcl(usersRetentionQuery);

    }
    @RequestMapping(value = "/downloadFile")
    @ResponseBody
    public void downloadFile(UsersRetentionQuery usersRetentionQuery, HttpServletRequest request, HttpServletResponse response)  {
        String queryDateTime = usersRetentionQuery.getQueryDateTime();
        if(StringUtils.isEmpty(queryDateTime)){
            return;
        }
        String systemType = System.getProperty("os.name");// 获取系统的类别, Window
        String realPath = systemType.toLowerCase().contains("windows") ? WINDOW_CONFIG_UPLOAD_PATH: LINUX_CONFIG_UPLOAD_PATH;


        String fullPath = realPath +"/"+ queryDateTime.replace("/", "-") +".xls" ;
        try {
            InputStream myStream = new FileInputStream(fullPath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            getStatisticsUserExcl(usersRetentionQuery);
        }


        File downloadFile = new File(fullPath);

        ServletContext context = request.getServletContext();

        // get MIME type of the file
        String mimeType = context.getMimeType(fullPath);
        if (mimeType == null) {
            // set to binary type if MIME mapping not found
            mimeType = "application/octet-stream";
            System.out.println("context getMimeType is null");
        }
        System.out.println("MIME type: " + mimeType);

        // set content attributes for the response
        response.setContentType(mimeType);
        response.setContentLength((int) downloadFile.length());

        // set headers for the response
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"",
                downloadFile.getName());
        response.setHeader(headerKey, headerValue);

        // Copy the stream to the response's output stream.
        try {
            InputStream myStream = new FileInputStream(fullPath);
            IOUtils.copy(myStream, response.getOutputStream());
            response.flushBuffer();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
