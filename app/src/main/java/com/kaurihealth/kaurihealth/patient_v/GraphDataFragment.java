package com.kaurihealth.kaurihealth.patient_v;

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
import com.kaurihealth.kaurihealth.eventbus.CompileProgramBeanEvent;
import com.kaurihealth.kaurihealth.eventbus.ChartsFgtStrEvent;
import com.kaurihealth.kaurihealth.eventbus.GraphDataFgtDeleteEvent;
import com.kaurihealth.utilslib.constant.Global;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by jianghw on 2016/8/31.
 * <p/>
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
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }

    /**
     * 订阅
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void eventBusMain(ChartsFgtStrEvent event) {
        String message = event.getMessage();
        mTvProgram.setText(message);
        drawIndexDataCharts(message);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void eventBusMain(GraphDataFgtDeleteEvent event) {
        String message = event.getMessage();
        LongTermMonitoringDisplayBean bean = event.getBean();
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
        EventBus.getDefault().postSticky(new CompileProgramBeanEvent(bean));
    }

    /**
     * 删除 itme
     * @param bean
     */
    private void onDeleteProgram(LongTermMonitoringDisplayBean bean) {

    }

    public Map<String, List<LongTermMonitoringDisplayBean>> getArrayMap() {
        LongHealthyStandardActivity activity = (LongHealthyStandardActivity) this.getActivity();
        return activity.getArrayMap();
    }

    /**
     * 数据显示
     *
     * @param message
     */
    private void drawIndexDataCharts(String message) {
        Map<String, List<LongTermMonitoringDisplayBean>> mArrayMap = getArrayMap();
        if (mArrayMap == null || mArrayMap.isEmpty())
            throw new IllegalStateException("mArrayMap must be init");

        //compare（a,b）方法:根据第一个参数小于、等于或大于第二个参数分别返回负整数、零或正整数。
        //降序列
        for (Map.Entry<String, List<LongTermMonitoringDisplayBean>> entry : mArrayMap.entrySet()) {
            List<LongTermMonitoringDisplayBean> listBean = entry.getValue();
            Collections.sort(listBean, new Comparator<LongTermMonitoringDisplayBean>() {
                @Override
                public int compare(LongTermMonitoringDisplayBean bean1, LongTermMonitoringDisplayBean bean2) {
                    return bean1.getDate().getTime() - bean2.getDate().getTime() < 0 ? 1 : bean1.getDate().getTime() - bean2.getDate().getTime() > 0 ? -1 : 0;
                }
            });
        }

        //血压
        if (message.equals(Global.Environment.BLOOD)
                && mArrayMap.containsKey(Global.Environment.BLOOD_DIASTOLIC)
                && mArrayMap.containsKey(Global.Environment.BLOOD_SHRINKAGE)) {
            List<LongTermMonitoringDisplayBean> mShrinkage = mArrayMap.get(Global.Environment.BLOOD_SHRINKAGE);
            List<LongTermMonitoringDisplayBean> mDiastolic = mArrayMap.get(Global.Environment.BLOOD_DIASTOLIC);

            if (!list.isEmpty()) list.clear();
            for (int i = 0; i < mShrinkage.size(); i++) {
                LongTermMonitoringDisplayBean beanShrinkage = mShrinkage.get(i);
                LongTermMonitoringDisplayBean beanDiastolic = mDiastolic.get(i);
                String s = beanShrinkage.getMeasurement();
                String d = beanDiastolic.getMeasurement();
                //TODO 造bean 注意啊~~
                LongTermMonitoringDisplayBean newBean = new LongTermMonitoringDisplayBean();
                newBean.setMeasurement(s + "--" + d + "--" + beanDiastolic.getLongTermMonitoringId());
                newBean.setDate(beanShrinkage.getDate());
                newBean.setLongTermMonitoringId(beanShrinkage.getLongTermMonitoringId());
                newBean.setPatientId(beanShrinkage.getPatientId());
                newBean.setType(Global.Environment.BLOOD);//血压
                newBean.setUnit(beanShrinkage.getUnit());
                list.add(newBean);
            }
            mAdapter.notifyDataSetChanged();
        } else if (mArrayMap.containsKey(message)) {
            List<LongTermMonitoringDisplayBean> mList = mArrayMap.get(message);
            if (!list.isEmpty()) list.clear();
            list.addAll(mList);
            mAdapter.notifyDataSetChanged();
        }
    }
}
