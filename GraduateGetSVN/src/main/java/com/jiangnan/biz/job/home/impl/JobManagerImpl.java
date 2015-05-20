package com.jiangnan.biz.job.home.impl;

import com.jiangnan.biz.job.home.JobManager;
import com.jiangnan.dal.dao.JobDao;
import com.jiangnan.dal.dataobject.*;
import com.jiangnan.dal.util.MatchingPointUtil;
import com.jiangnan.web.common.WebConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.util.*;

/**
 * Created by GS-YL on 2015/2/27.
 * 职位信息处理接口实现
 */
@Component
public class JobManagerImpl implements JobManager {

    @Autowired
    private JobDao jobDao;

    @Override
    public List<JobDO> getJobList() {
        return jobDao.getJobList();
    }

    @Override
    public List getJobListWithPageSize(Integer startRow) {
        return jobDao.getJobListWithPageSize(startRow, WebConstant.PAGE_SIZE);
    }

    @Override
    public JobDO getJobInfoById(Integer id) {
        return jobDao.getJobInfoById(id);
    }

    @Override
    public List getJobListByUserId(String userId, Integer flag) {
        return jobDao.getJobListByUserId(userId, flag);
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
    public Integer addDeliveryRecord(String userId, Integer jobId, Integer flag) {

        return jobDao.addDeliveryRecord(userId, jobId, flag);
    }

    @Override
    public List queryJobsBySearchText(String searchText, Integer startRow) {
        return jobDao.queryJobsBySearchText(searchText, startRow, WebConstant.PAGE_SIZE);
    }

    @Override
    public Integer postJob(JobDO jobDO) {
        return jobDao.postJob(jobDO);
    }

    @Override
    public List<DeliveryPostDO> getDeliveryNumsCountJobName(String userId) {
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
    public DeliveryPostDO getCheckSendInfoByJobIdAndUserId(int jobId, String userId) {
        return jobDao.getCheckSendInfoByJobIdAndUserId(jobId, userId);
    }

    @Override
    public List<AiResultDO<JobDO>> calculationMatching(ResumeDO resumeDO) {
        List<AiResultDO<JobDO>> aiResultDOList = new ArrayList<AiResultDO<JobDO>>();
        List<JobDO> jobDOList = getJobList();
        DecimalFormat df = new DecimalFormat("######0.00");
        for (JobDO jobDO : jobDOList) {
            System.out.println("jobDo:" + jobDO);
            Double recommendPoint = calculationRecommendPoint(jobDO, resumeDO);
            AiResultDO<JobDO> aiResultDO = new AiResultDO<JobDO>();
            aiResultDO.setData(jobDO);
            aiResultDO.setAiNums(df.format(recommendPoint));
            aiResultDOList.add(aiResultDO);
        }
        Collections.sort(aiResultDOList, new Comparator<AiResultDO>() {
            public int compare(AiResultDO arg0, AiResultDO arg1) {
                return arg1.getAiNums().compareTo(arg0.getAiNums());
            }
        });
        return aiResultDOList;
    }

    public List<ProbabilityListDO> getProbabilityList() {
        return jobDao.getProbabilityList();
    }

    public MatchingPointDO getMatchingPointDO(JobDO jobDO, ResumeDO resumeDO) {

        Map<String, Double> matchingPointMap = new HashMap<String, Double>();
        MatchingPointDO matchingPointDO = new MatchingPointDO();
        if (jobDO == null || resumeDO == null) {
            System.out.println("jobDO or resumeDO is null");
            return null;
        }

        boolean isSameUniversity = jobDO.getUniversity().contains(resumeDO.getUniversity()) || resumeDO.getUniversity().contains(jobDO.getUniversity());
        boolean isSameAddress = jobDO.getAddress().contains(resumeDO.getCity()) || resumeDO.getCity().contains(jobDO.getAddress());

        matchingPointMap.put("university", MatchingPointUtil.getUniversityUtil().get(isSameUniversity));
        matchingPointMap.put("address", MatchingPointUtil.getAddressUtil().get(isSameAddress));
        matchingPointMap.put("companySize", MatchingPointUtil.getCompanySizeUtil().get(jobDO.getCompanySize()));
        matchingPointMap.put("deliveryThree", MatchingPointUtil.getDeliveryThreeUtil().get(getRankings(jobDO.getJobName(), resumeDO.getUserId()) == null ? 100 : getRankings(jobDO.getJobName(), resumeDO.getUserId())));
        matchingPointMap.put("postTime", MatchingPointUtil.getPostTimeUtil().get(calculationDate(jobDO.getTime())));
        matchingPointMap.put("moneyPoint", getMoneyPoint(resumeDO.getMinMoney(), resumeDO.getMaxMoney(), jobDO.getMinMoney(), jobDO.getMaxMoney()));

        matchingPointDO.setUniversityPoint(matchingPointMap.get("university"));
        matchingPointDO.setAddressPoint(matchingPointMap.get("address"));
        matchingPointDO.setCompanySizePoint(matchingPointMap.get("companySize"));
        matchingPointDO.setDeliveryThreePoint(matchingPointMap.get("deliveryThree"));
        matchingPointDO.setPostTimePoint(matchingPointMap.get("postTime"));
        matchingPointDO.setMoneyPoint(matchingPointMap.get("moneyPoint"));
        return matchingPointDO;
    }

    public Integer getRankings(String jobName, String userId) {
        Integer ranking = null;
        List<DeliveryPostDO> jobDeliveryThreeList = getDeliveryNumsCountJobName(userId);
        if (jobDeliveryThreeList == null) {
            System.out.println("jobDeliveryThreeList is null");
            return null;
        }
        for (int i = 0; i < jobDeliveryThreeList.size(); i++) {
            String jobNameList = jobDeliveryThreeList.get(i).getJobName();
            if (jobNameList.contains(jobName) || jobName.contains(jobNameList)) {
                ranking = i;
                break;
            }
        }
        return ranking;
    }

    public String calculationDate(Date date) {
        String resultString;
        Calendar calendar = Calendar.getInstance();
        Integer day = calendar.get(Calendar.DATE) - date.getDate();
        if (day >= 0 && day <= 3) {
            resultString = "lessThreeDay";
        } else if (day > 3 && day <= 7) {
            resultString = "lessOneWeek";
        } else if (day > 7 && day <= 14) {
            resultString = "lessTwoWeek";
        } else if (day > 14 && day <= 30) {
            resultString = "lessOneMonth";
        } else {
            resultString = "moreOneMonth";
        }
        return resultString;
    }

    public Double getMoneyPoint(Integer rMinMoney, Integer rMaxMoney, Integer jMinMoney, Integer jMaxMoney) {
        Double moneyPoint = 0.0;
        if (rMaxMoney < jMinMoney) {
            moneyPoint = 1.00;
        } else if (rMinMoney > jMinMoney && rMaxMoney < jMaxMoney) {
            moneyPoint = 0.80;
        } else if (rMinMoney > jMaxMoney) {
            moneyPoint = 0.50;
        }
        return moneyPoint;
    }


    public Double calculationRecommendPoint(JobDO jobDO, ResumeDO resumeDO) {
        List<ProbabilityListDO> probabilityList = getProbabilityList();
        MatchingPointDO matchingPointDO = getMatchingPointDO(jobDO, resumeDO);
        if (probabilityList == null) {
            System.out.println("probabilityList is null");
            return null;
        }

        ProbabilityListDO mostAttention = probabilityList.get(0);//最关注的属性分值
        ProbabilityListDO mostAttentionOrder = probabilityList.get(1);//最关注的属性顺序分值

        if (matchingPointDO == null) {
            System.out.println("matchingPointDO is null");
            return null;
        }
        System.out.println("matchingPointDO:" + matchingPointDO);
        Double pointOfMostAttention = mostAttention.getUniversity() * matchingPointDO.getUniversityPoint()
                + mostAttention.getAddress() * matchingPointDO.getAddressPoint()
                + mostAttention.getMoney() * matchingPointDO.getMoneyPoint()
                + mostAttention.getEduBackground()
                + mostAttention.getCompanySize() * matchingPointDO.getCompanySizePoint()
                + mostAttention.getDeliveryThree() * matchingPointDO.getDeliveryThreePoint();

        Double pointOfMostAttentionOrder = mostAttentionOrder.getUniversity() * matchingPointDO.getUniversityPoint()
                + mostAttentionOrder.getAddress() * matchingPointDO.getAddressPoint()
                + mostAttentionOrder.getMoney() * matchingPointDO.getMoneyPoint()
                + mostAttentionOrder.getEduBackground()
                + mostAttentionOrder.getSpecialty()
                + mostAttentionOrder.getCompanySize() * matchingPointDO.getCompanySizePoint()
                + mostAttentionOrder.getPostTime() * matchingPointDO.getPostTimePoint()
                + mostAttentionOrder.getDeliveryThree() * matchingPointDO.getDeliveryThreePoint();

        return (pointOfMostAttention + pointOfMostAttentionOrder) / 2;
    }
}
