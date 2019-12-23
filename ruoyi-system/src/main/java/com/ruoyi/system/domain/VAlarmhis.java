package com.ruoyi.system.domain;

/**
 * @author hanyihu
 * @title  告警信息
 * @date 2019/11/15 11:37
 */
public class VAlarmhis {

    private  String id;
    private  String tagName;
    private  String tagDesc;
    private  String tagType;
    private  String dataType;
    private  String unit;
    private  String systemType;
    private  String rangMin;
    private  String rangMax;
    private  String tagKey;
    private  String lowAlarm;
    private  String startValue;
    private  String avgValue;
    private  String endValue;

    private  String maxValue;

    private  String duration;


    private String startTime;


    private  String endTime;


    private  String createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getTagDesc() {
        return tagDesc;
    }

    public void setTagDesc(String tagDesc) {
        this.tagDesc = tagDesc;
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

    public String getTagKey() {
        return tagKey;
    }

    public void setTagKey(String tagKey) {
        this.tagKey = tagKey;
    }

    public String getLowAlarm() {
        return lowAlarm;
    }

    public void setLowAlarm(String lowAlarm) {
        this.lowAlarm = lowAlarm;
    }

    public String getStartValue() {
        return startValue;
    }

    public void setStartValue(String startValue) {
        this.startValue = startValue;
    }

    public String getAvgValue() {
        return avgValue;
    }

    public void setAvgValue(String avgValue) {
        this.avgValue = avgValue;
    }

    public String getEndValue() {
        return endValue;
    }

    public void setEndValue(String endValue) {
        this.endValue = endValue;
    }

    public String getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(String maxValue) {
        this.maxValue = maxValue;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
