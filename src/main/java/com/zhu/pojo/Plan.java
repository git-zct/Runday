package com.zhu.pojo;

import java.util.Date;

public class Plan {
    private Integer id;

    private Integer userId;

    private String target;

    private Integer checked;

    private Integer score;

    private Date createTime;

    public Plan(Integer id, Integer userId, String target, Integer checked, Integer score, Date createTime) {
        this.id = id;
        this.userId = userId;
        this.target = target;
        this.checked = checked;
        this.score = score;
        this.createTime = createTime;
    }

    public Plan() {
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

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target == null ? null : target.trim();
    }

    public Integer getChecked() {
        return checked;
    }

    public void setChecked(Integer checked) {
        this.checked = checked;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}