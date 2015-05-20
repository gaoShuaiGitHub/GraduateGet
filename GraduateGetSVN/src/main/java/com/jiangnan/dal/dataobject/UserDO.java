package com.jiangnan.dal.dataobject;

/**
 * Created by GS-YL on 2015/2/23.
 * 用户个人信息对象
 */
public class UserDO {
    private Integer id;
    private String headPhoto;
    private String reallyName;
    private String email;
    private String university;
    private String sdescrip;
    private String qq;
    private Integer aiNums;
    private String userId;
    private String password;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHeadPhoto() {
        return headPhoto;
    }

    public void setHeadPhoto(String headPhoto) {
        this.headPhoto = headPhoto;
    }

    public String getReallyName() {
        return reallyName;
    }

    public void setReallyName(String reallyName) {
        this.reallyName = reallyName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getSdescrip() {
        return sdescrip;
    }

    public void setSdescrip(String sdescrip) {
        this.sdescrip = sdescrip;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public Integer getAiNums() {
        return aiNums;
    }

    public void setAiNums(Integer aiNums) {
        this.aiNums = aiNums;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "user{" +
                "id=" + id +
                ", headPhoto='" + headPhoto + '\'' +
                ", reallyName='" + reallyName + '\'' +
                ", email='" + email + '\'' +
                ", university='" + university + '\'' +
                ", sdescrip='" + sdescrip + '\'' +
                ", qq='" + qq + '\'' +
                ", aiNums=" + aiNums +
                ", userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
