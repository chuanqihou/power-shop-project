package com.chuanqihou.powershop.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author 传奇后
 * @date 2023/6/27 9:28
 * @description 阿里云OOS配置类（从配置中心读取信息）
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@ConfigurationProperties(prefix = "aliyun.oss")
public class AliyunOSSPropertiesConfig {

    /**
     * 填写Bucket所在地域对应的Endpoint
     * 以华东1（杭州）为例，Endpoint填写为https://oss-cn-hangzhou.aliyuncs.com。
     */
    private String endpoint;

    /**
     * 阿里云账号AccessKey拥有所有API的访问权限，风险很高。
     * 强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户
     */
    private String accessKeyId;
    private String accessKeySecret;

    /**
     * 填写Bucket名称，例如example-bucket。
     */
    private String bucketName;

    /**
     * 填写Object完整路径，例如exampledir/exampleobject.txt。Object完整路径中不能包含Bucket名称。
     * 即目录路径
     */
    private String objectName;

}
