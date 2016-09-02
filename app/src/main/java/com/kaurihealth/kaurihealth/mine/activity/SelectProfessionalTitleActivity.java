package com.kaurihealth.kaurihealth.mine.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.commonlibrary.widget.util.LoadingUtil;
import com.kaurihealth.utilslib.dialog.PopUpNumberPickerDialog;
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

/**
 * 我的Fragment --> 个人信息设置--> 职称
 */
public class SelectProfessionalTitleActivity extends CommonActivity {
    //教学职称
    @Bind(R.id.tvEducationTitles)
    TextView tvEducationTitles;
    //导师
    @Bind(R.id.tvMentorshipTitles)
    TextView tvMentorshipTitles;
    //医疗职称
    @Bind(R.id.tvHospitalTitles)
    TextView tvHospitalTitles;
    private Dialog educationTitleDialog;
    private Dialog hospitalTitlesDialog;
    private Dialog mentorshipTitlesDialog;

    private String[] hospitalTitles;
    private String[] educationTitles;
    private String[] mentorshipTitles;
    private LoadingUtil loadingUtil;
    private String hospitalTitlesStr;
    private String educationTitlesStr;
    private String mentorshipTitlesStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_title);
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
        setBack(R.id.iv_back);
        Bundle data = getBundle();
        loadingUtil = LoadingUtil.getInstance(SelectProfessionalTitleActivity.this);
        hospitalTitlesStr = data.getString("hospitalTitles");
        educationTitlesStr = data.getString("educationTitles");
        mentorshipTitlesStr = data.getString("mentorshipTitles");
        tvEducationTitles.setText(educationTitlesStr);
        tvHospitalTitles.setText(hospitalTitlesStr);
        tvMentorshipTitles.setText(mentorshipTitlesStr);
        hospitalTitles = getResources()
                .getStringArray(R.array.hospitalTitles);
        educationTitles = getResources()
                .getStringArray(R.array.educationTitles);
        mentorshipTitles = getResources()
                .getStringArray(R.array.mentorshipTitles);
        PopUpNumberPickerDialog educationTitleNumberpickerDialog = new PopUpNumberPickerDialog(this, educationTitles, new PopUpNumberPickerDialog.SetClickListener() {
            @Override
            public void onClick(int index) {
                tvEducationTitles.setText(educationTitles[index]);
            }
        });
        educationTitleDialog = educationTitleNumberpickerDialog.getDialog();

        PopUpNumberPickerDialog hospitalTitlesNumberpickerDialog = new PopUpNumberPickerDialog(this, hospitalTitles, new PopUpNumberPickerDialog.SetClickListener() {
            @Override
            public void onClick(int index) {
                tvHospitalTitles.setText(hospitalTitles[index]);
            }
        });
        hospitalTitlesDialog = hospitalTitlesNumberpickerDialog.getDialog();

        PopUpNumberPickerDialog mentorshipTitlesNumberpickerDialog = new PopUpNumberPickerDialog(this, mentorshipTitles, new PopUpNumberPickerDialog.SetClickListener() {
            @Override
            public void onClick(int index) {
                tvMentorshipTitles.setText(mentorshipTitles[index]);
            }
        });
        mentorshipTitlesDialog = mentorshipTitlesNumberpickerDialog.getDialog();

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
            case R.id.tv_operate:
                store();
                break;
            case R.id.tvMentorshipTitles:
                clickMentorshipTitles();
                break;
        }
    }

    //点击在院职称
    public void clickHospitalTitles() {
        hospitalTitlesDialog.show();
    }

    //点击学历职称
    public void clickEducationTitles() {
        educationTitleDialog.show();
    }

    //点击指导职称
    @OnClick(R.id.tvMentorshipTitles)
    public void clickMentorshipTitles() {
        mentorshipTitlesDialog.show();
    }

    //点击保存
    @OnClick(R.id.tv_operate)
    public void store() {

//         final PersonInfoBean personInfo = Getter.getInstance(SelectProfessionalTitleActivity.this).getPersonInfo();
//        personInfo.HospitalTitles =tvHospitalTitles.getText().toString();
//        personInfo.EducationTitles = tvEducationTitles.getText().toString();
//        personInfo.MentorshipTitles =tvMentorshipTitles.getText().toString();
//        PersonUtilNew.updataPersoninfo(personInfo, new SuccessInterfaceM<ResponseDisplayBean>() {
//            @Override
//            public void success(final ResponseDisplayBean message) {

//                        if (message.isSucess){
//                            putter.setPersonInfo(null);
//                            putter.setPersonInfo(personInfo);
//                        successInterface.success(message);

        Intent intent = getIntent();
        Bundle bundle = new Bundle();
        bundle.putString("hospitalTitles", tvHospitalTitles.getText().toString());
        bundle.putString("educationTitles", tvEducationTitles.getText().toString());
        bundle.putString("mentorshipTitles", tvMentorshipTitles.getText().toString());
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        if (!(tvEducationTitles.getText().toString().equals(educationTitlesStr)&&
                tvHospitalTitles.getText().toString().equals(hospitalTitlesStr)&&
                tvMentorshipTitles.getText().toString().equals(mentorshipTitlesStr))) {
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
