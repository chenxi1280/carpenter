package com.wxcz.carpenter.service.impl;

import com.wxcz.carpenter.dao.*;
import com.wxcz.carpenter.pojo.dto.EcmArtworkVersionInfoDTO;
import com.wxcz.carpenter.pojo.dto.PageDTO;
import com.wxcz.carpenter.pojo.dto.ResponseDTO;
import com.wxcz.carpenter.pojo.entity.*;
import com.wxcz.carpenter.pojo.query.EcmArtworkQuery;
import com.wxcz.carpenter.pojo.query.EcmArtworkVersionInfoQuery;
import com.wxcz.carpenter.pojo.vo.*;
import com.wxcz.carpenter.service.BaseService;
import com.wxcz.carpenter.service.EcmArtworkService;
import com.wxcz.carpenter.service.EcmMessageService;
import com.wxcz.carpenter.util.PageUtil;
import com.wxcz.carpenter.util.TreeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author by cxd
 * @Classname EcmArtworkServiceImpl
 * @Description TODO
 * @Date 2020/8/7 10:53
 */
@Service
public class EcmArtworkServiceImpl implements EcmArtworkService, BaseService {

    @Resource
    EcmArtworkDao ecmArtworkDao;

    @Resource
    EcmUserDao ecmUserDao;

    @Resource
    EcmReportHistroyDao ecmReportHistroyDao;

    @Resource
    EcmArtworkNodesDao ecmArtworkNodesDao;

    @Resource
    EcmMessageService ecmMessageService;

    @Resource
    EcmArtworkBroadcastHotDao ecmArtworkBroadcastHotDao;

    @Resource
    EcmArtworkVersionInfoDao ecmArtworkVersionInfoDao;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO addEcmArtworkHot() {
       List<EcmArtworkBroadcastHotVO>  list = ecmArtworkBroadcastHotDao.selectAll();
       for (EcmArtworkBroadcastHotVO ecmArtworkBroadcastHotVO : list) {
            if ( ecmArtworkBroadcastHotVO != null  ){
                if (ecmArtworkBroadcastHotVO.getBroadcastCount().equals(0)) {
                    ecmArtworkBroadcastHotVO.setBroadcastCount(12);
                }
                int count = ecmArtworkBroadcastHotVO.getBroadcastCount() * 7  +  new Random().nextInt(100);
                ecmArtworkBroadcastHotVO.setBroadcastCount(count);
                ecmArtworkBroadcastHotVO.setWaitCount(count);
            }
       }
       try{
           ecmArtworkBroadcastHotDao.updateByNewBroadcastHot(list);
       }catch (Exception e){
           e.printStackTrace();
           return ResponseDTO.fail();
       }

        return ResponseDTO.ok();
    }

    @Override
    public PageDTO ajaxList(EcmArtworkQuery ecmArtworkQuery) {

        List<EcmArtworkVO> list = ecmArtworkDao.selectajaxListByQuery(ecmArtworkQuery);
//        List<EcmArtworkVO>  list = ecmArtworkDao.selectajaxList(ecmArtworkQuery);
        //?????? ??????list ????????????????????????list
        if (!CollectionUtils.isEmpty(list)) {
            List<EcmUserVO> listVOs = ecmUserDao.selectUserNameByList(list);
            List<EcmUserVO> lists = ecmUserDao.selectByList(list);
            // ?????? ?????? ??????VO?????????????????????
            list.forEach(ecmArtworkVO -> {
                lists.forEach(ecmUserVO -> {
                    if (ecmArtworkVO.getFkAuditId() != null) {
                        if (ecmUserVO.getPkUserId().equals(ecmArtworkVO.getFkAuditId())) {
                            ecmArtworkVO.setFkAuditName(ecmUserVO.getUsername());
                        }
                    }
                });
                listVOs.forEach(ecmUserVO -> {

                    if (ecmArtworkVO.getFkUserid() != null) {
                        if (ecmUserVO.getPkUserId().equals(ecmArtworkVO.getFkUserid())) {
                            ecmArtworkVO.setUsername(ecmUserVO.getUsername());
                        }

                    }

                });

            });
        }


        Integer count = ecmArtworkDao.selectCountByQuery(ecmArtworkQuery);
        return PageDTO.setPageData(count, list);
    }

