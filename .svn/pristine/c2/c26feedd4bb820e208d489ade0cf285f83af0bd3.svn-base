package com.kaurihealth.kaurihealth.base_v;

import android.support.v4.app.Fragment;

/**
 * Created by jianghw on 2016/8/5.
 * <p/>
 * 描述：的基础父类
 */
public abstract class LazyFragment extends Fragment {
    /**
     * 是否可见
     */
    protected boolean isVisible;

    /**
     * 标志位，标志Fragment view已经初始化完成。
     */
    protected boolean isPrepared = false;

    /**
     * 实现Fragment数据的缓加载,比onCreateView()先调用
     *
     * @param isVisibleToUser 设置一个提示,代表的是Fragment在当前是否处于对用户的可见状态
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            loadingData();
        } else {
            isVisible = false;
            unVisible();
        }
    }


    protected abstract void loadingData();

    private void unVisible() {
//TODO
    }
}
