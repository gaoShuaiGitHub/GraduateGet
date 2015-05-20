package com.jiangnan.web.user.module.action;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.jiangnan.biz.job.home.JobManager;
import com.jiangnan.biz.user.UserManager;
import com.jiangnan.dal.dataobject.JobDO;
import com.jiangnan.dal.dataobject.UserDO;
import com.jiangnan.web.common.WebConstant;
import com.jiangnan.web.common.auth.SessionUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

/**
 * Created by gaoshuai.gs on 2015/3/27.
 * 发布职位action类
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
                          @Param("companySize") String companySize,
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
            UserDO userDO =userManager.getUserByUserId(userId);
            JobDO jobDO = new JobDO();
            jobDO.setUserId(userId);
            jobDO.setJobName(jobName);
            jobDO.setCompany(company);
            jobDO.setCompanySize(companySize);
            jobDO.setMinMoney(minMoney);
            jobDO.setMaxMoney(maxMoney);
            jobDO.setAddress(address);
            jobDO.setJobReq(jobReq);
            jobDO.setReqSummary(reqSummary);
            jobDO.setUniversity(userDO.getUniversity());
            jobDO.setEduBackground(eduBackground);
            jobDO.setSpecialty(specialty);

            Integer jobId = jobManager.postJob(jobDO);
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
            context.put("job", jobDO);
        }
    }
}
