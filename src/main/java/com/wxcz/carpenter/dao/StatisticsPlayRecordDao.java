package com.wxcz.carpenter.dao;

import com.wxcz.carpenter.pojo.entity.StatisticsPlayRecord;
import com.wxcz.carpenter.pojo.query.UsersRetentionQuery;
import com.wxcz.carpenter.pojo.vo.StatisticsPlayRecordVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StatisticsPlayRecordDao {
    int deleteByPrimaryKey(Integer pkPlayRecordId);

    int insert(StatisticsPlayRecord record);

    int insertSelective(StatisticsPlayRecord record);

    StatisticsPlayRecord selectByPrimaryKey(Integer pkPlayRecordId);

    int updateByPrimaryKeySelective(StatisticsPlayRecord record);

    int updateByPrimaryKey(StatisticsPlayRecord record);

    List<StatisticsPlayRecordVO> selectDailyUsers(UsersRetentionQuery usersRetentionQuery);

    List<StatisticsPlayRecordVO> getUsersRetention(UsersRetentionQuery usersRetentionQuery);

    List<StatisticsPlayRecordVO> selectDailyUsersByTimeList(@Param("list") List<StatisticsPlayRecordVO> list,@Param("queryDateTime") String queryDateTime);

    List<StatisticsPlayRecordVO> selectDailyUsersByStatisticsPlayRecordVOList(@Param("list")List<StatisticsPlayRecordVO> list);

    List<StatisticsPlayRecordVO> getViewedPerCapita(UsersRetentionQuery usersRetentionQuery);

    List<StatisticsPlayRecordVO> getPlayCountByQueryTime(UsersRetentionQuery usersRetentionQuery);
}
