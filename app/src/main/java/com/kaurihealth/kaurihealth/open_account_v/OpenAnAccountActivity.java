package com.kaurihealth.kaurihealth.open_account_v;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.kaurihealth.datalib.request_bean.bean.NewRegistByDoctorBean;
import com.kaurihealth.datalib.request_bean.builder.NewRegistByDoctorBeanBuilder;
import com.kaurihealth.datalib.response_bean.RegisterResponse;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.kaurihealth.eventbus.OpenAnAccountToAllMedicalRecordFragmentEvent;
import com.kaurihealth.kaurihealth.tinker.SampleApplicationLike;
import com.kaurihealth.kaurihealth.util.TextListener;
import com.kaurihealth.mvplib.open_an_account_p.IOpenAnAccountView;
import com.kaurihealth.mvplib.open_an_account_p.OpenAnAccountPresenter;
import com.kaurihealth.utilslib.ValidatorUtils;
import com.kaurihealth.utilslib.dialog.DialogUtils;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Pattern;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by xx on 2016/8/22.
 */
public class OpenAnAccountActivity extends BaseActivity implements IOpenAnAccountView, Validator.ValidationListener {
    @Inject
    OpenAnAccountPresenter<IOpenAnAccountView> mOpenAnAccountPresenter;

    //电话
    @Bind(R.id.tvPhone)
    @NotEmpty(sequence = 1, message = "电话号码不能为空")
    @Pattern(sequence = 2, regex = "^1[3|4|5|7|8]\\d{9}$", message = "请输入正确的电话号码")
    EditText tvPhone;

    //姓
    @NotEmpty(message = "姓不能为空")
    @Length(max = 5,message = "姓不能为超过5位")
    @Bind(R.id.tvFirstName)
    EditText tvFirstName;

    //名
    @NotEmpty(message = "名不能为空")
    @Length(max = 5,message = "名不能为超过5位")
    @Bind(R.id.tvLastName)
    EditText tvLastName;

    //生日
    @NotEmpty(message = "生日不能为空")
    @Bind(R.id.tvBirthday)
    TextView tvBirthday;

    //注册完成
    @Bind(R.id.tv_operate)
    TextView tvOperate;
    //男
    @Bind(R.id.radio_male)
    RadioButton radio_male;
    //女
    @Bind(R.id.radio_female)
    RadioButton radio_female;

    @Bind(R.id.id_radiogroup)
    RadioGroup id_radiogroup;

    @Bind(R.id.tv_open_accuont_complete)
    TextView tv_open_accuont_complete;

    @Bind(R.id.tv_01)
    TextView tv_01;
    @Bind(R.id.tv_02)
    TextView tv_02;
    @Bind(R.id.tv_03)
    TextView tv_03;
    @Bind(R.id.tv_04)
    TextView tv_04;
    @Bind(R.id.tv_05)
    TextView tv_05;

    private Validator validator;

    @Override
    protected int getActivityLayoutID() {
        return R.layout.activity_open_an_account;
    }

