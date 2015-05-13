package com.jiangnan.web.user.module.screen;

import com.alibaba.citrus.turbine.Context;
import com.jiangnan.biz.user.UserManager;
import com.jiangnan.dal.dataobject.ResumeDO;
import com.jiangnan.web.common.WebConstant;
import com.jiangnan.web.common.auth.SessionUser;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpSession;

/**
 * Created by gaoshuai.gs on 2015/3/30.
 */
public class EditResume {
    @Autowired
    private UserManager userManager;
    public void execute(Context context, HttpSession session) {
        SessionUser sessionUser= (SessionUser) session.getAttribute(WebConstant.SESSION_USER_SESSION_KEY);
        ResumeDO resumeDO = userManager.getUserResumeByUserId(sessionUser.getUserId());
        
        System.out.println("resume="+ resumeDO);
        if(resumeDO !=null){
            System.out.println("resume="+ resumeDO);
            context.put("resume", resumeDO);
        }
    }
}