    @Override
    public ResponseDTO chengArtWork(EcmArtworkVO ecmArtworkVO) {
        // ????????????
        ecmArtworkVO.setLastModifyDate(new Date());
        // ????????? ????????? ?????? ?????????
        if (ecmArtworkVO.getArtworkStatus() != null) {
            if (ecmArtworkVO.getArtworkStatus() == 1) {
                return ResponseDTO.get(1 == ecmArtworkDao.updateByPrimaryKeyFail(ecmArtworkVO));
            }
        }
        if (ecmArtworkVO.getArtworkStatus() == 4){
            EcmArtworkBroadcastHotVO ecmArtworkBroadcastHotVO = ecmArtworkBroadcastHotDao.selectByArtworkId(ecmArtworkVO.getPkArtworkId());
            if ( ecmArtworkBroadcastHotVO == null){
                ecmArtworkBroadcastHotVO  = new EcmArtworkBroadcastHotVO();
                ecmArtworkBroadcastHotVO.setWaitCount(0);
                ecmArtworkBroadcastHotVO.setBroadcastCount(0);
                ecmArtworkBroadcastHotVO.setFkArkworkId(ecmArtworkVO.getPkArtworkId());
                ecmArtworkBroadcastHotDao.insertSelective(ecmArtworkBroadcastHotVO);
            }
        }

        return ResponseDTO.get(ecmArtworkDao.updateByPrimaryKeySelective(ecmArtworkVO) == 1);
    }

    @Override
    public ResponseDTO updateArtWorkPlayClient(EcmArtworkVO ecmArtworkVO) {
        if (  ecmArtworkVO.getPlayClient() == null ) {
            return ResponseDTO.get(ecmArtworkDao.updateArtWorkPlayClient(ecmArtworkVO) == 1);
        }else {
            return ResponseDTO.get(ecmArtworkDao.updateByPrimaryKeySelective(ecmArtworkVO) == 1);
        }

    }

    @Override
    public PageDTO ajaxCheckList(EcmArtworkQuery ecmArtworkQuery) {
        // ??????????????? ???????????? ?????? ?????? username ??? ????????????????????????  ?????????????????????
        List<EcmArtworkVO> list = ecmArtworkDao.selectajaxCheckList(ecmArtworkQuery);
        //?????? ??????list ????????????????????????list
        if (!CollectionUtils.isEmpty(list)) {
            List<EcmUserVO> listVOs = ecmUserDao.selectUserNameByList(list);
            List<EcmUserVO> lists = ecmUserDao.selectByList(list);
            // ?????? ?????? ??????VO?????????????????????
            list.forEach(ecmArtworkVO -> {
                lists.forEach(ecmUserVO -> {
                    if (ecmArtworkVO.getFkAuditId() != null) {
                        if (ecmUserVO.getPkUserId().equals(ecmArtworkVO.getFkAuditId())) {
                            ecmArtworkVO.setFkAuditName(ecmUserVO.getUsername());
                        }
                    }
                });
                listVOs.forEach(ecmUserVO -> {
                    if (ecmArtworkVO.getFkUserid() != null) {
                        if (ecmUserVO.getPkUserId().equals(ecmArtworkVO.getFkUserid())) {
                            ecmArtworkVO.setUsername(ecmUserVO.getUsername());
                        }

                    }

                });

            });
        }
        Integer count = ecmArtworkDao.selectCountByCheckList(ecmArtworkQuery);
        return PageDTO.setPageData(count, list);
    }

    @Override
    public ResponseDTO getArtWorkNoteS(EcmArtworkQuery ecmArtworkVO) {

        EcmArtwork ecmArtwork = ecmArtworkDao.selectByPrimaryKey(ecmArtworkVO.getPkArtworkId());
        if (ecmArtwork == null) {
            return ResponseDTO.fail("??????id??????");
        }
        List<EcmArtworkNodesVo> list = ecmArtworkNodesDao.selectByArtWorkId(ecmArtworkVO.getPkArtworkId());
//        List<EcmArtworkNodesVo> y = list.stream().filter(ecmArtworkNodesVo -> ecmArtworkNodesVo.getIsDeleted().equals("Y")).collect(Collectors.toList());
        List<EcmArtworkNodesVo> collect = list.stream().filter(ecmArtworkNodesVo -> !"Y".equals(ecmArtworkNodesVo.getIsDeleted())).collect(Collectors.toList());
        return ResponseDTO.ok("success", TreeUtil.buildTree(collect).get(0));
    }

    @Override
    public ResponseDTO upDataNode(EcmArtworkNodes ecmArtworkNodes) {
        return ResponseDTO.get(ecmArtworkNodesDao.updateByPrimaryKeySelective(ecmArtworkNodes) == 1);
    }


