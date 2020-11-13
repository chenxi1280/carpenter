package com.wxcz.carpenter.service.impl;

import com.alibaba.fastjson.JSON;
import com.wxcz.carpenter.common.ReportMnum;
import com.wxcz.carpenter.dao.*;
import com.wxcz.carpenter.pojo.dto.PageDTO;
import com.wxcz.carpenter.pojo.dto.ResponseDTO;
import com.wxcz.carpenter.pojo.entity.*;
import com.wxcz.carpenter.pojo.query.EcmTemplateQuery;
import com.wxcz.carpenter.pojo.vo.*;
import com.wxcz.carpenter.service.BaseService;
import com.wxcz.carpenter.service.EcmMessageService;
import com.wxcz.carpenter.service.EcmReportHistroyService;
import com.wxcz.carpenter.util.HttpUtils;
import com.wxcz.carpenter.util.Parser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author by cxd
 * @Classname EcmMessageServiceImpl
 * @Description TODO
 * @Date 2020/8/19 17:30
 */
@Service
public class EcmMessageServiceImpl implements EcmMessageService, BaseService {

    @Resource
    EcmInnerMessageDao ecmInnerMessageDao;

    @Resource
    EcmUserDao ecmUserDao;

    @Resource
    EcmArtworkDao ecmArtworkDao;

    @Resource
    EcmReportHistroyDao ecmReportHistroyDao;

    @Resource
    EcmTemplateDao ecmTemplateDao;

    @Resource
    EcmReportHistroyService ecmReportHistroyService;

    @Resource
    EcmArtworkNodesDao ecmArtworkNodesDao;


    @Override
    public PageDTO ajaxMsgTemplateList(EcmTemplateQuery ecmTemplateQuery) {

        List<EcmTemplateVo> ecmTemplateVos = ecmTemplateDao.ajaxMsgTemplateList(ecmTemplateQuery);
        Integer count = ecmTemplateDao.ajaxMsgTemplateCount(ecmTemplateQuery);

        return PageDTO.setPageData(count,ecmTemplateVos);
    }

    @Override
    public ResponseDTO updataMsgTemplate(EcmTemplateVo ecmTemplateVo) {
        ecmTemplateVo.setUpdateDate(new Date());
        return ResponseDTO.get(1 ==   ecmTemplateDao.updateByPrimaryKeySelective(ecmTemplateVo),"成功");
    }

    @Override
    public ResponseDTO delMsgTemplate(EcmTemplateQuery ecmTemplateQuery) {

        return ResponseDTO.get(1 == ecmTemplateDao.deleteByPrimaryKey(ecmTemplateQuery.getPkTemplateId()));
//        return ResponseDTO.ok();
    }

    @Override
    public ResponseDTO addMsgTemplate(EcmTemplateVo ecmTemplateVo) {
        ecmTemplateVo.setCreateDate( new Date());
        return ResponseDTO.get(1 == ecmTemplateDao.insertSelective(ecmTemplateVo));
    }

    @Override
    @Transactional
    public ResponseDTO addMsgAll(Integer pkTemplateId) {

        try {
            EcmTemplate ecmTemplate = ecmTemplateDao.selectByPrimaryKey(pkTemplateId);
            List<EcmUserVO>  list = ecmUserDao.selectAll();
            List<EcmInnerMessageVO> ecmInnerMessageVOS = msgReplace( ecmTemplate, list);

            ecmInnerMessageDao.insertMsgPart(ecmInnerMessageVOS);
            HttpUtils.post(HttpUtils.sendHttpsUrl, JSON.toJSONString(ecmInnerMessageVOS));
            return ResponseDTO.ok();
        } catch (Exception e){

            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

            return ResponseDTO.fail("发送错误，请重试");

        }
    }

    @Override
    @Transactional
    public ResponseDTO addMsgPart(EcmTemplateVo ecmTemplateVo) {
        EcmTemplate ecmTemplate = ecmTemplateDao.selectByPrimaryKey(ecmTemplateVo.getPkTemplateId());
        List<EcmUserVO>  list =  ecmUserDao.selectIds( Arrays.asList(ecmTemplateVo.getIds()));
        //
        List<EcmInnerMessageVO> ecmInnerMessageVOS = msgReplace( ecmTemplate, list);

        try {

            ecmInnerMessageDao.insertMsgPart(ecmInnerMessageVOS);
            System.out.println( JSON.toJSONString(ecmInnerMessageVOS));
            HttpUtils.post(HttpUtils.sendHttpsUrl, JSON.toJSONString(ecmInnerMessageVOS));
            return ResponseDTO.ok();
        } catch (Exception e){

            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponseDTO.fail("发送错误，请重试");

        }

    }

