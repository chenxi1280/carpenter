package com.wxcz.carpenter.dao;

import com.wxcz.carpenter.pojo.entity.StatisticsPlayRecord;
import com.wxcz.carpenter.pojo.vo.StatisticsPlayRecordVO;

import java.util.List;

public interface StatisticsPlayRecordDao {
    int deleteByPrimaryKey(Integer pkPlayRecordId);

    int insert(StatisticsPlayRecord record);

    int insertSelective(StatisticsPlayRecord record);

    StatisticsPlayRecord selectByPrimaryKey(Integer pkPlayRecordId);

    int updateByPrimaryKeySelective(StatisticsPlayRecord record);

    int updateByPrimaryKey(StatisticsPlayRecord record);

    List<StatisticsPlayRecordVO> selectDailyUsers();

}
