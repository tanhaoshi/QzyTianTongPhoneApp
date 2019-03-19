package com.qzy.tiantong.service.utils;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.util.concurrent.ExecutorService;

public class ModuleDormancyUtil {

    public static String getNodeString(String path) {
        String prop = "1";// 默认值
        try {
            FileReader fileReader = new FileReader(path);
            BufferedReader reader = new BufferedReader(fileReader);
            prop = reader.readLine();
            fileReader.close();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("exception", "error : " + e.getMessage());
        }
        return prop;
    }

    public static synchronized void writeNode(final String path, final String state) {
        if(path.endsWith("/trigger")){
           writeNodeFileTriger(path,state);
        }else{
            writeNodeFile(path,state);
        }
    }

    public static synchronized void writeNodeFileTriger(final String path, final String state) {
        ExecutorService executorService = ThreadUtils.getCachedPool();
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        OutputStream output = null;
                        OutputStreamWriter outputWrite = null;
                        PrintWriter print = null;
                        File file = new File(path);
                        Log.i("ModuleDormancyUtil", "write file[" + path + "] with value (" + state + ")");

                        output = new FileOutputStream(file);
                        outputWrite = new OutputStreamWriter(output);
                        print = new PrintWriter(outputWrite);
                        print.print(state);
                        print.flush();
                        print.close();
                        outputWrite.close();
                        output.close();

                        //强制刷新内存到文件
                        Runtime.getRuntime().exec("sync");

                        FileReader fileReader = new FileReader(file);
                        BufferedReader bufferedReader = new BufferedReader(fileReader);
                        String value = bufferedReader.readLine();
                        fileReader.close();
                        bufferedReader.close();

                        boolean isSuccess = false;

                        String compare = "[" + state + "]";

                        isSuccess = value.contains(compare);

                        Log.i("ModuleDormancyUtil", "write file[" + path + "] with value (" + state + ")" + " get writ value = " + value + " is success = " + isSuccess);
                        if (isSuccess) {
                            break;
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        if(executorService.isShutdown()){
            executorService.shutdownNow();
        }
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    while (true) {
//                        OutputStream output = null;
//                        OutputStreamWriter outputWrite = null;
//                        PrintWriter print = null;
//                        File file = new File(path);
//                        Log.i("ModuleDormancyUtil", "write file[" + path + "] with value (" + state + ")");
//
//                        output = new FileOutputStream(file);
//                        outputWrite = new OutputStreamWriter(output);
//                        print = new PrintWriter(outputWrite);
//                        print.print(state);
//                        print.flush();
//                        print.close();
//                        outputWrite.close();
//                        output.close();
//
//                        //强制刷新内存到文件
//                        Runtime.getRuntime().exec("sync");
//
//
//                        FileReader fileReader = new FileReader(file);
//                        BufferedReader bufferedReader = new BufferedReader(fileReader);
//                        String value = bufferedReader.readLine();
//                        fileReader.close();
//                        bufferedReader.close();
//
//                        boolean isSuccess = false;
//
//                        String compare = "[" + state + "]";
//
//                        isSuccess = value.contains(compare);
//
//
//                        Log.i("ModuleDormancyUtil", "write file[" + path + "] with value (" + state + ")" + " get writ value = " + value + " is success = " + isSuccess);
//                        if (isSuccess) {
//                            break;
//                        }
//
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
    }

    public static synchronized void writeNodeFile(final String path, final String state) {
        //  1代表已经休眠 0代表正常可工作状态
//        try{
//            BufferedWriter bufWriter = null;
//            bufWriter = new BufferedWriter(new FileWriter(path));
//            bufWriter.write(state);  // 写操作
//            bufWriter.close();
//        }catch (Exception e){
//            e.printStackTrace();
//            Log.e("exception","error : " + e.getMessage());
//        }
        ExecutorService executorService = ThreadUtils.getCachedPool();
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        OutputStream output = null;
                        OutputStreamWriter outputWrite = null;
                        PrintWriter print = null;
                        File file = new File(path);
                        Log.i("ModuleDormancyUtil", "write file[" + path + "] with value (" + state + ")");

                        output = new FileOutputStream(file);
                        outputWrite = new OutputStreamWriter(output);
                        print = new PrintWriter(outputWrite);
                        print.print(state);
                        print.flush();
                        print.close();
                        outputWrite.close();
                        output.close();

                        //强制刷新内存到文件
                        Runtime.getRuntime().exec("sync");

                        FileReader fileReader = new FileReader(file);
                        BufferedReader bufferedReader = new BufferedReader(fileReader);
                        String value = bufferedReader.readLine();
                        fileReader.close();
                        bufferedReader.close();

                        boolean isSuccess = value.equals(state);

                        Log.i("ModuleDormancyUtil", "write file[" + path + "] with value (" + state + ")" + " get writ value = " + value + " is success = " + isSuccess);
                        if (isSuccess) {
                            break;
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        if(executorService.isShutdown()){
            executorService.shutdownNow();
        }
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    while (true) {
//                        OutputStream output = null;
//                        OutputStreamWriter outputWrite = null;
//                        PrintWriter print = null;
//                        File file = new File(path);
//                        Log.i("ModuleDormancyUtil", "write file[" + path + "] with value (" + state + ")");
//
//                        output = new FileOutputStream(file);
//                        outputWrite = new OutputStreamWriter(output);
//                        print = new PrintWriter(outputWrite);
//                        print.print(state);
//                        print.flush();
//                        print.close();
//                        outputWrite.close();
//                        output.close();
//
//                        //强制刷新内存到文件
//                        Runtime.getRuntime().exec("sync");
//
//
//                        FileReader fileReader = new FileReader(file);
//                        BufferedReader bufferedReader = new BufferedReader(fileReader);
//                        String value = bufferedReader.readLine();
//                        fileReader.close();
//                        bufferedReader.close();
//
//
//                        boolean isSuccess = value.equals(state);
//
//
//                        Log.i("ModuleDormancyUtil", "write file[" + path + "] with value (" + state + ")" + " get writ value = " + value + " is success = " + isSuccess);
//                        if (isSuccess) {
//                            break;
//                        }
//
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
    }
}
