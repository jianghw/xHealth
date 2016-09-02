package com.kaurihealth.kaurihealth.home.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.kaurihealth.datalib.response_bean.PatientDisplayBean;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.common.adapter.PatientAdapter;
import com.kaurihealth.kaurihealth.common.util.HandleData;
import com.kaurihealth.kaurihealth.util.Interface.ISkip;
import com.youyou.zllibrary.util.CommonFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 患者
 */
public class BlankPatientFragment extends CommonFragment implements HandleData<List<PatientDisplayBean>> {
    @Bind(R.id.history_recordes_fragment_blank_patient)
    RelativeLayout history_recordes_fragment_blank_patient;
    @Bind(R.id.lv_content)
    ListView lvContent;
    private List<PatientDisplayBean> sblist = new ArrayList<>();
    private PatientAdapter blankPatientAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blank_patient, null);
        //初始化下拉刷新框架
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    @Override
    public void setData(List<PatientDisplayBean> patientDisplayBeanList) {
        //去除以前的数据
        sblist.clear();
        //添加新的数据
        sblist.addAll(patientDisplayBeanList);
        //动态跟新listview
        blankPatientAdapter.notifyDataSetChanged();

        //如果从网上拿到数据就将历史纪录布局设为不可见
        history_recordes_fragment_blank_patient.setVisibility(View.GONE);
    }

    @Override
    public void clear() {
        sblist.clear();
        if (blankPatientAdapter != null) {
            blankPatientAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public void init() {
        super.init();

        //将list放入listview并显示
        blankPatientAdapter = new PatientAdapter(this.getContext(), sblist);

        blankPatientAdapter.setSkip(skip);

        lvContent.setAdapter(blankPatientAdapter);
    }

    ISkip skip;

    public void setSkip(ISkip skip) {
        this.skip = skip;
    }


    //清除历史搜索按钮的点击事件
    @OnClick(R.id.fragment_blank_patient_clear_record)
    public void clearRecord() {
        sblist.clear();
        blankPatientAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);

    }
}
