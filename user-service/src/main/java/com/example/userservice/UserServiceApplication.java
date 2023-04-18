package com.example.userservice;

import com.example.userservice.error.FeignErrorDecoder;
import feign.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient // Eureka Server에 등록
@EnableFeignClients // 다른 마이크로 서비스와의 통신 위함
public class UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    @LoadBalanced // 마이크로 서비스의 이름으로 사용을 가능하게 함
//    public RestTemplate getRestTemplate() {
//        return new RestTemplate();
//    }

    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL; // 전체 레벨 확인
    }

//    @Bean
//    public FeignErrorDecoder getFeignErrorDecoder() {
//        return new FeignErrorDecoder();
//    }

}

// application이 구동 되면서 이 안에 선언되어 있었던 Bean들을 메모리에 등록 시킴