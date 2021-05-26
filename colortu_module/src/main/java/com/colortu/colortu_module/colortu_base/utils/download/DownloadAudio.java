package com.colortu.colortu_module.colortu_base.utils.download;

import android.annotation.SuppressLint;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author : Code23
 * @time : 2020/11/5
 * @module : DownloadAudio
 * @describe :下载音频
 */
public class DownloadAudio {
    private File file;
    //储存下载文件的目录
    public String savePath;
    private List<String> audiolist = new ArrayList<>();
    private List<String> audioNamelist = new ArrayList<>();
    public DownloadAudioListener downloadAudioListener;

    public DownloadAudio(DownloadAudioListener downloadAudioListener, String savePath, List<String> audiolist) {
        this.downloadAudioListener = downloadAudioListener;
        this.savePath = savePath;
        this.audiolist = audiolist;
        audioNamelist.clear();
        audioNamelist = getFilesAllName(savePath);
    }

    public void isDownloadAudio(final String url, final int i) {
        if (audioNamelist != null) {
            if (audioNamelist.size() > 0) {
                for (int t = 0; t < audioNamelist.size(); t++) {
                    if (audiolist.get(i).equals(audioNamelist.get(t))) {
                        downloadAudioListener.DownloadSuccess(savePath + "/" + audioNamelist.get(i), i);
                        break;
                    } else {
                        if (t == audiolist.size() - 1) {
                            OkHttpDownloadAudio(url, i);
                        }
                    }
                }
            } else {
                OkHttpDownloadAudio(url, i);
            }
        } else {
            OkHttpDownloadAudio(url, i);
        }
    }

    /**
     * @author : Code23
     * @time : 2020/11/5
     * @name : downloadFile
     * @Parameters : [url, name, dowloadpath]
     * @describe :
     */
    @SuppressLint("NewApi")
    public void OkHttpDownloadAudio(final String url, final int i) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //请求失败
                downloadAudioListener.DownloadFailure("failure", i);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //请求成功
                InputStream inputStream = null;
                FileOutputStream fileOutputStream = null;

                byte[] bytes = new byte[1024 * 10];
                int length = 0;
                file = new File(savePath);
                //文件夹不存在
                if (!file.exists()) {
                    // 创建文件夹
                    file.mkdirs();
                }
                try {
                    inputStream = response.body().byteStream();
                    file = new File(savePath, audiolist.get(i));
                    fileOutputStream = new FileOutputStream(file);
                    while ((length = inputStream.read(bytes)) != -1) {
                        fileOutputStream.write(bytes, 0, length);
                    }
                    fileOutputStream.flush();
                    //下载成功
                    downloadAudioListener.DownloadSuccess(file.getPath(), i);
                } catch (Exception e) {
                    e.printStackTrace();
                    // 下载失败
                    downloadAudioListener.DownloadFailure("failure", i);
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

    public interface DownloadAudioListener {
        void DownloadSuccess(String audiourl, int i);

        void DownloadFailure(String audiourl, int i);
    }

    /**
     * 获取文件夹所有文件名
     *
     * @param path
     * @return
     */
    public List<String> getFilesAllName(String path) {
        File file = new File(path);
        //文件夹不存在
        if (!file.exists()) {
            // 创建文件夹
            file.mkdirs();
        }
        File[] files = file.listFiles();
        //空目录
        if (files == null) {
            return null;
        }
        List<String> stringList = new ArrayList<>();
        for (int i = 0; i < files.length; i++) {
            stringList.add(files[i].getName());
        }
        return stringList;
    }
}