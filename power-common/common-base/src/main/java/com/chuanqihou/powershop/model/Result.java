package com.chuanqihou.powershop.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * @author 传奇后
 * @date 2023/6/19 19:26
 * @description 返回结果
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 提示信息
     */
    private String msg;

    /**
     * 返回数据
     */
    private T data;


    /**
     * 操作成功
     * @param data 数据
     * @return Result
     */
    public static <T> Result<T> success(T data) {
        return new Result<T>(HttpStatus.OK.value(), "success", data);
    }

    /**
     * 操作成功
     * @return Result
     */
    public static <T> Result<T> success() {
        return new Result<T>(HttpStatus.OK.value(), "success");
    }

    /**
     * 操作失败
     * @param code 状态码
     * @param msg 提示信息
     * @return Result
     */
    public static <T> Result<T> fails(Integer code,String msg) {
        return new Result<T>(code, msg);
    }

    /**
     * 操作失败
     * @param data 提示信息
     * @return Result
     */
    public static <T> Result<T> fails(T data) {
        return new Result<T>(data);
    }

    public Result(T data) {
        this.data = data;
    }

    public Result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
