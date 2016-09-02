package com.kaurihealth.kaurihealth.patientwithdoctor.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.commonlibrary.widget.util.LoadingUtil;
import com.kaurihealth.datalib.request_bean.bean.DepartmentDisplayWrapBean;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.patientwithdoctor.adapter.DepartmentLevel2Adapter;
import com.youyou.zllibrary.util.CommonActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DepartmentLevel2Activity extends CommonActivity {
    public static String DepartmentLevel2ActivityKey = "DepartmentLevel2ActivityKey";
    @Bind(R.id.lv_content)
    ListView lvContent;
    private Bundle bundle;
    private DepartmentDisplayWrapBean bean;
    private LoadingUtil loadingUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_department_level2);
        ButterKnife.bind(this);
        init();
    }

    @Override
    public void init() {
        super.init();
        loadingUtil = LoadingUtil.getInstance(DepartmentLevel2Activity.this);
        setBack(R.id.iv_back);
        bundle = getBundle();
        bean = (DepartmentDisplayWrapBean) bundle.getSerializable(DepartmentLevel1Activity.Level1ToLevel2Key);
        DepartmentLevel2Adapter adapter = new DepartmentLevel2Adapter(getApplicationContext(), bean.level2);
        lvContent.setAdapter(adapter);
        lvContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                bundle.putSerializable(DepartmentLevel2ActivityKey, bean.level2.get(position));
                Intent intent = getIntent();
                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);
                loadingUtil.show();
                loadingUtil.dismiss("修改成功", new LoadingUtil.Success() {
                    @Override
                    public void dismiss() {
                        finishCur();
                    }
                });
            }
        });
    }
}
