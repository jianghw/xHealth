package com.kaurihealth.mvplib.base_p;

import android.support.annotation.UiThread;

/**
 * Created by jianghw on 2016/8/4.
 * <p>
 * 描述：
 */
public interface IMvpView {
    /**
     * 数据交互 提示
     */
    @UiThread
    void dataInteractionDialog();

    /**
     * 取消 数据交互 提示
     */
    @UiThread
    void dismissInteractionDialog();

    /**
     * 出错提示
     *
     * @param error
     */
    @UiThread
    void displayErrorDialog(String error);


    /**
     * 显示Toast
     *
     * @param message
     */
    @UiThread
    void showToast(CharSequence message);

    /**
     * 页面跳转
     *
     * @param className
     */
    @UiThread
    void switchPageUI(String className);

}
