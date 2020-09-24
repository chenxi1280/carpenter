package com.wxcz.carpenter;

import com.alibaba.fastjson.JSON;
import com.wxcz.carpenter.pojo.entity.EcmInnerMessage;
import com.wxcz.carpenter.util.HttpUtils;
import org.jsoup.Connection;

import java.io.IOException;
import java.util.Date;

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

    public static void main(String[] args) throws IOException {
        EcmInnerMessage ecmInnerMessage = new EcmInnerMessage();
        ecmInnerMessage.setFkUserId(5);
        ecmInnerMessage.setFkTemplateId(1);
        ecmInnerMessage.setPkMessageId(240);
        ecmInnerMessage.setMessageStatus((short) 0);
        ecmInnerMessage.setContent("恭喜用户注册成功加入我们万象成帧的大家庭");
        ecmInnerMessage.setSendDate(new Date());
        Connection.Response post = HttpUtils.post("http://localhost:8008/pushMsg", JSON.toJSONString(ecmInnerMessage));
        System.out.println("发送成功");
    }

}
