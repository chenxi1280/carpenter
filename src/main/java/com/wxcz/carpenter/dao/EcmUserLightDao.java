package com.wxcz.carpenter.dao;

import com.wxcz.carpenter.pojo.entity.EcmUserLight;
import com.wxcz.carpenter.pojo.query.EcmUserLightQurey;
import com.wxcz.carpenter.pojo.vo.EcmUserLightVO;

import java.util.List;

public interface EcmUserLightDao {
    int deleteByPrimaryKey(Integer pkId);

    int insert(EcmUserLight record);

    int insertSelective(EcmUserLight record);

    EcmUserLight selectByPrimaryKey(Integer pkId);

    int updateByPrimaryKeySelective(EcmUserLight record);

    int updateByPrimaryKey(EcmUserLight record);

    List<EcmUserLightVO> selectAjaxUserLightList(EcmUserLightQurey ecmUserLightQurey);

    Integer selectAjaxUserLightListCount(EcmUserLightQurey ecmUserLightQurey);
}
