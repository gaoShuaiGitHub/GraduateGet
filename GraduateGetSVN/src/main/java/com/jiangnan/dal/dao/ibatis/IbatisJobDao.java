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
    public List getJobList() {

        return getSqlMapClientTemplate().queryForList("selectJobList", null);
    }

    @Override
    public List getJobListWithPageSize(Integer startRow,Integer pageSize) {
        Map map=new HashMap();
        map.put("startRow",startRow);
        map.put("pageSize",pageSize);
        return getSqlMapClientTemplate().queryForList("getJobListWithPageSize", map);
    }

    @Override
    public Job getJobInfoById(Integer id) {
        Job job = new Job();
        job.setId(id);
        return (Job) getSqlMapClientTemplate().queryForObject("selectJobInfoById", job);
    }

    @Override
    public Integer addDeliveryRecord(String userId, Integer jobId,Integer flag) {
        DeliveryPost deliveryPost = new DeliveryPost();
        Job job=getJobInfoById(jobId);
        Resume userResume=userManager.getUserResumeByUserId(userId);
        deliveryPost.setJobId(jobId);
        deliveryPost.setUserId(userId);
        deliveryPost.setJobName(job.getJobName());
        deliveryPost.setUserName(userResume.getName());
        deliveryPost.setUniversity(userResume.getUniversity());
        deliveryPost.setSpecialty(userResume.getSpecialty());
        deliveryPost.setFlag(flag);
        return getSqlMapClientTemplate().update("addDeliveryRecord", deliveryPost);
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
    public Integer postJob(Job job) {
        return getSqlMapClientTemplate().update("postJob", job);
    }

    @Override
    public List getJobListByUserId(String userId, Integer flag) {
        DeliveryPost deliveryPost = new DeliveryPost();
        deliveryPost.setUserId(userId);
        deliveryPost.setFlag(flag);
        List<DeliveryPost> deliveryPost1 = (List<DeliveryPost>) getSqlMapClientTemplate().queryForList("queryJobListByUserId", deliveryPost);
        List<Job> jobs = new ArrayList<Job>();
        if (deliveryPost1 != null) {
            for (int i = 0; i < deliveryPost1.size(); i++) {
                Job job = getJobInfoById(deliveryPost1.get(i).getJobId());
                if (job != null) {
                    job.setTime(deliveryPost1.get(i).getTime());//设置投递时间
                    jobs.add(job);
                }
            }
            return jobs;
        } else {
            return null;
        }
    }

    @Override
    public List getJobInfoByJobId(Integer jobId, Integer flag) {
        DeliveryPost deliveryPost=new DeliveryPost();
        deliveryPost.setJobId(jobId);
        deliveryPost.setFlag(flag);
        return getSqlMapClientTemplate().queryForList("getJobInfoByJobId",deliveryPost);
    }

    @Override
    public List getJobInfoByUserId(String userId) {
        Job job=new Job();
        job.setUserId(userId);
        return getSqlMapClientTemplate().queryForList("getJobInfoByUserId", job);
    }

    @Override
    public List getDeliveryNumsCountJobName(String userId) {
        DeliveryPost deliveryPost=new DeliveryPost();
        deliveryPost.setUserId(userId);
        deliveryPost.setFlag(1);
        return getSqlMapClientTemplate().queryForList("deliveryNumsCountJobName",deliveryPost);
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
    public DeliveryPost getCheckSendInfoByJobIdAndUserId(Integer jobId, String userId) {
        Map map=new HashMap();
        map.put("jobId",jobId);
        map.put("userId",userId);
        return (DeliveryPost) getSqlMapClientTemplate().queryForObject("getCheckSendInfoByJobIdAndUserId",map);
    }
}
