package com.lx.redis.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: bunny
 * @description: 测试redis整合sprinspringboot
 * @author: james
 * @create: 2021-12-19 15:30
 **/
@RestController
@RequestMapping("/redis")
public class Demo2 {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @GetMapping
    public String test1(){
        redisTemplate.opsForValue().set("name","lucy");
        return redisTemplate.opsForValue().get("name");
    }

}
