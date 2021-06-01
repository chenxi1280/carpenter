package com.wxcz.carpenter.pojo.query;

import lombok.Data;

/**
 * @author by cxd
 * @Classname EcmWorkOrderQuery
 * @Description TODO
 * @Date 2021/6/1 11:15
 */
@Data
public class EcmWorkOrderQuery extends PageQuery {

    /**
     * 工单主键
     */
    private Integer pkWorkOrderId;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 用户公司
     */
    private String userCompany;

    /**
     * 用户联系方式
     */
    private String userContactInformation;

    /**
     * 用户申请功能
     */
    private String userApplicationFunction;

    /**
     * 工单状态 0 等待处理 ，1 处理中  ， 2 完成
     */
    private Integer applyStatus;
}
