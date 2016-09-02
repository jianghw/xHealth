package com.kaurihealth.kaurihealth.patient_v;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.kaurihealth.datalib.request_bean.bean.AddressDisplayBean;
import com.kaurihealth.datalib.response_bean.DoctorPatientRelationshipBean;
import com.kaurihealth.datalib.response_bean.PatientDisplayBean;
import com.kaurihealth.kaurihealth.MyApplication;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.adapter.CommonViewAdapter;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.kaurihealth.common.activity.MedicaHistoryActivity;
import com.kaurihealth.kaurihealth.common.activity.PrescriptionActivity;
import com.kaurihealth.kaurihealth.eventbus.LongHStandardBeanEvent;
import com.kaurihealth.kaurihealth.eventbus.PatientInfoShipBeanEvent;
import com.kaurihealth.kaurihealth.patientwithdoctor.activity.ExpertsActivity;
import com.kaurihealth.kaurihealth.patientwithdoctor.activity.HealthyRecordNewActivity;
import com.kaurihealth.kaurihealth.util.ImagSizeMode;
import com.kaurihealth.kaurihealth.util.PicassoImageLoadUtil;
import com.kaurihealth.mvplib.patient_p.IPatientInfoView;
import com.kaurihealth.mvplib.patient_p.PatientInfoPresenter;
import com.kaurihealth.utilslib.constant.Global;
import com.kaurihealth.utilslib.date.DateUtils;
import com.kaurihealth.utilslib.date.RemainTimeBean;
import com.kaurihealth.utilslib.dialog.DialogUtils;
import com.kaurihealth.utilslib.widget.CircleImageView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

public class PatientInfoActivity extends BaseActivity implements IPatientInfoView {

    /**
     * 标注，提示这是需要依赖导入的对象
     */
    @Inject
    PatientInfoPresenter<IPatientInfoView> mPresenter;

    @Bind(R.id.iv_back)
    ImageView mIvBack;
    @Bind(R.id.iv_photo_topleft_patientinfo)
    CircleImageView mIvPhotoTopleftPatientinfo;
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
    @Bind(R.id.floatbtn_chat)
    FloatingActionButton mFloatbtnChat;
    @Bind(R.id.floatbtn_active)
    FloatingActionButton mFloatbtnActive;

    TextView mTvTel;  //联系电话
    TextView mTvAdress;//联系地址
    TextView mTvType; //服务项目
    TextView mTvDay; //剩余时间天
    TextView mTvTime;//剩余时间
    LinearLayout mLay;
    TextView mTvTimeTag;//时间的tag 如果endData为null  让其显示，否则不显示
    TextView mTvReason; //预约原因
    private DoctorPatientRelationshipBean mDoctorPatientRelationshipBean;


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
        if (mDoctorPatientRelationshipBean != null) mDoctorPatientRelationshipBean = null;
        mPresenter.unSubscribe();
        //注销事件
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected int getActivityLayoutID() {
        return R.layout.activity_patient_info;
    }

    @Override
    protected void initPresenterAndData(Bundle savedInstanceState) {
        MyApplication.getApp().getComponent().inject(this);
        mPresenter.setPresenter(this);
    }


