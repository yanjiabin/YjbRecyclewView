# YjbRecyclewView
带有下拉刷新和上拉加载更多的recyclewview

# YjbRecyclewView 使用方式
##  添加依赖
1.在project目录的build.gradle的allprojects节点添加
```java maven { url "https://jitpack.io" }```
如下:
```java
    allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
2.在自己Modul的build.gradle中添加```compile 'com.github.yanjiabin:SwitchView:-SNAPSHOT'''```
如下:
```java
dependencies {
	        compile 'com.github.yanjiabin:YjbRecyclewView:1.0'
	}
```
## 开始使用
* 在你的layout布局文件中设置
```java

    <com.github.yjbrecycleview.library.recyclerview.LRecyclerView
        android:id="@+id/recycleview"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>

 ```
* YjbRecyclewVieww的API:
###  普通listview条目
```java
	lRecyclerView = (LRecyclerView) findViewById(R.id.recycleview);
        DividerDecoration dividerDecoration =  new DividerDecoration.Builder(this)
                .setHeight(R.dimen.DEFAULT_HEIGHT)
                .setLeftPadding(R.dimen.PADDING)
                .setRightPadding(R.dimen.PADDING)
                .setColorResource(R.color.gray1)
                .build();
        //item之间的分割线
        lRecyclerView.addItemDecoration(dividerDecoration);
        //设置布局管理器
        lRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //创建一个内部的adapter 用来设置数据的adpater
        dataAdapter = new DataAdapter(this);
        lRecyclerViewAdapter = new LRecyclerViewAdapter(dataAdapter);
        lRecyclerView.setAdapter(lRecyclerViewAdapter);
        //设置下拉刷新的箭头   这个可以换
        lRecyclerView.setArrowImageView(R.drawable.ic_pulltorefresh_arrow);
        //设置下拉刷新的样式   
        lRecyclerView.setRefreshProgressStyle(ProgressStyle.LineSpinFadeLoader);
        //设置上拉加载更多的样式
        lRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        //设置可以下拉刷新
        lRecyclerView.setPullRefreshEnabled(true);
        //设置可以上拉加载更多
        lRecyclerView.setLoadMoreEnabled(true);
        //下拉刷新的监听
        lRecyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                dataAdapter.clear();
                lRecyclerViewAdapter.notifyDataSetChanged();
                mCurrentCounter = 0;
                requestData();
            }
        });

        // 上拉加载更多的监听
        lRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (mCurrentCounter < TOTAL_COUNTER) {
                    // loading more
                    requestData();
                } else {
                    //the end
                    lRecyclerView.setNoMore(true);
                }
            }
        });

```
### 带有banner头的普通条目  需要注意的是我这边使用的banner库是这个 compile 'com.youth.banner:banner:1.4.9'
```java
	
        lRecyclerView = (LRecyclerView) findViewById(R.id.banner_rv);
        lRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerDecoration dividerDecoration = new DividerDecoration.Builder(this)
                .setHeight(R.dimen.DEFAULT_HEIGHT)
                .setLeftPadding(R.dimen.PADDING)
                .setRightPadding(R.dimen.PADDING)
                .setColorResource(R.color.gray2)
                .build();

        lRecyclerView.addItemDecoration(dividerDecoration);
	//banner数据
        ArrayList<BannerInfo> data = new ArrayList<>();
        data.add(new BannerInfo(R.mipmap.slient, "第一张图片"));
        data.add(new BannerInfo(R.mipmap.arrow_down, "第三张图片"));
        data.add(new BannerInfo(R.mipmap.ic_action_add, "第四张图片"));
        data.add(new BannerInfo(R.mipmap.smile, "第五张图片"));
        View headViewContainer = View.inflate(this, R.layout.banner_head_layout, null);
        Banner banner = (Banner) headViewContainer.findViewById(R.id.banner);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(data);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
        dataAdapter = new DataAdapter(this);
        lRecyclerViewAdapter = new LRecyclerViewAdapter(dataAdapter);
        lRecyclerViewAdapter.addHeaderView(headViewContainer);
        lRecyclerView.setAdapter(lRecyclerViewAdapter);
        requestData();
        lRecyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                dataAdapter.clear();
                mCurrentCounter  = 0;
                requestData();
            }
        });

        lRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (mCurrentCounter < TOTAL_MAX) {
                    requestData();
                } else {
                    lRecyclerView.setNoMore(true);
                }
            }
        });
```
### banner头的layout布局文件
```java
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">


    <com.youth.banner.Banner
        android:id="@+id/banner"
        android:layout_width="match_parent"
        android:layout_height="150dp" />

</LinearLayout>
```
###  带有多类型条目的recycleview   
```java
 mRecyclerView = (LRecyclerView) findViewById(R.id.recycleview);
        mMultipleItemAdapter = new MultipleItemAdapter(this);
        mLRecyclerViewAdapter = new LRecyclerViewAdapter(mMultipleItemAdapter);
        mRecyclerView.setAdapter(mLRecyclerViewAdapter);
        requestData();
        DividerDecoration divider = new DividerDecoration.Builder(this)
                .setHeight(R.dimen.DEFAULT_HEIGHT)
                .setPadding(R.dimen.PADDING)
                .setColorResource(R.color.gray2)
                .build();
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(divider);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setArrowImageView(R.drawable.ic_pulltorefresh_arrow);
```
#### 在这里我把我的多条目的adapter展示出来
```java
public class MultipleItemAdapter extends  BaseMultiAdapter<MultiItemInfo>{


    public MultipleItemAdapter(Context context) {
        super(context);
        addItemType(MultiItemInfo.TEXT, R.layout.list_item_text);
        addItemType(MultiItemInfo.IMG, R.layout.list_item_pic);
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        MultiItemInfo item = getDataList().get(position);
        switch (item.getItemType()) {
            case MultiItemInfo.TEXT:
                //文本的设置
                bindTextItem(holder,item);

                break;
            case MultiItemInfo.IMG:
                // 图片的设置
                bindPicItem(holder,item);
                break;
        }
    }

    private void bindTextItem(SuperViewHolder holder, MultiItemInfo item) {
        TextView textView = holder.getView(R.id.info_text);
        textView.setText(item.getTitle());
    }

    private void bindPicItem(SuperViewHolder holder, MultiItemInfo item) {
        TextView textView = holder.getView(R.id.info_text);
        ImageView avatarImage = holder.getView(R.id.avatar_image);

        textView.setText(item.getTitle());
        avatarImage.setImageResource(R.mipmap.icon);
    }
}
```
## 以上的三种类型展示可以解决大多数的条目展示，希望对你有所帮助。

