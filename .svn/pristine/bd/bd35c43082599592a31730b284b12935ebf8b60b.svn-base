package com.kaurihealth.kaurihealth.patient_v.long_monitoring;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.kaurihealth.datalib.response_bean.LongTermMonitoringDisplayBean;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.adapter.ProgramSwipeAdapter;
import com.kaurihealth.kaurihealth.eventbus.ChartsFgtStrEvent;
import com.kaurihealth.kaurihealth.eventbus.CompileProgramBeanEvent;
import com.kaurihealth.kaurihealth.eventbus.GraphDataFgtDeleteEvent;
import com.kaurihealth.utilslib.constant.Global;
import com.kaurihealth.utilslib.log.LogUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;

/**
 * Created by jianghw on 2016/8/31.
 * 描述: 患者信息--> 长期监测指标 --> 图/表列
 * <p>
 * 描述：
 */
public class GraphDataFragment extends Fragment {
    @Bind(R.id.tv_program)
    TextView mTvProgram;
    @Bind(R.id.lv_project)
    ListView mLvProject;
    private ProgramSwipeAdapter mAdapter;
    private List<LongTermMonitoringDisplayBean> list = new ArrayList<>();

    public static Fragment newInstance() {
        return new GraphDataFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_graph_data, container, false);
        ButterKnife.bind(this, view);
        mAdapter = new ProgramSwipeAdapter(getContext(), list);
        mLvProject.setAdapter(mAdapter);
        EventBus.getDefault().register(this);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        removeStickyEvent(ChartsFgtStrEvent.class);
        removeStickyEvent(GraphDataFgtDeleteEvent.class);
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }

    protected <T> void removeStickyEvent(Class<T> eventType) {
        T stickyEvent = EventBus.getDefault().getStickyEvent(eventType);
        if (stickyEvent != null) {
            EventBus.getDefault().removeStickyEvent((T) stickyEvent);
        }
    }

    /**
     * 订阅 用于显示的数据
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void eventBusMain(ChartsFgtStrEvent event) {
        List<List<LongTermMonitoringDisplayBean>> lists = event.getListsMessage();
        drawIndexDataCharts(lists);
    }

    /**
     * 编辑
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void eventBusMain(GraphDataFgtDeleteEvent event) {
        String message = event.getMessage();
        LongTermMonitoringDisplayBean bean = event.getBean();
        LogUtils.jsonDate(bean);
        if (message.equals(Global.Environment.COMPILE)) {
            onCompileProgram(bean);
        } else if (message.equals(Global.Environment.DELETE)) {
            onDeleteProgram(bean);
        }
    }

    /**
     * LongTermMonitoringDisplayBean 血压有变化
     *
     * @param bean
     */
    private void onCompileProgram(LongTermMonitoringDisplayBean bean) {
        LongHealthyStandardActivity activity = (LongHealthyStandardActivity) getActivity();
        activity.onCompileProgram();
        EventBus.getDefault().postSticky(new CompileProgramBeanEvent(bean));
    }

    /**
     * 删除 itme
     *
     * @param bean
     */
    private void onDeleteProgram(LongTermMonitoringDisplayBean bean) {
        LongHealthyStandardActivity activity = (LongHealthyStandardActivity) getActivity();
        activity.onDeleteProgram(bean);
    }

    /**
     * 数据显示
     * 1、嵌套list中各item list降序
     * 2、判断 :当size==1
     * 3、加载血压数据 特殊
     */
    private void drawIndexDataCharts(List<List<LongTermMonitoringDisplayBean>> lists) {
        if (lists == null || lists.isEmpty() || lists.size() == 0 || lists.size() == 1 && lists.get(0).isEmpty()) {
            if (!list.isEmpty()) list.clear();
            mAdapter.notifyDataSetChanged();
            return;
        }
        if(lists.size() == 2 && lists.get(0).isEmpty()&&lists.get(1).isEmpty()){
            if (!list.isEmpty()) list.clear();
            mAdapter.notifyDataSetChanged();
            return;
        }
        Observable.from(lists)
                .map(beanList -> {
                    Collections.sort(beanList, (o1, o2) -> {
                        long time = o1.getDate().getTime() - o2.getDate().getTime();
                        return time < 0 ? 1 : time > 0 ? -1 : 0;
                    });
                    return beanList;
                })
                .toList()
                .subscribe(lists1 -> {
                    if (lists1.size() == 1) {
                        List<LongTermMonitoringDisplayBean> mList = lists1.get(0);
                        mTvProgram.setText(mList.get(0).getType());
                        if (!list.isEmpty()) list.clear();
                        list.addAll(mList);
                        mAdapter.notifyDataSetChanged();
                    } else {
                        List<LongTermMonitoringDisplayBean> mShrinkage = new ArrayList<>();
                        List<LongTermMonitoringDisplayBean> mDiastolic = new ArrayList<>();
                        addBloodData(lists1, mShrinkage);
                        addBloodData2(lists1, mDiastolic);

                        mTvProgram.setText(Global.Environment.BLOOD);
                        Observable
                                .zip(Observable.from(mShrinkage), Observable.from(mDiastolic), (beanShrinkage, beanDiastolic) -> {
                                    String shrinkageM = beanShrinkage.getMeasurement();
                                    String diastorlicM = beanDiastolic.getMeasurement();
                                    LongTermMonitoringDisplayBean newBean = new LongTermMonitoringDisplayBean();
                                    newBean.setMeasurement(shrinkageM + "--" + diastorlicM + "--" + beanDiastolic.getLongTermMonitoringId());
                                    newBean.setDate(beanShrinkage.getDate());
                                    newBean.setLongTermMonitoringId(beanShrinkage.getLongTermMonitoringId());
                                    newBean.setPatientId(beanShrinkage.getPatientId());
                                    newBean.setType(Global.Environment.BLOOD);//血压
                                    newBean.setUnit(beanShrinkage.getUnit());
                                    return newBean;
                                })
                                .toSortedList((o1, o2) -> {
                                    long time = o1.getDate().getTime() - o2.getDate().getTime();
                                    return time < 0 ? 1 : time > 0 ? -1 : 0;
                                })
                                .subscribe(longTermMonitoringDisplayBeen -> {
                                    if (!list.isEmpty()) list.clear();
                                    list.addAll(longTermMonitoringDisplayBeen);
                                    mAdapter.notifyDataSetChanged();
                                });
                    }
                });

    }

    private void addBloodData(List<List<LongTermMonitoringDisplayBean>> lists, List<LongTermMonitoringDisplayBean> mShrinkage) {
        Observable.from(lists)
                .filter(longTermMonitoringDisplayBeen -> longTermMonitoringDisplayBeen.get(0).getType().equals(Global.Environment.BLOOD_SHRINKAGE))
                .subscribe(longTermMonitoringDisplayBeen -> {
                    if (!mShrinkage.isEmpty()) mShrinkage.clear();
                    mShrinkage.addAll(longTermMonitoringDisplayBeen);
                });
    }

    private void addBloodData2(List<List<LongTermMonitoringDisplayBean>> lists, List<LongTermMonitoringDisplayBean> mShrinkage) {
        Observable.from(lists)
                .filter(longTermMonitoringDisplayBeen -> longTermMonitoringDisplayBeen.get(0).getType().equals(Global.Environment.BLOOD_DIASTOLIC))
                .subscribe(longTermMonitoringDisplayBeen -> {
                    if (!mShrinkage.isEmpty()) mShrinkage.clear();
                    mShrinkage.addAll(longTermMonitoringDisplayBeen);
                });
    }


}
