package com.jiangnan.dal.dao.ibatis;

import com.jiangnan.biz.user.UserManager;
import com.jiangnan.dal.dao.JobDao;

import com.jiangnan.dal.dataobject.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by GS-YL on 2015/2/25.
 */
@Component
public class IbatisJobDao extends SqlMapClientDaoSupport implements JobDao {

    @Autowired
    private UserManager userManager;

    @Override
    public List<JobDO> getJobList() {
        return (List<JobDO>)getSqlMapClientTemplate().queryForList("selectJobList", null);
    }

    @Override
    public List getJobListWithPageSize(Integer startRow,Integer pageSize) {
        Map map=new HashMap();
        map.put("startRow",startRow);
        map.put("pageSize",pageSize);
        return getSqlMapClientTemplate().queryForList("getJobListWithPageSize", map);
    }

    @Override
    public JobDO getJobInfoById(Integer id) {
        JobDO jobDO = new JobDO();
        jobDO.setId(id);
        return (JobDO) getSqlMapClientTemplate().queryForObject("selectJobInfoById", jobDO);
    }

    @Override
    public Integer addDeliveryRecord(String userId, Integer jobId,Integer flag) {
        DeliveryPostDO deliveryPostDO = new DeliveryPostDO();
        JobDO jobDO =getJobInfoById(jobId);
        ResumeDO userResumeDO =userManager.getUserResumeByUserId(userId);
        deliveryPostDO.setJobId(jobId);
        deliveryPostDO.setUserId(userId);
        deliveryPostDO.setJobName(jobDO.getJobName());
        deliveryPostDO.setUserName(userResumeDO.getName());
        deliveryPostDO.setUniversity(userResumeDO.getUniversity());
        deliveryPostDO.setSpecialty(userResumeDO.getSpecialty());
        deliveryPostDO.setFlag(flag);
        return getSqlMapClientTemplate().update("addDeliveryRecord", deliveryPostDO);
    }

    @Override
    public List queryJobsBySearchText(String searchText, Integer startRow, Integer pageSize) {

        Map map=new HashMap();
        map.put("startRow",startRow);
        map.put("searchText",searchText);
        map.put("pageSize",pageSize);
        return getSqlMapClientTemplate().queryForList("queryJobsBySearchText", map);
    }


    @Override
    public Integer postJob(JobDO jobDO) {
        return getSqlMapClientTemplate().update("postJob", jobDO);
    }

    @Override
    public List getJobListByUserId(String userId, Integer flag) {
        DeliveryPostDO deliveryPostDO = new DeliveryPostDO();
        deliveryPostDO.setUserId(userId);
        deliveryPostDO.setFlag(flag);
        List<DeliveryPostDO> deliveryPostDO1 = (List<DeliveryPostDO>) getSqlMapClientTemplate().queryForList("queryJobListByUserId", deliveryPostDO);
        List<JobDO> jobDOs = new ArrayList<JobDO>();
        if (deliveryPostDO1 != null) {
            for (int i = 0; i < deliveryPostDO1.size(); i++) {
                JobDO jobDO = getJobInfoById(deliveryPostDO1.get(i).getJobId());
                if (jobDO != null) {
                    jobDO.setTime(deliveryPostDO1.get(i).getTime());//设置投递时间
                    jobDOs.add(jobDO);
                }
            }
            return jobDOs;
        } else {
            return null;
        }
    }

    @Override
    public List getJobInfoByJobId(Integer jobId, Integer flag) {
        DeliveryPostDO deliveryPostDO =new DeliveryPostDO();
        deliveryPostDO.setJobId(jobId);
        deliveryPostDO.setFlag(flag);
        return getSqlMapClientTemplate().queryForList("getJobInfoByJobId", deliveryPostDO);
    }

    @Override
    public List getJobInfoByUserId(String userId) {
        JobDO jobDO =new JobDO();
        jobDO.setUserId(userId);
        return getSqlMapClientTemplate().queryForList("getJobInfoByUserId", jobDO);
    }

    @Override
    public List<DeliveryPostDO> getDeliveryNumsCountJobName(String userId) {
        DeliveryPostDO deliveryPostDO =new DeliveryPostDO();
        deliveryPostDO.setUserId(userId);
        deliveryPostDO.setFlag(1);
        return (List<DeliveryPostDO>) getSqlMapClientTemplate().queryForList("deliveryNumsCountJobName", deliveryPostDO);
    }

    @Override
    public List getMaxUniversity() {
        return  getSqlMapClientTemplate().queryForList("getMaxUniversity",null);
    }
    @Override
    public List getMaxJobName() {
        return  getSqlMapClientTemplate().queryForList("getMaxJobName",null);
    }

    @Override
    public DeliveryPostDO getCheckSendInfoByJobIdAndUserId(Integer jobId, String userId) {
        Map map=new HashMap();
        map.put("jobId",jobId);
        map.put("userId",userId);
        return (DeliveryPostDO) getSqlMapClientTemplate().queryForObject("getCheckSendInfoByJobIdAndUserId",map);
    }

    @Override
    public List<ProbabilityListDO> getProbabilityList() {
        return (List<ProbabilityListDO>) getSqlMapClientTemplate().queryForList("probabilityList.getProbabilityList");
    }
}
