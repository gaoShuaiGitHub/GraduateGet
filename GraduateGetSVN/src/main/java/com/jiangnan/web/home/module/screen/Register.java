package com.jiangnan.web.home.module.screen;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.jiangnan.dal.dataobject.UserDO;

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
        UserDO userDO =new UserDO();
        userDO.setUserId(userId);
        userDO.setPassword(password);
        userDO.setEmail(email);

        context.put("user", userDO);
        context.put("rePassword", rePassword);
    }
}
