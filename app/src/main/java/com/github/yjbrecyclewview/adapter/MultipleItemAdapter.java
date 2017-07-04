package com.github.yjbrecyclewview.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.yjbrecyclewview.R;
import com.github.yjbrecyclewview.bean.MultiItemInfo;

/**
 * Created by cnsunrun on 2017-07-04.
 */

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
