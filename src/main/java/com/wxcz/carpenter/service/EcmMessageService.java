package com.wxcz.carpenter.service;

import com.wxcz.carpenter.pojo.dto.PageDTO;
import com.wxcz.carpenter.pojo.dto.ResponseDTO;
import com.wxcz.carpenter.pojo.entity.EcmArtwork;
import com.wxcz.carpenter.pojo.entity.EcmReportHistroy;
import com.wxcz.carpenter.pojo.query.EcmTemplateQuery;
import com.wxcz.carpenter.pojo.vo.EcmTemplateVo;

/**
 * @author by cxd
 * @Classname EcmMessageService
 * @Description TODO
 * @Date 2020/8/19 17:30
 */
public interface EcmMessageService {

    /**
     * @param: [ecmInnerMessageQuery] 查询条件 自带分页
     * @return: com.wxcz.carpenter.pojo.dto.PageDTO
     * @author: cxd
     * @Date: 2020/8/25
     * 描述 : layui 表格数据 获取 msg模板列表
     */
    PageDTO ajaxMsgTemplateList(EcmTemplateQuery ecmInnerMessageQuery);

    /**
     * @param: [ecmTemplateVo] 更新的 msg 的信息
     * @return: com.wxcz.carpenter.pojo.dto.ResponseDTO
     * @author: cxd
     * @Date: 2020/8/25
     * 描述 : 更新msg模板
     *       保存成功: status 200  msg "success”
     *       保存失败: status 500  msg "error“
     */
    ResponseDTO updataMsgTemplate(EcmTemplateVo ecmTemplateVo);

    /**
     * @param: [ecmTemplateQuery]msg模板id
     * @return: com.wxcz.carpenter.pojo.dto.ResponseDTO
     * @author: cxd
     * @Date: 2020/8/25
     * 描述 : 删除选中的 msg 模板
     *       保存成功: status 200  msg "success”
     *       保存失败: status 500  msg "error“
     */
    ResponseDTO delMsgTemplate(EcmTemplateQuery ecmTemplateQuery);

    /**
     * @param: [ecmTemplateVo] msg模板信息
     * @return: com.wxcz.carpenter.pojo.dto.ResponseDTO
     * @author: cxd
     * @Date: 2020/8/25
     * 描述 : 新增 msg 模板
     *       保存成功: status 200  msg "success”
     *       保存失败: status 500  msg "error“
     */
    ResponseDTO addMsgTemplate(EcmTemplateVo ecmTemplateVo);

    /**
     * @param: [pkTemplateId] msg模板id
     * @return: com.wxcz.carpenter.pojo.dto.ResponseDTO
     * @author: cxd
     * @Date: 2020/8/25
     * 描述 :  给所有用户发站内信
     *       保存成功: status 200  msg "success”
     *       保存失败: status 500  msg "error“
     */
    ResponseDTO addMsgAll(Integer pkTemplateId);

    /**
     * @param: [ecmTemplateVo] msg模板id 用户ids
     * @return: com.wxcz.carpenter.pojo.dto.ResponseDTO
     * @author: cxd
     * @Date: 2020/8/25
     * 描述 : 给传入的用户 ids，发送站内信
     *       保存成功: status 200  msg "success”
     *       保存失败: status 500  msg "error“
     */
    ResponseDTO addMsgPart(EcmTemplateVo ecmTemplateVo);

    /**
     * @param: [ecmArtwork （需要发送 站内信的 作品类 包含作品id ，作者 id）, template （msg 模板 名字 会进行模糊查询进行 ）]
     * @return: java.lang.Integer  返回成功的 条数
     * @author: cxd
     * @Date: 2020/8/25
     * 描述 : 作品审核站内信 发送方法
     */
    Integer insertSystemMsg(EcmArtwork ecmArtwork, String template);

    /**
     * @param: [ecmReportHistroy,（需要发送 站内信的 站内信类 包含作品id ，作者 id，节点）, template （msg 模板 名字 会进行模糊查询进行 ）
     * @return: java.lang.Integer返回成功的 条数
     * @author: cxd
     * @Date: 2020/8/25
     * 描述 : 作品违规站内信 发送方法
     */
    Integer insertViolationMsg(EcmReportHistroy ecmReportHistroy, String template);
}
