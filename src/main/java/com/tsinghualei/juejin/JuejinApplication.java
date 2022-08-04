package com.tsinghualei.juejin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import tk.mybatis.spring.annotation.MapperScan;

@EnableWebMvc
@SpringBootApplication(scanBasePackages = "com.tsinghualei.juejin")
@MapperScan(basePackages = "com.tsinghualei.juejin.mapper")
public class JuejinApplication {

    public static void main(String[] args) {
        SpringApplication.run(JuejinApplication.class, args);
    }

}
