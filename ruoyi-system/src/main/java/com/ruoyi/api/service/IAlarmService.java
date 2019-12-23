package com.ruoyi.api.service;

import com.ruoyi.system.domain.VAlarmReal;
import com.ruoyi.system.domain.VAlarmhis;
import com.ruoyi.system.domain.VLive;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hanyihu
 * @title  告警接口
 * @date 2019/11/7 9:05
 */
@Service
public interface IAlarmService {


    List<VLive> getRealData(String queryJson);

    List<VAlarmhis> getAlarmCountByDay(String date);

    List<VAlarmhis> getAlarmHis(String date);

    VAlarmhis getAlarmInfo(String id);

    VAlarmReal getAlarmReal(String id);
}




