package com.wxcz.carpenter.dao;

import com.wxcz.carpenter.pojo.entity.EcmUserLightReward;
import com.wxcz.carpenter.pojo.query.EcmLightManagementQuery;
import com.wxcz.carpenter.pojo.vo.EcmUserLightRewardVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EcmUserLightRewardDao {
    int deleteByPrimaryKey(Integer ecmUserLightRewardId);

    int insert(EcmUserLightReward record);

    int insertSelective(EcmUserLightReward record);

    EcmUserLightReward selectByPrimaryKey(Integer ecmUserLightRewardId);

    int updateByPrimaryKeySelective(EcmUserLightReward record);

    int updateByPrimaryKey(EcmUserLightReward record);

    List<EcmUserLightRewardVO> ajaxLightRewardList(EcmLightManagementQuery ecmTemplateQuery);

    Integer ajaxLightRewardListCount(EcmLightManagementQuery ecmTemplateQuery);

    List<EcmUserLightRewardVO> selectByAll();
}
