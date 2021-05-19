package com.wxcz.carpenter.dao;

import com.wxcz.carpenter.pojo.entity.EcmUserNoticeRecord;

public interface EcmUserNoticeRecordDao {
    int deleteByPrimaryKey(Integer pkId);

    int insert(EcmUserNoticeRecord record);

    int insertSelective(EcmUserNoticeRecord record);

    EcmUserNoticeRecord selectByPrimaryKey(Integer pkId);

    int updateByPrimaryKeySelective(EcmUserNoticeRecord record);

    int updateByPrimaryKey(EcmUserNoticeRecord record);

    EcmUserNoticeRecord selectByUserId(Integer fkUserId);
}
