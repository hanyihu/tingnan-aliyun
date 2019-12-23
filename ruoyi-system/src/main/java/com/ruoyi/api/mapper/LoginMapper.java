package com.ruoyi.api.mapper;

import com.ruoyi.system.domain.SysUser;
import org.apache.ibatis.annotations.Param;

/**
 * @author hanyihu
 * @title 用户登录验证
 * @date 2019/11/7 9:27
 */
public interface LoginMapper {


    SysUser login(@Param("loginName") String loginName, @Param("password") String password);

    SysUser getUserInfor(@Param("loginName") String loginName);

    void insertUserInfo(SysUser userInfor);

    void updatePasswordByLoginName(@Param("loginName") String loginName, @Param("password") String password);
}
