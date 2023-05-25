package com.example.dynamicDruid.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.example.dynamicDruid.mapper.mysql.MysqlTestMapper;
import com.example.dynamicDruid.mapper.td.TdTestMapper;
import com.example.dynamicDruid.service.TestService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author Karl
 * @since 2023-05-25
 */
@Service
@AllArgsConstructor
public class TestServiceImpl implements TestService {

    private final MysqlTestMapper mysqlTestMapper;
    private final TdTestMapper tdTestMapper;

    @Override
    public Integer testMysql() {
        return mysqlTestMapper.test();
    }

    @Override
    public Integer testTd() {
        return tdTestMapper.test();
    }
}