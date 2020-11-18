package com.wxcz.carpenter.dao;

import com.wxcz.carpenter.pojo.entity.EcmUserLightEvent;
import com.wxcz.carpenter.pojo.query.EcmLightManagementQuery;
import com.wxcz.carpenter.pojo.vo.EcmUserLightEventVO;
import com.wxcz.carpenter.pojo.vo.EcmUserLightRewardVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EcmUserLightEventDao {
    int deleteByPrimaryKey(Integer ecmUserLightEventId);

    int insert(EcmUserLightEvent record);

    int insertSelective(EcmUserLightEvent record);

    EcmUserLightEvent selectByPrimaryKey(Integer ecmUserLightEventId);

    int updateByPrimaryKeySelective(EcmUserLightEvent record);

    int updateByPrimaryKey(EcmUserLightEvent record);

    List<EcmUserLightEventVO> ajaxLightEventList(EcmLightManagementQuery ecmTemplateQuery);

    Integer ajaxLightEventListCount(EcmLightManagementQuery ecmTemplateQuery);

    List<EcmUserLightEventVO> selectByEcmUserLightRewardVOList(@Param("ids") List<EcmUserLightRewardVO> list);


}
