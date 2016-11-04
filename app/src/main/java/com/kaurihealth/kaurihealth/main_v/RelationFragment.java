package com.kaurihealth.kaurihealth.main_v;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.adapter.MainFragmentAdapter;
import com.kaurihealth.kaurihealth.base_v.ChildBaseFragment;
import com.kaurihealth.kaurihealth.doctor_v.DoctorFragment;
import com.kaurihealth.kaurihealth.eventbus.DoctorFragmentEvent;
import com.kaurihealth.kaurihealth.eventbus.DoctorFragmentRefreshEvent;
import com.kaurihealth.kaurihealth.patient_v.PatientFragment;
import com.kaurihealth.kaurihealth.search_v.SearchActivity;
import com.kaurihealth.utilslib.TranslationAnim;
import com.kaurihealth.utilslib.constant.Global;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 医患关系 母页
 * 描述:医患Fragment
 */
public class RelationFragment extends Fragment {

    @Bind(R.id.rbtn_patient)
    RadioButton mRbtnPatient;
    @Bind(R.id.rbtn_doctor)
    RadioButton mRbtnDoctor;
    @Bind(R.id.vp_relation)
    ViewPager mVpRelation;
    @Bind(R.id.tv_add)
    ImageView addText;

    List<Fragment> mFragmentList = new ArrayList<>();
    private int mCurItem = 0;

    public static RelationFragment newInstance() {
        return new RelationFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_relation, container, false);
        ButterKnife.bind(this, view);

        initChildFragment();
        initViewPager();
        //注册事件
        EventBus.getDefault().register(this);
        return view;
    }

    private void initChildFragment() {
        if (mFragmentList.size() > 0) mFragmentList.clear();
        PatientFragment patientFragment = PatientFragment.newInstance();
        DoctorFragment doctorFragment = DoctorFragment.newInstance();
        mFragmentList.add(patientFragment);
        mFragmentList.add(doctorFragment);
    }

    private void initViewPager() {
        MainFragmentAdapter mainFragmentAdapter = new MainFragmentAdapter(getChildFragmentManager(), mFragmentList);
        mVpRelation.setAdapter(mainFragmentAdapter);
        mVpRelation.setOffscreenPageLimit(mFragmentList.size() - 1);
        mVpRelation.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                initCurPager(position);
                mCurItem = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        initCurPager(mCurItem);
    }

    private void initCurPager(int mCurItem) {
        switch (mCurItem) {
            case 0:
                mVpRelation.setCurrentItem(mCurItem);
                mRbtnPatient.setChecked(true);
                break;
            case 1:
                mVpRelation.setCurrentItem(mCurItem);
                mRbtnDoctor.setChecked(true);
                break;
            default:
                break;
        }
    }

    /**
     * 点击患者
     */
    @OnClick(R.id.rbtn_patient)
    public void patient() {
        mVpRelation.setCurrentItem(0);
    }

    /**
     * 点击医生
     */
    @OnClick(R.id.rbtn_doctor)
    public void doctor() {
        mVpRelation.setCurrentItem(1);
    }

    /**
     * 添加医生
     */
    @OnClick(R.id.tv_add)
    public void addDoctor() {
        Bundle bundle = new Bundle();
        if(mCurItem==0){
            bundle.putString(Global.Bundle.SEARCH_BUNDLE, Global.Bundle.SEARCH_PATIENT);
        }else{
            bundle.putString(Global.Bundle.SEARCH_BUNDLE, Global.Bundle.SEARCH_DOCTOR);
        }
        skipToBundle(SearchActivity.class, bundle);
    }

    protected void skipToBundle(Class<? extends Activity> className, Bundle bundle) {
        TranslationAnim.zlStartActivity(getActivity(), className, bundle);
    }

    /**
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (getActivity() != null) {
            List<Fragment> childFragmentList = getChildFragmentManager().getFragments();
            if (isVisibleToUser) {
                // 将所有正等待显示的子Fragment设置为显示状态，并取消等待显示标记
                if (childFragmentList != null && childFragmentList.size() > 0) {
                    for (Fragment childFragment : childFragmentList) {
                        if (childFragment instanceof ChildBaseFragment) {
                            ChildBaseFragment childBaseFragment = (ChildBaseFragment) childFragment;
                            if (childBaseFragment.isWaitingShowToUser()) {
                                childBaseFragment.setWaitingShowToUser(false);
                                childFragment.setUserVisibleHint(true);
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void eventBusMain(DoctorFragmentEvent event) {
        initCurPager(1);
        mCurItem = 1;
        EventBus.getDefault().postSticky(new DoctorFragmentRefreshEvent());//让其刷新
    }

}
