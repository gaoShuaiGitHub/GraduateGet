package com.jiangnan.biz.job.home.impl;

import com.jiangnan.biz.job.home.JobManager;
import com.jiangnan.dal.dao.JobDao;
import com.jiangnan.dal.dataobject.DeliveryPost;
import com.jiangnan.dal.dataobject.Job;
import com.jiangnan.web.common.WebConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by GS-YL on 2015/2/27.
 */
@Component
public class JobManagerImpl implements JobManager {

    @Autowired
    private JobDao jobDao;

    @Override
    public List getJobList() {
        return jobDao.getJobList();
    }
    @Override
    public List getJobListWithPageSize(Integer startRow) {
        return jobDao.getJobListWithPageSize(startRow, WebConstant.PAGE_SIZE);
    }

    @Override
    public Job getJobInfoById(Integer id) {
        return jobDao.getJobInfoById(id);
    }

    @Override
    public List getJobListByUserId(String userId,Integer flag) {
        return jobDao.getJobListByUserId(userId,flag);
    }

    @Override
    public List getJobListByJobId(Integer jobId, Integer flag) {
        return jobDao.getJobInfoByJobId(jobId, flag);
    }

    @Override
    public List getJobInfoByUserId(String userId) {
        return jobDao.getJobInfoByUserId(userId);
    }

    @Override
    public Integer addDeliveryRecord(String userId, Integer jobId,Integer flag) {

        return jobDao.addDeliveryRecord(userId,jobId,flag);
    }

    @Override
    public List queryJobsBySearchText(String searchText, Integer startRow) {
        return jobDao.queryJobsBySearchText(searchText,startRow, WebConstant.PAGE_SIZE);
    }

    @Override
    public Integer postJob(Job job) {
        return jobDao.postJob(job);
    }

    @Override
    public List getDeliveryNumsCountJobName(String userId) {
        return jobDao.getDeliveryNumsCountJobName(userId);
    }

    @Override
    public List getMaxUniversity() {
        return jobDao.getMaxUniversity();
    }

    @Override
    public List getMaxJobName() {
        return jobDao.getMaxJobName();
    }

    @Override
    public DeliveryPost getCheckSendInfoByJobIdAndUserId(int jobId, String userId) {
        return jobDao.getCheckSendInfoByJobIdAndUserId(jobId,userId);
    }
}
