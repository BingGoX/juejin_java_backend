package com.tsinghualei.juejin.controller;

import com.tsinghualei.juejin.common.util.RedisOperator;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.logging.Logger;

public class BaseController {
    @Autowired
    RedisOperator redis;

    Logger logger = Logger.getLogger(BaseController.class.toString());
    public static String REDIS_SMS_CODE_KEY = "sms_code_key";
    public static String REDIS_USER_TOKEN_KEY = "user_token_key";
}


