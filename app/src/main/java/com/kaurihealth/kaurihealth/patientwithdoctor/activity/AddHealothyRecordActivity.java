package com.kaurihealth.kaurihealth.patientwithdoctor.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.patientwithdoctor.bean.HealthyRecordNewActivityBean;
import com.youyou.zllibrary.util.CommonActivity;
import com.youyou.zllibrary.util.StringUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddHealothyRecordActivity extends CommonActivity {

    @Bind(R.id.edtDiseaseName)
    EditText edtDiseaseName;

    @Bind(R.id.tv_txtname)
    TextView tv_txtname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_healothy_record);
        ButterKnife.bind(this);
        init();
    }

    @Override
    public void init() {
        super.init();
        setBack(R.id.iv_back);
        HealthyRecordNewActivityBean bean = (HealthyRecordNewActivityBean) getBundle().getSerializable(HealthyRecordNewActivity.Key);
        tv_txtname.setText(bean.getTxtName());
        edtDiseaseName.setText(bean.getValue());
    }

    @OnClick(R.id.tv_operate)
    public void onclictv_operate() {
        returnResult();

    }


    private void returnResult() {
        if (StringUtils.isEmpty(getTvValue(edtDiseaseName))) {
            finishCur();
        } else {
            Intent intent = getIntent();
            Bundle bundle = getBundle();
            HealthyRecordNewActivityBean bean = (HealthyRecordNewActivityBean) getBundle().getSerializable(HealthyRecordNewActivity.Key);
            bean.setValue(getTvValue(edtDiseaseName));
            bundle.putSerializable(HealthyRecordNewActivity.Key, bean);
            intent.putExtras(bundle);
            setResult(RESULT_OK, intent);
            finishCur();
        }
    }

    @Override
    public void onBackPressed() {
        returnResult();
    }
}
