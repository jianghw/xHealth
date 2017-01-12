package com.kaurihealth.kaurihealth.manager_v.record;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kaurihealth.chatlib.utils.LCIMConstants;
import com.kaurihealth.datalib.response_bean.AddressBean;
import com.kaurihealth.datalib.response_bean.PatientBean;
import com.kaurihealth.datalib.response_bean.PatientRecordCountDisplayBean;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.eventbus.RecordPatientToReferralDoctorEvent;
import com.kaurihealth.kaurihealth.record_details_v.RecordDetailsActivity;
import com.kaurihealth.kaurihealth.referrals_v.ReferralDoctorActivity;
import com.kaurihealth.utilslib.CheckUtils;
import com.kaurihealth.utilslib.TranslationAnim;
import com.kaurihealth.utilslib.controller.AbstractViewController;
import com.kaurihealth.utilslib.date.DateUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jianghw on 2016/12/15.
 * <p/>
 * Describe: 病人头部
 */

public class RecordPatientView extends AbstractViewController<PatientRecordCountDisplayBean> implements View.OnClickListener {

    @Bind(R.id.tv_patient_name)
    TextView mTvPatientName;
    @Bind(R.id.img_gender)
    ImageView mImgGender;
    @Bind(R.id.tv_patient_age)
    TextView mTvPatientAge;
    @Bind(R.id.tv_type)
    TextView mTvType;
    @Bind(R.id.tv_phone)
    TextView mTvPhone;
    @Bind(R.id.tv_address)
    TextView mTvAddress;
    @Bind(R.id.tv_referral)
    CardView mTvReferral;//转诊
    @Bind(R.id.tv_chat)
    CardView mTvChat;
    @Bind(R.id.cv_patient)
    CardView mCvPatient;
    //添加
    @Bind(R.id.tv_add)
    CardView tv_add;

    private String kauriHealthId;
    private int mPatientId;

    RecordPatientView(Context context) {
        super(context);
    }

    @Override
    protected int getResLayoutId() {
        return R.layout.include_record_patient;
    }

    @Override
    protected void onCreateView(View view) {
        ButterKnife.bind(this, view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void bindViewData(PatientRecordCountDisplayBean data) {
        iniTypeView(data);

        PatientBean patientBean = data.getPatient();
        mPatientId = patientBean.getPatientId();
//patientBean 必不为空
        kauriHealthId = patientBean.getKauriHealthId();
        String fullName = patientBean.getFullName();
        mTvPatientName.setText(CheckUtils.checkTextContent(fullName));
        mImgGender.setImageResource(patientBean.getGender().contains("男") ? R.drawable.gender_icon : R.drawable.gender_women);
        mTvPatientAge.setText(DateUtils.calculateAge(patientBean.getDateOfBirth()) + "岁");
        mTvPhone.setText(CheckUtils.checkTextContent(patientBean.getPhoneNumber()));
        mTvType.setText(CheckUtils.checkTextContent(patientBean.getPatientType()));
        mTvType.setVisibility(View.GONE);
        AddressBean addressBean = patientBean.getAddress();
        if (addressBean == null) {
            mTvAddress.setText(CheckUtils.checkTextContent(null));
        } else {
            mTvAddress.setText(CheckUtils.checkTextContent(addressBean.getAddressLine1()));
        }
    }

    /**
     * 设置view
     */
    private void iniTypeView(PatientRecordCountDisplayBean data) {
        mTvReferral.setVisibility(data.isFriend ? View.VISIBLE : View.GONE);
        tv_add.setVisibility(data.isFriend ? View.GONE : View.VISIBLE);
    }

    @Override
    protected void defaultViewData() {
        mTvPatientName.setText(CheckUtils.checkTextContent(null));
        mImgGender.setImageResource(R.drawable.gender_icon);
        mTvPatientAge.setText("0岁");
        mTvPhone.setText(CheckUtils.checkTextContent(null));
        mTvType.setText(CheckUtils.checkTextContent(null));
        mTvAddress.setText(CheckUtils.checkTextContent(null));
    }

    @Override
    protected void unbindView() {
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.tv_referral, R.id.tv_chat, R.id.tv_add})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_referral://转诊
                EventBus.getDefault().postSticky(new RecordPatientToReferralDoctorEvent(mPatientId));
                TranslationAnim.zlStartActivity((RecordDetailsActivity) mContext, ReferralDoctorActivity.class, null);
                break;
            case R.id.tv_chat://聊天
                try {
                    Intent intent = new Intent();
                    intent.setPackage(mContext.getPackageName());
                    intent.setAction(LCIMConstants.CONVERSATION_ITEM_CLICK_ACTION);
                    intent.addCategory(Intent.CATEGORY_DEFAULT);
                    intent.putExtra(LCIMConstants.PEER_ID, kauriHealthId);
                    mContext.startActivity(intent);
                } catch (ActivityNotFoundException exception) {
                    Log.i(LCIMConstants.LCIM_LOG_TAG, exception.toString());
                }
                break;
            case R.id.tv_add:
                listener.refresh();
                break;
        }
    }

    private void stateTypeView(boolean isActive) {
        mTvReferral.setVisibility(isActive ? View.VISIBLE : View.GONE);
        mTvChat.setVisibility(isActive ? View.VISIBLE : View.GONE);
    }

    @Override
    protected void showLayout() {
        mCvPatient.setVisibility(View.VISIBLE);
    }

    @Override
    protected void hiddenLayout() {
        mCvPatient.setVisibility(View.GONE);
    }

    private IRefreshListener listener;

    public void setListener(IRefreshListener listener) {
        this.listener = listener;
    }

    public interface IRefreshListener {
        void refresh();
    }
}
