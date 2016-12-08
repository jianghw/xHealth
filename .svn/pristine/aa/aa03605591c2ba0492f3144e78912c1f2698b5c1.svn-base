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
import com.kaurihealth.mvplib.mine_p.EnterStudyExperiencePresenter;
import com.kaurihealth.mvplib.mine_p.IEnterStudyExperienceView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;


/**
 * 我的Fragment-->个人信息设置--> 学习与工作经历  created by Nick
 */
public class EnterStudyExperienceActivity extends BaseActivity implements IEnterStudyExperienceView {

    @Inject
    EnterStudyExperiencePresenter<IEnterStudyExperienceView> mPresenter;

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
        initNewBackBtn(getString(R.string.study_experience_tv_title));
        tvMore.setText(getString(R.string.title_save));

        //获取学历与工作经历
        String workingExperience = LocalData.getLocalData().getMyself().getWorkingExperience();
        setEdtPractice(workingExperience);
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

    @OnClick(R.id.tv_more)
    public void restore() {
        mPresenter.onSubscribe();
    }

    @Override
    public DoctorDisplayBean getDoctorDisplayBean() {
        DoctorDisplayBean bean = (DoctorDisplayBean) LocalData.getLocalData().getMyself().clone();
        bean.setWorkingExperience(getEdtPractice());
        return bean;
    }

    public String getEdtPractice() {
        return edtPractice.getText().toString().trim();
    }

    public void setEdtPractice(String edtPractice) {
        this.edtPractice.setText(edtPractice);
    }

}
