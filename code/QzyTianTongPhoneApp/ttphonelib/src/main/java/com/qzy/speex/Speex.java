package com.qzy.speex;

/**
 * Created by yanghao1 on 2017/4/18.
 */

public class Speex {

    private static final int DEFAULT_COMPRESSION = 5;

    private boolean isFree = false;

    static {
        try {
            System.loadLibrary("qzyspeex");
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

  //  private static Speex speex = null;

    public Speex() {
        isFree = false;
        open(DEFAULT_COMPRESSION);
        //preprocessStateInit(512,8000,8000);
    }

  /*  public static Speex getInstance() {
        if (speex == null) {
            synchronized (Speex.class) {
                if (speex == null) {
                    speex = new Speex();
                }
            }
        }
        return speex;
    }*/

    public void release(){
        close();
        isFree = true;
        //preprocessDestroy();
    }

    public  boolean isFree() {
        return isFree;
    }

    public native int open(int compression);

    public native int getFrameSize();

    public native int decode(byte encoded[], short lin[], int size);

    public native int encode(short lin[], byte encoded[], int size);

    public native void close();

    public native int preprocessStateInit(int frame_size, int rate, int agc_level);
    public native int echoStateInit(int frame_size, int rate, int filter_length);
    public native int preprocessRunDenoise(short[] lin);

    public native int runEcho(short[] ref_buf, short[] echo_buf, short[] e_buf,int frame_size);
    public native void preprocessDestroy();
    public native void echoDestroy();


}
