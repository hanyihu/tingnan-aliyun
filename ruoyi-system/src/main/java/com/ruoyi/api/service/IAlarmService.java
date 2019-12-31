package com.ruoyi.api.service;

import com.ruoyi.system.domain.*;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author hanyihu
 * @title  告警接口
 * @date 2019/11/7 9:05
 */
@Service
public interface IAlarmService {


    List<VLive> getRealData(String queryJson);

    List<Map<String, Integer>> getAlarmCountByDay(String date);

    List<InfoEvent> getAlarmHis(String date);

    List<InfoEvent> getAlarmHis1();

    VAlarmhis getAlarmInfo(String id);

    InfoEvent getAlarmReal(String id);

    List<InfoEvent> getInfoEvent();

    Integer getInfoEventCount();

    void updateInfoEventCount(int newCount);
}




