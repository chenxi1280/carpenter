package com.wxcz.carpenter.pojo.vo;

import com.wxcz.carpenter.pojo.entity.EcmWorkOrder;
import lombok.Data;

/**
 * @author by cxd
 * @Classname EcmWorkOrderVO
 * @Description TODO
 * @Date 2021/6/1 11:18
 */
@Data
public class EcmWorkOrderVO extends EcmWorkOrder {
    private String fkHandlerName;

}
