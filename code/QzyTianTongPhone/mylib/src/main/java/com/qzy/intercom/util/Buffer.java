package com.qzy.intercom.util;

/**
 * Created by yj.zhang on 2018/8/1/001.
 */

public class Buffer {
    private int size;
    private byte[] buf = null;
    public int validLen = 0;
    public int head = 0;
    public boolean isCompleted = false;

    public Buffer(int size) {
        this.size = size;
        this.buf = new byte[size];
        this.head = this.validLen = 0;
    }

    public Buffer(byte[] buf, int len) {
        this.buf = new byte[len];
        for (int i = 0; i < len; i++) {
            this.buf[i] = buf[i];
        }
        this.validLen = len;
        this.head = 0;
        this.size = len;
    }

    public void init() {
        head = 0;
        validLen = 0;
        isCompleted = false;
    }

    /**
     * read data ,not move the head nor the validLen
     *
     * @param len
     * @return
     */
    public byte[] read(int len) {
        if (len <= 0 || validLen <= 0) {
            return null;
        }
        if (len > validLen) {
            return null;
        }
        byte[] readBuf = new byte[len];
        if (head + len <= size) {
            for (int i = 0; i < len; i++) {
                readBuf[i] = buf[head + i];
            }
        } else if (head + len > size) {
            //first read from head to size,total read bytes is size-head
            for (int i = 0; i < size - head; i++) {
                readBuf[i] = buf[head + i];
            }
            //then read from the beginning of the buffer,read bytes is len+head-size
            for (int i = 0; i < len + head - size; i++) {
                readBuf[size - head + i] = buf[i];
            }
        }
        return readBuf;
    }

    /**
     * read data ,not move the head nor the validLen
     *
     * @param len
     * @return
     */
    public byte[] read(int offset, int len) {
        if (len <= 0 || validLen <= 0) {
            return null;
        }
        if (len > validLen) {
            return null;
        }
        byte[] readBuf = new byte[len];
        if (head + offset + len <= size) {
            for (int i = 0; i < len; i++) {
                readBuf[i] = buf[head + offset + i];
            }
        } else if (head + offset + len > size) {
            //first read from head to size,total read bytes is size-head
            for (int i = 0; i < size - head - offset; i++) {
                readBuf[i] = buf[head + offset + i];
            }
            //then read from the beginning of the buffer,read bytes is len+head-size
            for (int i = 0; i < len + head + offset - size; i++) {
                readBuf[size - head - offset + i] = buf[i];
            }
        }
        return readBuf;
    }

    /**
     * read data ,and move the head ,decrease the validLen
     *
     * @param len
     * @return
     */
    public byte[] readAndMove(int len) {
        byte[] read = read(len);
        //move head to new location,decrease validLen
        validLen -= len;
        if (head + len <= size) {
            head += len;
            if (head == size) {
                head = 0;
            }
        } else {
            head = (len + head - size);
        }
        //加入清除之前的数据
        /*byte[] tmp = new byte[size - head];
        System.arraycopy(buf,head,tmp,0,tmp.length);
        buf = tmp;*/
        return read;
    }

    /**
     * append data to buffer,increase validLen
     *
     * @param src
     * @param len
     */
    public void append(byte[] src, int len) {
        if (getAvailabeSize() < len) {
            reAlloc(len);
        }

        if (head + validLen + len <= size) {
            for (int i = 0; i < len; i++) {
                buf[head + validLen + i] = src[i];
            }
        } else if (head + validLen + len > size) {
            if (head + validLen >= size) {
                for (int i = 0; i < len; i++) {
                    buf[head + validLen - size + i] = src[i];
                }
            } else if (head + validLen < size) {
                //first append from head+validLen to size
                for (int i = 0; i < size - head - validLen; i++) {
                    buf[head + validLen + i] = src[i];
                }
                //then append from the beginning of the buffer
                for (int i = 0; i < len + head + validLen - size; i++) {
                    buf[i] = src[size - head - validLen + i];
                }
            }
        }
        validLen += len;
    }

    /**
     * realloc buffer ,new size equals with old size plus len
     *
     * @param len
     */
    public void reAlloc(int len) {
        byte[] tmp = new byte[size + len];
        if (validLen > 0) {
            byte[] tmp1 = read(validLen);
            for (int i = 0; i < tmp1.length; i++) {
                tmp[i] = tmp1[i];
            }
            this.validLen = tmp1.length;
        }
        this.buf = tmp;
        this.head = 0;
        this.size += len;
    }

    public void modify(int offset, int len, byte[] src) {
        for (int i = 0; i < len; i++) {
            buf[offset + i] = src[i];
        }
    }

    /**
     * get available buffer size,if not enough ,call reAlloc to add the buffer size dymaticly
     *
     * @return
     */
    public int getAvailabeSize() {
        return size - validLen;
    }

    public byte[] getBuffer() {
        return buf;
    }

    public int getSize() {
        return size;
    }

}
