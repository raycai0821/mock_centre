package com.example.book_crud.domain;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author leifeng.cai
 * @description
 * @time: 2022/8/1 15:40
 **/
@Data
@TableName("mock_data")
public class MockDataEntity {


    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;

    private String name;

    private String url;

    private String ifUse;


    private Integer httpRespCode;


    private String respMsg;

    private Integer latency;


    private String contentType;

    private String reqMsg;

    @TableField(exist = false)
    private String errorMsg;

    @Override
    public String toString() {
        return "MockDataEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", ifUse=" + ifUse +
                ", httpRespCode=" + httpRespCode +
                ", respMsg='" + respMsg + '\'' +
                ", latency=" + latency +
                ", contentType='" + contentType + '\'' +
                ", reqMsg='" + reqMsg + '\'' +
                '}';
    }
}
