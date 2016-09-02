package com.kaurihealth.kaurihealth.open_an_account_v;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.kaurihealth.utilslib.dialog.PopUpNumberPickerDialog;
import com.kaurihealth.utilslib.dialog.SelectDateDialog;
import com.kaurihealth.datalib.request_bean.bean.NewRegistByDoctorBean;
import com.kaurihealth.datalib.request_bean.builder.NewRegistByDoctorBeanBuilder;
import com.kaurihealth.kaurihealth.MyApplication;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.kaurihealth.util.TextWatchClearError;
import com.kaurihealth.mvplib.open_an_account_p.IOpenAnAccountView;
import com.kaurihealth.mvplib.open_an_account_p.OpenAnAccountPresenter;
import com.kaurihealth.utilslib.ValidatorUtils;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Pattern;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Garnet_Wu on 2016/8/22.
 */
public class OpenAnAccountActivity extends BaseActivity implements IOpenAnAccountView, Validator.ValidationListener {

    //电话
    @Bind(R.id.tvPhone)
    @NotEmpty(sequence = 1, message = "电话号码不能为空")
    @Pattern(sequence = 2, regex = "^1[3|4|5|7|8]\\d{9}$", message = "请输入正确的电话号码")
    EditText tvPhone;

    //姓
    @NotEmpty(message = "姓不能为空")
    @Length(max = 5)
    @Bind(R.id.tvFirstName)
    EditText tvFirstName;

    //名
    @NotEmpty(message = "名不能为空")
    @Length(max = 5)
    @Bind(R.id.tvLastName)
    EditText tvLastName;

    //性别
    @NotEmpty(message = "性别不能为空")
    @Bind(R.id.tv_gender)
    EditText tvGendar;

    //生日
    @NotEmpty(message = "生日不能为空")
    @Bind(R.id.tvBirthday)
    EditText tvBirthday;

    //注册完成
    @Bind(R.id.tv_operate)
    TextView tvOperate;

    //删除，清空
    @Bind(R.id.ivDelete1)
    ImageView ivDelete1;

    @Bind(R.id.ivDelete2)
    ImageView ivDelete2;

    @Bind(R.id.ivDelete3)
    ImageView ivDelete3;

    @Bind(R.id.ivDelete4)
    ImageView ivDelete4;

    @Bind(R.id.ivDelete5)
    ImageView ivDelete5;


    @Inject
    OpenAnAccountPresenter<IOpenAnAccountView> mOpenAnAccountPresenter;
    private Validator validator;
    private Dialog gendarDialog;
    private Dialog dateDialog;


    //BaseActiivty
    @Override
    protected int getActivityLayoutID() {
        return R.layout.activity_open_an_account;
    }

    //BaseActivity
    @Override
    protected void initPresenterAndData(Bundle savedInstanceState) {
        MyApplication.getApp().getComponent().inject(this);
        mOpenAnAccountPresenter.setPresenter(this);
        //回退键,结束当前Activity
        initBackBtn(R.id.iv_back);
        final String[] gendars = {"男", "女"};
        PopUpNumberPickerDialog gendarDialogPopUp = new PopUpNumberPickerDialog(this, gendars, new PopUpNumberPickerDialog.SetClickListener() {
            @Override
            public void onClick(int index) {
                tvGendar.setText(gendars[index]);
            }
        });
        gendarDialog = gendarDialogPopUp.getDialog();
        SelectDateDialog selectDateDialog = new SelectDateDialog(this, new SelectDateDialog.DialogListener() {
            @Override
            public void onclick(String year, String month, String day) {
                setDateOfBirth(year, month, day);
            }
        });
        dateDialog = selectDateDialog.getDataDialog();
        //给删除按钮设置监听
        tvPhone.addTextChangedListener(new TextWatchClearError(tvPhone, ivDelete1));
        tvFirstName.addTextChangedListener(new TextWatchClearError(tvFirstName, ivDelete2));
        tvLastName.addTextChangedListener(new TextWatchClearError(tvLastName, ivDelete5));
        tvGendar.addTextChangedListener(new TextWatchClearError(tvGendar, ivDelete3));
        tvBirthday.addTextChangedListener(new TextWatchClearError(tvBirthday, ivDelete4));

    }

    //BaseActivity  -- 初始化表达验证器
    @Override
    protected void initDelayedView() {
        validator = new Validator(this);
        validator.setValidationListener(this);
    }

    //IOpenAnAccountView 得到医生为患者快速注册的bean
    @Override
    public NewRegistByDoctorBean getNewRegistByDoctorBean() {
        //获取textView里面的数据
        String firstName = getTvValue(tvLastName);
        String lastName = getTvValue(tvFirstName);
        String userName = getTvValue(tvPhone);
        String gender = getTvValue(tvGendar);
        String dayOfBirth = getTvValue(tvBirthday);
        NewRegistByDoctorBean newRegistByDoctorBean = new NewRegistByDoctorBeanBuilder().Build(userName, firstName, lastName, gender, dayOfBirth);
        return newRegistByDoctorBean;
    }


    //IMvpView   跳转页面
    @Override
    public void switchPageUI(String className) {
        //开户成功后，跳转至医患界面
        //skipTo();
    }

    //Validator 重写
    @Override
    public void onValidationSucceeded() {
        //请求数据
        mOpenAnAccountPresenter.onSubscribe();  //Obeservable+Retrofit
    }


    //Validator重写
    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        //弹出sweetDialog对话框
        String message = ValidatorUtils.validationErrorMessage(getApplicationContext(), errors);
        displayErrorDialog(message);
    }


    /**
     * 设置生日
     */
    private void setDateOfBirth(String year, String month, String day) {
        StringBuilder stringBuilder = new StringBuilder(year);
        stringBuilder.append("-");
        stringBuilder.append(month);
        stringBuilder.append("-");
        stringBuilder.append(day);
        tvBirthday.setText(stringBuilder.toString());
        stringBuilder.append("T00:00:00");
    }

    //点击事件
    @OnClick({R.id.ivDelete1, R.id.ivDelete2, R.id.ivDelete3, R.id.ivDelete4, R.id.ivDelete5, R.id.rlay_gendar, R.id.rlay_birthday, R.id.tv_operate, R.id.tvBirthday, R.id.tv_gender})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivDelete1:
                clearTextView(tvPhone);
                break;
            case R.id.ivDelete2:
                clearTextView(tvFirstName);
                break;
            case R.id.ivDelete3:
                clearTextView(tvGendar);
                break;
            case R.id.ivDelete4:
                clearTextView(tvBirthday);
                break;
            case R.id.ivDelete5:
                clearTextView(tvLastName);
                break;
            case R.id.rlay_gendar:   //性别条目相对布局
                gendarDialog.show();
                break;
            case R.id.rlay_birthday:  //生日条目相对布局
                dateDialog.show();
                break;
            case R.id.tvBirthday:
                dateDialog.show();
                break;
            case R.id.tv_gender:
                gendarDialog.show();
                break;
            case R.id.tv_operate:           //注册完成按钮
                validator.validate();   //调重写方法
                break;
        }
    }

}
