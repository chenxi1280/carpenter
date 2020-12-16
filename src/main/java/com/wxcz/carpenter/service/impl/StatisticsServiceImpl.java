package com.wxcz.carpenter.service.impl;

import com.wxcz.carpenter.ExportPoi;
import com.wxcz.carpenter.dao.StatisticsPlayRecordDao;
import com.wxcz.carpenter.dao.StatisticsStorylineNaturalshowDao;
import com.wxcz.carpenter.pojo.dto.ResponseDTO;
import com.wxcz.carpenter.pojo.dto.StatisticUserExcl;
import com.wxcz.carpenter.pojo.entity.StatisticsPlayRecord;
import com.wxcz.carpenter.pojo.entity.StatisticsStorylineNaturalshow;
import com.wxcz.carpenter.pojo.query.UsersRetentionQuery;
import com.wxcz.carpenter.pojo.vo.StatisticsPlayRecordVO;
import com.wxcz.carpenter.pojo.vo.StatisticsStorylineNaturalshowVO;
import com.wxcz.carpenter.service.StatisticsService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;

import static com.wxcz.carpenter.common.CommomConfig.LINUX_CONFIG_UPLOAD_PATH;
import static com.wxcz.carpenter.common.CommomConfig.WINDOW_CONFIG_UPLOAD_PATH;
import static com.wxcz.carpenter.util.DateUtil.*;
import static java.lang.Double.NaN;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

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

    /**
     * @param: [usersRetentionQuery]
     * @return: com.wxcz.carpenter.pojo.dto.ResponseDTO
     * @author: cxd
     * @Date: 2020/11/24
     * 描述 : 每日新增
     * 成功: status 200  msg "success”   date:
     * 失败: status 500  msg "error“
     */
    @Override
    public ResponseDTO getDailyUsers(UsersRetentionQuery usersRetentionQuery) {

        List<StatisticsPlayRecordVO> list = statisticsPlayRecordDao.selectDailyUsers(usersRetentionQuery);
        return ResponseDTO.ok("success", list.size());
    }

    /**
     * @param: [usersRetentionQuery]
     * @return: com.wxcz.carpenter.pojo.dto.ResponseDTO
     * @author: cxd
     * @Date: 2020/11/24
     * 描述 : 留存率计算 -
     * 成功: status 200  msg "success”   date:
     * 失败: status 500  msg "error“
     */
    @Override
    public ResponseDTO getAddDailyUsers(UsersRetentionQuery usersRetentionQuery) {

        HashMap<String, Integer> map = new HashMap<>();
        try {
            List<StatisticsPlayRecordVO> list = statisticsPlayRecordDao.selectDailyUsers(usersRetentionQuery);

            map.put(usersRetentionQuery.getQueryDateTime(), list.size());
            for (int days = usersRetentionQuery.getDays(); days > 0; days--) {
                usersRetentionQuery.setQueryDateTime(subDay((usersRetentionQuery.getQueryDateTime())));
                List<StatisticsPlayRecordVO> lists = statisticsPlayRecordDao.selectDailyUsers(usersRetentionQuery);
                map.put(usersRetentionQuery.getQueryDateTime(), lists.size());
            }

        } catch (ParseException e) {
            e.printStackTrace();
            return ResponseDTO.fail();
        }
        return ResponseDTO.ok("succe", map);
    }

    /**
     * @param: [usersRetentionQuery]
     * @return: com.wxcz.carpenter.pojo.dto.ResponseDTO
     * @author: cxd
     * @Date: 2020/11/24
     * 描述 : 留存率计算 +
     * 成功: status 200  msg "success”   date:
     * 失败: status 500  msg "error“
     */
    @Override
    public ResponseDTO getUsersRetention(UsersRetentionQuery usersRetentionQuery) {
//        usersRetentionQuery.get
        HashMap<String, Integer> map = new HashMap<>();
        try {
            List<StatisticsPlayRecordVO> list = statisticsPlayRecordDao.selectDailyUsers(usersRetentionQuery);

            if (CollectionUtils.isEmpty(list)) {
                return ResponseDTO.fail();
            }

            map.put(usersRetentionQuery.getQueryDateTime(), list.size());
            for (int days = usersRetentionQuery.getDays(); days > 0; days--) {
                usersRetentionQuery.setQueryDateTime(addDay(usersRetentionQuery.getQueryDateTime()));
                List<StatisticsPlayRecordVO> lists = statisticsPlayRecordDao.selectDailyUsersByTimeList(list, usersRetentionQuery.getQueryDateTime());
                map.put(usersRetentionQuery.getQueryDateTime(), lists.size());

            }

        } catch (ParseException e) {
            e.printStackTrace();
            return ResponseDTO.fail();
        }
        return ResponseDTO.ok("succe", map);
    }

    /**
     * @param: [usersRetentionQuery]
     * @return: com.wxcz.carpenter.pojo.dto.ResponseDTO
     * @author: cxd
     * @Date: 2020/11/24
     * 描述 : 人均观看数
     * 成功: status 200  msg "success”   date:
     * 失败: status 500  msg "error“
     */
    @Override
    public ResponseDTO getViewedPerCapita(UsersRetentionQuery usersRetentionQuery) {
        List<StatisticsPlayRecordVO> list = statisticsPlayRecordDao.getViewedPerCapita(usersRetentionQuery);
        int count = 0;
        for (StatisticsPlayRecordVO statisticsPlayRecordVO : list) {
            count += statisticsPlayRecordVO.getUserIdCount();
        }
        double i = (count + 1.00 - 1) / (list.size() + 1.00 - 1);
        if (Double.isNaN(i) ){
            i = 0.0;
        }
        return ResponseDTO.ok("succe", i);
    }

    /**
     * @param: [usersRetentionQuery]
     * @return: com.wxcz.carpenter.pojo.dto.ResponseDTO
     * @author: cxd
     * @Date: 2020/11/24
     * 描述 : 平均完播率
     * 成功: status 200  msg "success”   date:
     * 失败: status 500  msg "error“
     */
    @Override
    public ResponseDTO getAverageCompletionRate(UsersRetentionQuery usersRetentionQuery) {
        List<StatisticsStorylineNaturalshowVO> list = statisticsStorylineNaturalshowDao.getAverageCompletionRate(usersRetentionQuery);
        List<StatisticsPlayRecordVO> lists = statisticsPlayRecordDao.getPlayCountByQueryTime(usersRetentionQuery);
        int count = 0;
        for (StatisticsStorylineNaturalshowVO statisticsPlayRecordVO : list) {
            count += statisticsPlayRecordVO.getUserIdCount();
        }

        double i = (count + 1.00 - 1) / lists.size();
        if (Double.isNaN(i) ){
            i = 0.0;
        }
        return ResponseDTO.ok("succe",i );
    }

    @Override
    public ResponseDTO getStatisticsUserExcl(UsersRetentionQuery usersRetentionQuery) {

        try {
            long daySub = getDaySub(usersRetentionQuery.getQueryDateTime(), timeStamp2Date(timeStamp(), "yyyy/MM/dd"));
            List<StatisticUserExcl> ls = new ArrayList();
            StatisticUserExcl statisticUserExcl = new StatisticUserExcl();
            statisticUserExcl.setQueryDateTime(usersRetentionQuery.getQueryDateTime());
            statisticUserExcl.setAddUserCount((Integer) getDailyUsers(usersRetentionQuery).getData());
            statisticUserExcl.setPerCompleteViews((Double) getAverageCompletionRate(usersRetentionQuery).getData());
            statisticUserExcl.setPerViews((Double) getViewedPerCapita(usersRetentionQuery).getData());

            ls.add(statisticUserExcl);
            for (int i = 1; i <= daySub; i++) {

                usersRetentionQuery.setQueryDateTime(addDay(usersRetentionQuery.getQueryDateTime()));
                StatisticUserExcl s = new StatisticUserExcl();
                s.setQueryDateTime(usersRetentionQuery.getQueryDateTime());
                s.setAddUserCount((Integer) getDailyUsers(usersRetentionQuery).getData());
                s.setPerCompleteViews((Double) getAverageCompletionRate(usersRetentionQuery).getData());
                s.setPerViews((Double) getViewedPerCapita(usersRetentionQuery).getData());

                if (i >=  7) {
                    s.setSevenRate(getAddDaysRate(usersRetentionQuery, 7));
                }
                if (i  >=  15 ) {
                    s.setFifteenRate(getAddDaysRate(usersRetentionQuery, 15));
                }
                if (i >= 30 ) {
                    s.setThirtyRate(getAddDaysRate(usersRetentionQuery, 30));
                }
                ls.add(s);
            }


//        ls.add(new StatisticUserExcl("2020/11/24",0,0.1,0.1,0.1,100.00,0.2));
//        ls.add(new StatisticUserExcl("2020/11/25",0,0.1,0.1,0.1,100.00,0.2));
//        ls.add(new StatisticUserExcl("2020/11/26",0,0.1,0.1,0.1,100.00,0.2));
//        ls.add(new StatisticUserExcl("2020/11/27",0,0.1,0.1,0.1,100.00,0.2));
//        ls.add(new StatisticUserExcl("2020/11/28",0,0.1,0.1,0.1,100.00,0.2));
//        ls.add(new StatisticUserExcl("2020/11/29",0,0.1,0.1,0.1,100.00,0.2));

            String systemType = System.getProperty("os.name");// 获取系统的类别, Window
            String realPath = systemType.toLowerCase().contains("windows") ? WINDOW_CONFIG_UPLOAD_PATH: LINUX_CONFIG_UPLOAD_PATH;

            System.out.println("系统环境为："+ systemType);
            System.out.println("地址为："+ realPath);
            ExportPoi.export(StatisticUserExcl.class, ls, statisticUserExcl.getQueryDateTime().replace('/','-')+".xls", realPath, new String[]{"时间", "新增用户", "7日留存率", "15日留存率", "30日留存率", "人均观看数", "平均完播率"}, new String[]{"name", "id", "gender", "score"});

        } catch (Exception e) {
            e.printStackTrace();
        }


        return null;
    }

    public Double getAddDaysRate(UsersRetentionQuery usersRetentionQuery, int days) {
        try {
            UsersRetentionQuery usersRetention = new UsersRetentionQuery();
            usersRetention.setQueryDateTime(subDay(usersRetentionQuery.getQueryDateTime(), days));
            List<StatisticsPlayRecordVO> list = statisticsPlayRecordDao.selectDailyUsers(usersRetention);
            if (CollectionUtils.isEmpty(list)) {
                return 0.0;
            }
            List<StatisticsPlayRecordVO> lists = statisticsPlayRecordDao.selectDailyUsersByTimeList(list, usersRetentionQuery.getQueryDateTime());
            ArrayList<StatisticsPlayRecordVO> collect = lists.stream().collect(collectingAndThen(toCollection(() -> new TreeSet<>(Comparator.comparing(StatisticsPlayRecord::getFkUserId))), ArrayList::new));
            if (CollectionUtils.isEmpty(collect)) {
                return 0.0;
            }
            return (list.size() + 1.00 - 1) / collect.size();

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
