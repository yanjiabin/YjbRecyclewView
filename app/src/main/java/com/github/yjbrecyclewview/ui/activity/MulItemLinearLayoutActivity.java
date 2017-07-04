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
import com.github.yjbrecyclewview.adapter.MultipleItemAdapter;
import com.github.yjbrecyclewview.bean.MultiItemInfo;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class MulItemLinearLayoutActivity extends AppCompatActivity {

    private LRecyclerView mRecyclerView;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private MultipleItemAdapter mMultipleItemAdapter;
    /**服务器端一共多少条数据*/
    private static final int TOTAL_COUNTER = 64;

    /**每一页展示多少条数据*/
    private static final int REQUEST_COUNT = 10;

    /**已经获取到多少条数据了*/
    private static int mCurrentCounter = 0;

    private PreviewHandler mHandler = new PreviewHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mul_item_linear_layout);
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


        mRecyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mMultipleItemAdapter.clear();
                mLRecyclerViewAdapter.notifyDataSetChanged();//fix bug:crapped or attached views may not be recycled. isScrap:false isAttached:true
                mCurrentCounter = 0;
                requestData();
            }
        });

        mRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (mCurrentCounter < TOTAL_COUNTER) {
                    // loading more
                    requestData();
                } else {
                    //the end
                    mRecyclerView.setNoMore(true);
                }
            }
        });
    }

    /**
     * 模拟请求网络
     */
    private void requestData() {
        new Thread() {

            @Override
            public void run() {
                super.run();

                try {
                    Thread.sleep(800);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                    mHandler.sendEmptyMessage(-1);
            }
        }.start();
    }

    private class PreviewHandler extends Handler {

        private WeakReference<MulItemLinearLayoutActivity> ref;

        PreviewHandler(MulItemLinearLayoutActivity activity) {
            ref = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            final MulItemLinearLayoutActivity activity = ref.get();
            if (activity == null || activity.isFinishing()) {
                return;
            }
            switch (msg.what) {

                case -1:

                    int currentSize = activity.mMultipleItemAdapter.getItemCount();

                    //模拟组装10个数据
                    ArrayList<MultiItemInfo> newList = new ArrayList<>();
                    for (int i = 0; i < 10; i++) {
                        if (newList.size() + currentSize >= TOTAL_COUNTER) {
                            break;
                        }

                        MultiItemInfo item ;
                        if(i == 2){
                            item = new MultiItemInfo(MultiItemInfo.IMG);
                        }else {
                            item = new MultiItemInfo(MultiItemInfo.TEXT);
                        }
                        item.setTitle("item"+(currentSize+i));

                        newList.add(item);
                    }


                    activity.addItems(newList);

                    activity.mRecyclerView.refreshComplete(REQUEST_COUNT);
                    activity.notifyDataSetChanged();
                    break;
                case -2:
                    activity.notifyDataSetChanged();
                    break;
                case -3:
                    activity.mRecyclerView.refreshComplete(REQUEST_COUNT);
                    activity.notifyDataSetChanged();
                    activity.mRecyclerView.setOnNetWorkErrorListener(new OnNetWorkErrorListener() {
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
        mLRecyclerViewAdapter.notifyDataSetChanged();
    }

    private void addItems(ArrayList<MultiItemInfo> list) {

        mMultipleItemAdapter.addAll(list);
        mCurrentCounter += list.size();

    }
}
