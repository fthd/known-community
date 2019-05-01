package cn.inc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class IncEurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(IncEurekaApplication.class, args);
    }

}
