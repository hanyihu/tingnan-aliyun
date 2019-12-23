package com.ruoyi.api.mapper;

import com.ruoyi.system.domain.SysUser;
import org.apache.ibatis.annotations.Param;

/**
 * @author hanyihu
 * @title 根据用户id获取用户信息
 * @date 2019/11/7 9:27
 */
public interface UserMapper {

    SysUser getUserInforById(@Param("userId") String userId);

    int updatePassWord(@Param("userId") String userId, @Param("newPassword") String newPassword);

    int uploadImg(@Param("userId") String userId, @Param("imgData") String imgData);

    int saveSetting(@Param("userId") String userId, @Param("userName") String userName, @Param("userAvatar") String userAvatar);

    void updateMobileById(@Param("userId") String userId, @Param("loginName") String loginName);
}
