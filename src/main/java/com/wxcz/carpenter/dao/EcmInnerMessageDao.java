package com.wxcz.carpenter.dao;

import com.wxcz.carpenter.pojo.entity.EcmInnerMessage;
import com.wxcz.carpenter.pojo.vo.EcmUserVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EcmInnerMessageDao {
    int deleteByPrimaryKey(Integer pkMessageId);

    int insert(EcmInnerMessage record);

    int insertSelective(EcmInnerMessage record);

    EcmInnerMessage selectByPrimaryKey(Integer pkMessageId);

    int updateByPrimaryKeySelective(EcmInnerMessage record);

    int updateByPrimaryKey(EcmInnerMessage record);

    int insertMsgAll(@Param("list") List<EcmUserVO> list, @Param("msg") EcmInnerMessage ecmInnerMessage);
}