package com.qzy.ftpserver;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.Authority;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.usermanager.PropertiesUserManagerFactory;
import org.apache.ftpserver.usermanager.impl.BaseUser;
import org.apache.ftpserver.usermanager.impl.WritePermission;

import java.io.File;
import java.io.FileOutputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FtpServerManager {

    private static String hostip = ""; // 本机IP
    public static final int PORT = 9997;
    // sd卡目录
    @SuppressLint("SdCardPath")
//    public static final String dir = Environment.getExternalStorageDirectory().getAbsolutePath();
    public static final String dir = "/mnt/sdcard/";
    public static final String dirname = dir + "tiantong_update/";
    // ftp服务器配置文件路径
    private static final String filename = dirname + "users.properties";
    private  FtpServer mFtpServer = null;

    private static FtpServerManager instance;
    private String properties = "ftpserver.user.admin.userpassword=E10ADC3949BA59ABBE56E057F20F883E\r\n" +
            "ftpserver.user.admin.homedirectory=/mnt/sdcard/ftp\r\n" +
            "ftpserver.user.admin.enableflag=true\r\n" +
            "ftpserver.user.admin.writepermission=true\r\n" +
            "ftpserver.user.admin.maxloginnumber=4\r\n" +
            "ftpserver.user.admin.maxloginperip=4\r\n" +
            "ftpserver.user.admin.idletime=3000\r\n" +
            "ftpserver.user.admin.uploadrate=4800\r\n" +
            "ftpserver.user.admin.downloadrate=4800\r\n";

    public synchronized static FtpServerManager getIntance() {
        if (instance == null) {
            instance = new FtpServerManager();
        }
        return instance;
    }

    /**
     * 创建服务器配置文件
     */
    public void creatDirsFiles() {
        try {
            File dir = new File(dirname);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            FileOutputStream fos = null;
            File sourceFile = new File(filename);
            if (sourceFile.exists()) {
                sourceFile.delete();
            }
            fos = new FileOutputStream(sourceFile);
            fos.write(properties.getBytes());
            if (fos != null) {
                fos.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 开启FTP服务器
     *
     * @param
     */
    public void startFtpServer() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    boolean isRunnin = true;
                    while (isRunnin){
                        hostip = getLocalIpAddress();
                        if(!TextUtils.isEmpty(hostip)){
                            break;
                        }
                        Thread.sleep(1000);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                LogUtils.d("获取本机IP = " + hostip);
                creatDirsFiles();
                FtpServerFactory serverFactory = new FtpServerFactory();
                PropertiesUserManagerFactory userManagerFactory = new PropertiesUserManagerFactory();
                File files = new File(filename);
                //设置配置文件
                userManagerFactory.setFile(files);
                serverFactory.setUserManager(userManagerFactory.createUserManager());

                // 设置监听IP和端口号
                ListenerFactory factory = new ListenerFactory();
                factory.setPort(PORT);
                factory.setServerAddress(hostip);
                serverFactory.addListener("default", factory.createListener());
                BaseUser user = new BaseUser();

                //设置权限
                List<Authority> authorities = new ArrayList<>();
                authorities.add(new WritePermission());
                user.setAuthorities(authorities);
                //设置用户名密码
                user.setName("admin");
                user.setPassword("123456");
                user.setEnabled(true);
                user.setMaxIdleTime(3000);
                user.setHomeDirectory(dirname);
                try {
                    serverFactory.getUserManager().save(user);
                } catch (FtpException e) {
                    e.printStackTrace();
                }
                if (mFtpServer != null) {
                    mFtpServer.stop();
                }
                // start the server
                mFtpServer = serverFactory.createServer();
                try {
                    mFtpServer.start();
//                    FTPClientUtils.getIntance().ftpConnet();
                    LogUtils.d("open  ip = " + hostip + " port = " + PORT);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 关闭FTP服务器
     */
    public void stopFtpServer() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (mFtpServer != null) {
                    mFtpServer.stop();
                    mFtpServer = null;
                    LogUtils.d("close ip = " + hostip + " port = " + PORT);
                }
            }
        }).start();
    }

    /**
     * 获取本机ip
     */
    public String getLocalIpAddress() {
        try {
            List<NetworkInterface> interfaces = Collections
                    .list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf
                        .getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        String sAddr = addr.getHostAddress().toUpperCase();
                        boolean isIPv4 = Isipv4(sAddr);
                        if (isIPv4) {
                            return sAddr;
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }


    public void onStartServer() {

        startFtpServer();

    }

    public static boolean Isipv4(String ipv4) {
        if (ipv4 == null || ipv4.length() == 0) {
            return false;//字符串为空或者空串
        }
        //因为java doc里已经说明, split的参数是reg, 即正则表达式, 如果用"|"分割, 则需使用"\\|"
        String[] parts = ipv4.split("\\.");
        if (parts.length != 4) {//分割开的数组根本就不是4个数字
            return false;
        }
        for (int i = 0; i < parts.length; i++) {
            try {
                int n = Integer.parseInt(parts[i]);
                //数字不在正确范围内
                if (n < 0 || n > 255) {
                    return false;
                }
            } catch (NumberFormatException e) {//转换数字不正确
                return false;
            }
        }
        return true;
    }

}
