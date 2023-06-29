package com.chuanqihou.powershop.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author 传奇后
 * @date 2023/6/29 19:41
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(prefix = "wechat.smallapp")
@Configuration
public class WeChatSmallAppProperties {

    private String appId;

    private String appSecret;

    private String jsCode2SessionUrl;

}
