package com.jiangnan.dal.dao;

import com.jiangnan.dal.dataobject.ResumeDO;
import com.jiangnan.dal.dataobject.UserDO;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by GS-YL on 2015/2/23.
 * 个人信息数据库信息操作接口类
 */
@Component
public interface UserDao {
    public List<UserDO> getAllUser();

    public UserDO getUser(String userId, String password);

    public UserDO getUserById(Integer id);

    public UserDO getUserByUserId(String userId);

    public Integer addUser(String userId, String password, String email);

    public Integer savePersonInfo(UserDO userDO);

    public Integer editResume(ResumeDO resumeDO);

    public ResumeDO getUserResumeByUserId(String userId);


}
