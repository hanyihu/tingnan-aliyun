package com.ruoyi.api.mapper;

import com.ruoyi.system.domain.VAlarmReal;
import com.ruoyi.system.domain.VAlarmhis;
import com.ruoyi.system.domain.VLive;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author hanyihu
 * @title
 * @date 2019/11/7 9:27
 */
public interface AlarmMapper {



    List<VLive> getRealData(@Param("systemType") String systemType);

    List<VAlarmhis> getAlarmCountByDay(@Param("startTime") String date);

    List<VAlarmhis> getAlarmHis(@Param("startTime") String date);

    VAlarmhis getAlarmInfo(@Param("id") String id);

    VAlarmReal getAlarmReal(@Param("id")String id);
}
