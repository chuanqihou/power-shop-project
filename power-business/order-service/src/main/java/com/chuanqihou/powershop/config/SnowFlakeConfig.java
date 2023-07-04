package com.chuanqihou.powershop.config;

import cn.hutool.core.lang.Snowflake;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 传奇后
 * @date 2023/7/4 19:34
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "snowflake.config")
public class SnowFlakeConfig {

    /**
     * 终端ID
     */
    private Long workerId;

    /**
     * 数据中心ID
     */
    private Long dataCenterId;

    @Bean
    public Snowflake snowflake() {
        return new Snowflake(workerId,dataCenterId);
    }

}
