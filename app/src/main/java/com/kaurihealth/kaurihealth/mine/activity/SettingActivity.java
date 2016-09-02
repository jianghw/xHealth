package com.kaurihealth.kaurihealth.mine.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.TextView;

import com.kaurihealth.kaurihealth.mine_v.FeedbackActivity;
import com.kaurihealth.utilslib.bugtag.EnvironmentConfig;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.login.activity.LoginActivity;
import com.kaurihealth.kaurihealth.mine_v.ServiceAgreementActivity;
import com.kaurihealth.kaurihealth.util.ExitApplication;
import com.youyou.zllibrary.util.CommonActivity;
import com.youyou.zllibrary.util.SpUtil;
import com.youyou.zllibrary.util.SysUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * 版权:    张磊
 * 作者:    张磊
 * 描述：
 * 修订日期:
 */
public class SettingActivity extends CommonActivity {
    //当前版本
    @Bind(R.id.tvVersionName)
    TextView tvVersionName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        init();
        ExitApplication.getInstance().addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
            //修改密码                  服务协议             服务说明                  意见反馈           客服热线            退出登录
    @OnClick({R.id.lay_resetpass, R.id.lay_agreement, R.id.secret_setting, R.id.opinion_feedback, R.id.lay_hotline, R.id.btn_logout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lay_resetpass:
                skipTo(SetPassWordActivity.class);
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
                tohotline();
                break;
            case R.id.btn_logout:
                SpUtil spUtil =SpUtil.getInstance(getApplicationContext());
                spUtil.put("Auto",false);
                ExitApplication.getInstance().exit();
                skipTo(LoginActivity.class);
                break;
        }
    }

    //客服热线
    public void tohotline() {
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



    public void init() {
        super.init();
        setBack(R.id.iv_back);
        String versionName = SysUtil.getVersionName(getApplicationContext());
        if (versionName != null) {
            tvVersionName.setText(String.format("当前版本：%s，当前平台：%s", versionName, EnvironmentConfig.CurVersion.toString()));
        }
    }
}
