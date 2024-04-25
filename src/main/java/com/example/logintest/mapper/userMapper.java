package com.example.logintest.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.logintest.pojo.user;
import org.apache.ibatis.annotations.Mapper;
@Mapper
public interface userMapper extends BaseMapper<user> {
}
