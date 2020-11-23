package com.wxcz.carpenter.pojo.vo;

import com.wxcz.carpenter.pojo.entity.StatisticsStorylineNaturalshow;
import lombok.Data;

/**
 * @author by cxd
 * @Classname StatisticsStorylineNaturalshowVO
 * @Description TODO
 * @Date 2020/11/23 9:55
 */
@Data
public class StatisticsStorylineNaturalshowVO extends StatisticsStorylineNaturalshow {
    private Integer userIdCount;

    private Integer playCount;

    private String queryDateTime;
}
