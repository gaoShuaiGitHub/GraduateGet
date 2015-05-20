package com.jiangnan.dal.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gaoshuai.gs on 2015/5/13.
 */
public class MatchingPointUtil {

    private static final Map<Boolean,Double> universityUtil=new HashMap<Boolean, Double>();//学校比较
    private static final Map<Boolean,Double> addressUtil=new HashMap<Boolean, Double>();//地点比较
    private static final Map<String,Double> companySizeUtil=new HashMap<String, Double>();//公司规模比较
    private static final Map<Integer,Double> deliveryThreeUtil=new HashMap<Integer, Double>();//职位投递前三
    private static final Map<String,Double> postTimeUtil=new HashMap<String, Double>();//职位发布时间

    static {
        universityUtil.put(true, 1.00);//在同一所大学
        universityUtil.put(false, 0.50);//不在同一所大学

        addressUtil.put(true,1.00);//期望地点与公司地点匹配
        addressUtil.put(false,0.50);//期望地点与公司地点不匹配

        companySizeUtil.put("1To50",0.20);//1-50人
        companySizeUtil.put("50To100",0.50);//50-100人
        companySizeUtil.put("100To500",0.80);//100-500人
        companySizeUtil.put("50ToXXX",1.00);//500人以上
        companySizeUtil.put(null,0.00);//错误情况没有写的情况

        deliveryThreeUtil.put(0,1.00);//第一个
        deliveryThreeUtil.put(1,0.80);//第二个
        deliveryThreeUtil.put(2,0.50);//第三个
        deliveryThreeUtil.put(100,0.00);//保底数据

        postTimeUtil.put("lessThreeDay",1.00);//近三天
        postTimeUtil.put("lessOneWeek",0.80);//近一周
        postTimeUtil.put("lessTwoWeek",0.50);//近两周
        postTimeUtil.put("lessOneMonth",0.20);//近一个月
        postTimeUtil.put("moreOneMonth",0.10);//超过一个月
    }

    public static Map<Boolean, Double> getUniversityUtil() {
        return universityUtil;
    }

    public static Map<Boolean, Double> getAddressUtil() {
        return addressUtil;
    }

    public static Map<String, Double> getCompanySizeUtil() {
        return companySizeUtil;
    }

    public static Map<Integer, Double> getDeliveryThreeUtil() {
        return deliveryThreeUtil;
    }

    public static Map<String, Double> getPostTimeUtil() {
        return postTimeUtil;
    }
}
