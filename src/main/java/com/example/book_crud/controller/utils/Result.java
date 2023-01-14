package com.example.book_crud.controller.utils;

import lombok.Data;

@Data
public class Result {
    private Boolean flag;
    private Object data;
    private String msg;
    public Result(){

    }
    public Result(Boolean flag){
        this.flag = flag;
        this.msg = msg;
    }
    public Result(Boolean flag,Object data){
        this.flag = flag;
        this.data = data;
        this.msg = msg;
    }
}
