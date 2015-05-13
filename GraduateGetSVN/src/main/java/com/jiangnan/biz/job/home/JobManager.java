package com.jiangnan.biz.job.home;

import com.jiangnan.dal.dataobject.AiResultDO;
import com.jiangnan.dal.dataobject.DeliveryPostDO;
import com.jiangnan.dal.dataobject.JobDO;
import com.jiangnan.dal.dataobject.ResumeDO;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by GS-YL on 2015/2/27.
 */
@Component
public interface JobManager {
    //获得职位
    public List getJobList();
    public List getJobListWithPageSize(Integer stratRow);

    public JobDO getJobInfoById(Integer id);

    public List getJobListByUserId(String userId,Integer flag);

    public List getJobListByJobId(Integer jobId,Integer flag);

    public List getJobInfoByUserId(String userId);

    public Integer addDeliveryRecord(String userId,Integer jobId,Integer flag);

    public List queryJobsBySearchText(String searchText,Integer startRow);

    public Integer postJob(JobDO jobDO);

    public List getDeliveryNumsCountJobName(String userId);

    public List getMaxUniversity();

    public List getMaxJobName();

    public DeliveryPostDO getCheckSendInfoByJobIdAndUserId(int jobId,String userId);

    public List<AiResultDO<JobDO>> calculationMatching(ResumeDO resumeDO);
}
