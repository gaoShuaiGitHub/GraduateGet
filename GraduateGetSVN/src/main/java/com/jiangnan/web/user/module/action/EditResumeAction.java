package com.jiangnan.web.user.module.action;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.jiangnan.biz.user.UserManager;
import com.jiangnan.dal.dataobject.Resume;
import com.jiangnan.web.common.WebConstant;
import com.jiangnan.web.common.auth.SessionUser;
import org.springframework.beans.factory.annotation.Autowired;
import javax.servlet.http.HttpSession;
import java.text.ParseException;

/**
 * Created by gaoshuai.gs on 2015/3/30.
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

            Resume resume = new Resume();
            resume.setUserId(userId);
            resume.setName(name);
            resume.setPhone(phone);
            resume.setEmail(email);
            resume.setBirthday(birthday);
            resume.setCity(city);
            resume.setMinMoney(minMoney);
            resume.setMaxMoney(maxMoney);
            resume.setExperience(experience);
            resume.setEduBackground(eduBackground);
            resume.setUniversity(university);
            resume.setSpecialty(specialty);

            Integer flag = userManager.editResume(resume);
            if (flag > 0) {
                context.put("result", "success");
            } else {
                context.put("result", "failed");
            }
            context.put("resume", resume);
        }
    }

}
