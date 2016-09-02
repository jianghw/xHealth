package com.kaurihealth.kaurihealth.mine_v;

import android.app.Dialog;
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
import com.kaurihealth.utilslib.dialog.PopUpNumberPickerDialog;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;


/**
 * 我的Button 里面的个人信息-->职称修改   created by Nick
 */
public class SelectTitleActivity extends BaseActivity implements ISelectTitleView {

    @Bind(R.id.tvEducationTitles)
    TextView tvEducationTitles;
    @Bind(R.id.tvMentorshipTitles)
    TextView tvMentorshipTitles;
    @Bind(R.id.tvHospitalTitles)
    TextView tvHospitalTitles;
    private Dialog educationTitleDialog;
    private Dialog hospitalTitlesDialog;
    private Dialog mentorshipTitlesDialog;

    @Inject
    SelectTitlePresenter<ISelectTitleView> mPresenter;

    private DoctorDisplayBean myself;

    @Override
    protected int getActivityLayoutID() {
        return R.layout.activity_select_title;
    }

    @Override
    protected void initPresenterAndData(Bundle savedInstanceState) {
        MyApplication.getApp().getComponent().inject(this);
        mPresenter.setPresenter(this);
        myself = LocalData.getLocalData().getMyself();
    }

    @Override
    protected void initDelayedView() {
        initBackBtn(R.id.iv_back);
        initDialog();
        tvEducationTitles.setText(myself.educationTitle);
        tvHospitalTitles.setText(myself.hospitalTitle);
        tvMentorshipTitles.setText(myself.mentorshipTitle);

    }


    private void initDialog() {
        final String[] hospitalTitles = getResources()
                .getStringArray(R.array.hospitalTitles);
        final String[] educationTitles = getResources()
                .getStringArray(R.array.educationTitles);
        final String[] mentorshipTitles = getResources()
                .getStringArray(R.array.mentorshipTitles);

        PopUpNumberPickerDialog educationTitleNumberpickerDialog = new PopUpNumberPickerDialog(this, educationTitles, new PopUpNumberPickerDialog.SetClickListener() {
            @Override
            public void onClick(int index) {
                tvEducationTitles.setText(educationTitles[index]);
                myself.educationTitle = educationTitles[index];
            }
        });
        //educationTitleDialog Dialog实例化
        educationTitleDialog = educationTitleNumberpickerDialog.getDialog();

        PopUpNumberPickerDialog hospitalTitlesNumberpickerDialog = new PopUpNumberPickerDialog(this, hospitalTitles, new PopUpNumberPickerDialog.SetClickListener() {
            @Override
            public void onClick(int index) {
                tvHospitalTitles.setText(hospitalTitles[index]);
                myself.hospitalTitle = hospitalTitles[index];
            }
        });
        //hospitalTitlesDialog Dialog实例化
        hospitalTitlesDialog = hospitalTitlesNumberpickerDialog.getDialog();

        PopUpNumberPickerDialog mentorshipTitlesNumberpickerDialog = new PopUpNumberPickerDialog(this, mentorshipTitles, new PopUpNumberPickerDialog.SetClickListener() {
            @Override
            public void onClick(int index) {
                tvMentorshipTitles.setText(mentorshipTitles[index]);
                myself.mentorshipTitle = mentorshipTitles[index];
            }
        });
        //mentorshipTitlesDialog Dialog实例化
        mentorshipTitlesDialog = mentorshipTitlesNumberpickerDialog.getDialog();
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

    @OnClick({R.id.tvHospitalTitles, R.id.tvEducationTitles, R.id.tvMentorshipTitles, R.id.tv_operate})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvHospitalTitles:
                clickHospitalTitles();
                break;
            case R.id.tvEducationTitles:
                clickEducationTitles();
                break;
            //点击"保存"
            case R.id.tv_operate:
                mPresenter.onSubscribe();
                break;
            case R.id.tvMentorshipTitles:
                clickMentorshipTitles();
                break;
        }
    }

    //点击在院职称
    private void clickHospitalTitles() {
        hospitalTitlesDialog.show();
    }

    //点击学历职称
    private void clickEducationTitles() {
        educationTitleDialog.show();
    }

    //点击指导职称
    private void clickMentorshipTitles() {mentorshipTitlesDialog.show();}

    @Override
    public DoctorDisplayBean getDoctorDisplayBean() {
        return myself;
    }

    @Override
    public void displayError(Throwable e) {
        displayErrorDialog(e.getMessage());
    }
}
