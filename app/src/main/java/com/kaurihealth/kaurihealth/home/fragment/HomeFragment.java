package com.kaurihealth.kaurihealth.home.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.kaurihealth.datalib.request_bean.bean.PatientRequestDisplayBean;
import com.kaurihealth.datalib.service.IPatientRequestService;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.common.RequestCode;
import com.kaurihealth.kaurihealth.home.activity.PatientRequestInfoActivity;
import com.kaurihealth.kaurihealth.home.activity.SearchActivityNew;
import com.kaurihealth.kaurihealth.home.adapter.PatientAdapter;
import com.kaurihealth.kaurihealth.open_an_account_v.OpenAnAccountActivity;
import com.kaurihealth.kaurihealth.services.ServiceFactory;
import com.kaurihealth.kaurihealth.util.Enums.LoadingStatu;
import com.kaurihealth.kaurihealth.util.Url;
import com.kaurihealth.utilslib.constant.Global;
import com.youyou.zllibrary.util.CommonFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends CommonFragment {
    @Bind(R.id.lv_content)
    ListView lvContent;

    @Bind(R.id.swipe_refresh)
    SwipeRefreshLayout refreshTool;
    private List<PatientRequestDisplayBean> list;
    private PatientAdapter patientAdapter;
    public static final int Update = 8;
    public static final int UpdateAcc = 7;
    private View view;
    private IPatientRequestService patientRequestService;
    private int index = 2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.homefragment, null);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    @Override
    public void init() {
        super.init();
        list = new ArrayList<>();
        patientAdapter = new PatientAdapter(getContext(), list);
        lvContent.setAdapter(patientAdapter);

        refreshTool.setSize(SwipeRefreshLayout.DEFAULT);
        refreshTool.setColorSchemeResources(R.color.holo_blue_light_new
                , R.color.holo_blue_light_new,
                R.color.holo_blue_light_new, R.color.holo_blue_light_new);
        refreshTool.setProgressBackgroundColor(R.color.linelogin);
        refreshTool.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });
        lvContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PatientRequestDisplayBean selectedItem = list.get(position);
                goToActivity(selectedItem);
            }
        });
        patientRequestService = new ServiceFactory(Url.prefix, getContext()).getPatientRequestService();
        getData();
    }

    /**
     * 点击listview中的某一个子项，跳转到该子项代表的activity中
     */
    private void goToActivity(PatientRequestDisplayBean patientRequestDisplayBean) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("CurrentPatientRequest", patientRequestDisplayBean);
        skipToForResult(PatientRequestInfoActivity.class, bundle, RequestCode.FROM_HOME_FRAGMENT);
    }

    @OnClick({R.id.iv_scanne_homefragment, R.id.iv_search_homefragment})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_scanne_homefragment:  //快速帮患者注册按钮
                skipToForResult(OpenAnAccountActivity.class, null, Global.ForResult.ToOpenAccount);
                break;
            case R.id.iv_search_homefragment:
                skipToForResult(SearchActivityNew.class, null, Global.ForResult.ToSearch);
                break;
        }
    }


    protected void getData() {
        Call<List<PatientRequestDisplayBean>> call = patientRequestService.LoadPatientRequestsByDoctor();
        call.enqueue(new Callback<List<PatientRequestDisplayBean>>() {
            @Override
            public void onResponse(Call<List<PatientRequestDisplayBean>> call, Response<List<PatientRequestDisplayBean>> response) {
                if (response.isSuccessful()) {
                    list.clear();
                    list.addAll(response.body());
                    patientAdapter.notifyDataSetChanged();
                }
                if (refreshTool != null) {
                    refreshTool.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<List<PatientRequestDisplayBean>> call, Throwable t) {
                showToast(LoadingStatu.NetError.value);

                if (refreshTool != null) {
                    refreshTool.setRefreshing(false);
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RequestCode.FROM_HOME_FRAGMENT) {
            if (resultCode == Update) {
                getData();
            }
            if (resultCode == UpdateAcc) {
                // ((MainActivity)(getActivity())).selectPatientAndDoctor();
                // ((MainActivity)(getActivity())).selectCurItem(index);
                getData();
            }
        }
    }
}
