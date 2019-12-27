package com.ruoyi.api.service;

import com.ruoyi.system.domain.SysUser;
import org.springframework.stereotype.Service;

/**
 * @author hanyihu
 * @title  登录验证接口
 * @date 2019/11/7 9:05
 */
@Service
public interface ILoginService {

/**
 *用户登录验证
 */
 SysUser login(String loginName, String password);

    /**
     *通过用户账号获取用户信息
     */
 SysUser getUserInfor(String loginName);

    /**
     * 注册用户信息
     * */
    void insertUserInfo(SysUser userInfor);

    /**
     * 通过账号修改密码
     */
    void updatePasswordByLoginName(String mobile, String password);

    /**
     * 修改cId
     */
    void updatecId(String cId, String userId);
}




