package com.kaurihealth.kaurihealth.mine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.commonlibrary.widget.util.LoadingUtil;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.util.TextWatchClearError;
import com.youyou.zllibrary.util.CommonActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 张磊 on 2016/6/14.
 * 介绍：
 */
public class GraduateSchoolActivity extends CommonActivity {
    @Bind(R.id.tv_operate)
    TextView tvOperate;
    @Bind(R.id.edtGraduateSchool)
    EditText edtGraduateSchool;

    @Bind(R.id.ivDelete)
    ImageView ivDelete;
    private Bundle bundle;
    private LoadingUtil loadingUtil;
    private String educationHistory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_graduate_school);
        ButterKnife.bind(this);
        init();
    }

    @Override
    public void init() {
        super.init();
        loadingUtil = LoadingUtil.getInstance(GraduateSchoolActivity.this);
        setBack(R.id.iv_back);
        bundle = getBundle();
        educationHistory = bundle.getString("educationHistory", null);
        edtGraduateSchool.setText(educationHistory);
        tvOperate.setOnClickListener(onClickListener);
        edtGraduateSchool.addTextChangedListener(new TextWatchClearError(edtGraduateSchool,ivDelete));
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBack();
        }
    };

    private void onBack() {
        bundle.putString("educationHistory", edtGraduateSchool.getText().toString().trim());
        Intent intent = getIntent();
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        if (edtGraduateSchool.getText().toString().equals(educationHistory)){
            finishCur();
        }else {
            loadingUtil.show();
            loadingUtil.dismiss("修改成功", new LoadingUtil.Success() {
                @Override
                public void dismiss() {
                    finishCur();
                }
            });
        }
    }
}
