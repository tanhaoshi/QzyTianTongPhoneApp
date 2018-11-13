package com.qzy.tiantong.service.update;

import com.qzy.tiantong.lib.utils.LogUtils;
import com.qzy.tiantong.lib.utils.MD5Utils;
import com.qzy.tiantong.lib.utils.ZipUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Arrays;

public class UpdateFileManager {

    /**
     * 解压文件
     *
     * @param fileName
     */
    public static boolean unzipUpdteZip(String fileName) {
        try {
            ZipUtils.UnZipFolder(fileName, "/mnt/sdcard/update");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * 复制脚本文件到操作区域
     *
     * @return
     */
    public static boolean copyShFile() {
        try {
            File shFiles = new File("/mnt/sdcard/update/sh");
            if (shFiles.exists()) {
                for (File file : shFiles.listFiles()) {
                    byte[] data = new byte[100 * 1024];
                    int len = 0;
                    FileInputStream inputStream = new FileInputStream(file);
                    File file1 = new File("/mnt/sdcard/update/" + file.getName());
                    FileOutputStream outputStream = new FileOutputStream(file1);
                    while ((len = inputStream.read(data)) != -1) {
                        outputStream.write(Arrays.copyOf(data, len));
                        outputStream.flush();
                    }
                    outputStream.close();
                    inputStream.close();
                    outputStream = null;
                    inputStream = null;
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }

    /**
     * 收到底层升级成功，检查文件md5
     */
    public static boolean checkUpdateFileMD5(String dirPath) {
        try {

            boolean isSuccess = false;
            File updateDirFiel = new File(dirPath);
            if (!updateDirFiel.exists()) {
                LogUtils.e("file not found :" + dirPath);
                return false;
            }

            for (File file : updateDirFiel.listFiles()) {
                if (file.isFile()) {
                    continue;
                }


                if (file.getName().equals("lib")) {
                    isSuccess = checkUpdateFileMD5(file);
                    if (!isSuccess) {
                        break;
                    }
                }

                if (file.getName().equals("server_apk")) {
                    isSuccess = checkUpdateFileMD5(file);
                    if (!isSuccess) {
                        break;
                    }
                }

                if (file.getName().equals("server_bin")) {
                    isSuccess = checkUpdateFileMD5(file);
                    if (!isSuccess) {
                        break;
                    }
                }


            }

            return isSuccess;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * 检查每个文件的md5
     *
     * @param updateDirFiel
     * @return
     */
    private static boolean checkUpdateFileMD5(File updateDirFiel) {
        try {

            boolean isSuccess = false;
            for (File file : updateDirFiel.listFiles()) {
                if (updateDirFiel.getName().equals("lib")) {
                    isSuccess = MD5Utils.checkFileMD5(file, new File("/system/lib/" + file.getName()));
                    LogUtils.d("check file md5 fileName = " + file.getAbsolutePath() + " result = " + isSuccess);
                    if (!isSuccess) {
                        break;
                    }
                }

                if (updateDirFiel.getName().equals("server_apk")) {
                    isSuccess = MD5Utils.checkFileMD5(file, new File("/system/app/" + file.getName()));
                    LogUtils.d("check file md5 fileName = " + file.getAbsolutePath() + " result = " + isSuccess);
                    if (!isSuccess) {
                        break;
                    }
                }

                if (updateDirFiel.getName().equals("server_bin")) {
                    isSuccess = MD5Utils.checkFileMD5(file, new File("/system/bin/" + file.getName()));
                    LogUtils.d("check file md5 fileName = " + file.getAbsolutePath() + "  result = " + isSuccess);
                    if (!isSuccess) {
                        break;
                    }
                }
            }
            LogUtils.d("check file md5 fileName = " + updateDirFiel.getAbsolutePath() + "  result = " + isSuccess);
            return isSuccess;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


}
