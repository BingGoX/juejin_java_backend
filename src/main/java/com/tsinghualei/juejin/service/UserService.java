package com.tsinghualei.juejin.service;

import com.tsinghualei.juejin.model.pojo.User;

public interface UserService {
    /**
     * 通过手机号判断用户是否存在，如果存在则返回User，否则创建user并返回
     */
    User queryUserByMobile(String phoneNumber);

    User queryUserById(String userId);

    /**
     * 通过手机号判创建user
     */
    User createUser(String phoneNumber);

    User updateUserById(String userId,User user);
}
