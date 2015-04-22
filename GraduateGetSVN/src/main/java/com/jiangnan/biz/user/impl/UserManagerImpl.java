package com.jiangnan.biz.user.impl;

import com.jiangnan.biz.user.UserManager;
import com.jiangnan.dal.dao.UserDao;
import com.jiangnan.dal.dao.ibatis.IbatisUserDao;
import com.jiangnan.dal.dataobject.Resume;
import com.jiangnan.dal.dataobject.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by GS-YL on 2015/2/25.
 */
@Component
public class UserManagerImpl implements UserManager {


    @Autowired
    private UserDao userDao;


    @Override
    public User login(String userId, String password) {
        User user=userDao.getUser(userId,password);
        return user;
    }

    @Override
    public Integer register(String userId, String password, String email) {
        return userDao.addUser(userId,password,email);
    }

    @Override
    public User getUserById(Integer id) {
        return userDao.getUserById(id);
    }

    @Override
    public User getUserByUserId(String userId) {
        return userDao.getUserByUserId(userId);
    }

    @Override
    public Integer savePersonInfo(User user) {
        return userDao.savePersonInfo(user);
    }

    @Override
    public Integer editResume(Resume resume) {
        return userDao.editResume(resume);
    }

    @Override
    public Resume getUserResumeByUserId(String userId) {
        return userDao.getUserResumeByUserId(userId);
    }
}
