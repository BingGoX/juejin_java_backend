package com.tsinghualei.juejin.common.util;

import com.tsinghualei.juejin.model.Vo.UserVo;
import com.tsinghualei.juejin.model.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.tsinghualei.juejin.controller.BaseController.REDIS_USER_TOKEN_KEY;

public class TokenUtil {

    public static void cacheToken(RedisOperator redis, UserVo user){
        String phoneNumber = user.getPhoneNumber();
        String userId = user.getUserId();
        String uToken = user.getUToken();
        if(StringUtil.isNotNullOrEmpty(phoneNumber) &&
                StringUtil.isNotNullOrEmpty(uToken)){
            redis.set(RedisUtil.getRedisKey(REDIS_USER_TOKEN_KEY,phoneNumber),uToken);
        }
        if(StringUtil.isNotNullOrEmpty(userId)){
            redis.set(RedisUtil.getRedisKey(REDIS_USER_TOKEN_KEY,userId),uToken);
        }
    }

    public static String getToken(RedisOperator redis,String userId){
        return redis.get(RedisUtil.getRedisKey(REDIS_USER_TOKEN_KEY,userId));
    }

    public static void delToken(RedisOperator redis,UserVo user){
        String phoneNumber = user.getPhoneNumber();
        String userId = user.getUserId();
        redis.del(RedisUtil.getRedisKey(REDIS_USER_TOKEN_KEY,userId));
        redis.del(RedisUtil.getRedisKey(REDIS_USER_TOKEN_KEY,phoneNumber));
    }
}
