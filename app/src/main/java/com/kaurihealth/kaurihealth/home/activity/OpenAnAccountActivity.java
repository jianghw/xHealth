package com.kaurihealth.kaurihealth.home.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bugtags.library.Bugtags;
import com.kaurihealth.utilslib.dialog.PopUpNumberPickerDialog;
import com.kaurihealth.utilslib.dialog.SelectDateDialog;
import com.kaurihealth.datalib.request_bean.bean.NewRegistByDoctorBean;
import com.kaurihealth.datalib.request_bean.bean.RegisterResponse;
import com.kaurihealth.datalib.service.IRegisterService;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.datalib.request_bean.builder.NewRegistByDoctorBeanBuilder;
import com.kaurihealth.kaurihealth.common.activity.MainActivity;
import com.kaurihealth.kaurihealth.services.ServiceFactory;
import com.kaurihealth.kaurihealth.util.Enums.LoadingStatu;
import com.kaurihealth.kaurihealth.util.ExitApplication;
import com.kaurihealth.kaurihealth.util.TextWatchClearError;
import com.kaurihealth.kaurihealth.util.Url;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Pattern;
import com.youyou.zllibrary.util.CommonActivity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 版权:    张磊
 * 作者:    张磊
 * 描述：
 * 修订日期:
 */
public class OpenAnAccountActivity extends CommonActivity {

    @Bind(R.id.tvPhone)
    @NotEmpty(sequence = 1, message = "电话号码不能为空")
    @Pattern(sequence = 2, regex = "^1[3|4|5|7|8]\\d{9}$", message = "请输入正确的电话号码")
    EditText tvPhone;
    @NotEmpty(message = "姓不能为空")
    @Length(max = 5)
    @Bind(R.id.tvFirstName)
    EditText tvFirstName;  //姓

    @NotEmpty(message = "名不能为空")
    @Length(max = 5)
    @Bind(R.id.tvLastName)
    EditText tvLastName; //名

    @NotEmpty(message = "性别不能为空")
    @Bind(R.id.tv_gender)
    EditText tvGendar;

    @NotEmpty(message = "生日不能为空")
    @Bind(R.id.tvBirthday)
    EditText tvBirthday;
    @Bind(R.id.tv_operate)
    TextView tvOperate;

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



    private Dialog gendarDialog;
    private Dialog dateDialog;
    private Validator validator;

    private IRegisterService registerService;

    private Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.open_an_account);
        ButterKnife.bind(this);
        init();
        ExitApplication.getInstance().addActivity(this);
    }


    public void init() {
        setBack(R.id.iv_back);
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
        tvPhone.addTextChangedListener(new TextWatchClearError(tvPhone,ivDelete1));
        tvFirstName.addTextChangedListener(new TextWatchClearError(tvFirstName,ivDelete2));
        tvLastName.addTextChangedListener(new TextWatchClearError(tvLastName,ivDelete5));
        tvGendar.addTextChangedListener(new TextWatchClearError(tvGendar,ivDelete3));
        tvBirthday.addTextChangedListener(new TextWatchClearError(tvBirthday,ivDelete4));

        //初始化表单验证器
        validator = new Validator(this);
        validator.setValidationListener(validationListener);
        registerService = new ServiceFactory(Url.prefix, this).getRegisterService();
        handler = new Handler();
    }

    @OnClick({R.id.ivDelete1, R.id.ivDelete2, R.id.ivDelete3, R.id.ivDelete4, R.id.ivDelete5, R.id.rlay_gendar, R.id.rlay_birthday, R.id.tv_operate,R.id.tvBirthday,R.id.tv_gender})
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


    Validator.ValidationListener validationListener = new Validator.ValidationListener() {
        @Override
        public void onValidationSucceeded() {
            String firstName = getTvValue(tvLastName);
            String lastName = getTvValue(tvFirstName);
            String userName = getTvValue(tvPhone);
            String gender = getTvValue(tvGendar);
            String dayOfBirth = getTvValue(tvBirthday);

            NewRegistByDoctorBean newRegistByDoctorBean = new NewRegistByDoctorBeanBuilder().Build(userName, firstName, lastName, gender, dayOfBirth);

            Call<RegisterResponse> call = registerService.RegistByDoctor_out(newRegistByDoctorBean);
            call.enqueue(new Callback<RegisterResponse>() {
                @Override
                public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body().isSuccessful) {
                            int time = 2000;
                            showToast("帮患者开户成功",null);
                            Intent intent = new Intent();
                            intent.putExtra(MainActivity.PositionKey, 2);
                            setResult(Activity.RESULT_OK, intent);
                            tvOperate.setClickable(false);
                            clearTextView(tvLastName);
                            clearTextView(tvPhone);
                            clearTextView(tvFirstName);
                            clearTextView(tvBirthday);
                            clearTextView(tvGendar);
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    finishCur();
                                }
                            }, time);

                        } else {
                            showToast(response.body().message,null);

                        }

                    } else {
                        showToast("帮患者开户失败",null);
                    }
                }

                @Override
                public void onFailure(Call<RegisterResponse> call, Throwable t) {
                    showToast(LoadingStatu.NetError.value);
                    Bugtags.sendException(t);
                }
            });

        }

        @Override
        public void onValidationFailed(List<ValidationError> errors) {
            showValidationMessage(errors);
        }
    };

}
