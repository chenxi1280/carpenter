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

    /**
     * @param: [query] 登录验证的对象 存入电话号码 和 加密后的 密码
     * @return: com.wxcz.carpenter.pojo.vo.EcmUserVO
     * @author: cxd
     * @Date: 2020/8/7
     * 描述 : 查询数据库是否有对应的 账号 加密密码
     *      有 返回对象
     *      无 返回 null
     */
    EcmUserVO login(EcmUserQuery query);

    /**
     * @param: [string] 电话号码
     * @return: java.util.List<com.wxcz.carpenter.pojo.vo.EcmUserRolesVO>
     * @author: cxd
     * @Date: 2020/8/7
     * 描述 : 按电话号码 插找 user 对象 并返回 角色 (EcmUserRolesVO 包含 权限 集合) 集合（list）
     */
    List<EcmUserRolesVO> selectUserRolesByPhone(String string);

    /**
     * @param: [ecmUserVO] 用户完整对象（roles 字段 不为 null）
     * @return: java.util.List<com.wxcz.carpenter.pojo.vo.EcmUserRolesVO>
     * @author: cxd
     * @Date: 2020/8/7
     * 描述 : 并返回 角色 (EcmUserRolesVO 包含 权限 集合) 集合（list）
     */
    List<EcmUserRolesVO> selectUserRolesByUser(EcmUserVO ecmUserVO);

    /**
     * @param: [rolesVOList] 角色集合
     * @return: java.util.List<com.wxcz.carpenter.pojo.vo.EcmUserAcessVO>权限 集合
     * @author: cxd
     * @Date: 2020/8/7
     * 描述 : 角色集合 查询 权限 集合
     */
    List<EcmUserAcessVO> selectUSerAcessByRoles(List<EcmUserRolesVO> rolesVOList);

    /**
     * @param: [ecmUserQuery] 查询条件
     * @return: com.wxcz.carpenter.pojo.dto.PageDTO
     * @author: cxd
     * @Date: 2020/8/7
     * 描述 : 按条件查询用户
     *       保存成功: status 0  msg "success” 数据 data
     *       保存失败: status 500  msg "error“
     */
    PageDTO ajaxList(EcmUserQuery ecmUserQuery);

    /**
     * @param: [ecmUserVO] 更新对象
     * @return: com.wxcz.carpenter.pojo.dto.ResponseDTO
     * @author: cxd
     * @Date: 2020/8/7
     * 描述 : 更新用户接口
     */
    ResponseDTO upDataUser(EcmUserVO ecmUserVO);

    /**
     * @param: [ecmUserVO]
     * @return: com.wxcz.carpenter.pojo.dto.ResponseDTO
     * @author: cxd
     * @Date: 2020/8/7
     * 描述 : 对用户状态进行修改
     *       保存成功: status 200  msg "success”
     *       保存失败: status 500  msg "error“
     */
    ResponseDTO chengUser(EcmUserVO ecmUserVO);

    /**
     * @param: [ecmUserVO]
     * @return: com.wxcz.carpenter.pojo.dto.ResponseDTO
     * @author: cxd
     * @Date: 2020/8/7
     * 描述 :
     *        用户自己在个人中心设置密码接口
     *       保存成功: status 200  msg "success”
     *       保存失败: status 500  msg "error“
     */
    ResponseDTO setPassWord(EcmUserVO ecmUserVO);
}
