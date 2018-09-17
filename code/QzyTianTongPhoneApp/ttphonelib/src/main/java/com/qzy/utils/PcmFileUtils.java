package com.qzy.utils;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by yj.zhang on 2018/8/29.
 */

public class PcmFileUtils {

    public static final void writePcmFile(byte[] data){
       try{
           File file = new File("/mnt/sdcard/test.pcm");
           if(!file.exists()){
               file.createNewFile();
           }
           FileOutputStream outputStream = new FileOutputStream(file,true);
           outputStream.write(data);
           outputStream.flush();

       }catch (Exception e){
           e.printStackTrace();
       }
    }

}
