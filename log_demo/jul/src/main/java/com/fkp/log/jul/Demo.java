package com.fkp.log.jul;

import com.sun.xml.internal.ws.api.ResourceLoader;

import java.util.logging.Logger;

public class Demo {

    static {
        System.setProperty("java.util.logging.config.file",Demo.class.getClassLoader().getResource("logging.properties").getPath());
    }

    private static final Logger logger = Logger.getLogger(Demo.class.getName());

    public static void main(String[] args) {
        logger.info("This is a info message");
        logger.finest("This is a finest message");
        System.out.println(System.getProperty("java.util.logging.config.file"));
    }
}
