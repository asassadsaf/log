package com.fkp.log.log4j2.action;

import com.fkp.log.log4j2.util.RSAUtils;
import org.apache.logging.log4j.core.Core;
import org.apache.logging.log4j.core.appender.rolling.action.*;
import org.apache.logging.log4j.core.config.plugins.*;

import javax.crypto.KeyGenerator;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.Signature;
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
            try (FileInputStream fis = new FileInputStream(fileName)){
                KeyPair keyPair = RSAUtils.getKeyPair();
                PrivateKey privateKey = keyPair.getPrivate();
                byte[] data = new byte[fis.available()];
                fis.read(data);
                byte[] signData = RSAUtils.sign(data, privateKey);
                Base64.Encoder base64Encoder = Base64.getEncoder();
                String encodeSignData = base64Encoder.encodeToString(signData);
                String encodePublicKey = base64Encoder.encodeToString(keyPair.getPublic().getEncoded());
                Runtime.getRuntime().exec("sed -i '1s/^/signData: " + encodeSignData + "\n/' " + fileName);
                Runtime.getRuntime().exec("sed -i '1s/^/publicKey: " + encodePublicKey + "\n/' " + fileName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
