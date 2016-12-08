package com.kaurihealth.kaurihealth.clinical_v.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.kaurihealth.datalib.request_bean.bean.MedicalLiteratureDisPlayBean;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.kaurihealth.clinical_v.adapter.ClinicalListAdapter;
import com.kaurihealth.kaurihealth.tinker.SampleApplicationLike;
import com.kaurihealth.mvplib.clinical_p.CliniCalListPresenter;
import com.kaurihealth.mvplib.clinical_p.IClinicalListActivityView;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * Created by mip on 2016/9/1.
 */
public class ClinicalListActivity extends BaseActivity implements IClinicalListActivityView{
    @Bind(R.id.tv_clinicalName)
    TextView tvClinicalName;
    @Bind(R.id.prlv_clinical)
    ListView prlvClinical;

    private Bundle bundle;
    private List<MedicalLiteratureDisPlayBean> literatureDisPlayBeanList;
    private ClinicalListAdapter clinicalListAdapter;
    private MedicalLiteratureDisPlayBean medicalLiteratureDisPlayBean;
    private String itemValue;
    private int browse = 0;
    private int posiint = 0;
    private boolean aBoolean = false;

    @Inject
    CliniCalListPresenter<IClinicalListActivityView> mPresenter;
    @Override
    protected int getActivityLayoutID() {
        return R.layout.activity_clinicallist;
    }

    @Override
    protected void initPresenterAndView(Bundle savedInstanceState) {
        SampleApplicationLike.getApp().getComponent().inject(this);
        mPresenter.setPresenter(this);
    }

    @Override
    protected void initDelayedData() {
        initBackBtn(R.id.iv_back);
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
                medicalLiteratureDisPlayBean = literatureDisPlayBeanList.get(position);
                mPresenter.onSubscribe();

            }
        });
    }

    @Override
    public void switchPageUI(String className) {

    }

    /**
     * 得到被点击的item的id
     * @return
     */
    @Override
    public int getCurrentMedicalLiteratureId() {
        return medicalLiteratureDisPlayBean.medicalLiteratureId;
    }

    /**
     * 得到被点击后的item的更新bean
     * @param bean
     */
    @Override
    public void getMedicalLiteratureDisPlayBean(MedicalLiteratureDisPlayBean bean) {
        aBoolean = true;
        Bundle bundle = new Bundle();
        bundle.putSerializable("MedicalLiteratureDisPlayBean", bean);
        skipToForResult(DynamicActivity.class, bundle, 0);
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
