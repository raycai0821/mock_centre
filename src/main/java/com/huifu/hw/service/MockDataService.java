package com.huifu.hw.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.huifu.hw.controller.utils.Result;
import com.huifu.hw.domain.MockDataEntity;

import javax.servlet.http.HttpServletRequest;


public interface MockDataService extends IService<MockDataEntity> {

    MockDataEntity handleRequest(HttpServletRequest request);

    //追加的操作与原始操作通过名称区分，功能类似
    Result saveMockData(MockDataEntity mockDataEntity);

    Result modifyMockData(MockDataEntity mockDataEntity);

    boolean delete(Integer id);

    IPage<MockDataEntity> getPage(int currentPage, int pageSize);

    IPage<MockDataEntity> getPage(int currentPage, int pageSize, MockDataEntity mockDataEntity);


}
