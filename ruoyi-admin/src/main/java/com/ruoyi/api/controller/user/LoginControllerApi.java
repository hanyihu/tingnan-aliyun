package com.ruoyi.api.controller.user;

import com.ruoyi.api.service.ILoginService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.redisManager.RedisTokenManager;
import com.ruoyi.common.redisManager.RedisUtil;
import com.ruoyi.framework.shiro.service.SysPasswordService;
import com.ruoyi.system.domain.SysUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.UUID;

/**
 * @author hanyihu
 * @title  用户登录注册验证
 * @date 2019/11/6 16:34
 */
@Api("用户中心")
@RestController
@RequestMapping("/api/login")
public class LoginControllerApi extends BaseController {

    @Autowired
    private ILoginService loginService;

    @Autowired
    private SysPasswordService passwordService;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 功能描述: <br>  用户登录验证
     * 〈〉
     * @Param: [userName, passWord]
     * @Return: com.ruoyi.common.core.domain.AjaxResult
     * @Author: 韩以虎
     * @Date: 2019/11/7 8:55
     */
    @ApiOperation(value = "登录验证", notes = "loginName:用户登录账号,password：用户登录密码", produces = "application/josn")
    @PostMapping("")
    public AjaxResult login(String loginName, String password) {

        //用户登录时进行token验证，是否存在 若存在，注销token
        //redisUtil.get("")

         if((loginName != null || loginName != "") && (password != null || password != "")){
             //根据用户登录中账号取出用户信息
             SysUser sysUser= loginService.getUserInfor(loginName);
             logger.info("获取的用户信息为=={}",sysUser);

             //对接受到的用户密码进行加密
             String passwordRevice = passwordService.encryptPassword(loginName, password, sysUser.getSalt());

             logger.info("loginName=={}-----------password=={}------------hash=={}",loginName,password,passwordRevice);

             //判断用户登录账号和密码是否相同
             if(loginName.equals(sysUser.getLoginName()) && passwordRevice.equals(sysUser.getPassword())){
                 String  userId = sysUser.getUserId().toString();

                 /* RedisTokenManager redisTokenManager  = new RedisTokenManager();

                //生成token，并返到前端中
                 String token = redisTokenManager.createToken();
                 logger.info("获取的token为=={}",token);

                //把用户信息放入token中
                 redisUtil.set(token,sysUser,60*60*24);
                 //设置缓存并设置过期时间 秒
                // redisUtil.set(String.valueOf(sysUser.getUserId()),token,60*60*24);
*/
                 return AjaxResult.success("验证成功！",userId);
             }
         }


         return AjaxResult.error();
    }


    /**
     * 功能描述: <br>  用户注册
     * 〈〉
     * @Param: [mobile, pwd]
     * @Return: com.ruoyi.common.core.domain.AjaxResult
     * @Author: 韩以虎
     * @Date: 2019/12/22 15:40
     */
    @ApiOperation(value = "用户注册", notes = "mobile:用户注册账号,pwd：用户注册密码", produces = "application/josn")
    @PostMapping("/register")
    public AjaxResult register(String mobile, String userName, String pwd){

        //根据注册手机号去查找注册手机号是否重复
        SysUser userInfor = loginService.getUserInfor(mobile);
        if(userInfor==null){

           SysUser sysUser = new SysUser();
            //对接受到的用户密码进行加密
            String password = passwordService.encryptPassword(mobile, pwd, "111111");

           // Long userId = 10000L+ (new Random()).nextLong();
            //sysUser.setUserId(userId);
            sysUser.setLoginName(mobile);
            sysUser.setPassword(password);
            sysUser.setSalt("111111");
            sysUser.setUserName(userName);
            logger.info("用户信息==={}",sysUser.toString());
            loginService.insertUserInfo(sysUser);
           return AjaxResult.success("注册成功！");
        }

        return AjaxResult.error("该手机号已注册,请直接登录！");
    }

}

