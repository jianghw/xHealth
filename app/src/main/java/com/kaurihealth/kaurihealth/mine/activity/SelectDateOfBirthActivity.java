package com.kaurihealth.kaurihealth.mine.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.kaurihealth.utilslib.dialog.SelectDateDialog;
import com.kaurihealth.kaurihealth.R;
import com.youyou.zllibrary.util.CommonActivity;
import com.youyou.zllibrary.util.TimeUtils;
import com.youyou.zllibrary.util.Utils;

import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 版权:    张磊
 * 作者:    张磊
 * 描述：
 * 修订日期:
 */
public class SelectDateOfBirthActivity extends CommonActivity {

    @Bind(R.id.tv_age)
    TextView tvAge;
    private String dateOfBirth;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_age);
        ButterKnife.bind(this);
        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    Dialog dateDialog = null;

    Calendar calendar = Calendar.getInstance();
    @Override
    public void init() {
        super.init();
        bundle = getBundle();
        final TimeUtils timeUtils = new TimeUtils();
        if (bundle != null) {
            dateOfBirth = bundle.getString("dateOfBirth");
            calendar.setTime(Utils.stringToDate(dateOfBirth));
            if (dateOfBirth != null) {
                timeUtils.setTime(dateOfBirth);
                tvAge.setText(timeUtils.getAge() + "岁");
            }
        }

        setBack(R.id.iv_back);
        SelectDateDialog selectDateDialog = new SelectDateDialog(this, new SelectDateDialog.DialogListener() {
            @Override
            public void onclick(String year, String month, String day) {
                calendar.set(Integer.valueOf(year), Integer.valueOf(month), Integer.valueOf(day));
                timeUtils.setTime(year, month, day, "00", "00", "00");
                age = timeUtils.getAge();
                tvAge.setText(String.format("%d岁", age));
                dateOfBirth = timeUtils.getTime();
            }
        });
        dateDialog = selectDateDialog.getDataDialog();
    }

    int age;

    @OnClick({R.id.tv_age, R.id.tv_operate})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_age:
                changgeDataOfBirth();
                break;
            case R.id.tv_operate:
                store();
                break;
        }
    }

    public void changgeDataOfBirth() {
        if (dateDialog != null) {
            dateDialog.show();
        }
    }

    /**
     * 保存
     */
    public void store() {
        Intent intent = getIntent();
        bundle.putSerializable("dateOfBirth", calendar.getTime());
        intent.putExtras(bundle);
        setResult(Activity.RESULT_OK, intent);
        finishCur();
    }
}
