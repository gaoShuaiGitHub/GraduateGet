package com.jiangnan.web.user.module.screen;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.jiangnan.biz.job.home.JobManager;
import com.jiangnan.biz.user.UserManager;
import com.jiangnan.dal.dataobject.Job;
import com.jiangnan.dal.dataobject.Resume;
import com.jiangnan.web.common.WebConstant;
import com.jiangnan.web.common.auth.SessionUser;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaoshuai.gs on 2015/3/31.
 */
public class ResumeInfo {

    @Autowired
    private UserManager userManager;

    public void execute(@Param("id") String userId,
                        HttpSession session,
                        Context context) {
        Resume resume = userManager.getUserResumeByUserId(userId);
        context.put("resume", resume);
    }
}
