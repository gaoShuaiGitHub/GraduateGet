package com.jiangnan.biz.user;

import com.jiangnan.dal.dataobject.ResumeDO;
import com.jiangnan.dal.dataobject.UserDO;
import org.springframework.stereotype.Component;

/**
 * Created by GS-YL on 2015/2/25.
 */
@Component
public interface UserManager {
    /*
      用户登录
      */
    public UserDO login(String userId, String password);

    /*
      用户注册
      */
    public Integer register(String userId, String password, String email);

    public UserDO getUserById(Integer id);

    public UserDO getUserByUserId(String userId);

    public Integer savePersonInfo(UserDO userDO);

    public Integer editResume(ResumeDO resumeDO);

    public ResumeDO getUserResumeByUserId(String userId);
}
