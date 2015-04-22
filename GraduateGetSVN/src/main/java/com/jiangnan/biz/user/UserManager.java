package com.jiangnan.biz.user;

import com.jiangnan.dal.dataobject.Resume;
import com.jiangnan.dal.dataobject.User;
import org.springframework.stereotype.Component;

/**
 * Created by GS-YL on 2015/2/25.
 */
@Component
public interface UserManager {
    /*
      用户登录
      */
    public User login(String userId, String password);

    /*
      用户注册
      */
    public Integer register(String userId, String password, String email);

    public User getUserById(Integer id);

    public User getUserByUserId(String userId);

    public Integer savePersonInfo(User user);

    public Integer editResume(Resume resume);

    public Resume getUserResumeByUserId(String userId);
}
