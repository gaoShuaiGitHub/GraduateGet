package com.jiangnan.web.user.module.screen;

import com.alibaba.citrus.turbine.Context;
import com.jiangnan.biz.job.home.JobManager;
import com.jiangnan.dal.dataobject.Job;
import com.jiangnan.web.common.WebConstant;
import com.jiangnan.web.common.auth.SessionUser;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by gaoshuai.gs on 2015/3/30.
 */
public class DeliveryRecord {

    private static final Integer DELIVERY=1;

    @Autowired
    private JobManager jobManager;
    public void execute(HttpSession session,
                        Context context){
        SessionUser sessionUser= (SessionUser) session.getAttribute(WebConstant.SESSION_USER_SESSION_KEY);

        List jobs=jobManager.getJobListByUserId(String.valueOf(sessionUser.getUserId()),DELIVERY);
        context.put("jobs",jobs);
    }
}
