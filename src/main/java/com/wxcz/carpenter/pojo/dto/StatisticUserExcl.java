package com.wxcz.carpenter.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * @author by cxd
 * @Classname StatisticUserExcl
 * @Description TODO
 * @Date 2020/11/24 16:25
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatisticUserExcl {

    // 时间
    private String queryDateTime;
    // 新增用户
    private Integer addUserCount;
    // 7天留存率
    private Double sevenRate;
    // 15天留存率
    private Double fifteenRate;
    // 30天留存率
    private Double thirtyRate;
    // 人均观看数
    private Double perViews;
    // 平均完播率
    private Double perCompleteViews;

}
