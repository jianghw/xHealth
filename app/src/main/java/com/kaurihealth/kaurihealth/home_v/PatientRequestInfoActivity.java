package com.kaurihealth.kaurihealth.home_v;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.RadioButton;
import android.widget.TextView;

import com.kaurihealth.datalib.request_bean.bean.AddressDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.PatientRequestDisplayBean;
import com.kaurihealth.datalib.response_bean.DoctorDisplayBean;
import com.kaurihealth.datalib.response_bean.DoctorPatientRelationshipBean;
import com.kaurihealth.datalib.response_bean.PatientDisplayBean;
import com.kaurihealth.kaurihealth.MyApplication;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.adapter.CommonViewAdapter;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.kaurihealth.eventbus.AcceptReasonEvent;
import com.kaurihealth.kaurihealth.eventbus.HealthConditionBeanEvent;
import com.kaurihealth.kaurihealth.eventbus.LongHStandardBeanEvent;
import com.kaurihealth.kaurihealth.eventbus.MedicalRecordIdEvent;
import com.kaurihealth.kaurihealth.eventbus.PatientRequestDisplayBeanEvent;
import com.kaurihealth.kaurihealth.eventbus.PrescriptionBeanEvent;
import com.kaurihealth.kaurihealth.eventbus.RejectReasonEvent;
import com.kaurihealth.kaurihealth.home_v.referral.RejectReasonActivity;
import com.kaurihealth.kaurihealth.main_v.PatientRequestActivity;
import com.kaurihealth.kaurihealth.patient_v.DoctorTeamActivity;
import com.kaurihealth.kaurihealth.patient_v.FamilyMembersActivity;
import com.kaurihealth.kaurihealth.patient_v.PrescriptionActivity;
import com.kaurihealth.kaurihealth.patient_v.health_condition.HealthConditionActivity;
import com.kaurihealth.kaurihealth.patient_v.long_monitoring.LongHealthyStandardActivity;
import com.kaurihealth.kaurihealth.patient_v.medical_records.MedicalRecordActivity;
import com.kaurihealth.mvplib.home_p.IPatientRequestInfoView;
import com.kaurihealth.mvplib.home_p.PatientRequesetInfoPresenter;
import com.kaurihealth.utilslib.CheckUtils;
import com.kaurihealth.utilslib.date.DateUtils;
import com.kaurihealth.utilslib.image.ImageUrlUtils;
import com.kaurihealth.utilslib.widget.CircleImageView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;


/**
 * Created by mip on 2016/9/21.
 */
public class PatientRequestInfoActivity extends BaseActivity implements IPatientRequestInfoView {
    @Inject
    PatientRequesetInfoPresenter<IPatientRequestInfoView> mPresenter;

    @Bind(R.id.iv_photo)
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
    View leftView;
    View rightView;
    TextView diseasename;
    TextView tv_tel;
    TextView tv_urgencyname;
    TextView tv_type;
    TextView tv_appontmentdate;

    private final int Accept = 3;
    private final int Reject = 4;

    String requestReason;
    private PatientRequestDisplayBean patientRequestDisplayBean;
    private DoctorPatientRelationshipBean bean;

    @Override
    protected int getActivityLayoutID() {
        return R.layout.patient_request_info;
    }

    @Override
    protected void initPresenterAndView(Bundle savedInstanceState) {
        MyApplication.getApp().getComponent().inject(this);
        mPresenter.setPresenter(this);
    }

    @Override
    protected void initDelayedData() {
        initBackBtn(R.id.iv_back);

        initViewPager();
        //注册事件
        EventBus.getDefault().register(this);
    }

    /**
     * 初始化adapter
     */
    private void initViewPager() {
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
    }


