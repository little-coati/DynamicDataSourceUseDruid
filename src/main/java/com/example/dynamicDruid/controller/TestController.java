package com.example.dynamicDruid.controller;

import com.example.dynamicDruid.service.TestService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Karl
 * @since 2023-05-25
 */
@RestController
@RequestMapping("/test")
@AllArgsConstructor
public class TestController {

    private final TestService testService;

    @GetMapping("/testMysql")
    public Integer testMysql() {
        return testService.testMysql();
    }

    @GetMapping("/testTd")
    public Integer testTd() {
        return testService.testTd();
    }
}