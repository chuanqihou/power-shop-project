package com.chuanqihou.powershop;

import com.chuanqihou.powershop.config.AliyunOSSPropertiesConfig;
import com.chuanqihou.powershop.util.AliyunOOSUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.InputStream;

/**
 * @author 传奇后
 * @date 2023/6/27 9:58
 * @description
 */
@SpringBootTest
public class MyTest {

    @Autowired
    private AliyunOSSPropertiesConfig aliyunOSSPropertiesConfig;

    @Autowired
    private AliyunOOSUtil aliyunOOSUtil;

    @Test
    void test1() {
        System.out.println(aliyunOSSPropertiesConfig);
        System.out.println(aliyunOOSUtil);
        InputStream aaa = aliyunOOSUtil.download("aaa");
        System.out.println(aaa);
        System.out.println("111");
    }

}
