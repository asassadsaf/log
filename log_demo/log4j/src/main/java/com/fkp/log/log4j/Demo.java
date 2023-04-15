package com.fkp.log.log4j;

import org.apache.log4j.Logger;

public class Demo {

    private static final Logger logger = Logger.getLogger(Demo.class);

    public static void main(String[] args) {
        logger.info("This is a info message!");
        logger.debug("This is a debug message!");
    }
}
