package com.fkp.action;

import com.fkp.util.RSAUtils;
import org.apache.logging.log4j.core.Core;
import org.apache.logging.log4j.core.appender.rolling.action.AbstractAction;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.util.Base64;

@Plugin(name = "SignAction", category = Core.CATEGORY_NAME)
public final class SignAction extends AbstractAction {

    private final String fileName;

    private SignAction(String fileName) {
        this.fileName = fileName;
    }

    @PluginFactory
    public static SignAction createMyAction(
            @PluginAttribute("fileName") String fileName) throws IOException {
        return new SignAction(fileName);
    }

    @Override
    public boolean execute(){
        File file = new File(fileName);
        if(file.exists()){
            try (FileReader reader = new FileReader(file);
                 LineNumberReader lineReader = new LineNumberReader(reader)){
                KeyPair keyPair = RSAUtils.getKeyPair();
                PrivateKey privateKey = keyPair.getPrivate();
                String line;
                StringBuilder sb = new StringBuilder();
                while ((line = lineReader.readLine()) != null){
                    sb.append(line);
                }
                byte[] data = sb.toString().getBytes(StandardCharsets.UTF_8);
                byte[] signData = RSAUtils.sign(data, privateKey);
                Base64.Encoder base64Encoder = Base64.getEncoder();
                String encodeSignData = base64Encoder.encodeToString(signData);
                String encodePublicKey = base64Encoder.encodeToString(keyPair.getPublic().getEncoded());
                System.out.println("sed -i \'1i\\signData: " + encodeSignData + "\' " + fileName);
                getExecShellProcess("sed -i \'1i\\signData: " + encodeSignData + "\' " + fileName).waitFor();
                getExecShellProcess("sed -i \'1i\\publicKey: " + encodePublicKey + "\' " + fileName).waitFor();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }
    public static Process getExecShellProcess(String command) {

        Process pid = null;

        String[] cmd = { "/bin/sh", "-c", command };
        //String[] cmd = { "/usr/bin/python", "-c", command };
        try {

            pid = Runtime.getRuntime().exec(cmd);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return pid;
    }
}
