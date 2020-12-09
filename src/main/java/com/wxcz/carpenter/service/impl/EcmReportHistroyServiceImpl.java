package com.wxcz.carpenter.service.impl;

import com.alibaba.fastjson.JSON;
import com.wxcz.carpenter.dao.*;
import com.wxcz.carpenter.pojo.dto.PageDTO;
import com.wxcz.carpenter.pojo.dto.ResponseDTO;
import com.wxcz.carpenter.pojo.entity.EcmArtwork;
import com.wxcz.carpenter.pojo.entity.EcmArtworkNodes;
import com.wxcz.carpenter.pojo.entity.EcmReportHistroy;
import com.wxcz.carpenter.pojo.entity.EcmUser;
import com.wxcz.carpenter.pojo.query.EcmArtworkQuery;
import com.wxcz.carpenter.pojo.query.ReportArtWorkQuery;
import com.wxcz.carpenter.pojo.vo.*;
import com.wxcz.carpenter.service.EcmReportHistroyService;
import com.wxcz.carpenter.util.TreeUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author by cxd
 * @Classname ReportArtWorkServiceImpl
 * @Description TODO
 * @Date 2020/8/18 16:55
 */
@Service
public class EcmReportHistroyServiceImpl implements EcmReportHistroyService {

    @Resource
    EcmReportHistroyDao ecmReportHistroyDao;

    @Resource
    EcmArtworkNodesDao ecmArtworkNodesDao;

    @Resource
    EcmArtworkDao ecmArtworkDao;

    @Resource
    EcmUserDao ecmUserDao;

    @Resource
    EcmArtworkEndingsDao ecmArtworkEndingsDao;



    @Override
    public PageDTO ajaxList(ReportArtWorkQuery reportArtWorkQuery) {

        List<EcmReportHistroyVO> list = ecmReportHistroyDao.selectAjaxList(reportArtWorkQuery);
        Integer count =  ecmReportHistroyDao.selectAjaxCount(reportArtWorkQuery);


        //通过 作品list 查询对用的审核人list
        if (!CollectionUtils.isEmpty(list)) {
            //查询处理人集合
            List<EcmUserVO> lists = ecmUserDao.selectByReportList(list);
            //查询用户 名字
            List<EcmUserVO> ecmUserVOS = ecmUserDao.selectUserNameByList(list);
            //查询作品名字
            List<EcmArtworkVO> ecmArtworkVOList = ecmArtworkDao.selectByReportList(list);

            // 遍历 赋值 作品VO中的审核人名字
            list.forEach(ecmReportHistroyVO -> {

                lists.forEach(ecmUserVO -> {
                    if (ecmReportHistroyVO.getFkChUserid() != null) {
                        if (ecmUserVO.getPkUserId().equals(ecmReportHistroyVO.getFkChUserid())) {
                            ecmReportHistroyVO.setFkChName(ecmUserVO.getUsername());
                        }
                    }
                });
                ecmUserVOS.forEach( ecmUserVO -> {

                    if (ecmReportHistroyVO.getFkUserid() != null ) {
                        if (ecmUserVO.getPkUserId().equals(ecmReportHistroyVO.getFkUserid())) {
                            ecmReportHistroyVO.setUsername(ecmUserVO.getUsername());
                        }

                    }

                });
                ecmArtworkVOList.forEach( ecmArtworkVO -> {
                    if (ecmReportHistroyVO.getFkArtworkId()!=null){
                        if (ecmArtworkVO.getPkArtworkId().equals(ecmReportHistroyVO.getFkArtworkId())){
                            ecmReportHistroyVO.setArtWorkName(ecmArtworkVO.getArtworkName());
                        }
                    }

                });

            });
        }
        return PageDTO.setPageData(count,list);
    }

    @Override
    public ResponseDTO artWorkAudit(EcmReportHistroy ecmReportHistroy) {
        //通过作品id查询作品
        EcmReportHistroy ecmReport = ecmReportHistroyDao.selectByPrimaryKey(ecmReportHistroy.getReportId());
        // 判断是否有 审核人
        if (!StringUtils.isEmpty(ecmReport.getFkChUserid())) {
            // 比对用户是否一致
            if (ecmReport.getFkChUserid().equals(ecmReportHistroy.getFkChUserid())) {
                return ResponseDTO.ok();
            }
        }else {
            // 没有审核人 ，当前用户做为审核人  需要修改，在前端 有 节点详情的时候需修改 单个节点的信息
            // ecmArtworkNodesDao.updateByReportHistroy(ecmReportHistroy.getFkArtworkId());
            // 单个节点的信息
            //ecmArtworkNodesDao.updateByReportHistroyNode(ecmReportHistroy.getFkArtworkNodeId());

            return ResponseDTO.get(1 == ecmReportHistroyDao.updateByPrimaryKeySelective(ecmReportHistroy));
        }
        return ResponseDTO.fail("error");
    }

