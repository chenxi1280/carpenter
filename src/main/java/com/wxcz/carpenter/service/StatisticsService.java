package com.wxcz.carpenter.service;

import com.wxcz.carpenter.pojo.dto.ResponseDTO;
import com.wxcz.carpenter.pojo.query.UsersRetentionQuery;

/**
 * @author by cxd
 * @Classname StatisticsService
 * @Description TODO
 * @Date 2020/11/19 10:24
 */
public interface StatisticsService {
    ResponseDTO getDailyUsers(UsersRetentionQuery usersRetentionQuery);

    ResponseDTO getUsersRetention(UsersRetentionQuery usersRetentionQuery);

    ResponseDTO getViewedPerCapita(UsersRetentionQuery usersRetentionQuery);

    ResponseDTO getAverageCompletionRate(UsersRetentionQuery usersRetentionQuery);
}
