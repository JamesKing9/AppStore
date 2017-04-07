package com.cheng.appstore.vm.holder;

/**
 * Created by itheima.
 * 应用条目
 */
public class AppInfoHolder1
//        extends BaseHolder<AppInfo> implements DownloadTaskObserve, View.OnClickListener
{
//
//    ItemAppinfoBinding binding;
//    private AppEntity entity;
//
//    public AppInfoHolder1(View view) {
//        super(view);
//        binding = DataBindingUtil.bind(view);
//    }
//
//    public void setData(final AppInfo data) {
//        init(data);
//        DownloadManager.getInstance().addObserve(this);
//        binding.setApp(data);
//        // http://localhost:8080/GooglePlayServer/image?name=
//        Glide.with(UIUtils.getContext()).load(getImageUrl(data.iconUrl)).into(binding.itemAppinfoIvIcon);
//
//        itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(UIUtils.getContext(), DetailActivity.class);
//                intent.putExtra("packageName", data.packageName);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                UIUtils.getContext().startActivity(intent);
//            }
//        });
//
//        binding.progress.setOnClickListener(this);
//    }
//
//    /**
//     * 初始化当前应用状态
//     *
//     * @param data
//     */
//    public void init(AppInfo data) {
//        // 读取数据库安装信息
//        entity = MyApplication.getAppEntityMap().get(data.id);
//        if (entity == null) {
//            entity = copyInfo(data);
//            boolean installed = AppUtils.isInstalled(UIUtils.getContext(), data.packageName);
//            entity.state = installed ? State.INSTALL_ALREADY : State.DOWNLOAD_NOT;
//            if (installed) {
//                entity.saveEntityByAppId(entity.state);
//                MyApplication.getAppEntityMap().put(entity.appId, this.entity);
//            }
//        }
//        // 依据状态设置按钮显示
//        setText();
//    }
//
//
//    @Override
//    public void onClick(View v) {
//        switch (entity.state) {
//            case State.INSTALL_ALREADY:
//                // 已经安装
//                AppUtils.openApp(UIUtils.getContext(), entity.packageName);
//                break;
//            case State.DOWNLOAD_COMPLETED:
//                // 已经下载完成,但未安装
//                AppUtils.installApp(UIUtils.getContext(), FileUtils.getApk("apk", entity.packageName));
//                break;
//            case State.QUEUE_NOT:
//                // 未添加到队列中
//            case State.DOWNLOAD_STOP:
//                // 暂停
//            case State.DOWNLOAD_ERROR:
//                // 出错，重试
//            case State.DOWNLOAD_NOT:
//                // 未下载
//                DownloadManager.getInstance().download(new DownloadTask(entity));
//                break;
//            case State.DOWNLOAD_WAIT:
//                // 线程池已满，应用添加到等待队列，用户点击后取消下载
//                DownloadManager.getInstance().deleteQueueTask(entity.appId);
//                break;
//            case State.DOWNLOADING:
//                // 未出现异常，显示下载进度，点击暂停
//                DownloadManager.getInstance().setState(entity.appId, State.DOWNLOAD_STOP);
//                break;
//
//        }
//    }
//
//    private AppEntity copyInfo(AppInfo data) {
//        AppEntity entity = new AppEntity();
//        entity.appId = data.id;
//        entity.downloadSize = 0;
//        entity.downloadUrl = data.downloadUrl;
//        entity.packageName = data.packageName;
//        entity.name = data.name;
//        entity.size = data.size;
//        return entity;
//    }
//
//    /**
//     * 设置按钮文本信息
//     */
//    private void setText() {
//        String text = "";
//        switch (entity.state) {
//            case State.INSTALL_ALREADY:
//                // 已经安装
//                binding.progress.setPercent(1);
//                text = "打开";
//                binding.progress.setText(text);
//                break;
//            case State.DOWNLOAD_COMPLETED:
//                // 已经下载完成
//                binding.progress.setPercent(1);
//                text = "安装";
//                binding.progress.setText(text);
//                break;
//            case State.QUEUE_NOT:
//            case State.DOWNLOAD_NOT:
//                // 未添加到队列中
//                binding.progress.setPercent(0);
//                text = "下载";
//                binding.progress.setText(text);
//                break;
//            case State.DOWNLOAD_WAIT:
//                // 线程池已满
//                text = "等待";
//                binding.progress.setText(text);
//                break;
//            case State.DOWNLOAD_ERROR:
//                // 出错，重试
//                text = "重试";
//                binding.progress.setText(text);
//                break;
//            case State.DOWNLOADING:
//                // 未出现异常，显示下载进度
//                binding.progress.setPercent(entity.downloadSize * 1.0f / entity.size);
//                break;
//            case State.DOWNLOAD_STOP:
//                // 暂停
//                binding.progress.setPercent(entity.downloadSize * 1.0f / entity.size);
//                text = "继续";
//                binding.progress.setText(text);
//                break;
//        }
//
//    }
//
//    @Override
//    public void update(int state, long downloadSize) {
//        entity.state = state;
//        entity.downloadSize = downloadSize;
//        setText();
//        LogUtils.s("update:" + entity.name + "::state::" + entity.state);
//    }
//
//    @Override
//    public long getAppId() {
//        return entity.appId;
//    }
}
