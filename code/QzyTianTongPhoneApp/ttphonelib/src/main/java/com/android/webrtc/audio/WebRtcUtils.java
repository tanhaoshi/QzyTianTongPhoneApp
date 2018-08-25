package com.android.webrtc.audio;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Created by yj.zhang on 2018/8/7.
 */

public class WebRtcUtils {

    public static final byte[] doAecm(byte[] data) {
        try {
            MobileAEC aecm = new MobileAEC(null);
            aecm.setAecmMode(MobileAEC.AggressiveMode.MOST_AGGRESSIVE).prepare();

            final int cacheSize = 320;
            byte[] pcmInputCache = new byte[cacheSize];
            ByteArrayInputStream fin = new ByteArrayInputStream(data);
            ByteArrayOutputStream fout = new ByteArrayOutputStream();

            // core procession
            while (fin.read(pcmInputCache, 0, pcmInputCache.length) != -1) {
                // convert bytes[] to shorts[], and make it into little endian.
                short[] aecTmpIn = new short[cacheSize / 2];
                short[] aecTmpOut = new short[cacheSize / 2];
                ByteBuffer.wrap(pcmInputCache).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer().get(aecTmpIn);

                // aecm procession, for now the echo tail is hard-coded 10ms,
                // but you
                // should estimate it correctly each time you call
                // echoCancellation, otherwise aecm
                // cannot work.
                aecm.farendBuffer(aecTmpIn, cacheSize / 2);
                aecm.echoCancellation(aecTmpIn, null, aecTmpOut, (short) (cacheSize / 2), (short) 10);

                // output
                byte[] aecBuf = new byte[cacheSize];
                ByteBuffer.wrap(aecBuf).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer().put(aecTmpOut);

                fout.write(aecBuf);
            }

            fout.close();
            fin.close();
            aecm.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


}
