package com.wxcz.carpenter.service.impl;

import com.wxcz.carpenter.common.ReportMnum;
import com.wxcz.carpenter.dao.EcmArtworkDao;
import com.wxcz.carpenter.dao.EcmInnerMessageDao;
import com.wxcz.carpenter.dao.EcmTemplateDao;
import com.wxcz.carpenter.dao.EcmUserDao;
import com.wxcz.carpenter.pojo.dto.PageDTO;
import com.wxcz.carpenter.pojo.dto.ResponseDTO;
import com.wxcz.carpenter.pojo.entity.*;
import com.wxcz.carpenter.pojo.query.EcmTemplateQuery;
import com.wxcz.carpenter.pojo.vo.EcmInnerMessageVO;
import com.wxcz.carpenter.pojo.vo.EcmReportHistroyVO;
import com.wxcz.carpenter.pojo.vo.EcmTemplateVo;
import com.wxcz.carpenter.pojo.vo.EcmUserVO;
import com.wxcz.carpenter.service.BaseService;
import com.wxcz.carpenter.service.EcmMessageService;
import com.wxcz.carpenter.service.EcmReportHistroyService;
import com.wxcz.carpenter.util.Parser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
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
    EcmTemplateDao ecmTemplateDao;

    @Resource
    EcmReportHistroyService ecmReportHistroyService;


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



            EcmInnerMessage ecmInnerMessage = new EcmInnerMessage();
            ecmInnerMessage.setMessageStatus((short) 0);
            ecmInnerMessage.setSendDate(new Date());
            ecmInnerMessage.setReviewerId((Integer) getRequstSession().getAttribute("userId"));
            ecmInnerMessage.setContent(ecmTemplate.getContent());
            ecmInnerMessageDao.insertMsgAll(list,ecmInnerMessage);
            //可替换的 全部消息 代码
//            List<EcmInnerMessageVO> ecmInnerMessageVOS = msgReplace(ecmTemplate.getContent(), list);
//            ecmInnerMessageDao.insertMsgPart(ecmInnerMessageVOS);
            return ResponseDTO.ok();
        } catch (Exception e){

            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

            return ResponseDTO.fail("发送错误，请重试");

        }
//        return ResponseDTO.get(1 ==  ecmInnerMessageDao.insertMsgAll(list,ecmInnerMessage) );
    }

    @Override
    @Transactional
    public ResponseDTO addMsgPart(EcmTemplateVo ecmTemplateVo) {
        EcmTemplate ecmTemplate = ecmTemplateDao.selectByPrimaryKey(ecmTemplateVo.getPkTemplateId());
        List<EcmUserVO>  list =  ecmUserDao.selectIds( Arrays.asList(ecmTemplateVo.getIds()));
        // 查出 模板
        List<EcmInnerMessageVO> ecmInnerMessageVOS = msgReplace( ecmTemplate.getContent(), list);

        try {
            ecmInnerMessageDao.insertMsgPart(ecmInnerMessageVOS);
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
        return ecmInnerMessageDao.insertSelective(insertMsgVO);


    }

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
        ecmInnerMessageVO.setContent(Parser.parse("@{","}", ecmInnerMessageVO.getContent(),ecmReportHistroyVO.getArtWorkNameNodeName() ));
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



        return ecmInnerMessageDao.insertSelective(ecmInnerMessageVO);
    }

    // 站内信 msg 所用用户 发送
    private List<EcmInnerMessageVO> msgReplace(String content ,List<EcmUserVO>  list ){


        List<EcmInnerMessageVO> ecmInnerMessageVOS = new ArrayList<>();
//        content.replaceAll("{userName}","")
        for (EcmUserVO ecmUserVO : list) {

            EcmInnerMessageVO ecmInnerMessageVO = getInsertMsgVO(content, ecmUserVO);

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
    private EcmInnerMessageVO getInsertMsgVO(String content, EcmUser ecmUserVO ){
        EcmInnerMessageVO ecmInnerMessageVO = new EcmInnerMessageVO();
        // ${} 替换名字
        ecmInnerMessageVO.setContent(Parser.parse0(content, ecmUserVO.getUsername()));
        // !{} 替换作品
//        ecmInnerMessageVO.setContent(Parser.parse("!{","}", ecmInnerMessageVO.getContent(),"artWorkName" ));
        // @{}替换节点名字
//        ecmInnerMessageVO.setContent(Parser.parse("@{","}", ecmInnerMessageVO.getContent(),"nodeName" ));

        ecmInnerMessageVO.setMessageStatus((short) 0);
        ecmInnerMessageVO.setSendDate(new Date());
        ecmInnerMessageVO.setReviewerId((Integer) getRequstSession().getAttribute("userId"));
        ecmInnerMessageVO.setFkUserId(ecmUserVO.getPkUserId());
        return ecmInnerMessageVO;
    }


}
