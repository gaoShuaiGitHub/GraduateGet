package com.jiangnan.web.job.module.screen;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.jiangnan.biz.job.home.JobManager;
import com.jiangnan.biz.user.UserManager;
import com.jiangnan.dal.dataobject.Job;
import com.jiangnan.dal.dataobject.User;
import org.springframework.beans.factory.annotation.Autowired;

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

    public void execute(Context context, @Param("id") Integer id) {
        job = jobManager.getJobInfoById(id);
        System.out.println("job="+job);
        if(job!=null) {
            user = userManager.getUserByUserId(job.getUserId());
            System.out.println("user="+user);
        context.put("job",job);
        }
        if(user!=null){
            context.put("user",user);
        }
    }
}
