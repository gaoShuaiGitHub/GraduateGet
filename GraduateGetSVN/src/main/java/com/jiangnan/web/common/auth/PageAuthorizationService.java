package com.jiangnan.web.common.auth;

import com.alibaba.common.lang.ClassUtil;

/**
 * 用来确定哪些页面需要登录，哪些不需要登录。
 *
 * @author Michael Zhou
 */
public interface PageAuthorizationService {
    String SERVICE_NAME = ClassUtil.getShortClassName(PageAuthorizationService.class);

    /** 配置文件属性：access descriptors。 */
    String ACCESS_DESCRIPTORS = "access.descriptors";

    /** MATCH_EVERYTHING代表用户和role时，不包含匿名用户 */
    String MATCH_EVERYTHING = "*";

    /** 特列用户名：匿名用户 */
    String ANONYMOUS_USER = "anonymous";

    /**
     * 根据规则判断指定的角色是否能访问指定的target。
     *
     * @param target 匹配的目标
     * @param realmName realm的名称
     * @param userId 用户名
     * @param roleNames 角色列表
     * @param action 行为
     * @return true 表明能够访问，false表明不能访问
     */
    boolean isAllow(String target, String realmName, String userId, String[] roleNames, String action);
}
