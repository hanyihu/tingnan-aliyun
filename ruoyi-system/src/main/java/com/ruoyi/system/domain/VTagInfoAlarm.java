package com.ruoyi.system.domain;

/**
 * @author hanyihu
 * @title 实时告警
 * @date 2020/1/1 9:18
 */
public class VTagInfoAlarm {

    private String tagKey;
    private String tagName;
    private String systemType;
    private String value;
    private String timestamps;
    private String tagStatus;


    public String getTagKey() {
        return tagKey;
    }

    public void setTagKey(String tagKey) {
        this.tagKey = tagKey;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getSystemType() {
        return systemType;
    }

    public void setSystemType(String systemType) {
        this.systemType = systemType;
    }

    public String getValues() {
        return value;
    }

    public void setValues(String values) {
        this.value = values;
    }

    public String getTimestamps() {
        return timestamps;
    }

    public void setTimestamps(String timestamps) {
        this.timestamps = timestamps;
    }

    public String getTagStatus() {
        return tagStatus;
    }

    public void setTagStatus(String tagStatus) {
        this.tagStatus = tagStatus;
    }
}
