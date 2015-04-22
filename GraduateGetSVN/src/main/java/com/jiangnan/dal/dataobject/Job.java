package com.jiangnan.dal.dataobject;


import javax.print.attribute.standard.DateTimeAtCompleted;
import java.util.Date;

/**
 * Created by GS-YL on 2015/2/27.
 */
public class Job {
    private Integer id;
    private String jobName;
    private String company;
    private Integer minMoney;
    private Integer maxMoney;
    private String address;
    private String jobReq;//职位要求
    private String reqSummary;//职位要求摘要
    private String userId;
    private String university;//所在大学
    private String eduBackground;//学历
    private String specialty;//专业

    private Date time;//发帖时间


    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getJobReq() {
        return jobReq;
    }

    public void setJobReq(String jobReq) {
        this.jobReq = jobReq;
    }

    public String getReqSummary() {
        return reqSummary;
    }

    public void setReqSummary(String reqSummary) {
        this.reqSummary = reqSummary;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getEduBackground() {
        return eduBackground;
    }

    public void setEduBackground(String eduBackground) {
        this.eduBackground = eduBackground;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Job{" +
                "id=" + id +
                ", jobName='" + jobName + '\'' +
                ", company='" + company + '\'' +
                ", minMoney=" + minMoney +
                ", maxMoney=" + maxMoney +
                ", address='" + address + '\'' +
                ", jobReq='" + jobReq + '\'' +
                ", reqSummary='" + reqSummary + '\'' +
                ", userId='" + userId + '\'' +
                ", university='" + university + '\'' +
                ", eduBackground='" + eduBackground + '\'' +
                ", specialty='" + specialty + '\'' +
                ", time=" + time +
                '}';
    }
}
