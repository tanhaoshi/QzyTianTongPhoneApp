package com.qzy.test;

/**
 * Created by yj.zhang on 2018/8/16.
 */

public class VoiceTtManagerTools {
    private VoiceReaderSpexx reader;

    private VoiceSenderSpexx sender;

    public VoiceTtManagerTools() {

    }

    public void init() {
        reader = new VoiceReaderSpexx();
        reader.setPlaying(true);
        sender = new VoiceSenderSpexx();
        sender.setRecording(true);

        reader.start();

        sender.start();
    }


    public void free() {
        if (reader != null) {
            reader.setPlaying(false);
            if (reader != null && reader.isAlive()) {
                reader.interrupt();
            }
            reader = null;
        }


        if (sender != null) {
            sender.setRecording(false);
            if (sender != null && sender.isAlive()) {
                sender.interrupt();
            }
            sender = null;
        }
    }


}
