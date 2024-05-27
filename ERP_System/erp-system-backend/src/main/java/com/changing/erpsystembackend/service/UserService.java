package com.changing.erpsystembackend.service;

import com.changing.erpsystembackend.entity.User;
import com.changing.erpsystembackend.mapper.UserMapper;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Resource
    private UserMapper userMapper;

    public User login(User user){
        return userMapper.selectEmployee(user);
    }
    public void register(User user) {
        userMapper.insertEmployee(user);
    }
}
