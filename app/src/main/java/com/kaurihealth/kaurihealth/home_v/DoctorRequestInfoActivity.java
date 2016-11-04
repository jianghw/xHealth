package com.kaurihealth.kaurihealth.home_v;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kaurihealth.datalib.response_bean.DoctorDisplayBean;
import com.kaurihealth.datalib.response_bean.DoctorRelationshipBean;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.utilslib.CheckUtils;
import com.kaurihealth.utilslib.date.DateUtils;
import com.kaurihealth.utilslib.image.ImageUrlUtils;
import com.kaurihealth.utilslib.widget.CircleImageView;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by mip on 2016/9/20.
 */
public class DoctorRequestInfoActivity extends BaseActivity {

    @Bind(R.id.iv_photo)
    CircleImageView mIvPhoto;

    @Bind(R.id.tv_name)
    TextView mTvName;
    @Bind(R.id.tv_gendar)
    TextView mTvGendar;
    @Bind(R.id.tv_age)
    TextView mTvAge;
    @Bind(R.id.tv_professional)
    TextView mTvProfessional;
    @Bind(R.id.tv_major)
    TextView mTvMajor;
    @Bind(R.id.tv_practice_number)
    TextView mTvPracticeNumber;
    @Bind(R.id.tv_organization)
    TextView mTvOrganization;
    @Bind(R.id.vpager_top)
    LinearLayout mVpagerTop;
    @Bind(R.id.lay_experience)
    LinearLayout mLayExperience;
    @Bind(R.id.lay_speciality)
    LinearLayout mLaySpeciality;
    @Bind(R.id.floatbtn_active_new)
    FloatingActionButton floatbtn_active;
    @Bind(R.id.floatbtn_chat_new)
    FloatingActionButton floatbtn_chat;
    @Bind(R.id.tv_mentorshipTitle)
    TextView tv_mentorshipTitle;
    @Bind(R.id.tv_EducationTitle)
    TextView tv_EducationTitle;

    private DoctorDisplayBean relatedDoctor;

    @Override
    protected int getActivityLayoutID() {
        return R.layout.activity_doctor_info;
    }

    @Override
    protected void initPresenterAndView(Bundle savedInstanceState) {
        Bundle bundle = getBundle();
        if (bundle != null) {
            DoctorRelationshipBean doctorRelationshipBean = (DoctorRelationshipBean) bundle.getSerializable("CurrentDoctorRelationshipBean");
            if (doctorRelationshipBean == null) return;
            relatedDoctor = doctorRelationshipBean.getRelatedDoctor();
        }
    }

    @Override
    protected void initDelayedData() {
        initNewBackBtn("医生信息");
        floatbtn_active.setVisibility(View.GONE);
        floatbtn_chat.setVisibility(View.GONE);
        initData();
    }

    private void initData() {
        if (relatedDoctor == null) return;


        if (CheckUtils.checkUrlNotNull(relatedDoctor.getAvatar())) {
            ImageUrlUtils.picassoBySmallUrlCircle(this, relatedDoctor.getAvatar(), mIvPhoto);
        } else {
            mIvPhoto.setImageResource(R.mipmap.ic_circle_head_green);
        }

        mTvName.setText(relatedDoctor.getFullName());
        mTvGendar.setText(relatedDoctor.getGender());
        mTvAge.setText(DateUtils.calculateAge(relatedDoctor.getDateOfBirth()) + "岁");

        mTvProfessional.setText(setDefaultText(relatedDoctor.getHospitalTitle()));
        tv_mentorshipTitle.setText(setDefaultText(relatedDoctor.getMentorshipTitle()));
        tv_EducationTitle.setText(setDefaultText(relatedDoctor.getEducationTitle()));
        mTvMajor.setText(relatedDoctor.getDoctorInformations() != null
                ? relatedDoctor.getDoctorInformations().getDepartment() == null ? "暂无"
                : relatedDoctor.getDoctorInformations().getDepartment().getDepartmentName() : "暂无");

        if (relatedDoctor.getCertificationNumber().equals("")) {
            mTvPracticeNumber.setText("暂无执业编号");
        } else {
            mTvPracticeNumber.setText(relatedDoctor.getCertificationNumber());
        }
        mTvOrganization.setText(relatedDoctor.getDoctorInformations().getHospitalName());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * ````````````````点击事件````````````````
     */
    @OnClick({R.id.lay_experience, R.id.lay_speciality})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lay_experience:
                Bundle bundle_workingExperience = new Bundle();
                bundle_workingExperience.putString("workingExperience", getWorkingExperience());
                skipToBundle(DoctorStudyExprienceActivity.class, bundle_workingExperience);
                break;
            case R.id.lay_speciality:
                Bundle bundle_practiceField = new Bundle();
                bundle_practiceField.putString("practiceField", getPracticeField());
                skipToBundle(DoctorPracticeFieldActivity.class, bundle_practiceField);
                break;
            default:
                break;
        }
    }

    public String getPracticeField() {
        if (relatedDoctor != null) {
            return relatedDoctor.getPracticeField();
        }
        return "";
    }

    public String getWorkingExperience() {
        if (relatedDoctor != null) {
            return relatedDoctor.getWorkingExperience();
        }
        return "";
    }
}
