package com.tsinghualei.juejin.controller;

import com.tsinghualei.juejin.common.result.GraceJSONResult;
import com.tsinghualei.juejin.common.result.ResponseStatusEnum;
import com.tsinghualei.juejin.common.util.IPUtil;
import com.tsinghualei.juejin.common.util.RedisUtil;
import com.tsinghualei.juejin.common.util.TokenUtil;
import com.tsinghualei.juejin.model.Vo.UserVo;
import com.tsinghualei.juejin.model.param.LoginRegister_Param;
import com.tsinghualei.juejin.model.pojo.User;
import com.tsinghualei.juejin.service.UserService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Slf4j
@RestController
@Api(tags = "LoginRegisterContorller")
@RequestMapping("/LoginRegisterContorller")
public class LoginRegisterContorller extends BaseController{
    String TAG = "LoginRegisterContorller_Log";
    @Autowired
    UserService userService;

    @GetMapping("getVerifyCode")
    GraceJSONResult getVerifyCode(@RequestParam String phoneNumber,HttpServletRequest request){
        log.info("getVerifyCode: "+phoneNumber);
        String ip = IPUtil.getRequestIp(request);
        String randomSmsCode = BaseUtil.generateCode(4);
        redis.setnx60s(RedisUtil.getRedisKey(REDIS_SMS_CODE_KEY ,ip),ip);
        redis.setnx60s(RedisUtil.getRedisKey(REDIS_SMS_CODE_KEY ,phoneNumber),randomSmsCode);
        logger.info("ip: "+ip+"code: "+randomSmsCode);
        return GraceJSONResult.ok(randomSmsCode);
    }

    @PostMapping("login")
    GraceJSONResult login(@RequestBody LoginRegister_Param param){
        String phoneNumber = param.getPhoneNumber();
        String smsCode = param.getSmsCode();
        String cachedCode = redis.get(RedisUtil.getRedisKey(REDIS_SMS_CODE_KEY ,phoneNumber) );
        if(!redis.keyIsExist(RedisUtil.getRedisKey(REDIS_SMS_CODE_KEY ,phoneNumber))){
            return new GraceJSONResult(ResponseStatusEnum.SMS_CODE_ERROR);
        }
        if(!cachedCode.equalsIgnoreCase(smsCode)){
            return new GraceJSONResult(ResponseStatusEnum.SMS_CODE_ERROR);
        }
        User user = userService.queryUserByMobile(phoneNumber);
        if(user == null){
            user = userService.createUser(phoneNumber);
        }
        String uToken = UUID.randomUUID().toString();
        redis.del(RedisUtil.getRedisKey(REDIS_SMS_CODE_KEY,phoneNumber));
        UserVo userVo =new UserVo();
        BeanUtils.copyProperties(user,userVo);
        userVo.setUToken(uToken);
        TokenUtil.cacheToken(redis,userVo);
        logger.info(TAG+"login"+"phone: "+phoneNumber+" token: "+uToken);
        return GraceJSONResult.ok(userVo);
    }

    @GetMapping("queryUserByMobile")
    GraceJSONResult queryUserByMobile(@RequestParam String phoneNumber){
        User user = userService.queryUserByMobile(phoneNumber);
        if(user == null){
            userService.createUser(phoneNumber);
            user = userService.queryUserByMobile(phoneNumber);
        }
        if(user == null){
            return GraceJSONResult.error("register fail!");
        }
        return GraceJSONResult.ok(user);
    }

    @ResponseBody
    @PostMapping("createUserByMobile")
    GraceJSONResult createUserByMobile(@RequestParam String phoneNumber){
        userService.createUser(phoneNumber);
        return GraceJSONResult.ok();
    }

}
