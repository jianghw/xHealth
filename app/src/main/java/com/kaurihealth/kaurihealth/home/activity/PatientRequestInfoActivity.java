package com.kaurihealth.kaurihealth.home.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.commonlibrary.widget.CircleImageView;
import com.kaurihealth.utilslib.date.DateUtils;
import com.kaurihealth.datalib.request_bean.bean.PatientDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.PatientRequestDisplayBean;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.patient_v.LongHealthyStandardActivity;
import com.kaurihealth.kaurihealth.common.activity.MedicaHistoryActivity;
import com.kaurihealth.kaurihealth.common.activity.PrescriptionActivity;
import com.kaurihealth.kaurihealth.home.fragment.HomeFragment;
import com.kaurihealth.kaurihealth.home.util.BundleFactory;
import com.kaurihealth.kaurihealth.home.util.Interface.IBundleFactory;
import com.kaurihealth.kaurihealth.home.util.Interface.ISetBundleHealthyRecord;
import com.kaurihealth.kaurihealth.patientwithdoctor.activity.ExpertsActivity;
import com.kaurihealth.kaurihealth.patientwithdoctor.activity.HealthyRecordNewActivity;
import com.kaurihealth.kaurihealth.adapter.PatientDocRelAdapter;
import com.kaurihealth.kaurihealth.util.ExitApplication;
import com.youyou.zllibrary.netutil.ImageLoadUtil;
import com.youyou.zllibrary.util.CommonActivity;
import com.kaurihealth.kaurihealth.adapter.CommonViewAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * 版权:    张磊
 * 作者:    张磊
 * 描述：
 * 修订日期:
 */
