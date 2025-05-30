package com.qfleaf.bootstarter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.qfleaf.bootstarter.dao.mapper")
public class BootstarterApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootstarterApplication.class, args);
    }

}
