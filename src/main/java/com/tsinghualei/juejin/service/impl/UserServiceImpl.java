package com.tsinghualei.juejin.service.impl;

import com.mysql.cj.util.LogUtils;
import com.tsinghualei.juejin.common.util.UUIDUtil;
import com.tsinghualei.juejin.mapper.UserMapper;
import com.tsinghualei.juejin.model.pojo.User;
import com.tsinghualei.juejin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;

@Service
public class UserServiceImpl implements UserService {

    String headerUrl = "https://img1.baidu.com/it/u=592570905,1313515675&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500";

    @Autowired
    UserMapper userMapper;

    @Override
    public User queryUserByMobile(String phoneNumber) {
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("phoneNumber",phoneNumber);
        User user = (User) userMapper.selectOneByExample(example);
        return user;
    }

    @Override
    public User queryUserById(String userId){
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId",userId);
        User user = (User) userMapper.selectOneByExample(example);
        return user;
    }


    @Override
    public User createUser(String phoneNumber) {
        User user = new User();
        String uuid = UUIDUtil.getUUID(32);
        user.setUserId(uuid);
        user.setUserName("user_"+uuid.trim());
        user.setCreateTime(new Date());
        user.setLevel(0L);
        user.setBadgeCount(0L);
        user.setFollowerCount(0L);
        user.setLikeCount(0L);
        user.setModifyTime(new Date());
        user.setHeaderUrl(headerUrl);
        user.setPhoneNumber(phoneNumber);
        user.setSex("null");
        user.setPassword(phoneNumber.substring(phoneNumber.length()-4));
        userMapper.insert(user);
        return user;
    }

    @Override
    public User updateUserById(String userId,User newUser){
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId",userId);
        int n = userMapper.updateByExample(newUser,example);
        if(n == 0){
            //todo ts 打印日志信息
        }
        return newUser;
    }
}
