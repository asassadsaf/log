package com.fkp.log.verify;

import com.fkp.log.verify.util.RSAUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
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
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(Files.newInputStream(file.toPath()), StandardCharsets.UTF_8))){
                String publicKeyLine = reader.readLine();
                String signDataLine = reader.readLine();
                if(StringUtils.isBlank(publicKeyLine) || StringUtils.isBlank(signDataLine) || !publicKeyLine.contains("publicKey: ") || !signDataLine.contains("signData: ")){
                    System.out.println("日志文件格式错误，请重新输入:");
                    continue;
                }
                String publicKeyStr = publicKeyLine.substring("publicKey: ".length());
                String signDataStr = signDataLine.substring("signData: ".length());
                byte[] contentBytes = IOUtils.toByteArray(reader, StandardCharsets.UTF_8);
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
