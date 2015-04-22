package com.jiangnan.web.user.module.screen;

import com.alibaba.citrus.turbine.Context;
import com.jiangnan.biz.job.home.JobManager;
import com.jiangnan.web.common.WebConstant;
import com.jiangnan.web.common.auth.SessionUser;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by gaoshuai.gs on 2015/3/30.
 */
public class PostRecord {

    private static final Integer POST=0;
    @Autowired
    private JobManager jobManager;
    public void execute(HttpSession session,
                        Context context){
        SessionUser sessionUser= (SessionUser) session.getAttribute(WebConstant.SESSION_USER_SESSION_KEY);
        List jobs=jobManager.getJobListByUserId(sessionUser.getUserId(),POST);
        context.put("jobs",jobs);
    }
}
