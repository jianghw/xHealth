package com.kaurihealth.kaurihealth.patient_v.long_monitoring;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kaurihealth.datalib.request_bean.builder.LongTermMonitoringDisplayBeanBuilder;
import com.kaurihealth.datalib.response_bean.LongTermMonitoringDisplayBean;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.kaurihealth.eventbus.CompileProgramBeanEvent;
import com.kaurihealth.kaurihealth.eventbus.LongHStandardCompileEvent;
import com.kaurihealth.kaurihealth.tinker.SampleApplicationLike;
import com.kaurihealth.mvplib.patient_p.CompileProgramPresenter;
import com.kaurihealth.mvplib.patient_p.ICompileProgramView;
import com.kaurihealth.utilslib.constant.Global;
import com.kaurihealth.utilslib.date.DateUtils;
import com.kaurihealth.utilslib.log.LogUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 描述：编辑监测指标
 * 修订日期:
 */
public class CompileProgramActivity extends BaseActivity implements ICompileProgramView {

    @Inject
    CompileProgramPresenter<ICompileProgramView> mPresenter;

    @Bind(R.id.tv_more)
    TextView mTvMore;
    @Bind(R.id.edit_first)
    EditText mEditFirst;
    @Bind(R.id.tv_first_unit)
    TextView mTvFirstUnit;
    @Bind(R.id.tv_date_1)
    TextView mTvDate1;
    @Bind(R.id.edit_second)
    EditText mEditSecond;
    @Bind(R.id.tv_second_unit)
    TextView mTvSecondUnit;
    @Bind(R.id.tv_date_2)
    TextView mTvDate2;
    @Bind(R.id.lay_second)
    LinearLayout mLaySecond;

    private LongTermMonitoringDisplayBean mBean;


    @Override
    protected int getActivityLayoutID() {
        return R.layout.activity_compile_program;
    }

    @Override
    protected void initPresenterAndView(Bundle savedInstanceState) {
        SampleApplicationLike.getApp().getComponent().inject(this);
        mPresenter.setPresenter(this);
    }

    @Override
    protected void initDelayedData() {
        mTvMore.setText(getString(R.string.title_save));
        //注册事件
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.unSubscribe();
        //注销事件
        EventBus.getDefault().unregister(this);
    }

    @OnClick(R.id.tv_more)
    public void saveOnClick() {//保存
        mPresenter.onSubscribe();
    }

    private void setProjectSituation(String program) {
        mLaySecond.setVisibility(program.equals("血压") ? View.VISIBLE : View.GONE);
    }

    /**
     * ----------------------------订阅事件----------------------------------
     **/
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void eventBusMain(CompileProgramBeanEvent event) {
        LongTermMonitoringDisplayBean bean = event.getBean();
        LogUtils.jsonDate(bean);
        if (bean == null) return;

        mBean = bean;
        String title = bean.getType();
        initNewBackBtn(title);

        setProjectSituation(title);

        mTvFirstUnit.setText(mBean.getUnit());
        mTvDate1.setText(DateUtils.getFormatDate(mBean.getDate()));
        String mBeanMeasurement = mBean.getMeasurement();
        if (mBeanMeasurement.contains("--")) {
            String[] strings = mBeanMeasurement.split("--");
            mEditFirst.setText(strings[0]);
            mEditSecond.setText(strings[1]);
        } else {
            mEditFirst.setText(mBeanMeasurement);
        }

        if (title.equals(Global.Environment.BLOOD)) {
            mTvSecondUnit.setText(mBean.getUnit());
            mTvDate2.setText(DateUtils.getFormatDate(mBean.getDate()));
        }
    }

    @Override
    public void switchPageUI(String className) {
    }

    public String getDataFirst() {
        return mEditFirst.getText().toString().trim();
    }

    public String getDataSecond() {
        return mEditSecond.getText().toString().trim();
    }

    @Override
    public List<LongTermMonitoringDisplayBean> getNewLongtermMonitoringBean() {
        List<LongTermMonitoringDisplayBean> list = new ArrayList<>();

        if (mBean.getType().equals(Global.Environment.BLOOD)) {
            String[] measuremens = mBean.getMeasurement().split("--");
            LongTermMonitoringDisplayBean firstBean = new LongTermMonitoringDisplayBeanBuilder().Builder(
                    mBean.getLongTermMonitoringId(), mBean.getPatientId(), Global.Environment.BLOOD_SHRINKAGE, mBean.getDate(), getDataFirst(), mBean.getUnit());
            list.add(firstBean);
            LongTermMonitoringDisplayBean secondBean = new LongTermMonitoringDisplayBeanBuilder().Builder(
                    Integer.valueOf(measuremens[2]), mBean.getPatientId(), Global.Environment.BLOOD_DIASTOLIC, mBean.getDate(), getDataSecond(), mBean.getUnit());
            list.add(secondBean);
        } else {
            LongTermMonitoringDisplayBean firstBean = new LongTermMonitoringDisplayBeanBuilder().Builder(
                    mBean.getLongTermMonitoringId(), mBean.getPatientId(), mBean.getType(), mBean.getDate(), getDataFirst(), mBean.getUnit());
            list.add(firstBean);
        }
        return list;
    }

    @Override
    public void onCompileSucceed() {
        EventBus.getDefault().postSticky(new LongHStandardCompileEvent("compile ok"));
        finishCur();
    }
}
