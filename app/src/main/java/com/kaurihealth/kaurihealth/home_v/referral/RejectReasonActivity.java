package com.kaurihealth.kaurihealth.home_v.referral;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.kaurihealth.datalib.request_bean.bean.PatientRequestDecisionBean;
import com.kaurihealth.datalib.request_bean.builder.PatientRequestDecisionBeanBuilder;
import com.kaurihealth.datalib.response_bean.ResponseDisplayBean;
import com.kaurihealth.kaurihealth.MyApplication;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.kaurihealth.eventbus.HomefragmentPatientEvent;
import com.kaurihealth.kaurihealth.eventbus.RejectReasonEvent;
import com.kaurihealth.mvplib.home_p.IRejectReasonView;
import com.kaurihealth.mvplib.home_p.RejectReasonPresenter;
import com.kaurihealth.utilslib.OnClickUtils;
import com.kaurihealth.utilslib.ValidatorUtils;
import com.kaurihealth.utilslib.constant.Global;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by mip on 2016/9/21.
 * <p/>
 * Describe: 拒绝预约意见
 */
public class RejectReasonActivity extends BaseActivity implements IRejectReasonView, Validator.ValidationListener {
    @Inject
    RejectReasonPresenter<IRejectReasonView> mPresenter;

    @NotEmpty(message = "请输入您的理由")
    @Bind(R.id.edt_name)
    EditText edt_name;

    @Bind(R.id.tv_more)
    TextView tvMore;

    private Validator validator;
    private int patientRequestId;

    @Override
    protected int getActivityLayoutID() {
        return R.layout.activity_reject_reason;
    }

    @Override
    protected void initPresenterAndView(Bundle savedInstanceState) {
        MyApplication.getApp().getComponent().inject(this);
        mPresenter.setPresenter(this);

        initNewBackBtn("拒绝预约");
        tvMore.setText(getString(R.string.title_complete));
        //表单校验器初始化
        validator = new Validator(this);
        validator.setValidationListener(this);
    }

    @Override
    protected void initDelayedData() {
        EventBus.getDefault().register(this);
    }

    /**
     * 订阅事件
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void eventBusMain(RejectReasonEvent event) {
        patientRequestId = event.getPatientRequestId();
    }


    /**
     * 完成
     */
    @OnClick(R.id.tv_more)
    public void complete() {
        if (OnClickUtils.onNoDoubleClick()) return;
        validator.validate();
    }

    @Override
    public void onValidationSucceeded() {
        mPresenter.onSubscribe();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        String message = ValidatorUtils.validationErrorMessage(getApplicationContext(), errors);
        displayErrorDialog(message);
    }

    @Override
    public void switchPageUI(String className) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.unSubscribe();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 得到当前的bean
     */
    @Override
    public PatientRequestDecisionBean getCurrentPatientRequestDecisionBean() {
        return new PatientRequestDecisionBeanBuilder().Build(patientRequestId, edt_name.getText().toString().trim());
    }

    @Override
    public void getRequestResult(ResponseDisplayBean bean) {
        EventBus.getDefault().postSticky(new HomefragmentPatientEvent(Global.RequestCode.REJECT));
        setResult(RESULT_OK);
        finishCur();
    }
}
