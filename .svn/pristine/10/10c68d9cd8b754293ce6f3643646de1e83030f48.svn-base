package com.kaurihealth.kaurihealth.base_v;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

/**
 * Created by jianghw on 2016/8/5.
 * <p/>
 * 描述：嵌套子类的基础父类
 */
public abstract class ChildBaseFragment extends BaseFragment {

    /**
     * 标记是否等待显现
     */
    private boolean waitingShowToUser;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // 如果自己是显示状态，但父Fragment却是隐藏状态，就把自己也改为隐藏状态，并且设置一个等待显示标记
        if (getUserVisibleHint()) {
            Fragment parentFragment = getParentFragment();
            if (parentFragment != null && !parentFragment.getUserVisibleHint()) {
                waitingShowToUser = true;
                super.setUserVisibleHint(false);
            }
        }
    }

    public boolean isWaitingShowToUser() {
        return waitingShowToUser;
    }

    public void setWaitingShowToUser(boolean b) {
        waitingShowToUser = b;
    }

    @Override
    protected void lazyLoadingData() {
        Fragment parentFragment = getParentFragment();
        if (!waitingShowToUser &&parentFragment != null && parentFragment.getUserVisibleHint()) childLazyLoadingData();
    }

    protected abstract void childLazyLoadingData();
}
