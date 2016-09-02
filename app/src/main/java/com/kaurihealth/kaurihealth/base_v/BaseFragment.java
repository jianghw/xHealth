package com.kaurihealth.kaurihealth.base_v;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.utilslib.dialog.DialogUtils;
import com.youyou.zllibrary.AnimUtil.TranslationAnim;

import butterknife.ButterKnife;

/**
 * Created by jianghw on 2016/8/5.
 * <p/>
 * 描述：的基础父类
 */
public abstract class BaseFragment extends LazyFragment {


    protected BaseActivity mActivity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = (BaseActivity) activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getFragmentLayoutID(), container, false);
        ButterKnife.bind(this, view);

        initPresenterAndData();
        initDelayedView();

        isPrepared = true;
        loadingData();
        return view;
    }

    protected abstract int getFragmentLayoutID();

    @Override
    protected void loadingData() {
        /**
         * 可见时isVisible=true;view加载好时isPrepared=true;
         */
        if (!isPrepared || !isVisible) return;
        lazyLoadingData();
    }

    protected abstract void initPresenterAndData();

    protected abstract void initDelayedView();

    protected abstract void lazyLoadingData();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    /**
     * 刷新数据
     *
     * @param flag
     */
    public void loadingIndicator(final boolean flag) {
        if (getView() == null) return;
        final SwipeRefreshLayout swipeRefreshLayout =
                (SwipeRefreshLayout) getView().findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(flag);
            }
        });
    }

    public void dataInteractionDialog() {
        DialogUtils.sweetProgressDialog(mActivity);
    }

    public void dismissInteractionDialog() {
        DialogUtils.dismissSweetProgress();
    }

    public void displayErrorDialog(String error) {
        DialogUtils.changeAlertTypeWarning(mActivity, error);
    }

    /**
     * @param className 跳转到这个Activity
     */
    protected void skipTo(Class<? extends Activity> className) {
        TranslationAnim.zlStartActivity(mActivity, className, null);
    }

    protected void skipToForResult(Class<? extends Activity> className, Bundle bundle, int requestCode) {
        TranslationAnim.zlStartActivityForResult(mActivity, className, bundle, requestCode);
    }

    /***
     * @param message Toast.makeText封装
     */
    public void showToast(CharSequence message) {
        if (TextUtils.isEmpty(message)) return;
        Toast.makeText(mActivity.getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}
