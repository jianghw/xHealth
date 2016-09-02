package com.kaurihealth.kaurihealth.mine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.example.commonlibrary.widget.util.LoadingUtil;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.util.ExitApplication;
import com.youyou.zllibrary.util.CommonActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * 版权:    张磊
 * 作者:    张磊
 * 描述：
 * 修订日期:
 */

/**
 * 专业特长
 */
public class PracticeFieldActivity extends CommonActivity {

    @Bind(R.id.edt_name)
    EditText edtName;
    private LoadingUtil loadingUtil;
    private String goodatOne;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_practice_field);
        ButterKnife.bind(this);
        init();
        ExitApplication.getInstance().addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        setBack(R.id.iv_back);

    }

    @Override
    public void init() {
        super.init();
        loadingUtil = LoadingUtil.getInstance(PracticeFieldActivity.this);
        setBack(R.id.iv_back);
        //获取专业特长
        goodatOne = getBundle().getString("practiceField");
        edtName.setText(goodatOne);
    }

    /**
     * 保存
     */
    @OnClick(R.id.tv_operate)
    public void restore() {
        final String practiceField = edtName.getText().toString().trim();
        Intent intent = getIntent();
        Bundle bundle=intent.getExtras();
        bundle.putString("practiceField", practiceField);
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        if (!(edtName.getText().toString().equals(goodatOne))) {
            loadingUtil.show();
            loadingUtil.dismiss("修改成功", new LoadingUtil.Success() {
                @Override
                public void dismiss() {
                    finishCur();
                }
            });
        }else {
            finishCur();
        }
    }
}
