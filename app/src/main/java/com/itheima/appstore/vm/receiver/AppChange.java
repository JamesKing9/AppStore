package com.itheima.appstore.vm.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.itheima.appstore.model.db.AppEntity;
import com.itheima.appstore.utils.FileUtils;
import com.itheima.appstore.utils.LogUtils;
import com.itheima.appstore.vm.holder.download.DownloadInfo;
import com.itheima.appstore.vm.holder.download.DownloadManager;
import com.itheima.appstore.vm.holder.download.State;

import java.util.List;

/**
 * 监听应用的安装和卸载
 */
public class AppChange extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //接收安装广播
        if (intent.getAction().equals("android.intent.action.PACKAGE_ADDED")) {
            String packageName = intent.getDataString();
            LogUtils.s("安装了:" + packageName + "包名的程序");
            updateAppInfo(packageName, State.INSTALL_ALREADY);

        }
        //接收卸载广播
        if (intent.getAction().equals("android.intent.action.PACKAGE_REMOVED")) {
            String packageName = intent.getDataString();
            LogUtils.s("卸载了:" + packageName + "包名的程序");
            updateAppInfo(packageName, State.DOWNLOAD_NOT);

        }
    }

    private void updateAppInfo(String packageName, int state) {
        int i = packageName.indexOf(":");
        if (i > 0) {
            packageName = packageName.substring(i + 1);
        }
        AppEntity.changeEntityByPackageName(packageName, state);
    }
}
