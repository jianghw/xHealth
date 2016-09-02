package com.kaurihealth.kaurihealth.mine.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioButton;

import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.util.ExitApplication;
import com.kaurihealth.kaurihealth.adapter.MainFragmentAdapter;
import com.kaurihealth.kaurihealth.mine.fragment.SetupprotocolActivityFragments.ExclusiveFragment;
import com.kaurihealth.kaurihealth.mine.fragment.SetupprotocolActivityFragments.OutpatientFragment;
import com.kaurihealth.kaurihealth.mine.fragment.SetupprotocolActivityFragments.RemoteFragment;
import com.youyou.zllibrary.util.CommonFragmentActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 版权:    张磊
 * 作者:    张磊
 * 描述：
 * 修订日期:
 */
public class SetupprotocolActivity extends CommonFragmentActivity {

    @Bind(R.id.rbtn_clinical_treatment_medicalhistory)
    RadioButton rbtnClinicalTreatmentMedicalhistory;
    @Bind(R.id.rbt_assist_check_medicalhistory)
    RadioButton rbtAssistCheckMedicalhistory;
    @Bind(R.id.rbtn_labcheck_medicalhistory)
    RadioButton rbtnLabcheckMedicalhistory;
    @Bind(R.id.vp_content_main)
    ViewPager vpagerContent;

    private List<Fragment> fragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setupprotocol);
        ButterKnife.bind(this);
        init();
        ExitApplication.getInstance().addActivity(this);
    }

    @Override
    public void init() {
        super.init();
        setBack(R.id.iv_back);
        Fragment exclusiveFragment = new ExclusiveFragment();//专属医生服务
        Fragment remoteFragment = new RemoteFragment();//远程医疗服务
        Fragment outpatientFragment = new OutpatientFragment();//门诊

        fragmentList = new ArrayList<>();
        fragmentList.add(exclusiveFragment);
        fragmentList.add(remoteFragment);
        fragmentList.add(outpatientFragment);

        MainFragmentAdapter mainFragmentAdapter = new MainFragmentAdapter(getSupportFragmentManager(), fragmentList);
        vpagerContent.setAdapter(mainFragmentAdapter);
        vpagerContent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setOneChecked(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @OnClick({R.id.rbtn_clinical_treatment_medicalhistory, R.id.rbt_assist_check_medicalhistory, R.id.rbtn_labcheck_medicalhistory})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rbtn_clinical_treatment_medicalhistory:
                vpagerContent.setCurrentItem(0);
                break;
            case R.id.rbt_assist_check_medicalhistory:
                vpagerContent.setCurrentItem(1);
                break;
            case R.id.rbtn_labcheck_medicalhistory:
                vpagerContent.setCurrentItem(2);
                break;
        }
    }

    private void setOneChecked(int index) {
        switch (index) {
            case 0:
                rbtnClinicalTreatmentMedicalhistory.setChecked(true);
                break;
            case 1:
                rbtAssistCheckMedicalhistory.setChecked(true);
                break;
            case 2:
                rbtnLabcheckMedicalhistory.setChecked(true);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
