package com.ruoyi.api.service;

import com.ruoyi.system.domain.SysUser;
import org.springframework.stereotype.Service;

/**
 * @author hanyihu
 * @title  根据用户id获取用户信息接口
 * @date 2019/11/7 9:05
 */
@Service
public interface IUserService {

    /**
    * 获取用户信息
    */
    SysUser getUserInforById(String userId);

    /**
     * 修改密码
     */
    int updatePassword(String userId, String newPassword);


    /**
     * 上传头像
     */
    int uploadImg(String userId, String imgData);


    /**
     * 保存设置修改
     */
    int saveSetting(String userId, String userName, String userAvatar);
}




