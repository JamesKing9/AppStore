package com.cheng.appstore.vm.holder;

/**
 * Created by itheima.
 * 详情页，下载容器
 */
public class DetailDownLoadHolder1
//        extends BaseHolder<AppInfo> implements DownloadManager.DownloadTaskObserve
{
//    ProgressBtn btn;
//    AppInfo info;
//
//    public DetailDownLoadHolder1(View itemView) {
//        super(itemView);
//        btn = (ProgressBtn) itemView.findViewById(R.id.app_detail_download_btn_download);
//        DownloadManager.getInstance().addObserve(this);
//    }
//
//    @Override
//    public void setData(final AppInfo data) {
//        info=data;
//        btn.setMax(data.size);
//        init(data);
//
//    }
//
//    /**
//     * 读取当前应用状态
//     *
//     * @param data
//     */
//    public void init(final AppInfo data) {
//        // 读取安装状态
//        boolean installed = AppUtils.isInstalled(UIUtils.getContext(), data.packageName);
//        if (installed) {
//            // 完成，显示打开
//            btn.setText("打开");
//            btn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    AppUtils.openApp(UIUtils.getContext(), data.packageName);
//                }
//            });
//        } else {
//            List<AppEntity> list = AppEntity.find(AppEntity.class, "app_id=" + data.id);
//
//            if (list.size() > 0) {
//                final AppEntity entity = list.get(0);
//                if (entity.downloadSize == entity.size) {
//                    // 完成，显示安装
//                    btn.setText("安装");
//                    btn.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            AppUtils.installApp(UIUtils.getContext(), FileUtils.getApk("apk", data.packageName));
//                        }
//                    });
//                } else {
//                    // 显示继续下载和完成进度
//                    btn.setText("继续下载");
//                    btn.setProgress(entity.downloadSize);
//                    btn.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            entity.appId = data.id;
//                            entity.downloadSize = entity.downloadSize;
//                            entity.downloadUrl = data.downloadUrl;
//                            entity.packageName = data.packageName;
//                            entity.size = data.size;
//                            DownloadManager.getInstance().download(DownloadManager.getInstance().createTask(entity));
//
//                        }
//                    });
//                }
//            } else {
//                // 显示下载
//                btn.setText("下载");
//
//                // 点击事件
//                btn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        AppEntity entity = new AppEntity();
//                        entity.appId = data.id;
//                        entity.downloadSize = 0;
//                        entity.downloadUrl = data.downloadUrl;
//                        entity.packageName = data.packageName;
//                        entity.size = data.size;
//                        DownloadManager.getInstance().download(DownloadManager.getInstance().createTask(entity));
//                    }
//                });
//            }
//        }
//    }
//
//
//    @Override
//    public void update(final DownloadManager.DownloadTask task) {
//        if(info==null){
//            return;
//        }
//        if(!task.info.packageName.equals(info.packageName)){
//            return;
//        }
//
//
//
//        switch (task.info.state) {
//            case DownloadManager.STATE_WAIT:
//                btn.setText("等待");
//                btn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        DownloadManager.getInstance().deleteQueueTask(task);
//                    }
//                });
//                break;
//            case DownloadManager.STATE_LOADING:
//                btn.setProgress(task.info.downloadSize);
//                int index = (int) (task.info.downloadSize * 1.0f / task.info.size * 100 + .5f);
//                btn.setText(index + "%");
//                btn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        btn.setText("继续下载");
//                        btn.setProgress(task.info.downloadSize);
//                        task.isStop = true;
//                    }
//                });
//                break;
//            case DownloadManager.STATE_RETRY:
//                btn.setText("重试");
//                btn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        AppEntity entity = new AppEntity();
//                        entity.appId = task.info.appId;
//                        entity.downloadSize = FileUtils.getApk(task.info.packageName).length();
//                        entity.downloadUrl = task.info.downloadUrl;
//                        entity.packageName = task.info.packageName;
//                        entity.size = task.info.size;
//                        DownloadManager.getInstance().download(DownloadManager.getInstance().createTask(entity));
//                    }
//                });
//                break;
//            case DownloadManager.STATE_INSTALL:
//                btn.setText("安装");
//                btn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        AppUtils.installApp(UIUtils.getContext(), FileUtils.getApk(task.info.packageName));
//                    }
//                });
//                break;
//            case DownloadManager.STATE_STOP:
//                btn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        AppEntity entity = new AppEntity();
//                        entity.appId = task.info.appId;
//                        entity.downloadSize = FileUtils.getApk(task.info.packageName).length();
//                        entity.downloadUrl = task.info.downloadUrl;
//                        entity.packageName = task.info.packageName;
//                        entity.size = task.info.size;
//                        DownloadManager.getInstance().download(DownloadManager.getInstance().createTask(entity));
//                    }
//                });
//                break;
//            case DownloadManager.STATE_UNLOAD:
//                btn.setText("下载");
//
//                btn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        AppEntity entity = new AppEntity();
//                        entity.appId = task.info.appId;
//                        entity.downloadSize = 0;
//                        entity.downloadUrl = task.info.downloadUrl;
//                        entity.packageName = task.info.packageName;
//                        entity.size = task.info.size;
//                        DownloadManager.getInstance().download(DownloadManager.getInstance().createTask(entity));
//                    }
//                });
//                break;
//        }
//    }
}
