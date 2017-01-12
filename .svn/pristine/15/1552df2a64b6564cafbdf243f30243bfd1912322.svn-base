package com.kaurihealth.kaurihealth.manager_v.loading;

import android.content.Context;
import android.view.ViewGroup;

import com.kaurihealth.utilslib.controller.IViewFactory;

/**
 * Created by jianghw on 2016/12/16.
 * <p/>
 * Describe: 加载失败页面
 */

public class LoadingErrorFactory implements IViewFactory<String> {

    private final Context mContext;
    /**
     * 加载失败模块
     */
    private LoadingErrorView mLoadingErrorView;

    public LoadingErrorFactory(Context context) {
        this.mContext = context;
    }

    @Override
    public void createIncludeViews() {
        mLoadingErrorView = new LoadingErrorView(mContext);
    }

    @Override
    public void attachRootView(ViewGroup root) {
        mLoadingErrorView.attachRoot(root);
    }

    @Override
    public void fillNewestData(String beanData,int position) {
        mLoadingErrorView.fillData(beanData);
    }

    @Override
    public void unbindView() {
        mLoadingErrorView.unbindView();
    }

    @Override
    public void showLayout() {
        mLoadingErrorView.showLayout();
    }

    @Override
    public void hiddenLayout() {
        mLoadingErrorView.hiddenLayout();
    }

}
