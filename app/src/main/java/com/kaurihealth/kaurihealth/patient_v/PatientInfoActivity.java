package com.kaurihealth.kaurihealth.patient_v;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kaurihealth.chatlib.utils.LCIMConstants;
import com.kaurihealth.datalib.request_bean.bean.AddressDisplayBean;
import com.kaurihealth.datalib.response_bean.DoctorPatientRelationshipBean;
import com.kaurihealth.datalib.response_bean.PatientDisplayBean;
import com.kaurihealth.kaurihealth.MyApplication;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.adapter.CommonViewAdapter;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.kaurihealth.eventbus.HealthConditionBeanEvent;
import com.kaurihealth.kaurihealth.eventbus.LongHStandardBeanEvent;
import com.kaurihealth.kaurihealth.eventbus.MedicalRecordIdEvent;
import com.kaurihealth.kaurihealth.eventbus.PatientFragmentEvent;
import com.kaurihealth.kaurihealth.eventbus.PatientInfoShipBeanEvent;
import com.kaurihealth.kaurihealth.eventbus.PrescriptionBeanEvent;
import com.kaurihealth.kaurihealth.eventbus.ReferralDoctorEvent;
import com.kaurihealth.kaurihealth.patient_v.health_condition.HealthConditionActivity;
import com.kaurihealth.kaurihealth.patient_v.long_monitoring.LongHealthyStandardActivity;
import com.kaurihealth.kaurihealth.patient_v.medical_records.MedicalRecordActivity;
import com.kaurihealth.kaurihealth.patient_v.referrals_patient.ReferralDoctorActivity;
import com.kaurihealth.mvplib.patient_p.IPatientInfoView;
import com.kaurihealth.mvplib.patient_p.PatientInfoPresenter;
import com.kaurihealth.utilslib.CheckUtils;
import com.kaurihealth.utilslib.constant.Global;
import com.kaurihealth.utilslib.date.DateUtils;
import com.kaurihealth.utilslib.date.RemainTimeBean;
import com.kaurihealth.utilslib.dialog.DialogUtils;
import com.kaurihealth.utilslib.image.ImageUrlUtils;
import com.kaurihealth.utilslib.widget.CircleImageView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 患者信息显示页面
 */
public class PatientInfoActivity extends BaseActivity implements IPatientInfoView {
    @Inject
    PatientInfoPresenter<IPatientInfoView> mPresenter;

    TextView mTvTel;  //联系电话
    TextView mTvAdress;//联系地址
    TextView mTvType; //服务项目
    TextView mTvDay; //剩余时间天
    TextView mTvTime;//剩余时间
    LinearLayout mLay;//倒计时框
    TextView mTvTimeTag;//时间的tag 如果endData为null  让其显示，否则不显示
    TextView mTvReason; //预约原因

    @Bind(R.id.iv_photo)
    CircleImageView mIvPhoto;
    @Bind(R.id.tv_name)
    TextView mTvName;
    @Bind(R.id.tv_gendar)
    TextView mTvGendar;
    @Bind(R.id.tv_age)
    TextView mTvAge;
    @Bind(R.id.vpager_top)
    ViewPager mVpagerTop;
    @Bind(R.id.rbtn_one)
    RadioButton mRbtnOne;
    @Bind(R.id.rbtn_two)
    RadioButton mRbtnTwo;
    @Bind(R.id.layHealthyRecord)
    LinearLayout mLayHealthyRecord;
    @Bind(R.id.lay_monitorstandard)
    LinearLayout mLayMonitorstandard;
    @Bind(R.id.layMedicalhistory)
    LinearLayout mLayMedicalhistory;
    @Bind(R.id.layRecipe)
    LinearLayout mLayRecipe;
    @Bind(R.id.layTeam)
    LinearLayout mLayTeam;
    @Bind(R.id.lay_family)
    LinearLayout mLayFamily;
    @Bind(R.id.rl_info)
    RelativeLayout rlInfo;
    @Bind(R.id.floatbtn_chat_new)
    Button floatbtnChat;
    @Bind(R.id.floatbtn_active_new)
    Button floatbtnActive;
    @Bind(R.id.LY_referral_layout)
    LinearLayout LY_referral_layout;//转诊布局 //暂时屏蔽
    @Bind(R.id.tv_refrral_name)
    TextView tv_refrral_name;//转诊人
    @Bind(R.id.tv_referral_reason)
    TextView tv_referral_reason;//转诊原因
    @Bind(R.id.tv_referral_time)
    TextView tv_referral_time;//转诊时间
    @Bind(R.id.floatbtn_referral_new)//暂时屏蔽
    Button floatbtn_referral;

