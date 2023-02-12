package com.xiaozhu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.xiaozhu.mapper")
@EnableScheduling
public class XiaoZhuBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(XiaoZhuBlogApplication.class, args);
    }
}
