package com.ruoyi.web.controller.feedback;

import com.ruoyi.api.service.IAlarmService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.shiro.service.SysPasswordService;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.system.domain.Feedback;
import com.ruoyi.system.domain.SysUser;
import com.ruoyi.system.service.ISysPostService;
import com.ruoyi.system.service.ISysRoleService;
import com.ruoyi.system.service.ISysUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 用户信息
 *
 * @author ruoyi
 */
@Controller
@RequestMapping("/feedback/feedback")
public class FeedBackController extends BaseController {
    private String prefix = "feedback/feedback";

    @Autowired
    private IAlarmService alarmService;


    @RequiresPermissions("feedback:feedback:view")
    @GetMapping()
    public String feed() {
        return prefix + "/feed";
    }

    @RequiresPermissions("feedback:feedback:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Feedback feedback) {
        logger.info("获取的信息==={}", feedback.toString());
        startPage();
        List<Feedback> list = alarmService.getFeedBackList(feedback);
        return getDataTable(list);
    }


    @RequiresPermissions("feedback:feedback:remove")
    @Log(title = "删除信息", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        try {
            return toAjax(alarmService.deleteFeedBackByIds(ids));
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

}