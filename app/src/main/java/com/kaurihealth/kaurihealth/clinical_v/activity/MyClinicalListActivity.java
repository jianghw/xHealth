package com.kaurihealth.kaurihealth.clinical_v.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.kaurihealth.datalib.request_bean.bean.MedicalLiteratureDisPlayBean;
import com.kaurihealth.kaurihealth.MyApplication;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.kaurihealth.clinical_v.Utils.ClinicalUtil;
import com.kaurihealth.kaurihealth.clinical_v.adapter.ClinicalListAdapter;
import com.kaurihealth.mvplib.clinical_p.IMyClinicalListActivityView;
import com.kaurihealth.mvplib.clinical_p.MyClinicalListpresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * Created by mip on 2016/9/1.
 */
public class MyClinicalListActivity extends BaseActivity implements IMyClinicalListActivityView{

    @Bind(R.id.prlv_clinical)
    ListView prlvClinical;

    private Bundle bundle;
    private List<MedicalLiteratureDisPlayBean> literatureDisPlayBeanList = new ArrayList<>();
    private ClinicalListAdapter clinicalListAdapter;
    private MedicalLiteratureDisPlayBean medicalLiteratureDisPlayBean;

    private int UserId;
    private int browse = 0;
    private int posiint = 0;
    private boolean aBoolean = false;

    @Inject
    MyClinicalListpresenter<IMyClinicalListActivityView> mPresenter;

    @Override
    protected int getActivityLayoutID() {
        return R.layout.activity_my_clinical_list;
    }

    @Override
    protected void initPresenterAndView(Bundle savedInstanceState) {
        MyApplication.getApp().getComponent().inject(this);
        mPresenter.setPresenter(this);

        clinicalListAdapter = new ClinicalListAdapter(this, literatureDisPlayBeanList);
        prlvClinical.setAdapter(clinicalListAdapter);
    }

    @Override
    protected void initDelayedData() {
        initBackBtn(R.id.iv_back);
        bundle = getBundle();
        UserId = bundle.getInt("UserId");
        getliteratureDisPlayBeanList();
        prlvClinical.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                browse = literatureDisPlayBeanList.get(position).browse + 1;
                posiint = position;
                medicalLiteratureDisPlayBean = literatureDisPlayBeanList.get(position);
                mPresenter.LoadMedicalLiteratureById();
            }
        });
    }

    @Override
    public void switchPageUI(String className) {

    }

    /**
     * 网络请求
     */
    public void getliteratureDisPlayBeanList() {
        mPresenter.onSubscribe();
    }

    @Override
    public void LoadAllMedicalLiteratures(List<MedicalLiteratureDisPlayBean> medicalLiteratureDisPlayBeen) {
        if (literatureDisPlayBeanList ==null){
            throw new IllegalStateException("literatureDisPlayBeanList must be not null");
        }
        if (literatureDisPlayBeanList.size() > 0) literatureDisPlayBeanList.clear();
        literatureDisPlayBeanList.addAll(new ClinicalUtil().getListMedicalLiteratureDisPlayBeanById(medicalLiteratureDisPlayBeen, UserId));
        clinicalListAdapter.notifyDataSetChanged();
    }

    /**
     * 得到当前点击item的id
     * @return
     */
    @Override
    public int getCurrentMedicalLiteratureId() {
        return medicalLiteratureDisPlayBean.medicalLiteratureId;
    }

    /**
     * 得到刷新后的bean
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
        getliteratureDisPlayBeanList();
        if (aBoolean) {
            literatureDisPlayBeanList.get(posiint).browse = browse;
            clinicalListAdapter.notifyDataSetChanged();
        }
    }
}
