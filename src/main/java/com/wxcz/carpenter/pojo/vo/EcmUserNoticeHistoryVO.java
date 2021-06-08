package com.wxcz.carpenter.pojo.vo;

import com.wxcz.carpenter.pojo.entity.EcmUser;
import com.wxcz.carpenter.pojo.entity.EcmUserNoticeHistory;
import lombok.Data;

/**
 * @author by cxd
 * @Classname EcmUserNoticeHistoryVO
 * @Description TODO
 * @Date 2021/6/8 14:49
 */
@Data
public class EcmUserNoticeHistoryVO extends EcmUserNoticeHistory {
    private String username;
}
