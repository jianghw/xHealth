package com.kaurihealth.kaurihealth.home.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kaurihealth.kaurihealth.R;
import com.youyou.zllibrary.util.CommonFragment;

public class HealthRecordEditFragment extends CommonFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.health_record_edit, null);
        return view;
    }

}
