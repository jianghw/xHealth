package com.kaurihealth.kaurihealth.department_v;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.kaurihealth.datalib.request_bean.bean.DepartmentDisplayWrapBean;
import com.kaurihealth.kaurihealth.MyApplication;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.kaurihealth.patientwithdoctor.adapter.DepartmentLevel1Adapter;
import com.kaurihealth.kaurihealth.util.Getter;
import com.kaurihealth.kaurihealth.util.Interface.IGetter;
import com.kaurihealth.mvplib.department_p.ISelectDepartmentLevel1View;
import com.kaurihealth.mvplib.department_p.SelectDepartmentLevel1Presenter;

import java.lang.reflect.Array;
import java.util.Arrays;

import javax.inject.Inject;

import butterknife.Bind;


/**
 * 我的-->个人信息-->科室            created by Nick
 */
public class SelectDepartmentLevel1Activity extends BaseActivity implements ISelectDepartmentLevel1View {
    @Bind(R.id.lv_content)
    ListView lvContent;
    public static final int ToDepartmentLevel = 2;
    public final int ToLevel2 = 1;
    public static final String Level1ToLevel2Key = "Level1ToLevel2Key";

    @Inject
    SelectDepartmentLevel1Presenter<ISelectDepartmentLevel1View> mPresenter;

    @Override
    protected int getActivityLayoutID() {
        return R.layout.activity_select_department_level1;
    }

    @Override
    protected void initPresenterAndData(Bundle savedInstanceState) {
        MyApplication.getApp().getComponent().inject(this);
        mPresenter.setPresenter(this);
    }

    @Override
    protected void initDelayedView() {
        initBackBtn(R.id.iv_back);
        IGetter iGetter = Getter.getInstance(getApplicationContext());
        final DepartmentDisplayWrapBean[] departmentDisplayWrapBean = iGetter.getDepartmentDisplayWrapBean();
        DepartmentLevel1Adapter adapter = new DepartmentLevel1Adapter(getApplicationContext(), Arrays.asList(departmentDisplayWrapBean));
        lvContent.setAdapter(adapter);
        lvContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                DepartmentDisplayWrapBean bean = departmentDisplayWrapBean[position];
                if (bean.level2.size()==0) {
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(SelectDepartmentLevel2Activity.DepartmentLevel2ActivityKey,bean.level1);
                    intent.putExtras(bundle);
                    setResult(RESULT_OK,intent);
                    finishCur();
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Level1ToLevel2Key,bean);
                    skipToForResult(SelectDepartmentLevel2Activity.class,bundle,ToLevel2);
                }
            }
        });
    }

    @Override
    protected void  onActivityResult(int requestCode , int resultCode ,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        switch (requestCode){
            case ToLevel2:
                if (resultCode == RESULT_OK) {
                     setResult(RESULT_OK,data);
                    finishCur();
                }
            break;
        }
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

    //跳转界面
    @Override
    public void switchPageUI(String className) {

    }
}
