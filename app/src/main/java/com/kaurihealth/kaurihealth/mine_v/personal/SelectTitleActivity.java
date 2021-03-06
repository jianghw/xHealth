package com.kaurihealth.kaurihealth.mine_v.personal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.kaurihealth.datalib.local.LocalData;
import com.kaurihealth.datalib.response_bean.DoctorDisplayBean;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.kaurihealth.tinker.SampleApplicationLike;
import com.kaurihealth.mvplib.mine_p.ISelectTitleView;
import com.kaurihealth.mvplib.mine_p.SelectTitlePresenter;
import com.kaurihealth.utilslib.constant.Global;
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

    private String mType;

    @Override
    protected int getActivityLayoutID() {
        return R.layout.activity_select_title;
    }

    @Override
    protected void initPresenterAndView(Bundle savedInstanceState) {
        SampleApplicationLike.getApp().getComponent().inject(this);
        mPresenter.setPresenter(this);
    }

    @Override
    protected void initDelayedData() {
        initNewBackBtn(getString(R.string.professional_title_tv_title));
        tvMore.setText(getString(R.string.title_save));

        initRenderView();

        initBundleData();
    }

    private void initRenderView() {
        DoctorDisplayBean bean = LocalData.getLocalData().getMyself();
        setTvHospitalTitles(bean.getHospitalTitle());
        setTvEducationTitles(bean.getEducationTitle());
        setTvMentorshipTitles(bean.getMentorshipTitle());
    }

    private void initBundleData() {
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                mType = bundle.getString(Global.Environment.BUNDLE);
            }
        }
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
                if (mType != null && mType.equals(Global.Environment.CHOICE)) {
                    Intent intent = getIntent();
                    Bundle bundle = new Bundle();
                    bundle.putString("HospitalTitles", getTvHospitalTitles());
                    bundle.putString("MentorshipTitles", getTvMentorshipTitles());
                    bundle.putString("EducationTitles", getTvEducationTitles());
                    intent.putExtras(bundle);
                    setResult(RESULT_OK, intent);
                    finishCur();
                } else {
                    mPresenter.onSubscribe();
                }
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
