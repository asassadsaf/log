package com.fkp.log.log4j2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Demo {

    private static final Logger logger = LogManager.getLogger(Demo.class);

    public static void main(String[] args) {
        //和logback同样避免了字符串拼接
        String name = "aaa";
        logger.info("This is a message,message:{}",name);

    }

}