    @Override
    @Transactional
    public ResponseDTO checkArtWork(EcmArtworkQuery ecmArtworkQuery) {

        Integer userId = (Integer) getRequstSession().getAttribute("userId");
        EcmArtwork artwork = ecmArtworkDao.selectByPrimaryKey(ecmArtworkQuery.getPkArtworkId());
        EcmArtwork ecmArtwork = new EcmArtwork();
        if (artwork.getFkAuditId() == null){
            ecmArtwork.setFkAuditId(userId);
        }
        if (!userId.equals(artwork.getFkAuditId()) &&artwork.getFkAuditId() != null ){
            return ResponseDTO.fail("?????????");
        }
        if ( artwork.getLogoPathStatus()  == null) {
            return ResponseDTO.fail("???????????????");
        }

        if (artwork.getLogoPathStatus() == 0) {
            return ResponseDTO.fail("???????????????");
        }
        // ??????????????? ????????????
        List<EcmArtworkNodesVo> ecmArtworkNodesVos = ecmArtworkNodesDao.selectByArtWorkId(ecmArtworkQuery.getPkArtworkId());
        List<EcmArtworkNodesVo> collect = ecmArtworkNodesVos.stream().filter(ecmArtworkNodesVo -> !"Y".equals(ecmArtworkNodesVo.getIsDeleted())).collect(Collectors.toList());
//
//        Set<Integer> ids = new HashSet<>();
//        Iterator<EcmArtworkNodesVo> iterator = collect.iterator();
//
//        ecmArtworkNodesVos.forEach( ecmArtworkNodesVo ->  {
//            ids.add(ecmArtworkNodesVo.getPkDetailId());
//            getRemoveNodeId(ecmArtworkNodesVo,ecmArtworkNodesVos,ids);
//        });
//
//        while (iterator.hasNext()) {
//            EcmArtworkNodesVo ecmArtworkNodesVo = iterator.next();
//            ids.forEach( e -> {
//                if ( e.equals(ecmArtworkNodesVo.getPkDetailId())){
//                    iterator.remove();
//                }
//            });
//        }
//


        // ???????????????????????????????????????
        ecmArtwork.setPkArtworkId(ecmArtworkQuery.getPkArtworkId());
        ecmArtwork.setLastModifyDate(new Date());
        // ???????????? ?????? ????????? ??????
        for (EcmArtworkNodesVo ecmArtworkNodesVo : collect) {
            if (ecmArtworkNodesVo.getFkEndingId() == null) {
                return ResponseDTO.fail("????????????????????????");
            } else if (ecmArtworkNodesVo.getFkEndingId() == 2) {
                return ResponseDTO.fail("????????????????????????");
            } else if (ecmArtworkNodesVo.getFkEndingId() == 3) {
                return ResponseDTO.fail("????????????????????????");
            } else if (ecmArtworkNodesVo.getFkEndingId() == 6) {
                return ResponseDTO.fail("??????????????????????????????");
            }
        }
        try {
            if (artwork.getLogoPathStatus() == 2) {
                // ??????????????? ?????????
                ecmMessageService.insertSystemMsg(ecmArtwork, "???????????????");
                // ??????????????????
                ecmArtwork.setArtworkStatus((short) 0);
                ecmArtwork.setLastModifyDate(new Date());
                ecmArtworkNodesDao.updateLinkNodeByFailCheckArtwork(ecmArtwork.getPkArtworkId());
                int i =  ecmArtworkDao.updateByPrimaryKeySelective(ecmArtwork);

                return ResponseDTO.get(1 == i , "???????????????,??????????????????");
            }



            // ???????????? ?????? ????????? ??????
            for (EcmArtworkNodesVo ecmArtworkNodesVo : collect) {
                // ???????????? ?????? ????????? ??????
                if (ecmArtworkNodesVo.getFkEndingId() == 5) {

                    ecmMessageService.insertSystemMsgByNode(ecmArtworkNodesVo, 2);
                    // ??????????????????
                    ecmArtwork.setArtworkStatus((short) 0);
                    ecmArtwork.setLastModifyDate(new Date());
                    ecmArtworkNodesDao.updateLinkNodeByFailCheckArtwork(ecmArtwork.getPkArtworkId());
                    int i =  ecmArtworkDao.updateByPrimaryKeySelective(ecmArtwork);

                    // ??????????????? ?????????

                    return ResponseDTO.get(1 == i, "???????????????,??????????????????");
                }
            }
            ecmMessageService.insertSystemMsg(ecmArtwork, "??????????????????");

            // ?????? ?????????  ?????? ??????
            ecmArtwork.setArtworkStatus((short) 2);
            // ??????????????????
            return ResponseDTO.get(1 == ecmArtworkDao.updateByPrimaryKeySelective(ecmArtwork), "???????????????");

        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponseDTO.fail("????????????");
        }

    }
    private void getRemoveNodeId(EcmArtworkNodesVo ecmArtworkNodesVo, List<EcmArtworkNodesVo> ecmArtworkNodesVoList,  Set<Integer> ids) {
        ecmArtworkNodesVoList.forEach(v -> {
            if (v.getParentId().equals(ecmArtworkNodesVo.getPkDetailId())) {
                ids.add(v.getPkDetailId());
                getRemoveNodeId(v,ecmArtworkNodesVoList,ids);
            }
        });
    }

