package com.jiangnan.web.home.module.action;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.jiangnan.biz.user.UserManager;
import com.jiangnan.dal.dataobject.UserDO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by GS-YL on 2015/2/27.
 * 首页接受注册action类
 */
public class RegisterAction {

    @Autowired
    UserManager userManager;

    public void doRegister(@Param("userId") String userId,
                           @Param("password") String password,
                           @Param("rePassword") String rePassword,
                           @Param("email") String email,
                           Context context
    ) {

        if (!password.equals(rePassword)) {
            context.put("errorMessage", "两次密码不相同，请重新输入！");
            context.put("result", "failed");
            return;
        }

        if (!checkEmail(email)) {
            context.put("errorMessage", "邮箱格式不正确，请重新输入！");
            context.put("result", "failed");
            return;
        }

        UserDO userDO = userManager.getUserByUserId(userId);
        if (userDO != null) {
            context.put("errorMessage", "该账号已存在，请换一个账号名注册哦！");
            context.put("result", "failed");
        } else {
            Integer flag = userManager.register(userId, password, email);//数据库update的时候返回的是受影响的行数，如果行数大于0就表示成功了
            if (flag > 0) {
                context.put("result", "success");
            } else {
                context.put("errorMessage", "注册失败，请稍后再试！");
                context.put("result", "failed");
            }
        }
    }

    public Boolean checkEmail(String email) {
        String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern regex = Pattern.compile(check);
        Matcher matcher = regex.matcher(email);
        return matcher.matches();
    }
}
