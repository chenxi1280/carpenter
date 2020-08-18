package com.wxcz.carpenter.service.impl;

import com.wxcz.carpenter.dao.EcmArtworkDao;
import com.wxcz.carpenter.dao.EcmReportHistroyDao;
import com.wxcz.carpenter.dao.EcmUserDao;
import com.wxcz.carpenter.pojo.dto.PageDTO;
import com.wxcz.carpenter.pojo.entity.EcmReportHistroy;
import com.wxcz.carpenter.pojo.query.ReportArtWorkQuery;
import com.wxcz.carpenter.pojo.vo.EcmArtworkVO;
import com.wxcz.carpenter.pojo.vo.EcmReportHistroyVO;
import com.wxcz.carpenter.pojo.vo.EcmUserVO;
import com.wxcz.carpenter.service.EcmReportHistroyService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

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
    EcmArtworkDao ecmArtworkDao;

    @Resource
    EcmUserDao ecmUserDao;

    @Override
    public PageDTO ajaxList(ReportArtWorkQuery reportArtWorkQuery) {

        List<EcmReportHistroyVO> list = ecmReportHistroyDao.selectAjaxList(reportArtWorkQuery);
        Integer count = ecmReportHistroyDao.selectAjaxCount(reportArtWorkQuery);


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
}
