package com.wxcz.carpenter.dao;

import com.wxcz.carpenter.pojo.entity.EcmUserFlow;
import com.wxcz.carpenter.pojo.vo.EcmUserFlowVO;
import com.wxcz.carpenter.pojo.vo.EcmUserVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Repository
public interface EcmUserFlowDao {
    int deleteByPrimaryKey(Integer userFlowId);

    int insert(EcmUserFlow record);

    int insertSelective(EcmUserFlow record);

    EcmUserFlow selectByPrimaryKey(Integer userFlowId);

    int updateByPrimaryKeySelective(EcmUserFlow record);

    int updateByPrimaryKey(EcmUserFlow record);

    List<EcmUserFlowVO> selectByUserList(@Param("ids") List<EcmUserVO> list);

    Integer updateUserFlowCheck(@Param("list")List<EcmUserFlow> ecmUserFlows);

    List<EcmUserFlowVO> selectByUserIds(@Param("ids") Collection<Integer> integers);

    Integer updateUserFlowByCheck(@Param("list")List<EcmUserFlow> ecmUserFlows);

}
