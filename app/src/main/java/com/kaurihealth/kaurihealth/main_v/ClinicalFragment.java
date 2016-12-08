package com.kaurihealth.kaurihealth.main_v;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioButton;

import com.kaurihealth.datalib.local.LocalData;
import com.kaurihealth.datalib.response_bean.UserBean;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.adapter.MainFragmentAdapter;
import com.kaurihealth.kaurihealth.base_v.BaseFragment;
import com.kaurihealth.kaurihealth.clinical_v.Fragment.DynamicFragment;
import com.kaurihealth.kaurihealth.clinical_v.Fragment.StudentFragment;
import com.kaurihealth.kaurihealth.clinical_v.Fragment.StudyFragment;
import com.kaurihealth.kaurihealth.clinical_v.Fragment.TreatmentFragment;
import com.kaurihealth.kaurihealth.clinical_v.activity.ClinicalSearchActivity;
import com.kaurihealth.kaurihealth.clinical_v.activity.MyClinicalListActivity;
import com.kaurihealth.kaurihealth.tinker.SampleApplicationLike;
import com.kaurihealth.mvplib.main_p.ClinicalPresenter;
import com.kaurihealth.mvplib.main_p.IClinicalView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 临床支持 母页
 * 描述: 临床支持Fragment
 */
public class ClinicalFragment extends BaseFragment implements IClinicalView {
    @Inject
    ClinicalPresenter<IClinicalView> mPresenter;

    //医疗动态
    @Bind(R.id.rbtn_clinical_treatment_medicalhistory)
    RadioButton rbtnClinicalTreatmentMedicalhistory;
    //医学教育
    @Bind(R.id.rbt_assist_check_medicalhistory)
    RadioButton rbtAssistCheckMedicalhistory;
    //疾病与治疗
    @Bind(R.id.rbtn_labcheck_medicalhistory)
    RadioButton rbtnLabcheckMedicalhistory;
    //会议及学习
    @Bind(R.id.rbtn_pathologycheck_medicalhistory)
    RadioButton rbtnPathologycheckMedicalhistory;


    @Bind(R.id.vp_content_main)
    ViewPager vpagerContent;

    private List<Fragment> mFragment = new ArrayList<>();

    public static ClinicalFragment newInstance() {
        return new ClinicalFragment();
    }

    @Override
    protected int getFragmentLayoutID() {
        return R.layout.fragment_clinical;
    }

    @Override
    protected void initPresenterAndView(Bundle savedInstanceState) {
        SampleApplicationLike.getApp().getComponent().inject(this);
        mPresenter.setPresenter(this);

        createCurrentFragment();//创建当前的fragment
    }

    /**
     * 得到用户id
     */
    public int getUserId() {
        UserBean user = LocalData.getLocalData().getTokenBean().getUser();
        if (user == null) throw new IllegalStateException("userBean is null ,it must be not null");
        return user.getUserId();
    }

    /**
     * 创建当前的fragment
     */
    public void createCurrentFragment() {
        if (mFragment.size() > 0) mFragment.clear();
        //医疗动态
        DynamicFragment dynamicFragment = DynamicFragment.newInstance();
        //医学教育
        StudentFragment studentFragment = StudentFragment.newInstance();
        //疾病与治疗
        TreatmentFragment treatmentFragment = TreatmentFragment.newInstance();
        //会议及学习
        StudyFragment studyFragment = StudyFragment.newInstance();

        mFragment.add(dynamicFragment);
        mFragment.add(studentFragment);
        mFragment.add(treatmentFragment);
        mFragment.add(studyFragment);
    }

    @Override
    protected void initDelayedData() {

        initPagerView();
    }

    @Override
    protected void lazyLoadingData() {
        vpagerContent.setCurrentItem(1);
        vpagerContent.setCurrentItem(0);
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.unSubscribe();
    }

    @Override
    public void switchPageUI(String className) {

    }

    /**
     * 导航Button点击事件
     *
     * @param view
     */
    @OnClick({R.id.rbtn_clinical_treatment_medicalhistory, R.id.rbt_assist_check_medicalhistory, R.id.rbtn_labcheck_medicalhistory, R.id.rbtn_pathologycheck_medicalhistory})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rbtn_clinical_treatment_medicalhistory:
                vpagerContent.setCurrentItem(0);
                break;
            case R.id.rbt_assist_check_medicalhistory:
                vpagerContent.setCurrentItem(1);
                break;
            case R.id.rbtn_labcheck_medicalhistory:
                vpagerContent.setCurrentItem(2);
                break;
            case R.id.rbtn_pathologycheck_medicalhistory:
                vpagerContent.setCurrentItem(3);
                break;
        }
    }

    /**
     * 初始化pagerView
     */
    private void initPagerView() {
        MainFragmentAdapter mainFragmentAdapter = new MainFragmentAdapter(getChildFragmentManager(), mFragment);
        vpagerContent.setAdapter(mainFragmentAdapter);
        vpagerContent.setOffscreenPageLimit(mFragment.size() - 1);
        vpagerContent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setOneChecked(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setOneChecked(int index) {
        switch (index) {
            case 0:
                rbtnClinicalTreatmentMedicalhistory.setChecked(true);
                break;
            case 1:
                rbtAssistCheckMedicalhistory.setChecked(true);
                break;
            case 2:
                rbtnLabcheckMedicalhistory.setChecked(true);
                break;
            case 3:
                rbtnPathologycheckMedicalhistory.setChecked(true);
                break;
        }
    }

    /**
     * 我发表的文章
     */
    @OnClick(R.id.ImageView_clinical_publish)
    public void onClick_ImageView_clinical_publish() {
        int UserId = getUserId();
        Bundle bundle = new Bundle();
        bundle.putInt("UserId", UserId);
        skipToForResult(MyClinicalListActivity.class, bundle, 0);
    }

    /**
     * 搜索文章
     */
    @OnClick(R.id.imageview_clinical_seek)
    public void onClick_imageview_clinical_seek() {
        skipTo(ClinicalSearchActivity.class);
    }
}
