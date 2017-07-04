package com.github.yjbrecyclewview.utils;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.github.yjbrecyclewview.bean.BannerInfo;
import com.youth.banner.loader.ImageLoader;

/**
 * Created by cnsunrun on 2017-07-04.
 */

public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        BannerInfo res = ((BannerInfo) path);
        Glide.with(context).load(res.url).into(imageView);
    }
}
