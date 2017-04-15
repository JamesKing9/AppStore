# content

[TOC]

---

# 该项目涉及的知识点：

1、列表向上滑动，隐藏标题栏来给用户更大的阅读空间。



# 开发中的用具

## 有用的网站

[Google Developers](https://developers.google.cn/)



## Android 平台对应的 API Level

？？？

---



# 需求分析

？？？？？

---



# 项目架构



？？？？

---

# 接口调试

？？？？

---

# 技术调研

？？？

---



# 项目开发实施

## 1、添加常用的工具类

Log、UI、Permission、String 等等；



---

# 随机需求

？？？？

---

# 项目上线

？？？

---

# Bug 管理

？？？

---

# 版本管理

？？？

---













---



# Sugar

使用 sugar 管理数据库（使用数据库来管理下载的数据逻辑）

`com.github.satyan:sugar:1.5`
Sugar轻量级数据库工具使用
官方链接：http://satyan.github.io/sugar/getting-started.html

https://github.com/satyan/sugar

# 使用步骤：

## 1、添加依赖

`compile 'com.github.satyan:sugar:1.4+'`
这样就可以在我们的项目里面使用Sugar的代码了。
## 2、配置清单文件

使用Sugar的时候，不需要像使用SQLiteOpenHelper那样要创建一个实例来构建我们的数据库信息，你只需要在我们的AndroidManifest.xml里面添加配置就可以了。
AndroidManifest.xml配置meta-data
		1.	DATABASE：告知Sugar数据库文件的名字。
	    2.	VERSION：告知Sugar当前的数据库版本。同样的，当当前版本比数据库文件的版本高的话，Sugar会自动帮我们执行升级操作。
	    3.	QUERY_LOG： 选择是否记录搜索语句到日志环境，这样你可以看到执行的查询SQL，便于确认的你SQL是否正确
	    4.	DOMAIN_PACKAGE_NAME：DOMAIN实体Bean，实体Bean所在包的名字。这个值告诉Sugar我们的程序的数据结构类的保存路径。Sugar会自动根据这个指定路径下面的所有类的结构去生成数据表及其对应的增删查改（CRUD）接口。
如下：

AndroidManifest.xml

```xml
<application
  android:name="com.cheng.appstore.MyApplication"
  android:allowBackup="true"
  android:icon="@mipmap/ic_launcher"
  android:label="@string/app_name"
  android:supportsRtl="true"
  android:theme="@style/AppTheme">

  <!--配置meta-data-->
  <meta-data
  android:name="DATABASE"
  android:value="app_store.db" />
  <meta-data
  android:name="VERSION"
  android:value="1" />
  <meta-data
  android:name="QUERY_LOG"
  android:value="true" />
  <meta-data
  android:name="DOMAIN_PACKAGE_NAME"
  android:value="com.cheng.appstore.model.db" />
  ...
</application>
```

## 3、Application中配置Sugar
需要在我们的Application类下面添加应用启动和应用停止时的处理代码，以便于让Sugar进行相应的初始化和结束动作。
```java
/**
 * 应用子类，利用这个类我们可以监听应用的生命周期并且定义一些应用级别的单例
 */
public class Application extends android.app.Application{
    /**
     * 这个方法在应用初始化的时候被调用
     */
    @Override
    public void onCreate() {
        super.onCreate();
        //Sugar 初始化相应的动作
        SugarContext.init(this);
    }

    //Sugar 结束相应的动作
    @Override
    public void onTerminate() {
        super.onTerminate();
        SugarContext.terminate();
    }
}
```
##4、创建表 ，定义Entities

创建实体Bean，作为SugarRecord子类（eg. public class Book extends SugarRecord）, 相当于创建了一张表.

Extend `SugarRecord` for all the classes that you need persisted. That's it. Sugar takes care of table creation for you.
Note: Please retain the default constructor.
```java
public class Book extends SugarRecord {
  String title;
  String edition;
  public Book(){
  }

  public Book(String title, String edition){
    this.title = title;
    this.edition = edition;
  }
}
```
Or you can use annotations`@Table`, but then you should define a private Long id field
```java
@Table
public class Book {
  private Long id;

  public Book(){
  }

  public Book(String title, String edition){
     this.title = title;
     this.edition = edition;
  }

  public Long getId() {
      return id;
  }
}
```
### 4.1、Adding properties.
Now that you have the entity, start defining their properties.
```java
public class Book extends SugarRecord {
  String name;
  String ISBN;
  String title;
  String shortSummary;
}
```
This would create corresponding columns in the `book` table. Column names would be `name, ISBN, title and short_summary`. Notice the conversion from `shortSummary` to `short_summary`. This is the convention followed in Sugar. (Next step: making it configurable.)

To skip a property from persisting, annotate it as Ignore.
In below example, `name` would not be persisted, neither would a corresponding column be created for this property.
```java
public class Book extends SugarRecord {
  @Ignore
  String name;

  String ISBN;
}
```

### 4.2、Managing relationships
Lets bring another entity into picture.
```java
public class Author extends SugarRecord {
  String name;

  public Author() {}

  // Get all books of this author
  List<Book> getBooks() {
      return Book.find(Book.class, "author = ?", new String{getId()})
  }
}
```
Each book has an author and that would be represented(被代表) by having a reference to Author in Book class, as follows:
```java
public class Book extends SugarRecord {
  String name;
  String ISBN;
  String title;
  String shortSummary;

  // defining a relationship
  Author author;
}
```
This would store a column named `author` in the `book` table. This would help with one-to-many(一对多) relationships.
* You should save(增加) `Author` before saving `Book`.
* We will try to improve（改进） how we handle one-to-many relationships
* One-to-one relationship doesn't work yet

## 5、对表的操作
Performing `CRUD` operations are very simple. Functions like`save()`, `delete()` and `findById(..)` are provided to make the work easy.
Note: Record indexes start at index 1.(记录的索引值开始于 1)
Save Entity:（增加一条记录）
```java
Book book = new Book("Title here", "2nd edition")
book.save();
```
Load Entity:（查询一条记录）
```java
Book book = Book.findById(Book.class, 1);
```
Update Entity:（更新/修改一条记录）
```
Book book = Book.findById(Book.class, 1);
book.title = "updated title here"; // modify the values
book.edition = "3rd edition";
book.save(); // updates the previous entry with new values.
```
Delete Entity:（删除一条记录）
```
Book book = Book.findById(Book.class, 1);
book.delete();
```
Bulk Operations:（批量化操作）
```
List<Book> books = Book.listAll(Book.class);

Book.deleteAll(Book.class);
```
其他的一些操作
```java
List<Book> books = Book.find(Book.class, "author = ?", new String{author.getId()});
```
```java
Book book = Book.findById(Books.class, 1);
Author author = book.author;
```

## adb shell 使用sqlite3 数据库
进入adb shell
```
adb shell
```

进入到 app 的数据库目录下
```
#cd /data/data/com.cheng.appstore/databases
#ls -l
```

sqlite3 进入 数据库
```
# sqlite3 app_store.db
SQLite version 3.7.11 2012-03-20 11:35:50
Enter ".help" for instructions
Enter SQL statements terminated with a ";"
sqlite>
```

列出可用的数据库
```
.databases
```

列出表
```
.tables 
```

查询表的数据
```
select * from APP_ENTITY;
```

退出sqlite3
```
.exit
```

# 首页

![device-2017-04-07-224530](../device-2017-04-07-224530.png)

## 1、轮播图

## 2、RecyclerView 列表
```java
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.cheng.appstore.Constants;
import com.cheng.appstore.MyApplication;
import com.cheng.appstore.R;
import com.cheng.appstore.model.net.AppInfo;
import com.cheng.appstore.model.net.HomeInfo;
import com.cheng.appstore.utils.HttpUtils;
import com.cheng.appstore.utils.UIUtils;
import com.cheng.appstore.vm.holder.BaseHolder;
import com.cheng.appstore.vm.holder.NextPagerHolder;

import java.util.HashMap;
import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<AppInfo> {
    protected List<D> datas;
    protected NextPagerHolder nextPagerHolder;

    // 如果加载其他样式的Item我们需要做的工作：
    // 判断具体有那些样式：2种（普通、加载下一页）
    protected static final int NOMAL = 0;
    protected static final int NEXTPAGER = 2;

    public BaseRecyclerViewAdapter(List<D> datas) {
        this.datas = datas;
    }

    /**
     * 获取不同界面需要展示数据的服务器链接
     * @return
     */
    protected abstract String getPath();


    @Override
    public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseHolder holder = null;
        switch (viewType) {
            case NOMAL:
                // 处理通用的项布局加载
                // 两种处理方案：
                // 1、交给子类完成
                // 2、依据getPath来区分不同的Adapter，从而创建对应的Holder

                if(getPath().equals(Constants.Http.SUBJECT)){
                    holder=new SubjectInfoHolder(LayoutInflater.from(
                            parent.getContext()).inflate(getNomalLayoutDesId(), parent,
                            false));
                }else {
                    holder = new AppInfoHolder(LayoutInflater.from(
                            parent.getContext()).inflate(getNomalLayoutDesId(), parent,
                            false));
                }
                break;
            case NEXTPAGER:
                holder = nextPagerHolder = new NextPagerHolder(LayoutInflater.from(
                        parent.getContext()).inflate(R.layout.item_loadmore, parent,
                        false), this);

                break;
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(BaseHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case NOMAL:
                D data = getNomalItemData(position);
                holder.setData(data);
                break;
            case NEXTPAGER:
                LogUtils.s("加载下一页数据");
                // 最后一项显现出来，这样就不用判断RecyclerView是否滚动到底部
                holder.setData(NextPagerHolder.LOADING);
                loadNextPagerData();
                break;
        }

    }

    /**
     * 获取通用条目的数据
     * @param position
     * @return
     */
    protected abstract D getNomalItemData(int position);


    /**
     * 获取通用条目布局的id信息
     * @return
     */
    protected abstract int getNomalLayoutDesId();

    /**
     * 加载下一页数据
     *
     */
    public void loadNextPagerData() {

        // 加载本地
        // 是否加载到数据
        // 加载到，展示界面
        // 没加载到，获取网络数据
        // 是否加载到数据
        // 加载到，展示界面
        // 没加载到，重试界面显示

        // 判断是否含有下一页数据
        // 有，显示加载中条目
        // 没有，什么都不显示

        String key = getPath() + "." + datas.size();
        String json = CommonCacheProcess.getLocalJson(key);

        if (json == null) {
            // 加载网络数据
            OkHttpClient client = new OkHttpClient();

            HashMap<String, Object> params = new HashMap<>();
            params.put("index", datas.size());
            final Request request = HttpUtils.getRequest(getPath(), params);

            Call call = HttpUtils.getClient().newCall(request);

            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    // 显示重试条目
                    nextPagerHolder.setData(NextPagerHolder.ERROR);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    // 判断是否获取到数据
                    if (response.code() == 200) {
                        String json = response.body().string();
                        showNextPagerData(json);
                    } else {
                        // 显示重试条目
                        nextPagerHolder.setData(NextPagerHolder.ERROR);
                    }
                }
            });

        } else {
            // 加载本地数据
            showNextPagerData(json);
        }
    }

    /**
     * 解析并显示下一页数据
     * @param json
     */
    protected void showNextPagerData(String json){
        List<D> nextPagerData=getNextPagerData(json);
        if (nextPagerData != null && nextPagerData.size() > 0) {
            // 更新界面
            datas.addAll(nextPagerData);
            SystemClock.sleep(2000);
            // 线程：子线程
            MyApplication.getHandler().post(new Runnable() {
                @Override
                public void run() {
                    notifyDataSetChanged();
                }
            });

        } else {
            nextPagerHolder.setData(NextPagerHolder.NULL);
        }
    }
    /**
     * 获取下一页数据
     */
    protected abstract List<D> getNextPagerData(String json);
    private HomeInfo info;// info.list
    private FragmentActivity activity;


    public HomeAdapter(HomeInfo info, FragmentActivity activity) {
        super(info.list);
        this.info = info;
        this.activity = activity;
    }

    // 如果加载其他样式的Item我们需要做的工作：
    // 判断具体有那些样式：2种+1种（加载下一页）
    private static final int CAROUSEL = 1; /* 轮播图模式*/

    // 具体的操作内容：
    // 1、添加getItemViewType，依据position去判断当前条目的样式
    // 2、修改onCreateViewHolder，依据样式加载不同layout
    // 3、修改onBindViewHolder，依据样式绑定不同数据

    @Override
    public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseHolder holder = super.onCreateViewHolder(parent, viewType);

        if (viewType == CAROUSEL) {
            holder = new CarouselHolder(LayoutInflater.from(
                    parent.getContext()).inflate(R.layout.fragment_home_carousel, parent,
                    false));
        }

        return holder;
    }

    @Override
    protected int getNomalLayoutDesId() {
        return R.layout.item_appinfo;
    }


    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return CAROUSEL;
        } else if (position == (datas.size() + 1)) {
            return NEXTPAGER;
        } else {
            return NOMAL;
        }
    }


    @Override
    public void onBindViewHolder(BaseHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        switch (holder.getItemViewType()) {
            case CAROUSEL:
                // 轮播
                holder.setData(info.picture);
                break;
        }

    }

    @Override
    protected AppInfo getNomalItemData(int position) {
        return datas.get(position - 1);
    }


    @Override
    protected String getPath() {
        return Constants.Http.HOME;
    }

    @Override
    protected List<AppInfo> getNextPagerData(String json) {
        HomeInfo nextPager = MyApplication.getGson().fromJson(json, HomeInfo.class);
        if (nextPager != null) {
            return nextPager.list;
        } else {
            nextPagerHolder.setData(NextPagerHolder.NULL);
        }
        return null;
    }


    @Override
    public int getItemCount() {
        return datas.size() + 1 + 1;// 轮播   下一页
    }

    /**
     * 轮播使用的Holder
     */
    class CarouselHolder extends BaseHolder<List<String>> {

        private final SliderLayout sliderLayout;

        public CarouselHolder(View itemView) {
            super(itemView);
            sliderLayout = (SliderLayout) itemView;

            // 高度的获取：保持图片的宽高比例不变
            // 181/480
            // 读取屏幕的宽度

            Display defaultDisplay = activity.getWindowManager().getDefaultDisplay();
            int height = defaultDisplay.getWidth() * 181 / 480;// 像素
            RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, UIUtils.px2Dip(height));
            sliderLayout.setLayoutParams(layoutParams);
        }

        public void setData(List<String> data) {
            sliderLayout.removeAllSliders();
            for (String item : data) {

                // 创建Item项
                // 添加到sliderLayout
                DefaultSliderView sliderView = new DefaultSliderView(UIUtils.getContext());

                // http://localhost:8080/GooglePlayServer/image?name=
                StringBuffer iconUrlBuffer = new StringBuffer(Constants.Http.HOST);
                HashMap<String, Object> params = new HashMap<>();
                params.put("name", item);
                iconUrlBuffer.append(Constants.Http.IMAGE).append(HttpUtils.getUrlParamsByMap(params));

                sliderView.image(iconUrlBuffer.toString());
                sliderLayout.addSlider(sliderView);
            }

        }
    }
}
```








