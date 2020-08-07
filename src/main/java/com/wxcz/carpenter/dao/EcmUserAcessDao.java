package com.wxcz.carpenter.dao;

import com.wxcz.carpenter.pojo.entity.EcmUserAcess;
import com.wxcz.carpenter.pojo.vo.EcmUserAcessVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface EcmUserAcessDao {
    int deleteByPrimaryKey(Integer pkAcessId);

    int insert(EcmUserAcess record);

    int insertSelective(EcmUserAcess record);

    EcmUserAcess selectByPrimaryKey(Integer pkAcessId);

    int updateByPrimaryKeySelective(EcmUserAcess record);

    int updateByPrimaryKey(EcmUserAcess record);

    List<EcmUserAcessVO> selectUSerAcessByRoles(@Param("ids") Set<String> set);
}