package com.ruoyi.api.controller.monitor;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ruoyi.api.service.IAlarmService;
import com.ruoyi.api.service.IUserService;
import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.config.Global;
import com.ruoyi.common.config.ServerConfig;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.PageDomain;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.system.domain.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @author hanyihu
 * @title  监测数据模块接口
 * @date 2019/11/13 15:35
 */
@Api("监测数据")
@RestController
@RequestMapping("/api/alarm")
public class AlarmControllerApi extends BaseController {

    @Autowired
    private IAlarmService alarmService;

    @Autowired
    private IUserService userService;
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


    /**
     * 功能描述: <br> 多图上传
     * 〈〉
     *
     * @Param: [token, imgData]
     * @Return: com.ruoyi.common.core.domain.AjaxResult
     * @Author: 韩以虎
     * @Date: 2019/11/12 15:04
     */
    @ApiOperation(value = "多图上传", notes = " imgDatas:图片", produces = "application/json")
    @PostMapping("/imgupload")

    public AjaxResult imgupload(@RequestParam(value = "imgList") List<String> imgList, String title, String content, String userId) {
        logger.info("前端传来的imgDatas地址=={}", imgList);
        logger.info("前端传来的userId=={}==={}==={}", userId, title, content);
        //截取[]
        String img = imgList.toString().substring(1, imgList.toString().length() - 1);

        SysUser sysUser = userService.getUserInforById(userId);
        Feedback feedback = new Feedback();
        feedback.setUserId(Integer.parseInt(userId));
        feedback.setTitle(title);
        feedback.setContent(content);
        feedback.setUserName(sysUser.getUserName());

        feedback.setImages(img);
        logger.info("图片========={}", img);
        alarmService.insertFeedBack(feedback);

       /* for (int i = 0; i <imgList.size() ; i++) {
            logger.info("图片=={}",imgList.get(i));
        }*/
        //String userAvatar = photo(imgDatas);
        // logger.info("头像存放路径=={}",userAvatar);

        return AjaxResult.success();
    }

    /**
     * 功能描述: <br> 多图上传
     * 〈〉
     *
     * @Param: [token, imgData]
     * @Return: com.ruoyi.common.core.domain.AjaxResult
     * @Author: 韩以虎
     * @Date: 2019/11/12 15:04
     */
    @ApiOperation(value = "多图上传", notes = " imgDatas:图片", produces = "application/json")
    @PostMapping("/imgupload1")
    @ResponseBody
    public String imgupload1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");// 跨域

        String imgPath = "";
        CommonsMultipartResolver cmr = new CommonsMultipartResolver(request.getServletContext());
        if (cmr.isMultipart(request)) {
            MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) (request);
            Iterator<String> files = mRequest.getFileNames();

            while (files.hasNext()) {
                MultipartFile mFile = mRequest.getFile(files.next());
                if (mFile != null) {
                    // 上传文件路径
                    String filePath = Global.getUploadPath();
                    // 上传并返回新文件名称

                    String fileName = FileUploadUtils.upload(filePath, mFile);
                    //String url = serverConfig.getUrl() + fileName;
                    String url = "http://47.108.30.209:8085" + fileName;

                    logger.info("filepath==={}", fileName);
                    logger.info("url==={}", url);

                    imgPath += url + "##";
                }
            }

        }
        return imgPath;
    }


    @ApiOperation(value = "信息反馈", notes = "", produces = "application/josn")
    @PostMapping("/getFeedBack")
    public PageInfo<Feedback> getFeedBack(String pageindex) {

        logger.info("获取的页数数据=={}==={}", pageindex);
        //  startPage();
        PageHelper.startPage(Integer.parseInt(pageindex), 8);
        List<Feedback> feedbacks = alarmService.getFeedBack();
        PageInfo<Feedback> pageInfo = new PageInfo<Feedback>(feedbacks);
        return pageInfo;
    }


    @ApiOperation(value = "根据id获取信息反馈详情", notes = "", produces = "application/josn")
    @PostMapping("/getFeedBackById")
    public AjaxResult getFeedBackById(String id) {

        logger.info("获取的id数据=={}==={}", id);

        Feedback feedbacks = alarmService.getFeedBackById(id);
        if (feedbacks != null) {

            return AjaxResult.success(feedbacks);
        }
        return AjaxResult.error("获取详情信息失败！");
    }




}
