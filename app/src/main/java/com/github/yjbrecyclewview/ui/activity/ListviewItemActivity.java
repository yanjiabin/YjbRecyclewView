package com.github.yjbrecyclewview.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.github.yjbrecycleview.library.ItemDecoration.DividerDecoration;
import com.github.yjbrecycleview.library.interfaces.OnLoadMoreListener;
import com.github.yjbrecycleview.library.interfaces.OnNetWorkErrorListener;
import com.github.yjbrecycleview.library.interfaces.OnRefreshListener;
import com.github.yjbrecycleview.library.recyclerview.LRecyclerView;
import com.github.yjbrecycleview.library.recyclerview.LRecyclerViewAdapter;
import com.github.yjbrecycleview.library.recyclerview.ProgressStyle;
import com.github.yjbrecyclewview.R;
import com.github.yjbrecyclewview.adapter.DataAdapter;
import com.github.yjbrecyclewview.bean.ItemModel;
import com.github.yjbrecyclewview.utils.NetworkUtils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class ListviewItemActivity extends AppCompatActivity {

    private LRecyclerView lRecyclerView;
    private DataAdapter dataAdapter;
    private LRecyclerViewAdapter lRecyclerViewAdapter;


    /**服务器端一共多少条数据*/
    private static final int TOTAL_COUNTER = 340;//如果服务器没有返回总数据或者总页数，这里设置为最大值比如10000，什么时候没有数据了根据接口返回判断

    /**每一页展示多少条数据*/
    private static final int REQUEST_COUNT = 10;

    /**已经获取到多少条数据了*/
    private static int mCurrentCounter = 0;

    private PreviewHandler mHandler = new PreviewHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview_item);
        lRecyclerView = (LRecyclerView) findViewById(R.id.list);

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

        lRecyclerView.setArrowImageView(R.drawable.ic_pulltorefresh_arrow);
        lRecyclerView.setRefreshProgressStyle(ProgressStyle.LineSpinFadeLoader);
        lRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        lRecyclerView.setPullRefreshEnabled(true);
        lRecyclerView.setLoadMoreEnabled(true);

        lRecyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                dataAdapter.clear();
                lRecyclerViewAdapter.notifyDataSetChanged();
                mCurrentCounter = 0;
                requestData();
            }
        });

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

//        lRecyclerView.refresh();

    }

    private void requestData() {
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    Thread.sleep(1000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //模拟一下网络请求失败的情况
                if(NetworkUtils.isNetAvailable(getApplicationContext())) {
                    mHandler.sendEmptyMessage(-1);
                } else {
                    mHandler.sendEmptyMessage(-1);
                }
            }
        }.start();
    }

    private class PreviewHandler extends Handler {

        private WeakReference<ListviewItemActivity> ref;

        PreviewHandler(ListviewItemActivity activity) {
            ref = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            final ListviewItemActivity activity = ref.get();
            if (activity == null || activity.isFinishing()) {
                return;
            }
            switch (msg.what) {

                case -1:

                    int currentSize = dataAdapter.getItemCount();

                    //模拟组装10个数据
                    ArrayList<ItemModel> newList = new ArrayList<>();
                    for (int i = 0; i < 20; i++) {
                        if (newList.size() + currentSize >= TOTAL_COUNTER) {
                            break;
                        }

                        ItemModel item = new ItemModel();
                        item.id = currentSize + i;
                        item.title = "item" + (item.id);

                        newList.add(item);
                    }

                    activity.addItems(newList);

                    activity.lRecyclerView.refreshComplete(REQUEST_COUNT);

                    break;
                case -3:
                    activity.lRecyclerView.refreshComplete(REQUEST_COUNT);
                    activity.notifyDataSetChanged();
                    activity.lRecyclerView.setOnNetWorkErrorListener(new OnNetWorkErrorListener() {
                        @Override
                        public void reload() {
                            requestData();
                        }
                    });

                    break;
                default:
                    break;
            }
        }

    }

    private void notifyDataSetChanged() {
        lRecyclerViewAdapter.notifyDataSetChanged();
    }

    private void addItems(ArrayList<ItemModel> newList) {
        dataAdapter.addAll(newList);
        mCurrentCounter += newList.size();
    }
    }