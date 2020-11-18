package com.wxcz.carpenter.dao;

import com.wxcz.carpenter.pojo.entity.EcmUserLightVip;
import com.wxcz.carpenter.pojo.query.EcmLightManagementQuery;
import com.wxcz.carpenter.pojo.vo.EcmUserLightRewardVO;
import com.wxcz.carpenter.pojo.vo.EcmUserLightVipVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EcmUserLightVipDao {
    int deleteByPrimaryKey(Integer ecmUserLightVipId);

    int insert(EcmUserLightVip record);

    int insertSelective(EcmUserLightVip record);

    EcmUserLightVip selectByPrimaryKey(Integer ecmUserLightVipId);

    int updateByPrimaryKeySelective(EcmUserLightVip record);

    int updateByPrimaryKey(EcmUserLightVip record);

    List<EcmUserLightVipVO> ajaxLightVipList(EcmLightManagementQuery ecmTemplateQuery);

    Integer ajaxLightVipListCount(EcmLightManagementQuery ecmTemplateQuery);

    List<EcmUserLightVipVO> selectByEcmUserLightRewardVOList(@Param("ids") List<EcmUserLightRewardVO> list);
}
