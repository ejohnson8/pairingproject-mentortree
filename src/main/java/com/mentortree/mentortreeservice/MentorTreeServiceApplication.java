package com.mentortree.mentortreeservice;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDiscoveryClient
public class MentorTreeServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MentorTreeServiceApplication.class, args);
    }
}
