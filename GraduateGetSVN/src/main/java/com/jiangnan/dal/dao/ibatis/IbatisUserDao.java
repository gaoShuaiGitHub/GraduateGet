package com.jiangnan.dal.dao.ibatis;

import com.jiangnan.dal.dao.UserDao;
import com.jiangnan.dal.dataobject.Resume;
import com.jiangnan.dal.dataobject.User;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by GS-YL on 2015/2/25.
 */
@Component
public class IbatisUserDao extends SqlMapClientDaoSupport implements UserDao {

    @Override
    public List<User> getAllUser() {
        return null;
    }

    @Override
    public User getUser(String userId, String password) {
        User user = new User();
        user.setUserId(userId);
        user.setPassword(password);
        return (User) getSqlMapClientTemplate().queryForObject("getUserByUserIdAndPassword", user);
    }

    @Override
    public User getUserById(Integer id) {
        User user=new User();
        user.setId(id);
        return (User)getSqlMapClientTemplate().queryForObject("getUserById",user);
    }

    @Override
    public Integer addUser(String userId, String password, String email) {
        User user=new User();
        user.setUserId(userId);
        user.setPassword(password);
        user.setEmail(email);
        return getSqlMapClientTemplate().update("addUser",user);
    }

    @Override
    public User getUserByUserId(String userId) {
        User user=new User();
        user.setUserId(userId);
        return (User)getSqlMapClientTemplate().queryForObject("getUserByUserId",user);
    }

    @Override
    public Integer savePersonInfo(User user) {
        return getSqlMapClientTemplate().update("saveUser",user);
    }

    @Override
    public Integer editResume(Resume resume) {
        Resume resume1=getUserResumeByUserId(resume.getUserId());
        if(resume1!=null){
            return getSqlMapClientTemplate().update("updateEditResume",resume);
        }else{
            return getSqlMapClientTemplate().update("insertEditResume",resume);
        }
    }

    @Override
    public Resume getUserResumeByUserId(String userId) {
        Resume resume=new Resume();
        resume.setUserId(userId);
        return (Resume) getSqlMapClientTemplate().queryForObject("getUserResumeByUserId",resume);
    }
}
