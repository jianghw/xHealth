package com.youyou.zllibrary.util;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.bugtags.library.Bugtags;
import com.mobsandgeeks.saripaar.ValidationError;
import com.youyou.zllibrary.AnimUtil.TranslationAnim;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * 创建时间: 2015/12/2
 * 创建人:   张磊
 * 描述:
 * 公用activity
 * 修订时间:
 */
public class CommonActivity extends AppCompatActivity implements CommonUtilInterface {
    private SpUtil spUtil;
    private Snackbar snackbar;

    public void clearTextView(TextView textView) {
        if (textView != null) {
            textView.setText("");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected long getTime(String time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try {
            return format.parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return -1;
        }
    }

    protected String getTvValue(TextView textView) {
        return textView.getText().toString().trim();
    }

    /**
     * 设置当点击某个控件的时候,结束当前activity
     *
     * @param id 返回控件的id
     */
    @Override
    public void setBack(int id) {
        findViewById(id).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishCur();
            }
        });
    }

    protected void getData() {
    }

    @Override
    public void debugShowToast(CharSequence message) {
        if (Config.DEBUG) {
            showToast(message);
        }
    }

    protected boolean isEmpty(String content) {
        return TextUtils.isEmpty(content);
    }

    /***
     * 设置textview控件的值
     *
     * @param value
     * @param textView
     */
    protected void setText(String value, TextView textView) {
        if (textView == null) {
            textView.setText("");
            return;
        }
        if (value.trim().equals("null") || value == null || value.trim().length() == 0) {
            textView.setText("");
        } else {
            textView.setText(value);
        }
    }

    public Bundle getBundle() {
        return getIntent().getExtras();
    }

    /**
     * 结束当前activity
     */

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

    protected void skipToForResult(Intent intent, Bundle bundle, int requestCode) {
        TranslationAnim.zlStartActivityForResult(this, intent, bundle, requestCode);
    }

    /***
     * @param message Toast.makeText封装
     */
    @Override
    public void showToast(CharSequence message) {
        if (TextUtils.isEmpty(message)) {
            return;
        }
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    /***
     * @param charSequence
     * @param str          Toast.makeText两个参数的封装
     */
    public void showToast(CharSequence charSequence, String str) {
        if (TextUtils.isEmpty(charSequence)) {
            return;
        }
        Toast toast = Toast.makeText(getApplicationContext(), charSequence, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    @Override
    public void init() {
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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            TranslationAnim.zlFinish(this);
        }
        return super.onKeyDown(keyCode, event);
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
        Snackbar.make(getWindow().getDecorView(), mes, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void hideSoftInput() {
        if (getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    @Override
    public void showValidationMessage(List<ValidationError> errors) {
        StringBuilder mes = new StringBuilder();
        for (ValidationError error : errors) {
            String collatedErrorMessage = error.getCollatedErrorMessage(this);
            mes.append(collatedErrorMessage).append("\n");
        }
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("错误提示!")
                .setContentText(mes.toString())
                .show();
    }
}
