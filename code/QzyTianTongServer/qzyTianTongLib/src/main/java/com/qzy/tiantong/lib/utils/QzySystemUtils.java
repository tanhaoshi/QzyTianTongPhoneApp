package com.qzy.tiantong.lib.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Method;

/**
 * Created by Administrator on 2018/5/21/021.
 */

public class QzySystemUtils {

    public static String getEmmcId(){
        String emmcid = null;
        try{
            FileReader fileReader = new FileReader(new File("/sys/class/mmc_host/mmc0/mmc0:0001/serial"));
            BufferedReader br = new BufferedReader(fileReader);
            emmcid = br.readLine();

        }catch (Exception e){
            e.printStackTrace();
        }
       return emmcid;
    }


    /**
     * 获取串口
     * @return
     */
    public static String getSerialNumberCustom(){
        String serial="";
        try{
            Class<?>c= Class.forName("android.os.SystemProperties");
            Method get =c.getMethod("get",String.class);
            return (String)get.invoke(c,"sys.serialno");
        }catch(Exception e){
            e.printStackTrace();
        }
        return serial;
    }

    public static String getSystemProperties(String key) {
        String value = "";
        try{
            Class<?>c = Class.forName("android.os.SystemProperties");
            Method get = c.getMethod("get", String.class);
            return (String)get.invoke(c,  key);
        }catch (Exception e){
            e.printStackTrace();
        }
        return value;
    }

}