    @Override
    public ResponseDTO reCheckArtWork(EcmArtworkQuery ecmArtworkQuery) {
        Integer userId = (Integer) getRequstSession().getAttribute("userId");
        // ?????????
        EcmReportHistroyVO ecmReportHistroyVO = ecmReportHistroyDao.selectByArtWorkId(ecmArtworkQuery.getPkArtworkId()).get(0);

        EcmArtwork ecmArtwork = new EcmArtwork();

        if (ecmReportHistroyVO.getFkChUserid() == null){
            ecmReportHistroyVO.setFkChUserid(userId);
        }
        if (!userId.equals(ecmReportHistroyVO.getFkChUserid()) && ecmReportHistroyVO.getFkChUserid() != null ){
            return ResponseDTO.fail("?????????");
        }

        // ??????????????? ????????????
        List<EcmArtworkNodesVo> ecmArtworkNodesVos = ecmArtworkNodesDao.selectByArtWorkId(ecmArtworkQuery.getPkArtworkId());
        List<EcmArtworkNodesVo> collect = ecmArtworkNodesVos.stream().filter(ecmArtworkNodesVo -> !"Y".equals(ecmArtworkNodesVo.getIsDeleted())).collect(Collectors.toList());
        // ???????????????????????????????????????
        ecmArtwork.setPkArtworkId(ecmArtworkQuery.getPkArtworkId());
        ecmArtwork.setLastModifyDate(new Date());
        // ???????????? ?????? ????????? ??????
        for (EcmArtworkNodesVo ecmArtworkNodesVo : collect) {
            if (ecmArtworkNodesVo.getFkEndingId() == null) {
                return ResponseDTO.fail("????????????????????????");
            } else if (ecmArtworkNodesVo.getFkEndingId() == 2) {
                return ResponseDTO.fail("????????????????????????");
            } else if (ecmArtworkNodesVo.getFkEndingId() == 3) {
                return ResponseDTO.fail("????????????????????????");
            } else if (ecmArtworkNodesVo.getFkEndingId() == 6) {
                return ResponseDTO.fail("??????????????????????????????");
            }
        }

        EcmReportHistroy ecmReportHistroy = new EcmReportHistroy();
        ecmReportHistroy.setReportId(ecmReportHistroyVO.getReportId());
        ecmReportHistroy.setUpdataTime(new Date());
        ecmReportHistroy.setReState((short) 2);
        ecmReportHistroy.setFkChUserid(userId);
        ecmReportHistroy.setFkArtworkId(ecmReportHistroyVO.getFkArtworkId());
        try {

            // ???????????? ?????? ????????? ??????
            for (EcmArtworkNodesVo ecmArtworkNodesVo : collect) {
                // ???????????? ?????? ????????? ??????
                if (ecmArtworkNodesVo.getFkEndingId() == 5) {
                    // ??????????????????
                    ecmArtwork.setArtworkStatus((short) 0);
                    // ??????????????? ?????? ??????
                    // ??????????????????
//                    ecmReportHistroyDao.updateStateSuccessByPrimaryKey(ecmReportHistroyVO.getReportId());
                    ecmReportHistroyDao.updateByPrimaryKeySelective(ecmReportHistroy);

//                    ecmMessageService.insertViolationMsg(ecmReportHistroy, "??????????????????");
                    ecmMessageService.insertViolationMsg(ecmArtworkNodesVo, ecmReportHistroy,12);
                    // ?????? ?????????  ?????? ??????
                    ecmArtwork.setLastModifyDate(new Date());
                    ecmArtwork.setFkAuditId(userId);
//                    ecmArtworkNodesDao.updateLinkNodeByFailCheckArtwork(ecmArtwork.getPkArtworkId());
                    return ResponseDTO.get(1 == ecmArtworkDao.updateByPrimaryKeySelective(ecmArtwork), "??????????????????,??????????????????");
                }
            }

            // ??????id ???????????? ??? ??????
//            ecmReportHistroyDao.updateStateSuccessByPrimaryKey(ecmReportHistroyVO.getReportId());
            ecmReportHistroyDao.updateReportHistroySByAtrworkId(ecmReportHistroy.getFkArtworkId());
//            ecmReportHistroyDao.updateByPrimaryKeySelective(ecmReportHistroy);
            // ??????????????????

            return ResponseDTO.ok("???????????????????????????");

        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponseDTO.fail("????????????");
        }
    }