    @Override
    protected void initDelayedView() {
        initBackBtn(R.id.iv_back);

        initViewPager();
        //注册事件
        EventBus.getDefault().register(this);
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

    @Override
    public void switchPageUI(String className) {
        switch (className) {
            case Global.Jump.HealthyRecordNewActivity:
                skipTo(HealthyRecordNewActivity.class);
                break;
            case Global.Jump.LongHealthyStandardActivity:
                skipTo(LongHealthyStandardActivity.class);
                EventBus.getDefault().postSticky(new LongHStandardBeanEvent(mDoctorPatientRelationshipBean));
                break;
            default:
                break;
        }
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
        skipTo(MedicaHistoryActivity.class);
    }

    /**
     * 处方
     */
    @OnClick(R.id.layRecipe)
    public void recipe() {
        skipTo(PrescriptionActivity.class);
    }

    /**
     * 医生团队
     */
    @OnClick(R.id.layTeam)
    public void doctorteam() {
        skipTo(ExpertsActivity.class);
    }

    @OnClick({R.id.floatbtn_chat, R.id.floatbtn_active})
    public void choose(View view) {
        switch (view.getId()) {
            case R.id.floatbtn_chat:
                break;
            case R.id.floatbtn_active:
                mPresenter.onSubscribe();
                break;
            default:
                break;
        }
    }

    /**
     * ----------------------------订阅事件----------------------------------
     *
     * @Subscribe 下的方法必须为public
     * postSticky()发送的粘性消息订阅时必须@Subscribe(sticky = true)否则接收不到
     * 发送的event事件是object类
     * @Subscribe(priority = 1) 使用时优先级默认为0，然后只有统一模式下设置优先级才有效果，自己看着合理使用
     **/

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void eventBusMain(PatientInfoShipBeanEvent event) {
        DoctorPatientRelationshipBean bean = event.getBean();
        updateBeanData(bean);
    }

    @Override
    public void updateBeanData(DoctorPatientRelationshipBean bean) {
        if (bean == null) return;
        mDoctorPatientRelationshipBean = bean;

        Date endDate = bean.getEndDate();
        boolean isActive = DateUtils.isActive(bean.isIsActive(), endDate);
        setFloatingActionButton(isActive);
        accordingToDesign(isActive);

        initViewPagerData(bean);

        mLay.setVisibility(endDate != null ? View.VISIBLE : View.GONE);
        mTvTimeTag.setVisibility(endDate != null ? View.GONE : View.VISIBLE);
        if (endDate != null) startCountdown(endDate);
    }

    @Override
    public DoctorPatientRelationshipBean getDoctorPatientRelationshipBean() {
        if (mDoctorPatientRelationshipBean == null)
            throw new IllegalStateException("mDoctorPatientRelationshipBean must be not null");
        return mDoctorPatientRelationshipBean;
    }

    @Override
    public void setFloatingActionButton(boolean active) {
        mFloatbtnChat.setVisibility(active ? View.VISIBLE : View.GONE);
        mFloatbtnActive.setVisibility(active ? View.GONE : View.VISIBLE);
    }

    /**
     * 实现是否激活的状态
     *
     * @param isActive
     */
    private void accordingToDesign(boolean isActive) {
        mIvPhotoTopleftPatientinfo.setImageResource(isActive ? R.mipmap.ic_login_head_default : R.mipmap.ic_avatar_over);
        mIvPhotoTopleftPatientinfo.setBorderColor(getColor(isActive, R.color.civPhoto, R.color.texthomeiteam));
        mTvTime.setTextColor(getColor(isActive, R.color.textblue, R.color.texthomeiteam));
        mTvDay.setBackgroundResource(isActive ? R.mipmap.timebg : R.mipmap.timebg_not_ative);
        mTvType.setTextColor(getColor(isActive, R.color.tvType, R.color.texthomeiteam));
        mLay.setBackgroundResource(isActive ? R.drawable.bg_edt_red : R.drawable.bg_edt_gar_gray);
    }

    private int getColor(boolean flag, int isTrue, int isFalse) {
        return getResources().getColor(flag ? isTrue : isFalse);
    }

    /**
     * viewpager 初始化
     *
     * @param bean
     */
    private void initViewPagerData(DoctorPatientRelationshipBean bean) {
        PatientDisplayBean patientDisplayBean = bean.getPatient();
        if (patientDisplayBean == null) return;

        PicassoImageLoadUtil.loadUrlToImageView(patientDisplayBean.getAvatar(),
                mIvPhotoTopleftPatientinfo,
                getApplicationContext(),
                ImagSizeMode.imageSizeBeens[4]);

        displaysTextContent(bean);
    }

    /**
     * 显示文本内容
     *
     * @param shipBean
     */
    private void displaysTextContent(DoctorPatientRelationshipBean shipBean) {
        PatientDisplayBean displayBean = shipBean.getPatient();
        if (displayBean == null) return;

        setText(displayBean.getFullName(), mTvName);
        int age = DateUtils.getAge(displayBean.getDateOfBirth());
        setText(getString(R.string.patient_age, age), mTvAge);
        setText(displayBean.getEmergencyContactNumber() == null ? "暂无" : displayBean.getEmergencyContactNumber(), mTvTel);
        setText(getAddress(displayBean), mTvAdress);
        setText(displayBean.getGender(), mTvGendar);

        setText(shipBean.getModifyRelationshipReason(), mTvType);

        String shipBeanComment = shipBean.getComment();
        setText(shipBeanComment, mTvReason);

        textContentProcessing(shipBeanComment);
    }

    private String getAddress(PatientDisplayBean displayBean) {
        AddressDisplayBean addressBean = displayBean.getAddress();
        if (addressBean == null) {
            return "暂无";
        } else {
            if (addressBean.addressLine1 == null) return "暂无";
            return addressBean.addressLine1;
        }
    }

    /**
     * 文本处理 为请求原因添加监听
     *
     * @param shipBeanComment
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
                    mTvReason.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            DialogUtils.sweetDialog(PatientInfoActivity.this, "请求原因", shipBeanComment);
                        }
                    });
                }
            }
        });
    }

    /**
     * 开始倒计时
     *
     * @param endDate
     */
    private void startCountdown(Date endDate) {
        mPresenter.startCountdown(endDate);
    }

    /**
     * 倒计时
     *
     * @param bean
     */
    @Override
    public void showCountdown(RemainTimeBean bean) {
        if (bean == null) return;
        mTvDay.setText(String.format("%02d", bean.getDay()) + "天");
        mTvTime.setText(String.format("%02d:%02d:%02d", bean.getHour(), bean.getMinutes(), bean.getSecond()));
    }
}
