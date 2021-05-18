package com.wxcz.carpenter.dao;

import com.wxcz.carpenter.pojo.entity.EcmDownlinkFlow;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface EcmDownlinkFlowDao {
    int deleteByPrimaryKey(Integer pkId);

    int insert(EcmDownlinkFlow record);

    int insertSelective(EcmDownlinkFlow record);

    EcmDownlinkFlow selectByPrimaryKey(Integer pkId);

    int updateByPrimaryKeySelective(EcmDownlinkFlow record);

    int updateByPrimaryKey(EcmDownlinkFlow record);

    List<EcmDownlinkFlow> selectByUserIdList(@Param("list") Collection<Integer> integers);
}
