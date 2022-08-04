package com.tsinghualei.juejin.controller;

import com.alibaba.fastjson.JSONObject;
import com.tsinghualei.juejin.common.result.GraceJSONResult;
import com.tsinghualei.juejin.common.util.StringUtil;
import com.tsinghualei.juejin.common.util.TokenUtil;
import com.tsinghualei.juejin.model.Vo.UserVo;
import com.tsinghualei.juejin.model.param.BaseRequestParam;
import com.tsinghualei.juejin.model.param.UserControllerParam;
import com.tsinghualei.juejin.model.pojo.User;
import com.tsinghualei.juejin.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.json.Json;

import static com.tsinghualei.juejin.common.result.ResponseStatusEnum.TICKET_INVALID;
import static com.tsinghualei.juejin.common.result.ResponseStatusEnum.USER_STATUS_ERROR;

@RestController
@RequestMapping("userController")
public class UserController extends BaseController{

    @Autowired
    UserService  userService;

    @PostMapping("modifyUserById")
    GraceJSONResult modifyUserById(@RequestBody BaseRequestParam param){
        JSONObject jsonObject = JSONObject.parseObject(param.jsonObj);
        UserVo user = JSONObject.toJavaObject(jsonObject,UserVo.class);
        String userId = user.getUserId();
        String token = param.token;
        String cachedToken = TokenUtil.getToken(redis,userId);
        if(!token.equalsIgnoreCase(cachedToken)){
            return new GraceJSONResult(TICKET_INVALID);
        }
        User newUser = userService.queryUserById(userId);
        BeanUtils.copyProperties(user,newUser);
        userService.updateUserById(userId,newUser);
        return GraceJSONResult.ok();
    }

    @PostMapping("getUserById")
    GraceJSONResult getUserById(@RequestParam String userId){
        if(StringUtil.isNullOrEmpty(userId)){
            return new GraceJSONResult(USER_STATUS_ERROR);
        }
        User user = userService.queryUserById(userId);
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(user,userVo);
        return GraceJSONResult.ok(userVo);
    }
}
