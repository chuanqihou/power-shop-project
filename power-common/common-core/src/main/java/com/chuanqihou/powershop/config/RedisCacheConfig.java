package com.chuanqihou.powershop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * @author 传奇后
 * @date 2023/6/26 9:50
 * @description 缓存序列化配置类
 */
@Configuration
public class RedisCacheConfig {

    /**
     * 获取Redis缓存配置 用于序列化
     * @return Redis缓存配置 用于序列化
     */
    @Bean
    public RedisCacheConfiguration redisCacheConfiguration() {
        // 创建Redis缓存配置对象
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
        // 设置缓存配置序列化方式
        redisCacheConfiguration = redisCacheConfiguration.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.json()));
        // 返回Redis缓存配置
        return redisCacheConfiguration;
    }


}
