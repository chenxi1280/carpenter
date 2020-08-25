package com.wxcz.carpenter.pojo.query;

import lombok.Data;

/**
 * creator：cxd
 * date: 2020/8/5
 */
@Data
public class PageQuery {
    private Integer page = 1;
    private Integer limit = 10;
    private Integer start = 0;
    // 偏移量计算出来
    public Integer getStart() {
        if (page != null && limit != null) {
            return (page - 1) * limit;
        }
        return start;
    }
}
