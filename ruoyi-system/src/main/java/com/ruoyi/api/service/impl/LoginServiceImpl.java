package com.ruoyi.api.service.impl;

import com.ruoyi.api.mapper.LoginMapper;
import com.ruoyi.api.service.ILoginService;
import com.ruoyi.system.domain.SysUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author hanyihu
 * @title
 * @date 2019/11/7 9:17
 */
@Service
public class LoginServiceImpl implements ILoginService {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private LoginMapper loginMapper;

    /**
     * 功能描述: <br> 用户登录验证
     * 〈〉
     * @Param: [loginName, password]
     * @Return: boolean
     * @Author: 韩以虎
     * @Date: 2019/11/7 9:49
     */
    @Override
    public SysUser login(String loginName, String password) {

        return   loginMapper.login(loginName, password);
    }

    /**
     * 功能描述: <br>
     * 〈〉
     * @Param: [loginName]
     * @Return: com.ruoyi.system.domain.SysUser
     * @Author: 韩以虎
     * @Date: 2019/11/7 14:01
     */
    @Override
    public SysUser getUserInfor(String loginName) {
       return loginMapper.getUserInfor(loginName);
    }


    /**
     * 功能描述: <br> 注册用户信息
     * 〈〉
     * @Param: [userInfor]
     * @Return: void
     * @Author: 韩以虎
     * @Date: 2019/12/22 16:03
     */
    @Override
    public void insertUserInfo(SysUser userInfor) {

        loginMapper.insertUserInfo(userInfor);
    }
}
