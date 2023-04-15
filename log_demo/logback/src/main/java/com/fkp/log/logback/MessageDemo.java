package com.fkp.log.logback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageDemo {
    private static final Logger log = LoggerFactory.getLogger("message");

    public static void main(String[] args) {
        log.info("This is message logger");
    }
}
