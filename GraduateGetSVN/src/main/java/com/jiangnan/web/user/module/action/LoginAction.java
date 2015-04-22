package com.jiangnan.web.user.module.action;

import com.alibaba.citrus.service.form.CustomErrors;
import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.alibaba.citrus.turbine.dataresolver.FormField;
import com.alibaba.citrus.turbine.dataresolver.FormGroup;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.jiangnan.biz.user.UserManager;
import com.jiangnan.dal.dataobject.User;
import com.jiangnan.web.common.WebConstant;
import com.jiangnan.web.common.auth.SessionUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

/**
 * Created by GS-YL on 2015/2/25.
 */
@Service
public class LoginAction {

    @Autowired
    private UserManager userManager;

    public void doLogin(@FormGroup("login")
                        @FormField(name = "loginError", group = "login")
                        @Param("userId") String userId,
                        @Param("password") String password,
                        Navigator navigator,
                        HttpSession session,
                        ParameterParser params,
                        Context context
    ) throws Exception {
        User user = userManager.login(userId, password);
        if (user != null) {
            //在session中创建SessionUser对象
            SessionUser sessionUser = (SessionUser) session.getAttribute(WebConstant.SESSION_USER_SESSION_KEY);
            if (sessionUser == null || sessionUser.hasLoggedIn()) {
                sessionUser = new SessionUser(WebConstant.ACCESS_REALM);
            }

            sessionUser.upgrade(userId);
            session.setAttribute(WebConstant.SESSION_USER_SESSION_KEY,sessionUser);

            navigator.redirectTo(WebConstant.LOGIN_RETURN_DEFAULT_LINK);
        } else {

//            context.put("loginResult", "failed");
//            context.put("errorMessage", "用户名或密码不对！请重新登录!");
            navigator.redirectTo(WebConstant.LOGIN_RETURN_DEFAULT_LINK).withParameter("loginResult","failed").withParameter("errorMessage","用户名或密码不对！请重新登录!");
        }
    }

    public void doLoginOut(HttpSession session, Navigator nav, ParameterParser params) throws Exception {
        // 清除session中的user
        session.removeAttribute(WebConstant.SESSION_USER_SESSION_KEY);

        // 跳转到主页面
        nav.redirectTo(WebConstant.LOGINOUT_LINK);
//        redirectToReturnPage(nav, params);
    }
}
