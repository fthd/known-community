package com.known;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(value = "com.known.manager.mapper")
public class KnownManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(KnownManagerApplication.class, args);
    }

}
