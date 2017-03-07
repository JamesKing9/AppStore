package com.itheima.appstore.vm.holder.download;

/**
 * 下载数据封装
 */
public class DownloadInfo {
    public Long appId;
    public int state;
    public long downloadSize;
    public long size;
    public String downloadUrl;
    public String packageName;

    @Override
    public String toString() {
        return "DownloadInfo{" +
                "appId=" + appId +
                ", state=" + state +
                ", downloadSize=" + downloadSize +
                '}';
    }
}
