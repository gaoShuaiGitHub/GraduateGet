package com.jiangnan.web.user.module.screen;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.jiangnan.biz.user.UserManager;
import com.jiangnan.dal.dataobject.ResumeDO;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpSession;

/**
 * Created by gaoshuai.gs on 2015/3/31.
 * 查看投递人简历信息页面渲染类
 */
public class ResumeInfo {

    @Autowired
    private UserManager userManager;

    public void execute(@Param("id") String userId,
                        HttpSession session,
                        Context context) {
        ResumeDO resumeDO = userManager.getUserResumeByUserId(userId);
        context.put("resume", resumeDO);
    }
}
