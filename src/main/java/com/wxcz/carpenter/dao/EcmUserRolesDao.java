package com.wxcz.carpenter.dao;

import com.wxcz.carpenter.pojo.entity.EcmUser;
import com.wxcz.carpenter.pojo.entity.EcmUserRoles;
import com.wxcz.carpenter.pojo.vo.EcmUserRolesVO;
import com.wxcz.carpenter.pojo.vo.EcmUserVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author cxd
 * @Date: 2020/8/14
 */
public interface EcmUserRolesDao {
    int deleteByPrimaryKey(Integer pkRoleId);

    int insert(EcmUserRoles record);

    int insertSelective(EcmUserRoles record);

    EcmUserRoles selectByPrimaryKey(Integer pkRoleId);

    int updateByPrimaryKeySelective(EcmUserRoles record);

    int updateByPrimaryKey(EcmUserRoles record);

    /**
     * @param: [roles] 角色字符串
     * @return: java.util.List<com.wxcz.carpenter.pojo.vo.EcmUserRolesVO> 角色集合
     * @author: cxd
     * @Date: 2020/8/14
     * 描述 : 角色字符串 查询  角色集合
     *          (${roles}) =》  （1,2,3）
     */
    List<EcmUserRolesVO> selectByRoles(String roles);



}