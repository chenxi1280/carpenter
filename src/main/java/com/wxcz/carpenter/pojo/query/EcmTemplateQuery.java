package com.wxcz.carpenter.pojo.query;

import lombok.Data;

/**
 * @author by cxd
 * @Classname EcmTemplateQuery
 * @Description TODO
 * @Date 2020/8/19 17:50
 */
@Data
public class EcmTemplateQuery extends PageQuery {

    /**
     * 消息模板
     */
    private Integer pkTemplateId;

    /**
     * 模板内容
     */
    private String content;

    /**
     * 模板名称
     */
    private String templateName;

}