    @Override
    public Integer insertSystemMsg(EcmArtwork ecmArtwork, String template) {

        // 模糊 还是 指定
        EcmTemplateVo ecmTemplateVo = ecmTemplateDao.selectByTitle(template);
//        EcmTemplate ecmTemplateVo = ecmTemplateDao.selectByPrimaryKey(10);
        EcmArtwork ecmArt = ecmArtworkDao.selectByPrimaryKey(ecmArtwork.getPkArtworkId());
        EcmUser ecmUser = ecmUserDao.selectByPrimaryKey(ecmArt.getFkUserid());
        EcmInnerMessageVO insertMsgVO = getInsertMsgVO(ecmTemplateVo.getContent(),  ecmUser,ecmArt.getArtworkName());
        insertMsgVO.setFkTemplateId(ecmTemplateVo.getPkTemplateId());
        insertMsgVO.setTemplateName(ecmTemplateVo.getTemplateName());

        try {
            Integer a = ecmInnerMessageDao.insertSelective(insertMsgVO);
            List<EcmInnerMessageVO>  ecmInnerMessageVOS = new ArrayList<>(1);
            ecmInnerMessageVOS.add(insertMsgVO);
            HttpUtils.post(HttpUtils.sendHttpsUrl, JSON.toJSONString(ecmInnerMessageVOS));
            return a;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }


    }

