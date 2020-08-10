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

    //检查 节点
    @Override
    public ResponseDTO checkArtWork(EcmArtworkQuery ecmArtworkQuery) {
        return null;
    }
}
