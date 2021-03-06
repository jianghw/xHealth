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
import com.kaurihealth.kaurihealth.adapter.DepartmentLevel2Adapter;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.kaurihealth.tinker.SampleApplicationLike;
import com.kaurihealth.mvplib.department_p.ISelectDepartmentLevel2View;
import com.kaurihealth.mvplib.department_p.SelectDepartmentLevel2Presenter;
import com.kaurihealth.utilslib.constant.Global;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;


/**
 * 我的-->个人信息-->科室   created by Nick
 */
public class SelectDepartmentLevel2Activity extends BaseActivity implements ISelectDepartmentLevel2View {
    @Inject
    SelectDepartmentLevel2Presenter<ISelectDepartmentLevel2View> mPresenter;

    @Bind(R.id.lv_content)
    ListView lvContent;

    public static String DepartmentLevel2ActivityKey = "DepartmentLevel2ActivityKey";

    private List<DepartmentDisplayBean> level2DepartmentList = new ArrayList<>();
    private DepartmentLevel2Adapter level2Adapter;

    private DepartmentDisplayBean selectedDepartment;
    private int departmentId;
    private String bundleString;

    @Override
    protected int getActivityLayoutID() {
        return R.layout.activity_select_department_level1;
    }

    @Override
    protected void initPresenterAndView(Bundle savedInstanceState) {
        SampleApplicationLike.getApp().getComponent().inject(this);
        mPresenter.setPresenter(this);

        Bundle bundle = getBundle();
        DepartmentDisplayBean displayBean = (DepartmentDisplayBean) bundle.getSerializable(Global.Jump.SelectDepartmentLevel2Activity);
        if (displayBean != null) departmentId = displayBean.getDepartmentId();
        bundleString = getBundle().getString(Global.Environment.BUNDLE);
    }

    @Override
    protected void initDelayedData() {
        initNewBackBtn(getString(R.string.department_two));

        //第二次网络请求,刷新出二级科室
        mPresenter.onSubscribe();

        level2Adapter = new DepartmentLevel2Adapter(getApplicationContext(), level2DepartmentList);
        lvContent.setAdapter(level2Adapter);

        lvContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedDepartment = level2DepartmentList.get(position);
                //更新科室信息 -->mobileUpdateDoctorBean 初始化
                if (bundleString.equals(Global.Environment.CHOICE)) {
                    returnBundle();
                } else {
                   mPresenter.upDateDoctor();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.unSubscribe();
    }

    @Override
    public void switchPageUI(String className) {
        finishCur();
    }

    //刷新二级科室的ListView数据
    @Override
    public void refreshListView(List<DepartmentDisplayBean> list) {
        level2DepartmentList.clear();
        level2DepartmentList.addAll(list);
        level2Adapter.notifyDataSetChanged();
    }

    //错误提示信息
    @Override
    public void displayError(Throwable e) {
        displayErrorDialog(e.getMessage());
    }

    @Override
    public int getParentDepartmentId() {
        return departmentId;
    }

    @Override
    public void returnBundle() {
        Intent intent = getIntent();
        Bundle bundle = new Bundle();
        bundle.putSerializable(DepartmentLevel2ActivityKey, selectedDepartment);
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finishCur();
    }

    /**
     * 更新doctorDisplayBean的科室
     */
    @Override
    public DoctorDisplayBean getDoctorDisplayBean() {
        DoctorDisplayBean bean = (DoctorDisplayBean) LocalData.getLocalData().getMyself().clone();
        if (bean.getDoctorInformations() != null) {
            bean.getDoctorInformations().setDepartmentId(selectedDepartment.getDepartmentId());
            if (bean.getDoctorInformations().getDepartment() != null) {
                bean.getDoctorInformations().getDepartment().setDepartmentId(selectedDepartment.getDepartmentId());
            } else {
                bean.getDoctorInformations().setDepartment(selectedDepartment);
            }
        }
        return bean;
    }


}
