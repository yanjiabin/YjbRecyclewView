# YjbRecyclewView
带有下拉刷新和上拉加载更多的recyclewview

# YjbRecyclewView 使用方式
##  添加依赖
1.在project目录的build.gradle的allprojects节点添加
```java maven { url "https://jitpack.io" }```
如下:
```java
    maven { url 'https://jitpack.io' }
```
2.在自己Modul的build.gradle中添加```compile 'com.github.yanjiabin:SwitchView:-SNAPSHOT'''```
如下:
```java
dependencies {
	     compile 'com.github.yanjiabin:SwitchView:-SNAPSHOT'
	}
```
## 开始使用
* 在你的布局文件
```java
<RelativeLayout
        android:layout_width="match_parent"
        android:layout_height="40dp"
        android:background="#fff"
        android:paddingLeft="15dp"
        android:paddingRight="15dp">

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_alignParentLeft="true"
            android:layout_centerInParent="true"
            android:gravity="center"
            android:text="名称"
            android:textColor="#000"
            android:textSize="16sp" />

        <com.github.yanjiabin.switchview.SwitchView
            android:id="@+id/name"
            android:layout_width="60dp"
            android:layout_height="30dp"
            android:layout_alignParentRight="true"
            android:layout_centerInParent="true" />
    </RelativeLayout>
    ... 
 ```
* SwitchView的API:
```java

  <declare-styleable name="SwitchView">
        <attr name="hasShadow" format="boolean" />   这个属性是表示是否要阴影效果
        <attr name="primaryColor" format="color" />  这个属性是表示背景的颜色
        <attr name="primaryColorDark" format="color" />
        <attr name="isOpened" format="boolean" />    默认是开还是关true表示的开false表示的关
    </declare-styleable>

```
