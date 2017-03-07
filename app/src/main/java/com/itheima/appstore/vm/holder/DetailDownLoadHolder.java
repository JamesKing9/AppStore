package com.itheima.appstore.vm.holder;

import android.view.View;

import com.itheima.appstore.R;
import com.itheima.appstore.model.net.AppInfo;
import com.itheima.appstore.utils.AppUtils;
import com.itheima.appstore.vm.holder.download.DownloadInfo;
import com.itheima.appstore.vm.holder.download.DownloadTaskObserve;
import com.itheima.appstore.vm.holder.download.DownloadManager;
import com.itheima.appstore.utils.FileUtils;
import com.itheima.appstore.utils.UIUtils;
import com.itheima.appstore.views.ProgressBtn;
import com.itheima.appstore.vm.holder.download.State;

/**
 * Created by itheima.
 * 详情页，下载容器
 */
public class DetailDownLoadHolder extends BaseHolder<AppInfo> implements DownloadTaskObserve, View.OnClickListener {
    ProgressBtn btn;
    private AppInfo data;

    public DetailDownLoadHolder(View itemView) {
        super(itemView);
        btn = (ProgressBtn) itemView.findViewById(R.id.app_detail_download_btn_download);
    }

    @Override
    public void setData(AppInfo data) {
        init(data);
        this.data = data;
        btn.setMax(data.size);
        btn.setOnClickListener(this);
        DownloadManager.getInstance().addObserve(this);
    }

    @Override
    public void onClick(View v) {

        if (data == null) {
            return;
        }
        DownloadInfo downloadInfo = DownloadManager.DOWNLOAD_INFO_HASHMAP.get(data.id);
        if (downloadInfo == null) {
            return;
        }

        switch (downloadInfo.state) {
            case State.INSTALL_ALREADY:
                // 已经安装
                AppUtils.openApp(UIUtils.getContext(), downloadInfo.packageName);
                break;
            case State.DOWNLOAD_COMPLETED:
                // 已经下载完成,但未安装
                AppUtils.installApp(UIUtils.getContext(), FileUtils.getApk("apk", downloadInfo.packageName));
                break;

            case State.DOWNLOAD_STOP:
                // 暂停
            case State.DOWNLOAD_ERROR:
                // 出错，重试
            case State.DOWNLOAD_NOT:
                // 未下载
                DownloadManager.getInstance().download(downloadInfo.appId);
                break;
            case State.DOWNLOAD_WAIT:
                // 线程池已满，应用添加到等待队列，用户点击后取消下载
                DownloadManager.getInstance().deleteQueueTask(downloadInfo.appId);
                break;
            case State.DOWNLOADING:
                // 未出现异常，显示下载进度，点击暂停
                DownloadManager.getInstance().setState(downloadInfo.appId, State.DOWNLOAD_STOP);
                break;

        }
    }

    /**
     * 设置按钮文本信息
     */
    private void setText(DownloadInfo info) {
        String text = "";
        switch (info.state) {
            case State.INSTALL_ALREADY:
                // 已经安装
                btn.setProgress(info.downloadSize);
                text = "打开";
                break;
            case State.DOWNLOAD_COMPLETED:
                // 已经下载完成
                btn.setProgress(info.downloadSize);
                text = "安装";
                break;
            case State.DOWNLOAD_NOT:
                // 未添加到队列中
                text = "下载";
                break;
            case State.DOWNLOAD_WAIT:
                // 线程池已满
                text = "等待";
                break;
            case State.DOWNLOAD_ERROR:
                // 出错，重试
                text = "重试";
                break;
            case State.DOWNLOADING:
                // 未出现异常，显示下载进度
                btn.setProgress(info.downloadSize);
                int index = (int) (info.downloadSize * 1.0f / info.size * 100 + .5f);
                text = index + "%";
                break;
            case State.DOWNLOAD_STOP:
                // 暂停
                btn.setProgress(info.downloadSize);
                text = "继续下载";
                break;
        }
        btn.setText(text);
    }

    /**
     * 读取当前应用状态
     *
     * @param data
     */
    public void init(AppInfo data) {
        DownloadInfo downloadInfo = DownloadManager.DOWNLOAD_INFO_HASHMAP.get(data.id);
        /*AppEntity entity = MyApplication.getAppEntityMap().get(data.id);
        if (entity != null) {
            if (entity.state != State.DOWNLOADING) {
                // 已经有过下载记录
                downloadInfo.downloadSize = entity.downloadSize;
            }
            downloadInfo.state = entity.state;
        }*/
        // 依据状态设置按钮显示
        setText(downloadInfo);
    }


    @Override
    public void update(long id) {
        if (id == getAppId()) {
            final DownloadInfo downLoadInfo = DownloadManager.DOWNLOAD_INFO_HASHMAP.get(id);
            setText(downLoadInfo);
        }
    }

    @Override
    public long getAppId() {
        return data.id;
    }
}
