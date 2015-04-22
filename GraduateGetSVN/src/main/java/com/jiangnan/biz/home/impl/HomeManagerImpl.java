package com.jiangnan.biz.home.impl;

import com.jiangnan.biz.home.HomeManager;
import com.jiangnan.dal.dao.ibatis.IbatisJobDao;
import com.jiangnan.dal.dataobject.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by GS-YL on 2015/2/27.
 */
@Component
public class HomeManagerImpl implements HomeManager {

    @Autowired
    private IbatisJobDao ibatisJobDao;


}
