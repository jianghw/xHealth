package com.kaurihealth.utilslib.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by jianghw on 2016/12/9.
 * <p/>
 * Describe:UI构建器，组件化是一种运用
 */

public abstract class AbstractViewController<T> {
    protected T mBeanData;
    protected Context mContext;

    public AbstractViewController(Context context) {
        this.mContext = context;
    }

    public void attachRoot(ViewGroup root) {
        int resLayoutId = getResLayoutId();
        if (resLayoutId <= 0) {
            throw new IllegalStateException("Please check your layout id in getResLayoutId() method");
        }
        View mView = LayoutInflater.from(mContext).inflate(resLayoutId, root, true);
        onCreateView(mView);
    }

    public void attachRoot(ViewGroup root, boolean attachToRoot) {
        int resLayoutId = getResLayoutId();
        if (resLayoutId <= 0) {
            throw new IllegalStateException("Please check your layout id in getResLayoutId() method");
        }
        View mView = LayoutInflater.from(mContext).inflate(resLayoutId, root, attachToRoot);
        onCreateView(mView);
    }

    public void fillData(T beanData) {
        this.mBeanData = beanData;
        if (beanData != null) {
            bindViewData(beanData);
        } else {
            defaultViewData();
        }
    }

    protected abstract int getResLayoutId();

    protected abstract void onCreateView(View view);

    protected abstract void bindViewData(T data);

    protected abstract void unbindView();

    protected abstract void defaultViewData();

    protected abstract void showLayout();

    protected abstract void hiddenLayout();
}
