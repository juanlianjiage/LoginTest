package com.example.logintest.controller;

import org.junit.jupiter.api.Test;

/**
 * @Author: chenxinjia
 * @Date: 2024/12/26 11:24
 */
public class HelloWorldTest {
    @Test
    public void test(){
        HelloWorld helloWorld = new HelloWorld();
        helloWorld.say();
    }
}
