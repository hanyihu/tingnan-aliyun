package com.ruoyi.common.push;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;

/**
 * @author hanyihu
 * @title  用于监听有没有httpsession，没有则创建
 * @date 2019/11/22 11:02
 */
@WebListener
public class RequestListener implements ServletRequestListener {

    @Override
    public void requestInitialized(ServletRequestEvent sre)  {
        //将所有request请求都携带上httpSession
        ((HttpServletRequest) sre.getServletRequest()).getSession();

    }
    public RequestListener() {
        // TODO Auto-generated constructor stub
    }

    @Override
    public void requestDestroyed(ServletRequestEvent arg0)  {
        // TODO Auto-generated method stub
    }
}
