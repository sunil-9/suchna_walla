package com.example.suchnawalla.helper;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class FileDownloader {
    private static final String TAG = "PdfDownloader";

    public interface DownloadListener {
        void onDownloadComplete(File pdfFile);
        void onDownloadFailed(String errorMessage);
    }
    public static void downloadPdf(Context context, String pdfUrl,String fileName, DownloadListener listener) {
        new DownloadFileTask(context, pdfUrl,fileName, listener).execute();
    }
    private static class DownloadFileTask extends AsyncTask<Void, Void, File> {

        private Context context;
        private String fileUrl,fileName;
        private DownloadListener listener;

        public DownloadFileTask(Context context, String fileUrl, String fileName, DownloadListener listener) {
            this.context = context;
            this.fileUrl = fileUrl;
            this.listener = listener;
            this.fileName = fileName;
        }


        @Override
        protected File doInBackground(Void... voids) {
            try {
                URL url = new URL(fileUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();

                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {

                    File directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                    File pdfFile = new File(directory, fileName);

                    FileOutputStream outputStream = new FileOutputStream(pdfFile);
                    InputStream inputStream = connection.getInputStream();
                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                    outputStream.close();
                    inputStream.close();

                    return pdfFile;
                } else {
                    return null;
                }
            } catch (IOException e) {
                Log.e(TAG, "Error downloading PDF: " + e.getMessage());
                return null;
            }
        }

        @Override
        protected void onPostExecute(File pdfFile) {
            if (pdfFile != null) {
                listener.onDownloadComplete(pdfFile);
            } else {
                listener.onDownloadFailed("PDF download failed");
            }
        }
    }
}
