package com.chuanqihou.powershop;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @author 传奇后
 * @date 2023/6/19 18:47
 * @description
 */
@SpringBootTest
public class MyTest {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Test
    void testConfig() {
        System.out.println(redisTemplate);
        String s = redisTemplate.opsForValue().get("test");
        System.out.println(s);
    }

}
