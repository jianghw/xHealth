package com.kaurihealth.kaurihealth.patient_v.health_condition;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.kaurihealth.eventbus.HealthConditionFragmentAddEvent;
import com.kaurihealth.kaurihealth.eventbus.HealthConditionFragmentEditEvent;
import com.kaurihealth.kaurihealth.record_details_v.HealthConditionFragment;
import com.kaurihealth.kaurihealth.record_details_v.bean.HealthyRecordNewActivityBean;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 描述 : 给患者添加医疗记录界面
 */

public class AddHealothyRecordActivity extends BaseActivity {

    @Bind(R.id.edtDiseaseName)
    EditText edtDiseaseName;

    @Bind(R.id.tv_txtname)
    TextView tv_txtname;

    @Override
    protected int getActivityLayoutID() {
        return R.layout.add_healothy_record;
    }

    @Override
    protected void initPresenterAndView(Bundle savedInstanceState) {
        initBackBtn(R.id.iv_back);
    }

    @Override
    protected void initDelayedData() {
        HealthyRecordNewActivityBean bean = (HealthyRecordNewActivityBean) getBundle().getSerializable(HealthConditionFragment.Key);
        tv_txtname.setText(bean.getTxtName());
        edtDiseaseName.setText(bean.getValue());
    }


    @OnClick(R.id.tv_operate)
    public void onclictv_operate() {
        returnResult();
    }


    private void returnResult() {
        if (TextUtils.isEmpty(getTvValue(edtDiseaseName))) {
            finishCur();
        } else {
            Intent intent = getIntent();
            Bundle bundle = getBundle();
            HealthyRecordNewActivityBean bean = (HealthyRecordNewActivityBean) getBundle().getSerializable(HealthConditionFragment.Key);


            bean.setValue(getTvValue(edtDiseaseName));
            String ADDOREDIT = bundle.getString("ADDOREDIT");
            if (ADDOREDIT.equals("add")){
                EventBus.getDefault().postSticky(new HealthConditionFragmentAddEvent(bean));
            }else {
                EventBus.getDefault().postSticky(new HealthConditionFragmentEditEvent(bean));
            }

//            bundle.putSerializable(HealthConditionFragment.Key, bean);
//            intent.putExtras(bundle);
//            setResult(RESULT_OK, intent);
            finishCur();
        }
    }

    @Override
    public void onBackPressed() {
        returnResult();
    }
}
