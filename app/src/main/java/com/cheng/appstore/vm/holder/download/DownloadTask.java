package com.cheng.appstore.vm.holder.download;

import android.support.annotation.NonNull;

import com.cheng.appstore.Constants;
import com.cheng.appstore.model.db.AppEntity;
import com.cheng.appstore.utils.FileUtils;
import com.cheng.appstore.utils.HttpUtils;
import com.cheng.appstore.utils.IOUtils;
import com.cheng.appstore.utils.ThreadPoolUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 下载任务
 */
public class DownloadTask extends ThreadPoolUtils.Task {

    private final DownloadInfo downloadInfo;

    public DownloadTask(Long id) {
        this.id = id;
        downloadInfo = DownloadManager.DOWNLOAD_INFO_HASHMAP.get(id);
    }

    @Override
    protected void work() {
        downloadApk();
    }

    private void downloadApk() {
        InputStream in = null;
        FileOutputStream out = null;

        try {
            //真正开始在子线中中下载apk
            OkHttpClient okHttpClient = new OkHttpClient();
            String url = getUrl();
            Request request = new Request.Builder().get().url(url).build();

            Response response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                in = response.body().byteStream();
                // 下载到Sdcard的/data/android/包名/apk/packageName.apk
                File saveApk = FileUtils.getApk(downloadInfo.packageName);
                out = new FileOutputStream(saveApk, true);//②,写文件的时候,以追加的方式去写

                int len = 0;
                byte[] buffer = new byte[1024];

                setState(State.DOWNLOADING);
                while (true) {
                    if (downloadInfo.state == State.DOWNLOAD_STOP) {
                        AppEntity.saveEntityByAppId(downloadInfo);
                        break;
                    }
                    len = in.read(buffer);
                    out.write(buffer, 0, len);
                    // 显示进度
                    downloadInfo.downloadSize += len;

                    DownloadManager.getInstance().notifyObserves(downloadInfo.appId);

                    if (downloadInfo.downloadSize == downloadInfo.size) {
                        // 显示安装
                        setState(State.DOWNLOAD_COMPLETED);
                        break;
                    }
                }
            } else {
                // 显示重试
                setState(State.DOWNLOAD_ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // 显示重试
            setState(State.DOWNLOAD_ERROR);
        } finally {
            IOUtils.close(out);
            IOUtils.close(in);
        }


    }

    /**
     * 获取url地址
     * @return
     */
    @NonNull
    private String getUrl() {
        //http://localhost:8080/GooglePlayServer/download?name=app/com.itheima.www/com.itheima.www.apk&range=0
        StringBuffer urlBuffer = new StringBuffer(Constants.Http.HOST);
        urlBuffer.append(Constants.Http.DOWNLOAD);

        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("name", downloadInfo.downloadUrl);
        paramsMap.put("range", downloadInfo.downloadSize > 0 ? downloadInfo.downloadSize : 0);
        //转换参数为字符串
        String urlParamsByMap = HttpUtils.getUrlParamsByMap(paramsMap);
        urlBuffer.append(urlParamsByMap);
        return urlBuffer.toString();
    }

    public void setState(int state) {
        downloadInfo.state = state;
        DownloadManager.getInstance().notifyObserves(downloadInfo.appId);

        // 数据库相关
        AppEntity.saveEntityByAppId(downloadInfo);
    }
}