package com.ruoyi.system.domain;

/**
 * @author hanyihu
 * @title  视图 告警实时数据
 * @date 2019/11/14 14:18
 */
public class VLive {

    private String tagName;
    private String tagType;
    private String dataType;
    private String unit;
    private String systemType;
    private String rangMin;
    private String rangMax;
    private String lowAlarm;
    private String alarmStatus;
    private String tagKey;
    private String value;
    private String timestamp;
    private String sendSms;
    private String dealAlarm;
    private String digCount;
    private String valueType;

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getTagType() {
        return tagType;
    }

    public void setTagType(String tagType) {
        this.tagType = tagType;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getSystemType() {
        return systemType;
    }

    public void setSystemType(String systemType) {
        this.systemType = systemType;
    }

    public String getRangMin() {
        return rangMin;
    }

    public void setRangMin(String rangMin) {
        this.rangMin = rangMin;
    }

    public String getRangMax() {
        return rangMax;
    }

    public void setRangMax(String rangMax) {
        this.rangMax = rangMax;
    }

    public String getAlarmStatus() {
        return alarmStatus;
    }

    public void setAlarmStatus(String alarmStatus) {
        this.alarmStatus = alarmStatus;
    }

    public String getTagKey() {
        return tagKey;
    }

    public void setTagKey(String tagKey) {
        this.tagKey = tagKey;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSendSms() {
        return sendSms;
    }

    public void setSendSms(String sendSms) {
        this.sendSms = sendSms;
    }

    public String getDealAlarm() {
        return dealAlarm;
    }

    public void setDealAlarm(String dealAlarm) {
        this.dealAlarm = dealAlarm;
    }

    public String getDigCount() {
        return digCount;
    }

    public void setDigCount(String digCount) {
        this.digCount = digCount;
    }

    public String getValueType() {
        return valueType;
    }

    public void setValueType(String valueType) {
        this.valueType = valueType;
    }

    public String getLowAlarm() {
        return lowAlarm;
    }

    public void setLowAlarm(String lowAlarm) {
        this.lowAlarm = lowAlarm;
    }
}
