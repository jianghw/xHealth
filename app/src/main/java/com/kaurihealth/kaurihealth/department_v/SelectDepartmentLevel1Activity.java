package com.kaurihealth.kaurihealth.department_v;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.kaurihealth.datalib.local.LocalData;
import com.kaurihealth.datalib.response_bean.DepartmentDisplayBean;
import com.kaurihealth.datalib.response_bean.DoctorDisplayBean;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.adapter.DepartmentLevel1Adapter;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.kaurihealth.tinker.SampleApplicationLike;
import com.kaurihealth.mvplib.department_p.ISelectDepartmentLevel1View;
import com.kaurihealth.mvplib.department_p.SelectDepartmentLevel1Presenter;
import com.kaurihealth.utilslib.constant.Global;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;


/**
 * 我的-->个人信息-->科室            created by Nick
 */
public class SelectDepartmentLevel1Activity extends BaseActivity implements ISelectDepartmentLevel1View {
    @Inject
    SelectDepartmentLevel1Presenter<ISelectDepartmentLevel1View> mPresenter;

    //科室列表
    @Bind(R.id.lv_content)
    ListView lvContent;

    public static final int ToDepartmentLevel = 2;
    public final int ToLevel2 = 1;

    private List<DepartmentDisplayBean> departmentList = new ArrayList<>();

    private DepartmentLevel1Adapter level1Adapter;
    private String bundleString;
    private DepartmentDisplayBean departmentDisplayBean;

    @Override
    protected int getActivityLayoutID() {
        return R.layout.activity_select_department_level1;
    }

    @Override
    protected void initPresenterAndView(Bundle savedInstanceState) {
        SampleApplicationLike.getApp().getComponent().inject(this);
        mPresenter.setPresenter(this);
    }


    @Override
    protected void initDelayedData() {
        initNewBackBtn(getString(R.string.department));
        initLevel1DepartmentList();

        bundleString = getBundle().getString(Global.Environment.BUNDLE);


        level1Adapter = new DepartmentLevel1Adapter(getApplicationContext(), departmentList);
        lvContent.setAdapter(level1Adapter);

        lvContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                 departmentDisplayBean = departmentList.get(position);
                if (departmentDisplayBean.getDepartmentName().equals("耳鼻喉科")) {
                    if (bundleString.equals(Global.Environment.CHOICE)) {
                        returnBundle();
                    } else {
                        mPresenter.upDateDoctor();
                    }
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString(Global.Environment.BUNDLE, bundleString);
                    bundle.putSerializable(Global.Jump.SelectDepartmentLevel2Activity, departmentDisplayBean);
                    skipToForResult(SelectDepartmentLevel2Activity.class, bundle, ToLevel2);
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
            default:
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.unSubscribe();
    }

    //跳转界面
    @Override
    public void switchPageUI(String className) {
        finishCur();
    }

    //第一次网络请求:加载一级科室
    public void initLevel1DepartmentList() {
        mPresenter.onSubscribe();
    }

    @Override
    public void refreshListView(List<DepartmentDisplayBean> list) {

        if (!departmentList.isEmpty()) departmentList.clear();
        departmentList.addAll(list);
        level1Adapter.notifyDataSetChanged();
    }

    @Override
    public DoctorDisplayBean getDoctorDisplayBean() {
        DoctorDisplayBean bean = (DoctorDisplayBean) LocalData.getLocalData().getMyself().clone();
        if (bean.getDoctorInformations() != null) {
            bean.getDoctorInformations().setDepartmentId(departmentDisplayBean.getDepartmentId());
            if (bean.getDoctorInformations().getDepartment() != null) {
                bean.getDoctorInformations().getDepartment().setDepartmentId(departmentDisplayBean.getDepartmentId());
            } else {
                bean.getDoctorInformations().setDepartment(departmentDisplayBean);
            }
        }
        return bean;
    }

    @Override
    public void returnBundle() {
        Intent intent = getIntent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("DepartmentLevel2ActivityKey", departmentDisplayBean);
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finishCur();
    }
}
