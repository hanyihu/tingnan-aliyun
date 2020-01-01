package com.ruoyi.api.service.impl;

import com.ruoyi.api.mapper.AlarmMapper;
import com.ruoyi.api.service.IAlarmService;
import com.ruoyi.system.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author hanyihu
 * @title
 * @date 2019/11/7 9:17
 */
@Service
public class AlarmServiceImpl implements IAlarmService {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private AlarmMapper alarmMapper;


    @Override
    public List<VLive> getRealData(String queryJson) {
        return alarmMapper.getRealData(queryJson);
    }

    @Override
    public List<Map<String, Integer>> getAlarmCountByDay(String date) {
        return alarmMapper.getAlarmCountByDay(date);
    }

    @Override
    public List<InfoEvent> getAlarmHis(String date) {
        return alarmMapper.getAlarmHis(date);
    }

    @Override
    public List<InfoEvent> getAlarmHis1() {
        return alarmMapper.getAlarmHis1();
    }

    @Override
    public VAlarmhis getAlarmInfo(String id) { return alarmMapper.getAlarmInfo(id); }

    @Override
    public InfoEvent getAlarmReal(String id) {
        return alarmMapper.getAlarmReal(id);
    }

    @Override
    public List<VTagInfoAlarm> getTagInfoAlarm() {
        return alarmMapper.getTagInfoAlarm();
    }

    @Override
    public Integer getInfoEventCount() {
        return alarmMapper.getInfoEventCount();
    }

    @Override
    public void updateInfoEventCount(int newCount) {
        alarmMapper.updateInfoEventCount(newCount);
    }
}
