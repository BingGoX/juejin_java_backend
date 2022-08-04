package com.tsinghualei.juejin.common.util;

public class RedisUtil {
    public static String getRedisKey(String redisKey,String value){
        return redisKey + ":" + value;
    }
}
