package org.lansir.ip.codecs;

/**
 * 保持不变
 * @author org.lansir.ip
 */
public class Speex {

	private static final int[] mCompressionValues;

	static {
		System.loadLibrary("speex_jni");
		mCompressionValues = new int[] { 6, 10, 15, 20, 20, 28, 28, 38, 38, 46, 62 };
	}

	public static int getCompressionValue(int speexQualatyValue) {
		return mCompressionValues[speexQualatyValue];
	}
	
	public static native void close();
	public static native int decode(byte[] decodeData, int offSet, short[] resultData);
	public static native int encode(short[] encodeData, byte[] resultData);
	public static native void open(int compressionValue);
}