    /**
     * 绑定view
     */
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
        tv_appontmentdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(PatientRequestInfoActivity.this)
                        .setMessage(getTvValue(tv_appontmentdate))
                        .setNegativeButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create()
                        .show();
            }
        });
    }

    /**
     * 订阅事件  点击item 传
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void eventBusMain(PatientRequestDisplayBeanEvent event) {
        patientRequestDisplayBean = event.getBean();
        if (patientRequestDisplayBean == null) return;
        skipToActivitySendData(patientRequestDisplayBean);
        renderActivity(patientRequestDisplayBean);
    }

    /**
     * 读取数据
     */
    private void renderActivity(PatientRequestDisplayBean patientRequest) {
        //患者头像
        String avatar = patientRequest.getPatient().getAvatar();
        //服务项目
        String requestType = patientRequest.getRequestType();
        //预约时间
        Date requestDate = patientRequest.getRequestDate();
        //预约原因
        requestReason = patientRequest.getRequestReason();

        PatientDisplayBean patient = patientRequest.getPatient();
        if (patient != null) {
            //联系电话
            String mobileNumber = patient.getMobileNumber();
            AddressDisplayBean addressBean = patient.getAddress();
            //联系地址
            if (addressBean != null) {
                StringBuilder sb = new StringBuilder();
                sb.append(addressBean.getProvince());
                sb.append(addressBean.getCity());
                sb.append(addressBean.getDistrict());
                if (addressBean.addressLine1 != null) sb.append(addressBean.getAddressLine1());
                setText(sb.toString(), tv_appontmentdate);
            } else {
                setText("暂无", tv_appontmentdate);
            }


            if (CheckUtils.checkUrlNotNull(avatar)) {
                ImageUrlUtils.picassoBySmallUrlCircle(this, avatar, ivPhotoTopleftPatientinfo);
            } else {
                ivPhotoTopleftPatientinfo.setImageResource(R.mipmap.ic_circle_head_green);
            }

            setText(patient.getFullName(), tvName);
            setText(patient.getGender(), tvGendar);
            int age = DateUtils.calculateAge(patient.getDateOfBirth());
            setText(String.format("%d岁", age), tvAge);
            setText(mobileNumber, tv_type);
        }

        setText(requestType, diseasename);
        setText((new SimpleDateFormat("yyyy.MM.dd hh:mm", Locale.SIMPLIFIED_CHINESE)).format(requestDate), tv_tel);
        setText(requestReason, tv_urgencyname);

        textContentProcessing();
    }

    /**
     * 跳转发送数据
     */
    private void skipToActivitySendData(PatientRequestDisplayBean patientRequest) {
        bean = new DoctorPatientRelationshipBean();
        PatientDisplayBean patientDisplayBean = patientRequest.getPatient();
        DoctorDisplayBean doctorDisplayBean = patientRequest.getDoctor();
        bean.setPatientId(patientRequest.getPatientId());
        bean.setPatient(patientDisplayBean);
        bean.setDoctor(doctorDisplayBean);
    }

    /**
     * 为请求原因文本添加监听
     */
    private void textContentProcessing() {
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
            default:
                break;
        }

    }

    /**
     * 点击事件
     */
    @OnClick({R.id.floatbtn_refuse, R.id.floatbtn_accept, R.id.layHealthyRecord, R.id.lay_monitorstandard, R.id.layMedicalhistory, R.id.layRecipe, R.id.layTeam,R.id.lay_family})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.floatbtn_refuse://拒绝
                SweetAlertDialog dialogCancle = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE).setTitleText("确定拒绝患者预约吗?").setConfirmText("确定").setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        EventBus.getDefault().postSticky(new RejectReasonEvent(patientRequestDisplayBean.patientRequestId));
                        skipToForResult(RejectReasonActivity.class, null, Reject);
                        sweetAlertDialog.dismiss();
                    }
                }).showCancelButton(true).setCancelText("取消");
                dialogCancle.show();
                break;
            case R.id.floatbtn_accept://同意
                SweetAlertDialog dialogAccept = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE).setTitleText("确定接受患者预约吗?").setConfirmText("确定").setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        EventBus.getDefault().postSticky(new AcceptReasonEvent(patientRequestDisplayBean.patientRequestId));
                        skipToForResult(AcceptReasonActivity.class, null, Accept);
                        sweetAlertDialog.dismiss();
                    }
                }).showCancelButton(true).setCancelText("取消");
                dialogAccept.show();
                break;
            case R.id.layHealthyRecord:
                EventBus.getDefault().postSticky(new HealthConditionBeanEvent(bean, false));
                skipTo(HealthConditionActivity.class);
                break;
            case R.id.lay_monitorstandard:
                EventBus.getDefault().postSticky(new LongHStandardBeanEvent(patientRequestDisplayBean.patientId, false, null));
                skipTo(LongHealthyStandardActivity.class);
                break;
            case R.id.layMedicalhistory:
                EventBus.getDefault().postSticky(new MedicalRecordIdEvent(patientRequestDisplayBean.patientId, bean, false));
                skipTo(MedicalRecordActivity.class);
                break;
            case R.id.layRecipe:
                EventBus.getDefault().postSticky(new PrescriptionBeanEvent(bean, false));
                skipTo(PrescriptionActivity.class);
                break;
            case R.id.layTeam:
                skipTo(DoctorTeamActivity.class);
                break;
            case R.id.lay_family:
                skipTo(FamilyMembersActivity.class);
                break;
            default:
                break;
        }
    }

    /**
     * 界面返回事件
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Reject:
                //拒绝
                if (resultCode == RESULT_OK) {
                    setResult(PatientRequestActivity.Update);
                    finishCur();
                }
                break;
            case Accept:
                if (resultCode == RESULT_OK) {
                    setResult(PatientRequestActivity.UpdateAcc);
                    finishCur();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void switchPageUI(String className) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
