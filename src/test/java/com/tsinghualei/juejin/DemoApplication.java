package com.tsinghualei.juejin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Autowired
    public DemoApplication(RedisTemplate redisTemplate,
                           StringRedisTemplate stringRedisTemplate) {
        this.redisTemplate = redisTemplate;
        this.stringRedisTemplate = stringRedisTemplate;
    }
    @Autowired
    private final RedisTemplate redisTemplate;
    @Autowired
    private final StringRedisTemplate stringRedisTemplate;

    @Bean
    @SuppressWarnings("unchecked")
    CommandLineRunner runRedisTemplateTest(){
        return args -> {
            System.out.println("redisTemplate");
            redisTemplate.opsForValue().set("name", "Kitty");
            Object name = redisTemplate.opsForValue().get("name");
            System.out.println(name);
            System.out.println("-----------End---------");
        };
    }

    @Bean
    CommandLineRunner runStringRedisTemplateTest(){
        return args -> {
            System.out.println("stringRedisTemplate start");
            Object name = stringRedisTemplate.opsForValue().get("name");
            System.out.println(name);
            System.out.println("-----------End---------");
        };
    }
}