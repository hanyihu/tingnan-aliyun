package com.ruoyi.api.controller.information;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hanyihu
 * @title  综合信息中 调度事务管理
 * @date 2019/11/13 17:05
 */
@Api("综合信息 -- 调度事务管理")
@RestController
@RequestMapping("/api/dispatch")
public class DispatchControllerApi extends BaseController {


    @ApiOperation(value = "每天的告警数量", notes = "date", produces = "application/josn")
    @PostMapping("/getAlarmCountByDay")
    public AjaxResult getAlarmCountByDay(String date){

        //  数据表 v_alarmhis
        return AjaxResult.error();

    }





}
