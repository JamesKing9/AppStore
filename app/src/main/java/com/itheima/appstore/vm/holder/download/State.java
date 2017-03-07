package com.itheima.appstore.vm.holder.download;

/**
 * 应用下载状态
 */
public interface State {
    // 已经安装
    int INSTALL_ALREADY = 1;
    // 未下载
    int DOWNLOAD_NOT = 2;
    // 下载完成
    int DOWNLOAD_COMPLETED = 3;
    // 下载等待
    int DOWNLOAD_WAIT = 4;
    // 下载暂停
    int DOWNLOAD_STOP = 5;
    // 下载中
    int DOWNLOADING = 0;
    // 下载过程中出现异常
    int DOWNLOAD_ERROR = -1;
}
