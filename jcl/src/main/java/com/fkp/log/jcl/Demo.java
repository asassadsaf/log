package com.fkp.log.jcl;

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

        //存在字符串拼接问题，当不需要输出debug信息时也会进行字符串拼接，当次数非常多时，频繁的字符串拼接会降低性能
        String name = "aaa";
        log.debug("This is a debug message,message:"+name);

        //解决方法，但写法比较麻烦
        if(log.isDebugEnabled()){
            log.debug("This is a debug message,message:"+name);
        }

    }

}
