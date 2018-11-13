package com.qzy.tiantong.lib.utils;

import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;

public class MD5Utils {

    public static String getFileMD5(File file) {
        if (!file.isFile()) {
            return null;
        }
        MessageDigest digest = null;
        FileInputStream in = null;
        byte buffer[] = new byte[1024];
        int len;
        try {
            digest = MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);
            while ((len = in.read(buffer, 0, 1024)) != -1) {
                digest.update(buffer, 0, len);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return ByteUtils.byteArrToHexString(digest.digest(), false);
    }


    public static boolean checkFileMD5(File file, File file1) {
        String md51 = MD5Utils.getFileMD5(file);
        String md52 = MD5Utils.getFileMD5(file1);
        boolean flag = md51.equals(md52) ? true : false;
        return flag;
    }

}
