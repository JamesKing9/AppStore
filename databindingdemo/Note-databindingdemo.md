# databinding 使用入门笔记
## 简介
```
2015年的Google IO大会上，Android 团队发布了一个数据绑定框架（Data Binding Library），官方原生支持 MVVM 模型。以后可以直接在 layout 布局 xml 文件中绑定数据了，无需再 findViewById然后手工设置数据了。其语法和使用方式和 JSP 中的 EL 表达式非常类似。
Android databinding 的使用，是为了解决数据传输问题。
Data binding 在2015年7月发布的Android Studio v1.3.0 版本上引入，在2016年4月Android Studio v2.0.0 上正式支持。目前为止，Data Binding 已经支持双向绑定了。
Databinding 是一个实现数据和UI绑定的框架，是一个实现 MVVM 模式的工具，有了 Data Binding，在Android中也可以很方便的实现MVVM开发模式。

Data Binding 是一个support库，最低支持到Android 2.1（API Level 7+）。

Data Binding 之前，我们不可避免地要编写大量的毫无营养的代码，如 findViewById()、setText()，setVisibility()，setEnabled() 或 setOnClickListener() 等，通过 Data Binding , 我们可以通过声明式布局以精简的代码来绑定应用程序逻辑和布局，这样就不用编写大量的毫无营养的代码了。
```

参考：http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2015/0603/2992.html

# 5.1． 使用步骤：

## 1、开启DataBinding

由于AndroidStudio已经默认集成了DataBinding，所以我们只需要将开关打开即可
在应用的 build.gradle 中添加开启配置
```
android {
    ....
    dataBinding {
        enabled = true
    }
}
```

## 2、创建实体(一个 POJO 类)，并在Layout中绑定数据

```java
public class User {
   public String name;
   public User(String name) {

       this.name = name;
   }
}
```
准备好bean后我们可以通过下面的表达是将控件和属性值绑定。
```xml
<EditText
	android:layout_width="wrap_content"
	android:layout_height="wrap_content"
	android:text="@{user.name}"
```
绑定好后我们需要解决user这个对象从那里来。
## 3、关联实体和控件
```
   <data>
       <variable name="user" type="com.example.User"/>
   </data>
```
此时就相当于我们代码中声明了一个User。
数据和控件都已经准备好了，我们需要将两者放到一起。此时，大家可以把data标签看成是全局变量，而原来的布局文件看成是方法，我们需要一个类将两者组装起来。

当然，`data` 节点也支持 `import`，所以上面的代码可以换一种形式来写。

```xml
<data>
  <import type="com.example.User" />
  <variable name="user" type="User" />
 </data>
```



## 4、使用layout标签将界面和data标签组装到一起

```xml
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android">
   <data>
       <variable name="user" type="com.example.User"/>
   </data>
   <LinearLayout
       android:orientation="vertical"
       android:layout_width="match_parent"
       android:layout_height="match_parent">
       <TextView android:layout_width="wrap_content"
           android:layout_height="wrap_content"
           android:text="@{user.name}"/>
   </LinearLayout>
</layout>
```
类有了之后，全局变量还没有赋值，我们可以通过变量的setter方法进行赋值，即调用setUser方法设置User数据。

```java
package com.cheng.databindingdemo;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cheng.databindingdemo.databinding.ActivityMainBinding;
import com.cheng.databindingdemo.model.bean.User;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);

        // Binding有一个设置User的入口，setUser
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        User user = new User("cheng");
        binding.setUser(user);
    }
}
```

## 5、实体对象的创建及数据设置

处理完上述4步工作后需要rebuild一下工程，工具会根据layout标签生成对应的类，类名与该布局文件。



>这篇文档介绍了如何使用 Data Binding Library 来编写声明式的布局，这样可以减少应用中逻辑代码和布局之间所需要的“胶水代码”。

>Data Binding Library 提供了非常好的灵活性与兼容性 － 它是一个 support library，可以在 Android 2.1(API level 7+)及其以上的平台使用。

>要使用 data binding，Android 的 Gradle 插件必须是 1.5.0-alpha1 或更高版本。

## 搭建构建环境
1. 在 Android SDK Manager 中下载最新的 Support Library。
2. 在 app module 的 build.gradle 文件中添加 dataBinding 元素，如下：
```java
android {
    ....
    dataBinding {
        enabled = true
    }
}
```
这样DataBinding插件就会在你的项目内添加编译和运行时必需的依赖配置。

如果你的 app module 依赖了一个使用 data binding 的库，那么你的 app module 的 build.gradle 也必须配置 data binding

