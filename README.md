# AlphaIndicatorView
### 仿微信底部tab标签，滑动的时候颜色渐变效果

该项目参考了：[http://blog.csdn.net/lmj623565791/article/details/41087219](http://blog.csdn.net/lmj623565791/article/details/41087219) 按照张鸿洋的思路把代码进行了优化和扩展，自动判断是否具有图标和文字，允许两张图片渐变。同时欢迎大家下载体验本项目，如果使用过程中遇到什么问题，欢迎反馈。

## 演示
 ![image](https://github.com/jeasonlzy0216/AlphaIndicatorView/blob/master/screenshots/demo1.gif)![image](https://github.com/jeasonlzy0216/AlphaIndicatorView/blob/master/screenshots/demo2.gif)


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
### 1.在Activity中做如下初始化
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
        android:layout_weight="1" />

    <View
        android:layout_width="match_parent"
        android:layout_height="1px"
        android:background="#888" />

    <com.lzy.ui.AlphaIndicator
        android:id="@+id/alphaIndicator"
        android:layout_width="match_parent"
        android:layout_height="50dp"
        android:orientation="horizontal">

        <com.lzy.ui.AlphaView
            android:layout_width="0dp"
            android:layout_height="match_parent"
            android:layout_weight="1"
            android:padding="5dp"
            app:tabIconNormal="@mipmap/community"
            app:tabIconSelected="@mipmap/community_on"
            app:tabText="首页"
            app:tabTextSize="14sp"
            app:textColorNormal="#484848"
            app:textColorSelected="#F1A100" />

        <com.lzy.ui.AlphaView
            android:layout_width="0dp"
            android:layout_height="match_parent"
            android:layout_weight="1"
            android:padding="5dp"
            app:tabIconNormal="@mipmap/talk"
            app:tabIconSelected="@mipmap/talk_on"
            app:tabText="指示控件"
            app:tabTextSize="14sp"
            app:textColorNormal="#484848"
            app:textColorSelected="#F1A100" />

        <com.lzy.ui.AlphaView
            android:layout_width="0dp"
            android:layout_height="match_parent"
            android:layout_weight="1"
            android:padding="5dp"
            app:tabIconNormal="@mipmap/shopping"
            app:tabIconSelected="@mipmap/shopping_on"
            app:tabText="UI特效"
            app:tabTextSize="14sp"
            app:textColorNormal="#484848"
            app:textColorSelected="#F1A100" />

        <com.lzy.ui.AlphaView
            android:layout_width="0dp"
            android:layout_height="match_parent"
            android:layout_weight="1"
            android:padding="5dp"
            app:tabIconNormal="@mipmap/wo"
            app:tabIconSelected="@mipmap/wo_on"
            app:tabText="条目效果"
            app:tabTextSize="14sp"
            app:textColorNormal="#484848"
            app:textColorSelected="#F1A100" />
    </com.lzy.ui.AlphaIndicator>
</LinearLayout>
	
```
