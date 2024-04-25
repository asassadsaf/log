初次加载配置文件<br>
org.apache.logging.log4j.core.LoggerContext#start<br>
org.apache.logging.log4j.core.config.ConfigurationFactory$Factory#getConfiguration<br>
log4j2: -Dlog4j.configurationFile<br>
log4j1: -Dlog4j.configuration<br>
不指定则从classpath下寻找<br>


springboot配置重加载<br>
org.springframework.boot.context.logging.LoggingApplicationListener#initializeSystem<br>
org.springframework.boot.logging.log4j2.Log4J2LoggingSystem#loadConfiguration<br>
--logging.config<br>
