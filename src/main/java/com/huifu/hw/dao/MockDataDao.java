package com.huifu.hw.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.huifu.hw.domain.MockDataEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MockDataDao extends BaseMapper<MockDataEntity> {

}
