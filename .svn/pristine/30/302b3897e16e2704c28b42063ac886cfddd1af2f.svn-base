package com.kaurihealth.kaurihealth.home_v.request;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.TextView;

import com.kaurihealth.datalib.request_bean.bean.AddressDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.PatientRequestDisplayBean;
import com.kaurihealth.datalib.response_bean.DoctorDisplayBean;
import com.kaurihealth.datalib.response_bean.DoctorPatientRelationshipBean;
import com.kaurihealth.datalib.response_bean.PatientDisplayBean;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.kaurihealth.eventbus.AcceptReasonEvent;
import com.kaurihealth.kaurihealth.eventbus.HealthConditionBeanEvent;
import com.kaurihealth.kaurihealth.eventbus.LongHStandardBeanEvent;
import com.kaurihealth.kaurihealth.eventbus.MedicalRecordIdEvent;
import com.kaurihealth.kaurihealth.eventbus.PatientRequestDisplayBeanEvent;
import com.kaurihealth.kaurihealth.eventbus.PrescriptionBeanEvent;
import com.kaurihealth.kaurihealth.eventbus.RejectReasonEvent;
import com.kaurihealth.kaurihealth.home_v.referral.AcceptReasonActivity;
import com.kaurihealth.kaurihealth.home_v.referral.RejectReasonActivity;
import com.kaurihealth.kaurihealth.patient_v.health_condition.HealthConditionActivity;
import com.kaurihealth.kaurihealth.patient_v.long_monitoring.LongHealthyStandardActivity;
import com.kaurihealth.kaurihealth.patient_v.medical_records.MedicalRecordActivity;
import com.kaurihealth.kaurihealth.patient_v.prescription.PrescriptionActivity;
import com.kaurihealth.kaurihealth.tinker.SampleApplicationLike;
import com.kaurihealth.mvplib.home_p.IPatientRequestInfoView;
import com.kaurihealth.mvplib.home_p.PatientRequesetInfoPresenter;
import com.kaurihealth.utilslib.CheckUtils;
import com.kaurihealth.utilslib.constant.Global;
import com.kaurihealth.utilslib.date.DateUtils;
import com.kaurihealth.utilslib.image.ImageUrlUtils;
import com.kaurihealth.utilslib.widget.CircleImageView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.Date;
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
    @Bind(R.id.request_type)
    TextView request_type;//服务项目
    @Bind(R.id.request_time)
    TextView request_time;//预约时间
    @Bind(R.id.request_reason)
    TextView request_reason;//预约原因
    @Bind(R.id.request_patient_phone)
    TextView request_patient_phone;//电话号码
    @Bind(R.id.request_patient_adress)
    TextView request_patient_adress;//联系地址
    @Bind(R.id.tv_tel_patientinfo_state)
    TextView tv_tel_patientinfo_state;//当前状态

    @Bind(R.id.floatbtn_refuse)
    Button floatbtnRefuse;

    @Bind(R.id.floatbtn_accept)
    Button floatbtnAccept;

    String requestReason;
    private PatientRequestDisplayBean patientRequestDisplayBean;
    private DoctorPatientRelationshipBean bean;

    @Override
    protected int getActivityLayoutID() {
        return R.layout.patient_request_info;
    }

    @Override
    protected void initPresenterAndView(Bundle savedInstanceState) {
        SampleApplicationLike.getApp().getComponent().inject(this);
        mPresenter.setPresenter(this);
    }

    @Override
    protected void initDelayedData() {
        initBackBtn(R.id.iv_back);
        setMonitor();
        //注册事件
        EventBus.getDefault().register(this);
    }

    /**
     * 绑定view
     */
    private void setMonitor() {
        request_patient_adress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(PatientRequestInfoActivity.this)
                        .setMessage(getTvValue(request_patient_adress))
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
//                sb.append(addressBean.getProvince());
//                sb.append(addressBean.getCity());
//                sb.append(addressBean.getDistrict());
                if (addressBean.addressLine1 != null) sb.append(addressBean.getAddressLine1());
                setText(sb.toString(), request_patient_adress);
            } else {
                setText("暂无", request_patient_adress);
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
            setText(mobileNumber, request_patient_phone);
        }

        setText(requestType, request_type);
        setText((new SimpleDateFormat("yyyy.MM.dd hh:mm", Locale.SIMPLIFIED_CHINESE)).format(requestDate), request_time);
        setText(requestReason, request_reason);
        //当前状态
        tv_tel_patientinfo_state.setText(patientRequest.getStatus().equals("等待")?"等待处理":"");

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
        ViewTreeObserver observer = request_reason.getViewTreeObserver(); //textAbstract为TextView控件
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ViewTreeObserver obs = request_reason.getViewTreeObserver();
                obs.removeGlobalOnLayoutListener(this);
                if (request_reason.getLineCount() > 3) {
                    int lineEndIndex = request_reason.getLayout().getLineEnd(2); //设置第3行打省略号
                    String text = request_reason.getText().subSequence(0, lineEndIndex - 3) + "...更多";
                    request_reason.setText(text);
                    request_reason.setOnClickListener(new View.OnClickListener() {
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
     * 点击事件
     */
    @OnClick({R.id.floatbtn_refuse, R.id.floatbtn_accept, R.id.layHealthyRecord, R.id.lay_monitorstandard, R.id.layMedicalhistory, R.id.layRecipe, R.id.layTeam, R.id.lay_family})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.floatbtn_refuse://拒绝
                SweetAlertDialog dialogCancle = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("确定拒绝患者预约吗?")
                        .setConfirmText("确定")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                EventBus.getDefault().postSticky(new RejectReasonEvent(patientRequestDisplayBean.patientRequestId));
                                skipToForResult(RejectReasonActivity.class, null, Global.RequestCode.REJECT);
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
                        skipToForResult(AcceptReasonActivity.class, null, Global.RequestCode.ACCEPT);
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
                showToast(getString(R.string.patient_request_team_tips));
                break;
            case R.id.lay_family:
                showToast(getString(R.string.patient_request_familytips));
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
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Global.RequestCode.REJECT:
                    setResult(Global.RequestCode.REJECT);
                    break;
                case Global.RequestCode.ACCEPT:
                    setResult(Global.RequestCode.ACCEPT);
                    break;
                default:
                    break;
            }
            finishCur();
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
