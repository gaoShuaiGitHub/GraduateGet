package com.jiangnan.dal.dao;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;
import com.jiangnan.dal.dao.ibatis.IbatisJobDao;

import java.io.IOException;
import java.io.Reader;

/**
 * Created by GS-YL on 2015/2/27.
 */
public class IbatisJobDaoTest {
    private static SqlMapClient client = null;

    static {
        try {
            Reader reader = Resources.getResourceAsReader("classpath:sqlmap-config.xml");
            client = SqlMapClientBuilder.buildSqlMapClient(reader);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public static void main(String[] args){
        IbatisJobDao ibatisJobDao=new IbatisJobDao();
        System.out.println(ibatisJobDao.getJobList());
    }
}
