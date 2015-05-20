package com.jiangnan.dal.dataobject;

import java.util.Date;

/**
 * Created by GS-YL on 2015/3/15.
 */
public class DeliveryPostDO {

    private String userId;
    private String userName;
    private String university;
    private String specialty;
    private Integer jobId;
    private String jobName;
    private Integer flag;
    private Date time;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "DeliveryPost{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", university='" + university + '\'' +
                ", specialty='" + specialty + '\'' +
                ", jobId=" + jobId +
                ", jobName='" + jobName + '\'' +
                ", flag=" + flag +
                ", time=" + time +
                '}';
    }
}
