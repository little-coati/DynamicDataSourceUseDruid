package com.example.dynamicDruid;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author Karl
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@MapperScan(basePackages = "com.example.dynamicDruid.mapper")
public class DynamicDataSourceUseDruidApplication {

    public static void main(String[] args) {
        SpringApplication.run(DynamicDataSourceUseDruidApplication.class, args);
    }

}
