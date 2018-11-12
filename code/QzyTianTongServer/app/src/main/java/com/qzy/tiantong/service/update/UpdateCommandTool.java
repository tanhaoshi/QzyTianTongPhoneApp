package com.qzy.tiantong.service.update;


import com.qzy.tiantong.lib.utils.ByteUtils;

/**
 * 升级说明：
 * APP与底层服务通过本地SOCKET通信
 * 升级按文件分步进行，每一个文件升级对应一个升级脚本执行
 * 所有的升级脚本放置在/mnt/sdcard/update/目录下
 * 如 update1.sh 内容 cp /mnt/sdcard/update/qzyserver.apk /system/app/
 * Recovery.sh 恢复上一版本
 * Backup.sh 备份当前版本
 * 升级指令格式： 0xaa, 0xff, 0x01(指令：升级), 0x01(数据：1代表第一个脚本) ===  0xaa , 0xff代表命令头
 * 恢复指令： APP 0xaa, 0xff, 0x03  恢复上一个版本
 * 重启指令： APP 0xaa, 0xff, 0x02  服务程序 重启猫
 * <p>
 * 1.服务端第一次收到升级指令后会先运行backup.sh备份当前可用版本，然后处理升级指令。
 * <p>
 * 2.指令流程 APP  0xaa, 0xff, 0x01, 0x01(第1个升级脚本)	服务程序
 * APP  0xaa, 0xff, 0x02, 0x1  服务程序 			开始升级(执行update1.sh)
 * APP  0xaa, 0xff, 0x01, 0x0(返回结束状态)      升级结束
 * 红色代表某一步，绿色代表返回状态 0表示成功 非0代表失败
 * <p>
 * 3.所有升级完成并检测成功发送 0xaa, 0xff, 0x02，系统会重启。
 * <p>
 * 4.守护apk检测程序是否正常运行，如果不正常 发送 0xaa, 0xff, 0x03 ，猫执行recovery.sh 恢复上一个版本并重启。
 */
public class UpdateCommandTool {

    public static final byte[] head = new byte[]{(byte) 0xaa, (byte) 0xff};
    public static final byte command_back_up = (byte) 0x01;  //备份
    public static final byte command_update = (byte) 0x02;  //升级
    public static final byte command_recover = (byte) 0x03; // 恢复
    public static final byte command_reboot = (byte) 0x04; // 重启

    /**
     * 备份
     *
     * @return
     */
    public static byte[] getBackupCommand() {
        byte[] command = new byte[3];
        command[0] = head[0];
        command[1] = head[1];
        command[2] = command_back_up;
        return command;
    }

    /**
     * 升级
     *
     * @return
     */
    public static byte[] getUpdateCommand() {
        byte[] command = new byte[3];
        command[0] = head[0];
        command[1] = head[1];
        command[2] = command_update;
        return command;
    }

    /**
     * 返回上一个版本
     *
     * @return
     */
    public static byte[] getRecoverCommand() {
        byte[] command = new byte[3];
        command[0] = head[0];
        command[1] = head[1];
        command[2] = command_recover;
        return command;
    }

    /**
     * 重启
     *
     * @return
     */
    public static byte[] getRebootCommand() {
        byte[] command = new byte[3];
        command[0] = head[0];
        command[1] = head[1];
        command[2] = command_reboot;
        return command;
    }


}
