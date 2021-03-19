package com.wxcz.carpenter.pojo.query;

import lombok.Data;

import java.util.Date;

/**
 * @author by SJ
 * @Classname EcmVipRoleAuthorityQuery
 * @Description TODO
 * @Date 2021/3/16 18:24
 */
@Data
public class EcmVipRoleAuthorityQuery extends PageQuery {
    private Integer pkId;

    /**
     * 用户vip角色id 0普通会员，1超级会员
     */
    private Integer fkVipRoleId;

    /**
     * 角色权限id
     */
    private Integer fkVipAuthorityId;

    /**
     * 普通会员,超级会员,普通用户
     */
    private String vipRoleDescribe;

    /**
     * 权限描述
     */
    private String vipAuthorityDescribe;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;
}
