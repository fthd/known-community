package cn.inc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(value = "cn.inc.manager.mapper")
public class IncManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(IncManagerApplication.class, args);
    }

}
