package com.ruoyi.system.domain;

/**
 * @author hanyihu
 * @title 手机App版本
 * @date 2019/12/24 9:56
 */
public class AppInfo {

    private String AppId;
    private String url;
    private String version;
    private String remark;
    private String createTime;

    public String getAppId() {
        return AppId;
    }

    public void setAppId(String appId) {
        AppId = appId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