    @Override
    public ResponseDTO getArtWorkNoteS(EcmArtworkQuery ecmArtworkVO) {
        //判断 作品表中是否有这个作品
        EcmArtwork ecmArtwork = ecmArtworkDao.selectByPrimaryKey(ecmArtworkVO.getPkArtworkId());
        if (ecmArtwork == null) {
            return ResponseDTO.fail("查询id为空");
        }
        List<EcmArtworkNodesVo> list = ecmArtworkNodesDao.selectByArtWorkId(ecmArtworkVO.getPkArtworkId());
//        List<EcmArtworkNodesVo> y = list.stream().filter(ecmArtworkNodesVo -> {
//            if (ecmArtworkNodesVo.getIsDeleted() != null){
//                return !ecmArtworkNodesVo.getIsDeleted().equals("Y");
//            }
//            return true;
//        }).collect(Collectors.toList());
        List<EcmArtworkNodesVo> y = list.stream().filter(ecmArtworkNodesVo -> !"Y".equals(ecmArtworkNodesVo.getIsDeleted())).collect(Collectors.toList());

        List<EcmReportHistroyVO> ecmReportHistroyVOList = ecmReportHistroyDao.selectByArtWorkId(ecmArtworkVO.getPkArtworkId());
        // 优化到chair举报
        List<EcmArtworkNodesVo> ecmArtworkNodesVos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(ecmReportHistroyVOList)) {
            y.forEach( v -> {
                ecmReportHistroyVOList.forEach( ecmReportHistroyVO -> {
                   if (ecmReportHistroyVO.getFkArtworkNodeId().equals(v.getPkDetailId())) {
                       EcmArtworkNodesVo ecmArtworkNodesVo = new EcmArtworkNodesVo();
                       v.setFkEndingId(6);
                       v.setContent(ecmReportHistroyVO.getContent());
                       v.setReportStatue(ecmReportHistroyVO.getReportStatue());
                       v.setReportImgUrl(ecmReportHistroyVO.getImgUrl());
                       v.setIsReport(1);
                       ecmArtworkNodesVo.setFkEndingId(6);
                       ecmArtworkNodesVo.setPkDetailId(v.getPkDetailId());
                       ecmArtworkNodesVos.add(ecmArtworkNodesVo);
                   }
                });
            });
        }

        EcmReportHistroy ecmReportHistroy = ecmReportHistroyDao.selectByPrimaryKey(ecmArtworkVO.getReportId());
        // 更新 节点 表 中  节点的投诉举报数据
        if (!CollectionUtils.isEmpty(ecmArtworkNodesVos)) {
            ecmArtworkNodesDao.updateByAtrworkNodes(ecmArtworkNodesVos);
        }
        //是否为多结局 作品
//        if (ecmArtwork.getIsEndings()!=null) {
//            if (ecmArtwork.getIsEndings().equals(1)){
//                List<EcmArtworkEndingsVO> ecmArtworkEndingsVOList = ecmArtworkEndingsDao.selectByArtwId(ecmArtwork.getPkArtworkId());
//                if (!CollectionUtils.isEmpty(ecmReportHistroyVOList)){
//                    ecmArtworkEndingsVOList.forEach( v -> {
//                        EcmArtworkNodesVo ecmArtworkNodesVo = new EcmArtworkNodesVo();
////                        ecmArtworkNodesVo.set
//
//
//                    });
//                }
//            }
//        }

        Map map = new HashMap(2);
        map.put("artWork",y);
        map.put("reportHistroy",ecmReportHistroy);
        return ResponseDTO.ok("success", map);

    }

    @Override
    public  List<EcmReportHistroyVO> getReportIdByArtWorkId(Integer pkArtworkId) {
        return ecmReportHistroyDao.selectByArtWorkId(pkArtworkId) ;
    }

    @Override
    public EcmReportHistroyVO getReportHistoryVOByEcmReportHistroy(EcmReportHistroy ecmReportHistroy) {

//        EcmUser ecmUser = ecmUserDao.selectByPrimaryKey(ecmReportHistroy.getFkUserid());

        EcmArtwork ecmArtwork = ecmArtworkDao.selectByPrimaryKey(ecmReportHistroy.getFkArtworkId());

        EcmArtworkNodes ecmArtworkNodesVos = ecmArtworkNodesDao.selectByPrimaryKey(ecmReportHistroy.getFkArtworkNodeId());

        EcmReportHistroyVO ecmReportHistroyVO = (EcmReportHistroyVO) ecmReportHistroy;

//        ecmReportHistroyVO.setUsername(ecmUser.getUsername());
        ecmReportHistroyVO.setArtWorkNameNodeName(ecmArtworkNodesVos.getVideoText());
        ecmReportHistroyVO.setArtWorkName(ecmArtwork.getArtworkName());


        return ecmReportHistroyVO;
    }


}
