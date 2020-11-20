package com.wxcz.carpenter.pojo.query;

import lombok.Data;

import java.util.Date;

/**
 * @author by cxd
 * @Classname UsersRetentionQuery
 * @Description TODO
 * @Date 2020/11/20 13:23
 */
@Data
public class UsersRetentionQuery {
    //前端传递的 查询日期 ex："2020-11-20"
    private String queryDateTime;
    private Date queryTime;
    //查询的天数
    private int days;
}
