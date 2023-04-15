package com.fkp;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.Stream;

@SpringBootTest
class SignLog4j2DemoApplicationTests {

    @Test
    void contextLoads() throws IOException {
        File file = new File("E:\\Example\\Java\\log\\log_demo\\sign-log4j2-demo\\logs\\fkp.log");
        FileInputStream fis = new FileInputStream(file);
        BufferedReader reader = new BufferedReader(new InputStreamReader(fis, StandardCharsets.UTF_8));
        String s = reader.readLine();
        System.out.println(s);
        byte[] bytes = IOUtils.toByteArray(reader, StandardCharsets.UTF_8);
        System.out.println(new String(bytes, StandardCharsets.UTF_8));
//        byte[] bytes = new byte[fis.available()];
//        fis.read(bytes);
//        System.out.println(new String(bytes, StandardCharsets.UTF_8));
    }

}
