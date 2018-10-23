package com.qzy.tiantong.service.utils;

import android.text.TextUtils;

import com.qzy.tiantong.lib.utils.LogUtils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.RandomAccessFile;

public class TtUpdateUtils {

    public static boolean openTtUpdateMode() {
        try {
            FileWriter writer = new FileWriter("/dev/tt-mode");
            writer.write(1);
            writer.flush();
            writer.close();
            LogUtils.e("write 1 successs ......");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        LogUtils.e("write 1 falied......");
        return false;

    }

    public static boolean closeTtUpdateMode() {
        try {
            FileWriter writer = new FileWriter("/dev/tt-mode");
            writer.write(0);
            writer.flush();
            writer.close();
            LogUtils.e("write 0 successs ......");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        LogUtils.e("write 0 falied......");
        return false;
    }

    public static int readTtUpdateMode() {
        try {
            FileReader fileReader = new FileReader("/dev/tt-mode");
            BufferedReader bf = new BufferedReader(fileReader);
            String s = bf.readLine().trim();
            if(TextUtils.isEmpty(s)){
                return 0;
            }
            return Integer.parseInt(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

}
