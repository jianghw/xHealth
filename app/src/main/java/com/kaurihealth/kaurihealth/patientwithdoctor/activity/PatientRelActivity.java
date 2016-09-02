package com.kaurihealth.kaurihealth.patientwithdoctor.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.commonlibrary.widget.CircleImageView;
import com.kaurihealth.datalib.request_bean.bean.DoctorPatientRelationshipBean;
import com.kaurihealth.datalib.service.IDoctorPatientRelationshipService;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.chat.activity.SingleChatActivity;
import com.kaurihealth.kaurihealth.patient_v.LongHealthyStandardActivity;
import com.kaurihealth.kaurihealth.common.activity.MedicaHistoryActivity;
import com.kaurihealth.kaurihealth.common.activity.PrescriptionActivity;
import com.kaurihealth.kaurihealth.home.util.BundleFactory;
import com.kaurihealth.kaurihealth.home.util.Config.ConfigBundleHealthyRecord;
import com.kaurihealth.kaurihealth.home.util.Interface.IBundleFactory;
import com.kaurihealth.kaurihealth.home.util.Interface.ISetBundleHealthyRecord;
import com.kaurihealth.kaurihealth.patientwithdoctor.util.Interface.IIsActive;
import com.kaurihealth.kaurihealth.patientwithdoctor.util.Interface.IsActive;
import com.kaurihealth.kaurihealth.patientwithdoctor.util.endtime.CalculateEndTimeInterface;
import com.kaurihealth.kaurihealth.patientwithdoctor.util.endtime.CalculateEndTimeUtil;
import com.kaurihealth.utilslib.date.RemainTimeBean;
import com.kaurihealth.kaurihealth.services.IServiceFactory;
import com.kaurihealth.kaurihealth.services.ServiceFactory;
import com.kaurihealth.kaurihealth.util.Enums.LoadingStatu;
import com.kaurihealth.kaurihealth.util.ImagSizeMode;
import com.kaurihealth.kaurihealth.util.PicassoImageLoadUtil;
import com.kaurihealth.kaurihealth.util.Url;
import com.youyou.zllibrary.thread.ThreadPoolUtil;
import com.youyou.zllibrary.util.CommonActivity;
import com.kaurihealth.kaurihealth.adapter.CommonViewAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PatientRelActivity extends CommonActivity {
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
    @Bind(R.id.floatbtn_chat)
    FloatingActionButton floatbtnChat;
    @Bind(R.id.floatbtn_active)
    FloatingActionButton floatbtnActive;
    private View left;
    private View right;
    private DoctorPatientRelationshipBean doctorPatientRelationshipBean;
    private TextView tvType;
    private TextView tvDay;
    private TextView tvTime;
    private TextView tvReason;
    private TextView tvTel;
    private TextView tvAdress;
    private TextView tv_more;
    private Bundle bundle;
    private int patientId;
    private String kauriHealthId;
    private CalculateEndTimeInterface calculateEndTimeInterface;
    Handler handler = new Handler();
    private TextView tvTimeTag;
    private LinearLayout timeLay;
    private IDoctorPatientRelationshipService doctorPatientRelationshipService;
    private IServiceFactory serviceFactory;
    private IIsActive iIsActive;
    private ISetBundleHealthyRecord iSetBundleHealthyRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_info);
        ButterKnife.bind(this);
        init();
    }

    @Override
    public void init() {
        super.init();
        IBundleFactory bundleFactory = new BundleFactory();
        iIsActive = new IsActive();
        serviceFactory = new ServiceFactory(Url.prefix, getApplicationContext());
        doctorPatientRelationshipService = serviceFactory.getDoctorPatientRelationshipService();

        setBack(R.id.iv_back);
        bundle = getBundle();
        iSetBundleHealthyRecord = bundleFactory.getISetBundleHealthyRecord(bundle);
        doctorPatientRelationshipBean = (DoctorPatientRelationshipBean)
                bundle.getSerializable(ConfigBundleHealthyRecord.CurrentDoctorPatientRelationship);


        patientId = doctorPatientRelationshipBean.patient.patientId;
        kauriHealthId = doctorPatientRelationshipBean.patient.kauriHealthId;
        iSetBundleHealthyRecord.setPatientId(patientId);
        iSetBundleHealthyRecord.setkauriHealthId(kauriHealthId);
        iSetBundleHealthyRecord.setGender(doctorPatientRelationshipBean.patient.gender);
        iSetBundleHealthyRecord.setName(doctorPatientRelationshipBean.patient.fullName);
        iSetBundleHealthyRecord.setBirthDate(doctorPatientRelationshipBean.patient.dateOfBirth);
        left = LayoutInflater.from(this).inflate(R.layout.vp_patient_info_left, null);
        right = LayoutInflater.from(this).inflate(R.layout.vp_patient_info_right, null);

        if (iIsActive.isActive(doctorPatientRelationshipBean.isActive, doctorPatientRelationshipBean.endDate)) {
            floatbtnActive.setVisibility(View.GONE);
            floatbtnChat.setVisibility(View.VISIBLE);
            iSetBundleHealthyRecord.setAble(true);
        } else {
            iSetBundleHealthyRecord.setAble(false);
            floatbtnActive.setVisibility(View.VISIBLE);
            floatbtnChat.setVisibility(View.GONE);
        }
        List<View> list = new ArrayList<>();
        list.add(left);
        list.add(right);
        CommonViewAdapter adapter = new CommonViewAdapter(list);
        vpagerTop.setAdapter(adapter);
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
        Bind();
        bindDataToView();

        if (doctorPatientRelationshipBean.endDate != null) {
            calculateEndTimeInterface = CalculateEndTimeUtil.getCalculateEndTimeInterface(doctorPatientRelationshipBean.endDate);
            timeLay.setVisibility(View.VISIBLE);
            tvTimeTag.setVisibility(View.GONE);
        } else {
            calculateEndTimeInterface = null;
            timeLay.setVisibility(View.GONE);
            tvTimeTag.setVisibility(View.VISIBLE);
        }

    }

    private boolean flag = true;

    @Override
    protected void onPause() {
        super.onPause();
        flag = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (calculateEndTimeInterface == null) {
            return;
        }
        flag = true;
        ThreadPoolUtil.execute(new Runnable() {
            @Override
            public void run() {
                while (flag) {
                    final RemainTimeBean endTime = calculateEndTimeInterface.getEndTime();
                    try {
                        Thread.sleep(1000);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                tvDay.setText(endTime.getDay() + "天");
//                                tvTime.setWidth(160);
                                tvTime.setText(String.format("%2d:%2d:%2d", endTime.getHour(), endTime.getMinutes(), endTime.getSecond()));
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
//                        Bugtags.sendException(e);
                    }

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
     * 将数据加载到界面
     */
    private void bindDataToView() {
        if (doctorPatientRelationshipBean == null) {
            return;
        }
        PicassoImageLoadUtil.loadUrlToImageView(doctorPatientRelationshipBean.patient.getAvatar(),
                ivPhotoTopleftPatientinfo, getApplicationContext(), ImagSizeMode.imageSizeBeens[4]);

        setText(doctorPatientRelationshipBean.patient.fullName, tvName);
        setText(doctorPatientRelationshipBean.patient.getDateOfBirth(), tvAge);
        setText(doctorPatientRelationshipBean.getRelationshipReason(), tvType);
        setText(doctorPatientRelationshipBean.patient.gender, tvGendar);
        setText(doctorPatientRelationshipBean.getComment(), tvReason);
        setText(doctorPatientRelationshipBean.patient.getEmergencyContactNumber(), tvTel);
        setText(doctorPatientRelationshipBean.patient.getAddress(), tvAdress);
        //为请求原因添加监听
        ViewTreeObserver observer = tvReason.getViewTreeObserver(); //textAbstract为TextView控件
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ViewTreeObserver obs = tvReason.getViewTreeObserver();
                obs.removeGlobalOnLayoutListener(this);
                if (tvReason.getLineCount() > 3) {
                    int lineEndIndex = tvReason.getLayout().getLineEnd(2); //设置第3行打省略号
                    String text = tvReason.getText().subSequence(0, lineEndIndex - 3) + "...更多";
                    tvReason.setText(text);
                    tvReason.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            new SweetAlertDialog(PatientRelActivity.this).setTitleText("请求原因").setContentText(doctorPatientRelationshipBean.getComment()).show();
                        }
                    });
                }
            }
        });

        boolean isActive = iIsActive.isActive(doctorPatientRelationshipBean.isActive, doctorPatientRelationshipBean.endDate);
        if (isActive) {
            ivPhotoTopleftPatientinfo.setImageResource(R.mipmap.ic_login_head_default);
            ivPhotoTopleftPatientinfo.setBorderColor(PatientRelActivity.this.getResources().getColor(R.color.civPhoto));
            tvTime.setTextColor(getResources().getColor(R.color.textblue));
            tvDay.setBackgroundResource(R.mipmap.timebg);
            tvType.setTextColor(getResources().getColor(R.color.tvType));
            timeLay.setBackgroundResource(R.drawable.bg_edt_red);
        } else {
            ivPhotoTopleftPatientinfo.setImageResource(R.mipmap.ic_avatar_over);
            ivPhotoTopleftPatientinfo.setBorderColor(PatientRelActivity.this.getResources().getColor(R.color.texthomeiteam));
            tvDay.setBackgroundResource(R.mipmap.timebg_not_ative);
            tvType.setTextColor(getResources().getColor(R.color.texthomeiteam));
            timeLay.setBackgroundResource(R.drawable.bg_edt_gar_gray);
            tvTime.setTextColor(getResources().getColor(R.color.texthomeiteam));
        }

    }


    private void Bind() {
        timeLay = (LinearLayout) left.findViewById(R.id.lay);
        //服务项目
        tvType = (TextView) left.findViewById(R.id.tvType);
        //剩余时间天
        tvDay = (TextView) left.findViewById(R.id.tvDay);
        //剩余时间
        tvTime = (TextView) left.findViewById(R.id.tvTime);
        //时间的tag 如果endData为null  让其显示，否则不显示
        tvTimeTag = (TextView) left.findViewById(R.id.tvTimeTag);
        //预约原因
        tvReason = (TextView) left.findViewById(R.id.tvReason);

        //联系电话

        tvTel = (TextView) right.findViewById(R.id.tvTel);
        //联系地址
        tvAdress = (TextView) right.findViewById(R.id.tvAdress);
    }

    /**
     * 健康记录
     */
    @OnClick(R.id.layHealthyRecord)
    public void healthhistory() {
        setBundle(bundle);
        skipTo(HealthyRecordNewActivity.class, bundle);
    }

    /**
     * 长期监测指标
     */
    @OnClick(R.id.lay_monitorstandard)
    public void monitorstandard() {
        setBundle(bundle);
        skipTo(LongHealthyStandardActivity.class, bundle);
    }

    private int TagMedicaHistoryActivity = 10;

    /**
     * 医疗记录
     */
    @OnClick(R.id.layMedicalhistory)
    public void medicalhistory() {
        setBundle(bundle);
        skipToForResult(MedicaHistoryActivity.class, bundle, TagMedicaHistoryActivity);
    }

    /**
     * 处方
     */
    @OnClick(R.id.layRecipe)
    public void recipe() {
        setBundle(bundle);
        skipTo(PrescriptionActivity.class, bundle);
    }

    /**
     * 医生团队
     */
    @OnClick(R.id.layTeam)
    public void doctorteam() {
        setBundle(bundle);
        skipTo(ExpertsActivity.class, bundle);
    }


    private void setBundle(Bundle bundle) {
        iSetBundleHealthyRecord.setAble(iIsActive.isActive(doctorPatientRelationshipBean.isActive, doctorPatientRelationshipBean.endDate));
    }

    @OnClick({R.id.floatbtn_chat, R.id.floatbtn_active})
    public void choose(View view) {
        switch (view.getId()) {
            case R.id.floatbtn_chat:
                Bundle bundle = new Bundle();
                bundle.putSerializable("CurrentDoctorPatientRelationship", doctorPatientRelationshipBean);
                skipTo(SingleChatActivity.class, bundle);
                break;
            case R.id.floatbtn_active:
                Call<DoctorPatientRelationshipBean> doctorPatientRelationshipBeanCall = doctorPatientRelationshipService.InsertNewRelationshipByDoctor(doctorPatientRelationshipBean.patientId);
                doctorPatientRelationshipBeanCall.enqueue(new Callback<DoctorPatientRelationshipBean>() {
                    @Override
                    public void onResponse(Call<DoctorPatientRelationshipBean> call, Response<DoctorPatientRelationshipBean> response) {
                        if (response.isSuccessful()) {

                            showToast("复诊成功");
                            floatbtnChat.setVisibility(View.VISIBLE);
                            floatbtnActive.setVisibility(View.GONE);
                            finishCur();
                        }
                    }

                    @Override
                    public void onFailure(Call<DoctorPatientRelationshipBean> call, Throwable t) {
                        showToast(LoadingStatu.NetError.value);
                        t.printStackTrace();
                    }
                });
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        判断是否更新当前界面
        if (requestCode == TagMedicaHistoryActivity) {
            if (resultCode == Activity.RESULT_OK) {
                boolean updataCur = data.getBooleanExtra(ConfigBundleHealthyRecord.UpdataPatientRelActivity, false);
                if (updataCur) {
                    if (doctorPatientRelationshipBean.relationshipReason.equals("远程医疗咨询")) {
                        if (doctorPatientRelationshipBean.endDate == null) {
                            timeCalculate();

                        }
                    }
                }
            }
        }
    }


    private void timeCalculate(){
        Calendar instance = Calendar.getInstance();
        instance.set(Calendar.DAY_OF_YEAR, instance.get(Calendar.DAY_OF_YEAR) + 7);
        doctorPatientRelationshipBean.endDate = instance.getTime();
        calculateEndTimeInterface = CalculateEndTimeUtil.getCalculateEndTimeInterface(doctorPatientRelationshipBean.endDate);
        timeLay.setVisibility(View.VISIBLE);
        tvTimeTag.setVisibility(View.GONE);
    }
}
