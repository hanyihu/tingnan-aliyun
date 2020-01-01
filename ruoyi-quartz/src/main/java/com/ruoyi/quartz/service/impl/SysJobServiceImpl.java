package com.ruoyi.quartz.service.impl;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.AppMessage;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.base.uitls.AppConditions;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.style.Style0;
import com.ruoyi.api.service.IAlarmService;
import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.constant.ScheduleConstants;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.common.exception.job.TaskException;
import com.ruoyi.quartz.domain.SysJob;
import com.ruoyi.quartz.mapper.SysJobMapper;
import com.ruoyi.quartz.service.ISysJobService;
import com.ruoyi.quartz.util.CronUtils;
import com.ruoyi.quartz.util.ScheduleUtils;
import com.ruoyi.system.domain.InfoEvent;
import com.ruoyi.system.domain.VTagInfoAlarm;
import org.quartz.JobDataMap;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 定时任务调度信息 服务层
 * 
 * @author ruoyi
 */
@Service
public class SysJobServiceImpl implements ISysJobService
{
    @Autowired
    private Scheduler scheduler;

    @Autowired
    private SysJobMapper jobMapper;




    /**
     * 项目启动时，初始化定时器 
     * 主要是防止手动修改数据库导致未同步到定时任务处理（注：不能手动修改数据库ID和任务组名，否则会导致脏数据）
     */
    @PostConstruct
    public void init() throws SchedulerException, TaskException
    {
        List<SysJob> jobList = jobMapper.selectJobAll();
        for (SysJob job : jobList)
        {
            updateSchedulerJob(job, job.getJobGroup());
        }
    }

    /**
     * 获取quartz调度器的计划任务列表
     * 
     * @param job 调度信息
     * @return
     */
    @Override
    public List<SysJob> selectJobList(SysJob job)
    {
        return jobMapper.selectJobList(job);
    }

    /**
     * 通过调度任务ID查询调度信息
     * 
     * @param jobId 调度任务ID
     * @return 调度任务对象信息
     */
    @Override
    public SysJob selectJobById(Long jobId)
    {
        return jobMapper.selectJobById(jobId);
    }

    /**
     * 暂停任务
     * 
     * @param job 调度信息
     */
    @Override
    @Transactional
    public int pauseJob(SysJob job) throws SchedulerException
    {
        Long jobId = job.getJobId();
        String jobGroup = job.getJobGroup();
        job.setStatus(ScheduleConstants.Status.PAUSE.getValue());
        int rows = jobMapper.updateJob(job);
        if (rows > 0)
        {
            scheduler.pauseJob(ScheduleUtils.getJobKey(jobId, jobGroup));
        }
        return rows;
    }

    /**
     * 恢复任务
     * 
     * @param job 调度信息
     */
    @Override
    @Transactional
    public int resumeJob(SysJob job) throws SchedulerException
    {
        Long jobId = job.getJobId();
        String jobGroup = job.getJobGroup();
        job.setStatus(ScheduleConstants.Status.NORMAL.getValue());
        int rows = jobMapper.updateJob(job);
        if (rows > 0)
        {
            scheduler.resumeJob(ScheduleUtils.getJobKey(jobId, jobGroup));
        }
        return rows;
    }

    /**
     * 删除任务后，所对应的trigger也将被删除
     * 
     * @param job 调度信息
     */
    @Override
    @Transactional
    public int deleteJob(SysJob job) throws SchedulerException
    {
        Long jobId = job.getJobId();
        String jobGroup = job.getJobGroup();
        int rows = jobMapper.deleteJobById(jobId);
        if (rows > 0)
        {
            scheduler.deleteJob(ScheduleUtils.getJobKey(jobId, jobGroup));
        }
        return rows;
    }

    /**
     * 批量删除调度信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    @Transactional
    public void deleteJobByIds(String ids) throws SchedulerException
    {
        Long[] jobIds = Convert.toLongArray(ids);
        for (Long jobId : jobIds)
        {
            SysJob job = jobMapper.selectJobById(jobId);
            deleteJob(job);
        }
    }

    /**
     * 任务调度状态修改
     * 
     * @param job 调度信息
     */
    @Override
    @Transactional
    public int changeStatus(SysJob job) throws SchedulerException
    {
        int rows = 0;
        String status = job.getStatus();
        if (ScheduleConstants.Status.NORMAL.getValue().equals(status))
        {
            rows = resumeJob(job);
        }
        else if (ScheduleConstants.Status.PAUSE.getValue().equals(status))
        {
            rows = pauseJob(job);
        }
        return rows;
    }

    /**
     * 立即运行任务
     * 
     * @param job 调度信息
     */
    @Override
    @Transactional
    public void run(SysJob job) throws SchedulerException
    {
        Long jobId = job.getJobId();
        String jobGroup = job.getJobGroup();
        SysJob properties = selectJobById(job.getJobId());
        // 参数
        JobDataMap dataMap = new JobDataMap();
        dataMap.put(ScheduleConstants.TASK_PROPERTIES, properties);
        scheduler.triggerJob(ScheduleUtils.getJobKey(jobId, jobGroup), dataMap);
    }

