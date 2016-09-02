package com.kaurihealth.kaurihealth.patient_v;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.ArrayMap;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.kaurihealth.datalib.response_bean.DoctorPatientRelationshipBean;
import com.kaurihealth.datalib.response_bean.LongTermMonitoringDisplayBean;
import com.kaurihealth.kaurihealth.MyApplication;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.kaurihealth.eventbus.AddMIndexBeanEvent;
import com.kaurihealth.kaurihealth.eventbus.ChartsFgtStrEvent;
import com.kaurihealth.kaurihealth.eventbus.LongHStandardBeanEvent;
import com.kaurihealth.mvplib.patient_p.ILongHealthyStandardView;
import com.kaurihealth.mvplib.patient_p.LongHealthyStandardPresenter;
import com.kaurihealth.utilslib.constant.Global;
import com.kaurihealth.utilslib.dialog.DialogUtils;
import com.kaurihealth.utilslib.dialog.PopUpNumberPickerDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 描述：  长期监测指标
 * 修订日期:
 */
public class LongHealthyStandardActivity extends BaseActivity implements ILongHealthyStandardView {

    @Bind(R.id.tv_more)
    TextView mTvMore;

    @Bind(R.id.rbtn_chart)
    RadioButton mRbtnChart;
    @Bind(R.id.rbtn_graphData)
    RadioButton mRbtnGraphData;
    @Bind(R.id.fl_chart)
    FrameLayout mFlChart;

    @Inject
    LongHealthyStandardPresenter<ILongHealthyStandardView> mPresenter;

    private Fragment mChartsFragment;
    private Fragment mGraphDataFragment;
    private int currentPosition = -1;
    private DoctorPatientRelationshipBean mDoctorPatientRelationshipBean;
    private String[] programs;
    /**
     * 网络加载的监测项目详情bean
     */
    private Map<String, List<LongTermMonitoringDisplayBean>> mArrayMap = new ArrayMap<>();

    @Override
    protected int getActivityLayoutID() {
        return R.layout.activity_long_standard;
    }

    @Override
    protected void initPresenterAndData(Bundle savedInstanceState) {
        MyApplication.getApp().getComponent().inject(this);
        mPresenter.setPresenter(this);
    }

    @Override
    protected void initDelayedView() {
        initNewBackBtn(getString(R.string.long_standard_title));
        mTvMore.setText(getString(R.string.more_add));

        setCurrentItem(0);
        //注册事件
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (programs != null) programs = null;
        if (mDoctorPatientRelationshipBean != null) mDoctorPatientRelationshipBean = null;

        mPresenter.unSubscribe();
        //注销事件
        EventBus.getDefault().unregister(this);
    }

    /**
     * ----------------------------订阅事件----------------------------------
     **/

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void eventBusMain(LongHStandardBeanEvent event) {
        DoctorPatientRelationshipBean bean = event.getBean();
        if (bean == null) return;
        mDoctorPatientRelationshipBean = bean;

        mPresenter.onSubscribe();
    }

    @Override
    public DoctorPatientRelationshipBean getDoctorPatientRelationshipBean() {
        if (mDoctorPatientRelationshipBean == null)
            throw new IllegalStateException("mDoctorPatientRelationshipBean must be not null");
        return mDoctorPatientRelationshipBean;
    }

    public void setCurrentItem(int i) {
        if (i != currentPosition) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            hideAllFragments(fragmentTransaction);
            switch (i) {
                case 0:
                    addOrShowFragment(i, fragmentTransaction, mChartsFragment, String.valueOf(i));
                    break;
                case 1:
                    addOrShowFragment(i, fragmentTransaction, mGraphDataFragment, String.valueOf(i));
                    break;
                default:
                    break;
            }
            fragmentTransaction.commit();
            currentPosition = i;
        }
    }

    private void hideAllFragments(FragmentTransaction fragmentTransaction) {
        if (mChartsFragment != null) fragmentTransaction.hide(mChartsFragment);
        if (mGraphDataFragment != null) fragmentTransaction.hide(mGraphDataFragment);
    }

    private void addOrShowFragment(int i, FragmentTransaction fragmentTransaction, Fragment fragment, String tag) {
        if (fragment == null) {
            fragmentTransaction.add(R.id.fl_chart, getItem(i), tag);
        } else {
            fragmentTransaction.show(fragment);
        }
    }

    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                if (mChartsFragment == null)
                    mChartsFragment = ChartsFragment.newInstance();
                return mChartsFragment;
            case 1:
                if (mGraphDataFragment == null)
                    mGraphDataFragment = GraphDataFragment.newInstance();
                return mGraphDataFragment;
            default:
                return null;
        }
    }

    @OnClick({R.id.rbtn_chart, R.id.rbtn_graphData})
    public void rBtnOnClick(View view) {
        switch (view.getId()) {
            case R.id.rbtn_chart:
                if (currentPosition == 1) {
                    setCurrentItem(0);
                } else {
                    DialogUtils.showStringDialog(this, programs, new PopUpNumberPickerDialog.SetClickListener() {
                        @Override
                        public void onClick(int index) {
                            //显示所有绘图
                            mRbtnChart.setText(programs[index]);
                            EventBus.getDefault().postSticky(new ChartsFgtStrEvent(programs[index]));
                        }
                    });
                }
                break;
            case R.id.rbtn_graphData:
                setCurrentItem(1);
                break;
            default:
                break;
        }
    }

    @OnClick(R.id.tv_more)
    public void moreOnClick() {
        skipTo(AddMonitorIndexActivity.class);
        EventBus.getDefault().postSticky(new AddMIndexBeanEvent(mDoctorPatientRelationshipBean));
    }

    /**
     * TODO 先写逻辑，在改~~
     *
     * @param list
     */
    @Override
    public void drawChartsByList(List<LongTermMonitoringDisplayBean> list) {
        if (list.size() == 0) return;
        ArrayMap<String, List<LongTermMonitoringDisplayBean>> arrayMap = new ArrayMap<>();
        for (LongTermMonitoringDisplayBean bean : list) {
            String key = bean.getType();
            if (arrayMap.containsKey(key)) {
                arrayMap.get(key).add(bean);
            } else {
                ArrayList<LongTermMonitoringDisplayBean> mapList = new ArrayList<>();
                mapList.add(bean);
                arrayMap.put(key, mapList);
            }
        }

        List<String> keyList = new ArrayList<>();
        if (arrayMap.containsKey(Global.Environment.BLOOD_SHRINKAGE)
                || arrayMap.containsKey(Global.Environment.BLOOD_DIASTOLIC)) {
            for (String key : arrayMap.keySet()) {
                if (!key.equals(Global.Environment.BLOOD_SHRINKAGE)
                        && !key.equals(Global.Environment.BLOOD_DIASTOLIC)) keyList.add(key);
            }
            keyList.add(Global.Environment.BLOOD);
        } else {
            for (String key : arrayMap.keySet()) {
                keyList.add(key);
            }
        }
        programs = new String[keyList.size()];
        for (int i = 0; i < keyList.size(); i++) {
            programs[i] = keyList.get(i);
        }

        if (!mArrayMap.isEmpty()) mArrayMap.clear();
        mArrayMap.putAll(arrayMap);
    }

    public Map<String, List<LongTermMonitoringDisplayBean>> getArrayMap() {
        return Collections.synchronizedMap(mArrayMap);
    }

    @Override
    public void switchPageUI(String className) {

    }
}
