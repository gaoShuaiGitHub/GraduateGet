package com.jiangnan.web.user.module.action;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.alibaba.citrus.turbine.dataresolver.FormField;
import com.alibaba.citrus.turbine.dataresolver.FormGroup;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.jiangnan.biz.job.home.JobManager;
import com.jiangnan.biz.user.UserManager;
import com.jiangnan.dal.dataobject.Job;
import com.jiangnan.dal.dataobject.User;
import com.jiangnan.web.common.WebConstant;
import com.jiangnan.web.common.auth.SessionUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

/**
 * Created by gaoshuai.gs on 2015/3/27.
 */
@Service
public class PostJobAction {

    @Autowired
    private JobManager jobManager;
    @Autowired
    private UserManager userManager;
    private Integer POST=0;
    public void doPostJob(@Param("jobName") String jobName,
                          @Param("company") String company,
                          @Param("minMoney") Integer minMoney,
                          @Param("maxMoney") Integer maxMoney,
                          @Param("address") String address,
                          @Param("jobReq") String jobReq,
                          @Param("reqSummary") String reqSummary,
                          @Param("eduBackground") String eduBackground,
                          @Param("specialty") String specialty,
                          Navigator navigator,
                          HttpSession session,
                          ParameterParser params,
                          Context context) {
        SessionUser sessionUser = (SessionUser) session.getAttribute(WebConstant.SESSION_USER_SESSION_KEY);

        if (sessionUser == null) {
            context.put("result", "failed");
        } else {
            String userId = sessionUser.getUserId();
            User user=userManager.getUserByUserId(userId);
            Job job = new Job();
            job.setUserId(userId);
            job.setJobName(jobName);
            job.setCompany(company);
            job.setMinMoney(minMoney);
            job.setMaxMoney(maxMoney);
            job.setAddress(address);
            job.setJobReq(jobReq);
            job.setReqSummary(reqSummary);
            job.setUniversity(user.getUniversity());
            job.setEduBackground(eduBackground);
            job.setSpecialty(specialty);

            Integer jobId = jobManager.postJob(job);
            if(jobId<0){
                context.put("result", "failed");
                return ;
            }
            Integer flag=jobManager.addDeliveryRecord(sessionUser.getUserId(),jobId,POST);
            if (flag>0) {
                context.put("result", "success");
            } else {
                context.put("result", "failed");
            }
            context.put("job", job);
        }
    }
}
