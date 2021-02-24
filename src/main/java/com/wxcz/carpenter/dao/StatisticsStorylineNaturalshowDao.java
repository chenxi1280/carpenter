package com.wxcz.carpenter.dao;

import com.wxcz.carpenter.pojo.entity.StatisticsStorylineNaturalshow;
import com.wxcz.carpenter.pojo.query.UsersRetentionQuery;
import com.wxcz.carpenter.pojo.vo.StatisticsPlayRecordVO;
import com.wxcz.carpenter.pojo.vo.StatisticsStorylineNaturalshowVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatisticsStorylineNaturalshowDao {
    int deleteByPrimaryKey(Integer pkStorylineNaturalshowId);

    int insert(StatisticsStorylineNaturalshow record);

    int insertSelective(StatisticsStorylineNaturalshow record);

    StatisticsStorylineNaturalshow selectByPrimaryKey(Integer pkStorylineNaturalshowId);

    int updateByPrimaryKeySelective(StatisticsStorylineNaturalshow record);

    int updateByPrimaryKey(StatisticsStorylineNaturalshow record);

    List<StatisticsStorylineNaturalshowVO> getAverageCompletionRate(UsersRetentionQuery usersRetentionQuery);
}
