package com.kaurihealth.kaurihealth.clinical_v.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.kaurihealth.datalib.request_bean.bean.MedicalLiteratureDisPlayBean;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.kaurihealth.clinical_v.Utils.ClinicalUtil;
import com.kaurihealth.kaurihealth.clinical_v.adapter.ClinicalListAdapter;
import com.kaurihealth.kaurihealth.tinker.SampleApplicationLike;
import com.kaurihealth.mvplib.clinical_p.ClinicalSearchPresenter;
import com.kaurihealth.mvplib.clinical_p.IClinicalSearchView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by mip on 2016/9/1.
 */
public class ClinicalSearchActivity extends BaseActivity implements IClinicalSearchView{

    @Bind(R.id.et_Search)
    EditText edSearch;
    @Bind(R.id.lv_clinical)
    ListView lvclinical;

    private List<MedicalLiteratureDisPlayBean> literatureDisPlayBeanList = new ArrayList<>();
    private ClinicalListAdapter clinicalListAdapter;

    private String medicalLiteratureTitle;
    private MedicalLiteratureDisPlayBean medicalLiteratureDisPlayBean;

    @Inject
    ClinicalSearchPresenter<IClinicalSearchView> mPresenter;

    @Override
    protected int getActivityLayoutID() {
        return R.layout.activity_clinical_search;
    }

    @Override
    protected void initPresenterAndView(Bundle savedInstanceState) {
        SampleApplicationLike.getApp().getComponent().inject(this);
        mPresenter.setPresenter(this);
    }

    @Override
    protected void initDelayedData() {
        initBackBtn(R.id.iv_back);
        medicalLiteratureTitle = edSearch.getText().toString().trim();
        clinicalListAdapter = new ClinicalListAdapter(this, literatureDisPlayBeanList);
        lvclinical.setAdapter(clinicalListAdapter);
        lvclinical.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                medicalLiteratureDisPlayBean = literatureDisPlayBeanList.get(position);
                mPresenter.onSubscribe();
            }
        });
    }

    @Override
    public void switchPageUI(String className) {

    }

    /**
     * 得到当前item的id
     * @return
     */
    @Override
    public int getCurrentMedicalLiteratureId() {
        return medicalLiteratureDisPlayBean.medicalLiteratureId;
    }

    /**
     * 得到更新后的bean
     * @param bean
     */
    @Override
    public void getMedicalLiteratureDisPlayBean(MedicalLiteratureDisPlayBean bean) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("MedicalLiteratureDisPlayBean", bean);
        skipToForResult(DynamicActivity.class, bundle, 0);
    }

    //得到所有的临床支持
    @Override
    public void getliteratureDisPlayBeanList(List<MedicalLiteratureDisPlayBean> been) {
        medicalLiteratureTitle = edSearch.getText().toString().trim();
        if (literatureDisPlayBeanList == null){
            throw new IllegalStateException("literatureDisPlayBeanList must be not null");
        }
        if (literatureDisPlayBeanList.size() > 0) literatureDisPlayBeanList.clear();
        literatureDisPlayBeanList.clear();
        literatureDisPlayBeanList.addAll(new ClinicalUtil().getListMedicalLiteratureDisPlayBeanBytitleImage(been, medicalLiteratureTitle));
        clinicalListAdapter.notifyDataSetChanged();
    }

    /**
     * 搜索
     */
    @OnClick(R.id.tv_Search)
    public void onClick_tv_Search() {
        String context = edSearch.getText().toString().trim();
        if (context.equals("")) return;
        mPresenter.getliteratureDisPlayBeanList();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.getliteratureDisPlayBeanList();
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if(event.getKeyCode() == KeyEvent.KEYCODE_ENTER){
            /*隐藏软键盘*/
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if(inputMethodManager.isActive()){
                inputMethodManager.hideSoftInputFromWindow(ClinicalSearchActivity.this.getCurrentFocus().getWindowToken(), 0);
            }

            String context = edSearch.getText().toString().trim();
            if (!context.equals("")) {
                mPresenter.getliteratureDisPlayBeanList();
            }

            return true;
        }
        return super.dispatchKeyEvent(event);
    }
}
