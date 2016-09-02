package com.kaurihealth.kaurihealth.main_v;

import android.support.v4.content.ContextCompat;
import android.support.v4.util.ArrayMap;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.GridView;

import com.kaurihealth.datalib.response_bean.DoctorPatientRelationshipBean;
import com.kaurihealth.datalib.response_bean.PatientDisplayBean;
import com.kaurihealth.kaurihealth.MyApplication;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.adapter.PatientDocRelAdapter;
import com.kaurihealth.kaurihealth.base_v.ChildBaseFragment;
import com.kaurihealth.kaurihealth.chat.activity.SingleChatActivity;
import com.kaurihealth.kaurihealth.eventbus.JumpEvent;
import com.kaurihealth.kaurihealth.patient_v.PatientInfoActivity;
import com.kaurihealth.mvplib.main_p.IPatientView;
import com.kaurihealth.mvplib.main_p.PatientPresenter;
import com.kaurihealth.utilslib.date.DateUtils;
import com.kaurihealth.utilslib.constant.Global;
import com.kaurihealth.utilslib.widget.ScrollChildSwipeRefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;


public class PatientFragment extends ChildBaseFragment implements IPatientView {

    @Bind(R.id.gv_content)
    GridView mGvContent;

    @Bind(R.id.swipe_refresh)
    ScrollChildSwipeRefreshLayout mSwipeRefresh;

    @Inject
    PatientPresenter<IPatientView> mPresenter;
    /**
     * 患者信息
     */
    private List<DoctorPatientRelationshipBean> mBeanList;
    private PatientDocRelAdapter adapter;

    public static PatientFragment newInstance() {
        return new PatientFragment();
    }


    @Override
    protected int getFragmentLayoutID() {
        return R.layout.fragment_patient;
    }

    @Override
    protected void initPresenterAndData() {
        MyApplication.getApp().getComponent().inject(this);
        mPresenter.setPresenter(this);

        mBeanList = new ArrayList<>();
        adapter = new PatientDocRelAdapter(getContext(), mBeanList);
        mGvContent.setAdapter(adapter);
    }

    @Override
    protected void initDelayedView() {
        mSwipeRefresh.setColorSchemeColors(
                ContextCompat.getColor(getActivity(), R.color.colorPrimary),
                ContextCompat.getColor(getActivity(), R.color.colorAccent),
                ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark)
        );
        mSwipeRefresh.setScrollUpChild(mGvContent);
        mSwipeRefresh.setDistanceToTriggerSync(Global.Numerical.SWIPE_REFRESH);
        mSwipeRefresh.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        mPresenter.loadingRemoteData(true);
                    }
                });
        //注册事件
        EventBus.getDefault().register(this);
    }

    @Override
    protected void childLazyLoadingData() {
        mPresenter.onSubscribe();
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.unSubscribe();
        if (mBeanList != null) mBeanList.clear();
        //注销事件
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void switchPageUI(String className) {
        switch (className) {
            case Global.Jump.SingleChatActivity:
                skipTo(SingleChatActivity.class);
                break;
            case Global.Jump.PatientInfoActivity:
                skipTo(PatientInfoActivity.class);
                break;
            default:break;
        }
    }

    @Override
    public void lazyLoadingDataError(String message) {
        showToast(message);
    }

    @Override
    public void lazyLoadingDataSuccess(List<DoctorPatientRelationshipBean> list) {
        if (mBeanList == null)
            throw new IllegalStateException("mBeanList must be init");
        if (mBeanList.size() > 0) mBeanList.clear();

        List<DoctorPatientRelationshipBean> datas = priorityDataArrangement(list);

        mBeanList.addAll(datas);
        adapter.notifyDataSetChanged();
    }


    private List<DoctorPatientRelationshipBean> priorityDataArrangement(List<DoctorPatientRelationshipBean> list) {
        ArrayMap<String, DoctorPatientRelationshipBean> arrayMap = new ArrayMap<>();
        for (DoctorPatientRelationshipBean bean : list) {
            //当前的bean
            PatientDisplayBean patientDisplayBean = bean.getPatient();
            String kauriHealthId = patientDisplayBean.getKauriHealthId();
            String beanRelationshipReason = bean.getRelationshipReason();
            String key = kauriHealthId + beanRelationshipReason;

            if (arrayMap.containsKey(key)) {
                //有同一个人时
                DoctorPatientRelationshipBean mapBean = arrayMap.get(key);
                Date beanEndDate = bean.getEndDate();
                Date mapBeanEndDate = mapBean.getEndDate();
                if (beanEndDate.getTime() > mapBeanEndDate.getTime()) {
                    arrayMap.put(key, bean);
                }
            } else {
                arrayMap.put(key, bean);
            }
        }

        List<DoctorPatientRelationshipBean> newList = new ArrayList<>();
        for (int i = 0; i < arrayMap.size(); i++) {
            newList.add(arrayMap.valueAt(i));
        }

        //"doctorPatientId"排序
        //        sortData(newList);
        Collections.sort(list, new Comparator<DoctorPatientRelationshipBean>() {
            @Override
            public int compare(DoctorPatientRelationshipBean bean1, DoctorPatientRelationshipBean bean2) {
                return DateUtils.isActive(bean1.isIsActive(), bean1.getEndDate())
                        && !DateUtils.isActive(bean2.isIsActive(), bean2.getEndDate()) ? -1 : 1;
            }
        });

        return newList;
    }

    private void sortData(List<DoctorPatientRelationshipBean> list) {
        Collections.sort(list, new Comparator<DoctorPatientRelationshipBean>() {
            @Override
            public int compare(DoctorPatientRelationshipBean bean1, DoctorPatientRelationshipBean bean2) {
                return bean1.getDoctorPatientId() > bean2.getDoctorPatientId() ? -1 : 1;
            }
        });
    }

    /**
     * ----------------------------订阅事件----------------------------------
     *
     * @Subscribe 下的方法必须为public
     * postSticky()发送的粘性消息订阅时必须@Subscribe(sticky = true)否则接收不到
     * 发送的event事件是object类
     * @Subscribe(priority = 1) 使用时优先级默认为0，然后只有统一模式下设置优先级才有效果，自己看着合理使用
     **/

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void eventBusMain(JumpEvent event) {
        switchPageUI(event.getMessage());
    }

}
