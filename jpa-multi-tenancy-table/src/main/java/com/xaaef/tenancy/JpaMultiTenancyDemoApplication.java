package com.xaaef.tenancy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;


@EnableAsync
@SpringBootApplication
public class JpaMultiTenancyDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(JpaMultiTenancyDemoApplication.class, args);
    }

}
