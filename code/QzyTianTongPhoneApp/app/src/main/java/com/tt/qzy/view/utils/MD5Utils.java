package com.tt.qzy.view.utils;

import com.qzy.utils.ByteUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;

public class MD5Utils {

//    public static String getFileMD5(File file) {
//        if (!file.isFile()) {
//            return null;
//        }
//        MessageDigest digest = null;
//        FileInputStream in = null;
//        byte buffer[] = new byte[1024];
//        int len;
//        try {
//            digest = MessageDigest.getInstance("MD5");
//            in = new FileInputStream(file);
//            while ((len = in.read(buffer, 0, 1024)) != -1) {
//                digest.update(buffer, 0, len);
//            }
//            in.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//        return ByteUtils.byteArrToHexString(digest.digest(),false);
//    }

    public static String getFileMD5(InputStream in) {

        MessageDigest digest = null;

        byte buffer[] = new byte[1024];
        int len;
        try {
            digest = MessageDigest.getInstance("MD5");

            while ((len = in.read(buffer, 0, 1024)) != -1) {
                digest.update(buffer, 0, len);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return ByteUtils.byteArrToHexString(digest.digest(),false);
    }



}
