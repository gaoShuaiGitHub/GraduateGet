package com.jiangnan.web.common;

/**
 * Created by GS-YL on 2015/2/26.
 * Web层常量
 */

public interface WebConstant {

    /** 如果未指定return，登录以后就跳到该URL。 */
    String LOGIN_RETURN_DEFAULT_LINK = "homepageLink";

    /** 前台的ACL realm。 */
    String ACCESS_REALM = "accessRealm";

    /** 在session中保存用户对象的key。 */
    String SESSION_USER_SESSION_KEY = "sessionUser";

    /** Login页面返回URL的key。 */
    String LOGIN_RETURN_KEY = "return";

    /** 登录URL的名字。 */
    String LOGIN_LINK = "homepageLink";

    /** 退出URL的名字。 */
    String LOGINOUT_LINK = "homepageLink";


    /** 登记用户URL的名字。 */
    String REGISTER_LINK = "registerLink";

    /** 登记用户信息URL的名字。 */
    String REGISTER_ACCOUNT_LINK = "petstoreRegisterAccountLink";

    /** 查看用户信息URL的名字。 */
    String ACCOUNT_LINK = "petstoreAccountLink";

    /**
     * 每页展示数
     */
    Integer PAGE_SIZE=10;
}
