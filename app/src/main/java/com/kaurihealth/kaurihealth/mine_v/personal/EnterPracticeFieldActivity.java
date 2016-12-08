package com.kaurihealth.kaurihealth.mine_v.personal;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.kaurihealth.datalib.local.LocalData;
import com.kaurihealth.datalib.response_bean.DoctorDisplayBean;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.kaurihealth.tinker.SampleApplicationLike;
import com.kaurihealth.mvplib.mine_p.EnterPracticeFieldPresenter;
import com.kaurihealth.mvplib.mine_p.IEnterPracticeFieldView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Model: 我的Fragment-->个人信息-->专业特长    created by Nick
 */
public class EnterPracticeFieldActivity extends BaseActivity implements IEnterPracticeFieldView {

    @Inject
    EnterPracticeFieldPresenter<IEnterPracticeFieldView> mPresenter;
    @Bind(R.id.tv_more)
    TextView tvMore;
    @Bind(R.id.edt_practice)
    EditText edtPractice;

    @Override
    protected int getActivityLayoutID() {
        return R.layout.activity_enter_practice_field;
    }

    @Override
    protected void initPresenterAndView(Bundle savedInstanceState) {
        SampleApplicationLike.getApp().getComponent().inject(this);
        mPresenter.setPresenter(this);
    }

    @Override
    protected void initDelayedData() {
        initNewBackBtn(getString(R.string.practice_title_tv_title));
        tvMore.setText(getString(R.string.title_save));

        String practiceField = LocalData.getLocalData().getMyself().getPracticeField();
        //获取专业特长
        setEdtPractice(practiceField);
        setSelection(edtPractice);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.unSubscribe();
    }

    /**
     * ----------------------------继承基础mvpView方法-----------------------------------
     */
    @Override
    public void switchPageUI(String className) {
        Intent intent = getIntent();  //初始化intent
        setResult(RESULT_OK, intent);
        finishCur();
    }

    /**
     * 保存
     */
    @OnClick(R.id.tv_more)
    public void save() {
        mPresenter.onSubscribe();
    }

    @Override
    public DoctorDisplayBean getDoctorDisplayBean() {
        DoctorDisplayBean bean = (DoctorDisplayBean) LocalData.getLocalData().getMyself().clone();
        bean.setPracticeField(getEdtPractice());
        return bean;
    }

    public String getEdtPractice() {
        return edtPractice.getText().toString().trim();
    }

    public void setEdtPractice(String edtPractice) {
        this.edtPractice.setText(edtPractice);
    }
}
