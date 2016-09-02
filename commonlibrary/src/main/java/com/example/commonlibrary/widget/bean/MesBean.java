package com.example.commonlibrary.widget.bean;

import java.io.Serializable;

/**
 * Created by 张磊 on 2016/5/24.
 * 介绍：
 */
public class MesBean implements Serializable{
    public static final int update=1;
    int tag;

    public MesBean(int tag) {
        this.tag = tag;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }
}
