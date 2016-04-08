# AlphaIndicatorView
### 仿微信底部tab标签，滑动的时候颜色渐变，使用极其简单，只需要两行代码。

欢迎大家下载体验本项目，如果使用过程中遇到什么问题，欢迎反馈。

### 联系方式
 * 邮箱地址： liaojeason@126.com
 * QQ群： 489873144 （建议使用QQ群，邮箱使用较少，可能看的不及时）
 * 本群刚建立，旨在为使用我的github项目的人提供方便，如果遇到问题欢迎在群里提问。个人能力也有限，希望一起学习一起进步。


## 演示
 ![image](https://github.com/jeasonlzy0216/AlphaIndicatorView/blob/master/screenshots/demo1.gif)![image](https://github.com/jeasonlzy0216/AlphaIndicatorView/blob/master/screenshots/demo2.png)![image](https://github.com/jeasonlzy0216/AlphaIndicatorView/blob/master/screenshots/demo3.png)

## 1.用法
该项目和我github上其他的view相关的项目已经一起打包上传到jCenter仓库中（源码地址 [https://github.com/jeasonlzy0216/ViewCore](https://github.com/jeasonlzy0216/ViewCore) ），使用的时候可以直接使用compile依赖，用法如下
```java
	compile 'com.lzy.widget:view-core:0.1.5'
```
或者使用
```java
    compile project(':alphaview')
```

## 注意事项
该项目可以自动判断每一个Tab中的View，是否具有图标和文字，允许出现以下三种情况：
 * 自定义属性只设置了 tabText ，表示只有文字，滑动时文字颜色渐变过度
 * 自定义属性只设置了 tabIconNormal 和 tabIconSelected ，表示只有图标，滑动时图标颜色渐变过度
 * 以上两种都设置了，表示文字和图标同时渐变过度

## 功能和参数含义

<table>
  <tdead>
    <tr>
      <th align="center">配置参数</th>
      <th align="center">参数含义</th>
    </tr>
  </tdead>
  <tbody>
    <tr>
      <td align="center">tabIconNormal</td>
      <td align="center">未选中的图标</td>
    </tr>
    <tr>
      <td align="center">tabIconSelected</td>
      <td align="center">已经选中的图标</td>
    </tr>
    <tr>
      <td align="center">tabText</td>
      <td align="center">tab标签的文字</td>
    </tr>
    <tr>
      <td align="center">tabTextSize</td>
      <td align="center">tab标签的文字大小</td>
    </tr>
    <tr>
      <td align="center">textColorNormal</td>
      <td align="center">未选中的文字颜色</td>
    </tr>
    <tr>
      <td align="center">textColorSelected</td>
      <td align="center">已选中的文字颜色</td>
    </tr>
  </tbody>
</table>

## 3.代码参考
### 1.在Activity中做如下初始化，只需要找到控件并且设置ViewPager即可完成。
```java
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(new MainAdapter(getSupportFragmentManager()));
        AlphaIndicator alphaIndicator = (AlphaIndicator) findViewById(R.id.alphaIndicator);
        alphaIndicator.setViewPager(viewPager);
    }
```
### 2.布局文件
```xml
	<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
              xmlns:app="http://schemas.android.com/apk/res-auto"
              android:layout_width="match_parent"
              android:layout_height="match_parent"
              android:orientation="vertical">

    <android.support.v4.view.ViewPager
        android:id="@+id/viewPager"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight="1"/>

    <View
        android:layout_width="match_parent"
        android:layout_height="1px"
        android:background="#888"/>

    <com.lzy.widget.AlphaIndicator
        android:id="@+id/alphaIndicator"
        android:layout_width="match_parent"
        android:layout_height="55dp"
        android:orientation="horizontal">

        <com.lzy.widget.AlphaView
            android:layout_width="0dp"
            android:layout_height="match_parent"
            android:layout_weight="1"
            android:padding="5dp"
            app:av_tabIconNormal="@mipmap/home_normal"
            app:av_tabIconSelected="@mipmap/home_selected"
            app:av_tabText="微信"
            app:av_tabTextSize="14sp"
            app:av_textColorNormal="#999999"
            app:av_textColorSelected="#46c01b"/>

        <com.lzy.widget.AlphaView
            android:layout_width="0dp"
            android:layout_height="match_parent"
            android:layout_weight="1"
            android:padding="5dp"
            app:av_tabIconNormal="@mipmap/category_normal"
            app:av_tabIconSelected="@mipmap/category_selected"
            app:av_tabText="通讯录"
            app:av_tabTextSize="14sp"
            app:av_textColorNormal="#999999"
            app:av_textColorSelected="#46c01b"/>

        <com.lzy.widget.AlphaView
            android:layout_width="0dp"
            android:layout_height="match_parent"
            android:layout_weight="1"
            android:padding="5dp"
            app:av_tabIconNormal="@mipmap/service_normal"
            app:av_tabIconSelected="@mipmap/service_selected"
            app:av_tabText="发现"
            app:av_tabTextSize="14sp"
            app:av_textColorNormal="#999999"
            app:av_textColorSelected="#46c01b"/>

        <com.lzy.widget.AlphaView
            android:layout_width="0dp"
            android:layout_height="match_parent"
            android:layout_weight="1"
            android:padding="5dp"
            app:av_tabIconNormal="@mipmap/mine_normal"
            app:av_tabIconSelected="@mipmap/mine_selected"
            app:av_tabText="我"
            app:av_tabTextSize="14sp"
            app:av_textColorNormal="#999999"
            app:av_textColorSelected="#46c01b"/>
    </com.lzy.widget.AlphaIndicator>
</LinearLayout>
	
```
