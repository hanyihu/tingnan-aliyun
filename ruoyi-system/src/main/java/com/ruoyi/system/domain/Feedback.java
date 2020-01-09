package com.ruoyi.system.domain;

import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author hanyihu
 * @title 信息反馈上传
 * @date 2020/1/3 9:22
 */
public class Feedback {

    /**
     * id
     */
    private Integer id;

    /**
     * 反馈人id
     */
    private Integer userId;

    /**
     * 反馈人昵称
     */
    private String userName;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 图片
     */
    private String images;

    /**
     * 时间
     */
    private String createTime;

    public Integer getId() {
        return id;
    }


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "id=" + id +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", images='" + images + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }
}
