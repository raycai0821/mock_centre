package com.huifu.hw.controller.utils;

import lombok.Data;

@Data
public class Result {
    private Boolean flag = false;
    private Object data;

    public Result(){

    }

    public Result(Boolean flag,Object data){
        this.flag = flag;
        this.data = data;

    }

    public Result(boolean flag){
        this.flag = flag;

    }


    public Result(Object data){
        this.data = data;

    }
}
