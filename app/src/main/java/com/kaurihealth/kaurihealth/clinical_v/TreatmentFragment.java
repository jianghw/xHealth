package com.kaurihealth.kaurihealth.clinical_v;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.kaurihealth.datalib.request_bean.bean.MedicalLiteratureDisPlayBean;
import com.kaurihealth.kaurihealth.MyApplication;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.base_v.BaseFragment;
import com.kaurihealth.kaurihealth.clinical.adapter.TreatmentAdapter;
import com.kaurihealth.kaurihealth.clinical.util.ClinicalUtil;
import com.kaurihealth.mvplib.clinical_p.ITreatmentView;
import com.kaurihealth.mvplib.clinical_p.TreatmentPresenter;
import com.kaurihealth.utilslib.widget.ScrollChildSwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * Created by mip on 2016/8/26.
 */
public class TreatmentFragment extends BaseFragment implements ITreatmentView{

    @Bind(R.id.lv_content)
    ListView mLvContent;
    @Bind(R.id.swipe_refresh)
    ScrollChildSwipeRefreshLayout mSwipeRefresh;

    private List<MedicalLiteratureDisPlayBean> medicalLiteratureDisPlayBeanList = new ArrayList<>();
    private TreatmentAdapter mAdapter;
    private MedicalLiteratureDisPlayBean mMedicalLiteratureDisPlayBean;

    @Inject
    TreatmentPresenter<ITreatmentView> mPresenter;

    public static TreatmentFragment newInstance() {
        return new TreatmentFragment();
    }
    @Override
    protected int getFragmentLayoutID() {
        return R.layout.fragment_treatment;
    }

    @Override
    protected void initPresenterAndData() {
        MyApplication.getApp().getComponent().inject(this);
        mPresenter.setPresenter(this);

        initPagerView();
    }

    private void initPagerView() {
        mAdapter = new TreatmentAdapter(getContext(), medicalLiteratureDisPlayBeanList);
        mLvContent.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void initDelayedView() {

        mSwipeRefresh.setColorSchemeColors(
                ContextCompat.getColor(getActivity(), R.color.colorPrimary),
                ContextCompat.getColor(getActivity(), R.color.colorAccent),
                ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark)
        );
        mSwipeRefresh.setScrollUpChild(mLvContent);
        mSwipeRefresh.setDistanceToTriggerSync(300);
        mSwipeRefresh.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        mPresenter.loadingRemoteData(true);
                    }
                });

        mLvContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MedicalLiteratureDisPlayBean medicalLiteratureDisPlayBean = (MedicalLiteratureDisPlayBean) mAdapter.getItem(position);
                mPresenter.LoadMedicalLiteratureById(medicalLiteratureDisPlayBean.medicalLiteratureId);
                Bundle bundle = new Bundle();
                bundle.putSerializable("MedicalLiteratureDisPlayBean", mMedicalLiteratureDisPlayBean);
                skipToForResult(DynamicActivity.class, bundle, 0);
            }
        });
    }

    @Override
    protected void lazyLoadingData() {

        mPresenter.onSubscribe();
    }

    /**
     * 获取数据成功
     * @param mMedicalLiteratureDisPlayBeanList
     */
    @Override
    public void getAllMedicalLitreaturesSuccess(List<MedicalLiteratureDisPlayBean> mMedicalLiteratureDisPlayBeanList) {
        if (medicalLiteratureDisPlayBeanList == null)
            throw new IllegalStateException("medicalLiteratureDisPlayBeanList must be not null");
        if (medicalLiteratureDisPlayBeanList.size() > 0) medicalLiteratureDisPlayBeanList.clear();
        medicalLiteratureDisPlayBeanList.addAll(new ClinicalUtil().getListMedicalLiteratureDisPlayBean(mMedicalLiteratureDisPlayBeanList));
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 获取数据失败
     * @param error
     */
    @Override
    public void getAllMedicalLitreaturesError(String error) {
        showToast(error);
    }

    /**
     * 每个item的点击后更新数据
     * @param mMedicalLiteratureDisPlayBean
     * @return
     */
    @Override
    public MedicalLiteratureDisPlayBean getMedicalLitreatures(MedicalLiteratureDisPlayBean mMedicalLiteratureDisPlayBean) {
        return this.mMedicalLiteratureDisPlayBean = mMedicalLiteratureDisPlayBean;
    }

    @Override
    public void switchPageUI(String className) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.unSubscribe();
    }
}
