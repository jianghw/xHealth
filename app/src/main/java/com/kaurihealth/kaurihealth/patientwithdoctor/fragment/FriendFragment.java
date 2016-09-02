package com.kaurihealth.kaurihealth.patientwithdoctor.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.example.commonlibrary.widget.util.GetDataInterface;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.adapter.MainFragmentAdapter;
import com.youyou.zllibrary.util.CommonFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 版权:    张磊
 * 作者:    张磊
 * 描述：
 * 修订日期:
 */
public class FriendFragment extends CommonFragment {
    @Bind(R.id.vp_content_main)
    ViewPager vpagerContent;
    @Bind(R.id.rbtn_patient)
    RadioButton rbtnPatient;
    @Bind(R.id.rbtn_doctor)
    RadioButton rbtnDoctor;
    PatientFragment patinentFragment = new PatientFragment();
    DoctorFragment doctorFragment = new DoctorFragment();
    List<Fragment> fragmentList = new ArrayList<>();
    {
        fragmentList.add(patinentFragment);
        fragmentList.add(doctorFragment);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_relation, null);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    @Override
    public void init() {
        super.init();

        MainFragmentAdapter mainFragmentAdapter = new MainFragmentAdapter(getChildFragmentManager(), fragmentList);
        vpagerContent.setAdapter(mainFragmentAdapter);
        vpagerContent.setCurrentItem(0);
        rbtnPatient.setChecked(true);
        vpagerContent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        rbtnPatient.setChecked(true);
                        break;
                    case 1:
                        rbtnDoctor.setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 点击患者
     */
    @OnClick(R.id.rbtn_patient)
    public void patient() {
        vpagerContent.setCurrentItem(0);
    }

    /**
     * 点击医生
     */
    @OnClick(R.id.rbtn_doctor)
    public void doctor() {
        vpagerContent.setCurrentItem(1);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    public GetDataInterface getDataInterface(Context context) {
        final GetDataInterface dataInterfacePatient = patinentFragment.getDataInterface(context);
        return new GetDataInterface() {
            @Override
            public void getData() {
                dataInterfacePatient.getData();
            }
        };
    }
}
