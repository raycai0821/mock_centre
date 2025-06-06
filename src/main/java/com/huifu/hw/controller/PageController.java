package com.huifu.hw.controller;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.huifu.hw.controller.utils.Result;
import com.huifu.hw.domain.MockDataEntity;
import com.huifu.hw.service.MockDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @author leifeng.cai
 * @version 1.0
 * @desc Create by 2023/1/13 23:52
 */
@Slf4j
@RestController
@RequestMapping("/page")
public class PageController {

    @Autowired
    private MockDataService mockDataService;

    @GetMapping
    public Result queryAll() {
        return new Result(true, mockDataService.list());
    }

    @PostMapping
    public Result save(@RequestBody MockDataEntity mockDataEntity) {
        log.info("开始新增mock" + mockDataEntity);
        return mockDataService.saveMockData(mockDataEntity);
    }

    @PutMapping
    public Result update(@RequestBody MockDataEntity mockDataEntity) {
        return mockDataService.modifyMockData(mockDataEntity);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return new Result(mockDataService.delete(id));
    }

    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        return new Result(true, mockDataService.getById(id));
    }

    @GetMapping("{currentPage}/{pageSize}")
    public Result getPage(@PathVariable int currentPage, @PathVariable int pageSize, MockDataEntity mockDataEntity) {
        log.info("-----开始分页查询-----" + "查询条件" + mockDataEntity);
        IPage<MockDataEntity> page = mockDataService.getPage(currentPage, pageSize, mockDataEntity);
        //如果当前页码值大于总页码数，那么重新执行查询操作，使用最大页码值作为当前页码值。
        if (currentPage > page.getPages() && 0 != page.getTotal()) {
            page = mockDataService.getPage((int) page.getPages(), pageSize);
        }
        return new Result(true, page);
    }


}
