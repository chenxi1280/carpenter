package com.wxcz.carpenter.dao;

import com.wxcz.carpenter.pojo.entity.EcmTemplate;
import com.wxcz.carpenter.pojo.query.EcmTemplateQuery;
import com.wxcz.carpenter.pojo.vo.EcmInnerMessageVO;
import com.wxcz.carpenter.pojo.vo.EcmTemplateVo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EcmTemplateDao {
    int deleteByPrimaryKey(Integer pkTemplateId);

    int insert(EcmTemplate record);

    int insertSelective(EcmTemplate record);

    EcmTemplate selectByPrimaryKey(Integer pkTemplateId);

    int updateByPrimaryKeySelective(EcmTemplate record);

    int updateByPrimaryKey(EcmTemplate record);

    /**
     * @param: [ecmTemplateQuery] 查询条件自带分页
     * @return: java.util.List<com.wxcz.carpenter.pojo.vo.EcmTemplateVo>
     * @author: cxd
     * @Date: 2020/8/25
     * 描述 :  根据条件查询 msg 模板 集合
     */
    List<EcmTemplateVo> ajaxMsgTemplateList(EcmTemplateQuery ecmTemplateQuery);

    /**
     * @param: [ecmTemplateQuery] 查询条件
     * @return: java.lang.Integer
     * @author: cxd
     * @Date: 2020/8/25
     * 描述 : 根据条件查询msg数量
     */
    Integer ajaxMsgTemplateCount(EcmTemplateQuery ecmTemplateQuery);

    /**
     * @param: [template]
     * @return: com.wxcz.carpenter.pojo.vo.EcmTemplateVo
     * @author: cxd
     * @Date: 2020/8/25
     * 描述 : 根据 msg title 模糊查询 msg模板
     */
    EcmTemplateVo selectByTitle(String template);
}
