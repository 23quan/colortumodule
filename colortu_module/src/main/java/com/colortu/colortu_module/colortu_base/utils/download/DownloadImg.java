package com.colortu.colortu_module.colortu_base.utils.download;

import android.annotation.SuppressLint;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author : Code23
 * @time : 2020/11/5
 * @module : DownloadAudio
 * @describe :
 */
public class DownloadImg {
    //下载文件
    private File file;
    //储存下载文件的目录
    private String savePath;
    public DownloadImgListener downloadImgListener;

    public DownloadImg(DownloadImgListener downloadImgListener, String savePath) {
        this.downloadImgListener = downloadImgListener;
        this.savePath = savePath;
    }

    /**
     * 下载图片
     *
     * @param url
     * @param name
     */
    @SuppressLint("NewApi")
    public void dowloadRequest(final String url, final String name) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //请求失败
                downloadImgListener.DownloadFailure();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                file = new File(savePath);
                deleteLocal(file);
                //文件夹不存在
                if (!file.exists()) {
                    // 创建文件夹
                    file.mkdirs();
                }

                //请求成功
                InputStream inputStream = null;
                FileOutputStream fileOutputStream = null;
                byte[] bytes = new byte[1024 * 10];
                int length = 0;
                try {
                    inputStream = response.body().byteStream();
                    file = new File(savePath, name);
                    fileOutputStream = new FileOutputStream(file);
                    while ((length = inputStream.read(bytes)) != -1) {
                        fileOutputStream.write(bytes, 0, length);
                    }
                    fileOutputStream.flush();
                    //下载成功
                    downloadImgListener.DownloadSuccess(file.getPath());
                } catch (Exception e) {
                    e.printStackTrace();
                    // 下载失败
                    downloadImgListener.DownloadFailure();
                } finally {
                    if (inputStream != null) {
                        inputStream.close();
                    }

                    if (fileOutputStream != null) {
                        fileOutputStream.close();
                    }
                }
            }
        });
    }

    public interface DownloadImgListener {
        void DownloadSuccess(String audiourl);

        void DownloadFailure();
    }

    /**
     * 删除本地文件
     */
    public void deleteLocal(File file) {
        if (file.exists()) {
            if (file.isFile()) {
                //如果为文件，直接删除
                file.delete();
            } else if (file.isDirectory()) {
                File[] files = file.listFiles();
                if (files != null) {
                    for (File file1 : files) {
                        //如果为文件夹，递归调用
                        deleteLocal(file1);
                    }
                }
            }
            file.delete();
        }
    }
}