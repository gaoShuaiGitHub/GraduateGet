package com.jiangnan.web.user.module.screen;

import com.alibaba.citrus.turbine.Context;
import com.jiangnan.biz.job.home.JobManager;
import com.jiangnan.biz.user.UserManager;
import com.jiangnan.dal.dataobject.*;
import com.jiangnan.web.common.WebConstant;
import com.jiangnan.web.common.auth.SessionUser;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Created by gaoshuai.gs on 2015/4/2.
 */
public class AiRecommendation {

    @Autowired
    private UserManager userManager;

    @Autowired
    private JobManager jobManager;

    public void execute(HttpSession session,
                        Context context) {
        List jobs = new ArrayList();
        SessionUser sessionUser = (SessionUser) session.getAttribute(WebConstant.SESSION_USER_SESSION_KEY);
        ResumeDO resumeDOInfo = userManager.getUserResumeByUserId(sessionUser.getUserId());
        List<AiResultDO<JobDO>> aiResultDOLists = jobManager.calculationMatching(resumeDOInfo);
        context.put("aiResultLists", aiResultDOLists);
    }

    public HashMap<String, Integer> getJobNameNums(List<DeliveryPostDO> jobNameList) {
        HashMap<String, Integer> hs = new HashMap<String, Integer>();
        for (int i = 0; i < jobNameList.size(); i++) {
            DeliveryPostDO dp = jobNameList.get(i);
            Integer count = 1;
            if (hs.get(dp.getJobName()) != null) {
                count = hs.get(dp.getJobName()) + 1;
            }
            hs.put(dp.getJobName(), count / jobNameList.size());
        }
        return hs;
    }

    public List<AiResultDO<JobDO>> countAiNums(HashMap<String, Integer> hm, List<JobDO> jobDOList, ResumeDO resumeDOInfo) {
        List<AiResultDO<JobDO>> aiResultDOList = new ArrayList<AiResultDO<JobDO>>();
        for (JobDO jobDO : jobDOList) {
            Integer aiNums = 0;
            AiResultDO<JobDO> ai = new AiResultDO<JobDO>();

            if (hm.get(jobDO.getJobName()) != null) {//投递次数
                aiNums = 30 * hm.get(jobDO.getJobName());
            }
            if (jobDO.getUniversity().equals(resumeDOInfo.getUniversity())) {//一样大学
                aiNums += 30;
            }
            if (Integer.parseInt(jobDO.getEduBackground()) >= Integer.parseInt(resumeDOInfo.getEduBackground())) {//学历匹配
                aiNums += 20;
            }
            if (jobDO.getSpecialty().equals(resumeDOInfo.getSpecialty())) {//专业相同
                aiNums += 20;
            }
            ai.setAiNums(aiNums);
            ai.setData(jobDO);
            aiResultDOList.add(ai);
        }

        Collections.sort(aiResultDOList, new Comparator<AiResultDO>() {
            public int compare(AiResultDO arg0, AiResultDO arg1) {
                return arg1.getAiNums().compareTo(arg0.getAiNums());
            }
        });
        return aiResultDOList;
    }
}
