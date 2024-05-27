package com.changing.erpsystembackend.controller;

import com.changing.erpsystembackend.common.Result;
import com.changing.erpsystembackend.entity.User;
import com.changing.erpsystembackend.service.UserService;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RestController
public class UserController {
    @Resource
    UserService userService;

    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        User curUser = userService.login(user);
        if(curUser == null){
            return Result.error("输入的用户名或者密码错误");
        }else{
            return Result.success(curUser);
        }
    }

    @PostMapping("/register")
    public Result register(@RequestParam("name") String name,
                           @RequestParam("gender") String gender,
                           @RequestParam("age") String age,
                           @RequestParam("department") String department,
                           @RequestParam("position") String position,
                           @RequestParam("resume") MultipartFile resume) {
        User user = new User();
        user.setName(name);
        user.setGender(gender);
        user.setAge(age);
        user.setPosition(position);
        user.setDepartment(department);
        user.setStatus("apply");

        if (!resume.isEmpty()) {
            try {
                String content = new String(resume.getBytes(), StandardCharsets.UTF_8);
                user.setResume(content);
            } catch (IOException e) {
                return Result.error("文件读取失败");
            }
        }

        userService.register(user);
        return Result.success("申请已提交");
    }
}
