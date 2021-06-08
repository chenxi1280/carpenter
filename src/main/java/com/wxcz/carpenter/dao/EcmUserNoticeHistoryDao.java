package com.wxcz.carpenter.dao;

import com.wxcz.carpenter.pojo.entity.EcmUserNoticeHistory;
import com.wxcz.carpenter.pojo.query.EcmUserQuery;
import com.wxcz.carpenter.pojo.vo.EcmUserNoticeHistoryVO;

import java.util.List;

public interface EcmUserNoticeHistoryDao {
    int deleteByPrimaryKey(Integer pkId);

    int insert(EcmUserNoticeHistory record);

    int insertSelective(EcmUserNoticeHistory record);

    EcmUserNoticeHistory selectByPrimaryKey(Integer pkId);

    int updateByPrimaryKeySelective(EcmUserNoticeHistory record);

    int updateByPrimaryKey(EcmUserNoticeHistory record);

    List<EcmUserNoticeHistoryVO> selectListByEcmUserQuery(EcmUserQuery ecmUserQuery);

    Integer selectCountByEcmUserQuery(EcmUserQuery ecmUserQuery);
}
