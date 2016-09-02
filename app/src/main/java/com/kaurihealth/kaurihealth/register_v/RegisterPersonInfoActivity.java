package com.kaurihealth.kaurihealth.register_v;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.example.chatlibrary.chat.ChatInjection;
import com.kaurihealth.utilslib.dialog.PopUpNumberPickerDialog;
import com.kaurihealth.utilslib.dialog.SelectDateDialog;
import com.kaurihealth.kaurihealth.MyApplication;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.kaurihealth.main_v.MainActivity;
import com.kaurihealth.kaurihealth.util.TextWatchClearError;
import com.kaurihealth.mvplib.register_p.IRegisterPersonInfoView;
import com.kaurihealth.mvplib.register_p.RegisterPersonInfoPresenter;
import com.kaurihealth.utilslib.ValidatorUtils;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

public class RegisterPersonInfoActivity extends BaseActivity
        implements Validator.ValidationListener,IRegisterPersonInfoView {
    //名
    @NotEmpty(messageResId = R.string.register_person_first)
    @Length(max = 5)
    @Bind(R.id.tvFirstName)
    EditText tvFirstName;

    //姓
    @NotEmpty(messageResId = R.string.register_person_last)
    @Length(max = 5)
    @Bind(R.id.tvLastName)
    EditText tvLastName;

    //性别
    @NotEmpty(messageResId = R.string.register_person_gender)
    @Bind(R.id.tv_gender)
    EditText tvGendar;
    //生日
    @NotEmpty(messageResId =R.string.register_person_birthday)
    @Bind(R.id.tvBirthday)
    EditText tvBirthday;

    @Bind(R.id.ivDelete2)
    ImageView ivDelete2;
    @Bind(R.id.ivDelete5)
    ImageView ivDelete5;
    @Bind(R.id.ivDelete3)
    ImageView ivDelete3;
    @Bind(R.id.ivDelete4)
    ImageView ivDelete4;

    @Inject
    RegisterPersonInfoPresenter<IRegisterPersonInfoView> mPresenter;

    private Dialog genderDialog;
    private Dialog dateDialog;
    private Validator validator;

    @Override
    protected int getActivityLayoutID() {
        return R.layout.activity_register_personlnfo;
    }

    @Override
    protected void initPresenterAndData(Bundle savedInstanceState) {
        MyApplication.getApp().getComponent().inject(this);
        mPresenter.setPresenter(this);
    }

    @Override
    protected void initDelayedView() {

        final String[] gender = {"男", "女"};

        PopUpNumberPickerDialog genderDialogPopUp = new PopUpNumberPickerDialog(this, gender, new PopUpNumberPickerDialog.SetClickListener() {
            @Override
            public void onClick(int index) {
                tvGendar.setText(gender[index]);
            }
        });
        genderDialog = genderDialogPopUp.getDialog();
        SelectDateDialog selectDateDialog = new SelectDateDialog(this, new SelectDateDialog.DialogListener() {
            @Override
            public void onclick(String year, String month, String day) {
                setDateOfBirth(year, month, day);
            }
        });
        dateDialog = selectDateDialog.getDataDialog();
        //第三方表单验证实例
        validator = new Validator(this);
        validator.setValidationListener(this);
        tvLastName.addTextChangedListener(new TextWatchClearError(tvFirstName, ivDelete2));
        tvFirstName.addTextChangedListener(new TextWatchClearError(tvFirstName, ivDelete5));
        tvGendar.addTextChangedListener(new TextWatchClearError(tvGendar, ivDelete3));
        tvBirthday.addTextChangedListener(new TextWatchClearError(tvBirthday, ivDelete4));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     *--------------------点击事件及表单验证-------------------------
     */
    @OnClick({R.id.ivDelete2, R.id.ivDelete3, R.id.ivDelete4, R.id.ivDelete5, R.id.tv_gender, R.id.tvBirthday, R.id.tv_operate})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivDelete2:
                clearTextView(tvLastName);
                break;
            case R.id.ivDelete3:
                clearTextView(tvGendar);
                break;
            case R.id.ivDelete4:
                clearTextView(tvBirthday);
                break;
            case R.id.ivDelete5:
                clearTextView(tvFirstName);
                break;
            case R.id.tv_gender:
                genderDialog.show();
                break;
            case R.id.tvBirthday:
                dateDialog.show();
                break;
            case R.id.tv_operate:
                validator.validate();
                break;
        }
    }

    /**
     *----------------get各view数值------------------
     */
    @Override
    public String getFirstName() {
        return getTvValue(tvFirstName);
    }

    @Override
    public String getLastName() {
        return getTvValue(tvLastName);
    }

    @Override
    public String getGender() {
        return  getTvValue(tvGendar);
    }

    @Override
    public String getBirthday() {
        return  getTvValue(tvBirthday) + "T00:00:00";
    }


    /**
     * 表单验证成功
     */
    @Override
    public void onValidationSucceeded() {
        mPresenter.onSubscribe();
    }

    /**
     *信息不完整，保存失败
     */
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
        stringBuilder.append("-");
        stringBuilder.append(month);
        stringBuilder.append("-");
        stringBuilder.append(day);
        tvBirthday.setText(stringBuilder.toString());
        stringBuilder.append("T00:00:00");
    }

    /**
     *连接留言板服务器
     */
    @Override
    public void connectLeanCloud(String kauriHealthId) {
        ChatInjection.chatConnect(kauriHealthId, new AVIMClientCallback() {
            @Override
            public void done(AVIMClient avimClient, AVIMException e) {
                if (e != null) {
                    showToast("连接留言服务器失败");
                } else {
                    ChatInjection.avimClient = avimClient;
                    showToast("连接留言服务器成功");
                }
            }
        });
    }

    @Override
    public void switchPageUI(String className) {
        skipTo(MainActivity.class);
    }
}
