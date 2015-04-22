package com.jiangnan.web.user.module.screen;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
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
        List<Job> jobList = jobManager.getJobList();
        Resume resumeInfo = userManager.getUserResumeByUserId(sessionUser.getUserId());
        User user = userManager.getUserByUserId(sessionUser.getUserId());
        List<DeliveryPost> jobNameList = jobManager.getDeliveryNumsCountJobName(sessionUser.getUserId());
        HashMap<String, Integer> hm = getJobNameNums(jobNameList);
        List<AiResult<Job>> aiResultLists = countAiNums(hm, jobList, resumeInfo).subList(0, user.getAiNums());
        context.put("aiResultLists", aiResultLists);
    }

    public HashMap<String, Integer> getJobNameNums(List<DeliveryPost> jobNameList) {
        HashMap<String, Integer> hs = new HashMap<String, Integer>();
        for (int i = 0; i < jobNameList.size(); i++) {
            DeliveryPost dp = jobNameList.get(i);
            Integer count = 1;
            if (hs.get(dp.getJobName()) != null) {
                count = hs.get(dp.getJobName()) + 1;
            }
            hs.put(dp.getJobName(), count / jobNameList.size());
        }
        return hs;
    }

    public List<AiResult<Job>> countAiNums(HashMap<String, Integer> hm, List<Job> jobList, Resume resumeInfo) {
        List<AiResult<Job>> aiResultList = new ArrayList<AiResult<Job>>();
        for (Job job : jobList) {
            Integer aiNums = 0;
            AiResult<Job> ai = new AiResult<Job>();

            if (hm.get(job.getJobName()) != null) {//投递次数
                aiNums = 30 * hm.get(job.getJobName());
            }
            if (job.getUniversity().equals(resumeInfo.getUniversity())) {//一样大学
                aiNums += 30;
            }
            if (Integer.parseInt(job.getEduBackground()) >= Integer.parseInt(resumeInfo.getEduBackground())) {//学历匹配
                aiNums += 20;
            }
            if (job.getSpecialty().equals(resumeInfo.getSpecialty())) {//专业相同
                aiNums += 20;
            }
            ai.setAiNums(aiNums);
            ai.setData(job);
            aiResultList.add(ai);
        }

        Collections.sort(aiResultList, new Comparator<AiResult>() {
            public int compare(AiResult arg0, AiResult arg1) {
                return arg1.getAiNums().compareTo(arg0.getAiNums());
            }
        });
        return aiResultList;
    }
}
