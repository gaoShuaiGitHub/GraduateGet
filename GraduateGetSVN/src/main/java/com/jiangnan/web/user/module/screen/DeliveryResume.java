package com.jiangnan.web.user.module.screen;

import com.alibaba.citrus.turbine.Context;
import com.jiangnan.biz.job.home.JobManager;
import com.jiangnan.biz.user.UserManager;
import com.jiangnan.dal.dataobject.DeliveryPost;
import com.jiangnan.dal.dataobject.Job;
import com.jiangnan.dal.dataobject.User;
import com.jiangnan.web.common.WebConstant;
import com.jiangnan.web.common.auth.SessionUser;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaoshuai.gs on 2015/3/31.
 */
public class DeliveryResume {

    private static final Integer DELIVERY = 1;
    @Autowired
    private JobManager jobManager;

    public void execute(HttpSession session,
                        Context context) {
        SessionUser sessionUser = (SessionUser) session.getAttribute(WebConstant.SESSION_USER_SESSION_KEY);
        List<Job> jobs = jobManager.getJobInfoByUserId(sessionUser.getUserId());
        List deliveryPosts = new ArrayList();
        for (Job job : jobs) {
            List deliveryPost = jobManager.getJobListByJobId(job.getId(), DELIVERY);
            deliveryPosts.addAll(deliveryPost);
        }
        context.put("deliveryPosts", deliveryPosts);
    }
}
