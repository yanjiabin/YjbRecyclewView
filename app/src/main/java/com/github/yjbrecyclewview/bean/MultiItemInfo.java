package com.github.yjbrecyclewview.bean;

/**
 * Created by cnsunrun on 2017-07-04.
 */

public class MultiItemInfo implements MultiItemEntity {
    public static final int TEXT = 1;
    public static final int IMG = 2;
    public static final int LIST = 3;
    public static final int TEXT_SPAN_SIZE = 1;
    public static final int IMG_SPAN_SIZE = 3;
    private int itemType;
    private int spanSize;

    public MultiItemInfo(int itemType) {
        this.itemType = itemType;
    }

    public MultiItemInfo(int itemType, int spanSize) {
        this.itemType = itemType;
        this.spanSize = spanSize;
    }

    public int getSpanSize() {
        return spanSize;
    }

    public void setSpanSize(int spanSize) {
        this.spanSize = spanSize;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
