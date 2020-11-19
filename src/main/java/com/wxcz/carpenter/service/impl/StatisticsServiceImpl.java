package com.wxcz.carpenter.service.impl;

import com.wxcz.carpenter.dao.StatisticsPlayRecordDao;
import com.wxcz.carpenter.pojo.dto.ResponseDTO;
import com.wxcz.carpenter.pojo.entity.StatisticsPlayRecord;
import com.wxcz.carpenter.pojo.vo.StatisticsPlayRecordVO;
import com.wxcz.carpenter.service.StatisticsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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

    @Override
    public ResponseDTO getDailyUsers() {
        List<StatisticsPlayRecordVO> list = statisticsPlayRecordDao.selectDailyUsers();

        return ResponseDTO.ok();
    }
}
