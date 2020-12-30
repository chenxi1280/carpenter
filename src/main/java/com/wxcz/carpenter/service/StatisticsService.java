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
    /**
     * @param: [usersRetentionQuery]
     * @return: com.wxcz.carpenter.pojo.dto.ResponseDTO
     * @author: cxd
     * @Date: 2020/12/29
     * 描述 : 获取当前
     *       成功: status 200  msg "success”   date:
     *       失败: status 500  msg "error“
     */
    ResponseDTO getDailyUsers(UsersRetentionQuery usersRetentionQuery);

    ResponseDTO getAddDailyUsers(UsersRetentionQuery usersRetentionQuery);

    ResponseDTO getUsersRetention(UsersRetentionQuery usersRetentionQuery);

    ResponseDTO getViewedPerCapita(UsersRetentionQuery usersRetentionQuery);

    ResponseDTO getAverageCompletionRate(UsersRetentionQuery usersRetentionQuery);

    ResponseDTO getStatisticsUserExcl(UsersRetentionQuery usersRetentionQuery);
}
