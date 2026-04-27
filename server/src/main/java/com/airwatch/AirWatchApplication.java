package com.airwatch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AirWatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(AirWatchApplication.class, args);
    }

}
