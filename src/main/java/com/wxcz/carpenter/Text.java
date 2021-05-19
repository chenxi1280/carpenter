package com.wxcz.carpenter;

import com.alibaba.fastjson.JSON;
import com.wxcz.carpenter.pojo.entity.EcmInnerMessage;
import com.wxcz.carpenter.pojo.vo.SendNoticeVO;
import com.wxcz.carpenter.util.HttpUtils;
import org.jsoup.Connection;

import java.io.IOException;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * @author by cxd
 * @Classname Text
 * @Description TODO
 * @Date 2020/8/14 13:14
 */
public class Text {
    /**
     * @param: [args]
     * @return: void
     * @author: cxd
     * @Date: 2020/8/14
     * 描述 :
     * 测试自动化部署 第3次
     *
     */
    public static void main(String[] args) {

        SendNoticeVO sendNoticeVO = new SendNoticeVO();
        sendNoticeVO.setTemplateId("960224");
        sendNoticeVO.setPhoneNumber("17754923091");
        try {
            HttpUtils.post(HttpUtils.SEND_NOTICE_URL_TEST, JSON.toJSONString(sendNoticeVO));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
