package com.qzy.tiantong.service.rtptest.audio.sender;

import com.qzy.tiantong.lib.utils.LogUtils;
import com.qzy.voice.VoiceManager;

public class AudioRecorder implements Runnable {

	String LOG = "RecorderSocket ";

	private boolean isRecording = false;

	private int BUFFER_FRAME_SIZE = -1;

	//
	private byte[] samples;// data
	// the size of audio read from recorder
	private int bufferRead = 0;
	// samples size
	private int bufferSize = 0;

	/*
	 * start recording
	 */
	public void startRecording() {


		// 初始化recorder
		new Thread(this).start();
	}

	/*
	 * stop
	 */
	public void stopRecording() {
		BUFFER_FRAME_SIZE = -1;
		this.isRecording = false;
	}

	public boolean isRecording() {
		return isRecording;
	}

	public void run() {
		// start encoder before recording
		AudioEncoder encoder = AudioEncoder.getInstance();
		encoder.startEncoding();
		LogUtils.e(LOG + "audioRecord startRecording()");
		BUFFER_FRAME_SIZE = VoiceManager.initPcmRecorder();
		LogUtils.e(LOG + "start recording");

		this.isRecording = true;
		while (isRecording) {
			if(BUFFER_FRAME_SIZE == -1){
               break;
			}
			samples = new byte[160 * 8];
			bufferRead = VoiceManager.readPcmData(samples);
			//LogUtils.e(LOG + "start recording = bufferRead" + bufferRead);
			if (bufferRead == 0) {
				// add data to encoder
				encoder.addData(samples, samples.length);
			}
			/*try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}*/
		}
		LogUtils.e(LOG + "end recording");
		VoiceManager.releasePcmRecorder();
		encoder.stopEncoding();

	}
}
