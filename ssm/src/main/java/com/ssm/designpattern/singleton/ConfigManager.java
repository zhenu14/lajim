package com.ssm.designpattern.singleton;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigManager {

    private static final String PFILE = "C:\\ideaCode\\ssm\\src\\main\\java\\com\\ssm\\designpattern\\singleton\\ssm.properties";

    private File file = null;

    private long lastModifiedTime = 0;

    private Properties properties = null;

    private static ConfigManager instance = new ConfigManager();

    private ConfigManager(){
        file = new File(PFILE);
        lastModifiedTime = file.lastModified();
        if(lastModifiedTime == 0){
            System.err.println(PFILE + " does not exist");
        }
        properties = new Properties();
        try{
            properties.load(new FileInputStream(PFILE));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    synchronized public static ConfigManager getInstance(){
        return instance;
    }

    public final Object getConfigItem(String name,Object defaultVal){
        long newTime = file.lastModified();
        if(newTime == 0){
            if(lastModifiedTime == 0){
                System.err.println(PFILE + " does not exist");
            }else {
                System.err.println(PFILE + " is deleted");
            }
        }else if(newTime > lastModifiedTime){
            properties.clear();
            try{
                properties.load(new FileInputStream(PFILE));
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        lastModifiedTime = newTime;
        Object val = properties.getProperty(name);
        if(val == null){
            return defaultVal;
        }else {
            return val;
        }
    }
}
