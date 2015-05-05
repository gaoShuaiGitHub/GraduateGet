package com.jiangnan.web.job.module.screen;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.jiangnan.biz.job.home.JobManager;
import com.jiangnan.biz.user.UserManager;
import com.jiangnan.dal.dataobject.Job;
import com.jiangnan.dal.dataobject.User;
import com.jiangnan.web.common.WebConstant;
import com.jiangnan.web.common.auth.SessionUser;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpSession;

/**
 * Created by GS-YL on 2015/3/2.
 */
public class JobInfo {
    @Autowired
    private JobManager jobManager;
    @Autowired
    private UserManager userManager;

    private Job job=new Job();
    private User user=new User();

    public void execute(Context context, @Param("id") Integer id,
                        HttpSession session) {
        SessionUser sessionUser = (SessionUser) session.getAttribute(WebConstant.SESSION_USER_SESSION_KEY);

        if(sessionUser!=null){
            context.put("loginResult","success");
        }else{
            context.put("loginResult","failed");
        }
        job = jobManager.getJobInfoById(id);
        System.out.println("job="+job);
        if(job!=null) {
            user = userManager.getUserByUserId(job.getUserId());
        context.put("job",job);
        }
        if(user!=null){
            context.put("user",user);
        }
    }
}
