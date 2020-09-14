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
    @Resource
    private EcmFlowCheckHistoryDao ecmFlowCheckHistoryDao;


    /**
     * @param: []
     * @return: void
     * @author: cxd
     * @Date: 2020/9/14
     * 描述 :  定时任务 重新计算用户剩余流量流量
     * 1.先计算过去24小时 用户使用的 流量 是否计算正确
     * 2.计算不正确的用户，会单独在计算该用户的所有使用流量记录，并修改
     */
    @Scheduled(cron = "0 10 4 ? * *")
    //@Scheduled(cron = "0/10 * * * * ?")
    private void handleUserFlowCheck() {

        //List<EcmUserHistoryFlowVO> ecmUserHistoryFlowVOS = ecmUserHistoryFlowDao.selectUserFlowAll();
        // 查询过去24 小的 流量使用情况
        List<EcmUserHistoryFlowVO> ecmUserHistoryFlowVOS = ecmUserHistoryFlowDao.selectUserFlowByOneDay();
        //需要修改的userid集合
        Set<Integer> userIds = new HashSet<>();
        List<EcmUserFlow> ecmUserFlows = new ArrayList<>();
        // 批量修改 当天的 checkflow字段
        List<EcmUserFlow> ecmUserFlowArrayList = new ArrayList<>();

        Map<Integer, List<EcmUserHistoryFlowVO>> collect = ecmUserHistoryFlowVOS.stream().collect(Collectors.groupingBy(EcmUserHistoryFlow::getUserId));
        Set<Integer> integers = collect.keySet();
        List<EcmUserFlowVO> userFlowList = ecmUserFlowDao.selectByUserIds(integers);

        // 计算是否有出错的 流量 用户
        collect.forEach((k, v) -> {
            Integer count = new Integer(0);
            for (EcmUserHistoryFlowVO ecmUserHistoryFlowVO : v) {
                count = ecmUserHistoryFlowVO.getVideoFlow() + count;
            }
            for (EcmUserFlow ecmUserFlow : userFlowList) {
                if (ecmUserFlow.getUserId().equals(k)) {
                    if (!ecmUserFlow.getSurplusFlow().equals(ecmUserFlow.getCheckFlow() - count)) {
                        ecmUserFlow.setUpdateTime(new Date());
                        // 计算错误的 用户 加入集合
                        userIds.add(ecmUserFlow.getUserId());
                    } else {
                        ecmUserFlow.setCheckFlow(ecmUserFlow.getSurplusFlow());
                        ecmUserFlow.setUpdateTime(new Date());
                        // 没有出错的 用户 修改 checkflow字段
                        ecmUserFlowArrayList.add(ecmUserFlow);
                    }
                }
            }

        });
        if (!CollectionUtils.isEmpty(ecmUserFlowArrayList)) {
            Integer c = ecmUserFlowDao.updateUserFlowCheck(ecmUserFlowArrayList);
        }
        List<EcmUserHistoryFlowVO> userHistoryFlowVOS = ecmUserHistoryFlowDao.selectUserFlowByUserIds(userIds);
        Map<Integer, List<EcmUserHistoryFlowVO>> integerListMap = userHistoryFlowVOS.stream().collect(Collectors.groupingBy(EcmUserHistoryFlow::getUserId));
        integerListMap.forEach((k, v) -> {
            Integer count = new Integer(0);
            for (EcmUserHistoryFlowVO ecmUserHistoryFlowVO : v) {
                count = ecmUserHistoryFlowVO.getVideoFlow() + count;
            }
            for (EcmUserFlowVO ecmUserFlow : userFlowList) {
                if (ecmUserFlow.getUserId().equals(k)) {
                    ecmUserFlow.setOldFlow(ecmUserFlow.getSurplusFlow());
                    ecmUserFlow.setUpdateTime(new Date());
                    ecmUserFlow.setSurplusFlow(ecmUserFlow.getTotalFlow() - count);
                    ecmUserFlow.setCheckFlow(ecmUserFlow.getTotalFlow() - count);

                    ecmUserFlows.add(ecmUserFlow);
                }
            }

        });
        // 批量更新
        // 保存修改记录
        if (!CollectionUtils.isEmpty(ecmUserFlows)) {
            Integer count = ecmUserFlowDao.updateUserFlowByCheck(ecmUserFlows);
            ecmFlowCheckHistoryDao.insertUserFlowByCheck(ecmUserFlows);

            log.info("核算后今日修改的用户流量为" + count + "用户");
        }
    }


}
