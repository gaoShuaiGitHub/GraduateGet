package com.jiangnan.web.job.module.action;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.jiangnan.biz.job.home.JobManager;
import com.jiangnan.dal.dataobject.DeliveryPostDO;
import com.jiangnan.web.common.WebConstant;
import com.jiangnan.web.common.auth.SessionUser;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpSession;

/**
 * Created by GS-YL on 2015/3/15.
 */
public class SendResumeAction {

    @Autowired
    private JobManager jobManager;

    private Integer DELIVER=1;
    public void doSendResume(@Param("id") Integer id,
                             HttpSession session,
                             Navigator navigator,
                        Context context
    ) throws Exception {

        SessionUser sessionUser= (SessionUser) session.getAttribute(WebConstant.SESSION_USER_SESSION_KEY);
        if(sessionUser==null){
            context.put("result","failed");
            context.put("errorMessage","你还没有登录，先到首页登录再投递该职位哦！");
            navigator.redirectTo(WebConstant.LOGIN_RETURN_DEFAULT_LINK);
            return ;
        }
        DeliveryPostDO deliveryPostDO =jobManager.getCheckSendInfoByJobIdAndUserId(id,sessionUser.getUserId());
        System.out.println("deliveryPost="+ deliveryPostDO);
        if(deliveryPostDO !=null){
            context.put("result","failed");
            context.put("errorMessage","你已经投递过该职位啦，相同职位职能投递一次，赶快去看看其他职位吧。");
            return ;
        }
        Integer flag=  jobManager.addDeliveryRecord(sessionUser.getUserId(),id,DELIVER);//数据库update的时候返回的是受影响的行数，如果行数大于0就表示成功了

        if(flag>0){
            context.put("result", "success");
            context.put("errorMessage", "投递成功啦，请静待学长联系你吧！");
        }else{
            context.put("result", "failed");
            context.put("errorMessage", "投递失败，请稍后再试下哦，亲！");

        }
    }
}
