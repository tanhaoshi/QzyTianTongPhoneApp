package com.qzy.tiantong.lib.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

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




}
