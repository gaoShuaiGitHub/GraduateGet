package com.jiangnan.web.common.auth;


import com.alibaba.citrus.util.StringUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaoshuai.gs on 2015/3/16.
 */
public class SessionUser implements Serializable {

    private static final long serialVersionUID = -5693787141625487757L;
    private static final ThreadLocal userHolder = new ThreadLocal();
    private String realm;
    private String userId;
    private List roles = new ArrayList(4);

    public static final SessionUser getCurrentUser() {
        return (SessionUser) userHolder.get();
    }

    public static final void setCurrentUser(SessionUser sessionUser) {
        userHolder.set(sessionUser);
    }

    //无参构造函数
    public SessionUser(){}

    /**
     * 创建匿名用户
     *
     * @param realm
     */
    public SessionUser(String realm) {
        this.realm = realm;
    }

    public SessionUser(String realm, String userId) {
        this.realm = realm;
        this.userId = userId;
    }

    public String getRealm() {
        return realm;
    }

    public String getUserId() {
        return userId;
    }

    public String[] getRoles() {
        return (String[]) roles.toArray(new String[roles.size()]);
    }
    public void addRole(String role) {
        roles.add(role);
    }

    public void cleanPrles() {
        roles.clear();
    }

    public boolean hasLoggedIn() {
        if (StringUtil.isEmpty(userId)) {
            return false;
        } else
            return true;
    }

    public void upgrade(String userId) {
        if (hasLoggedIn()) {
            throw new IllegalStateException();
        }
        if (StringUtil.isEmpty(userId)) {
            throw new IllegalArgumentException();
        }
        this.userId=userId;
    }

}
