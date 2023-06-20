package com.chuanqihou.powershop.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * @author 传奇后
 * @date 2023/6/19 19:26
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {

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
    private Object data;


    /**
     * 操作成功
     * @param data 数据
     * @return Result
     */
    public static Result success(Object data) {
        return new Result(HttpStatus.OK.value(), "success", data);
    }

    /**
     * 操作成功
     * @return Result
     */
    public static Result success() {
        return new Result(HttpStatus.OK.value(), "success");
    }

    /**
     * 操作失败
     * @param code 状态码
     * @param msg 提示信息
     * @return Result
     */
    public static Result fails(Integer code,String msg) {
        return new Result(code, msg);
    }

    public Result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
