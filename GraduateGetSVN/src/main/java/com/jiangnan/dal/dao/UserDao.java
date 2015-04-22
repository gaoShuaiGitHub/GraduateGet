package com.jiangnan.dal.dao;

import com.jiangnan.dal.dataobject.Resume;
import com.jiangnan.dal.dataobject.User;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by GS-YL on 2015/2/23.
 */
@Component
public interface UserDao {
    public List<User> getAllUser();

    public User getUser(String userId, String password);

    public User getUserById(Integer id);

    public User getUserByUserId(String userId);

    public Integer addUser(String userId, String password, String email);

    public Integer savePersonInfo(User user);

    public Integer editResume(Resume resume);

    public Resume getUserResumeByUserId(String userId);


}
