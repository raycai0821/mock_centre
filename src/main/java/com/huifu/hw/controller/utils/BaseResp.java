package com.huifu.hw.controller.utils;

import lombok.Data;

/**
 * @author leifeng.cai
 * @version 1.0
 * @desc Create by 2023/1/15 21:06
 */
@Data
public class BaseResp {

    private String msg;

    public BaseResp(String msg) {
        this.msg = msg;
    }
}
