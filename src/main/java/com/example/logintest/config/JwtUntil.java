package com.example.logintest.config;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.UUID;

public class JwtUntil {
    public static final long expireTime=30*60*1000;

    /*生成jwt，并且设置30分钟过期
    * */
    public static String makeToken(String iphone,String pwd){

        JwtBuilder jwtBuilder= Jwts.builder();

        jwtBuilder.setHeaderParam("typ","JWT").setHeaderParam("alg","HS256")
        .claim("iphone",iphone)
                .claim("pwd",pwd)
                .setExpiration(new Date(System.currentTimeMillis()+expireTime))
                .signWith(SignatureAlgorithm.HS256,"user")
                .setId(UUID.randomUUID().toString());


        return jwtBuilder.compact();

    }

}
