package com.wxcz.carpenter.task.flow;

import com.wxcz.carpenter.dao.EcmFlowCheckHistoryDao;
import com.wxcz.carpenter.dao.EcmUserFlowDao;
import com.wxcz.carpenter.dao.EcmUserHistoryFlowDao;
import com.wxcz.carpenter.pojo.entity.EcmUserFlow;
import com.wxcz.carpenter.pojo.entity.EcmUserHistoryFlow;
import com.wxcz.carpenter.pojo.vo.EcmUserFlowVO;
import com.wxcz.carpenter.pojo.vo.EcmUserHistoryFlowVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;

import java.util.stream.Collectors;

/**
 * @author by cxd
 * @Classname UserFlowhandle
 * @Description TODO
 * @Date 2020/9/11 17:26
 */
@Slf4j
@Component
@EnableScheduling
public class UserFlowhandle {

    @Resource
    private EcmUserFlowDao ecmUserFlowDao;
    @Resource
    private EcmUserHistoryFlowDao ecmUserHistoryFlowDao;


    /**
     * @param: []
     * @return: void
     * @author: cxd
     * @Date: 2020/9/14
     * 描述 :  定时任务 重新计算用户剩余流量流量
     * 1.先计算过去24小时 用户使用的 流量 是否计算正确
     * 2.计算不正确的用户，会单独在计算该用户的所有使用流量记录，并修改
     */
//    @Scheduled(cron = "0 10 4 ? * *")
//    @Scheduled(cron = "0/10 * * * * ?")
    private void handleUserFlowCheck() {

        //List<EcmUserHistoryFlowVO> ecmUserHistoryFlowVOS = ecmUserHistoryFlowDao.selectUserFlowAll();
        // 查询过去24 小的 流量使用情况
        List<EcmUserHistoryFlowVO> ecmUserHistoryFlowVOList = ecmUserHistoryFlowDao.selectUserFlowByOneDay();
        //需要修改的userid集合
        Set<Integer> userIds = new HashSet<>();
        List<EcmUserFlow> ecmUserFlows = new ArrayList<>();
        // 批量修改 当天的 checkflow字段 的 集合
        List<EcmUserFlow> ecmUserFlowArrayList = new ArrayList<>();

        Map<Integer, List<EcmUserHistoryFlowVO>> collect = ecmUserHistoryFlowVOList.stream().collect(Collectors.groupingBy(EcmUserHistoryFlow::getUserId));
        //数据错误的用户id 的集合
        Set<Integer> integers = collect.keySet();
        List<EcmUserFlowVO> userFlowList = ecmUserFlowDao.selectByUserIds(integers);

        // 计算是否有出错的 流量 用户
        for (Map.Entry<Integer, List<EcmUserHistoryFlowVO>> entry : collect.entrySet()) {
            Integer key = entry.getKey();
            List<EcmUserHistoryFlowVO> value = entry.getValue();
            // 今日 使用 流量集合
            Integer count = 0;
            for (EcmUserHistoryFlowVO ecmUserHistoryFlowVO : value) {
                count = ecmUserHistoryFlowVO.getVideoFlow() + count;
            }
            for (EcmUserFlow ecmUserFlow : userFlowList) {
                if (ecmUserFlow.getUserId().equals(key)) {
                    //3种情况下的 核算
                    if (ecmUserFlow.getPermanentFlow() > 0) {
                        if (!ecmUserFlow.getCheckFlow().equals(ecmUserFlow.getPermanentFlow() + count)) {
                            //流量计算不正确 校准流量
                            if (ecmUserFlow.getCheckFlow() == null){
                                ecmUserFlow.setCheckFlow((ecmUserFlow.getPermanentFlow() - count));
                            }else {
                                ecmUserFlow.setCheckFlow(ecmUserFlow.getCheckFlow() - count);
                                ecmUserFlow.setPermanentFlow(ecmUserFlow.getCheckFlow());
                            }
                        } else {
                            ecmUserFlow.setCheckFlow(ecmUserFlow.getPermanentFlow());
                        }
                        ecmUserFlow.setUpdateTime(new Date());
                        // 没有出错的 用户 修改 checkflow字段
                        ecmUserFlowArrayList.add(ecmUserFlow);
                    }
                    if (ecmUserFlow.getPermanentFlow().equals(0) && ecmUserFlow.getUsedFlow() >= 0) {
                        if (!ecmUserFlow.getUsedFlow().equals(ecmUserFlow.getCheckFlow() + count - 500)) {
                            //流量计算不正确 校准流量
                            ecmUserFlow.setCheckFlow(ecmUserFlow.getCheckFlow() + count - 500);
                            ecmUserFlow.setPermanentFlow(ecmUserFlow.getCheckFlow() );
                        } else {
                            ecmUserFlow.setCheckFlow(ecmUserFlow.getUsedFlow() + 500);
                        }
                        ecmUserFlow.setUpdateTime(new Date());
                        // 没有出错的 用户 修改 checkflow字段
                        ecmUserFlowArrayList.add(ecmUserFlow);

                    }
                }
            }

        }
        // 批量更新
        // 保存修改记录
        if (!CollectionUtils.isEmpty(ecmUserFlowArrayList)) {
                Integer c = ecmUserFlowDao.updateUserFlowCheck(ecmUserFlowArrayList);
                log.info("核算后今日修改的用户流量为" + c + "用户");
        }
    }


}
