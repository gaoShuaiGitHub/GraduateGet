package com.jiangnan.web.home.module.screen;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.jiangnan.biz.job.home.JobManager;
import com.jiangnan.biz.user.UserManager;
import com.jiangnan.dal.dataobject.Job;
import com.jiangnan.dal.dataobject.User;
import com.jiangnan.web.common.WebConstant;
import com.jiangnan.web.common.auth.SessionUser;
import com.jiangnan.web.job.module.control.JobList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by GS-YL on 2015/3/2.
 */
public class Homepage {

    private final static Logger log = LoggerFactory.getLogger(Homepage.class);

    @Autowired
    private UserManager userManager;

    @Autowired
    private JobManager jobManager;


    public void execute(@Param(name = "startRow", defaultValue = "1") int startRow,
                        @Param(name = "countPage", defaultValue = "1") int countPage,
                        @Param(name = "flag", defaultValue = "0") int flag,
                        @Param(name = "searchText", defaultValue = "") String searchText,
                        @Param(name = "loginResult") String loginResult,
                        @Param(name = "errorMessage") String errorMessage,
                        HttpSession session,
                        Context context) {

        SessionUser sessionUser = (SessionUser) session.getAttribute(WebConstant.SESSION_USER_SESSION_KEY);

        log.info("homepage test sessionUser=" + sessionUser);
        if (sessionUser == null) {
            context.put("result", "failed");
        } else {
            User user = userManager.getUserByUserId(sessionUser.getUserId());
            context.put("result", "success");
            context.put("user", user);
            log.info("homepage test user=" + user);
        }
        List jobs;
        int sumResult;  //查询出来的总条数
        if (startRow < 1) {//如果输入的startRow小于1则将startRow置为1。
            startRow = 1;
        }
        startRow = changeStartRow(flag, startRow, countPage, context);

        if (searchText == null) {
            jobs = jobManager.getJobListWithPageSize(startRow);
            sumResult = jobManager.getJobList().size();
        } else {
            jobs = jobManager.queryJobsBySearchText(searchText, startRow);
            sumResult = jobs.size();
        }

        //查询热门职位
        List dpfjns=jobManager.getMaxJobName();
        context.put("dpfjns",dpfjns);//被投递次数最多的大学。
        //查询热门大学
        List dpfus=jobManager.getMaxUniversity();
        context.put("dpfus",dpfus);//被投递次数最多的大学。

        countPage = sumResult % WebConstant.PAGE_SIZE == 0 ? sumResult / WebConstant.PAGE_SIZE : sumResult / WebConstant.PAGE_SIZE + 1;
        context.put("searchText", searchText);//查询内容
        context.put("jobs", jobs);     //查询总列表
        context.put("sumResult", sumResult); //总条数
        context.put("countPage", countPage); //总页数
        context.put("loginResult",loginResult);
        context.put("errorMessage",errorMessage);
    }

    //分页 逻辑是：flag :0 查询  -1：上一页  1 ：下一页  转：3  尾页：2
    public int changeStartRow(int flag, int startRow, int countPage, Context context) {
        int pageSize = WebConstant.PAGE_SIZE;//默认是10个
        int realStartRow = 0;
        if (flag == 0) {
            realStartRow = 0;
            startRow = 1;
        } else if (flag == 1) {
            startRow += 1;
            if (startRow >= countPage) {
                startRow = countPage;
            }
            realStartRow = (startRow - 1) * pageSize;
        } else if (flag == -1) {
            startRow -= 1;
            if (startRow <= 1) {
                startRow = 1;
            }
            realStartRow = (startRow - 1) * pageSize;
        } else if (flag == 2) {
            startRow = countPage;
            realStartRow = (startRow - 1) * pageSize;
        } else if (flag == 3) {

            if (startRow > countPage) {
                startRow = countPage;
            } else if (startRow < 1) {
                startRow = 1;
            }

            realStartRow = (startRow - 1) * pageSize;
        }
        context.put("startRow", startRow);   //当前页
        return realStartRow;
    }
}