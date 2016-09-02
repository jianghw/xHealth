package com.youyou.zllibrary.util;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.bugtags.library.Bugtags;
import com.mobsandgeeks.saripaar.ValidationError;
import com.youyou.zllibrary.AnimUtil.TranslationAnim;
import com.youyou.zllibrary.BuildConfig;

import java.util.List;

/**
 * 创建时间: 2015/12/7
 * 创建人:   张磊
 * 描述:
 * 修订时间:
 */
public class CommonFragmentActivity extends AppCompatActivity implements CommonUtilInterface {
    private SpUtil spUtil;

    public Bundle getBundle() {
        return getIntent().getExtras();
    }

    @Override
    public void showToast(CharSequence message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 在sharepreference中存放数据,只可以存放String,int,float数据
     *
     * @param key   键
     * @param value 值
     */

    @Override
    public <T> void put(String key, T value) {
        if (spUtil == null) {
            spUtil = SpUtil.getInstance(getApplicationContext());
        }
        spUtil.put(key, value);
    }

    /**
     * 在sharepreference中取值,只可以取tring,int,float数据
     *
     * @param key    键
     * @param tClass 值  取的值得类型
     */
    @Override
    public <T> T get(String key, Class<T> tClass) throws IllegalAccessException, IllegalArgumentException {
        if (spUtil == null) {
            spUtil = SpUtil.getInstance(getApplicationContext());
        }
        return spUtil.get(key, tClass);
    }

    @Override
    public void init() {

    }

    @Override
    public void debugShowToast(CharSequence message) {
        if (BuildConfig.DEBUG) {
            showToast(message);
        }
    }

    @Override
    public void setBack(int id) {
        findViewById(id).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishCur();
            }
        });
    }

    @Override
    public void finishCur() {
        TranslationAnim.zlFinish(this);
    }

    /**
     * @param className 跳转到这个Activity
     */
    protected void skipTo(Class<? extends Activity> className) {
        TranslationAnim.zlStartActivity(this, className, null);
    }

    /**
     * @param className 跳转到这个Activity
     */
    protected void skipTo(Class<? extends Activity> className, Bundle bundle) {
        TranslationAnim.zlStartActivity(this, className, bundle);
    }

    protected void skipToForResult(Class<? extends Activity> className, Bundle bundle, int requestCode) {
        TranslationAnim.zlStartActivityForResult(this, className, bundle, requestCode);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Bugtags.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Bugtags.onPause(this);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Bugtags.onDispatchTouchEvent(this, ev);
        return super.dispatchTouchEvent(ev);
    }

    Snackbar snackbar;

    @Override
    public void showSnackBar(String mes, int time) {
        if (snackbar == null) {
            snackbar = Snackbar.make(getWindow().getDecorView(), mes, time);
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
        if(getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    @Override
    public void showValidationMessage(List<ValidationError> errors) {

    }
}
