package com.jiangnan.dal.dataobject;

import java.util.Date;

/**
 * Created by gaoshuai.gs on 2015/3/30.
 * 个人简历对象
 */
public class ResumeDO {
    private String userId;
    private String name;
    private String phone;
    private String email;
    private String birthday;
    private String company;
    private String city;
    private Integer minMoney;
    private Integer maxMoney;
    private String experience;
    private String eduBackground;
    private String university;
    private String specialty;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getMinMoney() {
        return minMoney;
    }

    public void setMinMoney(Integer minMoney) {
        this.minMoney = minMoney;
    }

    public Integer getMaxMoney() {
        return maxMoney;
    }

    public void setMaxMoney(Integer maxMoney) {
        this.maxMoney = maxMoney;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getEduBackground() {
        return eduBackground;
    }

    public void setEduBackground(String eduBackground) {
        this.eduBackground = eduBackground;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    @Override
    public String toString() {
        return "Resume{" +
                "userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", birthday='" + birthday + '\'' +
                ", company='" + company + '\'' +
                ", city='" + city + '\'' +
                ", minMoney=" + minMoney +
                ", maxMoney=" + maxMoney +
                ", experience='" + experience + '\'' +
                ", eduBackground='" + eduBackground + '\'' +
                ", university='" + university + '\'' +
                ", specialty='" + specialty + '\'' +
                '}';
    }
}
