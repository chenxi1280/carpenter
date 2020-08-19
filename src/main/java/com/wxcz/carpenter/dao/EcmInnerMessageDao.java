package com.wxcz.carpenter.dao;

import com.wxcz.carpenter.pojo.entity.EcmInnerMessage;
import com.wxcz.carpenter.pojo.query.EcmInnerMessageQuery;
import com.wxcz.carpenter.pojo.vo.EcmInnerMessageVO;

import java.util.List;

public interface EcmInnerMessageDao {
    int deleteByPrimaryKey(Integer pkMessageId);

    int insert(EcmInnerMessage record);

    int insertSelective(EcmInnerMessage record);

    EcmInnerMessage selectByPrimaryKey(Integer pkMessageId);

    int updateByPrimaryKeySelective(EcmInnerMessage record);

    int updateByPrimaryKey(EcmInnerMessage record);

    List<EcmInnerMessageVO> ajaxMsgTemplateList(EcmInnerMessageQuery ecmInnerMessageQuery);
}