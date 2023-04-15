package com.fkp.log.verify;

import com.fkp.log.verify.util.RSAUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.LineNumberReader;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入日志文件路径:(输入0结束程序)");
        while (scanner.hasNext()){
            String fileName = scanner.next();
            if("0".equals(fileName)){
                break;
            }
            File file = new File(fileName);
            if(!file.exists()){
                System.out.println("路径错误，请重新输入:");
                continue;
            }
            try (FileReader reader = new FileReader(file);
                 LineNumberReader lineReader = new LineNumberReader(reader)){
                String publicKeyLine = lineReader.readLine();
                String signDataLine = lineReader.readLine();
                if(StringUtils.isBlank(publicKeyLine) || StringUtils.isBlank(signDataLine) || !publicKeyLine.contains("publicKey: ") || !signDataLine.contains("signData: ")){
                    System.out.println("日志文件格式错误，请重新输入:");
                    continue;
                }
                String publicKeyStr = publicKeyLine.substring("publicKey: ".length());
                String signDataStr = signDataLine.substring("signData: ".length());
                StringBuilder builder = new StringBuilder();
                String content;
                while(StringUtils.isNotBlank(content = lineReader.readLine())){
                    builder.append(content);
                }
                byte[] contentBytes = builder.toString().getBytes(StandardCharsets.UTF_8);
                boolean verify = RSAUtils.verify(contentBytes, RSAUtils.getPublicKey(publicKeyStr), Base64.getDecoder().decode(signDataStr));
                if(verify){
                    System.out.println("验签成功");
                }else {
                    System.out.println("验签失败");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("请输入日志文件路径:(输入0结束程序)");
        }
    }
}
