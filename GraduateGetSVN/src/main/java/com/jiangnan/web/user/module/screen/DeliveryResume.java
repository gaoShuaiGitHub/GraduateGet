package com.jiangnan.web.user.module.screen;

import com.alibaba.citrus.turbine.Context;
import com.jiangnan.biz.job.home.JobManager;
import com.jiangnan.dal.dataobject.JobDO;
import com.jiangnan.web.common.WebConstant;
import com.jiangnan.web.common.auth.SessionUser;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaoshuai.gs on 2015/3/31.
 * 投递简历页面渲染类
 */
public class DeliveryResume {

    private static final Integer DELIVERY = 1;
    @Autowired
    private JobManager jobManager;

    public void execute(HttpSession session,
                        Context context) {
        SessionUser sessionUser = (SessionUser) session.getAttribute(WebConstant.SESSION_USER_SESSION_KEY);
        List<JobDO> jobDOs = jobManager.getJobInfoByUserId(sessionUser.getUserId());
        List deliveryPosts = new ArrayList();
        for (JobDO jobDO : jobDOs) {
            List deliveryPost = jobManager.getJobListByJobId(jobDO.getId(), DELIVERY);
            deliveryPosts.addAll(deliveryPost);
        }
        context.put("deliveryPosts", deliveryPosts);
    }
}
