package com.ruoyi.common.push;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;
import javax.websocket.server.ServerEndpointConfig.Configurator;

/**
 * @author hanyihu
 * @title  将http request的session 存入websocket的session内
 * @date 2019/11/22 10:58
 */
public class HttpSessionConfigurator extends Configurator {

    @Override
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {

        // 获取当前Http连接的session
        HttpSession httpSession = (HttpSession) request.getHttpSession();
        // 将http session信息注入websocket session
        sec.getUserProperties().put(HttpSession.class.getName(), httpSession);
    }

}
