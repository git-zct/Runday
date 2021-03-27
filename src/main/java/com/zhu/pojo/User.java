package com.zhu.pojo;

import java.util.Date;

public class User {
    private Integer id;

    private String username;

    private String password;

    private Integer gender;

    private String phone;

    private String description;

    private String profilePic;

    private Integer fans;

    private Integer following;

    private Integer rank;//体重

    private Integer scores;

    private String question;

    private String answer;

    private Date createTime;

    private Date updateTime;

    public User(Integer id, String username, String password, Integer gender, String phone, String description, String profilePic, Integer fans, Integer following, Integer rank, Integer scores, String question, String answer, Date createTime, Date updateTime) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.gender = gender;
        this.phone = phone;
        this.description = description;
        this.profilePic = profilePic;
        this.fans = fans;
        this.following = following;
        this.rank = rank;
        this.scores = scores;
        this.question = question;
        this.answer = answer;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public User() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic == null ? null : profilePic.trim();
    }

    public Integer getFans() {
        return fans;
    }

    public void setFans(Integer fans) {
        this.fans = fans;
    }

    public Integer getFollowing() {
        return following;
    }

    public void setFollowing(Integer following) {
        this.following = following;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Integer getScores() {
        return scores;
    }

    public void setScores(Integer scores) {
        this.scores = scores;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question == null ? null : question.trim();
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer == null ? null : answer.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}