    /**
     * 新增任务
     * 
     * @param job 调度信息 调度信息
     */
    @Override
    @Transactional
    public int insertJob(SysJob job) throws SchedulerException, TaskException
    {
        job.setStatus(ScheduleConstants.Status.PAUSE.getValue());
        int rows = jobMapper.insertJob(job);
        if (rows > 0)
        {
            ScheduleUtils.createScheduleJob(scheduler, job);
        }
        return rows;
    }

    /**
     * 更新任务的时间表达式
     * 
     * @param job 调度信息
     */
    @Override
    @Transactional
    public int updateJob(SysJob job) throws SchedulerException, TaskException
    {
        SysJob properties = selectJobById(job.getJobId());
        int rows = jobMapper.updateJob(job);
        if (rows > 0)
        {
            updateSchedulerJob(job, properties.getJobGroup());
        }
        return rows;
    }

    /**
     * 更新任务
     * 
     * @param job 任务对象
     * @param jobGroup 任务组名
     */
    public void updateSchedulerJob(SysJob job, String jobGroup) throws SchedulerException, TaskException
    {
        Long jobId = job.getJobId();
        // 判断是否存在
        JobKey jobKey = ScheduleUtils.getJobKey(jobId, jobGroup);
        if (scheduler.checkExists(jobKey))
        {
            // 防止创建时存在数据问题 先移除，然后在执行创建操作
            scheduler.deleteJob(jobKey);
        }
        ScheduleUtils.createScheduleJob(scheduler, job);
    }

    // STEP1：获取应用基本信息
    private static String appId = "6oqP28NEX09HPQg38FxcQ3";
    private static String appKey = "WoBdu7eOttA2C51ah5Wjp";
    private static String masterSecret = "nQjk4aZvNE9Q6IidmjYaN6";
    private static String host = "http://sdk.open.api.igexin.com/apiex.htm";
    @Autowired
    private IAlarmService alarmService;

    /**
     * 功能描述: <br>
     * 〈〉  个推消息推送
     *
     * @Param: []
     * @Return: void
     * @Author: 韩以虎
     * @Date: 2019/12/26 10:59
     */
    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public void push() {
        IGtPush push = new IGtPush(host, appKey, masterSecret);
        //先进行告警数据查询 是否有告警信息
        List<VTagInfoAlarm> tagInfoAlarm = alarmService.getTagInfoAlarm();

        //查询阿里云数据库中告警数量
        int count = alarmService.getInfoEventCount();
        System.out.println("数量不相等=====" + (count != tagInfoAlarm.size()));
        System.out.println("今日告警数量===" + tagInfoAlarm.size());
        if (count != tagInfoAlarm.size()) {
            System.out.println("=============进入到推送========");
            Style0 style = new Style0();
            // STEP2：设置推送标题、推送内容

            System.out.println("标题====" + tagInfoAlarm.get(0).getTagName());
            System.out.println("内容====" + tagInfoAlarm.get(0).getSystemType());

            style.setTitle(tagInfoAlarm.get(0).getTagName());
            style.setText(tagInfoAlarm.get(0).getSystemType());
            style.setLogo("push.png");  // 设置推送图标

            // STEP3：设置响铃、震动等推送效果
            style.setRing(true);  // 设置响铃
            style.setVibrate(true);  // 设置震动


            // STEP4：选择通知模板
            NotificationTemplate template = new NotificationTemplate();
            template.setAppId(appId);
            template.setAppkey(appKey);
            template.setTransmissionType(2);
            // template.setTransmissionContent("请输⼊您要透传的内容");
            template.setStyle(style);


            // STEP5：定义"AppMessage"类型消息对象,设置推送消息有效期等推送参数
            List<String> appIds = new ArrayList<String>();
            appIds.add(appId);
            AppMessage message = new AppMessage();
            message.setData(template);
            message.setAppIdList(appIds);
            message.setOffline(true);
            message.setOfflineExpireTime(24 * 1000 * 3600);  // 时间单位为毫秒

            // STEP6：执行推送
            IPushResult ret = push.pushMessageToApp(message);
            System.out.println(ret.getResponse().toString());
            if (ret.getResponse().get("result").toString().equals("ok")) {
                System.out.println("修改=============================");
                //把数量更新到info_event_count 中
                alarmService.updateInfoEventCount(tagInfoAlarm.size());
            }

        }

    }


    /**
     * 校验cron表达式是否有效
     * 
     * @param cronExpression 表达式
     * @return 结果
     */
    @Override
    public boolean checkCronExpressionIsValid(String cronExpression)
    {
        return CronUtils.isValid(cronExpression);
    }
}