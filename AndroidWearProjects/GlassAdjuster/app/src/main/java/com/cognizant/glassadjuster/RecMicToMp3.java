/* 
 * Copyright (c) 2011-2012 Yuichi Hirano
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.cognizant.glassadjuster;

import android.content.Context;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Handler;
import com.cognizant.utils.Appsettings;
import com.cognizant.utils.WaveformView;
import com.uraroji.garage.android.lame.SimpleLame;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidParameterException;
public class RecMicToMp3 {

    static {
        System.loadLibrary("mp3lame");
    }

    public static final int MSG_REC_STARTED = 0;
    public static final int MSG_REC_STOPPED = 1;
    public static final int MSG_ERROR_GET_MIN_BUFFERSIZE = 2;
    public static final int MSG_ERROR_CREATE_FILE = 3;
    public static final int MSG_ERROR_REC_START = 4;
    public static final int MSG_ERROR_AUDIO_RECORD = 5;
    public static final int MSG_ERROR_AUDIO_ENCODE = 6;
    public static final int MSG_ERROR_WRITE_FILE = 7;
    public static final int MSG_ERROR_CLOSE_FILE = 8;
    WaveformView wfprm;
    /**
     * MP3�t�@�C����ۑ�����t�@�C���p�X
     */
    private String mFilePath;
    private short[] mAudioBuffer;
    /**
     * �T���v�����O��?[�g
     */
    private int mSampleRate;
    /**
     * �^������
     */
    private boolean mIsRecording = false;
    private Handler mHandler;

    public RecMicToMp3(WaveformView mWaveformView, Context con, int sampleRate) {
        String file_path = Appsettings.getAudioPath(con);
        if (sampleRate <= 0) {
            throw new InvalidParameterException(
                    "Invalid sample rate specified.");
        }
        this.mFilePath = file_path;
        this.mSampleRate = sampleRate;
        wfprm = mWaveformView;
    }

    public void start() {

        if (mIsRecording) {
            return;
        }

        new Thread() {
            @Override
            public void run() {
                android.os.Process
                        .setThreadPriority(android.os.Process.THREAD_PRIORITY_URGENT_AUDIO);

                final int minBufferSize = AudioRecord.getMinBufferSize(
                        mSampleRate, AudioFormat.CHANNEL_IN_MONO,
                        AudioFormat.ENCODING_PCM_16BIT);

                if (minBufferSize < 0) {
                    if (mHandler != null) {
                        mHandler.sendEmptyMessage(MSG_ERROR_GET_MIN_BUFFERSIZE);
                    }
                    return;
                }

                AudioRecord audioRecord = new AudioRecord(
                        MediaRecorder.AudioSource.MIC, mSampleRate,
                        AudioFormat.CHANNEL_IN_MONO,
                        AudioFormat.ENCODING_PCM_16BIT, minBufferSize * 2);

                // PCM buffer size (5sec)
                short[] buffer = new short[mSampleRate * (16 / 8) * 1 * 5]; // SampleRate[Hz]
                // *
                // 16bit
                // *
                // Mono
                // *
                // 5sec
                byte[] mp3buffer = new byte[(int) (7200 + buffer.length * 2 * 1.25)];
                FileOutputStream output = null;
                try {
                    output = new FileOutputStream(new File(mFilePath));
                } catch (FileNotFoundException e) {
                    if (mHandler != null) {
                        mHandler.sendEmptyMessage(MSG_ERROR_CREATE_FILE);
                    }
                    return;
                }

                // Lame init
                SimpleLame.init(mSampleRate, 1, mSampleRate, 32);

                mIsRecording = true;
                try {
                    try {
                        audioRecord.startRecording();
                    } catch (IllegalStateException e) {
                        // �^���̊J�n�Ɏ��s����
                        if (mHandler != null) {
                            mHandler.sendEmptyMessage(MSG_ERROR_REC_START);
                        }
                        return;
                    }

                    try {

                        if (mHandler != null) {
                            mHandler.sendEmptyMessage(MSG_REC_STARTED);
                        }

                        int readSize = 0;
                        while (mIsRecording) {
                            readSize = audioRecord.read(buffer, 0,
                                    minBufferSize);
                            mAudioBuffer = new short[minBufferSize / 2];
                            wfprm.updateAudioData(buffer);
                            if (readSize < 0) {
                                // �^�����ł��Ȃ�
                                if (mHandler != null) {
                                    mHandler.sendEmptyMessage(MSG_ERROR_AUDIO_RECORD);
                                }
                                break;
                            }
                            // �f?[�^���ǂ�?��߂Ȃ�����?�?��͉������Ȃ�
                            else if (readSize == 0) {

                            }
                            // �f?[�^������Ă���?�?�
                            else {
                                int encResult = SimpleLame.encode(buffer,
                                        buffer, readSize, mp3buffer);
                                if (encResult < 0) {
                                    // �G���R?[�h�Ɏ��s����
                                    if (mHandler != null) {
                                        mHandler.sendEmptyMessage(MSG_ERROR_AUDIO_ENCODE);
                                    }
                                    break;
                                }
                                if (encResult != 0) {
                                    try {
                                        output.write(mp3buffer, 0, encResult);
                                    } catch (IOException e) {
                                        // �t�@�C����?���?o���Ɏ��s����
                                        if (mHandler != null) {
                                            mHandler.sendEmptyMessage(MSG_ERROR_WRITE_FILE);
                                        }
                                        break;
                                    }
                                }
                            }
                        }

                        int flushResult = SimpleLame.flush(mp3buffer);
                        if (flushResult < 0) {
                            // �G���R?[�h�Ɏ��s����
                            if (mHandler != null) {
                                mHandler.sendEmptyMessage(MSG_ERROR_AUDIO_ENCODE);
                            }
                        }
                        if (flushResult != 0) {
                            try {
                                output.write(mp3buffer, 0, flushResult);
                            } catch (IOException e) {
                                // �t�@�C����?���?o���Ɏ��s����
                                if (mHandler != null) {
                                    mHandler.sendEmptyMessage(MSG_ERROR_WRITE_FILE);
                                }
                            }
                        }

                        try {
                            output.close();
                        } catch (IOException e) {
                            // �t�@�C���̃N�??[�Y�Ɏ��s����
                            if (mHandler != null) {
                                mHandler.sendEmptyMessage(MSG_ERROR_CLOSE_FILE);
                            }
                        }
                    } finally {
                        audioRecord.stop(); // �^�����~����
                        audioRecord.release();
                    }
                } finally {
                    SimpleLame.close();
                    mIsRecording = false;
                }

                if (mHandler != null) {
                    mHandler.sendEmptyMessage(MSG_REC_STOPPED);
                }
            }
        }.start();
    }

    public void stop() {
        mIsRecording = false;
    }

    public boolean isRecording() {
        return mIsRecording;
    }

    public void setHandle(Handler handler) {
        this.mHandler = handler;
    }
}
