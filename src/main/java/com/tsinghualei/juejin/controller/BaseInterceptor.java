package com.tsinghualei.juejin.controller;

import com.alibaba.fastjson.JSONObject;
import com.tsinghualei.juejin.common.result.GraceJSONResult;
import com.tsinghualei.juejin.common.result.ResponseStatusEnum;
import io.netty.handler.codec.json.JsonObjectDecoder;
import io.netty.util.internal.StringUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@Component
public class BaseInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String tokenKey = "Authorization";
        //检查header里面是否有token
        String token = request.getHeader(tokenKey);
        //检查参数里面是否有token
        if(token.isEmpty()){
            token = request.getParameter(tokenKey);
        }
        //检查cookie里面是否有token
        if(token.isEmpty()){
            Cookie[] cookies = request.getCookies();
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals(tokenKey)){
                    token = cookie.getValue();
                }
            }
        }
        if(StringUtil.isNullOrEmpty(token)){
            PrintWriter printWriter = response.getWriter();
            printWriter.write(JSONObject.toJSONString(
                    new GraceJSONResult(ResponseStatusEnum.UN_LOGIN,"用户未登录")));
            return false;
        }



        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
