package com.kaurihealth.kaurihealth.patientwithdoctor.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.kaurihealth.datalib.request_bean.bean.DepartmentDisplayWrapBean;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.patientwithdoctor.adapter.DepartmentLevel1Adapter;
import com.kaurihealth.kaurihealth.util.Getter;
import com.kaurihealth.kaurihealth.util.Interface.IGetter;
import com.youyou.zllibrary.util.CommonActivity;

import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DepartmentLevel1Activity extends CommonActivity {
    public static final int ToDepartmentLevel = 2;
    public final int ToLevel2 = 1;
    @Bind(R.id.lv_content)
    ListView lvContent;
    public static final String Level1ToLevel2Key = "Level1ToLevel2Key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_department_level1);
        ButterKnife.bind(this);
        init();
    }

    @Override
    public void init() {
        super.init();
        setBack(R.id.iv_back);
        IGetter iGetter = Getter.getInstance(getApplicationContext());
        final DepartmentDisplayWrapBean[] departmentDisplayWrapBean = iGetter.getDepartmentDisplayWrapBean();
        DepartmentLevel1Adapter adapter = new DepartmentLevel1Adapter(getApplicationContext(), Arrays.asList(departmentDisplayWrapBean));
        lvContent.setAdapter(adapter);
        lvContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DepartmentDisplayWrapBean bean = departmentDisplayWrapBean[position];
                if (bean.level2.size() == 0) {
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(DepartmentLevel2Activity.DepartmentLevel2ActivityKey, bean.level1);
                    intent.putExtras(bundle);
                    setResult(RESULT_OK, intent);
                    finishCur();
                } else {

                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Level1ToLevel2Key, bean);
                    skipToForResult(DepartmentLevel2Activity.class, bundle, ToLevel2);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case ToLevel2:
                if (resultCode == RESULT_OK) {
                    setResult(RESULT_OK, data);
                    finishCur();
                }
                break;
        }
    }
}
