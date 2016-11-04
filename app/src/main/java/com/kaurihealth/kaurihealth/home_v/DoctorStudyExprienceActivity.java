package com.kaurihealth.kaurihealth.home_v;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;

import butterknife.Bind;

/**
 * Created by mip on 2016/9/30.
 * <p/>
 * Describe:
 */
public class DoctorStudyExprienceActivity extends BaseActivity {

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

    }

    @Override
    protected void initDelayedData() {
        initNewBackBtn("学习与工作经历");

        tv_more.setVisibility(View.GONE);

        String practiceField = (String) getBundle().get("workingExperience");
        edt_practice.setText(practiceField == null ? "暂无" : practiceField);
        edt_practice.setFocusable(false);
    }
}