此外，还要确定您使用的 Android Studio 支持 DataBinding 特性的。在 Android Studio 1.3 以及之后的版本提供了 data binding 的支持，详见 [Android Studio Support for Data Binding](http://developer.android.com/intl/zh-cn/tools/data-binding/guide.html#studio_support)。

# Data Binding 中的布局文件入门
### 1. 编写 data binding 表达式
DataBinding 的布局文件与以前的布局文件有一点不同。它以一个 layout 标签作为根节点，里面包含一个 data 标签与 view 标签。view 标签的内容就是不使用 data binding 时的普通布局文件内容。例子如下：
```xml
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android">
   <data>
       <variable name="user" type="com.example.User"/>
   </data>
   <LinearLayout
       android:orientation="vertical"
       android:layout_width="match_parent"
       android:layout_height="match_parent">
       <TextView android:layout_width="wrap_content"
           android:layout_height="wrap_content"
           android:text="@{user.firstName}"/>
       <TextView android:layout_width="wrap_content"
           android:layout_height="wrap_content"
           android:text="@{user.lastName}"/>
   </LinearLayout>
</layout>
```
在 data 标签中定义的 user 变量描述了一个可以在布局中使用的属性
```xml
<variable name="user" type="com.example.User"/>
```
在布局文件中写在属性值里的表达式使用 “@{}” 的语法。在这里，TextView 的文本被设置为 user 中的 firstName 属性。
```xml
<TextView android:layout_width="wrap_content"
          android:layout_height="wrap_content"
          android:text="@{user.firstName}"/>
```

### 2. 数据对象
假设你有一个 plain-old Java object(POJO) 的 User 对象。
```java
public class User {
   public final String firstName;
   public final String lastName;
   public User(String firstName, String lastName) {
       this.firstName = firstName;
       this.lastName = lastName;
   }
}
```
这个类型的对象拥有不可改变的数据(immutable)。在应用中，写一次之后永不变动数据的对象很常见。这里也可以使用 JavaBeans 对象：
```java
public class User {
   private final String firstName;
   private final String lastName;
   public User(String firstName, String lastName) {
       this.firstName = firstName;
       this.lastName = lastName;
   }
   public String getFirstName() {
       return this.firstName;
   }
   public String getLastName() {
       return this.lastName;
   }
}
```
从 data binding 的角度看，这两个类是等价的。用于 TextView 的`android:text`属性的表达式`@{user.firstName}`，对于 POJO 对象会读取 firstName 字段，对于 JavaBeans 对象会调用 getFirstName() 方法。此外，如果 user 中有 firstName() 方法存在的话，也可以使用@{user.firstName}表达式调用。

### 3. 绑定数据
3.1 数据绑定工具在编译时会基于布局文件生成一个 Binding 类。默认情况下，这个类的名字将基于布局文件的名字产生，把布局文件的名字转换成帕斯卡命名形式并在名字后面接上”Binding”。例如，上面的那个布局文件叫 main_activity.xml，所以会生成一个 MainActivityBinding 类。这个类包含了布局文件中所有的绑定关系（user 变量和user表达式），并且会根据绑定表达式给布局文件中的 View 赋值。
在 inflate 一个布局的时候创建 binding 的方法如下：
```java
@Override
protected void onCreate(Bundle savedInstanceState) {
   super.onCreate(savedInstanceState);
   MainActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.main_activity);
   User user = new User("Test", "User");
   binding.setUser(user);
}
```
就这么简单！运行应用，你会发现测试信息已经显示在界面中了。

3.2 你也可以通过以下这种方式绑定view：
```java
MainActivityBinding binding = MainActivityBinding.inflate(getLayoutInflater());
```
MainActivityBinding.inflate() 会填充 MainActivityBinding 对应的布局，并创建 MainActivityBinding 对象，把布局和MainActivityBinding对象绑定起来。

3.3 如果在 ListView 或者 RecyclerView 的 adapter 中使用 data binding，可以这样写：
```java
ListItemBinding binding = ListItemBinding.inflate(layoutInflater, viewGroup, false);
//or
ListItemBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.list_item, viewGroup, false);
```

### 4. 事件处理
数据绑定允许你编写表达式来处理view分发的事件（比如 onClick）。事件属性名字取决于监听器方法名字。例如[View.OnLongClickListener](https://developer.android.com/reference/android/view/View.OnLongClickListener.html)有[onLongClick()](https://developer.android.com/reference/android/view/View.OnLongClickListener.html#onLongClick(android.view.View))的方法，因此这个事件的属性是android:onLongClick
。处理事件有两种方法：
- 方法引用绑定：在您的表达式中，您可以引用符合监听器方法签名的方法。当表达式的值为方法引用时，Data Binding会创建一个侦听器中封装方法引用和方法所有者对象，并在目标视图上设置该侦听器。如果表达式的值为null，数据绑定则不会创建侦听器，而是设置一个空侦听器。
- Lisenter 绑定：如果事件处理表达式中包含lambda表达式。数据绑定一直会创建一个监听器，设置给视图。当事件分发时，侦听器才会计算lambda表达式的值。

#### 4.1. 方法引用绑定
事件可以直接绑定到处理器的方法上，类似于`android：onClick`可以分配一个Activity中的方法。与`View＃onClick`属性相比，方法绑定一个主要优点是表达式在编译时就处理了，因此如果该方法不存在或其签名不正确，您会收到一个编译时错误。

方法引用绑定和监听器绑定之间的主要区别是，包裹方法引用的监听器是在数据绑定时创建的，包裹监听器绑定是在触发事件时创建的。如果您喜欢在事件发生时执行表达式，则应使用监听器绑定。

要将事件分配给其处理程序，使用正常的绑定表达式，该值是要调用的方法名称。例如，如果数据对象有两个方法：
```java
public class MyHandlers {
    public void onClickFriend(View view) { ... }
}
```
像下面这样，绑定表达式可以为视图分配一个点击监听器：
```java
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android">
   <data>
       <variable name="handlers" type="com.example.Handlers"/>
       <variable name="user" type="com.example.User"/>
   </data>
   <LinearLayout
       android:orientation="vertical"
       android:layout_width="match_parent"
       android:layout_height="match_parent">
       <TextView android:layout_width="wrap_content"
           android:layout_height="wrap_content"
           android:text="@{user.firstName}"
           android:onClick="@{handlers::onClickFriend}"/>
   </LinearLayout>
</layout>
```
请注意，表达式中的方法签名必须与监听器对象中的方法签名完全匹配。

#### 4.2. Lisenter 绑定
Lisenter 绑定是在应用程序运行中事件发生时才绑定表达式。它们和方法引用绑定类似，但Listener 绑定允许运行时的任意数据绑定表达式。此功能适用于版本2.0及更高版本的Android Gradle插件。 在方法引用绑定中，方法的参数必须与事件侦听器的参数匹配。在Listener 绑定中，只要返回值必须与Lisenter的预期返回值匹配（除非它期望void）。

例如，您可以有一个presenter类，它具有以下方法：
```java
public class Presenter {
    public void onSaveClick(Task task){}
}
```
然后，您可以将点击事件绑定到您的类中，如下所示：
```xml
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android">
  <data>
      <variable name="task" type="com.android.example.Task" />
      <variable name="presenter" type="com.android.example.Presenter" />
  </data>
  <LinearLayout
    android:layout_width="match_parent"
    android:layout_height="match_parent">
      <Button
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
          android:onClick="@{() -> presenter.onSaveClick(task)}" />
  </LinearLayout>
</layout>
```
侦听器由只允许由作为表达式的根元素的lambda表达式表示。当在表达式中使用回调时，数据绑定自动创建必要的侦听器并且为事件注册。当视图触发事件时，数据绑定将执行给定的表达式。与在正则绑定表达式中一样，在执行这些侦听器表达式时，仍然会获得数据绑定的空值和线程安全性。
注意，在上面的示例中，我们没有在ambda表达式中定义传递到 onClick（android.view.View）的视图参数。侦听器绑定为侦听器参数提供两个选择：忽略方法的所有参数和命名所有参数。如果您喜欢命名参数，可以在表达式中使用它们。例如，上面的表达式可以写成：
```xml
android:onClick="@{(view) -> presenter.onSaveClick(task)}"
```
如果你想要使用表达式中的参数，可以在这样使用：
```java
public class Presenter {
    public void onSaveClick(View view, Task task){}
}
```
```xml
android:onClick="@{(theView) -> presenter.onSaveClick(theView, task)}"
```
您也可以使用具有多个参数的lambda表达式：
```java
public class Presenter {
    public void onCompletedChanged(Task task, boolean completed){}
}
```
```xml
<CheckBox
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:onCheckedChanged="@{(cb, isChecked) -> presenter.completeChanged(task, isChecked)}" />
```
如果正在侦听的事件返回类型不是void的值，则表达式也必须返回相同类型的值。例如，如果你想监听长点击事件，你的表达式应该返回布尔值。
```java
public class Presenter {
    public boolean onLongClick(View view, Task task){}
}
```
```xml
android:onLongClick="@{(theView) -> presenter.onLongClick(theView, task)}"
```
如果由于空对象而无法计算表达式，数据绑定将返回该类型的默认Java值。例如，引用类型为null，int为0，boolean为false等。 如果需要使用带谓词（例如三元）的表达式，则可以使用void作为符号。
```xml
android:onClick="@{(v) -> v.isVisible() ? doSomething() : void}"
```
#### 4.3. 避免复杂侦听器
Listener表达式非常强大，可以使您的代码非常容易阅读。另一方面，包含复杂表达式的 Listener 又会使您的布局难以阅读和难以维护。这些表达式应该像从UI传递可用数据到回调方法一样简单。您应该在从侦听器表达式调用的回调方法中实现任何业务逻辑。

一些专门的点击事件处理程序存在，他们需要一个属性，而不是android：onClick避免冲突。已创建以下属性以避免此类冲突。

|    Class     |             Listener Setter              |       Attribute       |
| :----------: | :--------------------------------------: | :-------------------: |
|  SearchView  | setOnSearchClickListener(View.OnClickListener) | android:onSearchClick |
| ZoomControls | setOnZoomInClickListener(View.OnClickListener) |   android:onZoomIn    |
| ZoomControls | setOnZoomOutClickListener(View.OnClickListener) |   android:onZoomOut   |

#  DataBinding 中的布局文件进阶
### 1. import
1.1 data标签内可以有0个或者多个 import 标签。你可以在布局文件中像使用 Java 一样导入引用。
```xml
<data>
    <import type="android.view.View"/>
</data>
```
导入的类有两种用处，一种是用来定义变量，一种是在表达式中访问类中的静态方法和属性
现在 View 可以被在表达式中这样引用：
```xml
<TextView
   android:text="@{user.lastName}"
   android:layout_width="wrap_content"
   android:layout_height="wrap_content"
   android:visibility="@{user.isAdult ? View.VISIBLE : View.GONE}"/>
```
1.2 当类名发生冲突时，可以使用 alias：
```xml
<import type="android.view.View"/>
<import type="com.example.real.estate.View"
        alias="Vista"/>
```
现在，Vista 可以用来引用 com.example.real.estate.View ，与 View 在布局文件中同时使用。

1.3 导入的类型可以用于变量的类型引用和表达式中：
```xml
<data>
    <import type="com.example.User"/>
    <import type="java.util.List"/>
    <variable name="user" type="User"/>
    <variable name="userList" type="List<User>"/>
</data>
```
> 注意：Android Studio 还没有对导入提供自动补全的支持。你的应用还是可以被正常编译，要解决这个问题，你可以在变量定义中使用完整的包名。

```xml
<TextView
   android:text="@{((User)(user.connection)).lastName}"
   android:layout_width="wrap_content"
   android:layout_height="wrap_content"/>
```
1.4 导入的类也可以在表达式中使用静态属性/方法:
```xml
<data>
    <import type="com.example.MyStringUtils"/>
    <variable name="user" type="com.example.User"/>
</data>
…
<TextView
   android:text="@{MyStringUtils.capitalize(user.lastName)}"
   android:layout_width="wrap_content"
   android:layout_height="wrap_content"/>
```
1.5 和 Java 一样，java.lang.* 会被自动导入。

### 2. 变量

### 3. 自定义 Binding 类名

### 4. Includes

### 5. 表达式语言

## 数据对象
### 1. Observable 对象

### 2. Observable 属性

### 3. Observable 容器类

## 生成绑定
### 1. 创建

### 2. 带有 ID 的 View

### 3. 变量

### 4. ViewStub

### 5. 高级绑定

#### 1. 动态变量

#### 2. 直接 binding

#### 3. 后台线程问题


## 属性 Setter

### 1. 自动 Setter

### 2. 重命名 Setter

### 3. 自定义 Setter

## 转换器

### 1. 对象转换

### 2. 自定义转换

# Android Studio 对 Data binding 的支持的名称相关。
```java
@Override
protected void onCreate(Bundle savedInstanceState) {
   super.onCreate(savedInstanceState);
   ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
   User user = new User("Test", "User");
   binding.setUser(user);
}
```
命名规范
布局文件：R.layout.activity_main
类名：ActivityMainBinding

总结：
把布局文件看成是一个类，有属性，有方法。属性值需要通过set进来。
