package com.kaurihealth.kaurihealth.patient_v;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.kaurihealth.kaurihealth.MyApplication;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.mvplib.patient_p.FamilyMembersPresenter;
import com.kaurihealth.mvplib.patient_p.IFamilyMembersView;

import javax.inject.Inject;

import butterknife.Bind;


/**
 * 医疗团队
 */
public class FamilyMembersActivity extends BaseActivity implements IFamilyMembersView {
    @Bind(R.id.tv_more)
    TextView tv_more;

    @Inject
    FamilyMembersPresenter<IFamilyMembersView> mPresenter;


    @Override
    protected int getActivityLayoutID() {
        return R.layout.family_member;
    }

    @Override
    protected void initPresenterAndView(Bundle savedInstanceState) {
        MyApplication.getApp().getComponent().inject(this);
        mPresenter.setPresenter(this);
    }

    @Override
    protected void initDelayedData() {
        initNewBackBtn("家庭成员");
        tv_more.setVisibility(View.GONE);
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
     * @param className*/

    @Override
    public void switchPageUI(String className) {

    }
}
