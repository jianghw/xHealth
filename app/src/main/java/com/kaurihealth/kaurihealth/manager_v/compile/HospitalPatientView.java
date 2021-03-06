package com.kaurihealth.kaurihealth.manager_v.compile;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kaurihealth.datalib.local.LocalData;
import com.kaurihealth.datalib.response_bean.PatientBean;
import com.kaurihealth.datalib.response_bean.PatientRecordDisplayDto;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.utilslib.CheckUtils;
import com.kaurihealth.utilslib.controller.AbstractViewController;
import com.kaurihealth.utilslib.date.DateUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by jianghw on 2016/12/15.
 * <p/>
 * Describe: 病人头部
 */

public class HospitalPatientView extends AbstractViewController<PatientRecordDisplayDto> {

    @Bind(R.id.tv_name)
    TextView mTvName;
    @Bind(R.id.tv_gendar)
    TextView mTvGendar;
    @Bind(R.id.img_gender)
    ImageView mImgGender;
    @Bind(R.id.tv_age)
    TextView mTvAge;
    @Bind(R.id.cv_patient)
    LinearLayout mCvPatient;

    public HospitalPatientView(Context context) {
        super(context);
    }

    @Override
    protected int getResLayoutId() {
        return R.layout.include_hospital_patient;
    }

    @Override
    protected void onCreateView(View view) {
        ButterKnife.bind(this, view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void bindViewData(PatientRecordDisplayDto data) {

        PatientBean patientBean = LocalData.getLocalData().getCurrentPatient();
        if (patientBean == null) {
            defaultViewData();
        } else {
            mTvName.setText(CheckUtils.checkTextContent(patientBean.getFullName()));
            mTvGendar.setText(CheckUtils.checkTextContent(patientBean.getGender()));
            mImgGender.setImageResource(patientBean.getGender().equals("男") ? R.drawable.gender_icon : R.drawable.gender_women);
            mTvAge.setText(DateUtils.calculateAge(patientBean.getDateOfBirth()) + "岁");
        }
    }

    @Override
    protected void defaultViewData() {
        mTvName.setText(CheckUtils.checkTextContent(null));
        mTvGendar.setText(CheckUtils.checkTextContent(null));
        mImgGender.setImageResource(R.drawable.gender_icon);
        mTvAge.setText("~岁");
    }

    @Override
    public void unbindView() {
        ButterKnife.unbind(this);
    }

    @Override
    public void showLayout() {
        mCvPatient.setVisibility(View.VISIBLE);
    }

    @Override
    public void hiddenLayout() {
        mCvPatient.setVisibility(View.GONE);
    }

}
