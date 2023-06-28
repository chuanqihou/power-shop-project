package com.chuanqihou.powershop.util;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.PutObjectResult;
import com.chuanqihou.powershop.config.AliyunOSSPropertiesConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.InputStream;

/**
 * @author 传奇后
 * @date 2023/6/27 9:35
 * @description 阿里云OSS文件上传和下载工具类
 */
@Component
public class AliyunOOSUtil {

    /**
     * 阿里云OSS配置对象
     */
    @Autowired
    private AliyunOSSPropertiesConfig propertiesConfig;

    /**
     * 定义OSSClient实例对象
     */
    private OSS ossClient = null;

    /**
     * 初始化OSSClient实例对象
     * 在bean初始完成之后执行
     */
    @PostConstruct
    public void initConfig() {
        // 初始化OSSClient实例对象
        ossClient = new OSSClientBuilder().build(propertiesConfig.getEndpoint(), propertiesConfig.getAccessKeyId(), propertiesConfig.getAccessKeySecret());
    }

    /**
     * 文件上传
     * @param fileName 文件名
     * @param in 输入流
     * @return boolean
     */
    public String upLoad(String fileName, InputStream in) {

        // 文件流上传到阿里云OOS
        ossClient.putObject(propertiesConfig.getBucketName(), propertiesConfig.getObjectName() + fileName, in);

/*        try {
            // 文件流上传到阿里云OOS
            ossClient.putObject(propertiesConfig.getBucketName(), propertiesConfig.getObjectName() + fileName, in);
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }*/

        /*
            返回公网访问链接地址，如：
            https://cqh-power-shop.oss-cn-guangzhou.aliyuncs.com/images/9dcca019-9432-43fa-94f5-72a227b1bb61.jpg
         */
        return "https://"+propertiesConfig.getBucketName()+"."+propertiesConfig.getEndpoint()+"/"+propertiesConfig.getObjectName()+fileName;
    }

    /**
     * 文件下载
     * @param filename 文件名
     * @return inputStream
     */
    public InputStream download(String filename) {
        OSSObject object = ossClient.getObject(propertiesConfig.getBucketName(), propertiesConfig.getObjectName() + filename);
        InputStream in = null;
        if (object != null) {
            in = object.getObjectContent();
        }
        return in;
    }

}
