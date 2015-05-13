package com.jiangnan.web.job.module.control;

import com.alibaba.citrus.turbine.Context;
import com.jiangnan.biz.job.home.JobManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by GS-YL on 2015/2/27.
 */
public class JobList {
    private final static Logger log = LoggerFactory.getLogger(JobList.class);
    @Autowired
    private JobManager jobManager;

    public void execute(Context context) {
        List jobs = jobManager.getJobList();
            context.put("jobs", jobs);
    }
}
