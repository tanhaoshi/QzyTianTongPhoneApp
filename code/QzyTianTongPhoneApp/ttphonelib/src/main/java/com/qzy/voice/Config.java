package com.qzy.voice;

import android.media.AudioFormat;
import android.media.AudioRecord;

public class Config {

	public static int AUDIO_RECORD_BUFFER;
	public static int AUDIO_TRACK_BUFFER;
	
	public static int CODEC_AMR = 0x0;
	public static int CODEC_SPEEX = 0x1;
	
	static {
		int minBufferSize = AudioRecord.getMinBufferSize(44100, 2, AudioFormat.ENCODING_PCM_16BIT);
		
		AUDIO_RECORD_BUFFER = Math.max(44100, calculate(minBufferSize));
		AUDIO_TRACK_BUFFER = Math.max(160, calculate(2));
	}
	
	/**
	 * 我也不能啥意思
	 * @param buffer
	 * @return
	 */
	private static int calculate(int buffer) {
		double d = 16484 / (double)buffer; // 0x4064
		int e = (int) Math.ceil(d);
		return e - 160;
	}
}
