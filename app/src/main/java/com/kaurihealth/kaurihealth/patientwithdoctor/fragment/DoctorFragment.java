package com.kaurihealth.kaurihealth.patientwithdoctor.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kaurihealth.kaurihealth.R;
import com.youyou.zllibrary.util.CommonFragment;

/**
 * 版权:    张磊
 * 作者:    张磊
 * 描述：
 * 修订日期:
 */
public class DoctorFragment extends CommonFragment {



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.doctorfragment,null);
        return view;
    }

}
