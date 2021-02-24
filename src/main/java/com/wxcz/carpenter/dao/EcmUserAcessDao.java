package com.wxcz.carpenter.dao;

import com.wxcz.carpenter.pojo.entity.EcmUserAcess;
import com.wxcz.carpenter.pojo.vo.EcmUserAcessVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * @author cxd
 * @Date: 2020/8/14
 */
@Repository
public interface EcmUserAcessDao {
    int deleteByPrimaryKey(Integer pkAcessId);

    int insert(EcmUserAcess record);

    int insertSelective(EcmUserAcess record);

    EcmUserAcess selectByPrimaryKey(Integer pkAcessId);

    int updateByPrimaryKeySelective(EcmUserAcess record);

    int updateByPrimaryKey(EcmUserAcess record);

    /**
     * @param: [set] 所有的 permission（权限） id 集合
     * @return: java.util.List<com.wxcz.carpenter.pojo.vo.EcmUserAcessVO>  所有权限的 集合
     * @author: cxd
     * @Date: 2020/8/14
     * 描述 : 权限id集合 查询权限VO集合
     */
    List<EcmUserAcessVO> selectUSerAcessByRoles(@Param("ids") Set<String> set);
}
