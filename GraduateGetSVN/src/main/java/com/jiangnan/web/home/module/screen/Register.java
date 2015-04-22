package com.jiangnan.web.home.module.screen;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.jiangnan.dal.dataobject.User;

/**
 * Created by gaoshuai.gs on 2015/4/7.
 */
public class Register {
    public void execute(@Param("userId") String userId,
                        @Param("password") String password,
                        @Param("rePassword") String rePassword,
                        @Param("email") String email,
                        Context context
                        ){
        User user=new User();
        user.setUserId(userId);
        user.setPassword(password);
        user.setEmail(email);

        context.put("user",user);
        context.put("rePassword", rePassword);
    }
}
