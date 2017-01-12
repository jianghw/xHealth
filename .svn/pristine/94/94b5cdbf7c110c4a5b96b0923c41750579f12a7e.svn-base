package com.kaurihealth.kaurihealth.record_details_v;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.kaurihealth.datalib.request_bean.bean.PrescriptionBean;
import com.kaurihealth.datalib.response_bean.DoctorPatientRelationshipBean;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.adapter.PrescriptionAdapter;
import com.kaurihealth.kaurihealth.base_v.BaseFragment;
import com.kaurihealth.kaurihealth.eventbus.AddPrescriptionBeanEvent;
import com.kaurihealth.kaurihealth.eventbus.PresctiptionRefreshEvent;
import com.kaurihealth.kaurihealth.eventbus.PresriptionOnlyReadEvent;
import com.kaurihealth.kaurihealth.patient_v.medical_records.AddAndEditPrescriptionActivityNew;
import com.kaurihealth.kaurihealth.patient_v.medical_records_only_read.PresriptionOnlyReadActivity;
import com.kaurihealth.kaurihealth.tinker.SampleApplicationLike;
import com.kaurihealth.mvplib.patient_p.IPrescriptionView;
import com.kaurihealth.mvplib.patient_p.PrescriptionPresenter;
import com.kaurihealth.utilslib.ColorUtils;
import com.kaurihealth.utilslib.widget.ScrollChildSwipeRefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * Created by mip on 2016/12/19.
 */

public class PresctiptionFragment extends BaseFragment implements IPrescriptionView, RecordDetailsActivity.DeliverListener {
    @Bind(R.id.tv_more)
    TextView mTvMore;
    @Bind(R.id.lv_content)
    ListView lvContent;
    @Bind(R.id.swipe_refresh)
    ScrollChildSwipeRefreshLayout refreshTool;
    @Bind(R.id.tv_prescription_tips)
    TextView tv_prescription_tips;

    private List<PrescriptionBean> list;
    private PrescriptionAdapter prescriptionAdapter;
    public static final int Update = 8;
    private DoctorPatientRelationshipBean doctorPatientRelationshipBean = null;
    private boolean isAble = true;
    private static final int PRESRIPTIONONLYREADACTIVITY = 23;

    @Inject
    PrescriptionPresenter<IPrescriptionView> mPresenter;

    public static PresctiptionFragment getInstance() {
        return new PresctiptionFragment();
    }

    @Override
    protected int getFragmentLayoutID() {
        return R.layout.activity_prescription;
    }

    @Override
    protected void initPresenterAndView(Bundle savedInstanceState) {
        SampleApplicationLike.getApp().getComponent().inject(this);
        mPresenter.setPresenter(this);

        list = new ArrayList<>();
        prescriptionAdapter = new PrescriptionAdapter(getActivity(), list);
        lvContent.setAdapter(prescriptionAdapter);
    }

    @Override
    protected void initDelayedData() {

        mPresenter.loadDoctorDetail();
        mPresenter.loadDoctorPatientRelationshipForPatientId(getPatientId());

//        getData();
        refreshTool.setSize(SwipeRefreshLayout.DEFAULT);
        refreshTool.setColorSchemeColors(
                ColorUtils.setSwipeRefreshColors(getActivity().getApplicationContext())
        );
        refreshTool.setProgressBackgroundColor(R.color.linelogin);
        refreshTool.setScrollUpChild(lvContent);

        refreshTool.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });

        lvContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                PrescriptionBean prescriptionBean = list.get(position);
                EventBus.getDefault().postSticky(new PresriptionOnlyReadEvent(prescriptionBean, doctorPatientRelationshipBean, isAble));//EventBus发送消息被AddAndEditPrescriptionActivityNew接收
                skipToForResult(PresriptionOnlyReadActivity.class, null, PRESRIPTIONONLYREADACTIVITY);
            }
        });

        //注册事件
        EventBus.getDefault().register(this);
    }

    /**
     * 添加完成后的刷新事件
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void eventBusMain(PresctiptionRefreshEvent event) {
        getData();
    }

    @Override
    public void deliverPosition(int position) {
        if (position == 2 && doctorPatientRelationshipBean != null) {
            skipToForResult(AddAndEditPrescriptionActivityNew.class, null, 1);
            EventBus.getDefault().postSticky(new AddPrescriptionBeanEvent(doctorPatientRelationshipBean));
        }
    }

    @Override
    protected void lazyLoadingData() {
        ((RecordDetailsActivity) (getActivity())).setOnclickInterface(this);//注册监听
        getData();
    }

    /**
     * 得到处方数据
     */
    private void getData() {
        mPresenter.onSubscribe();
    }

    /**
     * 得到当前的id
     *
     * @return
     */
    @Override
    public int getPatientId() {
        return ((RecordDetailsActivity) (getActivity())).getPatientId();
    }

    /**
     * 得到网络数据
     *
     * @param prescriptionBeen
     */
    @Override
    public void getPresctiptionBeanList(List<PrescriptionBean> prescriptionBeen) {
        if (list == null) {
            throw new IllegalStateException("list must be not null");
        }
        if (prescriptionBeen.size() > 0) tv_prescription_tips.setVisibility(View.GONE);//隐藏提示信息
        if (list.size() > 0) list.clear();
        list.addAll(prescriptionBeen);
        prescriptionAdapter.notifyDataSetChanged();
        refreshTool.setRefreshing(false);
    }

    /**
     * 得到bean
     *
     * @param relationshipBean
     */
    @Override
    public void getDoctorRelationshipBean(DoctorPatientRelationshipBean relationshipBean) {
        if (relationshipBean != null) {
            doctorPatientRelationshipBean = relationshipBean;
        }
    }

    @Override
    public void switchPageUI(String className) {

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.unSubscribe();
        //注销事件
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void loadingIndicator(boolean flag) {
        if (refreshTool == null) return;
        refreshTool.post(() -> refreshTool.setRefreshing(flag));
    }
}
