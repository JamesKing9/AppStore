package com.cheng.appstore.model.db;

import com.cheng.appstore.utils.FileUtils;
import com.cheng.appstore.vm.holder.download.DownloadInfo;
import com.cheng.appstore.vm.holder.download.DownloadManager;
import com.cheng.appstore.vm.holder.download.State;
import com.orm.SugarRecord;

import java.util.List;

public class AppEntity extends SugarRecord {

    public Long appId;
    public String packageName;
    public long size;
    public long downloadSize;
    public int state;

    public static void saveEntityByAppId(DownloadInfo downloadInfo) {
        AppEntity entity = null;
        //查询数据库，得到结果集
        List<AppEntity> list = find(AppEntity.class, "app_id=" + downloadInfo.appId);
        if (list != null && list.size() > 0) {
            entity = list.get(0);
        } else {
            entity = new AppEntity();
            entity.appId = downloadInfo.appId;
            entity.packageName = downloadInfo.packageName;
            entity.size = downloadInfo.size;
        }
        entity.downloadSize = downloadInfo.downloadSize;
        entity.state = downloadInfo.state;
        entity.save();
    }

    /**
     * 处理安装和卸载
     *
     * @param state
     */
    public static void changeEntityByPackageName(String packageName, int state) {
        List<AppEntity> entities = AppEntity.find(AppEntity.class, "package_name = '" + packageName + "'");
        if (entities != null && entities.size() > 0) {
            AppEntity entity = entities.get(0);
            if (state == State.DOWNLOAD_NOT) {
                entity.delete();
                FileUtils.deleteApk(packageName);
            } else {
                entity.state = state;
                entity.save();
            }

            DownloadInfo downloadInfo = DownloadManager.DOWNLOAD_INFO_HASHMAP.get(entity.appId);
            downloadInfo.state = state;
            DownloadManager.getInstance().notifyObserves(entity.appId);
        }
    }
}
