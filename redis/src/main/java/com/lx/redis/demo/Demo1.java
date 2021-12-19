package com.lx.redis.demo;

import redis.clients.jedis.Jedis;

import java.util.Random;

/**
 * @program: bunny
 * @description: 手机验证码3s过期，每个手机每天只能验证3次，jedis操作
 * @author: james
 * @create: 2021-12-19 11:24
 **/
public class Demo1 {


    public static void main(String[] args) {

        boolean result = codeStore("13688114710",codeGen());

        if (result){
            if ("获取的验证码".equals(codeGet("13688114710"))){
                System.out.println("验证成功");
            } else {
                System.out.println("验证失败");
            }
        }

    }

    // 从redis获取验证码
    public static String codeGet(String phoneNumber){
        Jedis jedis = new Jedis("127.0.0.1",6379);
        String codeKey = "VertifyCode"+ phoneNumber +":code";
        String code = jedis.get(codeKey);
        jedis.close();
        return code;
    }

    // 存储验证码到redis，设置过期时间3s，每个手机号只能验证三次
    public static boolean codeStore(String phoneNumber,String code){
        Jedis jedis = new Jedis("127.0.0.1",6379);
        // 验证码的key
        String codeKey = "VertifyCode"+ phoneNumber +":code";
        // 手机号验证次数的key
        String countKey = "VertifyCode" + phoneNumber + ":count";

        String ctk = jedis.get(countKey);
        if (ctk == null){
            jedis.setex(countKey,24*60*60,"0");
        } else if (Integer.parseInt(ctk) > 2){
            System.out.println("输入次数超过3次");
            return false;
        }

        jedis.setex(codeKey,180,code);
        jedis.incr(countKey);
        jedis.close();
        return true;
    }

    // 生成6位验证码
    public static String codeGen(){
        Random random = new Random();
        String code = "";
        for (int i=0;i<6;i++){
            int r = random.nextInt(10);
            code += r;
        }
        return code;
    }
}
