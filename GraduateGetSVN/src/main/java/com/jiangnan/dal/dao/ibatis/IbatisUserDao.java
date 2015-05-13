package com.jiangnan.dal.dao.ibatis;

import com.jiangnan.dal.dao.UserDao;
import com.jiangnan.dal.dataobject.ResumeDO;
import com.jiangnan.dal.dataobject.UserDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by GS-YL on 2015/2/25.
 */
@Component
public class IbatisUserDao extends SqlMapClientDaoSupport implements UserDao {

    @Override
    public List<UserDO> getAllUser() {
        return null;
    }

    @Override
    public UserDO getUser(String userId, String password) {
        UserDO userDO = new UserDO();
        userDO.setUserId(userId);
        userDO.setPassword(password);
        return (UserDO) getSqlMapClientTemplate().queryForObject("getUserByUserIdAndPassword", userDO);
    }

    @Override
    public UserDO getUserById(Integer id) {
        UserDO userDO =new UserDO();
        userDO.setId(id);
        return (UserDO)getSqlMapClientTemplate().queryForObject("getUserById", userDO);
    }

    @Override
    public Integer addUser(String userId, String password, String email) {
        UserDO userDO =new UserDO();
        userDO.setUserId(userId);
        userDO.setPassword(password);
        userDO.setEmail(email);
        return getSqlMapClientTemplate().update("addUser", userDO);
    }

    @Override
    public UserDO getUserByUserId(String userId) {
        UserDO userDO =new UserDO();
        userDO.setUserId(userId);
        return (UserDO)getSqlMapClientTemplate().queryForObject("getUserByUserId", userDO);
    }

    @Override
    public Integer savePersonInfo(UserDO userDO) {
        return getSqlMapClientTemplate().update("saveUser", userDO);
    }

    @Override
    public Integer editResume(ResumeDO resumeDO) {
        ResumeDO resumeDO1 =getUserResumeByUserId(resumeDO.getUserId());
        if(resumeDO1 !=null){
            return getSqlMapClientTemplate().update("updateEditResume", resumeDO);
        }else{
            return getSqlMapClientTemplate().update("insertEditResume", resumeDO);
        }
    }

    @Override
    public ResumeDO getUserResumeByUserId(String userId) {
        ResumeDO resumeDO =new ResumeDO();
        resumeDO.setUserId(userId);
        return (ResumeDO) getSqlMapClientTemplate().queryForObject("getUserResumeByUserId", resumeDO);
    }
}
