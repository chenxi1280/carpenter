package com.wxcz.carpenter.service.impl;

import com.wxcz.carpenter.dao.EcmArtworkDao;
import com.wxcz.carpenter.dao.EcmArtworkNodesDao;
import com.wxcz.carpenter.dao.EcmReportHistroyDao;
import com.wxcz.carpenter.dao.EcmUserDao;
import com.wxcz.carpenter.pojo.dto.PageDTO;
import com.wxcz.carpenter.pojo.dto.ResponseDTO;
import com.wxcz.carpenter.pojo.entity.EcmArtwork;
import com.wxcz.carpenter.pojo.entity.EcmArtworkNodes;
import com.wxcz.carpenter.pojo.entity.EcmReportHistroy;
import com.wxcz.carpenter.pojo.entity.EcmUser;
import com.wxcz.carpenter.pojo.query.EcmArtworkQuery;
import com.wxcz.carpenter.pojo.vo.EcmArtworkNodesVo;
import com.wxcz.carpenter.pojo.vo.EcmArtworkVO;
import com.wxcz.carpenter.pojo.vo.EcmReportHistroyVO;
import com.wxcz.carpenter.pojo.vo.EcmUserVO;
import com.wxcz.carpenter.service.EcmArtworkService;
import com.wxcz.carpenter.util.TreeUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author by cxd
 * @Classname EcmArtworkServiceImpl
 * @Description TODO
 * @Date 2020/8/7 10:53
 */
@Service
public class EcmArtworkServiceImpl implements EcmArtworkService {

    @Resource
    EcmArtworkDao ecmArtworkDao;

    @Resource
    EcmUserDao ecmUserDao;

    @Resource
    EcmReportHistroyDao ecmReportHistroyDao;

    @Resource
    EcmArtworkNodesDao ecmArtworkNodesDao;

