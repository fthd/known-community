package cn.inc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@MapperScan("cn.inc.manager.mapper")
public class IncSsoBizApplication {

    public static void main(String[] args) {
        SpringApplication.run(IncSsoBizApplication.class, args);
    }

}
