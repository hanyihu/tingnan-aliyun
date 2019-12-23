package com.ruoyi.api.controller.user;

import com.ruoyi.api.service.ILoginService;
import com.ruoyi.api.service.IUserService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.redisManager.RedisUtil;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.framework.shiro.service.SysPasswordService;
import com.ruoyi.system.domain.SysUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authc.credential.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.UUID;

/**
 * @author hanyihu
 * @title  用户信息
 * @date 2019/11/6 16:34
 */
@Api("用户中心")
@RestController
@RequestMapping("/api/user")
public class UserControllerApi extends BaseController {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private SysPasswordService passwordService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private ILoginService loginService;
    /**
     * 功能描述: <br> 获取我的信息
     * 〈〉
     * @Param: [userId]
     * @Return: com.ruoyi.common.core.domain.AjaxResult
     * @Author: 韩以虎
     * @Date: 2019/11/8 11:42
     */
    @ApiOperation(value = "获取我的界面信息", notes = "token:用户令牌token", produces = "application/josn")
    @PostMapping("/getUserInfor")
    public AjaxResult getMyInfor(String userId){
        logger.info("userId=={}",userId);
       // token = "f42c9aa845c74014a8092cf918b6ecc7";
        //SysUser sysUser = (SysUser) redisUtil.get(token);
        SysUser sysUser = iUserService.getUserInforById(userId);
        logger.info("我的界面--用户信息=={}",sysUser);
        if(null != sysUser){
            return AjaxResult.success("操作成功！",sysUser);
        }

        return AjaxResult.error();
    }


    @ApiOperation(value = "我的--设置--修改手机号", notes = "userId:userId , oldMobile:旧手机号, mobile:新手机号", produces = "application/josn")
    @PostMapping("/updateMobile")
    public AjaxResult updateMobile(String userId, String oldMobile, String mobile) {

        //查找手机号是否存在
        SysUser userInfor = loginService.getUserInfor(oldMobile);
        if (userInfor == null) {
            return AjaxResult.error("该手机号不存在！");

        } else if (userInfor.getUserId() != Long.parseLong(userId)) {
            return AjaxResult.error("用户令牌不一致,请核对！");
        } else {
            iUserService.updateMobileById(userId, mobile);
            return AjaxResult.success("手机号修改成功！");
        }


    }


    /**
     * 功能描述: <br> 修改用户密码
     * 〈〉
     * @Param: [token, oldPassword, newPassword]
     * @Return: com.ruoyi.common.core.domain.AjaxResult
     * @Author: 韩以虎
     * @Date: 2019/11/12 10:12
     */
    @ApiOperation(value = "修改用户密码" , notes = "token:用户令牌；oldPassword:旧密码 ； newPassword:新密码  " ,produces = "application/json")
    @PostMapping(value = "/updatePassword")
    public AjaxResult updatePassword(String userId, String oldPassword, String newPassword) {

        //SysUser sysUser = (SysUser)redisUtil.get(token);
        SysUser sysUser = iUserService.getUserInforById(userId);

        if(null != sysUser){
            //对接受到的用户密码进行加密
            String encryptPassword = passwordService.encryptPassword(sysUser.getLoginName(), oldPassword, sysUser.getSalt());

            logger.info("从前端收到的密码=={}",encryptPassword);
            logger.info("token中获取的密码=={}",sysUser.getPassword());
            //判断旧密码是否正确
            if( !encryptPassword.equals(sysUser.getPassword()) ){
                return AjaxResult.error("旧密码输入错误！");
            }
            //修改密码
            encryptPassword = passwordService.encryptPassword(sysUser.getLoginName(), newPassword, sysUser.getSalt());
            int i = iUserService.updatePassword(String.valueOf(sysUser.getUserId()), encryptPassword);
            if(i>0){
                return AjaxResult.success("密码修改成功！");
            }

        }else {
            return AjaxResult.error("令牌过期！");
        }

        return AjaxResult.error("密码修改失败！");
    }


    /**
     * 功能描述: <br> 修改并上传用户头像
     * 〈〉
     * @Param: [token, imgData]
     * @Return: com.ruoyi.common.core.domain.AjaxResult
     * @Author: 韩以虎
     * @Date: 2019/11/12 15:04
     */
    @ApiOperation(value = "修改并上传头像", notes = " imgDatas:头像地址", produces = "application/json")
    @PostMapping("/uploadImg")

