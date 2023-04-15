package com.fkp.log.slf4j;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * jcl是一个日志接口，具体的日志输出要看使用的具体实现，日志级别用谁就在谁的配置文件中配置
 * 使用具体实现的优先级为：log4j > jul > SimpleLog(jcl自带的日志实现)
 */
public class Demo {

    private static final Log log = LogFactory.getLog(Demo.class);

    public static void main(String[] args) {
        log.info("This is a info message");

    }

}
