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
     * 是否有效Y/N
     */
    private String isValid;


    /**
     * 角色id , 分割
     */
    private String roles;
}
