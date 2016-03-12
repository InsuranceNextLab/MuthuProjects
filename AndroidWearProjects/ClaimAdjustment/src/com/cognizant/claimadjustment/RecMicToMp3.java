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

package com.cognizant.claimadjustment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidParameterException;

import com.cognizant.utils.Appsettings;
import com.cognizant.utils.WaveformView;
import com.uraroji.garage.android.lame.SimpleLame;

import android.content.Context;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Handler;
import android.util.Log;

/**
 * ƒ}ƒCƒN‚©‚çŽæ“¾‚µ‚½‰¹?º‚ðMP3‚É•Û‘¶‚·‚é
 * 
 * •ÊƒXƒŒƒbƒh‚Åƒ}ƒCƒN‚©‚ç‚Ì˜^‰¹?AMP3‚Ö‚Ì•ÏŠ·‚ð?s‚¤
 */
public class RecMicToMp3 {

	static {
		System.loadLibrary("mp3lame");
	}

	/**
	 * MP3ƒtƒ@ƒCƒ‹‚ð•Û‘¶‚·‚éƒtƒ@ƒCƒ‹ƒpƒX
	 */
	private String mFilePath;
	private short[] mAudioBuffer;

	/**
	 * ƒTƒ“ƒvƒŠƒ“ƒOƒŒ?[ƒg
	 */
	private int mSampleRate;

	/**
	 * ˜^‰¹’†‚©
	 */
	private boolean mIsRecording = false;
	WaveformView wfprm;

	private Handler mHandler;

	public static final int MSG_REC_STARTED = 0;

	public static final int MSG_REC_STOPPED = 1;

	public static final int MSG_ERROR_GET_MIN_BUFFERSIZE = 2;

	public static final int MSG_ERROR_CREATE_FILE = 3;

	public static final int MSG_ERROR_REC_START = 4;

	public static final int MSG_ERROR_AUDIO_RECORD = 5;

	public static final int MSG_ERROR_AUDIO_ENCODE = 6;

	public static final int MSG_ERROR_WRITE_FILE = 7;

	public static final int MSG_ERROR_CLOSE_FILE = 8;

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
						// ˜^‰¹‚ÌŠJŽn‚ÉŽ¸”s‚µ‚½
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
							Log.i("StreamingBuffer", buffer + "");
							if (readSize < 0) {
								// ˜^‰¹‚ª‚Å‚«‚È‚¢
								if (mHandler != null) {
									mHandler.sendEmptyMessage(MSG_ERROR_AUDIO_RECORD);
								}
								break;
							}
							// ƒf?[ƒ^‚ª“Ç‚Ý?ž‚ß‚È‚©‚Á‚½?ê?‡‚Í‰½‚à‚µ‚È‚¢
							else if (readSize == 0) {
								;
							}
							// ƒf?[ƒ^‚ª“ü‚Á‚Ä‚¢‚é?ê?‡
							else {
								int encResult = SimpleLame.encode(buffer,
										buffer, readSize, mp3buffer);
								if (encResult < 0) {
									// ƒGƒ“ƒR?[ƒh‚ÉŽ¸”s‚µ‚½
									if (mHandler != null) {
										mHandler.sendEmptyMessage(MSG_ERROR_AUDIO_ENCODE);
									}
									break;
								}
								if (encResult != 0) {
									try {
										output.write(mp3buffer, 0, encResult);
									} catch (IOException e) {
										// ƒtƒ@ƒCƒ‹‚Ì?‘‚«?o‚µ‚ÉŽ¸”s‚µ‚½
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
							// ƒGƒ“ƒR?[ƒh‚ÉŽ¸”s‚µ‚½
							if (mHandler != null) {
								mHandler.sendEmptyMessage(MSG_ERROR_AUDIO_ENCODE);
							}
						}
						if (flushResult != 0) {
							try {
								output.write(mp3buffer, 0, flushResult);
							} catch (IOException e) {
								// ƒtƒ@ƒCƒ‹‚Ì?‘‚«?o‚µ‚ÉŽ¸”s‚µ‚½
								if (mHandler != null) {
									mHandler.sendEmptyMessage(MSG_ERROR_WRITE_FILE);
								}
							}
						}

						try {
							output.close();
						} catch (IOException e) {
							// ƒtƒ@ƒCƒ‹‚ÌƒNƒ??[ƒY‚ÉŽ¸”s‚µ‚½
							if (mHandler != null) {
								mHandler.sendEmptyMessage(MSG_ERROR_CLOSE_FILE);
							}
						}
					} finally {
						audioRecord.stop(); // ˜^‰¹‚ð’âŽ~‚·‚é
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
