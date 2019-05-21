package com.example.demo.controller;

import com.example.demo.config.ConfigProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private ConfigProperties configProperties;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Value("${value}")
    private String value;

    @RequestMapping("/")
    public String index(){
        logger.debug("这是：debug");
        logger.info("这是：info");
        logger.warn("这是：warn");
        logger.error("这是：error");
        return "hello springboot " + value + configProperties.getName();
    }
}
