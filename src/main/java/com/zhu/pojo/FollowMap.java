package com.zhu.pojo;

import java.util.Date;

public class FollowMap {
    private Integer id;

    private Integer userId;

    private Integer followerId;

    private Integer intimacy;

    private Date createTime;

    public FollowMap(Integer id, Integer userId, Integer followerId, Integer intimacy, Date createTime) {
        this.id = id;
        this.userId = userId;
        this.followerId = followerId;
        this.intimacy = intimacy;
        this.createTime = createTime;
    }

    public FollowMap() {
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

    public Integer getFollowerId() {
        return followerId;
    }

    public void setFollowerId(Integer followerId) {
        this.followerId = followerId;
    }

    public Integer getIntimacy() {
        return intimacy;
    }

    public void setIntimacy(Integer intimacy) {
        this.intimacy = intimacy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}