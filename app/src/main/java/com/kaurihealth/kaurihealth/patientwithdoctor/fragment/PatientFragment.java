package com.kaurihealth.kaurihealth.patientwithdoctor.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.commonlibrary.widget.util.GetDataInterface;
import com.example.commonlibrary.widget.util.LogUtilInterface;
import com.kaurihealth.datalib.response_bean.DoctorPatientRelationshipBean;
import com.kaurihealth.datalib.service.IDoctorPatientRelationshipService;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.adapter.PatientDocRelAdapter;
import com.kaurihealth.kaurihealth.patientwithdoctor.util.Interface.IIsActive;
import com.kaurihealth.kaurihealth.patientwithdoctor.util.Interface.IsActive;
import com.youyou.zllibrary.util.CommonFragment;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 版权:    张磊
 * 作者:    张磊
 * 描述：
 * 修订日期:
 */
public class PatientFragment extends CommonFragment {
    @Bind(R.id.gv_content)
    GridView gvContent;

    @Bind(R.id.swipe_refresh)
    SwipeRefreshLayout rotateHeaderGridViewFrame;
    private PatientDocRelAdapter adapter;
    private List<DoctorPatientRelationshipBean> list = new ArrayList<>();
    private IDoctorPatientRelationshipService doctorPatientRelationshipService;
    private LogUtilInterface logUtil;
    private IIsActive iIsActive = new IsActive();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_patient, null);
        ButterKnife.bind(this, view);
        init();
        return view;
    }


    @Override
    public void init() {
        super.init();

        rotateHeaderGridViewFrame.setSize(SwipeRefreshLayout.DEFAULT);
        rotateHeaderGridViewFrame.setColorSchemeResources(R.color.holo_blue_light_new
                ,R.color.holo_blue_light_new,
                R.color.holo_blue_light_new,R.color.holo_blue_light_new);
        rotateHeaderGridViewFrame.setProgressBackgroundColor(R.color.linelogin);
        rotateHeaderGridViewFrame.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                getData();
            }
        });
        adapter = new PatientDocRelAdapter(getContext(), list);
        gvContent.setAdapter(adapter);
    }



    /**
     * @param data
     * @param ifRefresh 是否刷新界面
     */
    private void setData(List<DoctorPatientRelationshipBean> data, boolean ifRefresh) {
        list.clear();
        if (data != null && data.size() != 0) {
            list.addAll(data);
        }
        if (ifRefresh) {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void getData() {
//        doctorPatientRelationshipService = new ServiceFactory(Url.prefix,getContext()).getDoctorPatientRelationshipService();
//        Call<List<DoctorPatientRelationshipBean>> call = doctorPatientRelationshipService.LoadDoctorPatientRelationshipForDoctor();
//        call.enqueue(new Callback<List<DoctorPatientRelationshipBean>>() {
//            @Override
//            public void onResponse(Call<List<DoctorPatientRelationshipBean>> call, Response<List<DoctorPatientRelationshipBean>> response) {
//                if (response.isSuccessful()) {
//                    transform(response.body(), true);
//                }
//                if (rotateHeaderGridViewFrame != null) {
//                    rotateHeaderGridViewFrame.setRefreshing(false);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<DoctorPatientRelationshipBean>> call, Throwable t) {
//                t.printStackTrace();
//                if (rotateHeaderGridViewFrame != null) {
//                    rotateHeaderGridViewFrame.setRefreshing(false);
//                }
//            }
//        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public GetDataInterface getDataInterface(final Context context) {
        return new GetDataInterface() {
            @Override
            public void getData() {
                getDataIniList(context);
            }
        };
    }

    /**
     * 存粹获取数据
     */
    private void getDataIniList(Context context) {
//        doctorPatientRelationshipService = new ServiceFactory(Url.prefix,getContext()).getDoctorPatientRelationshipService();
//        logUtil = LogFactory.getSimpleLog(this.getClass().getName());
//        Call<List<DoctorPatientRelationshipBean>> call = doctorPatientRelationshipService.LoadDoctorPatientRelationshipForDoctor();
//        call.enqueue(new Callback<List<DoctorPatientRelationshipBean>>() {
//            @Override
//            public void onResponse(Call<List<DoctorPatientRelationshipBean>> call, Response<List<DoctorPatientRelationshipBean>> response) {
//                if (response.isSuccessful()) {
//                    transform(response.body(), false);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<DoctorPatientRelationshipBean>> call, Throwable t) {
//                t.printStackTrace();
//            }
//        });
    }

    /**
     * 转数据
     */
    private void transform(List<DoctorPatientRelationshipBean> data, boolean ifRefresh) {
        HashSet<DoctorPatientRelationshipBean> setNotActive = new HashSet<>();
        List<DoctorPatientRelationshipBean> listIsActive = new LinkedList<>();
        //分类，并删除 NotActive中重复的
        for (DoctorPatientRelationshipBean bean : data) {
//            if (iIsActive.isActive(bean.isActive, bean.endDate)) {
//                listIsActive.add(bean);
//            } else {
//                setNotActive.add(bean);
//            }
        }
        //清理NotActive中跟listIsActive  有重复的数据
        for (int i = 0; i < listIsActive.size(); i++) {
            if (setNotActive.contains(listIsActive.get(i))) {
                setNotActive.remove(listIsActive.get(i));
            }
        }
        //将处理好的数据放到list中
        List<DoctorPatientRelationshipBean> result = new LinkedList<>();
        result.addAll(listIsActive);
        Iterator<DoctorPatientRelationshipBean> iterator = setNotActive.iterator();
        while (iterator.hasNext()) {
            result.add(iterator.next());
        }
        setData(result, ifRefresh);
    }

    @Override
    public void onStart() {
        super.onStart();
        getData();
    }
}
