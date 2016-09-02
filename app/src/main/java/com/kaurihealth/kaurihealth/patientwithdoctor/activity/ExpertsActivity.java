package com.kaurihealth.kaurihealth.patientwithdoctor.activity;

import android.os.Bundle;
import android.widget.GridView;

import com.kaurihealth.datalib.request_bean.bean.DoctorPatientRelationshipBean;
import com.kaurihealth.datalib.service.IDoctorPatientRelationshipService;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.common.adapter.ExpertsAdapter;
import com.kaurihealth.kaurihealth.services.ServiceFactory;
import com.kaurihealth.kaurihealth.util.ExitApplication;
import com.kaurihealth.kaurihealth.util.Getter;
import com.kaurihealth.kaurihealth.util.Interface.IGetter;
import com.kaurihealth.kaurihealth.util.Url;
import com.youyou.zllibrary.util.CommonActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExpertsActivity extends CommonActivity {

    @Bind(R.id.gv_content)
    GridView gvContent;

    private ExpertsAdapter expertsAdapter;
    private List<DoctorPatientRelationshipBean> doctorPatientRelationshipBeanList;
    private IDoctorPatientRelationshipService doctorPatientRelationshipService;
    private Bundle bundle;

    private int patientId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experts);
        ButterKnife.bind(this);
        init();
        ExitApplication.getInstance().addActivity(this);
    }

    @Override
    public void init() {
        super.init();
        setBack(R.id.iv_back);
        bundle = getBundle();
        patientId = bundle.getInt("patientId");
        IGetter getter = Getter.getInstance(this);
        doctorPatientRelationshipService = new ServiceFactory(Url.prefix,getApplicationContext()).getDoctorPatientRelationshipService();
        doctorPatientRelationshipBeanList = new ArrayList<>();
        expertsAdapter = new ExpertsAdapter(getApplicationContext(), doctorPatientRelationshipBeanList);
        gvContent.setAdapter(expertsAdapter);
    }


    /**
     * 加载数据
     */
    public void getData(int patientId) {
        Call<List<DoctorPatientRelationshipBean>> call = doctorPatientRelationshipService.LoadDoctorPatientRelationshipForPatientId(patientId);
        call.enqueue(new Callback<List<DoctorPatientRelationshipBean>>() {
            @Override
            public void onResponse(Call<List<DoctorPatientRelationshipBean>> call, Response<List<DoctorPatientRelationshipBean>> response) {
                if (response.isSuccessful()) {
                    DoctorPatientRelationshipBean doctorPatientRelationshipBean = (DoctorPatientRelationshipBean) response.body();
                    expertsAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<DoctorPatientRelationshipBean>> call, Throwable t) {

            }
        });
    }
}
