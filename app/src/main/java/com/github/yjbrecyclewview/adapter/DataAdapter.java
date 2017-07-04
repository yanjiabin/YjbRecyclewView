package com.github.yjbrecyclewview.adapter;

import android.content.Context;
import android.widget.TextView;

import com.github.yjbrecyclewview.R;
import com.github.yjbrecyclewview.bean.ItemModel;

/**
 * Created by cnsunrun on 2017-07-04.
 */

public class DataAdapter extends ListBaseAdapter<ItemModel>{

    public DataAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.sample_item_text;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        ItemModel item = mDataList.get(position);

        TextView titleText = holder.getView(R.id.info_text);
        titleText.setText(item.title);
    }

}
