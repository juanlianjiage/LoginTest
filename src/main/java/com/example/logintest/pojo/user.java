package com.example.logintest.pojo;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
@Data
@TableName("user1")
public class user {

    private String iphone;
    @TableField("pwd")
    private String pwd;


}