    @Override
    protected void initPresenterAndView(Bundle savedInstanceState) {
        SampleApplicationLike.getApp().getComponent().inject(this);
        mOpenAnAccountPresenter.setPresenter(this);
        initBackBtn(R.id.iv_back);

        tvPhone.addTextChangedListener(new TextListener(tvPhone, tv_01, R.mipmap.phone_icon, R.mipmap.phone_icon_on, this));
        tvFirstName.addTextChangedListener(new TextListener(tvFirstName, tv_02, R.mipmap.name_icon, R.mipmap.name_icon_on, this));
        tvLastName.addTextChangedListener(new TextListener(tvLastName, tv_03, R.mipmap.name_icon, R.mipmap.name_icon_on, this));

        id_radiogroup.setOnCheckedChangeListener((radioGroup, i) -> {
            setComplete();
            tv_04.setBackgroundResource(R.color.color_enable_green);
        });

        tvBirthday.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (tvBirthday.getText().toString().length() > 0) {
                    Drawable drawableLeft = getResources().getDrawable(R.mipmap.date_on);
                    drawableLeft.setBounds(0, 0, drawableLeft.getMinimumWidth(), drawableLeft.getMinimumHeight());
                    tvBirthday.setCompoundDrawables(drawableLeft, null, null, null);

                    tv_05.setBackgroundResource(R.color.color_enable_green);

                    setComplete();
                } else {
                    Drawable drawableLeft = getResources().getDrawable(R.mipmap.date_icon);
                    drawableLeft.setBounds(0, 0, drawableLeft.getMinimumWidth(), drawableLeft.getMinimumHeight());
                    tvBirthday.setCompoundDrawables(drawableLeft, null, null, null);

                    tv_05.setBackgroundResource(R.color.color_text_gray);
                    tv_open_accuont_complete.setBackgroundResource(R.drawable.btn_gray);
                }
            }
        });
    }

    @Override
    protected void initDelayedData() {
        validator = new Validator(this);
        validator.setValidationListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mOpenAnAccountPresenter.unSubscribe();
    }

    /**
     * 判断是够都已经填完
     */
    private boolean judge() {
        String firstName = getTvValue(tvLastName);
        String lastName = getTvValue(tvFirstName);
        String userName = getTvValue(tvPhone);
        String gender = radio_female.isChecked() ? "女" : radio_male.isChecked() ? "男" : null;
        String dayOfBirth = getTvValue(tvBirthday);
        if (!(firstName.equals(""))
                && !(lastName.equals(""))
                && !(userName.equals(""))
                && gender != null
                && !(dayOfBirth.equals(""))) {
                return true;
        }
        return false;
    }

    //完成按钮设置
    public void setComplete(){
        if (judge()){
            tv_open_accuont_complete.setEnabled(true);
            tv_open_accuont_complete.setBackgroundResource(R.drawable.btn_green);
        }else {
            tv_open_accuont_complete.setEnabled(false);
            tv_open_accuont_complete.setBackgroundResource(R.drawable.btn_gray);
        }
    }

    //医生为患者快速注册的bean
    @Override
    public NewRegistByDoctorBean getNewRegistByDoctorBean() {
        String firstName = getTvValue(tvLastName);
        String lastName = getTvValue(tvFirstName);
        String userName = getTvValue(tvPhone);
        String gender = radio_female.isChecked() ? "女" : radio_male.isChecked() ? "男" : "";
        String dayOfBirth = getTvValue(tvBirthday);
        return new NewRegistByDoctorBeanBuilder().Build(userName, firstName, lastName, gender, dayOfBirth);
    }

    /**
     * 开户成功
     *
     * @param response
     */
    @Override
    public void openAnAccountSuccess(RegisterResponse response) {
        showToast("为患者开户成功");
        tvOperate.setClickable(false);
        clearTextview(tvLastName);
        clearTextview(tvPhone);
        clearTextview(tvFirstName);
        clearTextview(tvBirthday);
        EventBus.getDefault().postSticky(new OpenAnAccountToAllMedicalRecordFragmentEvent());
        finishCur();
    }

    //IMvpView   跳转页面
    @Override
    public void switchPageUI(String className) {

    }

    @Override
    public void onValidationSucceeded() {
        mOpenAnAccountPresenter.onSubscribe();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        String message = ValidatorUtils.validationErrorMessage(getApplicationContext(), errors);
        displayErrorDialog(message);
    }

    /**
     * 设置生日
     */
    private void setDateOfBirth(String year, String month, String day) {
        StringBuilder stringBuilder = new StringBuilder(year);
        stringBuilder.append("/");
        stringBuilder.append(String.format(getResources().getString(R.string.date_month), Integer.valueOf(month)));
        stringBuilder.append("/");
        stringBuilder.append(String.format(getResources().getString(R.string.date_month), Integer.valueOf(day)));
        tvBirthday.setText(stringBuilder.toString());
        stringBuilder.append("T00:00:00");
    }

    //点击事件
    @OnClick({R.id.tv_open_accuont_complete, R.id.tvBirthday,})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvBirthday:
                DialogUtils.showDateDialog(this, this::setDateOfBirth);
                break;
            case R.id.tv_open_accuont_complete://注册完成按钮
                validator.validate();
                break;
        }
    }

}
