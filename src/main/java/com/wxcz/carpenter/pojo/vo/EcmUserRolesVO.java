package com.wxcz.carpenter.pojo.vo;

/**
 * @author by cxd
 * @Classname EcmUserRolesVO
 * @Description TODO
 * @Date 2020/8/6 11:29
 */

import com.wxcz.carpenter.pojo.entity.EcmUserAcess;
import com.wxcz.carpenter.pojo.entity.EcmUserRoles;
import lombok.Data;

import java.util.List;

@Data
public class EcmUserRolesVO extends EcmUserRoles {
    List<EcmUserAcessVO> acessVOS;
}

