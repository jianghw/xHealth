package com.kaurihealth.kaurihealth.patient_v;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kaurihealth.datalib.request_bean.bean.NewLongtermMonitoringBean;
import com.kaurihealth.datalib.request_bean.builder.NewLongtermMonitoringBeanBuilder;
import com.kaurihealth.datalib.response_bean.DoctorPatientRelationshipBean;
import com.kaurihealth.kaurihealth.MyApplication;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.kaurihealth.eventbus.AddMIndexBeanEvent;
import com.kaurihealth.mvplib.patient_p.AddMonitorIndexPresenter;
import com.kaurihealth.mvplib.patient_p.IAddMonitorIndexView;
import com.kaurihealth.utilslib.date.DateUtils;
import com.kaurihealth.utilslib.dialog.DialogUtils;
import com.kaurihealth.utilslib.dialog.PopUpNumberPickerDialog;
import com.kaurihealth.utilslib.dialog.SelectDateDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 描述：添加监测指标
 * 修订日期:
 */
public class AddMonitorIndexActivity extends BaseActivity implements IAddMonitorIndexView {

    @Inject
    AddMonitorIndexPresenter<IAddMonitorIndexView> mPresenter;

    @Bind(R.id.tv_more)
    TextView mTvMore;
    @Bind(R.id.tv_time)
    TextView mTvTime;
    @Bind(R.id.tv_program)
    TextView mTvProgram;
    @Bind(R.id.edit_first)
    EditText mEditFirst;
    @Bind(R.id.tv_first_unit)
    TextView mTvFirstUnit;
    @Bind(R.id.edit_second)
    EditText mEditSecond;
    @Bind(R.id.tv_second_unit)
    TextView mTvSecondUnit;
    @Bind(R.id.lay_second)
    LinearLayout mLaySecond;

    private int mPatientID;


    @Override
    protected int getActivityLayoutID() {
        return R.layout.activity_add_monitor;
    }

    @Override
    protected void initPresenterAndData(Bundle savedInstanceState) {
        MyApplication.getApp().getComponent().inject(this);
        mPresenter.setPresenter(this);
    }

    @Override
    protected void initDelayedView() {
        initNewBackBtn(getString(R.string.long_standard_title));
        mTvMore.setText(getString(R.string.more_save));
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

    @OnClick(R.id.tv_time)
    public void timeOnClick() {//时间
        DialogUtils.showDateDialog(this, new SelectDateDialog.DialogListener() {
            @Override
            public void onclick(String year, String month, String day) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(year);
                stringBuilder.append("-");
                stringBuilder.append(month.startsWith("0") ? month : "0" + month);
                stringBuilder.append("-");
                stringBuilder.append(day.startsWith("0") ? month : "0" + day);
                String sb = stringBuilder.toString();
                mTvTime.setText(sb);
            }
        });
    }

    @OnClick(R.id.tv_program)
    public void programOnClick() {//项目
        final String[] programs = getResources().getStringArray(R.array.array_monitor_project);
        final String[] units = getResources().getStringArray(R.array.array_monitor_unit);

        DialogUtils.showStringDialog(this, programs, new PopUpNumberPickerDialog.SetClickListener() {
            @Override
            public void onClick(int index) {
                mTvProgram.setText(programs[index]);
                mTvFirstUnit.setText(units[index]);
                setProjectSituation(programs[index]);
            }
        });
    }

    private void setProjectSituation(String program) {
        mLaySecond.setVisibility(program.equals("血压") ? View.VISIBLE : View.GONE);
        if (program.equals("血压")) mTvSecondUnit.setText(mTvFirstUnit.getText());
    }


    /**
     * ----------------------------订阅事件----------------------------------
     **/

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void eventBusMain(AddMIndexBeanEvent event) {
        DoctorPatientRelationshipBean bean = event.getBean();
        if (bean == null) return;
        mPatientID = bean.getPatientId();
    }

    @Override
    public int getPatientID() {
        return mPatientID;
    }

    @Override
    public void switchPageUI(String className) {

    }

    public String getTvTime() {
        return mTvTime.getText().toString().trim();
    }

    public String getTvProgram() {
        return mTvProgram.getText().toString().trim();
    }

    public String getDataFirst() {
        return mEditFirst.getText().toString().trim();
    }

    public String getUnitFirst() {
        return mTvFirstUnit.getText().toString().trim();
    }

    public String getDataSecond() {
        return mEditSecond.getText().toString().trim();
    }

    public String getUnitSecond() {
        return mTvSecondUnit.getText().toString().trim();
    }

    @Override
    public List<NewLongtermMonitoringBean> getNewLongtermMonitoringBean() {
        String time = getTvTime();
        String program = getTvProgram();
        String dataFirst = getDataFirst();
        String unitFirst = getUnitFirst();
        Date date = DateUtils.getDateConversion(time);

        List<NewLongtermMonitoringBean> list = new ArrayList<>();
        if (getTvProgram().equals("血压")) program = "收缩压 ";
        NewLongtermMonitoringBean firstBean = new NewLongtermMonitoringBeanBuilder()
                .Builder(getPatientID(), program, date, dataFirst, unitFirst);
        list.add(firstBean);
        if (getTvProgram().equals("血压")) {
            String dataSecond = getDataSecond();
            String unitSecond = getUnitSecond();
            program = "舒张压 ";
            NewLongtermMonitoringBean secondBean = new NewLongtermMonitoringBeanBuilder()
                    .Builder(getPatientID(), program, date, dataSecond, unitSecond);
            list.add(secondBean);
        }
        return list;
    }
}
