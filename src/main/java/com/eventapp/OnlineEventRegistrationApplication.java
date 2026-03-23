package com.eventapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class OnlineEventRegistrationApplication {
    public static void main(String[] args) {
        SpringApplication.run(OnlineEventRegistrationApplication.class, args);
    }
}

