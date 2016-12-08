package com.kaurihealth.kaurihealth.conversation_v;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.kaurihealth.datalib.response_bean.NotifyConfigDisplayBean;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.kaurihealth.tinker.SampleApplicationLike;
import com.kaurihealth.mvplib.patient_p.ISystemMessageSettingView;
import com.kaurihealth.mvplib.patient_p.SystemMessageSettingPresenter;
import com.kaurihealth.utilslib.dialog.DialogUtils;
import com.kaurihealth.utilslib.dialog.PopUpNumberPickerDialog;
import com.rey.material.widget.Switch;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by jianghw on 2016/11/9.
 * <p/>
 * Describe:设置系统消息
 */

public class SystemMessageSettingActivity extends BaseActivity implements ISystemMessageSettingView {
    @Inject
    SystemMessageSettingPresenter<ISystemMessageSettingView> mPresenter;

    @Bind(R.id.switch_system_meg)
    Switch mSwitchSystemMeg;
    @Bind(R.id.rl_clean_msg)
    RelativeLayout mRlCleanMsg;


    @Override
    protected int getActivityLayoutID() {
        return R.layout.activity_system_message_setting;
    }

    @Override
    protected void initPresenterAndView(Bundle savedInstanceState) {
        SampleApplicationLike.getApp().getComponent().inject(this);
        mPresenter.setPresenter(this);

        initNewBackBtn(getString(R.string.system_message_setting));
    }

    @Override
    protected void initDelayedData() {
        mPresenter.loadNotifyConfig();

        mSwitchSystemMeg.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(Switch view, boolean checked) {
                mPresenter.updateNotifyConfig(checked);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.unSubscribe();
    }

    @OnClick(R.id.rl_clean_msg)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_clean_msg:
                String[] gender = getResources().getStringArray(R.array.system_setting);
                DialogUtils.showStringDialog(this, gender, new PopUpNumberPickerDialog.SetClickListener() {
                    @Override
                    public void onClick(int index) {
                        if (index == 0) mPresenter.onSubscribe();
                    }
                });
                break;
        }
    }

    @Override
    public void switchPageUI(String className) {

    }

    @Override
    public void updateUserNotifySuccessful() {
        setResult(RESULT_OK);
        showToast("信息删除成功");
    }

    @Override
    public void initSwitchState(NotifyConfigDisplayBean bean) {
        mSwitchSystemMeg.setChecked(bean.isIsNotify());
    }
}
