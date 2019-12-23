package com.ruoyi.api.service.impl;

import com.ruoyi.api.mapper.UserMapper;
import com.ruoyi.api.service.IUserService;
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
public class UserServiceImpl implements IUserService {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserMapper userMapper;



    /**
     * 功能描述: <br>
     * 〈〉
     * @Param: [loginName]
     * @Return: com.ruoyi.system.domain.SysUser
     * @Author: 韩以虎
     * @Date: 2019/11/7 14:01
     */
    @Override
    public SysUser getUserInforById(String userId) {
       return userMapper.getUserInforById(userId);
    }

    /**
     * 修改密码
     * */
    @Override
    public int updatePassword(String userId ,String newPassword) {
       return userMapper.updatePassWord(userId,newPassword);
    }

    @Override
    public int uploadImg(String userId, String imgData) {
        return userMapper.uploadImg(userId,imgData);
    }

    @Override
    public int saveSetting(String userId, String userName, String  userAvatar) {
        return userMapper.saveSetting(userId,userName,userAvatar);
    }
}
