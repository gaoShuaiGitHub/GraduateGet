package com.jiangnan.web.job.module.screen;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.jiangnan.biz.job.home.JobManager;
import com.jiangnan.biz.user.UserManager;
import com.jiangnan.dal.dataobject.JobDO;
import com.jiangnan.dal.dataobject.UserDO;
import com.jiangnan.web.common.WebConstant;
import com.jiangnan.web.common.auth.SessionUser;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpSession;

/**
 * Created by GS-YL on 2015/3/2.
 * 工作信息渲染类
 */
public class JobInfo {
    @Autowired
    private JobManager jobManager;
    @Autowired
    private UserManager userManager;

    private JobDO jobDO =new JobDO();
    private UserDO userDO =new UserDO();

    public void execute(Context context, @Param("id") Integer id,
                        HttpSession session) {
        SessionUser sessionUser = (SessionUser) session.getAttribute(WebConstant.SESSION_USER_SESSION_KEY);

        if(sessionUser!=null){
            context.put("loginResult","success");
        }else{
            context.put("loginResult","failed");
        }
        jobDO = jobManager.getJobInfoById(id);
        System.out.println("job="+ jobDO);
        if(jobDO !=null) {
            userDO = userManager.getUserByUserId(jobDO.getUserId());
        context.put("job", jobDO);
        }
        if(userDO !=null){
            context.put("user", userDO);
        }
    }
}
