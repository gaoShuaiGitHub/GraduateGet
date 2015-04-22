package com.jiangnan.web.user.module.screen;

import com.alibaba.citrus.turbine.Context;
import com.jiangnan.biz.user.UserManager;
import com.jiangnan.dal.dataobject.Resume;
import com.jiangnan.dal.dataobject.User;
import com.jiangnan.web.common.WebConstant;
import com.jiangnan.web.common.auth.SessionUser;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;

/**
 * Created by gaoshuai.gs on 2015/3/30.
 */
public class EditResume {
    @Autowired
    private UserManager userManager;
    public void execute(Context context, HttpSession session) {
        SessionUser sessionUser= (SessionUser) session.getAttribute(WebConstant.SESSION_USER_SESSION_KEY);
        Resume resume = userManager.getUserResumeByUserId(sessionUser.getUserId());
        
        System.out.println("resume="+resume);
        if(resume!=null){
            System.out.println("resume="+resume);
            context.put("resume",resume);
        }
    }
}