    /**
     * @param: [ecmReportHistroy, template]
     * @return: java.lang.Integer
     * @author: cxd
     * @Date: 2020/8/28
     * 描述 :   投诉替换
     */
    @Override
    public Integer insertViolationMsg(EcmReportHistroy ecmReportHistroy, String template) {
        EcmTemplateVo ecmTemplateVo = ecmTemplateDao.selectByTitle(template);
        // 节点 id  名称 作品 id 作品名字
        EcmReportHistroyVO ecmReportHistroyVO = ecmReportHistroyService.getReportHistoryVOByEcmReportHistroy(ecmReportHistroy);

        EcmInnerMessageVO ecmInnerMessageVO = new EcmInnerMessageVO();

        ecmInnerMessageVO.setContent(ecmTemplateVo.getContent());
        // !{} 替换作品
        ecmInnerMessageVO.setContent(Parser.parse("!{","}", ecmInnerMessageVO.getContent(),ecmReportHistroyVO.getArtWorkName() ));
        // @{}替换节点名字
        ecmInnerMessageVO.setContent(Parser.parse("@{","}", ecmInnerMessageVO.getContent(),ecmReportHistroyVO.getArtWorkNameNodeName()));
        // #{} 替换节点违规信息
        ecmInnerMessageVO.setContent(Parser.parse("#{","}", ecmInnerMessageVO.getContent(),ReportMnum.getByValue(ecmReportHistroyVO.getReportStatue()).getName() ));


        ecmInnerMessageVO.setMessageStatus((short) 0);
        ecmInnerMessageVO.setSendDate(new Date());

        // 可能问题，在ai 审核自动通过的 时候 没有session
        if ( getRequstSession().getAttribute("userId") != null){
            ecmInnerMessageVO.setReviewerId((Integer) getRequstSession().getAttribute("userId"));
        }
        ecmInnerMessageVO.setReviewerId(2);
        ecmInnerMessageVO.setFkUserId(ecmReportHistroyVO.getFkUserid());
        ecmInnerMessageVO.setFkTemplateId(ecmTemplateVo.getPkTemplateId());
        ecmInnerMessageVO.setTemplateName(ecmTemplateVo.getTemplateName());

        try {
            Integer a = ecmInnerMessageDao.insertSelective(ecmInnerMessageVO);
            List<EcmInnerMessageVO>  ecmInnerMessageVOS = new ArrayList<>(1);
            ecmInnerMessageVOS.add(ecmInnerMessageVO);
            HttpUtils.post(HttpUtils.sendHttpsUrl, JSON.toJSONString(ecmInnerMessageVOS));
            return a;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public Integer insertSystemMsgByNode(EcmArtworkNodesVo ecmArtworkNodesVo, String msg) {
        // 模糊 还是 指定
        EcmTemplateVo ecmTemplateVo = ecmTemplateDao.selectByTitle(msg);
//        EcmTemplate ecmTemplateVo = ecmTemplateDao.selectByPrimaryKey(10);
        EcmArtwork ecmArt = ecmArtworkDao.selectByPrimaryKey(ecmArtworkNodesVo.getFkArtworkId());
        EcmUser ecmUser = ecmUserDao.selectByPrimaryKey(ecmArt.getFkUserid());
        EcmArtworkNodes ecmArtworkNodes = ecmArtworkNodesDao.selectByPrimaryKey(ecmArtworkNodesVo.getPkDetailId());
        EcmInnerMessageVO insertMsgVO = getInsertMsgVO(ecmTemplateVo.getContent(),  ecmUser,ecmArt.getArtworkName(),ecmArtworkNodes.getVideoText());
        insertMsgVO.setFkTemplateId(ecmTemplateVo.getPkTemplateId());
        insertMsgVO.setTemplateName(ecmTemplateVo.getTemplateName());

        try {
            Integer a = ecmInnerMessageDao.insertSelective(insertMsgVO);
            List<EcmInnerMessageVO>  ecmInnerMessageVOS = new ArrayList<>(1);
            ecmInnerMessageVOS.add(insertMsgVO);
            HttpUtils.post(HttpUtils.sendHttpsUrl, JSON.toJSONString(ecmInnerMessageVOS));
            return a;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public Integer insertSystemMsgByNode(EcmArtworkNodesVo ecmArtworkNodesVo, Integer templateId) {
        // 模糊 还是 指定
        EcmTemplate ecmTemplateVo = ecmTemplateDao.selectByPrimaryKey(templateId);
//        EcmTemplate ecmTemplateVo = ecmTemplateDao.selectByPrimaryKey(10);
        EcmArtwork ecmArt = ecmArtworkDao.selectByPrimaryKey(ecmArtworkNodesVo.getFkArtworkId());
        EcmUser ecmUser = ecmUserDao.selectByPrimaryKey(ecmArt.getFkUserid());
        EcmArtworkNodes ecmArtworkNodes = ecmArtworkNodesDao.selectByPrimaryKey(ecmArtworkNodesVo.getPkDetailId());
        EcmInnerMessageVO insertMsgVO = getInsertMsgVO(ecmTemplateVo.getContent(),ecmUser,ecmArt.getArtworkName(),ecmArtworkNodes.getVideoText());
        insertMsgVO.setFkTemplateId(ecmTemplateVo.getPkTemplateId());
        insertMsgVO.setTemplateName(ecmTemplateVo.getTemplateName());

        try {
            Integer a = ecmInnerMessageDao.insertSelective(insertMsgVO);
            List<EcmInnerMessageVO>  ecmInnerMessageVOS = new ArrayList<>(1);
            ecmInnerMessageVOS.add(insertMsgVO);
            HttpUtils.post(HttpUtils.sendHttpsUrl, JSON.toJSONString(ecmInnerMessageVOS));
            return a;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Integer insertViolationMsg(EcmArtworkNodesVo ecmArtworkNodesVo,  EcmReportHistroy ecmReportHistroy,Integer templateId) {

        // 指定
        EcmReportHistroy ecmReportHistroyVO = ecmReportHistroyDao.selectByPrimaryKey(ecmReportHistroy.getReportId());

        EcmTemplate ecmTemplateVo = ecmTemplateDao.selectByPrimaryKey(templateId);
//        EcmTemplate ecmTemplateVo = ecmTemplateDao.selectByPrimaryKey(10);
        EcmArtwork ecmArt = ecmArtworkDao.selectByPrimaryKey(ecmArtworkNodesVo.getFkArtworkId());
        EcmUser ecmUser = ecmUserDao.selectByPrimaryKey(ecmArt.getFkUserid());
        EcmArtworkNodes ecmArtworkNodes = ecmArtworkNodesDao.selectByPrimaryKey(ecmArtworkNodesVo.getPkDetailId());
        EcmInnerMessageVO insertMsgVO = getInsertMsgVO(ecmTemplateVo.getContent(),  ecmUser,ecmArt.getArtworkName(),ecmArtworkNodes.getVideoText(),ReportMnum.getByValue(ecmReportHistroyVO.getReportStatue()).getName());
//        insertMsgVO
//        insertMsgVO.set
//        insertMsgVO.setContent(Parser.parse("#{","}", insertMsgVO.getContent(),ReportMnum.getByValue(insertMsgVO.getReportStatue()).getName() ));

        insertMsgVO.setFkTemplateId(ecmTemplateVo.getPkTemplateId());
        insertMsgVO.setTemplateName(ecmTemplateVo.getTemplateName());

        try {
            Integer a = ecmInnerMessageDao.insertSelective(insertMsgVO);
            List<EcmInnerMessageVO>  ecmInnerMessageVOS = new ArrayList<>(1);
            ecmInnerMessageVOS.add(insertMsgVO);
            HttpUtils.post(HttpUtils.sendHttpsUrl, JSON.toJSONString(ecmInnerMessageVOS));
            return a;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 站内信 msg 所用用户 发送 无替换
    private List<EcmInnerMessageVO> msgReplace(EcmTemplate ecmTemplate , List<EcmUserVO>  list ){


        List<EcmInnerMessageVO> ecmInnerMessageVOS = new ArrayList<>();
//        content.replaceAll("{userName}","")
        for (EcmUserVO ecmUserVO : list) {
            EcmInnerMessageVO ecmInnerMessageVO = getInsertMsgVO(ecmTemplate.getContent(), ecmUserVO);
            ecmInnerMessageVO.setFkTemplateId(ecmTemplate.getPkTemplateId());
            ecmInnerMessageVO.setTemplateName(ecmTemplate.getTemplateName());
            ecmInnerMessageVOS.add(ecmInnerMessageVO);
        }
        return ecmInnerMessageVOS;

    }


    // 站内信 msg 模板替换方法
    private EcmInnerMessageVO getInsertMsgVO(String content, EcmUser ecmUserVO,String artWorkName ,String nodeName ) {

        EcmInnerMessageVO ecmInnerMessageVO = new EcmInnerMessageVO();
        // ${} 替换名字
        ecmInnerMessageVO.setContent(Parser.parse0(content, ecmUserVO.getUsername()));
        // !{} 替换作品
        ecmInnerMessageVO.setContent(Parser.parse("!{","}", ecmInnerMessageVO.getContent(),artWorkName ));
        // @{}替换节点名字
        ecmInnerMessageVO.setContent(Parser.parse("@{","}", ecmInnerMessageVO.getContent(),nodeName ));

        ecmInnerMessageVO.setMessageStatus((short) 0);
        ecmInnerMessageVO.setSendDate(new Date());
        ecmInnerMessageVO.setReviewerId((Integer) getRequstSession().getAttribute("userId"));
        ecmInnerMessageVO.setFkUserId(ecmUserVO.getPkUserId());
        return ecmInnerMessageVO;
    }

    // 站内信 msg 模板替换方法
    private EcmInnerMessageVO getInsertMsgVO(String content, EcmUser ecmUserVO,String artWorkName  ) {

        EcmInnerMessageVO ecmInnerMessageVO = new EcmInnerMessageVO();
        // ${} 替换名字
        ecmInnerMessageVO.setContent(Parser.parse0(content, ecmUserVO.getUsername()));
        // !{} 替换作品
        ecmInnerMessageVO.setContent(Parser.parse("!{","}", ecmInnerMessageVO.getContent(),artWorkName ));
        // @{}替换节点名字


        ecmInnerMessageVO.setMessageStatus((short) 0);
        ecmInnerMessageVO.setSendDate(new Date());
        ecmInnerMessageVO.setReviewerId((Integer) getRequstSession().getAttribute("userId"));
        ecmInnerMessageVO.setFkUserId(ecmUserVO.getPkUserId());
        return ecmInnerMessageVO;
    }

    // 站内信 msg 模板替换方法
    private EcmInnerMessageVO getInsertMsgVO(String content, EcmUser ecmUserVO ,String artWorkName ,String nodeName ,String stuts ){
        EcmInnerMessageVO ecmInnerMessageVO = new EcmInnerMessageVO();
        // ${} 替换名字
        ecmInnerMessageVO.setContent(Parser.parse0(content, ecmUserVO.getUsername()));
//         !{} 替换作品
        ecmInnerMessageVO.setContent(Parser.parse("!{","}", ecmInnerMessageVO.getContent(),artWorkName ));
//         @{}替换节点名字
        ecmInnerMessageVO.setContent(Parser.parse("@{","}", ecmInnerMessageVO.getContent(),nodeName ));

        // #{} 替换节点违规信息
        ecmInnerMessageVO.setContent(Parser.parse("#{","}", ecmInnerMessageVO.getContent(), stuts ));


        ecmInnerMessageVO.setMessageStatus((short) 0);
        ecmInnerMessageVO.setSendDate(new Date());
        ecmInnerMessageVO.setReviewerId((Integer) getRequstSession().getAttribute("userId"));
        ecmInnerMessageVO.setFkUserId(ecmUserVO.getPkUserId());
        return ecmInnerMessageVO;
    }

    // 站内信 msg 模板替换方法
    private EcmInnerMessageVO getInsertMsgVO(String content, EcmUser ecmUserVO ){
        EcmInnerMessageVO ecmInnerMessageVO = new EcmInnerMessageVO();
        // ${} 替换名字
        ecmInnerMessageVO.setContent(Parser.parse0(content, ecmUserVO.getUsername()));
//         !{} 替换作品
        ecmInnerMessageVO.setContent(Parser.parse("!{","}", ecmInnerMessageVO.getContent(),"artWorkName" ));
//         @{}替换节点名字
        ecmInnerMessageVO.setContent(Parser.parse("@{","}", ecmInnerMessageVO.getContent(),"nodeName" ));

        ecmInnerMessageVO.setMessageStatus((short) 0);
        ecmInnerMessageVO.setSendDate(new Date());
        ecmInnerMessageVO.setReviewerId((Integer) getRequstSession().getAttribute("userId"));
        ecmInnerMessageVO.setFkUserId(ecmUserVO.getPkUserId());
        return ecmInnerMessageVO;
    }

}