    @Override
    public PageDTO ajaxList(EcmArtworkQuery ecmArtworkQuery) {

        List<EcmArtworkVO>  list = ecmArtworkDao.selectajaxListByQuery(ecmArtworkQuery);
//        List<EcmArtworkVO>  list = ecmArtworkDao.selectajaxList(ecmArtworkQuery);
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
                listVOs.forEach( ecmUserVO -> {

                    if (ecmArtworkVO.getFkUserid() != null ) {
                        if (ecmUserVO.getPkUserId().equals(ecmArtworkVO.getFkUserid())) {
                            ecmArtworkVO.setUsername(ecmUserVO.getUsername());
                        }

                    }

                });

            });
        }


        Integer count = ecmArtworkDao.selectCountByQuery(ecmArtworkQuery);
        return PageDTO.setPageData(count,list);
    }

    @Override
    public ResponseDTO chengArtWork(EcmArtworkVO ecmArtworkVO) {
        // 更新时间
        ecmArtworkVO.setLastModifyDate(new Date());
        return ResponseDTO.get(ecmArtworkDao.updateByPrimaryKeySelective(ecmArtworkVO) == 1);
    }

    @Override
    public PageDTO ajaxCheckList(EcmArtworkQuery ecmArtworkQuery) {
        // 这里是一个 链接查询 返回 带有 username 的 未审核的作品集合  按修改时间排序
        List<EcmArtworkVO>  list = ecmArtworkDao.selectajaxCheckList(ecmArtworkQuery);
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
                listVOs.forEach( ecmUserVO -> {

                    if (ecmArtworkVO.getFkUserid() != null ) {
                        if (ecmUserVO.getPkUserId().equals(ecmArtworkVO.getFkUserid())) {
                            ecmArtworkVO.setUsername(ecmUserVO.getUsername());
                        }

                    }

                });

            });
        }
        Integer count = ecmArtworkDao.selectCountByCheckList(ecmArtworkQuery);
        return PageDTO.setPageData(count,list);
    }

    @Override
    public ResponseDTO getArtWorkNoteS(EcmArtworkQuery ecmArtworkVO) {

        EcmArtwork ecmArtwork = ecmArtworkDao.selectByPrimaryKey(ecmArtworkVO.getPkArtworkId());
        if (ecmArtwork == null) {
            return ResponseDTO.fail("查询id为空");
        }
        List<EcmArtworkNodesVo> list = ecmArtworkNodesDao.selectByArtWorkId(ecmArtworkVO.getPkArtworkId());
        return ResponseDTO.ok("success", TreeUtil.buildTree(list).get(0));
    }

    @Override
    public ResponseDTO upDataNode(EcmArtworkNodes ecmArtworkNodes) {
        return ResponseDTO.get(ecmArtworkNodesDao.updateByPrimaryKeySelective(ecmArtworkNodes) == 1 );
    }

    @Override
    public ResponseDTO checkArtWork(EcmArtworkQuery ecmArtworkQuery) {
        // 查询作品的 所有节点
        List<EcmArtworkNodesVo> ecmArtworkNodesVos = ecmArtworkNodesDao.selectByArtWorkId(ecmArtworkQuery.getPkArtworkId());
        // 先创建一个需要修个作品对象
        EcmArtwork ecmArtwork = new EcmArtwork();
        ecmArtwork.setPkArtworkId(ecmArtworkQuery.getPkArtworkId());
        ecmArtwork.setLastModifyDate(new Date());
        // 循环遍历 比对 作品的 状态
        for (EcmArtworkNodesVo ecmArtworkNodesVo : ecmArtworkNodesVos) {
            if (ecmArtworkNodesVo.getFkEndingId() == null){
                return ResponseDTO.fail("作品有节点未审核");
            }else if (ecmArtworkNodesVo.getFkEndingId()== 2){
                return ResponseDTO.fail("作品有节点未审核");
            }else if (ecmArtworkNodesVo.getFkEndingId()== 3){
                return ResponseDTO.fail("作品有节点未审核");
            }else if (ecmArtworkNodesVo.getFkEndingId()== 6){
                return ResponseDTO.fail("作品有举报节点未处理");
            }
        }


        EcmReportHistroyVO ecmReportHistroyVO = ecmReportHistroyDao.selectByArtWorkId(ecmArtworkQuery.getPkArtworkId());
//
//        //判断是否为 投诉节点
//        if (ecmReportHistroyVO.getReportId() != null){
//            // 改变节点状态
//            ecmReportHistroyDao.updateStateSuccessByPrimaryKey(ecmReportHistroyVO.getReportId());
//            System.out.println("发送违规");
//            // 发送 站内信  违规 ！！
//        }else {
//            System.out.println("发送成功");
//            // 发送 站内信  通过 ！！
//
//        }

        // 判断是否 存在 不通过 节点
        for (EcmArtworkNodesVo ecmArtworkNodesVo : ecmArtworkNodesVos) {
            // 判断是否 存在 不通过 节点
            if (ecmArtworkNodesVo.getFkEndingId() == 5){
                // 设置作品状态
                ecmArtwork.setArtworkStatus((short) 0);
                if (ecmReportHistroyVO != null){
                    // 改变节点状态
                    ecmReportHistroyDao.updateStateSuccessByPrimaryKey(ecmReportHistroyVO.getReportId());
                    System.out.println("发送违规");
                    // 发送 站内信  违规 ！！
                }else {
                    // 发送站内信 不通过
                    System.out.println("发送不通过");
                }
                return ResponseDTO.get(1 == ecmArtworkDao.updateByPrimaryKeySelective(ecmArtwork),"作不通过审核,以还原成草稿");
            }
        }

        System.out.println("发送成功");
        // 发送 站内信  通过 ！！
        ecmArtwork.setArtworkStatus((short) 2);
        // 修改作品状态
        return ResponseDTO.get(1 == ecmArtworkDao.updateByPrimaryKeySelective(ecmArtwork),"作通过审核");
    }

    @Override
    public ResponseDTO upDataArtWork(EcmArtwork ecmArtwork) {
        return  ResponseDTO.get(1 == ecmArtworkDao.updateByPrimaryKeySelective(ecmArtwork));
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
        }else {
            // 没有审核人 ，当前用户做为审核人
            return upDataArtWork(ecmArtwork);
        }
        return ResponseDTO.fail("error");
    }
}
