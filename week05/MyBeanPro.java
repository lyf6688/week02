package com.school.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "week05")
public class MyBeanPro {
    private String test;
}
