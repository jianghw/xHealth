package com.kaurihealth.kaurihealth.mine_v.personal;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.kaurihealth.datalib.local.LocalData;
import com.kaurihealth.datalib.response_bean.DoctorDisplayBean;
import com.kaurihealth.kaurihealth.MyApplication;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.mvplib.mine_p.ISelectTitleView;
import com.kaurihealth.mvplib.mine_p.SelectTitlePresenter;
import com.kaurihealth.utilslib.dialog.DialogUtils;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;


/**
 * 我的Fragment-->个人信息-->"职称"修改   created by Nick
 */
public class SelectTitleActivity extends BaseActivity implements ISelectTitleView {
    @Inject
    SelectTitlePresenter<ISelectTitleView> mPresenter;

    @Bind(R.id.tv_more)
    TextView tvMore;
    @Bind(R.id.tvHospitalTitles)
    TextView tvHospitalTitles;
    @Bind(R.id.tvEducationTitles)
    TextView tvEducationTitles;
    @Bind(R.id.tvMentorshipTitles)
    TextView tvMentorshipTitles;

    @Override
    protected int getActivityLayoutID() {
        return R.layout.activity_select_title;
    }

    @Override
    protected void initPresenterAndView(Bundle savedInstanceState) {
        MyApplication.getApp().getComponent().inject(this);
        mPresenter.setPresenter(this);
    }

    @Override
    protected void initDelayedData() {
        initNewBackBtn(getString(R.string.professional_title_tv_title));
        tvMore.setText(getString(R.string.title_save));
        renderView();
    }

    private void renderView() {
        DoctorDisplayBean bean = LocalData.getLocalData().getMyself();
        setTvHospitalTitles(bean.getHospitalTitle());
        setTvEducationTitles(bean.getEducationTitle());
        setTvMentorshipTitles(bean.getMentorshipTitle());
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
        setResult(RESULT_OK, getIntent());
        finishCur();
    }

    @OnClick({R.id.tvHospitalTitles, R.id.tvEducationTitles, R.id.tvMentorshipTitles, R.id.tv_more})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_more:
                mPresenter.onSubscribe();
                break;
            case R.id.tvHospitalTitles:
                clickHospitalTitles();
                break;
            case R.id.tvEducationTitles:
                clickEducationTitles();
                break;
            case R.id.tvMentorshipTitles:
                clickMentorshipTitles();
                break;
            default:
                break;
        }
    }

    //点击在院职称
    private void clickHospitalTitles() {
        String[] hospitalTitles = getResources().getStringArray(R.array.hospitalTitles);
        DialogUtils.showStringDialog(this, hospitalTitles, index -> setTvHospitalTitles(hospitalTitles[index]));
    }

    //点击学历职称
    private void clickEducationTitles() {
        String[] educationTitles = getResources().getStringArray(R.array.educationTitles);
        DialogUtils.showStringDialog(this, educationTitles, index -> setTvEducationTitles(educationTitles[index]));
    }

    //点击指导职称
    private void clickMentorshipTitles() {
        String[] mentorshipTitles = getResources().getStringArray(R.array.mentorshipTitles);
        DialogUtils.showStringDialog(this, mentorshipTitles, index -> setTvMentorshipTitles(mentorshipTitles[index]));
    }

    @Override
    public DoctorDisplayBean getDoctorDisplayBean() {
        DoctorDisplayBean bean = (DoctorDisplayBean) LocalData.getLocalData().getMyself().clone();
        bean.setHospitalTitle(getTvHospitalTitles());
        bean.setEducationTitle(getTvEducationTitles());
        bean.setMentorshipTitle(getTvMentorshipTitles());
        return bean;
    }

    public String getTvEducationTitles() {
        return tvEducationTitles.getText().toString().trim();
    }

    public void setTvEducationTitles(String tvEducationTitles) {
        this.tvEducationTitles.setText(tvEducationTitles);
    }

    public String getTvMentorshipTitles() {
        return tvMentorshipTitles.getText().toString().trim();
    }

    public void setTvMentorshipTitles(String tvMentorshipTitles) {
        this.tvMentorshipTitles.setText(tvMentorshipTitles);
    }

    public String getTvHospitalTitles() {
        return tvHospitalTitles.getText().toString().trim();
    }

    public void setTvHospitalTitles(String tvHospitalTitles) {
        this.tvHospitalTitles.setText(tvHospitalTitles);
    }

}
