package com.wxcz.carpenter.service.impl;

import com.wxcz.carpenter.dao.EcmInnerMessageDao;
import com.wxcz.carpenter.dao.EcmTemplateDao;
import com.wxcz.carpenter.dao.EcmUserDao;
import com.wxcz.carpenter.pojo.dto.PageDTO;
import com.wxcz.carpenter.pojo.dto.ResponseDTO;
import com.wxcz.carpenter.pojo.entity.EcmInnerMessage;
import com.wxcz.carpenter.pojo.entity.EcmTemplate;
import com.wxcz.carpenter.pojo.query.EcmInnerMessageQuery;
import com.wxcz.carpenter.pojo.query.EcmTemplateQuery;
import com.wxcz.carpenter.pojo.vo.EcmInnerMessageVO;
import com.wxcz.carpenter.pojo.vo.EcmTemplateVo;
import com.wxcz.carpenter.pojo.vo.EcmUserVO;
import com.wxcz.carpenter.service.BaseService;
import com.wxcz.carpenter.service.EcmMessageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.TransactionManagementConfigurationSelector;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
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
    EcmTemplateDao ecmTemplateDao;

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

            return ResponseDTO.ok();
        } catch (Exception e){

            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

            return ResponseDTO.fail("发送错误，请重试");

        }
//        return ResponseDTO.get(1 ==  ecmInnerMessageDao.insertMsgAll(list,ecmInnerMessage) );
    }

    @Override
    public ResponseDTO addMsgPart(EcmTemplateVo ecmTemplateVo) {
        EcmTemplate ecmTemplate = ecmTemplateDao.selectByPrimaryKey(ecmTemplateVo.getPkTemplateId());

        List<EcmUserVO>  list =  ecmUserDao.selectIds( Arrays.asList(ecmTemplateVo.getIds()));
        // 查出 模板
        String content = ecmTemplate.getContent();
//        content.replaceAll("{userName}","")
        for (EcmUserVO ecmUserVO : list) {
            content.replaceAll("#userName#",ecmUserVO.getUsername());
        }

        return null;
    }

}
