package com.itheima.appstore.vm.activity;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.itheima.appstore.R;
import com.itheima.appstore.utils.PermissionUtils;
import com.itheima.appstore.utils.UIUtils;
import com.itheima.appstore.vm.fragment.FragmentFactory;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        PermissionUtils.checkPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // 注意：需要在setSupportActionBar调用之前设置好Toolbar的配置


        // 使用toolbar代替actionbar
        setSupportActionBar(toolbar);

        // 初始化DrawerLayout
        // 将Toolbar与DrawerLayout整合

        drawerLayout = (DrawerLayout) findViewById(R.id.main_drawerLayout);
        ActionBarDrawerToggle barDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        barDrawerToggle.syncState();
        // 如果想看到菜单滑动过程中，Toolbar的一些细节变化（菜单按钮）
        drawerLayout.addDrawerListener(barDrawerToggle);

        // 处理菜单项点击
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        // TabLayout处理
        // 1、手动添加
        // 2、与ViewPager绑定，通过Pageadapter的getPageTitle(int position)可以获取到选项卡

        // 1、手动添加
        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);

        // 2、与ViewPager绑定，通过Pageadapter的getPageTitle(int position)可以获取到选项卡
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
        tabs.setupWithViewPager(viewPager);

        tabs.setTabMode(TabLayout.MODE_SCROLLABLE);

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        item.setChecked(true);
        drawerLayout.closeDrawers();
        return false;
    }

    private class ViewPagerAdapter extends FragmentStatePagerAdapter {


        private final String[] titles;

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
            titles = UIUtils.getStrings(R.array.content_title);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment  = FragmentFactory.createFragment(position);
            return fragment;
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }

}
