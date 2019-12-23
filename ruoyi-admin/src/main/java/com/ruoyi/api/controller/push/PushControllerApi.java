package com.ruoyi.api.controller.push;

import com.ruoyi.api.websocket.WebSocketServer;
import com.ruoyi.common.core.domain.AjaxResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author hanyihu
 * @title
 * @date 2019/11/28 11:33
 */
@Api("消息推送")
@RestController
@RequestMapping(value = "/api/push")
public class PushControllerApi {

    @ApiOperation(value = "消息推送", notes = "", produces = "application/josn")
    @PostMapping("/test")
    public AjaxResult pushVideoListToWeb() {
        System.out.println("dfdfdfdf");
        Map<String,Object> result =new HashMap<String,Object>();

        try {
            WebSocketServer.sendInfo("有新客户呼入,sltAccountId:");
            result.put("operationResult", true);
            return AjaxResult.success(result);
        }catch (IOException e) {
            result.put("operationResult", true);
        }

        return AjaxResult.error();
    }


    /**
     * 简单通知
     */
   /* private void sendNotification9() {
        Notification.Builder mBuilder = new Notification.Builder(this.getApplicationContext())
                .setSound(android.provider.Settings.System.DEFAULT_NOTIFICATION_URI)
                .setSmallIcon(R.mipmap.ic_launcher)                                        //设置通知的图标
                .setTicker("有新消息呢9")                                                    //设置状态栏的标题
                .setContentTitle("这个是标题9")                                              //设置标题
                .setContentText("这个是内容9")                                                //消息内容
                .setDefaults(Notification.DEFAULT_ALL)                                      //设置默认的提示音
                .setOngoing(false)                                                          //让通知左右滑的时候不能取消通知
                .setAutoCancel(true);                                                        //打开程序后图标消失
        Notification notification = mBuilder.build();
        //设置 Notification 的 flags = FLAG_NO_CLEAR
        //FLAG_NO_CLEAR 表示该通知不能被状态栏的清除按钮给清除掉,也不能被手动清除,但能通过 cancel() 方法清除
        //flags 可以通过 |= 运算叠加效果
        notification.flags |= Notification.FLAG_NO_CLEAR;

        //获取NotificationManager 对象
        mNotificationManager.notify(9, notification);
    }*/

}
