package com.qzy.tiantong.service.rtptest.audio.receiver;

import android.util.Log;



import com.qzy.tiantong.service.rtptest.audio.AudioCodec;
import com.qzy.tiantong.service.rtptest.audio.AudioData;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class AudioDecoder implements Runnable {

	String LOG = "AudioDecoder";
	private static AudioDecoder decoder;

	private static final int MAX_BUFFER_SIZE = 2048;

	private byte[] decodedData = new byte[1024];// data of decoded
	private boolean isDecoding = false;
	private BlockingQueue<AudioData> dataList = null;
	private Thread thread;

	public static AudioDecoder getInstance() {
		if (decoder == null) {
			decoder = new AudioDecoder();
		}
		return decoder;
	}

	private AudioDecoder() {
		dataList = new LinkedBlockingQueue<>();
	}

	/*
	 * add Data to be decoded
	 * 
	 * @ data:the data recieved from server
	 * 
	 * @ size:data size
	 */
	public void addData(byte[] data, int size) {
		AudioData adata = new AudioData();
		adata.setSize(size);
		byte[] tempData = new byte[size];
		System.arraycopy(data, 0, tempData, 0, size);
		adata.setRealData(tempData);
		dataList.add(adata);
		// Log.e(LOG, "添加一次数据 " + dataList.size());

	}

	/*
	 * start decode AMR data
	 */
	public void startDecoding() {
		System.out.println(LOG + "开始解码");
		if (isDecoding) {
			return;
		}
		thread = new Thread(this);
		thread.start();
	}

	public void run() {
		// start player first
		AudioPlayer player = AudioPlayer.getInstance();
		player.startPlaying();
		//
		this.isDecoding = true;
		// init ILBC parameter:30 ,20, 15
		AudioCodec.audio_codec_init(30);

		Log.d(LOG, LOG + "initialized decoder");
		int decodeSize = 0;
		AudioData encodedData;
		try {
			while ((encodedData = dataList.take()) != null) {
                if (!isDecoding) {
                    break;
                }
				byte[] data = encodedData.getRealData();
				if (data.length > 0) {
					// add decoded audio to player
					player.addData(data, data.length);
				}
            }


		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally {
			System.out.println(LOG + "stop decoder");
			// stop playback audio
			player.stopPlaying();
		}

	}

	public void stopDecoding() {
		this.isDecoding = false;

		if(thread != null && thread.isAlive()){
			thread.interrupt();
		}
	}
}