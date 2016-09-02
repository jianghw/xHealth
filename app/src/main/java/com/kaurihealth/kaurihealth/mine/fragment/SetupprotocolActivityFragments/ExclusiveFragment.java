package com.kaurihealth.kaurihealth.mine.fragment.SetupprotocolActivityFragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.common.util.FragmentControl;
import com.youyou.zllibrary.util.CommonFragment;
import com.youyou.zllibrary.util.FragmentData;

import org.codehaus.jackson.JsonNode;

import java.util.List;

import butterknife.ButterKnife;

public class ExclusiveFragment extends CommonFragment implements FragmentData<JsonNode>, FragmentControl {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exclusive, null);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    @Override
    public void init() {
        super.init();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void setEditable(boolean isEditable) {

    }

    @Override
    public void setData(List<JsonNode> list) {

    }

    @Override
    public void setBundle(Bundle bundle) {

    }
}
