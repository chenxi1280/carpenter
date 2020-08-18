package com.wxcz.carpenter.pojo.vo;

import com.wxcz.carpenter.pojo.entity.EcmReportHistroy;
import lombok.Data;

/**
 * @author by cxd
 * @Classname EcmReportHistroyVO
 * @Description TODO
 * @Date 2020/8/18 17:45
 */
@Data
public class EcmReportHistroyVO extends EcmReportHistroy {

    private String username;

    private String fkChName;

    private String artWorkName;
}
