package com.jiangnan.web.user.module.screen;

import com.alibaba.citrus.turbine.Context;
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
        SessionUser sessionUser = (SessionUser) session.getAttribute(WebConstant.SESSION_USER_SESSION_KEY);
        ResumeDO resumeDOInfo = userManager.getUserResumeByUserId(sessionUser.getUserId());
        UserDO userDO=userManager.getUserByUserId(sessionUser.getUserId());
        List<AiResultDO<JobDO>> aiResultDOLists = jobManager.calculationMatching(resumeDOInfo).subList(0, userDO.getAiNums());
        context.put("aiResultLists", aiResultDOLists);
    }
}
