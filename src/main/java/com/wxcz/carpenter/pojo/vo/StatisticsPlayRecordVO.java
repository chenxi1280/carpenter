package com.wxcz.carpenter.pojo.vo;

import com.wxcz.carpenter.pojo.entity.StatisticsPlayRecord;
import lombok.Data;

/**
 * @author by cxd
 * @Classname StatisticsPlayRecordVO
 * @Description TODO
 * @Date 2020/11/19 16:15
 */
@Data
public class StatisticsPlayRecordVO extends StatisticsPlayRecord {
    private Integer userIdCount;

    private Integer playCount;

    private String queryDateTime;
}
