package com.kaurihealth.kaurihealth.main_v;

import android.os.Bundle;

import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.base_v.BaseFragment;

public class EmptyFragment extends BaseFragment {

    public static EmptyFragment newInstance() {
        return new EmptyFragment();
    }


    @Override
    protected int getFragmentLayoutID() {
        return R.layout.fragment_empty;
    }

    @Override
    protected void initPresenterAndView(Bundle savedInstanceState) {

    }

    @Override
    protected void initDelayedData() {

    }

    @Override
    protected void lazyLoadingData() {

    }

}
