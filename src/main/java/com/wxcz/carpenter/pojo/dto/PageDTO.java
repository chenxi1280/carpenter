package com.wxcz.carpenter.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * creator：cxd
 * date: 2020/8/5
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageDTO implements Serializable {// 传递给layui使用的DTO对象
    private Integer code = 0;

    private String msg = "";

    private Integer count = 0;

    private Object data;

    private Object moreData;

    public static PageDTO setPageData(Integer count, Object data) {
        return new PageDTO(0, "成功", count, data,null);
    }
    public static PageDTO setPageData(Integer count, Object data, Object moreData) {
        return new PageDTO(0, "成功", count, data,moreData);
    }
}
