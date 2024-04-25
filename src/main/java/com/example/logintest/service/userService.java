package com.example.logintest.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.logintest.config.TestResult;
import com.example.logintest.pojo.user;

public interface userService extends IService<user> {
    TestResult userLogin(String iphoneNumber, String pwd);
}
