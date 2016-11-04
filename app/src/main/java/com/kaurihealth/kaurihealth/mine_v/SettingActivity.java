package com.kaurihealth.kaurihealth.mine_v;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.TextView;

import com.kaurihealth.datalib.local.LocalData;
import com.kaurihealth.datalib.response_bean.DoctorDisplayBean;
import com.kaurihealth.datalib.response_bean.TokenBean;
import com.kaurihealth.kaurihealth.MyApplication;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.kaurihealth.login_v.LoginActivity;
import com.kaurihealth.mvplib.mine_p.ISettingView;
import com.kaurihealth.mvplib.mine_p.SettingPresenter;
import com.kaurihealth.utilslib.ActivityManager;
import com.kaurihealth.utilslib.SharedUtils;
import com.kaurihealth.utilslib.constant.Global;
import com.kaurihealth.utilslib.SysUtil;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;


/**
 * 我的Button 里面的设置   created by Nick
 */
public class SettingActivity extends BaseActivity implements ISettingView {
    //当前版本
    @Bind(R.id.tvVersionName)
    TextView tvVersionName;

    @Inject
    SettingPresenter<ISettingView> mPresenter;

    @Override
    protected int getActivityLayoutID() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initPresenterAndView(Bundle savedInstanceState) {
        MyApplication.getApp().getComponent().inject(this);
        mPresenter.setPresenter(this);
        initVersion();
    }

    //初始化版本号
    private void initVersion() {
        String versionName = SysUtil.getVersionName(getApplicationContext());
        if (versionName != null) {
            String curVersion = SharedUtils.getString(this, Global.Environment.ENVIRONMENT, Global.Environment.PREVIEW);
            String version = curVersion.equals(Global.Environment.PREVIEW) ? "正式版"
                    : curVersion.equals(Global.Environment.TEST) ? "测试版" : "开发版";
            tvVersionName.setText(String.format("当前版本:%s,当前平台:%s", versionName, version));
        }
    }

    @Override
    protected void initDelayedData() {
        initNewBackBtn(getString(R.string.tv_title_setting));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.unSubscribe();
    }

    /**
     * ----------------------------继承基础mvpView方法-----------------------------------
     */
    @Override
    public void switchPageUI(String className) {

    }

    //修改密码              服务协议             服务说明              意见反馈            客服热线            退出登录
    @OnClick({R.id.lay_resetpass, R.id.lay_agreement, R.id.secret_setting, R.id.opinion_feedback, R.id.lay_hotline, R.id.btn_logout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lay_resetpass:
                skipTo(SetPasswordActivity.class);
                break;
            case R.id.lay_agreement:
                skipTo(ServiceAgreementActivity.class);
                break;
            case R.id.secret_setting:
                skipTo(SetupprotocolActivityNew.class);

                break;
            case R.id.opinion_feedback:
                skipTo(FeedbackActivity.class);
                break;
            case R.id.lay_hotline:
                //拨打佳仁热线
                callHelpDesk();
                break;
            case R.id.btn_logout:
                LocalData.getLocalData().deleteAll(TokenBean.class);
                LocalData.getLocalData().deleteAll(DoctorDisplayBean.class);
                LocalData.getLocalData().setTokenBean(null);
                LocalData.getLocalData().setMyself(null);
                ActivityManager.getInstance().finishAllActivity();
                skipTo(LoginActivity.class);
                break;
        }
    }

    //调用拨打电话Api
    public void callHelpDesk() {
        SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.CUSTOM_IMAGE_TYPE);
        pDialog.setTitleText("佳仁客服热线");
        pDialog.setContentText("021-60522067");
        pDialog.setCancelText("取消");
        pDialog.setConfirmText("确定");
        pDialog.showCancelButton(true);
        pDialog.setConfirmClickListener(sweetAlertDialog -> {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "02160522067"));
            if (ActivityCompat.checkSelfPermission(SettingActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            SettingActivity.this.startActivity(intent);
        });
        pDialog.show();
    }

}
