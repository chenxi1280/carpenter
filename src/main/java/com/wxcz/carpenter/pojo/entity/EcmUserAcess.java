package com.wxcz.carpenter.pojo.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * ecm_user_acess
 * @author 
 */
@Data
public class EcmUserAcess implements Serializable {
    /**
     * 用户权限表
     */
    private Integer pkAcessId;

    /**
     * 权限名称
     */
    private String name;

    /**
     * 权限标记
     */
    private String flag;

    /**
     * 备注
     */
    private String note;

    private static final long serialVersionUID = 1L;
}