public class PatientRequestInfoActivity extends CommonActivity {
    @Bind(R.id.iv_photo_topleft_patientinfo)
    CircleImageView ivPhotoTopleftPatientinfo;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_gendar)
    TextView tvGendar;
    @Bind(R.id.tv_age)
    TextView tvAge;
    @Bind(R.id.vpager_top)
    ViewPager vpagerTop;
    @Bind(R.id.rbtn_one)
    RadioButton rbtnOne;
    @Bind(R.id.rbtn_two)
    RadioButton rbtnTwo;
    @Bind(R.id.floatbtn_refuse)
    FloatingActionButton floatbtnRefuse;
    @Bind(R.id.floatbtn_accept)
    FloatingActionButton floatbtnAccept;

    private View leftView;
    private View rightView;
    private TextView diseasename;
    private TextView tv_tel;
    private TextView tv_urgencyname;
    private TextView tv_type;
    private TextView tv_appontmentdate;

    private int patientRequestId;
    private final int Accept = 3;
    private final int Reject = 4;
    private SweetAlertDialog dialogCancle;
    private SweetAlertDialog dialogAccept;
    private int patientId = -1;
    private String kauriHealthId;
    private Bundle bundle;
    private String gender;
    private ISetBundleHealthyRecord iSetBundleHealthyRecord;
    String requestReason;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_request_info);
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
        bundle = getBundle();
        IBundleFactory bundleFactory = new BundleFactory();
        iSetBundleHealthyRecord = bundleFactory.getISetBundleHealthyRecord(bundle);
        setBack(R.id.iv_back);
        PatientRequestDisplayBean patientRequestDisplayBean = (PatientRequestDisplayBean) getBundle().getSerializable("CurrentPatientRequest");
        patientId = patientRequestDisplayBean.patientId;
        gender = patientRequestDisplayBean.patient.gender;
        patientRequestId = patientRequestDisplayBean.patientRequestId;
        kauriHealthId = patientRequestDisplayBean.patient.kauriHealthId;
        iSetBundleHealthyRecord.setGender(gender);
        iSetBundleHealthyRecord.setkauriHealthId(kauriHealthId);
        iSetBundleHealthyRecord.setPatientId(patientId);
        iSetBundleHealthyRecord.setAble(false);
        iSetBundleHealthyRecord.setName(patientRequestDisplayBean.patient.fullName);
        iSetBundleHealthyRecord.setBirthDate(patientRequestDisplayBean.patient.dateOfBirth);

        String select = getBundle().getString("select");
        //提示来自PatientDocRelAdapter
        if (!TextUtils.isEmpty(select) && select.equals(PatientDocRelAdapter.class.getName())) {
            hideBtn();
        }
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        leftView = layoutInflater.inflate(R.layout.topleft_patientinfo, null);
        rightView = layoutInflater.inflate(R.layout.topright_patientinfo, null);
        binds();
        List<View> listView = new ArrayList<>();
        listView.add(leftView);
        listView.add(rightView);
        setPage(0);
        CommonViewAdapter commonViewAdapter = new CommonViewAdapter(listView);
        vpagerTop.setAdapter(commonViewAdapter);
        vpagerTop.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                setPage(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        renderActivity(patientRequestDisplayBean);
    }

    private void binds() {
        //服务项目
        //疾病名称
        diseasename = (TextView) leftView.findViewById(R.id.tv_disease_topleft_patientinfo);
        //预约时间
        tv_tel = (TextView) leftView.findViewById(R.id.tv_tel_patientinfo);
        //预约原因
        tv_urgencyname = (TextView) leftView.findViewById(R.id.tv_urgencyname_topleft_patientinfo);
        //联系电话
        tv_type = (TextView) rightView.findViewById(R.id.tv_apponintmenttype_topleft_patientinfo);
        //联系地址
        tv_appontmentdate = (TextView) rightView.findViewById(R.id.tv_appontmentdate_patientinfo);
    }

    private void renderActivity(PatientRequestDisplayBean patientRequest) {
        PatientDisplayBean patient = patientRequest.patient;
        //患者头像
        String avatar = patientRequest.patient.getAvatar();
        //服务项目
        String requestType = patientRequest.requestType;
        //预约时间
        Date requestDate = patientRequest.requestDate;
        //预约原因
         requestReason = patientRequest.requestReason;
        //联系电话
        String mobileNumber = patient.getEmergencyContactNumber();
        //联系地址
        String address = patient.getAddress();

        if (patient.avatar != null) {
            ImageLoadUtil.displayImg(avatar, ivPhotoTopleftPatientinfo, getApplicationContext());
        }
        setText(patient.fullName, tvName);
        setText(patient.gender, tvGendar);
        int age = DateUtils.getAge(patient.dateOfBirth);
        setText(String.format("%d岁", age), tvAge);
        setText(requestType, diseasename);
        setText((new SimpleDateFormat("yyyy.MM.dd hh:mm")).format(requestDate), tv_tel);
        setText(requestReason, tv_urgencyname);
        setText(mobileNumber, tv_type);
        setText(address, tv_appontmentdate);

        //为请求原因添加监听
        ViewTreeObserver observer = tv_urgencyname.getViewTreeObserver(); //textAbstract为TextView控件
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ViewTreeObserver obs = tv_urgencyname.getViewTreeObserver();
                obs.removeGlobalOnLayoutListener(this);
                if (tv_urgencyname.getLineCount() > 3) {
                    int lineEndIndex = tv_urgencyname.getLayout().getLineEnd(2); //设置第3行打省略号
                    String text = tv_urgencyname.getText().subSequence(0, lineEndIndex - 3) + "...更多";
                    tv_urgencyname.setText(text);
                    tv_urgencyname.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            new SweetAlertDialog(PatientRequestInfoActivity.this).setTitleText("请求原因").setContentText(requestReason).show();
                        }
                    });
                }
            }
        });
    }

    /**
     * 设置viewpager显示在第几页 0 1
     */
    private void setPage(int index) {
        switch (index) {
            case 0:
                rbtnOne.setChecked(true);
                rbtnTwo.setChecked(false);
                break;
            case 1:
                rbtnOne.setChecked(false);
                rbtnTwo.setChecked(true);
                break;
        }

    }


    /**
     * 医疗记录
     */
    @OnClick(R.id.layMedicalhistory)
    public void medicalhistory() {
        skipTo(MedicaHistoryActivity.class, bundle);
    }

    /**
     * 处方
     */
    @OnClick(R.id.layRecipe)
    public void recipe() {
        skipTo(PrescriptionActivity.class, bundle);
    }

    /**
     * 医生团队
     */
    @OnClick(R.id.layTeam)
    public void doctorteam() {
        skipTo(ExpertsActivity.class, bundle);
    }


    @OnClick({R.id.floatbtn_refuse, R.id.floatbtn_accept, R.id.layHealthyRecord, R.id.lay_monitorstandard})
    public void onClick(View view) {
        final Bundle bundle = new Bundle();
        bundle.putInt("PatientRequestId", patientRequestId);
        switch (view.getId()) {
            case R.id.floatbtn_refuse:
                dialogCancle = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE).setTitleText("确定拒绝患者预约吗?").setConfirmText("确定").setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        skipToForResult(RejectReasonActivity.class, bundle, Reject);
                        sweetAlertDialog.dismiss();
                    }
                }).showCancelButton(true).setCancelText("取消");
                dialogCancle.show();
                break;
            case R.id.floatbtn_accept:
                dialogAccept = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE).setTitleText("确定接受患者预约吗?").setConfirmText("确定").setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        skipToForResult(AcceptReasonActivity.class, bundle, Accept);
                        sweetAlertDialog.dismiss();
                    }
                }).showCancelButton(true).setCancelText("取消");
                dialogAccept.show();
                break;
            case R.id.layHealthyRecord:
                skipTo(HealthyRecordNewActivity.class, bundle);
                break;
            case R.id.lay_monitorstandard:
                skipTo(LongHealthyStandardActivity.class, bundle);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Reject:
                //拒绝
                if (resultCode == RESULT_OK) {
                    setResult(HomeFragment.Update);
                    finishCur();
                }
                break;
            case Accept:
                if (resultCode == RESULT_OK) {
                    setResult(HomeFragment.UpdateAcc);
                    finishCur();
                }
                break;
        }
    }

    /**
     * 设置隐藏两个按钮
     */
    private void hideBtn() {
        floatbtnAccept.setVisibility(View.GONE);
        floatbtnRefuse.setVisibility(View.GONE);
    }

}
