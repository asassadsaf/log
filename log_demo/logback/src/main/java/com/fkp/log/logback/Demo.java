package com.fkp.log.logback;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
public class Demo {

//    private static final Logger logger = LoggerFactory.getLogger(Demo.class);

    public static void main(String[] args) throws InterruptedException {
        String name = "aaa";
        //解决了jcl字符串拼接问题
        for (int i =0;i<10;i++){
            log.info("This is a info message,message:{}",name);
            log.debug("This is a debug message,message:{}",name);
            Thread.sleep(3000);
        }
    }
}