    @Bind(R.id.RL_middle_btn)
    RelativeLayout RL_middle_btn;//中间按钮
    @Bind(R.id.floatbtn_chat_middle_btn)
    Button floatbtn_chat_middle_btn;//中间聊天按钮
    @Bind(R.id.floatbtn_active_middle_btn)
    Button floatbtn_active_middle_btn;//中间复诊按钮
    @Bind(R.id.LY_referral_layout_gone)
    LinearLayout LY_referral_layout_gone;

    private boolean isActiveMode;//激活状态
    private DoctorPatientRelationshipBean doctorPatientRelationshipBean;
    private int doctorPatientReplationshipId;
    private int TagMedicaHistoryActivity = 10;

    @Override
    protected int getActivityLayoutID() {
        return R.layout.activity_patient_info;
    }

    @Override
    protected void initPresenterAndView(Bundle savedInstanceState) {
        MyApplication.getApp().getComponent().inject(this);
        mPresenter.setPresenter(this);

        initNewBackBtn(getString(R.string.patient_tv_title));
        initViewPager();
    }

    @Override
    protected void initDelayedData() {
        //注册事件
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.unSubscribe();
        //注销事件
        EventBus.getDefault().unregister(this);
    }

    private void initViewPager() {
        View left = LayoutInflater.from(this).inflate(R.layout.vp_patient_info_left, null);
        mLay = (LinearLayout) left.findViewById(R.id.lay);
        mTvType = (TextView) left.findViewById(R.id.tvType);
        mTvDay = (TextView) left.findViewById(R.id.tvDay);
        mTvTime = (TextView) left.findViewById(R.id.tvTime);
        mTvTimeTag = (TextView) left.findViewById(R.id.tvTimeTag);
        mTvReason = (TextView) left.findViewById(R.id.tvReason);

        View right = LayoutInflater.from(this).inflate(R.layout.vp_patient_info_right, null);
        mTvTel = (TextView) right.findViewById(R.id.tvTel);
        mTvAdress = (TextView) right.findViewById(R.id.tvAdress);
        mTvAdress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(PatientInfoActivity.this)
                        .setMessage(getTvValue(mTvAdress))
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

        List<View> list = new ArrayList<>();
        list.add(left);
        list.add(right);
        CommonViewAdapter adapter = new CommonViewAdapter(list);
        mVpagerTop.setAdapter(adapter);
        mVpagerTop.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mRbtnOne.setChecked(position == 0);
                mRbtnTwo.setChecked(position == 1);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 健康记录
     */
    @OnClick(R.id.layHealthyRecord)
    public void healthHistory() {
        switchPageUI(Global.Jump.HealthyRecordNewActivity);
    }

    /**
     * 长期监测指标
     */
    @OnClick(R.id.lay_monitorstandard)
    public void monitorStandard() {
        switchPageUI(Global.Jump.LongHealthyStandardActivity);
    }

    /**
     * 医疗记录
     */
    @OnClick(R.id.layMedicalhistory)
    public void medicalhistory() {
        switchPageUI(Global.Jump.MedicalRecordActivity);
    }

    /**
     * 处方
     */
    @OnClick(R.id.layRecipe)
    public void recipe() {
        switchPageUI(Global.Jump.PrescriptionActivity);
    }

    /**
     * 医生团队
     */
    @OnClick(R.id.layTeam)
    public void doctorteam() {
        //TODO 跳转到协作医生界面
        int patient = 10;
        Bundle bundle = new Bundle();
        bundle.putInt("patientId", getPatientId());
        skipToForResult(DoctorTeamActivity.class, bundle, patient);
        //skipTo(DoctorTeamActivity.class);
    }

    @OnClick(R.id.lay_family)
    public void family() {
        skipTo(FamilyMembersActivity.class);
    }

    @OnClick({R.id.floatbtn_chat_new, R.id.floatbtn_active_new, R.id.floatbtn_referral_new, R.id.floatbtn_chat_middle_btn, R.id.floatbtn_active_middle_btn})
    public void choose(View view) {
        switch (view.getId()) {
            case R.id.floatbtn_chat_new://跳转聊天界面
                jumpChatActivity();
                break;
            case R.id.floatbtn_active_new:
                mPresenter.onSubscribe();
                break;
            case R.id.floatbtn_chat_middle_btn://跳转聊天界面
                jumpChatActivity();
                break;
            case R.id.floatbtn_active_middle_btn:
                mPresenter.onSubscribe();
                break;
            case R.id.floatbtn_referral_new://转诊
                EventBus.getDefault().postSticky(new ReferralDoctorEvent(doctorPatientRelationshipBean));
                skipTo(ReferralDoctorActivity.class);
                break;
            default:
                break;
        }
    }

    /**
     * 聊天
     */
    private void jumpChatActivity() {  //跳转到聊天界面-->数据如何接收？
        try {
            Intent intent = new Intent();
            intent.setPackage(getPackageName());
            intent.setAction(LCIMConstants.CONVERSATION_ITEM_CLICK_ACTION);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.putExtra(LCIMConstants.PEER_ID, doctorPatientRelationshipBean.getPatient().getKauriHealthId());
            startActivity(intent);
        } catch (ActivityNotFoundException exception) {
            Log.i(LCIMConstants.LCIM_LOG_TAG, exception.toString());
        }
    }

    @Override
    public void switchPageUI(String className) {
        switch (className) {
            case Global.Jump.HealthyRecordNewActivity://健康记录
                skipTo(HealthConditionActivity.class);
                EventBus.getDefault().postSticky(new HealthConditionBeanEvent(doctorPatientRelationshipBean, isActiveMode));
                break;
            case Global.Jump.LongHealthyStandardActivity://长期监测指标
                EventBus.getDefault().postSticky(new LongHStandardBeanEvent(getPatientId(), isActiveMode, doctorPatientRelationshipBean));
                skipTo(LongHealthyStandardActivity.class);
                break;
            case Global.Jump.MedicalRecordActivity://医疗记录
                EventBus.getDefault().postSticky(new MedicalRecordIdEvent(getPatientId(), getShipBean(), isActiveMode));
                skipToForResult(MedicalRecordActivity.class, null, TagMedicaHistoryActivity);
                break;
            case Global.Jump.PrescriptionActivity://处方
                EventBus.getDefault().postSticky(new PrescriptionBeanEvent(doctorPatientRelationshipBean, isActiveMode));
                skipTo(PrescriptionActivity.class);
                break;
            default:
                break;
        }
    }

    /**
     * ----------------------------订阅事件----------------------------------
     * <p>  患者数据初始化
     * #@Subscribe 下的方法必须为public
     * postSticky()发送的粘性消息订阅时必须@Subscribe(sticky = true)否则接收不到
     * 发送的event事件是object类
     * #@Subscribe(priority = 1) 使用时优先级默认为0，然后只有统一模式下设置优先级才有效果，自己看着合理使用
     **/
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void eventBusMain(PatientInfoShipBeanEvent event) {
        updateBeanData(event.getBean());
    }

    @Override
    public void updateBeanData(DoctorPatientRelationshipBean bean) {
        if (bean == null) return;
        doctorPatientRelationshipBean = bean;
        doctorPatientReplationshipId = bean.getDoctorPatientId();

        Date endDate = bean.getEndDate();
        boolean isActive = DateUtils.isActive(bean.isIsActive(), endDate);
        isActiveMode = isActive;
        setFloatingActionButton(isActive, bean.getModifyRelationshipReason().equals("转诊患者服务"));
        accordingToDesign(isActive);

        displaysTextContent(bean);

        mLay.setVisibility(endDate != null ? View.VISIBLE : View.GONE);
        mTvTimeTag.setVisibility(endDate != null ? View.GONE : View.VISIBLE);
        if (endDate != null) startCountdown(endDate);
    }

    /**
     * 得到医患id
     */
    @Override
    public int getDoctorPatientId() {
        return doctorPatientReplationshipId;
    }

    @Override
    public void visitSuccessful() {
        EventBus.getDefault().postSticky(new PatientFragmentEvent());
        showToast("复诊成功");
    }


    @Override
    public int getPatientId() {
        return doctorPatientRelationshipBean != null ? doctorPatientRelationshipBean.getPatientId() : -1;
    }

    @Override
    public DoctorPatientRelationshipBean getShipBean() {
        return doctorPatientRelationshipBean;
    }

    @Override
    public void setFloatingActionButton(boolean active, boolean b) {
        floatbtnChat.setVisibility(active ? View.VISIBLE : View.GONE);
        floatbtnActive.setVisibility(active ? View.GONE : View.VISIBLE);

        floatbtn_referral.setVisibility(b ? View.GONE : View.VISIBLE);

        RL_middle_btn.setVisibility(b ? View.VISIBLE : View.GONE);
        LY_referral_layout_gone.setVisibility(b ? View.GONE : View.VISIBLE);
//        floatbtn_chat_middle_btn.setVisibility(b && active ? View.VISIBLE : View.GONE);
        floatbtn_active_middle_btn.setVisibility(b && active ? View.GONE : View.VISIBLE);
    }

    /**
     * 实现是否激活的状态
     */
    private void accordingToDesign(boolean isActive) {
        if(CheckUtils.checkUrlNotNull(doctorPatientRelationshipBean.getPatient().getAvatar())){
            ImageUrlUtils.picassoByUrlCircle(this, doctorPatientRelationshipBean.getPatient().getAvatar(), mIvPhoto);
        }else{
            mIvPhoto.setImageResource(isActive ? R.mipmap.ic_circle_head_green : R.mipmap.ic_avatar_over);
        }
        mIvPhoto.setBorderColor(getColor(isActive, R.color.color_enable_green, R.color.texthomeiteam));
        mTvTime.setTextColor(getColor(isActive, R.color.color_enable_green, R.color.texthomeiteam));
        mTvDay.setBackgroundResource(isActive ? R.mipmap.timebg : R.mipmap.timebg_not_ative);
        mTvType.setTextColor(getColor(isActive, R.color.tvType, R.color.texthomeiteam));
        mLay.setBackgroundResource(isActive ? R.drawable.bg_edt_red : R.drawable.bg_edt_gar_gray);
    }

    private int getColor(boolean flag, int isTrue, int isFalse) {
        return getResources().getColor(flag ? isTrue : isFalse);
    }

    /**
     * 显示文本内容
     */
    private void displaysTextContent(DoctorPatientRelationshipBean shipBean) {  //**
        PatientDisplayBean displayBean = shipBean.getPatient();
        if (displayBean != null) {
            setText(displayBean.getFullName(), mTvName);
            int age = DateUtils.calculateAge(displayBean.getDateOfBirth());
            setText(age + "岁", mTvAge);
            setText(displayBean.getMobileNumber() == null ? "暂无" : displayBean.getMobileNumber(), mTvTel);
            setText(getAddress(displayBean), mTvAdress);
            setText(displayBean.getGender(), mTvGendar);
        }

        //转诊患者
        LY_referral_layout.setVisibility(shipBean.getModifyRelationshipReason().equals("转诊服务") ? View.VISIBLE : View.GONE);
        if (LY_referral_layout.getVisibility() == View.VISIBLE) {
            setText(shipBean.getReferralDoctor(), tv_refrral_name);
            setText(shipBean.getReferralReason(), tv_referral_reason);
            setText(DateUtils.GetDateText(shipBean.getReferralDate(), "yyyy:MM:dd HH:mm"), tv_referral_time);
        }

        setText(shipBean.getModifyRelationshipReason(), mTvType);
        String shipBeanComment = shipBean.getComment();  //请求原因 String
        setText(shipBeanComment, mTvReason);

        textContentProcessing(shipBeanComment);
    }

    private String getAddress(PatientDisplayBean displayBean) {
        AddressDisplayBean addressBean = displayBean.getAddress();
        if (addressBean == null) {
            return "暂无";
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append(addressBean.getProvince());
            sb.append(addressBean.getCity());
            sb.append(addressBean.getDistrict());
            if (addressBean.addressLine1 != null) sb.append(addressBean.getAddressLine1());
            return sb.toString();
        }
    }

    /**
     * 文本处理 为请求原因添加监听
     * 描述:  文本省略号逻辑
     */
    private void textContentProcessing(final String shipBeanComment) {
        ViewTreeObserver observer = mTvReason.getViewTreeObserver(); //textAbstract为TextView控件
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ViewTreeObserver obs = mTvReason.getViewTreeObserver();
                obs.removeGlobalOnLayoutListener(this);
                if (mTvReason.getLineCount() > 3) {
                    int lineEndIndex = mTvReason.getLayout().getLineEnd(2); //设置第3行打省略号
                    String text = mTvReason.getText().subSequence(0, lineEndIndex - 3) + "...更多";
                    mTvReason.setText(text);
                    mTvReason.setOnClickListener(view -> DialogUtils.sweetDialog(PatientInfoActivity.this, "请求原因", shipBeanComment));
                }
            }
        });
    }

    /**
     * 开始倒计时
     */
    private void startCountdown(Date endDate) {
        mPresenter.startCountdown(endDate);
    }

    /**
     * 倒计时 注意
     */
    @Override
    public void showCountdown(RemainTimeBean bean) {
        if (bean == null) return;
        mTvDay.setText(String.format("%02d", bean.getDay()) + "天");
        mTvTime.setText(String.format("%02d:%02d:%02d", bean.getHour(), bean.getMinutes(), bean.getSecond()));

        if (bean.getDay() == 0 && bean.getHour() == 0 && bean.getMinutes() == 0 && bean.getSecond() == 0) {
            isActiveMode = false;
            setFloatingActionButton(false, true);
            accordingToDesign(false);
            mPresenter.closeCountDown();
        }
    }

    /**
     * 远程患者更新本地倒计时及重新刷新patientFragment
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK
                && requestCode == TagMedicaHistoryActivity
                && doctorPatientRelationshipBean.getEndDate() == null) {
            if (doctorPatientRelationshipBean.getRelationshipReason().equals("远程医疗咨询")) {
                boolean updataCur = data.getBooleanExtra("ADDEND", false);
                if (updataCur) {
                    mLay.setVisibility(View.VISIBLE);
                    mTvTimeTag.setVisibility(View.GONE);
                    mTvDay.setBackgroundResource(updataCur ? R.mipmap.timebg : R.mipmap.timebg_not_ative);
                    EventBus.getDefault().postSticky(new PatientFragmentEvent());
                    Calendar instance = Calendar.getInstance();
                    instance.set(Calendar.DAY_OF_YEAR, instance.get(Calendar.DAY_OF_YEAR) + 7);
                    mPresenter.startCountdown(instance.getTime());
                }
            }
        }
    }
}
