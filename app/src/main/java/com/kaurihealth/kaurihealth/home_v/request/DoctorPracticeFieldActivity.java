package com.kaurihealth.kaurihealth.home_v.request;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.utilslib.constant.Global;

import butterknife.Bind;

/**
 * Created by KauriHealth on 2016/9/21.
 */
public class DoctorPracticeFieldActivity extends BaseActivity {

    @Bind(R.id.edt_practice)
    TextView edt_practice;
    @Bind(R.id.tv_more)
    TextView tv_more;

    @Override
    protected int getActivityLayoutID() {
        return R.layout.activity_enter_practice_field;
    }

    @Override
    protected void initPresenterAndView(Bundle savedInstanceState) {
        initNewBackBtn("专业特长");
        tv_more.setVisibility(View.GONE);
    }

    @Override
    protected void initDelayedData() {
        String practiceField = (String) getBundle().get(Global.Bundle.REQUEST_DOCTOR_PRACTICE);
        edt_practice.setText(practiceField != null ?"暂无": practiceField);
        edt_practice.setFocusable(false);
    }
}
