package com.example.logintest.config;

import java.io.Serializable;

import static com.example.logintest.config.TestResultEnum.YF_200;
import static com.example.logintest.config.TestResultEnum.YF_500;


//静态导入静态枚举类
//统一结果类
public class TestResult<T> implements Serializable {
    private Integer code;
    private String msg;
    private T data;
    public Integer getCode() {
        return code;
    }
    public void setCode(Integer code) {
        this.code = code;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
    public T getData() {
        return data;
    }
    public void setData(T data) {
        this.data = data;
    }
    public TestResult() {
    }
    public TestResult(Integer code) {
        this.code = code;
    }
    public TestResult(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
    //成功
    public static <T> TestResult<T> success(){
        return success(YF_200);
    }
    public static <T> TestResult<T> success(T data)
    {
        return success(YF_200,data);
    }
    public static <T> TestResult<T> success(TestResultEnum re)
    {
        return success(re,null);
    }
    public static <T> TestResult<T> success(TestResultEnum re, T data)
    {

        return success(re.getCode(),re.getMsg(),data);
    }
    public static <T> TestResult<T> success(Integer code, String msg, T data)
    {
        return new TestResult<>(code,msg,data);
    }
    //失败
    public static <T> TestResult<T> fail(){
        return fail(YF_500);
    }
    public static <T> TestResult<T> fail(TestResultEnum re){
        return fail(re,null);
    }
    public static <T> TestResult<T> fail(String msg){
        return fail(YF_500.getCode(),msg,null);
    }
    public static <T> TestResult<T> fail(T data){
        return fail(YF_500,data);
    }
    public static <T> TestResult<T> fail(TestResultEnum re, T data){
        Integer code = re.getCode();
        String msg = re.getMsg();
        return fail(code,msg,data);
    }
    public static <T> TestResult<T> fail(Integer code , String msg, T data){
        TestResult<T> result = new TestResult<>();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }
}
