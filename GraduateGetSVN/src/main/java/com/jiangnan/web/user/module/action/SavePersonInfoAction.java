package com.jiangnan.web.user.module.action;

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
public class SavePersonInfoAction {

    @Autowired
    private UserManager userManager;

    public void doSave(@Param("reallyName") String reallyName,
                       @Param("email") String email,
                       @Param("university") String university,
                       @Param("sdescrip") String sdescrip,
                       @Param("qq") String qq,
                       @Param("aiNums") Integer aiNums,
                       HttpSession  session,
                       Context context
    ) throws Exception {

        SessionUser sessionUser= (SessionUser) session.getAttribute(WebConstant.SESSION_USER_SESSION_KEY);
        User user=new User();
        user.setUserId(sessionUser.getUserId());
        user.setReallyName(reallyName);
        user.setEmail(email);
        user.setUniversity(university);
        user.setSdescrip(sdescrip);
        user.setQq(qq);
        if(aiNums!=null){
        user.setAiNums(aiNums);
        }else{
            user.setAiNums(5);
        }
        Integer flag=userManager.savePersonInfo(user);

        if(flag>0){
            context.put("result","success");
        }else{
            context.put("result","failed");
        }
    }
}
