package com.wxcz.carpenter.service.impl;

import com.wxcz.carpenter.dao.EcmArtworkDao;
import com.wxcz.carpenter.dao.EcmArtworkNodesDao;
import com.wxcz.carpenter.dao.EcmUserDao;
import com.wxcz.carpenter.pojo.dto.PageDTO;
import com.wxcz.carpenter.pojo.dto.ResponseDTO;
import com.wxcz.carpenter.pojo.entity.EcmArtwork;
import com.wxcz.carpenter.pojo.entity.EcmArtworkNodes;
import com.wxcz.carpenter.pojo.entity.EcmUser;
import com.wxcz.carpenter.pojo.query.EcmArtworkQuery;
import com.wxcz.carpenter.pojo.vo.EcmArtworkNodesVo;
import com.wxcz.carpenter.pojo.vo.EcmArtworkVO;
import com.wxcz.carpenter.pojo.vo.EcmUserVO;
import com.wxcz.carpenter.service.EcmArtworkService;
import com.wxcz.carpenter.util.TreeUtil;
import org.springframework.stereotype.Service;
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
    EcmArtworkNodesDao ecmArtworkNodesDao;

    @Override
    public PageDTO ajaxList(EcmArtworkQuery ecmArtworkQuery) {
        // 这里是一个 链接查询 返回 带有 username 的 作品集合
        List<EcmArtworkVO>  list = ecmArtworkDao.selectajaxList(ecmArtworkQuery);

        List<EcmUserVO>  lists = ecmUserDao.selectByList(list);
        list.forEach(ecmArtworkVO -> {
            lists.forEach(ecmUserVO ->  {
                if (ecmArtworkVO.getFkAuditId() != null) {
                    if (ecmUserVO.getPkUserId().equals(ecmArtworkVO.getFkAuditId())) {
                        ecmArtworkVO.setFkAuditName(ecmUserVO.getUsername());
                    }
                }
            });
        });



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
        List<EcmUserVO>  lists = ecmUserDao.selectByList(list);
        list.forEach(ecmArtworkVO -> {
            lists.forEach(ecmUserVO ->  {
                if (ecmArtworkVO.getFkAuditId() != null) {
                    if (ecmUserVO.getPkUserId().equals(ecmArtworkVO.getFkAuditId())) {
                        ecmArtworkVO.setFkAuditName(ecmUserVO.getUsername());
                    }
                }
            });
        });
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
        List<EcmArtworkNodesVo> ecmArtworkNodesVos = ecmArtworkNodesDao.selectByArtWorkId(ecmArtworkQuery.getPkArtworkId());
        EcmArtwork ecmArtwork = new EcmArtwork();
        ecmArtwork.setPkArtworkId(ecmArtworkQuery.getPkArtworkId());
        ecmArtwork.setLastModifyDate(new Date());
        for (EcmArtworkNodesVo ecmArtworkNodesVo : ecmArtworkNodesVos) {
            if (ecmArtworkNodesVo.getVideoCode() == null){
                return ResponseDTO.fail("作品有节点未审核");
            }
            if (ecmArtworkNodesVo.getVideoCode().equals("1")){
                return ResponseDTO.fail("作品有节点未审核");
            }
            if (ecmArtworkNodesVo.getVideoCode().equals("3")){
                ecmArtwork.setArtworkStatus((short) 3);
                return ResponseDTO.get(1 == ecmArtworkDao.updateByPrimaryKeySelective(ecmArtwork),"作品未通过审核") ;
            }
        }
        ecmArtwork.setArtworkStatus((short) 2);
        return ResponseDTO.get(1 == ecmArtworkDao.updateByPrimaryKeySelective(ecmArtwork),"作通过审核");
    }

    @Override
    public ResponseDTO upDataArtWork(EcmArtwork ecmArtwork) {
        return  ResponseDTO.get(1 == ecmArtworkDao.updateByPrimaryKeySelective(ecmArtwork));
    }

    @Override
    public ResponseDTO artWorkAudit(EcmArtwork ecmArtwork) {
        EcmArtwork artwork = ecmArtworkDao.selectByPrimaryKey(ecmArtwork.getPkArtworkId());
        if (!StringUtils.isEmpty(artwork.getFkAuditId())) {
            if (ecmArtwork.getFkAuditId().equals(artwork.getFkAuditId())) {
                return ResponseDTO.ok();
            }
        }else {
            return upDataArtWork(ecmArtwork);
        }
        return ResponseDTO.fail("error");
    }
}
