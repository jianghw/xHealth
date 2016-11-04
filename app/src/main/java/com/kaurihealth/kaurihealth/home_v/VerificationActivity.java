package com.kaurihealth.kaurihealth.home_v;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.kaurihealth.datalib.request_bean.bean.NewDoctorRelationshipBean;
import com.kaurihealth.datalib.response_bean.DoctorRelationshipBean;
import com.kaurihealth.datalib.response_bean.ResponseDisplayBean;
import com.kaurihealth.kaurihealth.MyApplication;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.kaurihealth.eventbus.DoctorRelationshipBeanEvent;
import com.kaurihealth.kaurihealth.eventbus.DoctorTeamJumpEvent;
import com.kaurihealth.kaurihealth.eventbus.VerificationJumpEvent;
import com.kaurihealth.mvplib.home_p.IVerificationView;
import com.kaurihealth.mvplib.home_p.VerificationPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by mip on 2016/9/19.
 * 验证消息界面
 */
public class VerificationActivity extends BaseActivity implements IVerificationView {
    @Inject
    VerificationPresenter<IVerificationView> mPresenter;

    @Bind(R.id.edtVerification)
    EditText edtVerification;
    @Bind(R.id.tv_more)
    TextView tvMore;

    private int relatedDoctorId;

    @Override
    protected int getActivityLayoutID() {
        return R.layout.verification_activity;
    }

    @Override
    protected void initPresenterAndView(Bundle savedInstanceState) {
        MyApplication.getApp().getComponent().inject(this);
        mPresenter.setPresenter(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void eventBusMain(VerificationJumpEvent event) {
        //DoctorRelationshipBean doctorRelationshipBean = event.getBean();
        //加载VerificationActivity
        tvMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editTextIsEmpter()) addNewFriend();
            }
        });
        showToast("跳转界面成功");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //注销事件
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void initDelayedData() {
        initNewBackBtn("验证信息");
        tvMore.setText("发送");

        Bundle bundle = getBundle();
        if (bundle!=null){

            relatedDoctorId = bundle.getInt("relatedDoctorId", -1);
        }
        //注册事件
        EventBus.getDefault().register(this);
    }

    /**
     * ``````````````点击事件```````````````
     */
    //添加按钮
    @OnClick({R.id.tv_more})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_more:
                if (editTextIsEmpter()) addNewFriend();
                break;
            default:
                break;
        }
    }

    public boolean editTextIsEmpter() {
        if (getEditText() == null || getEditText().isEmpty() || getEditText().length() < 1) {
            displayErrorDialog("你不写点什么吗?");
            return false;
        }
        return true;
    }

    public String getEditText() {
        return edtVerification.getText().toString().trim();
    }

    private void addNewFriend() {
        if (relatedDoctorId != -1) {
            mPresenter.onSubscribe();//网络请求
        }
    }

    @Override
    public void switchPageUI(String className) {
//TODO
    }

    /**
     * 得到当前的请求bean
     */
    @Override
    public NewDoctorRelationshipBean getCurrentNewDoctorRelationshipBean() {
        NewDoctorRelationshipBean requestBean = new NewDoctorRelationshipBean();
        requestBean.relatedDoctorId = relatedDoctorId;
        requestBean.description = getEditText();
        return requestBean;
    }

    /**
     * 得到的请求返回的结果
     */
    @Override
    public void getRequestResult(ResponseDisplayBean bean) {
        showToast("请求成功发送");
        setResult(RESULT_OK, null);
        finishCur();
    }
}
