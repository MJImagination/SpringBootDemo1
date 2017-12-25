package com.mj.configuration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by ancun on 2017/12/24.
 * 项目启动入口，配置包根路径com.mj
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.mj")
public class Entry {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(Entry.class, args);
    }
}
