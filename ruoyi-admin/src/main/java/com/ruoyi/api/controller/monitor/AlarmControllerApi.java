package com.ruoyi.api.controller.monitor;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ruoyi.api.service.IAlarmService;
import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.PageDomain;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.domain.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @author hanyihu
 * @title  监测数据模块接口
 * @date 2019/11/13 15:35
 */
@Api("监测数据")
@RestController
@RequestMapping("/api/alarm")
@DataSource(value = DataSourceType.SLAVE)
public class AlarmControllerApi extends BaseController {

    @Autowired
    private IAlarmService alarmService;
    /**
     * 功能描述: <br> 告警统计
     * 〈〉
     * @Param: [token]
     * @Return: com.ruoyi.common.core.domain.AjaxResult
     * @Author: 韩以虎
     * @Date: 2019/11/13 15:39
     */
    @ApiOperation(value = "告警统计", notes = "date", produces = "application/josn")
    @PostMapping("/getAlarmCountByDay")
    public AjaxResult getAlarmCountByDay(String date){
      logger.info("获取的前端传来的日期=={}",date);

        //根据月份计算天数
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DateUtils.parseDate(date));
        int days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        logger.info("{}的天数为==={}", date, days);

        List<Map<String, Integer>> data = alarmService.getAlarmCountByDay(date);
        logger.info("data===={}", data.toString());
        if (null != data) {

          return AjaxResult.success(data);
      }
        //  数据表 v_alarmhis
        return AjaxResult.error();

    }

    @ApiOperation(value = "历史告警", notes = "", produces = "application/josn")
    @PostMapping("/getAlarmHis")
    public PageInfo<InfoEvent> getAlarmHis(String date, String pageindex) {

        //由字符串日期转化为date类型的日期
        //  Date parseDate = DateUtils.parseDate(date);
        logger.info("历史告警记录前一段传来的日期==={}",date);
        logger.info("历史告警记录前一段传来的页数==={}",pageindex);
        PageHelper.startPage(Integer.parseInt(pageindex), 7);
        logger.info("date为空={}", date.equals(""));
        List<InfoEvent> list = null;
        if (date.equals("")) {
            list = alarmService.getAlarmHis1();
        } else {
            list = alarmService.getAlarmHis(date);
        }

        PageInfo<InfoEvent> pageInfo = new PageInfo<>(list);
        return  pageInfo;
   }

    /**
     * 功能描述: <br>
     * 〈〉  告警--告警信息详情
     * @Param: [id, type]
     * @Return: com.ruoyi.common.core.domain.AjaxResult
     * @Author: 韩以虎
     * @Date: 2019/12/15 14:08
     */
    @ApiOperation(value = "告警信息详情", notes = "", produces = "application/josn")
    @PostMapping("/getAlarmInfo")
    public AjaxResult getAlarmInfo(String id) {


        InfoEvent data = alarmService.getAlarmReal(id);

       return AjaxResult.success(data) ;

    }



    @ApiOperation(value = "数据监测---数据曲线", notes = "", produces = "application/josn")
    @PostMapping("/getHisData")
    public AjaxResult getHisData(String tags, String date){
           logger.info("tags===={}，，，date===={}",tags,date);
        /*
        * public string GetHisData(string Tags, string sdate, string edate)
        {

            StringBuilder sb = new StringBuilder();
            RepositoryFactory repositoryFactory = new RepositoryFactory();

            var TagInfoS = repositoryFactory.BaseRepository().FindList<TagInfoEntity>("select * from TagInfo where TagKey in ('" + Tags.Replace(",", "','") + "')");


            var dataAll = repositoryFactory.BaseRepository().FindList<LiveDataEntity>("select * from V_His where TagKey in ('" + Tags.Replace(",", "','") + "')  and timestamp>='" + sdate + " 00:00:01' and  timestamp<='" + edate + " 23:59:59' order by timestamp");


            sb.Append("{");

            string legend = "";
            sb.Append("\"Series\":[");
            foreach (TagInfoEntity s in TagInfoS)
            {
                legend += ",\"" + s.TagName + "\"";

                string tagkey = s.TagKey;
                var data = from item in dataAll
                           where item.TagKey.ToLower() == tagkey.ToLower()
                           orderby item.timestamp
                           select item;

                var list = data.ToList<LiveDataEntity>();
                sb.Append(",{\"name\": \"" + s.TagName + "\",\"type\": \"line\",\"showSymbol\": \"false\",\"hoverAnimation\": \"false\",\"data\":[");
                foreach (LiveDataEntity ld in list)
                {
                    string name = ((DateTime)ld.timestamp).ToString("yyyy/M/d HH:mm:ss");
                    sb.Append(",{\"name\":\"" + name + "\",\"value\":[\"" + name + "\",\"" + ld.value + "\"]}");
                }
                sb.Append("]}");
            }
            sb.Append("]");
            legend = legend.Substring(1);
            sb.Append(",\"LegendData\":[" + legend + "]");
            sb.Append("}");
            return sb.ToString().Replace("[,", "[").Replace("{,", "{").Replace(",]", "]");
        }
        * */

        return AjaxResult.error();
    }


    @ApiOperation(value = "实时告警列表", notes = "", produces = "application/josn")
    @PostMapping("/getAlarmList")
    public PageInfo<VTagInfoAlarm> getAlarmList(String pageindex) {

        logger.info("获取的页数数据=={}==={}", pageindex);
        //  startPage();
        PageHelper.startPage(Integer.parseInt(pageindex), 7);
        List<VTagInfoAlarm> vTagInfoAlarms = alarmService.getTagInfoAlarm();
        PageInfo<VTagInfoAlarm> pageInfo = new PageInfo<VTagInfoAlarm>(vTagInfoAlarms);
        return pageInfo;
    }


    @ApiOperation(value = "实时数据列表", notes = "", produces = "application/josn")
    @PostMapping("/getRealData")
    public PageInfo<VLive> getRealData(String queryJson, String pageindex) {
      /* PageDomain pageDomain = new PageDomain();
       pageDomain.setPageNum(Integer.valueOf(pageindex));*/
        logger.info("获取的queryJson数据=={}",queryJson);
        logger.info("获取的页数数据=={}==={}",pageindex,queryJson);
       //  startPage();
        PageHelper.startPage(Integer.parseInt(pageindex), 7);
      List<VLive > vLive= alarmService.getRealData(queryJson);
        PageInfo<VLive> pageInfo = new PageInfo<VLive>(vLive);
        return pageInfo;

    }






}
