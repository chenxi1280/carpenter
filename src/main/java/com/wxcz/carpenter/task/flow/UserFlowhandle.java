package com.wxcz.carpenter.task.flow;

import com.wxcz.carpenter.dao.EcmUserFlowDao;
import com.wxcz.carpenter.dao.EcmUserHistoryFlowDao;
import com.wxcz.carpenter.pojo.entity.EcmUserFlow;
import com.wxcz.carpenter.pojo.entity.EcmUserHistoryFlow;
import com.wxcz.carpenter.pojo.vo.EcmUserHistoryFlowVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

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

//    @Scheduled(cron = "0 10 4 ? * *")
    @Scheduled(cron = "0/10 * * * * ?")
    private void handleUserFlowCheck(){

        // 查询每天
        List<EcmUserHistoryFlowVO> ecmUserHistoryFlowVOS = ecmUserHistoryFlowDao.selectUserFlowAll();

        List<EcmUserFlow> ecmUserFlows = new ArrayList<>();
//        List<>
        Map<Integer, List<EcmUserHistoryFlowVO>> collect = ecmUserHistoryFlowVOS.stream().collect(Collectors.groupingBy(EcmUserHistoryFlow::getUserId));
        Set<Integer> integers = collect.keySet();
        List<EcmUserFlow> userFlowList = ecmUserFlowDao.selectByUserIds(integers);

        collect.forEach( (k,v) -> {
            Integer count = new Integer(0);
            for (EcmUserHistoryFlowVO ecmUserHistoryFlowVO : v) {
                count = ecmUserHistoryFlowVO.getVideoFlow() +count  ;
            }
            for (EcmUserFlow ecmUserFlow : userFlowList) {
                if (!ecmUserFlow.getSurplusFlow().equals(count)){
                    ecmUserFlow.setUpdateTime(new Date());
                    ecmUserFlow.setSurplusFlow(count);
                    ecmUserFlows.add(ecmUserFlow);
                }
            }

        });

        // 批量更新
//        Integer count = ecmUserFlowDao.updateUserFlowCheck(ecmUserFlows);

//        System.out.println(count);

    }
}
