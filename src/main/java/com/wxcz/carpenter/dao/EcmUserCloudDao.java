package com.wxcz.carpenter.dao;

import com.wxcz.carpenter.pojo.entity.EcmUserCloud;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Repository
public interface EcmUserCloudDao {
    int deleteByPrimaryKey(Integer pkUserCloudId);

    int insert(EcmUserCloud record);

    int insertSelective(EcmUserCloud record);

    EcmUserCloud selectByPrimaryKey(Integer pkUserCloudId);

    int updateByPrimaryKeySelective(EcmUserCloud record);

    int updateByPrimaryKey(EcmUserCloud record);

    List<EcmUserCloud> selectByUserIdList(@Param("list") Collection<Integer> integers);
}
