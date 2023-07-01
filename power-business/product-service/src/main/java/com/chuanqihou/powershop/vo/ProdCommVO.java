package com.chuanqihou.powershop.vo;

import com.chuanqihou.powershop.domain.ProdComm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 传奇后
 * @date 2023/7/1 19:10
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProdCommVO extends ProdComm {

    /**
     * 用户头像
     */
    private String pic;

    /**
     * 用户昵称
     */
    private String nickName;

}
