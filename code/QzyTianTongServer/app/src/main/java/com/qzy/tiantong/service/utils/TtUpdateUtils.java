package com.qzy.tiantong.service.utils;

import java.io.FileWriter;

public class TtUpdateUtils {

    public static void openTtUpdateMode() {
        try {
            FileWriter writer = new FileWriter("/dev/tt-mode");
            writer.write(1);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void closeTtUpdateMode() {
        try {
            FileWriter writer = new FileWriter("/dev/tt-mode");
            writer.write(0);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
