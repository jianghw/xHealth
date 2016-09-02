package com.kaurihealth.kaurihealth.mine_v;

import android.os.Bundle;
import android.widget.EditText;

import com.kaurihealth.datalib.local.LocalData;
import com.kaurihealth.datalib.response_bean.DoctorDisplayBean;
import com.kaurihealth.kaurihealth.MyApplication;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.mvplib.mine_p.EnterPracticeFieldPresenter;
import com.kaurihealth.mvplib.mine_p.IEnterPracticeFieldView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;


/**
 * 我的Fragment-->个人信息-->专业特长    created by Nick
 */
public class EnterPracticeFieldActivity extends BaseActivity implements IEnterPracticeFieldView {
    //"专业特长"编辑框
    @Bind(R.id.edt_name)
    EditText edtName;

    @Inject
    EnterPracticeFieldPresenter<IEnterPracticeFieldView> mPresenter;

    private DoctorDisplayBean myself;


    @Override
    protected int getActivityLayoutID() {
        return R.layout.activity_enter_practice_field;
    }

    @Override
    protected void initPresenterAndData(Bundle savedInstanceState) {
        MyApplication.getApp().getComponent().inject(this);
        mPresenter.setPresenter(this);
        myself = LocalData.getLocalData().getMyself();
    }

    @Override
    protected void initDelayedView() {
        initBackBtn(R.id.iv_back);
        //获取专业特长
        String practiceField = myself.practiceField;
        edtName.setText(practiceField);
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

    /**
     * ----------------------------继承基础mvpView方法-----------------------------------
     *
     * @param className
     */

    @Override
    public void switchPageUI(String className) {
        finishCur();
    }

    /**
     * 保存
     */
    @OnClick(R.id.tv_operate)
    public void save() {
        mPresenter.onSubscribe();
    }

    @Override
    public void displayError(Throwable e) {
        displayErrorDialog(e.getMessage());
    }

    @Override
    public DoctorDisplayBean getDoctorDisplayBean() {
        myself.practiceField = edtName.getText().toString();
        return myself;
    }
}
