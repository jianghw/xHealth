package com.kaurihealth.kaurihealth.register_v;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.kaurihealth.chatlib.LCChatKit;
import com.kaurihealth.datalib.local.LocalData;
import com.kaurihealth.datalib.request_bean.bean.DoctorUserBean;
import com.kaurihealth.datalib.request_bean.builder.DoctorUserBeanBuilder;
import com.kaurihealth.datalib.response_bean.TokenBean;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.kaurihealth.main_v.MainActivity;
import com.kaurihealth.kaurihealth.tinker.SampleApplicationLike;
import com.kaurihealth.mvplib.register_p.IRegisterPersonInfoView;
import com.kaurihealth.mvplib.register_p.RegisterPersonInfoPresenter;
import com.kaurihealth.utilslib.ValidatorUtils;
import com.kaurihealth.utilslib.constant.Global;
import com.kaurihealth.utilslib.dialog.DialogUtils;
import com.kaurihealth.utilslib.widget.TextWatchClearError;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 注册信息完成
 */
public class RegisterPersonInfoActivity extends BaseActivity
        implements Validator.ValidationListener, IRegisterPersonInfoView {
    @Inject
    RegisterPersonInfoPresenter<IRegisterPersonInfoView> mPresenter;

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
    TextView tvGendar;

    //生日
    @NotEmpty(messageResId = R.string.register_person_birthday)
    @Bind(R.id.tvBirthday)
    TextView tvBirthday;

    @Bind(R.id.ivDelete2)
    ImageView ivDelete2;
    @Bind(R.id.ivDelete5)
    ImageView ivDelete5;
    @Bind(R.id.ivDelete3)
    ImageView ivDelete3;

    @Bind(R.id.ivDelete4)
    ImageView ivDelete4;
    /**
     * 表单验证
     */
    private Validator validator;

    @Override
    protected int getActivityLayoutID() {
        return R.layout.activity_register_personlnfo;
    }

    @Override
    protected void initPresenterAndView(Bundle savedInstanceState) {
        SampleApplicationLike.getApp().getComponent().inject(this);
        mPresenter.setPresenter(this);
    }

    @Override
    protected void initDelayedData() {
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
        mPresenter.unSubscribe();
    }

    /**
     * --------------------点击事件及表单验证-------------------------
     */
    @OnClick({R.id.tv_gender, R.id.tvBirthday, R.id.tv_operate})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_gender:
                showGenderDialog();
                break;
            case R.id.tvBirthday:
                showDateDialog();
                break;
            case R.id.tv_operate:
                validator.validate();
                break;
        }
    }

    private void showGenderDialog() {
        String[] gender = getResources().getStringArray(R.array.gender);
        DialogUtils.showStringDialog(this, gender, index -> tvGendar.setText(gender[index]));
    }

    private void showDateDialog() {
        DialogUtils.showDateDialog(this, this::setDateOfBirth);
    }

    /**
     * 表单验证成功
     */
    @Override
    public void onValidationSucceeded() {
        mPresenter.onSubscribe();
    }

    /**
     * 信息不完整，保存失败
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
     * 连接留言板服务器
     */
    @Override
    public void connectLeanCloud() {
        TokenBean tokenBean = LocalData.getLocalData().getTokenBean();
        if (tokenBean.getUser() != null) {
            LCChatKit.getInstance().open(tokenBean.getUser().getKauriHealthId(), new AVIMClientCallback() {
                @Override
                public void done(AVIMClient avimClient, AVIMException e) {
                    showToast("保存成功");
                    switchPageUI(Global.Jump.MainActivity);
                }
            });
        }
    }

    @Override
    public void switchPageUI(String className) {
        skipTo(MainActivity.class);
    }

    @Override
    public DoctorUserBean getDoctorUserBean() {
        String firstName = getFirstName();
        String lastName = getLastName();
        String gender = getGender();
        String dayOfBirth = getBirthday();
        return new DoctorUserBeanBuilder().Build(firstName, lastName, gender, dayOfBirth);
    }

    /**
     * ----------------get各view数值------------------
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
        return getTvValue(tvGendar);
    }

    @Override
    public String getBirthday() {
        return getTvValue(tvBirthday) + "T00:00:00";
    }
}
