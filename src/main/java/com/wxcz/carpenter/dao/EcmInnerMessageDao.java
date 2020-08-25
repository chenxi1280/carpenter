package com.wxcz.carpenter.dao;

import com.wxcz.carpenter.pojo.entity.EcmInnerMessage;
import com.wxcz.carpenter.pojo.vo.EcmInnerMessageVO;
import com.wxcz.carpenter.pojo.vo.EcmUserVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author cxd
 * @Date: 2020/8/25
 */
public interface EcmInnerMessageDao {
    int deleteByPrimaryKey(Integer pkMessageId);

    int insert(EcmInnerMessage record);

    int insertSelective(EcmInnerMessage record);

    EcmInnerMessage selectByPrimaryKey(Integer pkMessageId);

    int updateByPrimaryKeySelective(EcmInnerMessage record);

    int updateByPrimaryKey(EcmInnerMessage record);

    /**
     * @param: [list （用户信息集合）, ecmInnerMessage （消息模板]
     * @return: int
     * @author: cxd
     * @Date: 2020/8/25
     * 描述 : 批量的插入 站内信
     */
    int insertMsgAll(@Param("list") List<EcmUserVO> list, @Param("msg") EcmInnerMessage ecmInnerMessage);

    /**
     * @param: [ecmInnerMessageVOS] 站内信 集合
     * @return: int
     * @author: cxd
     * @Date: 2020/8/25
     * 描述 : 批量插入 EcmInnerMessage
     */
    int insertMsgPart(@Param("list") List<EcmInnerMessageVO> ecmInnerMessageVOS);
}