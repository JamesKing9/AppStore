package com.itheima.appstore.vm.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.itheima.appstore.Constants;
import com.itheima.appstore.MyApplication;
import com.itheima.appstore.R;
import com.itheima.appstore.model.net.AppInfo;
import com.itheima.appstore.utils.HttpUtils;
import com.itheima.appstore.vm.BaseCallBack;
import com.itheima.appstore.vm.CommonCacheProcess;
import com.itheima.appstore.vm.CommonPager;
import com.itheima.appstore.vm.holder.BaseHolder;
import com.itheima.appstore.vm.holder.DetailDesHolder;
import com.itheima.appstore.vm.holder.DetailDownLoadHolder;
import com.itheima.appstore.vm.holder.DetailInfoHolder;
import com.itheima.appstore.vm.holder.DetailPicHolder;
import com.itheima.appstore.vm.holder.DetailSafeHolder;

import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Request;

public class DetailActivity extends AppCompatActivity {

    private CommonPager pager;
    private Toolbar toolbar;
    private String key;
    private AppInfo appInfo;
    private String packageName;
    private DetailDownLoadHolder downLoadHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        packageName = getIntent().getStringExtra("packageName");

        pager=new CommonPager() {
            @Override
            public void showSuccess() {
                DetailActivity.this.showSuccess();
            }

            @Override
            public void loadData() {
                DetailActivity.this.loadData();
            }
        };
        setContentView(pager.commonContainer);

    }

    @Override
    protected void onResume() {
        super.onResume();
        pager.dynamic();
        if(downLoadHolder!=null){
            downLoadHolder.init(appInfo);
        }
    }

    private void loadData() {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("packageName", packageName);

        key = Constants.Http.DETAIL + "."+packageName;
        String json = CommonCacheProcess.getLocalJson(key);
        if (json != null) {
            parserJson(json);
            pager.runOnUiThread();
        } else {
            loadNetData(params);
        }
    }


    private void loadNetData(HashMap<String, Object> params) {

        Request request = HttpUtils.getRequest(Constants.Http.DETAIL, params);

        Call call = HttpUtils.getClient().newCall(request);
        // call.download():同步
        // 异步
        call.enqueue(new BaseCallBack(pager) {
            @Override
            protected void onSuccess(String json) {
                // 需要在内存存一份文件
                json=json.replaceAll("\n","");
                MyApplication.getProtocolCache().put(key, json);
                CommonCacheProcess.cacheToFile(key, json);
                parserJson(json);
            }
        });
    }

    private void parserJson(String json) {
        pager.isReadData = true;
        appInfo = MyApplication.getGson().fromJson(json, AppInfo.class);
        if (appInfo != null) {
            pager.isNullData = false;
        } else {
            pager.isNullData = true;
        }
    }



    private void showSuccess() {
        View view =View.inflate(this,R.layout.activity_detail,null);

        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);// 添加返回键
//        toolbar.setTitle(appInfo.name);


        // 应用详情部分容器
        BaseHolder holder=new DetailInfoHolder(view.findViewById(R.id.item_detail_info));
        holder.setData(appInfo);
        // 应用的安全部分的容器
        holder=new DetailSafeHolder(view.findViewById(R.id.item_detail_safe));
        holder.setData(appInfo.safe);
        // 应用的截图部分的容器
        holder=new DetailPicHolder(view.findViewById(R.id.item_detail_pic));
        holder.setData(appInfo.screen);
        // 应用的描述部分的容器
        holder = new DetailDesHolder(view.findViewById(R.id.item_detail_des));
        holder.setData(appInfo);

        // 下载容器
        downLoadHolder = new DetailDownLoadHolder(view.findViewById(R.id.item_detail_download));
        downLoadHolder.setData(appInfo);

        pager.changeViewTo(view);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
