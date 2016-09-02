package com.kaurihealth.kaurihealth.mine_v;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.commonlibrary.widget.util.LoadingUtil;
import com.kaurihealth.kaurihealth.MyApplication;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.kaurihealth.util.TextWatchClearError;
import com.kaurihealth.mvplib.mine_p.EnterGraduateSchoolPresenter;
import com.kaurihealth.mvplib.mine_p.IEnterGraduateSchoolView;

import javax.inject.Inject;

import butterknife.Bind;


/**
 * 我的Fragment-->个人信息-->院校  created by Nick
 */
public class EnterGraduateSchoolActivity extends BaseActivity implements IEnterGraduateSchoolView {
    //"保存" 按钮
    @Bind(R.id.tv_operate)
    TextView tvOperate;
   //"毕业院校"编辑框
    @Bind(R.id.edtGraduateSchool)
    EditText edtGraduateSchool;
    //编辑框"删除"按钮
    @Bind(R.id.ivDelete)
    ImageView ivDelete;
    private Bundle bundle;
    private LoadingUtil loadingUtil;



    @Inject
    EnterGraduateSchoolPresenter<IEnterGraduateSchoolView> mPresenter;
    private LoadingUtil loadingUtil1;
    private String educationHistory;


    @Override
    protected int getActivityLayoutID() {
        return R.layout.activity_enter_graduate_school;
    }

    @Override
    protected void initPresenterAndData(Bundle savedInstanceState) {
        MyApplication.getApp().getComponent().inject(this);
        mPresenter.setPresenter(this);
    }

    @Override
    protected void initDelayedView() {
        initBackBtn(R.id.iv_back);
        loadingUtil1 = LoadingUtil.getInstance(EnterGraduateSchoolActivity.this);
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
        bundle.putString("educationHistory",edtGraduateSchool.getText().toString().trim());
        Intent intent = getIntent();
        intent.putExtras(bundle);
        setResult(RESULT_OK,intent);
        if (edtGraduateSchool.getText().toString().equals(educationHistory)) {
            finishCur();
        }else{
            loadingUtil.show();
            loadingUtil.dismiss("修改成功", new LoadingUtil.Success() {
                @Override
                public void dismiss() {
                    finishCur();
                }
            });
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * ----------------------------继承基础mvpView方法-----------------------------------
     *
     * @param className*/

    @Override
    public void switchPageUI(String className) {

    }
}
