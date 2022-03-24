package com.skyestock.service;

import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;

import java.util.concurrent.TimeUnit;

public class FixCacheService {
    @Cached(name = "h_order_xn_map:", key = "#orderno",cacheType= CacheType.REMOTE,expire = 10, timeUnit = TimeUnit.MINUTES)
    public String findFixOrder(String orderno){

        System.out.println("findFixOrder nocache orderno:" + orderno);
        return orderno+" 123";
    }
}
