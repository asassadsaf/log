package com.fkp.action;

import com.fkp.util.RSAUtils;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.Core;
import org.apache.logging.log4j.core.appender.rolling.action.AbstractAction;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;

import java.io.*;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.util.Base64;

@Plugin(name = "SignAction", category = Core.CATEGORY_NAME)
public final class SignAction extends AbstractAction {

    private final Logger log = LogManager.getLogger(SignAction.class);

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
            try (FileInputStream fis = new FileInputStream(file)){
                KeyPair keyPair = RSAUtils.getKeyPair();
                PrivateKey privateKey = keyPair.getPrivate();
                byte[] data = IOUtils.toByteArray(fis);
                byte[] signData = RSAUtils.sign(data, privateKey);
                Base64.Encoder base64Encoder = Base64.getEncoder();
                String encodeSignData = base64Encoder.encodeToString(signData);
                String encodePublicKey = base64Encoder.encodeToString(keyPair.getPublic().getEncoded());
                getExecShellProcess("sed -i \'1i\\signData: " + encodeSignData + "\' " + fileName).waitFor();
                getExecShellProcess("sed -i \'1i\\publicKey: " + encodePublicKey + "\' " + fileName).waitFor();
            } catch (Exception e) {
                log.error("Failed to sign log file during log cutting.", e);
            }
        }
        return true;
    }

    public static Process getExecShellProcess(String command) throws IOException {
        String[] cmd = {"/bin/sh", "-c", command };
        return Runtime.getRuntime().exec(cmd);
    }
}
