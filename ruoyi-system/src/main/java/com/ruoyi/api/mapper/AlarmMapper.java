package com.ruoyi.api.mapper;

import com.ruoyi.system.domain.*;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author hanyihu
 * @title
 * @date 2019/11/7 9:27
 */
public interface AlarmMapper {



    List<VLive> getRealData(@Param("systemType") String systemType);

    List<Map<String, Integer>> getAlarmCountByDay(@Param("startTime") String date);

    List<InfoEvent> getAlarmHis(@Param("startTime") String date);

    List<InfoEvent> getAlarmHis1();

    VAlarmhis getAlarmInfo(@Param("id") String id);

    InfoEvent getAlarmReal(@Param("id") String id);

    List<InfoEvent> getInfoEvent();

    int getInfoEventCount();

    void updateInfoEventCount(@Param("newCount") int newCount);
}
