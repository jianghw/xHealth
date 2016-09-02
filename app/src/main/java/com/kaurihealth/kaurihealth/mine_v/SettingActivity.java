package com.kaurihealth.kaurihealth.mine_v;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.TextView;

import com.kaurihealth.kaurihealth.MyApplication;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.kaurihealth.login.activity.LoginActivity;
import com.kaurihealth.kaurihealth.mine.activity.SetupprotocolActivity;
import com.kaurihealth.kaurihealth.util.ExitApplication;
import com.kaurihealth.mvplib.mine_p.ISettingView;
import com.kaurihealth.mvplib.mine_p.SettingPresenter;
import com.kaurihealth.utilslib.bugtag.EnvironmentConfig;
import com.youyou.zllibrary.util.SpUtil;
import com.youyou.zllibrary.util.SysUtil;

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
    TextView  tvVersionName;

    @Inject
    SettingPresenter<ISettingView> mPresenter;

    @Override
    protected int getActivityLayoutID() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initPresenterAndData(Bundle savedInstanceState) {
        MyApplication.getApp().getComponent().inject(this);
        mPresenter.setPresenter(this);
        init();

    }
    //初始化版本号
    private void init() {
        String versionName = SysUtil.getVersionName(getApplicationContext());
        if (versionName!=null){
            tvVersionName.setText(String.format("当前版本:%s,当前平台:%s",versionName, EnvironmentConfig.CurVersion.toString()));
        }
    }

    @Override
    protected void initDelayedView() {
        initBackBtn(R.id.iv_back);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

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

    }

    /**
     * ----------------------------继承基础mvpView方法-----------------------------------
     *
     * @param className*/

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
                skipTo(SetupprotocolActivity.class);
                break;
            case R.id.opinion_feedback:
                skipTo(FeedbackActivity.class);
                break;
            case R.id.lay_hotline:
                //拨打佳仁热线
                callHelpDesk();
                break;
            case R.id.btn_logout:
                SpUtil spUtil =SpUtil.getInstance(getApplicationContext());
                spUtil.put("Auto",false);
                ExitApplication.getInstance().exit();
                skipTo(LoginActivity.class);
                break;
        }
    }

    //调用拨打电话Api
    public void callHelpDesk(){
        SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.CUSTOM_IMAGE_TYPE);
        pDialog.setTitleText("佳仁客服热线");
        pDialog.setContentText("021-60522067");
        pDialog.setCancelText("取消");
        pDialog.setConfirmText("确定");
        pDialog.showCancelButton(true);
        pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "02160522067"));
                if (ActivityCompat.checkSelfPermission(SettingActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                SettingActivity.this.startActivity(intent);
            }
        });
        pDialog.show();
    }

}
