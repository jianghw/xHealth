package com.kaurihealth.kaurihealth.base_v;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bugtags.library.Bugtags;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.utilslib.dialog.DialogUtils;
import com.youyou.zllibrary.AnimUtil.TranslationAnim;

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

        setContentView(getActivityLayoutID());
        ButterKnife.bind(this);
        initPresenterAndData(savedInstanceState);
        initDelayedView();
    }

    protected abstract int getActivityLayoutID();

    protected abstract void initPresenterAndData(Bundle savedInstanceState);

    protected abstract void initDelayedView();


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
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        Bugtags.onDispatchTouchEvent(this, motionEvent);
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
    }

    //将Activity 和 Fragment 的关闭都封装在了这里面
    public void initBackBtn(int id) {
        findViewById(id).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeFragmentOrAty();
            }
        });
    }

    public void initNewBackBtn(String title) {
        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishCur();
            }
        });
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
     *
     * @param textView
     */
    public void clearTextView(TextView textView) {
        if (textView != null) {
            textView.setText("");
        }
    }

    /***
     * 设置textView控件的值
     *
     * @param value
     * @param textView
     */
    protected void setText(String value, TextView textView) {
        if (textView == null) return;
        if (value.trim().equalsIgnoreCase("null") || value.trim().length() == 0) {
            textView.setText("暂无");
        } else {
            textView.setText(value);
        }
    }

    /**
     * 获取textview的数值
     *
     * @param textView
     * @return
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
     * 设置当点击某个控件的时候,结束当前activity
     *
     * @param id 返回控件的id
     */
    public void setBack(int id) {
        findViewById(id).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishCur();
            }
        });
    }

    /**
     * 得到传过来的Bundle
     * @return
     */
    public Bundle getBundle() {
        return getIntent().getExtras();
    }
}
