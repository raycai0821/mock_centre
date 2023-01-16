package com.huifu.hw.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huifu.hw.controller.utils.BaseResp;
import com.huifu.hw.controller.utils.Result;
import com.huifu.hw.dao.MockDataDao;
import com.huifu.hw.domain.MockDataEntity;
import com.huifu.hw.service.MockDataService;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;


/**
 * @author leifeng.cai
 * @description
 * @time: 2022/8/1 15:46
 **/

@Service
@Slf4j
public class MockDataServiceImpl extends ServiceImpl<MockDataDao, MockDataEntity> implements MockDataService {

    @Autowired
    private MockDataDao mockDataDao;

    @Override
    public Result saveMockData(MockDataEntity mockDataEntity) {
        log.info("保存前开始校验请求");
        Result validateResult = validatePageRequest(mockDataEntity);

        if (validateResult.getFlag()) {
            try {
                mockDataDao.insert(mockDataEntity);
            } catch (DuplicateKeyException e) {
                return new Result(new BaseResp("URL重复"));
            }
            return new Result(true);
        }
        return new Result(validateResult.getData());
    }

    @Override
    public Result modifyMockData(MockDataEntity mockDataEntity) {

        log.info("修改前开始校验请求");

        Result validateResult = validatePageRequest(mockDataEntity);

        if (validateResult.getFlag()) {
            try {
                mockDataDao.updateById(mockDataEntity);
                //捕获sql异常
            } catch (DuplicateKeyException e) {
                return new Result(new BaseResp("URL重复"));
            }
            return new Result(true);
        }

        return new Result(validateResult.getData());

    }

    @Override
    public boolean delete(Integer id) {
        return mockDataDao.deleteById(id) > 0;
    }

    @Override
    public IPage<MockDataEntity> getPage(int currentPage, int pageSize) {
        IPage page = new Page(currentPage, pageSize);
        mockDataDao.selectPage(page, null);
        return page;
    }

    @Override
    public IPage<MockDataEntity> getPage(int currentPage, int pageSize, MockDataEntity mockDataEntity) {
        LambdaQueryWrapper<MockDataEntity> lqw = new LambdaQueryWrapper<>();
        lqw.like(Strings.isNotEmpty(mockDataEntity.getUrl()), MockDataEntity::getUrl, mockDataEntity.getUrl());
        lqw.like(Strings.isNotEmpty(mockDataEntity.getName()), MockDataEntity::getName, mockDataEntity.getName());
        lqw.eq(Objects.nonNull(mockDataEntity.getId()), MockDataEntity::getId, mockDataEntity.getId());
        lqw.eq(Strings.isNotEmpty(mockDataEntity.getIfUse()), MockDataEntity::getIfUse, mockDataEntity.getIfUse());
        IPage page = new Page(currentPage, pageSize);
        mockDataDao.selectPage(page, lqw);
        return page;
    }


    @Override
    public MockDataEntity handleRequest(HttpServletRequest request) {
        String url = request.getRequestURI();
        log.info("url为" + url);
        log.info("body请求体" + getReqBody(request));

        LambdaQueryWrapper<MockDataEntity> queryWrapper = Wrappers.lambdaQuery(MockDataEntity.class);
        queryWrapper.eq(MockDataEntity::getUrl, url);
        queryWrapper.eq(MockDataEntity::getIfUse, "ACTIVE");
        MockDataEntity mockDataEntity = mockDataDao.selectOne(queryWrapper);

        if (null != mockDataEntity) {
            log.info("查询到mock数据" + mockDataEntity.getName());
            handleResponse(mockDataEntity);
            return mockDataEntity;
        }

        return null;
    }


    public void handleResponse(MockDataEntity mockDataEntity) {

        String respMsg = mockDataEntity.getRespMsg();
        while (needReplace(respMsg)) {
            if (respMsg.contains("_UUID")) {
                respMsg = respMsg.replaceFirst("_UUID", UUID.randomUUID().toString().replaceAll("-", ""));
                log.info("" + "UUID");
            }
            if (respMsg.contains("_AMT")) {
                respMsg = respMsg.replaceFirst("_AMT", BigDecimal.valueOf(Math.random() * 1300).setScale(2, RoundingMode.HALF_UP).toString());
                log.info("AMT");
            }
            if (respMsg.contains("_ACCT")) {
                Random random = new Random();
                respMsg = respMsg.replaceFirst("_ACCT", String.valueOf(Math.abs(random.nextLong())));
            }
        }
        mockDataEntity.setRespMsg(respMsg);
    }


    /**
     * @desc 从请求里获取请求body
     */

    public String getReqBody(HttpServletRequest request) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder("");
        try {
            br = request.getReader();
            String str;
            while ((str = br.readLine()) != null) {
                sb.append(str);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != br) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

    /**
     * 替换mock响应体中的变量
     *
     * @param origin
     * @return
     */

    private boolean needReplace(String origin) {

        return (origin.contains("_UUID") || origin.contains("_AMT") || origin.contains("_ACCT"));

    }

    /**
     * 校验页面传递过来的respmsg请求体是否符合格式
     *
     * @return
     */
    public Result validatePageRequest(MockDataEntity mockDataEntity) {

        //如果添加的是json格式则校验
        if (null != mockDataEntity.getContentType()) {
            if (("json").equalsIgnoreCase(mockDataEntity.getContentType())) {
                try {
                    JSONObject.parseObject(mockDataEntity.getRespMsg());
                } catch (Exception e) {
                    return new Result(false, new BaseResp(mockDataEntity.getContentType() + "格式错误"));
                }
                //xml校验
            } else if (("xml").equalsIgnoreCase(mockDataEntity.getContentType())) {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                try {
                    DocumentBuilder builder = factory.newDocumentBuilder();
                    builder.parse(new InputSource(new StringReader(mockDataEntity.getRespMsg())));
                } catch (Exception e) {

                    return new Result(false, new BaseResp(mockDataEntity.getContentType() + "格式错误"));
                }
            }
        }

        return new Result(true);
    }
}


