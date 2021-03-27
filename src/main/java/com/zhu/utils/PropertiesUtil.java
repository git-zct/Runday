package com.zhu.utils;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;


public class PropertiesUtil {

    private static Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);

    private static Properties props;

    static {//执行顺序：静态代码块->（优于）普通代码块->(优于)构造代码块
        String fileName = "datasource.properties";
        props = new Properties();
        try {
            InputStream rs = PropertiesUtil.class.getClassLoader().getResourceAsStream(fileName);
            InputStreamReader is = new InputStreamReader(rs, "UTF-8");
            props.load(is);
        } catch (IOException e) {
            logger.error("配置文件读取异常",e);
        }
    }

    public static String getProperty(String key){
        String value = props.getProperty(key.trim());
        if(StringUtils.isBlank(value)){
            return null;
        }
        return value.trim();
    }

    public static String getProperty(String key,String defaultValue){

        String value = props.getProperty(key.trim());//key.trim避免两边的空格
        if(StringUtils.isBlank(value)){
            value = defaultValue;
        }
        return value.trim();
    }



}
