package com.jiangnan.dal.dao;

import com.jiangnan.dal.dataobject.DeliveryPostDO;
import com.jiangnan.dal.dataobject.JobDO;
import com.jiangnan.dal.dataobject.ProbabilityListDO;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by GS-YL on 2015/2/23.
 */
@Component
public interface JobDao {
    public List<JobDO> getJobList();
    public List getJobListWithPageSize(Integer startRow,Integer pageSize);
    public JobDO getJobInfoById(Integer id);
    public Integer addDeliveryRecord(String userId,Integer jobId,Integer flag);
    public List queryJobsBySearchText(String searchText,Integer startRow,Integer pageSize);
    public Integer  postJob(JobDO jobDO);
    public List getJobListByUserId(String userId,Integer flag);
    public List getJobInfoByJobId(Integer jobId,Integer flag);
    public List getJobInfoByUserId(String userId);
    public List<DeliveryPostDO> getDeliveryNumsCountJobName(String userId);
    public List getMaxUniversity();
    public List getMaxJobName();
    public DeliveryPostDO getCheckSendInfoByJobIdAndUserId(Integer jobId,String userId);
    public List<ProbabilityListDO> getProbabilityList();
}
