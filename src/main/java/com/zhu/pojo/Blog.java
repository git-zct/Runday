package com.zhu.pojo;

import java.util.Date;

public class Blog {
    private Integer id;

    private Integer userId;

    private Integer stars;

    private Integer selfOnly;

    private String content;

    private String image;

    private Date createTime;

    public Blog(Integer id, Integer userId, Integer stars, Integer selfOnly, String content, String image, Date createTime) {
        this.id = id;
        this.userId = userId;
        this.stars = stars;
        this.selfOnly = selfOnly;
        this.content = content;
        this.image = image;
        this.createTime = createTime;
    }

    public Blog() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getStars() {
        return stars;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }

    public Integer getSelfOnly() {
        return selfOnly;
    }

    public void setSelfOnly(Integer selfOnly) {
        this.selfOnly = selfOnly;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image == null ? null : image.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}