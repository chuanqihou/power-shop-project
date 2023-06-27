package com.chuanqihou.powershop.controller;

import com.chuanqihou.powershop.model.Result;
import com.chuanqihou.powershop.util.AliyunOOSUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * @author 传奇后
 * @date 2023/6/27 11:09
 * @description 文件上传下载
 */
@RestController
@RequestMapping("/admin/file")
public class FileController {

    /**
     * 阿里云OOS工具类
     */
    @Autowired
    private AliyunOOSUtil aliyunOOSUtil;

    /**
     * 文件上传
     * @param file 文件对象
     * @return result 文件公网访问路径
     * @throws IOException IO异常
     */
    @PostMapping("/upload/element")
    public Result<String> uploadFile(MultipartFile file) throws IOException {
        //获取原文件名
        String originalFilename = file.getOriginalFilename();
        //获取文件后缀名
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        //重新设置文件名 [ UUID+suffix ]
        String filename = UUID.randomUUID().toString() + suffix;
        // 获取文件字节流
        InputStream inputStream = file.getInputStream();
        // 将文件保存
        String resultPath = aliyunOOSUtil.upLoad(filename, inputStream);
        // 返回文件访问路径
        return Result.success(resultPath);
    }

}
