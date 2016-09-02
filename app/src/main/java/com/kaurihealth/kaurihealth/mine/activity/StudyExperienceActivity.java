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
public class StudyExperienceActivity extends CommonActivity {
    @Bind(R.id.edt_name)
    EditText edt_name;
    private LoadingUtil loadingUtil;
    private String workingExperienceOne;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_study_experience);
        ButterKnife.bind(this);
        init();
        ExitApplication.getInstance().addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void init() {
        super.init();
        loadingUtil = LoadingUtil.getInstance(StudyExperienceActivity.this);
        setBack(R.id.iv_back);
        Bundle data = getBundle();
        workingExperienceOne = data.getString("workingExperience");
        edt_name.setText(workingExperienceOne);
    }

    /**
     * 保存个人信息
     */
    @OnClick(R.id.tv_operate)
    public void restore() {
        final String workingExperience = edt_name.getText().toString().trim();
        Intent data = getIntent();
        Bundle bundle = data.getExtras();
        bundle.putString("workingExperience", workingExperience);
        data.putExtras(bundle);
        setResult(RESULT_OK, data);
        if (!(edt_name.getText().toString().equals(workingExperienceOne))) {
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
