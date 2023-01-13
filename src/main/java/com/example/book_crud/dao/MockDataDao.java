package com.example.book_crud.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.book_crud.domain.MockDataEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MockDataDao extends BaseMapper<MockDataEntity> {

}
