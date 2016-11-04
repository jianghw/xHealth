package com.kaurihealth.kaurihealth.base_v;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.utilslib.ActivityManager;
import com.kaurihealth.utilslib.TranslationAnim;
import com.kaurihealth.utilslib.dialog.DialogUtils;
import com.rey.material.widget.CheckBox;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Field;

import butterknife.ButterKnife;

/**
 * Created by jianghw on 2016/8/5.
 * <p>
 * 描述：activity的基础父类
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityManager.getInstance().addActivity(this);
        setContentView(getActivityLayoutID());
        ButterKnife.bind(this);
        initPresenterAndView(savedInstanceState);
        initDelayedData();
    }

    protected abstract int getActivityLayoutID();

    protected abstract void initPresenterAndView(Bundle savedInstanceState);

    protected abstract void initDelayedData();


    @Override
    protected void onResume() {
        super.onResume();
        //Bugtags.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //Bugtags.onPause(this);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
             // Bugtags.onDispatchTouchEvent(this, motionEvent);
        return super.dispatchTouchEvent(motionEvent);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        ActivityManager.getInstance().finishActivity(this);
    }

    //将Activity 和 Fragment 的关闭都封装在了这里面
    public void initBackBtn(int id) {
        findViewById(id).setOnClickListener(v -> removeFragmentOrAty());
    }

    public void initNewBackBtn(String title) {
        findViewById(R.id.iv_back).setOnClickListener(view -> finishCur());
        TextView tvTitle = (TextView) findViewById(R.id.tv_title);
        tvTitle.setText(title);
    }

    /**
     * 手动回退
     */
    protected void removeFragmentOrAty() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            finishCur();
        }
    }

    /**
     * 隐藏输入
     */
    public void hideSoftInput() {
        if (getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode && getSupportFragmentManager().getBackStackEntryCount() == 1) {
            finishCur();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 跳转动画
     */
    public void finishCur() {
        TranslationAnim.zlFinish(this);
    }


    /**
     * 清空textView数值
     */
    public void clearTextView(TextView textView) {
        if (textView != null) {
            textView.setText("");
        }
    }

    /***
     * 设置textView控件的值
     */
    protected void setText(String value, TextView textView) {
        if (textView == null) return;
        if (value.trim().equalsIgnoreCase("null") || value.trim().length() == 0) {
            textView.setText("暂无");
        } else {
            textView.setText(value);
        }
    }

    protected String setDefaultText(String value) {
        return value != null ? value.equalsIgnoreCase("null") ? "暂无" : value.length() == 0 ? "暂无" : value : "暂无";
    }

    /**
     * 清除数据
     *
     * @param textView
     */
    public void clearTextview(TextView textView) {
        if (textView != null) {
            textView.setText("");
        }
    }

    /**
     * 获取textView的数值
     */
    protected String getTvValue(TextView textView) {
        return textView.getText().toString().trim();
    }

    public void dataInteractionDialog() {
        DialogUtils.sweetProgressDialog(this);
    }

    public void dismissInteractionDialog() {
        DialogUtils.dismissSweetProgress();
    }

    public void displayErrorDialog(String error) {
        DialogUtils.changeAlertTypeWarning(this, error);
    }

    /**
     * @param className 跳转到这个Activity
     */
    protected void skipTo(Class<? extends Activity> className) {
        TranslationAnim.zlStartActivity(this, className, null);
    }

    protected void skipToBundle(Class<? extends Activity> className, Bundle bundle) {
        TranslationAnim.zlStartActivity(this, className, bundle);
    }

    protected void skipToForResult(Class<? extends Activity> className, Bundle bundle, int requestCode) {
        TranslationAnim.zlStartActivityForResult(this, className, bundle, requestCode);
    }

    /***
     * @param message Toast.makeText封装
     */
    public void showToast(CharSequence message) {
        if (TextUtils.isEmpty(message)) return;
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 得到传过来的Bundle
     *
     * @return
     */
    public Bundle getBundle() {
        return getIntent().getExtras();
    }

    /**
     * 判断是否为空
     *
     * @param content
     * @return
     */
    protected boolean isEmpty(String content) {
        return TextUtils.isEmpty(content);
    }

    /**
     * 设置所有控件可操作
     *
     * @param enable
     * @param object
     */
    protected void setAllViewsEnable(boolean enable, Object object) {
        Class<?> aClass = object.getClass();
        Field[] declaredFields = aClass.getDeclaredFields();
        try {
            for (Field field : declaredFields) {
                field.setAccessible(true);
                Object mObject = field.get(object);
                if (mObject instanceof EditText) {
                    EditText editText = (EditText) mObject;
                    editText.setFocusableInTouchMode(enable);
                    editText.setFocusable(enable);
                    if (enable) editText.requestFocus();
                    editText.setBackground(enable ?
                            getApplicationContext().getResources().getDrawable(R.drawable.bg_edt_login) : null);
                } else if (mObject instanceof ImageView) {
                    ((ImageView) mObject).setEnabled(enable);
                } else if (mObject instanceof com.rey.material.widget.CheckBox) {
                    CheckBox checkBox = (CheckBox) mObject;
                    checkBox.setEnabled(enable);
                } else if (mObject instanceof TextView) {
                    TextView textView = (TextView) mObject;
                    textView.setEnabled(enable);
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 移除指定的粘性订阅事件
     *
     * @param eventType class的字节码，例如：String.class
     */
    protected static <T> void removeStickyEvent(Class<T> eventType) {
        T stickyEvent = EventBus.getDefault().getStickyEvent(eventType);
        if (stickyEvent != null) {
            EventBus.getDefault().removeStickyEvent((T) stickyEvent);
        }
    }

    protected boolean filterException(Exception e) {
        if (e != null) {
            showToast(e.getMessage());
            return false;
        } else {
            return true;
        }
    }

    /**
     * 设置光标移动到末尾位置
     * @param selection
     */
    protected void setSelection(EditText selection){
        if (!(selection.getText().equals(""))){
            selection.setSelection(selection.getText().length());
        }
    }

}
