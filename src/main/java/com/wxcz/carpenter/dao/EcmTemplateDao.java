package com.wxcz.carpenter.dao;

import com.wxcz.carpenter.pojo.entity.EcmTemplate;
import com.wxcz.carpenter.pojo.query.EcmTemplateQuery;
import com.wxcz.carpenter.pojo.vo.EcmInnerMessageVO;
import com.wxcz.carpenter.pojo.vo.EcmTemplateVo;

import java.util.List;

public interface EcmTemplateDao {
    int deleteByPrimaryKey(Integer pkTemplateId);

    int insert(EcmTemplate record);

    int insertSelective(EcmTemplate record);

    EcmTemplate selectByPrimaryKey(Integer pkTemplateId);

    int updateByPrimaryKeySelective(EcmTemplate record);

    int updateByPrimaryKey(EcmTemplate record);

    List<EcmTemplateVo> ajaxMsgTemplateList(EcmTemplateQuery ecmTemplateQuery);

    Integer ajaxMsgTemplateCount(EcmTemplateQuery ecmTemplateQuery);

    EcmTemplateVo selectByTitle(String template);
}