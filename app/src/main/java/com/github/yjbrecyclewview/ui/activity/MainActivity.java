package com.github.yjbrecyclewview.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.yjbrecycleview.library.ItemDecoration.DividerDecoration;
import com.github.yjbrecycleview.library.recyclerview.LRecyclerView;
import com.github.yjbrecycleview.library.recyclerview.LRecyclerViewAdapter;
import com.github.yjbrecyclewview.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private LRecyclerView mLRecyclewvView;
    private Class<?>[] classes = {ListviewItemActivity.class,BannerListActivity.class,MulItemLinearLayoutActivity.class};
    private String[] mTitles ={"普通的listview的item","带有banner头部的Recycleview","MulItemLinearLayoutActivity"};
    private DataAdapter dataAdapter;
    private LRecyclerViewAdapter mLRecyclewViewAdapter;
    private ArrayList<ListItem> mDataList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLRecyclewvView = (LRecyclerView) findViewById(R.id.lv);
        mLRecyclewvView.setLayoutManager(new LinearLayoutManager(this));

        DividerDecoration divide = new DividerDecoration.Builder(this)
                .setHeight(R.dimen.DEFAULT_HEIGHT)
                .setLeftPadding(R.dimen.PADDING)
                .setLeftPadding(R.dimen.PADDING)
                .setColorResource(R.color.gray2)
                .build();
        mLRecyclewvView.addItemDecoration(divide);
        initHeadData();
        dataAdapter = new DataAdapter(this);
        dataAdapter.setData(mDataList);
        mLRecyclewViewAdapter = new LRecyclerViewAdapter(dataAdapter);
        mLRecyclewvView.setAdapter(mLRecyclewViewAdapter);




//        //添加头
//        View headContainer = View.inflate(this, R.layout.banner_head_layout, null);
//
//        Banner banner = (Banner) headContainer.findViewById(R.id.banner);
//        //设置图片加载器
//        banner.setImageLoader(new GlideImageLoader());
//        //设置图片集合
//        banner.setImages(images);
//        //banner设置方法全部调用完毕时最后调用
//        banner.start();

    }

    private void initHeadData() {
        mDataList = new ArrayList<>();
        for (int i = 0; i < classes.length; i++) {

            ListItem item = new ListItem();
            item.title = mTitles[i];
            item.clazz = classes[i];
            mDataList.add(item);
        }

    }


    private class DataAdapter extends RecyclerView.Adapter {

        private LayoutInflater mLayoutInflater;
        private ArrayList<ListItem> mDataList = new ArrayList<>();

        public DataAdapter(Context context) {
            mLayoutInflater = LayoutInflater.from(context);
        }

        public void setData(ArrayList<ListItem> list) {
            this.mDataList = list;
            notifyDataSetChanged();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(mLayoutInflater.inflate(R.layout.sample_item_text, parent, false));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

            ListItem listItem = mDataList.get(position);

            ViewHolder viewHolder = (ViewHolder) holder;
            viewHolder.textView.setText(listItem.title);

            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ListItem listItem = mDataList.get(position);
                    startActivity(new Intent(MainActivity.this, listItem.clazz));
                }
            });
        }

        @Override
        public int getItemCount() {
            return mDataList.size();
        }

        public List<ListItem> getDataList() {
            return mDataList;
        }

        private class ViewHolder extends RecyclerView.ViewHolder {

            private TextView textView;

            public ViewHolder(View itemView) {
                super(itemView);
                textView = (TextView) itemView.findViewById(R.id.info_text);
            }
        }
    }

    private class ListItem {
        String title;
        Class<?>  clazz;
    }
}
