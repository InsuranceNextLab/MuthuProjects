package com.cognizant.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Appsettings {

   /* public static final String STREAM_URL = "rtsp://174.129.3.17:1935/live/claimadjuster";
    public static final String PUBLISHER_USERNAME = "insurancenxt";
    public static final String PUBLISHER_PASSWORD = "welcome123";*/
    public static final String STREAM_URL = "rtsp://10.251.52.21:1935/live/claimadjuster";
    public static final String PUBLISHER_USERNAME = "admin";
    public static final String PUBLISHER_PASSWORD = "admin";
    public static final int LONG = 2;
    public static final int NORMAL = 1;
    public static final int SHORT = 0;
    public static final String PORT = "8080";
    public static final String IP_ADDRESS = "104.196.43.212:9080";
    public static final String REGISTER_URL = "http://104.196.43.212:9080/ClaimAdjustment/addClaimDetails/";//http://174.129.3.17:8080/claimadjuster/ClaimRequest.php";
    public static final String ClaimList_URL = "http://104.196.43.212/HomeSurveyorServices/services/GetGlassClaimList";
    public static final int TIMEOUT_CON_SHORT = 300000;
    public static final int TIMEOUT_SOCKET_SHORT = 300000;
    public static final int TIMEOUT_CON_NORMAL = 500000;
    public static final int TIMEOUT_SOCKET_NORMAL = 500000;
    public static final int TIMEOUT_CON_LONG = 700000;
    public static final int TIMEOUT_SOCKET_LONG = 700000;
    /**
     * * Live Details *********
     */
    private static final String PREF_NAME = "ClaimAdjustment";
    static String path;
    static String finalpath;
    private static String sdcard_path = Environment.getExternalStorageDirectory()
            .getAbsolutePath();

    public static String fileCreation(String image_filepath, Context context) {
        BufferedOutputStream bw;
        BufferedInputStream br;
        File localFile;
        String image_filename, temp_path;
        String sdcard_filepath;
        sdcard_filepath = sdcard_path + "/" + "ClaimAdjuster";

        File file = new File(sdcard_filepath);
        if (!file.exists()) {
            if (file.mkdir()) {
            }
        }
        int lastIndex = image_filepath.length();
        int idx = image_filepath.lastIndexOf("/");
        image_filename = image_filepath.substring(idx + 1, lastIndex);
        temp_path = sdcard_filepath + "/"
                + Appsettings.getString(context, "Claim_number") + ".MP4";
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

    public static String finalPath(Context context) {

        path = Environment.getExternalStorageDirectory().getAbsolutePath()
                .toString();
        String timeStamp = new SimpleDateFormat("yyyMMdd_HHmmss", Locale.UK)
                .format(new Date());
        File direct = new File(sdcard_path + "/claimadjuster");
        Log.i("tempdirectory", "Initial Folder Created = " + path);
        if (direct.exists()) {
            finalpath = path + "/claimadjuster/"
                    + Appsettings.getString(context, "Claim_number") + ".jpg";
        } else if (direct.mkdir()) {
            // directory is created;
            Log.i("tempdirectory", "Folder Created");
            finalpath = path + "/claimadjuster/"
                    + Appsettings.getString(context, "Claim_number")
                    + ".jpg";
        }
        Log.i("tempdirectory", "End Folder Created = " + finalpath);
        return finalpath;

    }

    public static String getAudioPath(Context context) {

        File direct = new File(sdcard_path + "/claimadjuster");
        Log.i("tempdirectory", "Initial Folder Created = " + sdcard_path);
        if (!direct.exists()) if (direct.mkdir()) {
            // directory is created;
            Log.i("tempdirectory", "Folder Created");
            finalpath = path + "/claimadjuster/"
                    + Appsettings.getString(context, "Claim_number")
                    + ".MP3";
        } else {
            finalpath = path + "/claimadjuster/"
                    + Appsettings.getString(context, "Claim_number") + ".MP3";
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
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteDirectory(file);
                } else {
                    file.delete();
                }
            }
        }
        return (path.delete());
    }

    public static boolean isCheckConnectivity(Context con) {
        ConnectivityManager conMgr = (ConnectivityManager) con
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conMgr.getActiveNetworkInfo() != null) if (conMgr.getActiveNetworkInfo().isAvailable())
            if (conMgr.getActiveNetworkInfo().isConnected()) return true;
        return false;

    }

    public static String getString(Context context, String tag) {
        SharedPreferences pref = context.getSharedPreferences(PREF_NAME, 0);

        return pref.getString(tag, "");
    }

    public static void putString(Context context, String tag, String value) {
        SharedPreferences pref = context.getSharedPreferences(PREF_NAME, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(tag, value);
        editor.commit();
    }

    public static boolean checkForObjectInSharedPrefs(final Context context,
                                                      final String key) {
        return PreferenceManager.getDefaultSharedPreferences(context).contains(
                key);
    }

    public static void commitNewMemoList(final Context context,
                                         final String key, final List<String> memoList) {
        SharedPreferences.Editor sharedPrefEditor = PreferenceManager
                .getDefaultSharedPreferences(context).edit();

        if (!memoList.isEmpty()) {
            sharedPrefEditor.putString(key, new JSONArray(memoList).toString());
        } else {
            sharedPrefEditor.putString(key, null);
        }

        sharedPrefEditor.commit();
    }


    public static ArrayList<String> getStringArrayPref(final Context context,
                                                       String key) {

        SharedPreferences sharedPref = PreferenceManager
                .getDefaultSharedPreferences(context);
        String json = sharedPref.getString(key, null);
        ArrayList<String> memoList = new ArrayList<String>();

        if (json != null) {
            try {
                JSONArray a = new JSONArray(json);

                for (int i = 0; i < a.length(); i++) {
                    memoList.add(a.optString(i));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return memoList;
    }


    public static void deleteMemoAtIndex(int index, Context context, String key) {
        ArrayList<String> memoList = new ArrayList<String>(getStringArrayPref(
                context, key));
        memoList.remove(index);
        commitNewMemoList(context, key, memoList);
    }

}
