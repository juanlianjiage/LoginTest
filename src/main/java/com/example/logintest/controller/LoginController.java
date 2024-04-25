package com.example.logintest.controller;
import com.example.logintest.config.TestResult;
import com.example.logintest.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class LoginController {
    @Autowired
    private userService userService;

    @PostMapping("/login")
    public TestResult login(@RequestParam String iphoneNumber, @RequestParam String pwd) {
        return userService.userLogin(iphoneNumber, pwd);
    }
}
