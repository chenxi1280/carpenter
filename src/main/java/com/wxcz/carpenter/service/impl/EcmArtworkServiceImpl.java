package com.wxcz.carpenter.service.impl;

import com.wxcz.carpenter.dao.*;
import com.wxcz.carpenter.pojo.dto.EcmArtworkVersionInfoDTO;
import com.wxcz.carpenter.pojo.dto.PageDTO;
import com.wxcz.carpenter.pojo.dto.ResponseDTO;
import com.wxcz.carpenter.pojo.entity.*;
import com.wxcz.carpenter.pojo.query.EcmArtworkQuery;
import com.wxcz.carpenter.pojo.query.EcmArtworkFreeAdQuery;
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

    @Resource
    EcmArtworkFreeAdDao ecmArtworkFreeAdDao;

    @Resource
    StatisticsPlayRecordDao statisticsPlayRecordDao;

    @Resource
    EcmUserCloudDao ecmUserCloudDao;

    @Resource
    EcmDownlinkFlowDao ecmDownlinkFlowDao;

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
        //通过 作品list 查询对用的审核人list
        if (!CollectionUtils.isEmpty(list)) {
            List<EcmUserVO> lists = ecmUserDao.selectByList(list);
            // 遍历 赋值 作品VO中的审核人名字
            list.forEach(ecmArtworkVO -> {
                lists.forEach(ecmUserVO -> {
                    if (ecmArtworkVO.getFkAuditId() != null) {
                        if (ecmUserVO.getPkUserId().equals(ecmArtworkVO.getFkAuditId())) {
                            ecmArtworkVO.setFkAuditName(ecmUserVO.getUsername());
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
        // 更新时间
        ecmArtworkVO.setLastModifyDate(new Date());
        // 还原成 待审核 清空 审核人
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
        // 这里是一个 链接查询 返回 带有 username 的 未审核的作品集合  按修改时间排序
        List<EcmArtworkVO> list = ecmArtworkDao.selectajaxCheckList(ecmArtworkQuery);
        //通过 作品list 查询对用的审核人list
        if (!CollectionUtils.isEmpty(list)) {
            List<EcmUserVO> listVOs = ecmUserDao.selectUserNameByList(list);
            List<EcmUserVO> lists = ecmUserDao.selectByList(list);
            // 遍历 赋值 作品VO中的审核人名字
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
            return ResponseDTO.fail("查询id为空");
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
            return ResponseDTO.fail("无权限");
        }
        if ( artwork.getLogoPathStatus()  == null) {
            return ResponseDTO.fail("图片未审核");
        }

        if (artwork.getLogoPathStatus() == 0) {
            return ResponseDTO.fail("图片未审核");
        }
        // 查询作品的 所有节点
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


        // 先创建一个需要修个作品对象
        ecmArtwork.setPkArtworkId(ecmArtworkQuery.getPkArtworkId());
        ecmArtwork.setLastModifyDate(new Date());
        // 循环遍历 比对 作品的 状态
        for (EcmArtworkNodesVo ecmArtworkNodesVo : collect) {
            if (ecmArtworkNodesVo.getFkEndingId() == null) {
                return ResponseDTO.fail("作品有节点未审核");
            } else if (ecmArtworkNodesVo.getFkEndingId() == 2) {
                return ResponseDTO.fail("作品有节点未审核");
            } else if (ecmArtworkNodesVo.getFkEndingId() == 3) {
                return ResponseDTO.fail("作品有节点未审核");
            } else if (ecmArtworkNodesVo.getFkEndingId() == 6) {
                return ResponseDTO.fail("作品有举报节点未处理");
            }
        }
        try {
            if (artwork.getLogoPathStatus() == 2) {
                // 发送站内信 不通过
                ecmMessageService.insertSystemMsg(ecmArtwork, "封面未通过");
                // 设置作品状态
                ecmArtwork.setArtworkStatus((short) 0);
                ecmArtwork.setLastModifyDate(new Date());
                ecmArtworkNodesDao.updateLinkNodeByFailCheckArtwork(ecmArtwork.getPkArtworkId());
                int i =  ecmArtworkDao.updateByPrimaryKeySelective(ecmArtwork);

                return ResponseDTO.get(1 == i , "不通过审核,以还原成草稿");
            }



            // 判断是否 存在 不通过 节点
            for (EcmArtworkNodesVo ecmArtworkNodesVo : collect) {
                // 判断是否 存在 不通过 节点
                if (ecmArtworkNodesVo.getFkEndingId() == 5) {

                    ecmMessageService.insertSystemMsgByNode(ecmArtworkNodesVo, 2);
                    // 设置作品状态
                    ecmArtwork.setArtworkStatus((short) 0);
                    ecmArtwork.setLastModifyDate(new Date());
                    ecmArtworkNodesDao.updateLinkNodeByFailCheckArtwork(ecmArtwork.getPkArtworkId());
                    int i =  ecmArtworkDao.updateByPrimaryKeySelective(ecmArtwork);

                    // 发送站内信 不通过

                    return ResponseDTO.get(1 == i, "不通过审核,以还原成草稿");
                }
            }
            ecmMessageService.insertSystemMsg(ecmArtwork, "作品通过审核");

            // 发送 站内信  通过 ！！
            ecmArtwork.setArtworkStatus((short) 2);
            // 修改作品状态
            return ResponseDTO.get(1 == ecmArtworkDao.updateByPrimaryKeySelective(ecmArtwork), "作通过审核");

        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponseDTO.fail("网络错误");
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
        // 可优化
        EcmReportHistroyVO ecmReportHistroyVO = ecmReportHistroyDao.selectByArtWorkId(ecmArtworkQuery.getPkArtworkId()).get(0);

        EcmArtwork ecmArtwork = new EcmArtwork();

        if (ecmReportHistroyVO.getFkChUserid() == null){
            ecmReportHistroyVO.setFkChUserid(userId);
        }
        if (!userId.equals(ecmReportHistroyVO.getFkChUserid()) && ecmReportHistroyVO.getFkChUserid() != null ){
            return ResponseDTO.fail("无权限");
        }

        // 查询作品的 所有节点
        List<EcmArtworkNodesVo> ecmArtworkNodesVos = ecmArtworkNodesDao.selectByArtWorkId(ecmArtworkQuery.getPkArtworkId());
        List<EcmArtworkNodesVo> collect = ecmArtworkNodesVos.stream().filter(ecmArtworkNodesVo -> !"Y".equals(ecmArtworkNodesVo.getIsDeleted())).collect(Collectors.toList());
        // 先创建一个需要修个作品对象
        ecmArtwork.setPkArtworkId(ecmArtworkQuery.getPkArtworkId());
        ecmArtwork.setLastModifyDate(new Date());
        // 循环遍历 比对 作品的 状态
        for (EcmArtworkNodesVo ecmArtworkNodesVo : collect) {
            if (ecmArtworkNodesVo.getFkEndingId() == null) {
                return ResponseDTO.fail("作品有节点未审核");
            } else if (ecmArtworkNodesVo.getFkEndingId() == 2) {
                return ResponseDTO.fail("作品有节点未审核");
            } else if (ecmArtworkNodesVo.getFkEndingId() == 3) {
                return ResponseDTO.fail("作品有节点未审核");
            } else if (ecmArtworkNodesVo.getFkEndingId() == 6) {
                return ResponseDTO.fail("作品有举报节点未处理");
            }
        }

        EcmReportHistroy ecmReportHistroy = new EcmReportHistroy();
        ecmReportHistroy.setReportId(ecmReportHistroyVO.getReportId());
        ecmReportHistroy.setUpdataTime(new Date());
        ecmReportHistroy.setReState((short) 2);
        ecmReportHistroy.setFkChUserid(userId);
        ecmReportHistroy.setFkArtworkId(ecmReportHistroyVO.getFkArtworkId());
        try {

            // 判断是否 存在 不通过 节点
            for (EcmArtworkNodesVo ecmArtworkNodesVo : collect) {
                // 判断是否 存在 不通过 节点
                if (ecmArtworkNodesVo.getFkEndingId() == 5) {
                    // 设置作品状态
                    ecmArtwork.setArtworkStatus((short) 0);
                    // 判断是不是 违规 作品
                    // 改变节点状态
//                    ecmReportHistroyDao.updateStateSuccessByPrimaryKey(ecmReportHistroyVO.getReportId());
                    ecmReportHistroyDao.updateByPrimaryKeySelective(ecmReportHistroy);

//                    ecmMessageService.insertViolationMsg(ecmReportHistroy, "作品涉嫌违规");
                    ecmMessageService.insertViolationMsg(ecmArtworkNodesVo, ecmReportHistroy,12);
                    // 发送 站内信  违规 ！！
                    ecmArtwork.setLastModifyDate(new Date());
                    ecmArtwork.setFkAuditId(userId);
//                    ecmArtworkNodesDao.updateLinkNodeByFailCheckArtwork(ecmArtwork.getPkArtworkId());
                    return ResponseDTO.get(1 == ecmArtworkDao.updateByPrimaryKeySelective(ecmArtwork), "作不通过审核,以还原成草稿");
                }
            }

            // 用户id ，发送人 ， 内容
//            ecmReportHistroyDao.updateStateSuccessByPrimaryKey(ecmReportHistroyVO.getReportId());
            ecmReportHistroyDao.updateReportHistroySByAtrworkId(ecmReportHistroy.getFkArtworkId());
//            ecmReportHistroyDao.updateByPrimaryKeySelective(ecmReportHistroy);
            // 修改作品状态

            return ResponseDTO.ok("作品以确认无问题！");

        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponseDTO.fail("网络错误");
        }
    }

    @Override
    public ResponseDTO chArtWorkImg(EcmArtworkVO ecmArtworkVO) {
        EcmArtwork ecmArtwork = ecmArtworkDao.selectByPrimaryKey(ecmArtworkVO.getPkArtworkId());
        Integer userId = (Integer) getRequstSession().getAttribute("userId");
        if (ecmArtwork != null){
            if (ecmArtwork.getFkAuditId() != null  &&  !userId.equals(ecmArtwork.getFkAuditId())){
                return ResponseDTO.ok("无权限");
            }else {
                ecmArtworkVO.setFkAuditId(userId);
            }
        }

        return ResponseDTO.get(1 == ecmArtworkDao.updateByPrimaryKeySelective(ecmArtworkVO));
    }

    @Override
    public ResponseDTO chengArtWorkReport(EcmArtworkVO ecmArtworkVO) {
        // 更新时间
        EcmReportHistroyVO ecmReportHistroyVO = ecmReportHistroyDao.selectByArtWorkId(ecmArtworkVO.getPkArtworkId()).get(0);
        if (ecmReportHistroyVO == null ){
            EcmReportHistroy ecmReportHistroy = new EcmReportHistroy();
            ecmReportHistroy.setReState((short) 1);
            ecmReportHistroy.setFkArtworkId(ecmArtworkVO.getPkArtworkId());
            ecmReportHistroy.setContent("测试");
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
        //通过作品id查询作品
        EcmArtwork artwork = ecmArtworkDao.selectByPrimaryKey(ecmArtwork.getPkArtworkId());
        // 判断是否有 审核人
        if (!StringUtils.isEmpty(artwork.getFkAuditId())) {
            // 比对用户是否一致
            if (ecmArtwork.getFkAuditId().equals(artwork.getFkAuditId())) {
                return ResponseDTO.ok();
            }
        } else {
            // 没有审核人 ，当前用户做为审核人
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

    @Override
    public PageDTO ajaxArtworkFreeAdList(EcmArtworkFreeAdQuery ecmArtworkFreeAdQuery) {
        // 作品ID 作者ID 作者 作品名字 作者剩余流量 已使用流量 作品播放次数
         List<EcmArtworkVO> list= ecmArtworkFreeAdDao.selectListByEcmArtworkFreeAdQuery(ecmArtworkFreeAdQuery);
         Integer count =  ecmArtworkFreeAdDao.selectCountByEcmArtworkFreeAdQuery(ecmArtworkFreeAdQuery);

//        Integer cun = ecmArtworkBroadcastHotDao.selectAll()
        if (!CollectionUtils.isEmpty(list)) {
            List<EcmArtworkBroadcastHotVO> listVOByEcmArtworkList = ecmArtworkBroadcastHotDao.selectListVOByEcmArtworkList(list);

            list.forEach( ecmArtworkVO ->  {
                if ( ecmArtworkVO.getSubTotalFlow() != null && ecmArtworkVO.getSubUsedFlow() != null) {
                    ecmArtworkVO.setSurplusFlow(ecmArtworkVO.getSubTotalFlow() - ecmArtworkVO.getSubUsedFlow());
                }
                if (!CollectionUtils.isEmpty(listVOByEcmArtworkList)) {
                    listVOByEcmArtworkList.forEach( statisticsPlayRecordVO ->  {
                        if(ecmArtworkVO.getPkArtworkId().equals(statisticsPlayRecordVO.getFkArkworkId())) {
                            ecmArtworkVO.setPlayCount(statisticsPlayRecordVO.getBroadcastCount());
                        }
                    });
                }
            });
        }


        return PageDTO.setPageData(count, list);
    }

    @Override
    public PageDTO ajaxFreeAdList(EcmArtworkQuery ecmArtworkQuery) {

        List<EcmArtworkVO> list = ecmArtworkDao.selectAjaxFreeAdList(ecmArtworkQuery);
        Integer count = ecmArtworkDao.selectCountByQuery(ecmArtworkQuery);
        List<EcmArtworkFreeAd> ecmArtworkFreeAdList = ecmArtworkFreeAdDao.selectList();

        list.forEach( ecmArtworkVO ->  {

            if (!CollectionUtils.isEmpty(ecmArtworkFreeAdList)){
                ecmArtworkFreeAdList.forEach( ecmArtworkFreeAd ->  {
                    if (ecmArtworkVO.getPkArtworkId().equals(ecmArtworkFreeAd.getFkArtworkId())){
                        ecmArtworkVO.setChecked(true);
                        ecmArtworkVO.setIsChecked(true);
                    }
                });
            }
            if (ecmArtworkVO.getSubTotalFlow() != null && ecmArtworkVO.getSubUsedFlow() != null) {
                ecmArtworkVO.setSurplusFlow(ecmArtworkVO.getSubTotalFlow() - ecmArtworkVO.getSubUsedFlow());
            }

        });

        return PageDTO.setPageData(count, list);
    }

    @Override
    public ResponseDTO saveArtWorkFreeAdSettingList(EcmArtworkFreeAdVO ecmArtworkFreeAdVO) {

        if (!CollectionUtils.isEmpty(ecmArtworkFreeAdVO.getUnFkArtworkIdList())){
            ecmArtworkFreeAdDao.deleteByEcmArtworkIdList(ecmArtworkFreeAdVO.getUnFkArtworkIdList());
        }
        if (!CollectionUtils.isEmpty(ecmArtworkFreeAdVO.getFkArtworkIdList())){
            List<EcmArtworkFreeAd> ecmArtworkFreeAdList =  ecmArtworkFreeAdDao.selectListByEcmArtworkIdList(ecmArtworkFreeAdVO.getFkArtworkIdList());
            if (!CollectionUtils.isEmpty(ecmArtworkFreeAdList)) {
                List<Integer> fkArtworkIdList = ecmArtworkFreeAdVO.getFkArtworkIdList();
                Iterator<Integer> iterator = fkArtworkIdList.iterator();
                while (iterator.hasNext()) {
                    Integer next = iterator.next();
                    ecmArtworkFreeAdList.forEach(  e -> {
                        if (next.equals(e.getFkArtworkId())) {
                            iterator.remove();
                        }
                    });
                }
                if (!CollectionUtils.isEmpty(fkArtworkIdList)){
                    return getResponseDTO(checkInsertArtworkFreeAd(ecmArtworkFreeAdVO.getFkArtworkIdList()));
                }
            }else {
                return getResponseDTO( checkInsertArtworkFreeAd(ecmArtworkFreeAdVO.getFkArtworkIdList()));
            }

        }

        return ResponseDTO.ok("success");

    }

    /**
     * @param: [checkId] 状态
     * @return: com.wxcz.carpenter.pojo.dto.ResponseDTO
     * @author: cxd
     * @Date: 2021/5/18
     * 描述 : 根据状态返回不同消息
     *       成功: status 200  msg "success”   date:
     *       失败: status 500  msg "error“
     */
    private ResponseDTO getResponseDTO(int checkId) {
        if (checkId ==1 )  {
            return ResponseDTO.fail("所选作品全部用户没有绑定私有云点播，请联系技术人员");
        }else if(checkId ==2) {
            return ResponseDTO.fail("所选作品中部分保存成功，有作者没有流量保存失败！");
        }else if(checkId ==3) {
            return ResponseDTO.fail("所选作品中部分保存成功，有作者没有成为免广告权限！");
        }
        return ResponseDTO.ok("success");
    }

    /**
     * @param: [fkArtworkIdList2] 需要保存的 作品id 数组
     * @return: int
     * @author: cxd
     * @Date: 2021/5/18
     * 描述 :   根据需要保存的 作品id 保存 开通免广告和 剩余下行流量足够的作品
     *       成功: status 200  msg "success”   date:
     *       失败: status 500  msg "error“
     */
    private int checkInsertArtworkFreeAd(List<Integer> fkArtworkIdList2) {
        List<EcmArtworkVO> ecmArtworkVOList = ecmArtworkDao.selectDownLinkFlowByPrimaryKeyList(fkArtworkIdList2);
        //所选作品全部用户没有绑定私有云点播，请联系技术人员
        if (CollectionUtils.isEmpty(ecmArtworkVOList)) {
            return 1;
        }
        Set<EcmArtworkVO> collect = ecmArtworkVOList.stream().filter(ecmArtworkVO -> ecmArtworkVO.getSubTotalFlow() > ecmArtworkVO.getSubUsedFlow()).collect(Collectors.toSet());
        if (CollectionUtils.isEmpty(collect)) {
            //所选作品中部分保存成功，有作者没有流量保存失败！
            if (ecmArtworkVOList.size() != collect.size()) {
                return 2;
            }
            return 1;
        }
        ecmArtworkFreeAdDao.insertArtWorkFreeAdEcmArtworkVOList(collect);


        //所选作品中部分保存成功，有作者没有成为免广告权限！
        if (ecmArtworkVOList.size() != fkArtworkIdList2.size()) {
            return 3;
        }

        return 0;
    }


}
