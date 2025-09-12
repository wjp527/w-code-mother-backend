package com.wjp.waicodemotherbackend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.wjp.waicodemotherbackend.mapper")
public class WAiCodeMotherBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(WAiCodeMotherBackendApplication.class, args);
    }

}
