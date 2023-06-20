package com.chuanqihou.powershop.util;

import com.alibaba.fastjson.JSON;
import com.chuanqihou.powershop.model.Result;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author 传奇后
 * @date 2023/6/20 10:37
 * @description 响应工具类
 */
public class ResponseUtil {

    /**
     * 响应json数据
     * @param response  响应对象
     * @param result   响应结果
     */
    public static void responseJson(HttpServletResponse response, Object result) {
        PrintWriter writer = null;
        try {
            // 将响应结果转换为json字符串
            String jsonString = JSON.toJSONString(result);
            // 设置响应编码和类型
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json;charset=UTF-8");
            writer = response.getWriter();
            // 响应结果
            writer.write(jsonString);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            // 刷新和关闭流
            if (writer != null) {
                writer.flush();
                writer.close();
            }
        }
    }


}
