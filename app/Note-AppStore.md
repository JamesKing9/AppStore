# "AppStore"项目分析

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