    @Override
    public ResponseDTO chArtWorkImg(EcmArtworkVO ecmArtworkVO) {
        EcmArtwork ecmArtwork = ecmArtworkDao.selectByPrimaryKey(ecmArtworkVO.getPkArtworkId());
        Integer userId = (Integer) getRequstSession().getAttribute("userId");
        if (ecmArtwork != null){
            if (ecmArtwork.getFkAuditId() != null  &&  !userId.equals(ecmArtwork.getFkAuditId())){
                return ResponseDTO.ok("?????????");
            }else {
                ecmArtworkVO.setFkAuditId(userId);
            }
        }

        return ResponseDTO.get(1 == ecmArtworkDao.updateByPrimaryKeySelective(ecmArtworkVO));
    }

    @Override
    public ResponseDTO chengArtWorkReport(EcmArtworkVO ecmArtworkVO) {
        // ????????????
        EcmReportHistroyVO ecmReportHistroyVO = ecmReportHistroyDao.selectByArtWorkId(ecmArtworkVO.getPkArtworkId()).get(0);
        if (ecmReportHistroyVO == null ){
            EcmReportHistroy ecmReportHistroy = new EcmReportHistroy();
            ecmReportHistroy.setReState((short) 1);
            ecmReportHistroy.setFkArtworkId(ecmArtworkVO.getPkArtworkId());
            ecmReportHistroy.setContent("??????");
            ecmReportHistroy.setCreatetime( new Date());
//            ecmReportHistroy.setFkChUserid(ecmArtworkVO.getFkUserid());
            ecmReportHistroy.setFkArtworkNodeId(1);
            ecmReportHistroy.setReportStatue((short) 1);
            ecmReportHistroy.setFkUserid(ecmArtworkVO.getFkUserid());

            return ResponseDTO.get(1 == ecmReportHistroyDao.insertSelective(ecmReportHistroy));
        }

        return ResponseDTO.get(ecmReportHistroyDao.updateStateSuccessByArtWorkId(ecmArtworkVO.getPkArtworkId()) == 1);
    }




    @Override
    public ResponseDTO upDataArtWork(EcmArtwork ecmArtwork) {
        return ResponseDTO.get(1 == ecmArtworkDao.updateByPrimaryKeySelective(ecmArtwork));
    }

    @Override
    public ResponseDTO artWorkAudit(EcmArtwork ecmArtwork) {
        //????????????id????????????
        EcmArtwork artwork = ecmArtworkDao.selectByPrimaryKey(ecmArtwork.getPkArtworkId());
        // ??????????????? ?????????
        if (!StringUtils.isEmpty(artwork.getFkAuditId())) {
            // ????????????????????????
            if (ecmArtwork.getFkAuditId().equals(artwork.getFkAuditId())) {
                return ResponseDTO.ok();
            }
        } else {
            // ??????????????? ??????????????????????????????
            return upDataArtWork(ecmArtwork);
        }
        return ResponseDTO.fail("error");
    }

