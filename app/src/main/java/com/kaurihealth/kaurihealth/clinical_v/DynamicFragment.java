package com.kaurihealth.kaurihealth.clinical_v;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.kaurihealth.datalib.request_bean.bean.MedicalLiteratureDisPlayBean;
import com.kaurihealth.datalib.request_bean.builder.Category;
import com.kaurihealth.kaurihealth.MyApplication;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.base_v.BaseFragment;

import com.kaurihealth.kaurihealth.clinical.adapter.ClinicalAdapter;
import com.kaurihealth.kaurihealth.clinical.util.ClinicalUtil;
import com.kaurihealth.mvplib.clinical_p.DynamicPresenter;
import com.kaurihealth.mvplib.clinical_p.IDynamicView;
import com.kaurihealth.utilslib.widget.ScrollChildSwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * Created by mip on 2016/8/24.
 */
public class DynamicFragment extends BaseFragment implements IDynamicView {

    @Bind(R.id.lv_content)
    ListView mLvContent;
    @Bind(R.id.swipe_refresh)
    ScrollChildSwipeRefreshLayout mSwipeRefresh;
    private List<MedicalLiteratureDisPlayBean> medicalLiteratureDisPlayBeanList = new ArrayList<>();
    private ClinicalAdapter adapter;
    private ArrayList<Category> mListData = new ArrayList<>();
    private MedicalLiteratureDisPlayBean mMedicalLiteratureDisPlayBean = new MedicalLiteratureDisPlayBean();


    @Inject
    DynamicPresenter<IDynamicView> mPresenter;


    public static DynamicFragment newInstance() {
        return new DynamicFragment();
    }

    @Override
    protected int getFragmentLayoutID() {
        return R.layout.fragment_dynamic;
    }

    @Override
    protected void initPresenterAndData() {
        MyApplication.getApp().getComponent().inject(this);
        mPresenter.setPresenter(this);

        initPagerView();
    }

    private void initPagerView() {
        adapter = new ClinicalAdapter(getContext(), getActivity(), mListData, medicalLiteratureDisPlayBeanList);
        mLvContent.setAdapter(adapter);
        adapter.notifyDataSetChanged();
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
                MedicalLiteratureDisPlayBean medicalLiteratureDisPlayBean = (MedicalLiteratureDisPlayBean) adapter.getItem(position);
                mPresenter.LoadMedicalLiteratureById(medicalLiteratureDisPlayBean.medicalLiteratureId);
            }
        });
    }

    /**
     * 调用p层获取数据
     */
    @Override
    protected void lazyLoadingData() {
        mPresenter.onSubscribe();
    }

    @Override
    public void switchPageUI(String className) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("MedicalLiteratureDisPlayBean", mMedicalLiteratureDisPlayBean);
        skipToForResult(DynamicActivity.class, bundle, 0);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.unSubscribe();
    }

    /**
     * 获取数据
     *
     * @param mMedicalLiteratureDisPlayBeanList
     */
    @Override
    public void getAllMedicalLitreaturesSuccess(List<MedicalLiteratureDisPlayBean> mMedicalLiteratureDisPlayBeanList) {
        if (medicalLiteratureDisPlayBeanList == null)
            throw new IllegalStateException("medicalLiteratureDisPlayBeanList must be not null");
        if (medicalLiteratureDisPlayBeanList.size() > 0) medicalLiteratureDisPlayBeanList.clear();
        medicalLiteratureDisPlayBeanList.addAll(new ClinicalUtil().getListMedicalLiteratureDisPlayBean(mMedicalLiteratureDisPlayBeanList));
        mPresenter.getArrayList();
        adapter.notifyDataSetChanged();
    }

    /**
     * 获取数据失败
     *
     * @param error
     */
    @Override
    public void getAllMedicalLitreaturesError(String error) {
        showToast(error);
    }

    /**
     * 每个item的点击后更新数据
     *
     * @param mMedicalLiteratureDisPlayBeanList
     * @return
     */
    @Override
    public MedicalLiteratureDisPlayBean getMedicalLitreatures(MedicalLiteratureDisPlayBean mMedicalLiteratureDisPlayBeanList) {
        return this.mMedicalLiteratureDisPlayBean = mMedicalLiteratureDisPlayBeanList;
    }

    /**
     * 得到当前集合数据
     * @return
     */
    @Override
    public List<MedicalLiteratureDisPlayBean> getMedicalLiteratureDisPlayBeanList() {
        if (medicalLiteratureDisPlayBeanList == null)
            throw new IllegalStateException("medicalLiteratureDisPlayBeanList must be not null");
        return medicalLiteratureDisPlayBeanList;
    }

    @Override
    public ArrayList<Category> getCategoryList() {
        if (mListData == null)
            throw new IllegalStateException("mListData must be not null");
        return mListData;
    }


}
