package com.jiangnan.dal.dao;

import com.jiangnan.biz.user.UserManager;
import com.jiangnan.biz.user.impl.UserManagerImpl;
import com.jiangnan.dal.dataobject.User;

/**
 * Created by GS-YL on 2015/2/27.
 */

public class ibatisUserDaoTest {


    public static void main(String[] args){
        UserManager userManager=new UserManagerImpl();
        User user=userManager.login("gaoshuai","1234");
    System.out.println("user="+user);
    }
}
