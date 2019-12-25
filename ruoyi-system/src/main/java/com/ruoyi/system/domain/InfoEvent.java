package com.ruoyi.system.domain;

/**
 * @author hanyihu
 * @title 告警数据
 * @date 2019/12/24 14:47
 */
public class InfoEvent {

    private String id;
    private String times;
    private String systype;
    private String tagname;
    private String tagkey;
    private String device;
    private String subDevice;
    private int addr;
    private int val;
    private String info;
    private int event;
    private int classs;
    private String users;
    private int res;
    private String varinfo;
    private int confirm;
    private String cuser;
    private String ctime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return times;
    }

    public void setTime(String time) {
        this.times = time;
    }

    public String getSystype() {
        return systype;
    }

    public void setSystype(String systype) {
        this.systype = systype;
    }

    public String getTagname() {
        return tagname;
    }

    public void setTagname(String tagname) {
        this.tagname = tagname;
    }

    public String getTagkey() {
        return tagkey;
    }

    public void setTagkey(String tagkey) {
        this.tagkey = tagkey;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getSubDevice() {
        return subDevice;
    }

    public void setSubDevice(String subDevice) {
        this.subDevice = subDevice;
    }

    public int getAddr() {
        return addr;
    }

    public void setAddr(int addr) {
        this.addr = addr;
    }

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getEvent() {
        return event;
    }

    public void setEvent(int event) {
        this.event = event;
    }

    public int getClasss() {
        return classs;
    }

    public void setClasss(int classs) {
        this.classs = classs;
    }

    public String getUser() {
        return users;
    }

    public void setUser(String user) {
        this.users = user;
    }

    public int getRes() {
        return res;
    }

    public void setRes(int res) {
        this.res = res;
    }

    public String getVarinfo() {
        return varinfo;
    }

    public void setVarinfo(String varinfo) {
        this.varinfo = varinfo;
    }

    public int getConfirm() {
        return confirm;
    }

    public void setConfirm(int confirm) {
        this.confirm = confirm;
    }

    public String getCuser_() {
        return cuser;
    }

    public void setCuser_(String cuser_) {
        this.cuser = cuser_;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    @Override
    public String toString() {
        return "InfoEvent{" +
                "id='" + id + '\'' +
                ", time='" + times + '\'' +
                ", systype='" + systype + '\'' +
                ", tagname='" + tagname + '\'' +
                ", tagkey='" + tagkey + '\'' +
                ", device='" + device + '\'' +
                ", subDevice='" + subDevice + '\'' +
                ", addr=" + addr +
                ", val=" + val +
                ", info='" + info + '\'' +
                ", event=" + event +
                ", classs=" + classs +
                ", user='" + users + '\'' +
                ", res=" + res +
                ", varinfo='" + varinfo + '\'' +
                ", confirm=" + confirm +
                ", cuser_='" + cuser + '\'' +
                ", ctime='" + ctime + '\'' +
                '}';
    }
}
