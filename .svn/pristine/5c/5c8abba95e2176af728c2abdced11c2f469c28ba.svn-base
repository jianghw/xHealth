package com.kaurihealth.kaurihealth.patient_v.long_monitoring;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kaurihealth.datalib.request_bean.bean.NewLongtermMonitoringBean;
import com.kaurihealth.datalib.request_bean.builder.NewLongtermMonitoringBeanBuilder;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.kaurihealth.eventbus.AddMIndexBeanEvent;
import com.kaurihealth.kaurihealth.eventbus.LongHStandardCompileEvent;
import com.kaurihealth.kaurihealth.tinker.SampleApplicationLike;
import com.kaurihealth.mvplib.patient_p.AddMonitorIndexPresenter;
import com.kaurihealth.mvplib.patient_p.IAddMonitorIndexView;
import com.kaurihealth.utilslib.OnClickUtils;
import com.kaurihealth.utilslib.ValidatorUtils;
import com.kaurihealth.utilslib.constant.Global;
import com.kaurihealth.utilslib.date.DateUtils;
import com.kaurihealth.utilslib.dialog.DialogUtils;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Pattern;

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
public class AddMonitorIndexActivity extends BaseActivity implements IAddMonitorIndexView, Validator.ValidationListener {
    @Inject
    AddMonitorIndexPresenter<IAddMonitorIndexView> mPresenter;

    @Bind(R.id.tv_more)
    TextView mTvMore;
    @Bind(R.id.tv_time)
    TextView tvTime;
    @Bind(R.id.tv_program)
    TextView tvProgram;

    @NotEmpty(message = "数据项不能为空")
    @Pattern(regex = "^\\d*\\.?\\d{1,4}$$", message = "最多保留小数点后四位")
    @Bind(R.id.edit_first)
    EditText mEditFirst;

    @Bind(R.id.tv_first_unit)
    TextView mTvFirstUnit;

    @NotEmpty(message = "数据项不能为空")
    @Pattern(regex = "^\\d*\\.?\\d{1,4}$", message = "最多保留小数点后四位")
    @Bind(R.id.edit_second)
    EditText mEditSecond;
    @Bind(R.id.tv_second_unit)
    TextView mTvSecondUnit;
    @Bind(R.id.lay_second)
    LinearLayout mLaySecond;

    private int mPatientID;
    private Validator mValidator;

    @Override
    protected int getActivityLayoutID() {
        return R.layout.activity_add_monitor;
    }

    @Override
    protected void initPresenterAndView(Bundle savedInstanceState) {
        SampleApplicationLike.getApp().getComponent().inject(this);
        mPresenter.setPresenter(this);
    }

    @Override
    protected void initDelayedData() {
        initNewBackBtn(getString(R.string.long_standard_title));
        mTvMore.setText(getString(R.string.title_save));
        mValidator = new Validator(this);
        mValidator.setValidationListener(this);
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

    @Override
    public void onValidationSucceeded() {
        mPresenter.onSubscribe();//保存
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        String message = ValidatorUtils.validationErrorMessage(getApplicationContext(), errors);
        displayErrorDialog(message);
    }

    @OnClick(R.id.tv_more)
    public void saveOnClick() {//表单验证开启
        if (OnClickUtils.onNoDoubleClick()) return;
        if (getTvTime().contains("时间")) {
            displayErrorDialog("必须选择时间");
        } else if (getTvProgram().contains("项目")) {
            displayErrorDialog("必须选择项目");
        } else {
            mValidator.validate();
        }
    }

    @OnClick(R.id.tv_time)
    public void timeOnClick() {//时间
        DialogUtils.showDateDialog(this, (year, month, day) -> {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(year);
            stringBuilder.append("-");
            stringBuilder.append(String.format("%02d", Integer.valueOf(month)));
            stringBuilder.append("-");
            stringBuilder.append(String.format("%02d", Integer.valueOf(day)));
            String sb = stringBuilder.toString();
            tvTime.setText(sb);
        });
    }

    @OnClick(R.id.tv_program)
    public void programOnClick() {//项目
        String[] programs = getResources().getStringArray(R.array.array_monitor_project);
        String[] units = getResources().getStringArray(R.array.array_monitor_unit);

        DialogUtils.showStringDialog(this, programs, index -> {
            tvProgram.setText(programs[index]);
            mTvFirstUnit.setText(units[index]);
            setProjectSituation(programs[index]);
        });
    }

    /**
     * 血压特例
     */
    private void setProjectSituation(String program) {
        mLaySecond.setVisibility(program.equals("血压") ? View.VISIBLE : View.GONE);
        if (program.equals("血压")) {
            mEditFirst.setHint("请填写收缩压");
            mEditSecond.setHint("请填写舒张压");
        } else {
            mEditFirst.setHint("请填写检测数据");
        }
        if (program.equals("血压")) mTvSecondUnit.setText(mTvFirstUnit.getText());
    }

    /**
     * ----------------------------订阅事件----------------------------------
     **/
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void eventBusMain(AddMIndexBeanEvent event) {
        mPatientID = event.getPatientId();
    }

    @Override
    public int getPatientID() {
        return mPatientID;
    }

    @Override
    public void addDataSucceed() {
        EventBus.getDefault().postSticky(new LongHStandardCompileEvent("add succeed ok"));
        switchPageUI(Global.Jump.LongHealthyStandardActivity);
    }

    @Override
    public void switchPageUI(String className) {
        finishCur();
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

    public String getTvTime() {
        return tvTime.getText().toString().trim();
    }

    public String getTvProgram() {
        return tvProgram.getText().toString().trim();
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

}
