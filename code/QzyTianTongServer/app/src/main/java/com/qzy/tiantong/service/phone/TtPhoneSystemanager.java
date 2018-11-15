package com.qzy.tiantong.service.phone;

import android.content.Context;
import android.content.Intent;

import com.qzy.tiantong.lib.utils.LogUtils;
import com.qzy.tiantong.service.phone.data.SosMessage;
import com.qzy.tt.data.TtPhoneSosMessageProtos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class TtPhoneSystemanager {


    /**
     * 恢复出厂设置
     *
     * @param context
     */
    public static void doMasterClear(Context context) {
        Intent intent = new Intent("android.intent.action.MASTER_CLEAR");
        intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
        intent.putExtra("android.intent.extra.REASON", "MasterClearConfirm");
        intent.putExtra("android.intent.extra.WIPE_EXTERNAL_STORAGE", false);
        context.sendBroadcast(intent);
        // Intent handling is asynchronous -- assume it will happen soon.
    }

    /**
     * 存储sos短信
     *
     * @param ttPhoneSosMessage
     */
    public static void saveSosMessage(TtPhoneSosMessageProtos.TtPhoneSosMessage ttPhoneSosMessage) {
        try {
            LogUtils.d("getSaveSosMsg");
            File file = new File("/mnt/sdcard/tiantong_work");
            if (!file.isDirectory()) {
                file.delete();
            }

            if (!file.exists()) {
                file.mkdirs();
            }

            file = new File("/mnt/sdcard/tiantong_work/sosmessage.txt");
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(ttPhoneSosMessage.getPhoneNumber() + "\r\n");
            fileWriter.write(ttPhoneSosMessage.getMessageContent() + "\r\n");
            fileWriter.write(ttPhoneSosMessage.getDelaytime() + "\r\n");
            fileWriter.flush();
            fileWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 存储sos短信
     */
    public static SosMessage getSosMessage() {
        SosMessage sosMessage = null;
        try {
            File file = new File("/mnt/sdcard/tiantong_work/sosmessage.txt");
            if (!file.exists()) {
               return null;
            }
            sosMessage = new SosMessage();
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String text = bufferedReader.readLine();
            sosMessage.setPhoneNumber(text.trim());
            text = bufferedReader.readLine();
            sosMessage.setMessage(text.trim());
            text = bufferedReader.readLine();
            sosMessage.setDelayTime(Integer.parseInt(text.trim()));

            fileReader.close();

            return sosMessage;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sosMessage;
    }


}
