package com.wxcz.carpenter.pojo.query;

import com.wxcz.carpenter.pojo.dto.PageDTO;
import lombok.Data;

/**
 * @author by cxd
 * @Classname EcmUserQuery
 * @Description TODO
 * @Date 2020/8/6 10:48
 */
@Data
public class EcmUserQuery extends PageQuery {


    private String userId;

    private String userName;

    /**
     * 加密
     */
    private String passWord;


    private String phone;

    /**
     * 是否有效封禁
     */
    private String isValid;


    /**
     * 角色id , 分割
     */
    private String roles;

    /**
     * 用户头像状态 0 未审核，1 通过 ，2 不通过
     */
    private Byte userLogoStatus;

    /**
     * 用户下行总流量（单位KB）
     */
    private Integer subTotalFlow;


}
