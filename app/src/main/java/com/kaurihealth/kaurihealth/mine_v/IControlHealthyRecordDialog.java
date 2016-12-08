package com.kaurihealth.kaurihealth.mine_v;

import java.util.List;

/**
 * Created by 张磊 on 2016/6/15.
 * 介绍：
 */
public interface IControlHealthyRecordDialog<T> {
    /**
     * 设置dialog中可以选择的内容
     *
     * @param list
     */
    void setData(List<T> list);

    /**
     * 设置dialog中可以选择的内容
     *
     * @param data
     */
    void setData(T[] data);

    /**
     * 设置点击数据源的触发事件
     *
     * @param selectListener
     */
    void setOnSelectListener(IHealthyRecordSelect selectListener);

    /**
     * 设置dialog的标题
     *
     * @param title
     */
    void setTitle(String title);

    /**
     * 触发dialog，如果dialog之前是显示的，将隐藏；否则，将显示
     */
    void touch();
}
