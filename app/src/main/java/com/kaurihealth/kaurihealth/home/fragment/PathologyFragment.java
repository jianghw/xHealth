package com.kaurihealth.kaurihealth.home.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.kaurihealth.datalib.request_bean.bean.PatientRecordDisplayBean;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.common.Interface.IGetMedicaHistoryRecord;
import com.kaurihealth.kaurihealth.common.activity.EditCommonMedicalRecordActivityNew;
import com.kaurihealth.kaurihealth.common.util.FragmentControl;
import com.kaurihealth.kaurihealth.home.adapter.PathologyAdapter;
import com.kaurihealth.kaurihealth.home.util.BundleFactory;
import com.kaurihealth.kaurihealth.home.util.CompatorUtil;
import com.kaurihealth.kaurihealth.home.util.Interface.IBundleFactory;
import com.kaurihealth.kaurihealth.home.util.Interface.ISetBundleHealthyRecord;
import com.kaurihealth.kaurihealth.util.Interface.IStrartActivity;
import com.youyou.zllibrary.util.CommonFragment;
import com.youyou.zllibrary.util.FragmentData;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 版权:    张磊
 * 作者:    张磊
 * 描述：病理检查
 * 创建日期:2016/1/22
 * 修订日期:
 */
public class PathologyFragment extends CommonFragment implements FragmentData<PatientRecordDisplayBean>, FragmentControl {
    @Bind(R.id.lv_content)
    ListView lvComtent;
    @Bind(R.id.pathologychech_SR)
    SwipeRefreshLayout pathologychech_SR;
    private View view;
    private List<PatientRecordDisplayBean> list = new ArrayList<>();
    private PathologyAdapter adapter;
    private IStrartActivity iStrartActivity;
    private CompatorUtil compatorUtil;
    private IGetMedicaHistoryRecord medicaHistoryRecord;

    public void setiStrartActivity(IStrartActivity iStrartActivity) {
        this.iStrartActivity = iStrartActivity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.pathologycheckfragment, null);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void init() {
        adapter = new PathologyAdapter(getContext(), list);
        final IBundleFactory bundleFactory = new BundleFactory();

        lvComtent.setAdapter(adapter);
        lvComtent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                bundle.putSerializable("data", list.get(position));
                ISetBundleHealthyRecord iSetBundleHealthyRecord = bundleFactory.getISetBundleHealthyRecord(bundle);
                iSetBundleHealthyRecord.setAble(isEditable);
                iStrartActivity.startActivityForResult(EditCommonMedicalRecordActivityNew.class, bundle, 11);
            }
        });
        pathologychech_SR.setSize(SwipeRefreshLayout.DEFAULT);
        pathologychech_SR.setColorSchemeResources(R.color.holo_blue_light_new
                , R.color.holo_blue_light_new,
                R.color.holo_blue_light_new, R.color.holo_blue_light_new);
        pathologychech_SR.setProgressBackgroundColor(R.color.linelogin);
        pathologychech_SR.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                medicaHistoryRecord.getDate();
                pathologychech_SR.setRefreshing(false);

            }
        });
    }

    @Override
    public void setData(List<PatientRecordDisplayBean> list) {
        this.list.clear();
        compatorUtil = new CompatorUtil();
        PatientRecordDisplayBean[] jsonNodes = compatorUtil.handleData(list);
        for (int i = 0; i < jsonNodes.length; i++) {
            this.list.add(jsonNodes[i]);
        }
        if (isResumed()) {
            adapter.notifyDataSetChanged();
        }
    }

    private Bundle bundle;

    @Override
    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }


    @Override
    public void setEditable(boolean isEditable) {
        this.isEditable = isEditable;
    }

    public void setGetMedicaHistoryRecordListener(IGetMedicaHistoryRecord medicaHistoryRecord){
        this.medicaHistoryRecord = medicaHistoryRecord;
    }

    boolean isEditable;

}
