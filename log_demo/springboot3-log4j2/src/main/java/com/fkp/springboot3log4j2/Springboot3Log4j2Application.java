package com.fkp.springboot3log4j2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Springboot3Log4j2Application {

    public static void main(String[] args) {
        SpringApplication.run(Springboot3Log4j2Application.class, args);
        Logger logger = LoggerFactory.getLogger(Springboot3Log4j2Application.class);
        logger.info("aaa");
    }

}
