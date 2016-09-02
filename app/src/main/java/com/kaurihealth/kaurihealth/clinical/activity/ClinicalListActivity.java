package com.kaurihealth.kaurihealth.clinical.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.kaurihealth.datalib.request_bean.bean.MedicalLiteratureDisPlayBean;
import com.kaurihealth.datalib.service.IMedicalLiteratureService;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.clinical.adapter.ClinicalListAdapter;
import com.kaurihealth.kaurihealth.clinical_v.DynamicActivity;
import com.kaurihealth.kaurihealth.services.ServiceFactory;
import com.kaurihealth.kaurihealth.util.Enums.LoadingStatu;
import com.kaurihealth.kaurihealth.util.ExitApplication;
import com.kaurihealth.kaurihealth.util.Url;
import com.youyou.zllibrary.util.CommonActivity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClinicalListActivity extends CommonActivity {

    @Bind(R.id.tv_clinicalName)
    TextView tvClinicalName;
    @Bind(R.id.prlv_clinical)
    ListView prlvClinical;

    private Bundle bundle;
    private List<MedicalLiteratureDisPlayBean> literatureDisPlayBeanList;
    private ClinicalListAdapter clinicalListAdapter;
    private IMedicalLiteratureService medicalLiteratureService;

    private String itemValue;
    private int browse = 0;
    private int posiint = 0;
    private boolean aBoolean = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinicallist);
        ButterKnife.bind(this);
        init();
        ExitApplication.getInstance().addActivity(this);
    }

    @Override
    public void init() {
        super.init();
        setBack(R.id.iv_back);
        medicalLiteratureService = new ServiceFactory(Url.prefix,getApplicationContext()).getMedicalLiteratureService();
        bundle = getBundle();
        itemValue = bundle.getString("itemValue");
        literatureDisPlayBeanList = (List<MedicalLiteratureDisPlayBean>) bundle.getSerializable("medicalLiteratureDisPlayBeanList");
        tvClinicalName.setText(itemValue);
        clinicalListAdapter = new ClinicalListAdapter(this, literatureDisPlayBeanList);
        prlvClinical.setAdapter(clinicalListAdapter);

        prlvClinical.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                browse = literatureDisPlayBeanList.get(position).browse + 1;
                posiint = position;
                MedicalLiteratureDisPlayBean medicalLiteratureDisPlayBean = literatureDisPlayBeanList.get(position);
                Call<MedicalLiteratureDisPlayBean> listCall = medicalLiteratureService.LoadMedicalLiteratureById_out(medicalLiteratureDisPlayBean.medicalLiteratureId);
                listCall.enqueue(new Callback<MedicalLiteratureDisPlayBean>() {
                    @Override
                    public void onResponse(Call<MedicalLiteratureDisPlayBean> call, Response<MedicalLiteratureDisPlayBean> response) {
                        if (response.isSuccessful()) {
                            aBoolean = true;
                            MedicalLiteratureDisPlayBean Bean = response.body();
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("MedicalLiteratureDisPlayBean", Bean);
                            skipToForResult(DynamicActivity.class, bundle, 0);
                        }
                    }

                    @Override
                    public void onFailure(Call<MedicalLiteratureDisPlayBean> call, Throwable t) {
                        showToast(LoadingStatu.NetError.value);
                    }
                });
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (aBoolean) {
            literatureDisPlayBeanList.get(posiint).browse = browse;
            clinicalListAdapter.notifyDataSetChanged();
        }
    }
}
