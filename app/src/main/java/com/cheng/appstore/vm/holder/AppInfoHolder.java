package com.cheng.appstore.vm.holder;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.text.TextUtils;
import android.view.View;

import com.bumptech.glide.Glide;
import com.cheng.appstore.databinding.ItemAppinfoBinding;
import com.cheng.appstore.model.db.AppEntity;
import com.cheng.appstore.model.net.AppInfo;
import com.cheng.appstore.utils.AppUtils;
import com.cheng.appstore.utils.LogUtils;
import com.cheng.appstore.utils.UIUtils;
import com.cheng.appstore.vm.activity.DetailActivity;
import com.cheng.appstore.vm.holder.download.DownloadInfo;
import com.cheng.appstore.vm.holder.download.DownloadTaskObserve;
import com.cheng.appstore.vm.holder.download.DownloadManager;
import com.cheng.appstore.vm.holder.download.State;

import java.util.List;

/**
 * Created by itheima.
 * 应用条目
 */
public class AppInfoHolder extends BaseHolder<AppInfo> implements DownloadTaskObserve, View.OnClickListener {
    ItemAppinfoBinding binding;

    public AppInfoHolder(View view) {
        super(view);
        binding = DataBindingUtil.bind(view);
        DownloadManager.getInstance().addObserve(this);
    }

    public void setData(AppInfo data) {
        binding.setApp(data);

        // http://localhost:8080/GooglePlayServer/image?name=
        Glide.with(UIUtils.getContext()).load(getImageUrl(data.iconUrl)).into(binding.itemAppinfoIvIcon);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UIUtils.getContext(), DetailActivity.class);
                intent.putExtra("packageName", binding.getApp().packageName);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                UIUtils.getContext().startActivity(intent);
            }
        });


        DownloadInfo downLoadInfo = DownloadManager.DOWNLOAD_INFO_HASHMAP.get(data.id);
        if (downLoadInfo == null) {
            downLoadInfo = copyInfo(data);
            DownloadManager.DOWNLOAD_INFO_HASHMAP.put(downLoadInfo.appId, downLoadInfo);
            // 第一次展示,初始化处理,加载本地数据，用于显示用户以前的操作
            init(downLoadInfo);
        } else {
            LogUtils.e(downLoadInfo.toString());
            // 第二次展示及以后，用于更新
            changeUI(downLoadInfo);
        }


        binding.progress.setOnClickListener(this);
    }

    /**
     * 初始化当前应用状态
     * @param downloadInfo
     */
    public void init(DownloadInfo downloadInfo) {
        // 检查是否存在下载记录
        AppEntity entity = null;
        List<AppEntity> list = AppEntity.find(AppEntity.class, "app_id=" + downloadInfo.appId);
        if (list != null && list.size() > 0) {
            entity = list.get(0);
        }

        if (entity == null) {
            // 判读是否安装
            boolean installed = AppUtils.isInstalled(UIUtils.getContext(), downloadInfo.packageName);
            downloadInfo.state = installed ? State.INSTALL_ALREADY : State.DOWNLOAD_NOT;
            if (installed) {
                downloadInfo.downloadSize = downloadInfo.size;
                AppEntity.saveEntityByAppId(downloadInfo);
            }
        } else {
            downloadInfo.state = entity.state;
            downloadInfo.downloadSize = entity.downloadSize;
        }
        // 依据状态设置按钮显示
        changeUI(downloadInfo);
    }


    @Override
    public void onClick(View v) {
        DownloadManager.getInstance().handleClick(getAppId());
    }

    private DownloadInfo copyInfo(AppInfo data) {
        DownloadInfo info = new DownloadInfo();
        info.appId = data.id;
        info.downloadSize = 0;
        info.downloadUrl = data.downloadUrl;
        info.packageName = data.packageName;
        info.size = data.size;
        info.state = State.DOWNLOAD_NOT;
        return info;
    }

    /**
     * 设置按钮文本信息
     *
     * @param downLoadInfo
     */
    private void changeUI(DownloadInfo downLoadInfo) {
        String text = "";
        long downloadSize = 0;
//        binding.progress.setPercent(downLoadInfo.downloadSize * 1.0f / downLoadInfo.size);
        switch (downLoadInfo.state) {
            case State.INSTALL_ALREADY:
                // 已经安装
                downloadSize = downLoadInfo.size;
                text = "打开";
                break;
            case State.DOWNLOAD_COMPLETED:
                // 已经下载完成
                downloadSize = downLoadInfo.size;
                text = "安装";
                break;
            case State.DOWNLOAD_NOT:
                // 未添加到队列中
                downloadSize = 0;
                text = "下载";
                break;
            case State.DOWNLOAD_WAIT:
                // 线程池已满
                downloadSize = downLoadInfo.downloadSize;
                text = "等待";
                break;
            case State.DOWNLOAD_ERROR:
                // 出错，重试
                downloadSize = downLoadInfo.downloadSize;
                text = "重试";
                break;
            case State.DOWNLOADING:
                // 下载中
                downloadSize = downLoadInfo.downloadSize;
                break;
            case State.DOWNLOAD_STOP:
                // 暂停
                downloadSize = downLoadInfo.downloadSize;
                text = "继续";
                break;
        }

        binding.progress.setPercent(downloadSize * 1.0f / downLoadInfo.size);
        if (!TextUtils.isEmpty(text))
            binding.progress.setText(text);
    }

    @Override
    public void update(long id) {
        if (id == getAppId()) {
            final DownloadInfo downLoadInfo = DownloadManager.DOWNLOAD_INFO_HASHMAP.get(id);
            LogUtils.w(downLoadInfo.toString());
            changeUI(downLoadInfo);
        }
    }

    @Override
    public long getAppId() {
        return binding.getApp().id;
    }
}
