package com.known;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan //扫描servlet组件 否则过滤器, 监听器不生效
@MapperScan(value = "com.known.manager.mapper")
public class KnownWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(KnownWebApplication.class, args);
    }

}
