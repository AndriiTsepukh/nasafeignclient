package com.example.nasafeignclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class NasafeignclientApplication {

    public static void main(String[] args) {
        SpringApplication.run(NasafeignclientApplication.class, args);
    }

}
