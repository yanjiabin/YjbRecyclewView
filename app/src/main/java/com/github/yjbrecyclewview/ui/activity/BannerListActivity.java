package com.github.yjbrecyclewview.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.github.yjbrecycleview.library.ItemDecoration.DividerDecoration;
import com.github.yjbrecycleview.library.interfaces.OnLoadMoreListener;
import com.github.yjbrecycleview.library.interfaces.OnRefreshListener;
import com.github.yjbrecycleview.library.recyclerview.LRecyclerView;
import com.github.yjbrecycleview.library.recyclerview.LRecyclerViewAdapter;
import com.github.yjbrecyclewview.R;
import com.github.yjbrecyclewview.adapter.DataAdapter;
import com.github.yjbrecyclewview.bean.BannerInfo;
import com.github.yjbrecyclewview.bean.ItemModel;
import com.github.yjbrecyclewview.utils.GlideImageLoader;
import com.youth.banner.Banner;

import java.util.ArrayList;

public class BannerListActivity extends AppCompatActivity {

    private LRecyclerView lRecyclerView;
    private DataAdapter dataAdapter;
    private LRecyclerViewAdapter lRecyclerViewAdapter;
    public static final int TOTAL_MAX = 100;
    public static final int ONEPAGE_NUM = 10;
    public   int mCurrentCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner_list);


        lRecyclerView = (LRecyclerView) findViewById(R.id.banner_rv);

        lRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        DividerDecoration dividerDecoration = new DividerDecoration.Builder(this)
                .setHeight(R.dimen.DEFAULT_HEIGHT)
                .setLeftPadding(R.dimen.PADDING)
                .setRightPadding(R.dimen.PADDING)
                .setColorResource(R.color.gray2)
                .build();

        lRecyclerView.addItemDecoration(dividerDecoration);
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
//                lRecyclerViewAdapter.notifyDataSetChanged();
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

    }

    /**
     *  请求数据
     */
    private void requestData() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //模拟组装10个数据
                ArrayList<ItemModel> newList = new ArrayList<>();
                int currenNum = dataAdapter.getItemCount();
                for (int i = 0; i < ONEPAGE_NUM; i++) {
                    if (currenNum + ONEPAGE_NUM > TOTAL_MAX) {
                        return;
                    }
                    ItemModel itemModel = new ItemModel();
                    itemModel.id = mCurrentCounter + i;
                    itemModel.title = "item" + (itemModel.id);
                    newList.add(itemModel);
                }
                addItem(newList);


            }
        }.start();
    }

    private void addItem(final ArrayList<ItemModel> newList) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dataAdapter.addAll( newList);
                mCurrentCounter += newList.size();
                lRecyclerView.refreshComplete(ONEPAGE_NUM);
            }
        });

    }
}
