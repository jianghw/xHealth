package com.kaurihealth.kaurihealth.clinical_v.Fragment;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.kaurihealth.datalib.request_bean.bean.MedicalLiteratureDisPlayBean;
import com.kaurihealth.datalib.request_bean.builder.Category;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.base_v.BaseFragment;
import com.kaurihealth.kaurihealth.clinical_v.Utils.ClinicalUtil;
import com.kaurihealth.kaurihealth.clinical_v.activity.DynamicActivity;
import com.kaurihealth.kaurihealth.clinical_v.adapter.ClinicalAdapter;
import com.kaurihealth.kaurihealth.eventbus.ClinicalRefreshEvent;
import com.kaurihealth.kaurihealth.tinker.SampleApplicationLike;
import com.kaurihealth.mvplib.clinical_p.ITreatmentView;
import com.kaurihealth.mvplib.clinical_p.TreatmentPresenter;
import com.kaurihealth.utilslib.OnClickUtils;
import com.kaurihealth.utilslib.widget.ScrollChildSwipeRefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import de.halfbit.pinnedsection.PinnedSectionListView;

/**
 * Created by mip on 2016/8/26.
 */
public class TreatmentFragment extends BaseFragment implements ITreatmentView {

    @Bind(R.id.lv_content)
    PinnedSectionListView mLvContent;
    @Bind(R.id.swipe_refresh)
    ScrollChildSwipeRefreshLayout mSwipeRefresh;
    private ArrayList<Category> mListData = new ArrayList<>();
    private List<MedicalLiteratureDisPlayBean> medicalLiteratureDisPlayBeanList = new ArrayList<>();
    private MedicalLiteratureDisPlayBean mMedicalLiteratureDisPlayBean;
    private TextView textView;
    private ClinicalAdapter mAdapter;

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
    protected void initPresenterAndView(Bundle savedInstanceState) {
        SampleApplicationLike.getApp().getComponent().inject(this);
        mPresenter.setPresenter(this);

        initPagerView();
        EventBus.getDefault().register(this);
    }

    private void initPagerView() {
        mAdapter = new ClinicalAdapter(getContext(), getActivity(), mListData, medicalLiteratureDisPlayBeanList);
        mLvContent.setAdapter(mAdapter);
    }

    @Override
    protected void initDelayedData() {
        mSwipeRefresh.setColorSchemeColors(
                ContextCompat.getColor(getActivity(), R.color.welcome_bg_cl),
                ContextCompat.getColor(getActivity(), R.color.welcome_bg_cl),
                ContextCompat.getColor(getActivity(), R.color.welcome_bg_cl)
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
                if (OnClickUtils.onNoDoubleClick()) return;
                MedicalLiteratureDisPlayBean medicalLiteratureDisPlayBean = (MedicalLiteratureDisPlayBean) mAdapter.getItem(position);
                /**mPresenter.LoadMedicalLiteratureById(medicalLiteratureDisPlayBean.medicalLiteratureId);*/

                Bundle bundle = new Bundle();
                bundle.putSerializable("MedicalLiteratureDisPlayBean", medicalLiteratureDisPlayBean);
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
     */
    @Override
    public void getAllMedicalLitreaturesSuccess(List<MedicalLiteratureDisPlayBean> mMedicalLiteratureDisPlayBeanList) {
        if (medicalLiteratureDisPlayBeanList != null && medicalLiteratureDisPlayBeanList.size() > 0)
            medicalLiteratureDisPlayBeanList.clear();
        medicalLiteratureDisPlayBeanList.addAll(new ClinicalUtil().getListMedicalLiteratureDisPlayBean(mMedicalLiteratureDisPlayBeanList));
        mPresenter.getArrayList();
        if (medicalLiteratureDisPlayBeanList.size() > 5 && mLvContent.getFooterViewsCount() == 0) {
            textView = new TextView(getActivity());
            textView.setText("已加载完成");
            textView.setEnabled(false);
            mLvContent.addFooterView(textView, null, false);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public ArrayList<Category> getCategoryList() {
        if (mListData == null)
            throw new IllegalStateException("mListData must be not null");
        return mListData;
    }

    @Override
    public List<MedicalLiteratureDisPlayBean> getMedicalLiteratureDisPlayBeanList() {
        if (medicalLiteratureDisPlayBeanList == null)
            throw new IllegalStateException("medicalLiteratureDisPlayBeanList must be not null");
        return medicalLiteratureDisPlayBeanList;
    }

    /**
     * 获取数据失败
     */
    @Override
    public void getAllMedicalLitreaturesError(String error) {
        showToast(error);
    }

    /**
     * 每个item的点击后更新数据
     *
     * @param mMedicalLiteratureDisPlayBean
     * @return
     */
    @Override
    public MedicalLiteratureDisPlayBean getMedicalLitreatures(MedicalLiteratureDisPlayBean mMedicalLiteratureDisPlayBean) {
        return this.mMedicalLiteratureDisPlayBean = mMedicalLiteratureDisPlayBean;
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
        EventBus.getDefault().unregister(this);
        mPresenter.unSubscribe();
    }

    /**
     * ----------------------------订阅事件----------------------------------
     **/
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void eventBusMain(ClinicalRefreshEvent event) {
        mPresenter.loadingRemoteData(true);
    }
}
