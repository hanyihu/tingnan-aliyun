package com.ruoyi.api.push;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.AppMessage;
import com.gexin.rp.sdk.base.impl.ListMessage;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.exceptions.RequestException;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.style.Style0;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: tingnan-aliyun
 * @description: 个推demo
 * @author: 韩以虎
 * @create: 2020-06-19 16:17
 **/

public class GeTui {


    // STEP1：获取应用基本信息
    static String appId = "cfFrxVamAa6nR73j7G1rq2";
    static String appKey = "fYuwP9L0s99QzVyUmxE197";
    static String masterSecret = "fjkOaORTYx7JH1dOGh2CrA";
    static String host = "http://sdk.open.api.igexin.com/apiex.htm";
    static String CID = "5c18e71a52aca3b58e33bbc5635104e2";

    public static void main(String[] args) {
        pushToList();//批量推送
    }

    //发送给所有的App用户
    public static void pushToApp() {
        IGtPush push = new IGtPush(host, appKey, masterSecret);
        //先进行告警数据查询 是否有告警信息

        System.out.println("=============进入到推送========");
        Style0 style = new Style0();
        // STEP2：设置推送标题、推送内容

        System.out.println("标题====");
        System.out.println("内容====");

        style.setTitle("测试");
        style.setText("重大灾害预警测试消息推送");
        style.setLogo("push.png");  // 设置推送图标

        // STEP3：设置响铃、震动等推送效果
        style.setRing(true);  // 设置响铃
        style.setVibrate(true);  // 设置震动


        // STEP4：选择通知模板
        NotificationTemplate template = new NotificationTemplate();
        template.setAppId(appId);
        template.setAppkey(appKey);
        template.setTransmissionType(2);
        // template.setTransmissionContent("请输⼊您要透传的内容");
        template.setStyle(style);


        // STEP5：定义"AppMessage"类型消息对象,设置推送消息有效期等推送参数
        List<String> appIds = new ArrayList<String>();
        appIds.add(appId);
        AppMessage message = new AppMessage();
        message.setData(template);
        message.setAppIdList(appIds);
        message.setOffline(true);
        message.setOfflineExpireTime(24 * 1000 * 3600);  // 时间单位为毫秒

        // STEP6：执行推送
        IPushResult ret = push.pushMessageToApp(message);
        System.out.println(ret.getResponse().toString());
    }


    //个推 批量推送给指定的cid用户
    public static void pushToList() {
        IGtPush push = new IGtPush(host, appKey, masterSecret);

        NotificationTemplate template = TemplateDemo();

        ListMessage message = new ListMessage();
        message.setOffline(true);
        // 离线有效时间，单位为毫秒，可选
        message.setOfflineExpireTime(24 * 3600 * 1000);
        message.setData(template);
        message.setPushNetWorkType(0); // 可选。判断是否客户端是否wifi环境下推送，1为在WIFI环境下，0为不限制网络环境。

        List<Target> targets = new ArrayList<Target>();
        Target target = new Target();
        target.setAppId(appId);
        target.setClientId(CID);
//       target.setAlias(Alias);
        targets.add(target);

        IPushResult ret = null;
        try {
            String taskId = push.getContentId(message, "任务别名_LIST");
            ret = push.pushMessageToList(taskId, targets);
            //ret = push.pushMessageToSingle(message, target);
        } catch (RequestException e) {
            e.printStackTrace();

        }
        if (ret != null) {
            System.out.println(ret.getResponse().toString());
        } else {
            System.out.println("服务器响应异常");
        }
    }

    //个推 推送给单个cid用户
    public static void pushTosingle() {
        //向单个ClientID或别名用户推送消息。
        IGtPush push = new IGtPush(host, appKey, masterSecret);

        NotificationTemplate template = TemplateDemo();
        SingleMessage message = new SingleMessage();
        message.setOffline(true);
        // 离线有效时间，单位为毫秒，可选
        message.setOfflineExpireTime(24 * 3600 * 1000);
        message.setData(template);
        message.setPushNetWorkType(0); // 可选。判断是否客户端是否wifi环境下推送，1为在WIFI环境下，0为不限制网络环境。
        Target target = new Target();

        target.setAppId(appId);
        target.setClientId(CID);
        // 用户别名推送，cid和用户别名只能2者选其一
        // String alias = "个";
        // target.setAlias(alias);
        IPushResult ret = null;
        try {
            ret = push.pushMessageToSingle(message, target);
        } catch (RequestException e) {
            e.printStackTrace();
            ret = push.pushMessageToSingle(message, target, e.getRequestId());
        }
        if (ret != null) {
            System.out.println(ret.getResponse().toString());
        } else {
            System.out.println("服务器响应异常");
        }
    }


    public static NotificationTemplate TemplateDemo() {


        Style0 style = new Style0();
        // STEP2：设置推送标题、推送内容

        System.out.println("标题====");
        System.out.println("内容====");

        style.setTitle("测试");
        style.setText("重大灾害预警测试消息推送");
        style.setLogo("push.png");  // 设置推送图标

        // STEP3：设置响铃、震动等推送效果
        style.setRing(true);  // 设置响铃
        style.setVibrate(true);  // 设置震动

        NotificationTemplate template = new NotificationTemplate();
        template.setAppId(appId);
        template.setAppkey(appKey);
        template.setTransmissionType(2);
        // template.setTransmissionContent("请输⼊您要透传的内容");
        template.setStyle(style);
        return template;
    }


}
