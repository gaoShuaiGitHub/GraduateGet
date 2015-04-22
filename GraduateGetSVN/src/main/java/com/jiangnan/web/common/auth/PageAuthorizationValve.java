package com.jiangnan.web.common.auth;

import com.alibaba.citrus.service.uribroker.URIBrokerService;
import com.alibaba.citrus.service.uribroker.uri.URIBroker;
import com.alibaba.common.lang.StringUtil;
import com.alibaba.service.pipeline.PipelineException;
import com.alibaba.service.pipeline.PipelineInitializationException;
import com.alibaba.service.pipeline.ValveForward;
import com.alibaba.turbine.pipeline.AbstractValve;
import com.alibaba.turbine.service.rundata.RunData;
import com.jiangnan.web.common.WebConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;

@Service
public class PageAuthorizationValve extends AbstractValve {
    private PageAuthorizationService pageAuthService;

    //    @Autowired
//    private URIBrokerService uriBrokerService;
    private String realm;
    private String sessionKey;
    private String brokerId;
    private String returnKey;

    /**
     * 设置acl的realm。
     *
     * @param realm acl区域
     */
    public void setRealm(String realm) {
        this.realm = StringUtil.trimToNull(realm);
    }

    /**
     * 设置保存user对象的session key。
     *
     * @param sessionKey 用来保存user对象的session key
     */
    public void setSessionKey(String sessionKey) {
        this.sessionKey = StringUtil.trimToNull(sessionKey);
    }

    /**
     * 设置重定向的URI。
     *
     * @param brokerId URIBroker的ID
     */
    public void setRedirectURI(String brokerId) {
        this.brokerId = StringUtil.trimToNull(brokerId);
    }

    /**
     * 设置返回URL的key。
     *
     * @param returnKey 返回URL的key
     */
    public void setReturnKey(String returnKey) {
        this.returnKey = StringUtil.trimToNull(returnKey);
    }

    /**
     * 初始化<code>Valve</code>。
     *
     * @throws PipelineInitializationException 初始化失败
     */
    @Override
    public void init() throws PipelineInitializationException {
        pageAuthService = (PageAuthorizationService) getWebxComponent().getService(
                PageAuthorizationService.SERVICE_NAME);

        if (pageAuthService == null) {
            throw new PipelineInitializationException("could not get PageAuthorizationService");
        }
//
//        if (uriBrokerService == null) {
//            throw new PipelineInitializationException("could not get URIBrokerService");
//        }

        if (realm == null) {
            realm = WebConstant.ACCESS_REALM;
        }

        if (sessionKey == null) {
            sessionKey = WebConstant.SESSION_USER_SESSION_KEY;
        }

        if (brokerId == null) {
            brokerId = WebConstant.LOGIN_LINK;
        }

        if (returnKey == null) {
            returnKey = WebConstant.LOGIN_RETURN_KEY;
        }
    }

    /**
     * 执行valve。
     */
    @Override
    public ValveForward invoke(RunData rundata) throws PipelineException {
        SessionUser user = (SessionUser) rundata.getSession().getAttribute(sessionKey);

        if (user == null) {
            // 创建匿名用户
            user = new SessionUser(realm);

            rundata.getSession().setAttribute(sessionKey, user);
        }

        // 将user设置到rundata中，以便其它程序使用。
        SessionUser.setCurrentUser(user);

        // 检查授权
        boolean allowAccess = pageAuthService.isAllow(rundata.getTarget(), user.getRealm(), user.getUserId(), user
                .getRoles(), null);

//        if (!allowAccess) {
//            URIBroker redirectURI = uriBrokerService.getURIBroker(brokerId);

//            if (redirectURI == null) {
//                throw new PipelineException("no URI broker: " + brokerId);
//            }

//            String returnURL = rundata.getRequestURL();

//            try {
//                rundata.setRedirectLocation(redirectURI.addQueryData(returnKey, returnURL).render());
//            } catch (IOException e) {
//                throw new PipelineException(e);
//            }
//        }

//        return null;
//    }

//    public void setUriBrokerService(URIBrokerService uriBrokerService) {
//        this.uriBrokerService = uriBrokerService;
//    }
        return null;
    }
}