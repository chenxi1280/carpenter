package com.wxcz.carpenter.pojo.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * ecm_user_notice_record
 * @author
 */
@Data
public class EcmUserNoticeRecord implements Serializable {
    /**
     * 主键
     */
    private Integer pkId;

    /**
     * 用户id
     */
    private Integer fkUserId;

    /**
     * 1未通知 0已通知
     */
    private Integer noticeStatus;

    /**
     * 创建时间
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;
}