    public AjaxResult uploadImg(String imgDatas){
        logger.info("前端传来的imgDatas地址=={}",imgDatas);

        String userAvatar = photo(imgDatas);
        logger.info("头像存放路径=={}",userAvatar);

        return AjaxResult.success(userAvatar);
    }

    /**
     * 功能描述: <br> 保存设置修改
     * 〈〉
     * @Param: [token, userName]
     * @Return: com.ruoyi.common.core.domain.AjaxResult
     * @Author: 韩以虎
     * @Date: 2019/11/13 8:52
     */
    @ApiOperation(value = "保存设置修改", notes = "userId:用户令牌userId ； userName:用户名称 ；userAvatar:用户头像 ", produces = "application/json")
    @PostMapping("/saveSetting")
    public AjaxResult saveSetting(String userId, String userName, String userAvatar) {

        logger.info("前端传来的用户名=={}",userName);
        logger.info("前端传来的用户头像地址=={}",userAvatar);
        // SysUser sysUser = (SysUser) redisUtil.get(token);
        SysUser sysUser = iUserService.getUserInforById(userId);

        if(null != sysUser){
            int i=  iUserService.saveSetting(String.valueOf(sysUser.getUserId()),userName,userAvatar);
            if(i>0){
                return AjaxResult.success();
            }
        }
        return AjaxResult.error("保存失败！");
    }


    /**
     * 功能描述: <br> 退出当前账号
     * 〈〉
     *
     * @Param: [token]
     * @Return: com.ruoyi.common.core.domain.AjaxResult
     * @Author: 韩以虎
     * @Date: 2019/11/12 9:11
     */
    @ApiOperation(value = "退出当前账号", notes = "token:用户令牌token", produces = "application/json")
    @PostMapping("/logout")
    public AjaxResult logout(String userId) {
        logger.info("userId=={}", userId);
        //SysUser sysUser = (SysUser) redisUtil.get(userId);
        SysUser sysUser = iUserService.getUserInforById(userId);
        if (null != sysUser) {
            //注销token
            // redisUtil.del(token);
            return AjaxResult.success();
        }

        return AjaxResult.error();
    }


    /**
     * Base64解码为图片写入流
     * @param imgDatas

     * @return
     */
    public   String photo( String imgDatas) {

        String bs = "data:image/png;base64,";
        String base = imgDatas.replace(bs, "");

        BASE64Decoder decoder = new BASE64Decoder();
        String imgFilePath=null;
        try {
            //解码
            byte[] b = decoder.decodeBuffer(base);
            for (int i = 0; i < b.length; ++i) {
                // logger.info("b====={}",b);
                //调整异常数据
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }

            imgFilePath = createFile()+"\\"+DateUtils.getDate()+"-"+UUID.randomUUID().toString().replaceAll("-","")+ ".png";
            logger.info("imgFilePath==={}",imgFilePath);
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(b);
            out.flush();
            out.close();

        } catch (Exception e) {
           logger.info("返回异常=={}",e);
            return "error";
        }
        return imgFilePath;
    }


    /**
     * 功能描述: <br>  生成图片存放路径
     * 〈〉
     * @Param: []
     * @Return: java.lang.String
     * @Author: 韩以虎
     * @Date: 2019/11/21 10:10
     */
    public  String createFile() {

        //当前项目下路径
        File file = new File("");
        // String filePath = null;
        String path = null;
        try {
            // filePath= file.getCanonicalPath();
            //创建目录
            //把图片放到阿里云远程服务器中
            // path=filePath+"\\fileUpload\\headImg";

            //获取tomcat路径，把图片放入tomcat路径下
            String property = System.getProperty("catalina.home");
            logger.info("tomcat根目录为===={}", property);
            path = property + "\\webapps\\ROOT\\tn-image";
            file = new File(path);
            //如果目录不存在 则创建
            if(!file.exists()&&!file.isDirectory()){
                file.mkdirs();
            }

            return path;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    public static void main(String[] args) {
        String time = DateUtils.getDate();

        System.out.println(time);
    }
}

