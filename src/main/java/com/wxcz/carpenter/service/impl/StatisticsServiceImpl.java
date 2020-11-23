package com.wxcz.carpenter.service.impl;

import com.wxcz.carpenter.dao.StatisticsPlayRecordDao;
import com.wxcz.carpenter.dao.StatisticsStorylineNaturalshowDao;
import com.wxcz.carpenter.pojo.dto.ResponseDTO;
import com.wxcz.carpenter.pojo.entity.StatisticsStorylineNaturalshow;
import com.wxcz.carpenter.pojo.query.UsersRetentionQuery;
import com.wxcz.carpenter.pojo.vo.StatisticsPlayRecordVO;
import com.wxcz.carpenter.pojo.vo.StatisticsStorylineNaturalshowVO;
import com.wxcz.carpenter.service.StatisticsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

import static com.wxcz.carpenter.util.DateUtil.subDay;

/**
 * @author by cxd
 * @Classname StatisticsServiceImpl
 * @Description TODO
 * @Date 2020/11/19 10:24
 */
@Service
public class StatisticsServiceImpl implements StatisticsService {

    @Resource
    StatisticsPlayRecordDao statisticsPlayRecordDao;

    @Resource
    StatisticsStorylineNaturalshowDao statisticsStorylineNaturalshowDao;

    @Override
    public ResponseDTO getDailyUsers(UsersRetentionQuery usersRetentionQuery) {

        List<StatisticsPlayRecordVO> list = statisticsPlayRecordDao.selectDailyUsers(usersRetentionQuery);
        return ResponseDTO.ok("success",list.size());
    }

    @Override
    public ResponseDTO getUsersRetention(UsersRetentionQuery usersRetentionQuery) {
//        usersRetentionQuery.get
        HashMap<String, List<StatisticsPlayRecordVO>> map = new HashMap<>();
        try {
            List<StatisticsPlayRecordVO> list = statisticsPlayRecordDao.selectDailyUsers(usersRetentionQuery);

            map.put(usersRetentionQuery.getQueryDateTime(),list);
            for (int days = usersRetentionQuery.getDays(); days > 0; days--) {
                usersRetentionQuery.setQueryDateTime( subDay(usersRetentionQuery.getQueryDateTime())) ;
                List<StatisticsPlayRecordVO> lists = statisticsPlayRecordDao.selectDailyUsersByTimeList(list,usersRetentionQuery.getQueryDateTime());
                map.put(usersRetentionQuery.getQueryDateTime(),lists);

            }

        } catch (ParseException e) {
            e.printStackTrace();
            return ResponseDTO.fail();
        }
        return ResponseDTO.ok("succe",map);
    }

    @Override
    public ResponseDTO getViewedPerCapita(UsersRetentionQuery usersRetentionQuery) {
        List<StatisticsPlayRecordVO> list =  statisticsPlayRecordDao.getViewedPerCapita(usersRetentionQuery);
        int count = 0;
        for (StatisticsPlayRecordVO statisticsPlayRecordVO : list) {
            count += statisticsPlayRecordVO.getUserIdCount();
        }
        double i = (count + 1.00 - 1 )/ (list.size() + 1.00 - 1);

        return ResponseDTO.ok("succe",i);
    }

    @Override
    public ResponseDTO getAverageCompletionRate(UsersRetentionQuery usersRetentionQuery) {
        List<StatisticsStorylineNaturalshowVO> list =  statisticsStorylineNaturalshowDao.getAverageCompletionRate(usersRetentionQuery);
        List<StatisticsPlayRecordVO> lists =  statisticsPlayRecordDao.getPlayCountByQueryTime(usersRetentionQuery);
        int count = 0;
        for (StatisticsStorylineNaturalshowVO statisticsPlayRecordVO : list) {
            count += statisticsPlayRecordVO.getUserIdCount();
        }
        int counts = 0;


        double i = (count + 1.00 - 1 ) / lists.size();

        return null;
    }
}
