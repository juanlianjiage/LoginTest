package com.example.logintest.config;

public enum TestResultEnum {
    //成功
    YF_200(200,"成功！"),
    //失败
    YF_500(500,"失败，系统异常！");
    private Integer code;
    private String msg;
    TestResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
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
}
