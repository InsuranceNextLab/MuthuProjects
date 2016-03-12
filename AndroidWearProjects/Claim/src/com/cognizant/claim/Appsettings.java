package com.cognizant.claim;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.os.Environment;
import android.util.Log;

public class Appsettings {

	/************** Local Details *****************/
	public static final String STREAM_URL = "rtsp://10.251.52.21:1935/live/claimadjuster";
	public static final String PUBLISHER_USERNAME = "admin";
	public static final String PUBLISHER_PASSWORD = "admin";

	/**** Live Details **********/

//	public static final String STREAM_URL = "rtsp://174.129.3.17:1935/live/claimadjuster";
//	public static final String PUBLISHER_USERNAME = "insurancenxt";
//	public static final String PUBLISHER_PASSWORD = "welcome123";
	static String path;
	static String finalpath;

	public static String fileCreation(String image_filepath) {
		BufferedOutputStream bw = null;
		BufferedInputStream br = null;
		File localFile;
		String image_filename, temp_path;
		String sdcard_filepath = Environment.getExternalStorageDirectory()
				.getAbsolutePath().toString()
				+ "/" + "ClaimAdjuster";

		File file = new File(sdcard_filepath);
		if (!file.exists()) {
			if (file.mkdir()) {
			}
		}
		int lastIndex = image_filepath.length();
		int idx = image_filepath.lastIndexOf("/");
		image_filename = image_filepath.substring(idx + 1, lastIndex);
		temp_path = sdcard_filepath + "/" + image_filename;
		try {
			localFile = new File(temp_path);
			if (!localFile.exists()) {
				localFile.createNewFile(); // otherwise dropbox client
			} // will fail

			br = new BufferedInputStream(new FileInputStream(image_filepath));
			bw = new BufferedOutputStream(new FileOutputStream(localFile));
			byte[] buf = new byte[1024];
			int read = 0;
			while ((read = br.read(buf, 0, buf.length)) != -1) {
				bw.write(buf, 0, read);
			}
			br.close();
			bw.close();
		} catch (Exception e) {
			// TODO: handle exception
			Log.i("Imageselection_Error", e.getMessage() + "");
			return temp_path;
		}
		return temp_path;
	}

	public static String finalPath() {
		path = Environment.getExternalStorageDirectory().getAbsolutePath()
				.toString();
		String timeStamp = new SimpleDateFormat("yyyMMdd_HHmmss", Locale.UK)
				.format(new Date());
		File direct = new File(path + "/claimadjuster");
		Log.i("tempdirectory", "Initial Folder Created = " + path);
		if (!direct.exists()) {
			if (direct.mkdir()) {
				// directory is created;
				Log.i("tempdirectory", "Folder Created");
				finalpath = path + "/claimadjuster/" + "IMG_" + timeStamp
						+ ".jpg";
			}

		} else {
			finalpath = path + "/claimadjuster/" + "IMG_" + timeStamp + ".jpg";
		}
		Log.i("tempdirectory", "End Folder Created = " + finalpath);
		return finalpath;

	}

	static public boolean deleteDirectory(File path) {

		if (path.exists()) {
			File[] files = path.listFiles();
			if (files == null) {
				return true;
			}
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					deleteDirectory(files[i]);
				} else {
					files[i].delete();
				}
			}
		}
		return (path.delete());
	}
}