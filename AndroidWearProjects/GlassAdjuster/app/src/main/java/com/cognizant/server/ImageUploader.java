package com.cognizant.server;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.cognizant.utils.Appsettings;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ImageUploader extends AsyncTask<Void, Void, String> {
    int serverResponseCode = 0;
    String image_path;
    Handler event_handler;
    HttpURLConnection conn = null;
    DataOutputStream dos = null;
    String lineEnd = "\r\n";
    String twoHyphens = "--";
    String boundary = "*****";
    int bytesRead, bytesAvailable, bufferSize;
    byte[] buffer;
    int maxBufferSize = 5 * 1024 * 1024;
    // String upLoadServerUri =
    // "http://174.129.3.17:8080/claimadjuster/fileupload.php";
    String upLoadServerUri = "http://104.196.43.212:9080/ClaimAdjustment/uploadProofs";
    String upload_msg;

    public ImageUploader(String file_path, Handler handler) {
        this.event_handler = handler;
        this.image_path = file_path;
    }

    protected String doInBackground(Void... params) {
        // TODO Auto-generated method stub
        File sourceFile = new File(image_path);

        try {

            // open a URL connection to the Servlet
            FileInputStream fileInputStream = new FileInputStream(sourceFile);
            URL url = new URL(upLoadServerUri);

            // Open a HTTP connection to the URL
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true); // Allow Inputs
            conn.setDoOutput(true); // Allow Outputs
            conn.setUseCaches(false); // Don't use a Cached Copy
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("ENCTYPE", "multipart/form-data");
            conn.setRequestProperty("Content-Type",
                    "multipart/form-data;boundary=" + boundary);
            conn.setRequestProperty("thumbnail", image_path);

            dos = new DataOutputStream(conn.getOutputStream());

            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"thumbnail\";filename=\""
                    + image_path + "\"" + lineEnd);

            dos.writeBytes(lineEnd);
            // create a buffer of maximum size
            bytesAvailable = fileInputStream.available();
            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            buffer = new byte[bufferSize];
            // read file and write it into form...
            bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            while (bytesRead > 0) {

                dos.write(buffer, 0, bufferSize);
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);

            }
            // send multipart form data necesssary after file data...
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

            // Responses from the server (code and message)
            serverResponseCode = conn.getResponseCode();
            String serverResponseMessage = conn.getResponseMessage();

            Log.i("uploadFile", "HTTP Response is : " + serverResponseMessage
                    + ":==== " + serverResponseCode);

            if (serverResponseCode == 200) {
                upload_msg = "uploaded successfully.";
            } else {
                upload_msg = "Upload Failed.";
            }

            // close the streams //
            fileInputStream.close();
            dos.flush();
            dos.close();

        } catch (MalformedURLException ex) {

            ex.printStackTrace();
            upload_msg = "Upload Failed.";
        } catch (Exception e) {

            Log.e("Upload file to server Exception","Exception : " + e.getMessage(), e);
            upload_msg = "Upload Failed.";
        }
        return upload_msg;
    }

    @Override
    protected void onPostExecute(String result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);
        Message msgObj = event_handler.obtainMessage();
        Bundle b = new Bundle();
        b.putString("Msg", result);
        msgObj.setData(b);
        event_handler.sendMessage(msgObj);
    }

}
