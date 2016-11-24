package com.kaurihealth.kaurihealth.clinical_v.Fragment;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.kaurihealth.datalib.request_bean.bean.MedicalLiteratureDisPlayBean;
import com.kaurihealth.datalib.request_bean.builder.Category;
import com.kaurihealth.kaurihealth.MyApplication;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.base_v.BaseFragment;
import com.kaurihealth.kaurihealth.clinical_v.Utils.ClinicalUtil;
import com.kaurihealth.kaurihealth.clinical_v.activity.DynamicActivity;
import com.kaurihealth.kaurihealth.clinical_v.adapter.ClinicalAdapter;
import com.kaurihealth.kaurihealth.eventbus.ClinicalRefreshEvent;
import com.kaurihealth.mvplib.clinical_p.DynamicPresenter;
import com.kaurihealth.mvplib.clinical_p.IDynamicView;
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
 * Created by mip on 2016/8/24.
 */
public class DynamicFragment extends BaseFragment implements IDynamicView {
    @Inject
    DynamicPresenter<IDynamicView> mPresenter;

    @Bind(R.id.lv_content)
    PinnedSectionListView mLvContent;
    @Bind(R.id.swipe_refresh)
    ScrollChildSwipeRefreshLayout mSwipeRefresh;


    private List<MedicalLiteratureDisPlayBean> medicalLiteratureDisPlayBeanList = new ArrayList<>();
    private ClinicalAdapter adapter;
    private ArrayList<Category> mListData = new ArrayList<>();
    private MedicalLiteratureDisPlayBean mMedicalLiteratureDisPlayBean = new MedicalLiteratureDisPlayBean();
    private TextView textView;

    public static DynamicFragment newInstance() {
        return new DynamicFragment();
    }

    @Override
    protected int getFragmentLayoutID() {
        return R.layout.fragment_dynamic;
    }

    @Override
    protected void initPresenterAndView(Bundle savedInstanceState) {
        MyApplication.getApp().getComponent().inject(this);
        mPresenter.setPresenter(this);

        initPagerView();
    }

    private void initPagerView() {
        adapter = new ClinicalAdapter(getContext(), getActivity(), mListData, medicalLiteratureDisPlayBeanList);
        mLvContent.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        EventBus.getDefault().register(this);
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
                () -> mPresenter.loadingRemoteData(true));

        mLvContent.setOnItemClickListener((parent, view, position, id) -> {
                if (OnClickUtils.onNoDoubleClick()) return;
                MedicalLiteratureDisPlayBean medicalLiteratureDisPlayBean = (MedicalLiteratureDisPlayBean) adapter.getItem(position);
                mPresenter.LoadMedicalLiteratureById(medicalLiteratureDisPlayBean.medicalLiteratureId);
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
        EventBus.getDefault().unregister(this);
    }

    /**
     * 获取数据
     *
     * @param mMedicalLiteratureDisPlayBeanList
     */
    @Override
    public void getAllMedicalLitreaturesSuccess(List<MedicalLiteratureDisPlayBean> mMedicalLiteratureDisPlayBeanList) {
        if (medicalLiteratureDisPlayBeanList.size() > 0) medicalLiteratureDisPlayBeanList.clear();
        medicalLiteratureDisPlayBeanList.addAll(new ClinicalUtil().getListMedicalLiteratureDisPlayBean(mMedicalLiteratureDisPlayBeanList));
        mPresenter.getArrayList();
        adapter.notifyDataSetChanged();
        if (medicalLiteratureDisPlayBeanList.size()> 5 && mLvContent.getFooterViewsCount()==0){
            textView = new TextView(getActivity());
            textView.setText("已加载完成");
            textView.setEnabled(false);
            mLvContent.addFooterView(textView,null,false);
        }
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
     *
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

    /**
     * ----------------------------订阅事件----------------------------------
     **/
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void eventBusMain(ClinicalRefreshEvent event) {
        mPresenter.loadingRemoteData(true);
    }

}
