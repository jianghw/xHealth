package com.youyou.zllibrary.util;


import android.app.Activity;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.youyou.zllibrary.AnimUtil.TranslationAnim;
import com.youyou.zllibrary.BuildConfig;

import java.util.List;

import butterknife.ButterKnife;

/**
 * 创建时间: 2015/12/2
 * 创建人:   张磊
 * 描述:
 * 修订时间:
 */
public class CommonFragment extends Fragment implements CommonUtilInterface {
    private SpUtil spUtil;
    protected String Result = "Result";
    protected String Data = "Data";

    @Override
    public void debugShowToast(CharSequence message) {
        if (BuildConfig.DEBUG) {
            showToast(message);
        }
    }

    /**
     * 获取初始化数据
     */
    protected void getData() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void showToast(CharSequence message) {
        if (getContext() != null) {
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 向sharepreference中存放数据,只可以存放int,flaot,String类型的数据
     */
    @Override
    public <T> void put(String key, T value) {
        if (spUtil == null) {
            spUtil = SpUtil.getInstance(getContext());
        }
        spUtil.put(key, value);
    }

    /**
     * 从sharepreference中取出数据,只可以取出int,flaot,String类型的数据
     */
    @Override
    public <T> T get(String key, Class<T> tClass) throws IllegalAccessException, IllegalArgumentException {
        if (spUtil == null) {
            spUtil = SpUtil.getInstance(getContext());
        }
        return spUtil.get(key, tClass);
    }

    @Override
    public void setBack(int id) {

    }

    @Override
    public void finishCur() {

    }

    @Override
    public void init() {

    }

    /**
     * @param className 跳转到这个Activity
     */
    protected void skipTo(Class<? extends Activity> className) {
        TranslationAnim.zlStartActivity(getActivity(), className, null);
    }


    /**
     * @param className 跳转到这个Activity
     */
    protected void skipTo(Class<? extends Activity> className, Bundle bundle) {
        TranslationAnim.zlStartActivity(getActivity(), className, bundle);
    }

    protected void skipToForResult(Class<? extends Activity> className, Bundle bundle, int requestCode) {
        TranslationAnim.zlStartActivityForResult(getActivity(), className, bundle, requestCode);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    Snackbar snackbar;

    @Override
    public void showSnackBar(String mes, int time) {
        if (snackbar == null) {
            snackbar = Snackbar.make(getView(), mes, time);
        } else {
            snackbar.setText(mes);
        }
        snackbar.show();
    }

    @Override
    public void showSnackBar(String mes) {
        showSnackBar(mes, Snackbar.LENGTH_SHORT);
    }

    @Override
    public void hideSoftInput() {
        if (getActivity().getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        }
    }

    @Override
    public void showValidationMessage(List<ValidationError> errors) {

    }
}
