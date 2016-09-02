package com.kaurihealth.kaurihealth.home.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.commonlibrary.widget.util.LoadingUtil;
import com.kaurihealth.datalib.request_bean.bean.DoctorPatientRelationshipBean;
import com.kaurihealth.datalib.request_bean.bean.DoctorRelationshipBean;
import com.kaurihealth.datalib.service.IDoctorPatientRelationshipService;
import com.kaurihealth.datalib.service.IDoctorRelationshipService;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.home.bean.FragmentTitleSearchBean;
import com.kaurihealth.kaurihealth.home.fragment.SearchCommonFragment;
import com.kaurihealth.kaurihealth.home.fragment.SearchPatientFragment;
import com.kaurihealth.kaurihealth.home.util.ISearchController;
import com.kaurihealth.kaurihealth.home.util.SearchCommonAdapter;
import com.kaurihealth.kaurihealth.mine.adapter.FragmentPageAdapter;
import com.kaurihealth.kaurihealth.services.IServiceFactory;
import com.kaurihealth.kaurihealth.services.ServiceFactory;
import com.kaurihealth.kaurihealth.util.Getter;
import com.kaurihealth.kaurihealth.util.Interface.IGetter;
import com.kaurihealth.kaurihealth.util.TextWatchClearError;
import com.kaurihealth.kaurihealth.util.Url;
import com.youyou.zllibrary.util.CommonActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivityNew extends CommonActivity {

    @Bind(R.id.etSearch)
    EditText etSearch;
    @Bind(R.id.vpContent)
    ViewPager vpContent;
    @Bind(R.id.tablayTop)
    TabLayout tablayTop;
    @Bind(R.id.iv_delete)
    ImageView iv_delete;
//    @Bind(R.id.search_swipe)
//    SwipeRefreshLayout search_swipe;
    List<FragmentTitleSearchBean> fragmentBeanList = new ArrayList<>();
    SearchCommonFragment searchAllFragment = new SearchCommonFragment();
    SearchCommonFragment searchHospitalFragment = new SearchCommonFragment();
    SearchCommonFragment searchDepartmentFragment = new SearchCommonFragment();
    SearchCommonFragment searchDoctorFragment = new SearchCommonFragment();
    SearchPatientFragment searchPatientFragment = new SearchPatientFragment();
    private IDoctorPatientRelationshipService getAllPatientsService;
    private IDoctorRelationshipService getAllDoctorService;
    private List<Integer> doctorFriends = new ArrayList<>();
    private List<Integer> patientFriends = new ArrayList<>();
    private IGetter getter;
    ISearchController controller = new ISearchController() {
        @Override
        public boolean isDoctorFriend(int doctorId) {
            return doctorFriends.contains(doctorId);
        }

        @Override
        public boolean isPatientFriend(int patientId) {
            return patientFriends.contains(patientId);
        }

        @Override
        public void addDoctorFriend(int doctorId) {
            doctorFriends.add(doctorId);
        }

        @Override
        public void addPatientFriend(int patientId) {
            patientFriends.add(patientId);
        }

        @Override
        public void startActivityForResult(Class<? extends Activity> purpose, Bundle bundle, int requestCode) {
            skipToForResult(purpose, bundle, requestCode);
        }
    };

    {
        searchAllFragment.setScope("default");
        searchHospitalFragment.setScope("hospital");
        searchDepartmentFragment.setScope("department");
        searchDoctorFragment.setScope("doctor");

        searchAllFragment.setISearchController(controller);
        searchHospitalFragment.setISearchController(controller);
        searchDepartmentFragment.setISearchController(controller);
        searchDoctorFragment.setISearchController(controller);
        searchPatientFragment.setISearchController(controller);
        fragmentBeanList.add(new FragmentTitleSearchBean(searchAllFragment, "所有", searchAllFragment.search));
        fragmentBeanList.add(new FragmentTitleSearchBean(searchHospitalFragment, "医院", searchHospitalFragment.search));
        fragmentBeanList.add(new FragmentTitleSearchBean(searchDepartmentFragment, "科室", searchDepartmentFragment.search));
        fragmentBeanList.add(new FragmentTitleSearchBean(searchDoctorFragment, "医生", searchDoctorFragment.search));
        fragmentBeanList.add(new FragmentTitleSearchBean(searchPatientFragment, "患者", searchPatientFragment.search));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_new);
        setBack(R.id.tvBack);
        ButterKnife.bind(this);
        initView();
        initService();
        initData();
    }


    private void initService() {
        IServiceFactory serviceFactory = new ServiceFactory(Url.prefix ,getApplicationContext());
        getAllPatientsService = serviceFactory.getDoctorPatientRelationshipService();
        getAllDoctorService = serviceFactory.getIDoctorRelationshipService();
    }

    private void initView() {
        getter = Getter.getInstance(this);
        FragmentPageAdapter adapter = new FragmentPageAdapter(getSupportFragmentManager(), fragmentBeanList);
        vpContent.setAdapter(adapter);
        tablayTop.setupWithViewPager(vpContent);
//        search_swipe.setRefreshing(true);
//        search_swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                initData();
//            }
//        });
        etSearch.addTextChangedListener(new TextWatchClearError(etSearch,iv_delete));
    }

    private void initData() {
        final LoadingUtil loadingUtil = LoadingUtil.getInstance(this);
        Call<List<DoctorPatientRelationshipBean>> patientCall = getAllPatientsService.LoadDoctorPatientRelationshipForDoctor();
        loadingUtil.show();
        loadingUtil.regist();
        doctorFriends.clear();
        patientFriends.clear();
        patientCall.enqueue(new Callback<List<DoctorPatientRelationshipBean>>() {
            @Override
            public void onResponse(Call<List<DoctorPatientRelationshipBean>> call, Response<List<DoctorPatientRelationshipBean>> response) {
                if (response.isSuccessful()) {
                    for (DoctorPatientRelationshipBean iteam : response.body()) {
                        patientFriends.add(iteam.patientId);
                    }
                }
                loadingUtil.unregist();
//                search_swipe.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<DoctorPatientRelationshipBean>> call, Throwable t) {
                loadingUtil.unregist();
            }
        });
        Call<List<DoctorRelationshipBean>> doctorCall = getAllDoctorService.LoadDoctorRelationships();
        loadingUtil.regist();
        doctorCall.enqueue(new Callback<List<DoctorRelationshipBean>>() {
            @Override
            public void onResponse(Call<List<DoctorRelationshipBean>> call, Response<List<DoctorRelationshipBean>> response) {
                if (response.isSuccessful()) {
                    for (DoctorRelationshipBean iteam : response.body()) {
                        try {

                                doctorFriends.add(iteam.relatedDoctor.doctorId);

                        } catch (NullPointerException ex) {

                        }
                    }
                }
                loadingUtil.unregist();
//                search_swipe.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<DoctorRelationshipBean>> call, Throwable t) {
                loadingUtil.unregist();
//                search_swipe.setRefreshing(false);
            }
        });
    }

    @OnClick({R.id.btnSearch, R.id.iv_delete})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSearch:
                search(getTvValue(etSearch), tablayTop.getSelectedTabPosition());
                hideSoftInput();
                break;
            case R.id.iv_delete:
                clearTextView(etSearch);
                break;
        }
    }

    private void search(String content, int index) {
        fragmentBeanList.get(index).search.search(content);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SearchCommonAdapter.RequestCode:
                if (resultCode == RESULT_OK) {
                    Bundle extras = data.getExtras();
                    int relatedDoctorId = extras.getInt("relatedDoctorId", -1);
                    if (relatedDoctorId != -1) {
                        controller.addDoctorFriend(relatedDoctorId);
                        for (FragmentTitleSearchBean iteam : fragmentBeanList) {
                            iteam.search.addDoctorFriendSuccess(relatedDoctorId);
                        }
                    }
                }
                break;
        }
    }
}
