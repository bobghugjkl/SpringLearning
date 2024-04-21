package com.example.demo;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.Base64Codec;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

@SpringBootTest
class DemoApplicationTests {

    @Test
    public void contextLoads() {
        long now = System.currentTimeMillis();
        long exp = now + 60* 1000;
        //测试生成token
        JwtBuilder jwtBuilder = Jwts.builder()
                .setId("8888")
                .setSubject("Rose")

                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256,"xxxx")//算法与盐
                .setExpiration(new Date(exp))//过期时间
//                自定义声明
                .claim("roles","admin")
                .claim("logo","xxx.jpg");
//                .addClaims(map)也可以这样直接加map里面
        String token = jwtBuilder.compact();//获取jwt的token
        System.out.println(token);
        System.out.println("==================================");
        String[] split = token.split("\\.");//分割
        System.out.println(Base64Codec.BASE64.decodeToString(split[0]));//转移头部信息
        System.out.println(Base64Codec.BASE64.decodeToString(split[1]));//后载
        System.out.println(Base64Codec.BASE64.decodeToString(split[2]));//签名

    }
//    解析token
    @Test
    public void testParseToken(){
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4ODg4Iiwic3ViIjoiUm9zZSIsImlhdCI6MTcxMzY4NjIwOX0.lkI7Zq4GIKeK-lvqa6YUf5C1ryYpL9zT6wzYPoUviXQ";
//        负载中声明的对象
        Claims claims = Jwts.parser()
                .setSigningKey("xxxx")//密钥
                .parseClaimsJws(token)
                .getBody();
        System.out.println("id:"+claims.getId()+"subject:"+claims.getSubject()+"issuedAt:"+claims.getIssuedAt());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//时间格式化
        System.out.println("签发时间:"+simpleDateFormat.format(claims.getIssuedAt()));
        System.out.println("过期时间:"+simpleDateFormat.format(claims.getExpiration()));
        System.out.println("当前时间:"+simpleDateFormat.format(new Date()));
        System.out.println("roles" + claims.get("roles"));//拿到自定义
    }
}