    @Override
    public PageDTO ajaxArtworkVersionList(EcmArtworkVersionInfoQuery ecmArtworkVersionInfoQuery) {
        List<EcmArtworkVersionInfoVO> list = ecmArtworkVersionInfoDao.selectListByEcmArtworkVersionInfoQuery(ecmArtworkVersionInfoQuery);
        Map<String, List<EcmArtworkVersionInfoVO>> collect = list.stream().sorted(Comparator.comparingInt(EcmArtworkVersionInfo::getPkId)).collect(Collectors.groupingBy(EcmArtworkVersionInfoVO::getVersionId));
        List<EcmArtworkVersionInfoDTO> ecmArtworkVersionInfoDTOArrayList = new ArrayList<>();
        collect.forEach( (k,v) -> {
            EcmArtworkVersionInfoDTO ecmArtworkVersionInfoDTO = new EcmArtworkVersionInfoDTO();
            BeanUtils.copyProperties(v.get(v.size() - 1 ),ecmArtworkVersionInfoDTO);
            ecmArtworkVersionInfoDTOArrayList.add(ecmArtworkVersionInfoDTO);
        });
        Integer count = collect.size();
        return PageDTO.setPageData(count, PageUtil.startPage(ecmArtworkVersionInfoDTOArrayList, ecmArtworkVersionInfoQuery.getPage(), ecmArtworkVersionInfoQuery.getLimit()).stream().sorted(Comparator.comparingInt(EcmArtworkVersionInfoDTO::getPkId)).collect(Collectors.toList()));
    }

    @Override
    public ResponseDTO addArtWorkVersion(EcmArtworkVersionInfoVO ecmArtworkVersionInfoVO) {
        ecmArtworkVersionInfoVO.setCreateTime(new Date());
        ecmArtworkVersionInfoVO.setFkArtworkId(1);
        return ResponseDTO.get( 1 == ecmArtworkVersionInfoDao.insertSelective(ecmArtworkVersionInfoVO));
    }

    @Override
    public PageDTO ajaxVersionList(EcmArtworkQuery ecmArtworkQuery) {
        List<EcmArtworkVO> list = ecmArtworkDao.selectajaxListByQuery(ecmArtworkQuery);
        Integer count = ecmArtworkDao.selectCountByQuery(ecmArtworkQuery);
        List<EcmArtworkVersionInfoVO> ecmArtworkVersionInfoList = ecmArtworkVersionInfoDao.selectListByEcmArtworkVersionId(ecmArtworkQuery.getVersionId());
        list.forEach( ecmArtworkVO ->  {
            if (!CollectionUtils.isEmpty(ecmArtworkVersionInfoList)){
                ecmArtworkVersionInfoList.forEach( ecmArtworkVersionInfoVO ->  {
                    if (ecmArtworkVO.getPkArtworkId().equals(ecmArtworkVersionInfoVO.getFkArtworkId())){
                        ecmArtworkVO.setChecked(true);
                        ecmArtworkVO.setIsChecked(true);
                    }
                });
            }
        });

        return PageDTO.setPageData(count, list);
    }

    @Override
    public ResponseDTO addArtWorkVersionList(EcmArtworkVersionInfoVO ecmArtworkVersionInfoVO) {


        EcmArtworkVersionInfo ecmArtworkVersionInfo = ecmArtworkVersionInfoDao.selectOneByVersionId(ecmArtworkVersionInfoVO.getVersionId());
        ecmArtworkVersionInfo.setUpdateTime(new Date());
        if (!CollectionUtils.isEmpty(ecmArtworkVersionInfoVO.getUnFkArtworkIdList())){
            ecmArtworkVersionInfoDao.deleteByEcmArtworkVersionList( ecmArtworkVersionInfoVO.getVersionId(), ecmArtworkVersionInfoVO.getUnFkArtworkIdList());
        }
        if (!CollectionUtils.isEmpty(ecmArtworkVersionInfoVO.getFkArtworkIdList())){
            List<EcmArtworkVersionInfoVO> artworkVersionInfoVOList = ecmArtworkVersionInfoDao.selectListByEcmArtworkIdList(ecmArtworkVersionInfoVO.getVersionId(),ecmArtworkVersionInfoVO.getFkArtworkIdList());
            if (!CollectionUtils.isEmpty(artworkVersionInfoVOList)) {
                List<Integer> fkArtworkIdList = ecmArtworkVersionInfoVO.getFkArtworkIdList();
                Iterator<Integer> iterator = fkArtworkIdList.iterator();
                while (iterator.hasNext()) {
                    Integer next = iterator.next();
                    artworkVersionInfoVOList.forEach(  e -> {
                        if (next.equals(e.getFkArtworkId())) {
                            iterator.remove();
                        }
                    });
                }
                if (!CollectionUtils.isEmpty(fkArtworkIdList)){
                    ecmArtworkVersionInfoDao.insertArtWorkVersionList(ecmArtworkVersionInfo,fkArtworkIdList);

                }
            }else {
                ecmArtworkVersionInfoDao.insertArtWorkVersionList(ecmArtworkVersionInfo,ecmArtworkVersionInfoVO.getFkArtworkIdList());
            }

        }

        return ResponseDTO.ok("success");
    }


}
