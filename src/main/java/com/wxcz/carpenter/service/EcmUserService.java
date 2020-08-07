package com.wxcz.carpenter.service;

import com.wxcz.carpenter.pojo.dto.PageDTO;
import com.wxcz.carpenter.pojo.dto.ResponseDTO;
import com.wxcz.carpenter.pojo.query.EcmUserQuery;
import com.wxcz.carpenter.pojo.vo.EcmUserAcessVO;
import com.wxcz.carpenter.pojo.vo.EcmUserRolesVO;
import com.wxcz.carpenter.pojo.vo.EcmUserVO;

import java.util.List;

/**
 * @author by cxd
 * @Classname EcmUserService
 * @Description TODO
 * @Date 2020/8/6 10:42
 */
public interface EcmUserService {
    EcmUserVO login(EcmUserQuery query);

    List<EcmUserRolesVO> selectUserRolesByPhone(String mobile);

    List<EcmUserRolesVO> selectUserRolesByUser(EcmUserVO ecmUserVO);

    List<EcmUserAcessVO> selectUSerAcessByRoles(List<EcmUserRolesVO> rolesVOList);

    PageDTO ajaxList(EcmUserQuery ecmUserQuery);

    ResponseDTO upDataUser(EcmUserVO ecmUserVO);

    ResponseDTO chengUser(EcmUserVO ecmUserVO);

}
