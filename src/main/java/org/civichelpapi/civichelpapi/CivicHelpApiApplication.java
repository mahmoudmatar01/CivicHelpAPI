package org.civichelpapi.civichelpapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CivicHelpApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(CivicHelpApiApplication.class, args);
    }

}
