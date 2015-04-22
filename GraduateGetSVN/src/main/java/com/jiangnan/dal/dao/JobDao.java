package com.jiangnan.dal.dao;

import com.jiangnan.dal.dataobject.DeliveryPost;
import com.jiangnan.dal.dataobject.Job;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by GS-YL on 2015/2/23.
 */
@Component
public interface JobDao {
    public List getJobList();
    public List getJobListWithPageSize(Integer startRow,Integer pageSize);
    public Job getJobInfoById(Integer id);
    public Integer addDeliveryRecord(String userId,Integer jobId,Integer flag);
    public List queryJobsBySearchText(String searchText,Integer startRow,Integer pageSize);
    public Integer  postJob(Job job);
    public List getJobListByUserId(String userId,Integer flag);
    public List getJobInfoByJobId(Integer jobId,Integer flag);
    public List getJobInfoByUserId(String userId);
    public List getDeliveryNumsCountJobName(String userId);
    public List getMaxUniversity();
    public List getMaxJobName();
    public DeliveryPost getCheckSendInfoByJobIdAndUserId(Integer jobId,String userId);

}
