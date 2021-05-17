package com.wxcz.carpenter.dao;

import com.wxcz.carpenter.pojo.entity.EcmUserCloud;
import org.springframework.stereotype.Repository;

@Repository
public interface EcmUserCloudDao {
    int deleteByPrimaryKey(Integer pkUserCloudId);

    int insert(EcmUserCloud record);

    int insertSelective(EcmUserCloud record);

    EcmUserCloud selectByPrimaryKey(Integer pkUserCloudId);

    int updateByPrimaryKeySelective(EcmUserCloud record);

    int updateByPrimaryKey(EcmUserCloud record);
}
