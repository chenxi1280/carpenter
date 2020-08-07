package com.wxcz.carpenter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.wxcz.carpenter.dao")
public class CarpenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarpenterApplication.class, args);
    }

}
