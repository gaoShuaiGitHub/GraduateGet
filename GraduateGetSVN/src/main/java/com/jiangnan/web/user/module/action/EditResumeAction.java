package com.jiangnan.web.user.module.action;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.jiangnan.biz.user.UserManager;
import com.jiangnan.dal.dataobject.ResumeDO;
import com.jiangnan.web.common.WebConstant;
import com.jiangnan.web.common.auth.SessionUser;
import org.springframework.beans.factory.annotation.Autowired;
import javax.servlet.http.HttpSession;
import java.text.ParseException;

/**
 * Created by gaoshuai.gs on 2015/3/30.
 * 用户编辑简历action类
 */
public class EditResumeAction {

    @Autowired
    private UserManager userManager;

    public void doEditResume(@Param("name") String name,
                            @Param("phone") String phone,
                            @Param("email") String email,
                            @Param("birthday") String birthday,
                            @Param("city") String city,
                            @Param("minMoney") Integer minMoney,
                            @Param("maxMoney") Integer maxMoney,
                            @Param("experience") String experience,
                            @Param("eduBackground") String eduBackground,
                            @Param("university") String university,
                            @Param("specialty") String specialty,
                            HttpSession session,
                            Context context
    ) throws ParseException {
        SessionUser sessionUser = (SessionUser) session.getAttribute(WebConstant.SESSION_USER_SESSION_KEY);
        if (sessionUser == null) {
            context.put("result", "failed");
        } else {
            String userId = sessionUser.getUserId();
//            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟
//            sdf.parse(birthday);

            ResumeDO resumeDO = new ResumeDO();
            resumeDO.setUserId(userId);
            resumeDO.setName(name);
            resumeDO.setPhone(phone);
            resumeDO.setEmail(email);
            resumeDO.setBirthday(birthday);
            resumeDO.setCity(city);
            resumeDO.setMinMoney(minMoney);
            resumeDO.setMaxMoney(maxMoney);
            resumeDO.setExperience(experience);
            resumeDO.setEduBackground(eduBackground);
            resumeDO.setUniversity(university);
            resumeDO.setSpecialty(specialty);

            Integer flag = userManager.editResume(resumeDO);
            if (flag > 0) {
                context.put("result", "success");
            } else {
                context.put("result", "failed");
            }
            context.put("resume", resumeDO);
        }
    }

}
