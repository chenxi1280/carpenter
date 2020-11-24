package com.wxcz.carpenter.task.broadcastHot;

import com.wxcz.carpenter.dao.EcmArtworkBroadcastHistoryDao;
import com.wxcz.carpenter.dao.EcmArtworkBroadcastHotDao;
import com.wxcz.carpenter.pojo.entity.EcmArtworkBroadcastHistory;
import com.wxcz.carpenter.pojo.vo.EcmArtworkBroadcastHistoryVO;
import com.wxcz.carpenter.pojo.vo.EcmArtworkBroadcastHotVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author by cxd
 * @Classname BroadcastHotHandle
 * @Description TODO
 * @Date 2020/9/18 13:17
 */
@Slf4j
@Component
@EnableScheduling
public class BroadcastHotHandle {

    @Resource
    EcmArtworkBroadcastHistoryDao ecmArtworkBroadcastHistoryDao;
    @Resource
    EcmArtworkBroadcastHotDao ecmArtworkBroadcastHotDao;


    /**
     * @param: []
     * @return: void
     * @author: cxd
     * @Date: 2020/9/18
     * 描述 :  没日重新计算 作品播放次数 定时任务， 每日 5.10 启动
     *          获取 过去24 小时的全部 播放记录 ，进行计算 ，
     *          有数据不等 会用 之前的  播放记录数+ 昨天播放记录数
     */
//    @Scheduled(cron = "0 10 5 ? * *")
    @Scheduled(cron = "0/10 * * * * ?")
    private void handleBroadcastHot()  {
        // 获取 所有 播放记录
//        List<EcmArtworkBroadcastHistoryVO> ecmArtworkBroadcastHistoryVOS = ecmArtworkBroadcastHistoryDao.selectAll();
        // 获取过去一天的 播放视频的 视频 播放记录
        System.out.println("热度定时任务开始工作");
        List<EcmArtworkBroadcastHistoryVO> ecmArtworkBroadcastHistoryVOS = ecmArtworkBroadcastHistoryDao.selectHotByOneDay();

//        Map<Integer, List<EcmArtworkBroadcastHistoryVO>> collect = ecmArtworkBroadcastHistoryVOS.stream().collect( Collectors.groupingBy(EcmArtworkBroadcastHistory::getFkArtworkId));

//        Set<Integer> integers = collect.keySet();

        if (CollectionUtils.isEmpty(ecmArtworkBroadcastHistoryVOS)) {
            return;
        }
        List<EcmArtworkBroadcastHotVO> ecmArtworkBroadcastHotVOList = ecmArtworkBroadcastHotDao.selectByEcmArtworkBroadcastHistoryVOs(ecmArtworkBroadcastHistoryVOS);
        ecmArtworkBroadcastHistoryVOS.forEach( v -> {
            ecmArtworkBroadcastHotVOList.forEach( ecmArtworkBroadcastHotVO -> {
                if (v.getFkArtworkId().equals(ecmArtworkBroadcastHotVO.getFkArkworkId())){
                    if (ecmArtworkBroadcastHotVO.getBroadcastCount() != null){
                        if (!ecmArtworkBroadcastHotVO.getBroadcastCount().equals(v.getArtworkCount() + ecmArtworkBroadcastHotVO.getWaitCount())){
                            ecmArtworkBroadcastHotVO.setBroadcastCount(v.getArtworkCount() + ecmArtworkBroadcastHotVO.getWaitCount());
                        }else {
                           System.out.println("没有出现问题");
                           ecmArtworkBroadcastHotVO.setBroadcastCount(null);
                        }
                        ecmArtworkBroadcastHotVO.setWaitCount( v.getArtworkCount() + ecmArtworkBroadcastHotVO.getWaitCount() );
                    }
                }

            });
        });

        // 全部重新计算
//        collect.forEach( (k,v) -> {
//            ecmArtworkBroadcastHotVOList.forEach( ecmArtworkBroadcastHotVO -> {
//                if (k.equals(ecmArtworkBroadcastHotVO.getFkArkworkId())){
//                    if (!ecmArtworkBroadcastHotVO.getBroadcastCount().equals(v.size() )){
//                        ecmArtworkBroadcastHotVO.setBroadcastCount(v.size() );
//                    }
//                }
//            });
//        });


        if (!CollectionUtils.isEmpty(ecmArtworkBroadcastHotVOList)){
            ecmArtworkBroadcastHotDao.updateByNewBroadcastHot(ecmArtworkBroadcastHotVOList);
        }
        System.out.println("热度定时任务完成工作");

    }



}
