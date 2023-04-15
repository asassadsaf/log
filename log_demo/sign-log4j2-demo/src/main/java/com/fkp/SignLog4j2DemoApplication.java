package com.fkp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SignLog4j2DemoApplication {
    private static final Logger logger = LogManager.getLogger(SignLog4j2DemoApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SignLog4j2DemoApplication.class, args);
        logger.info("this is a message!");
    }
}
