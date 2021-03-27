package com.zhu.pojo;

import java.util.Date;

public class Comment {
    private Integer id;

    private Integer blogId;

    private Integer userId;

    private String content;

    private String image;

    private Date createTime;

    public Comment(Integer id, Integer blogId, Integer userId, String content, String image, Date createTime) {
        this.id = id;
        this.blogId = blogId;
        this.userId = userId;
        this.content = content;
        this.image = image;
        this.createTime = createTime;
    }

    public Comment() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBlogId() {
        return blogId;
    }

    public void setBlogId(Integer blogId) {
        this.blogId = blogId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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