package com.fkp.log.log4j2;

import org.apache.logging.log4j.core.Core;
import org.apache.logging.log4j.core.appender.rolling.action.*;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.plugins.*;

import java.io.*;
import java.nio.charset.StandardCharsets;

@Plugin(name = "MyAction", category = Core.CATEGORY_NAME, printObject = true)
public class MyAction extends AbstractAction {

    private final String fileName;

    public MyAction(String fileName) {
        this.fileName = fileName;
    }

    @PluginFactory
    public static MyAction createMyAction(
            @PluginAttribute("fileName") String fileName) throws IOException {
        return new MyAction(fileName);
    }
    @Override
    public boolean execute(){
        File file = new File(fileName);
        if(file.exists()){
            try {
                FileInputStream fis = new FileInputStream(fileName);
//                int available = fis.available();
//                byte[] bytes = new byte[available];
//                fis.read(bytes);
                byte[] buffer = new byte[1024];
                while(fis.read(buffer) > 0){
                    System.out.println(new String(buffer));
                }
                fis.close();
                FileOutputStream fos = new FileOutputStream(fileName, true);
                fos.write("fkp\n".getBytes(StandardCharsets.UTF_8));
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
