package com.jiangnan.web.user.module.screen;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.jiangnan.biz.user.UserManager;
import com.jiangnan.dal.dataobject.User;
import com.jiangnan.web.common.WebConstant;
import com.jiangnan.web.common.auth.SessionUser;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpSession;

/**
 * Created by gaoshuai.gs on 2015/3/15.
 */
public class PersonalInfo {
    @Autowired
    private UserManager userManager;
    public void execute(Context context, HttpSession session) {
        SessionUser sessionUser= (SessionUser) session.getAttribute(WebConstant.SESSION_USER_SESSION_KEY);
        User user = userManager.getUserByUserId(sessionUser.getUserId());
        System.out.println("user="+user);
        if(user!=null){
            System.out.println("user="+user);
            context.put("user",user);
        }
    }
}
