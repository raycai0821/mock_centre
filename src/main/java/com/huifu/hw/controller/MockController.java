package com.huifu.hw.controller;

import com.huifu.hw.controller.utils.BaseResp;
import com.huifu.hw.domain.MockDataEntity;
import com.huifu.hw.service.MockDataService;
import com.huifu.hw.service.MockRiskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@RestController
@RequestMapping("/api")
public class MockController {

    @Autowired
    private MockDataService mockDataService;

    @Autowired
    private MockRiskService mockRiskService;

    @RequestMapping("/rc/**")
    public ResponseEntity submitToRc(HttpServletRequest httpServletRequest){


        return null;
    }

    @RequestMapping("/**")
    public ResponseEntity getMockData(HttpServletRequest request) {
        MockDataEntity mockDataEntity = mockDataService.handleRequest(request);
        if (Objects.isNull(mockDataEntity)){
            return new ResponseEntity(new BaseResp("未找到对应MOCK数据"),HttpStatus.BAD_REQUEST);
        }
        Integer latency = mockDataEntity.getLatency();
        Integer http_resp_code = mockDataEntity.getHttpRespCode();
        if (null != latency) {
            try {
                Thread.sleep(mockDataEntity.getLatency());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        HttpHeaders httpHeaders = new HttpHeaders();
        switch (mockDataEntity.getContentType()) {
            case "json":
                httpHeaders.add("Content-Type", "application/json");
                break;
            case "xml":
                httpHeaders.add("Content-Type", "text/xml");
                break;
        }

        return new ResponseEntity(mockDataEntity.getRespMsg(), httpHeaders, HttpStatus.valueOf(http_resp_code));

    }

}
