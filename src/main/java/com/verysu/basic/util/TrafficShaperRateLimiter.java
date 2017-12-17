package com.verysu.basic.util;

import com.google.common.collect.Maps;
import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.ConcurrentMap;


/**
 * 限流工具
 *
 * @author Cocodroid
 * @create 2017-07-28 9:25
 */
public class TrafficShaperRateLimiter {
    private static final ConcurrentMap<String,RateLimiter> userResourceLimiterMap = Maps.newConcurrentMap();

    private static final ConcurrentMap<String,Double> resourceLimiterMap = Maps.newConcurrentMap();

    public static void setResourceQps(String resource, Double qps){
        resourceLimiterMap.put(resource, qps);
    }

    public static void remove(String resource){
        resourceLimiterMap.remove(resource);
    }

    /**
     * 是否还能请求
     * @param resource 请求资源
     * @param userKey 用户key
     * @return
     */
    public static boolean req(String resource, String userKey){
        Long t1 = System.currentTimeMillis();
        Double qps = resourceLimiterMap.get(resource);
        if(qps == null || qps.doubleValue() == 0.0){// no limit
            return true;
        }
        String key = resource + "_" + userKey;
        RateLimiter rateLimiter = userResourceLimiterMap.get(key);
        if(null == rateLimiter){
            rateLimiter = RateLimiter.create(qps);
            RateLimiter pRateLimiter = userResourceLimiterMap.putIfAbsent(key, rateLimiter);
            if(pRateLimiter != null){
                rateLimiter = pRateLimiter;
            }
            rateLimiter.setRate(qps);
        }
        if (!rateLimiter.tryAcquire()) {
//            System.out.println("use:"+(System.currentTimeMillis()-t1)+"ms;"+resource+"  visited  too frequently  by key:"+userKey);
            return false;
        }else{
//            System.out.println("use:"+(System.currentTimeMillis()-t1)+"ms;");
            return true;
        }
    }

}
