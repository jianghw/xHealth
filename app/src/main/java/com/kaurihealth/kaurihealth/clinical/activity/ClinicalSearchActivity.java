package com.kaurihealth.kaurihealth.clinical.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.kaurihealth.datalib.request_bean.bean.MedicalLiteratureDisPlayBean;
import com.kaurihealth.datalib.service.IMedicalLiteratureService;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.clinical.adapter.ClinicalListAdapter;
import com.kaurihealth.kaurihealth.clinical.util.ClinicalUtil;
import com.kaurihealth.kaurihealth.clinical_v.DynamicActivity;
import com.kaurihealth.kaurihealth.services.ServiceFactory;
import com.kaurihealth.kaurihealth.util.Enums.LoadingStatu;
import com.kaurihealth.kaurihealth.util.ExitApplication;
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

/**
 * 搜索
 */
public class ClinicalSearchActivity extends CommonActivity {

    @Bind(R.id.et_Search)
    EditText edSearch;
    @Bind(R.id.lv_clinical)
    ListView lvclinical;

    private IMedicalLiteratureService medicalLiteratureService;
    private List<MedicalLiteratureDisPlayBean> literatureDisPlayBeanList = new ArrayList<>();
    private ClinicalListAdapter clinicalListAdapter;

    private String medicalLiteratureTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinical_search);
        ButterKnife.bind(this);
        init();
        ExitApplication.getInstance().addActivity(this);
    }

    @Override
    public void init() {
        super.init();
        setBack(R.id.iv_back);
        medicalLiteratureService = new ServiceFactory(Url.prefix,getApplicationContext()).getMedicalLiteratureService();
        medicalLiteratureTitle = edSearch.getText().toString().trim();
        clinicalListAdapter = new ClinicalListAdapter(this, literatureDisPlayBeanList);
        lvclinical.setAdapter(clinicalListAdapter);
        lvclinical.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MedicalLiteratureDisPlayBean medicalLiteratureDisPlayBean = literatureDisPlayBeanList.get(position);
                Call<MedicalLiteratureDisPlayBean> listCall = medicalLiteratureService.LoadMedicalLiteratureById_out(medicalLiteratureDisPlayBean.medicalLiteratureId);
                listCall.enqueue(new Callback<MedicalLiteratureDisPlayBean>() {
                    @Override
                    public void onResponse(Call<MedicalLiteratureDisPlayBean> call, Response<MedicalLiteratureDisPlayBean> response) {
                        if (response.isSuccessful()) {
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

    public void getliteratureDisPlayBeanList(final String medicalLiteratureTitle) {
        Call<List<MedicalLiteratureDisPlayBean>> listCall = medicalLiteratureService.LoadAllMedicalLiteratures();
        listCall.enqueue(new Callback<List<MedicalLiteratureDisPlayBean>>() {
            @Override
            public void onResponse(Call<List<MedicalLiteratureDisPlayBean>> call, Response<List<MedicalLiteratureDisPlayBean>> response) {
                if (response.isSuccessful()) {
                    literatureDisPlayBeanList.clear();
                    literatureDisPlayBeanList.addAll(new ClinicalUtil().getListMedicalLiteratureDisPlayBeanBytitleImage(response.body(), medicalLiteratureTitle));
                    clinicalListAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<MedicalLiteratureDisPlayBean>> call, Throwable t) {
                showToast(LoadingStatu.NetError.value);
            }
        });
    }

    @OnClick(R.id.tv_Search)
    public void onClick_tv_Search() {
        medicalLiteratureTitle = edSearch.getText().toString().trim();
        getliteratureDisPlayBeanList(medicalLiteratureTitle);
        clinicalListAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getliteratureDisPlayBeanList(medicalLiteratureTitle);
        clinicalListAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
