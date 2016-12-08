package com.kaurihealth.kaurihealth.patient_v.subsequent_visit;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.kaurihealth.datalib.request_bean.bean.NewReferralMessageAlertBean;
import com.kaurihealth.datalib.request_bean.builder.NewReferralMessageAlertBeanBuilder;
import com.kaurihealth.datalib.response_bean.ReferralMessageAlertDisplayBean;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.kaurihealth.tinker.SampleApplicationLike;
import com.kaurihealth.mvplib.patient_p.AddVisitRecordPresenter;
import com.kaurihealth.mvplib.patient_p.IAddVisitRecordView;
import com.kaurihealth.utilslib.OnClickUtils;
import com.kaurihealth.utilslib.constant.Global;
import com.kaurihealth.utilslib.date.DateUtils;
import com.kaurihealth.utilslib.dialog.DialogUtils;
import com.kaurihealth.utilslib.dialog.SelectAllDateDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by jianghw on 2016/11/9.
 * <p/>
 * Describe:
 */
public class AddVisitRecordActivity extends BaseActivity implements IAddVisitRecordView {
    @Inject
    AddVisitRecordPresenter<IAddVisitRecordView> mPresenter;

    @Bind(R.id.tv_more)
    TextView mTvMore;
    @Bind(R.id.edt_name)
    EditText mEdtName;
    @Bind(R.id.tv_time)
    TextView mTvTime;

    private int doctorPatientReplationshipId;

    @Override
    protected int getActivityLayoutID() {
        return R.layout.activity_add_visit_record;
    }

    @Override
    protected void initPresenterAndView(Bundle savedInstanceState) {
        SampleApplicationLike.getApp().getComponent().inject(this);
        mPresenter.setPresenter(this);

        initNewBackBtn(getString(R.string.add_visit_record));
        mTvMore.setText(getString(R.string.title_save));
    }

    @Override
    protected void initDelayedData() {
        Bundle bundle = getBundle();
        if (bundle != null) {
            doctorPatientReplationshipId = bundle.getInt(Global.Bundle.VISIT_PATIENT);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.unSubscribe();
    }

    @OnClick(R.id.tv_time)
    public void setTimeClick() {
        DialogUtils.showAllDateDialog(this, new SelectAllDateDialog.DialogListener() {
            @Override
            public void onclick(String year, String month, String day, String hour, String minute) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(year);
                stringBuilder.append("-");
                stringBuilder.append(month);
                stringBuilder.append("-");
                stringBuilder.append(day);
                stringBuilder.append(" ");
                stringBuilder.append(hour);
                stringBuilder.append(":");
                stringBuilder.append(minute);
                mTvTime.setText(stringBuilder.toString());
            }
        });
    }

    @OnClick(R.id.tv_more)
    public void insertHealthCondition() {
        if (OnClickUtils.onNoDoubleClick()) return;
        if (getTvTime().equals("请选择日期")) {
            displayErrorDialog("请先选择提醒日期,这很重要!");
        } else if (getEdtName() == null || getEdtName() != null && getEdtName().length() < 1) {
            displayErrorDialog("请填写提醒事件,这很重要!");
        } else {
            mPresenter.onSubscribe();
        }
    }


    @Override
    public void switchPageUI(String className) {

    }

    @Override
    public NewReferralMessageAlertBean getNewReferralMessageAlertBean() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年-M月-d日 HH时:mm分", Locale.SIMPLIFIED_CHINESE);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.SIMPLIFIED_CHINESE);
        try {
            Date date = simpleDateFormat.parse(getTvTime());
            Date today = DateUtils.getNowDate();
            return new NewReferralMessageAlertBeanBuilder().Build(sdf.parse(sdf.format(date)), getEdtName(), doctorPatientReplationshipId, today);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        throw new IllegalStateException("time is error");
    }

    @Override
    public void insertHealthConditionSucceed(ReferralMessageAlertDisplayBean bean) {
        if (bean == null) {
            showToast("添加复诊消息失败,请重新添加~");
        } else {
            showToast(bean.isIsSend() ? "添加复诊消息成功,消息已发送" : "添加复诊消息成功,消息将按计划发送");
            setResult(RESULT_OK);
            finishCur();
        }
    }

    public String getEdtName() {
        return mEdtName.getText().toString().trim();
    }

    public String getTvTime() {
        return mTvTime.getText().toString().trim();
    }
}
