package com.qzy.rtptest.audio.sender;

import com.qzy.rtptest.Global;
import com.qzy.rtptest.audio.AudioData;
import com.qzy.tiantong.lib.utils.LogUtils;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class AudioSender implements Runnable {
	String LOG = "AudioSender ";

	private boolean isSendering = false;
	private BlockingQueue<AudioData> dataList;

	private int nSendPacks = 0;
	private int nSendBytes = 0;

	private Thread thread;

	public AudioSender() {
		dataList = new LinkedBlockingQueue<>();
	}

	public void addData(byte[] data, int size) {
		AudioData encodedData = new AudioData();
		encodedData.setSize(size);
		byte[] tempData = new byte[size];
		System.arraycopy(data, 0, tempData, 0, size);
		encodedData.setRealData(tempData);
		dataList.add(encodedData);
		//Log.e(LOG, "addData !!!");
	}

	/*
	 * send data to server
	 */
	private void sendData(byte[] data, int size) {
		    //LogUtils.e(" ok ==" + Global.ok);
			if (!Global.ok) {
				return;
			}
			byte[] buf = new byte[size];
			System.arraycopy(data, 0, buf, 0, size);
			try {
				Global.rtpManager.rtpApp.rtpSession.sendData(buf);
			} catch (NullPointerException e) {
				return;
			}
			nSendPacks++;
			nSendBytes += size;
			/*Message message = new Message();
            message.what = 1;
            message.arg1 = nSendPacks;
            message.arg2 = nSendBytes;
            Global.rtpManager.handler.sendMessage(message);*/
			//Log.e(LOG, "发送一段数据 " + data.length);
	}

	/*
	 * start sending data
	 */
	public void startSending() {
		thread = new Thread(this);
		thread.start();
		thread = new Thread(this);
		thread.start();
	}

	/*
	 * stop sending data
	 */
	public void stopSending() {
		this.isSendering = false;
	}

	// run
	public void run() {
		this.isSendering = true;
		LogUtils.e(LOG + "start....");
		AudioData encodedData;
		try {
			while ((encodedData = dataList.take()) != null) {
                if (!isSendering) {
                   break;
                }
				sendData(encodedData.getRealData(), encodedData.getSize());
            }
			LogUtils.e(LOG + "stop!!!!");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
}