package com.jiangnan.biz.user.impl;

import com.jiangnan.biz.user.UserManager;
import com.jiangnan.dal.dao.UserDao;
import com.jiangnan.dal.dataobject.ResumeDO;
import com.jiangnan.dal.dataobject.UserDO;
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
    public UserDO login(String userId, String password) {
        UserDO userDO =userDao.getUser(userId,password);
        return userDO;
    }

    @Override
    public Integer register(String userId, String password, String email) {
        return userDao.addUser(userId,password,email);
    }

    @Override
    public UserDO getUserById(Integer id) {
        return userDao.getUserById(id);
    }

    @Override
    public UserDO getUserByUserId(String userId) {
        return userDao.getUserByUserId(userId);
    }

    @Override
    public Integer savePersonInfo(UserDO userDO) {
        return userDao.savePersonInfo(userDO);
    }

    @Override
    public Integer editResume(ResumeDO resumeDO) {
        return userDao.editResume(resumeDO);
    }

    @Override
    public ResumeDO getUserResumeByUserId(String userId) {
        return userDao.getUserResumeByUserId(userId);
    }
}
