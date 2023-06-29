package com.chuanqihou.powershop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 传奇后
 * @date 2023/6/29 15:24
 * @description 发送短信DTO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SMSCodeDTO {

    /**
     * 手机号
     */
    private String phonenum;

    /**
     * 验证码
     */
    private String code;

}
