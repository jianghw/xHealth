package com.kaurihealth.kaurihealth.home.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.kaurihealth.datalib.request_bean.bean.SearchDoctorDisplayBean;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.common.adapter.DoctorAdapter;
import com.kaurihealth.kaurihealth.common.util.HandleData;
import com.youyou.zllibrary.util.CommonFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class BlankMedicineFragment extends CommonFragment implements HandleData<List<SearchDoctorDisplayBean>> {
    @Bind(R.id.history_recordes_fragment_blank_doctor_all)
    RelativeLayout history_recordes_fragment_blank_doctor_all;
    @Bind(R.id.lv_content)
    ListView lvContent;
    private List<SearchDoctorDisplayBean> sblist = new ArrayList<>();
    private DoctorAdapter blankoctoradapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blank_doctor, null);
        //初始化下拉刷新框架
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    @Override
    public void setData(List<SearchDoctorDisplayBean> searchBeanslist) {
        //去除以前的数据
        sblist.clear();
        //添加新的数据
        sblist.addAll(searchBeanslist);
        //动态跟新listview
        blankoctoradapter.notifyDataSetChanged();

        history_recordes_fragment_blank_doctor_all.setVisibility(View.GONE);
    }

    @Override
    public void clear() {
        sblist.clear();
        if (blankoctoradapter != null) {
            blankoctoradapter.notifyDataSetChanged();
        }

    }


    @Override
    public void init() {
        super.init();
        //初始化list
        //将list放入listview  并显示
        blankoctoradapter = new DoctorAdapter(this.getContext(), sblist);
        lvContent.setAdapter(blankoctoradapter);
    }

    //清除历史搜索按钮的点击事件
    @OnClick(R.id.fragment_blank_doctor_clear_record)
    public void clearRecord() {
        clear();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);

    }
}
