package com.kaurihealth.kaurihealth.department_v;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.commonlibrary.widget.util.LoadingUtil;
import com.kaurihealth.datalib.request_bean.bean.DepartmentDisplayWrapBean;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.kaurihealth.patientwithdoctor.activity.DepartmentLevel1Activity;
import com.kaurihealth.kaurihealth.patientwithdoctor.adapter.DepartmentLevel2Adapter;

import java.io.Serializable;

import butterknife.Bind;


/**
 * 我的Button 里面的设置   created by Nick
 */
public class SelectDepartmentLevel2Activity extends BaseActivity {
    public static String DepartmentLevel2ActivityKey = "DepartmentLevel2ActivityKey";
    @Bind(R.id.lv_content)
    ListView lvContent;
    private Bundle bundle;
    private DepartmentDisplayWrapBean bean;
    private LoadingUtil loadingUtil;


    @Override
    protected int getActivityLayoutID() {
        return R.layout.activity_select_department_level1;
    }

    @Override
    protected void initPresenterAndData(Bundle savedInstanceState) {

    }

    @Override
    protected void initDelayedView() {
        initBackBtn(R.id.iv_back);
        loadingUtil = LoadingUtil.getInstance(SelectDepartmentLevel2Activity.this);
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

}
