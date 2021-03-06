package com.kaurihealth.kaurihealth.register_v;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.kaurihealth.chatlib.LCChatKit;
import com.kaurihealth.datalib.local.LocalData;
import com.kaurihealth.datalib.request_bean.bean.MobileUpdateDoctorBean;
import com.kaurihealth.datalib.response_bean.TokenBean;
import com.kaurihealth.datalib.response_bean.UserBean;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.kaurihealth.eventbus.HospitalAndDepartmentEvent;
import com.kaurihealth.kaurihealth.main_v.MainActivity;
import com.kaurihealth.kaurihealth.mine_v.personal.DoctorDetailsActivity;
import com.kaurihealth.kaurihealth.mine_v.personal.HospitalAndDepartmentActivity;
import com.kaurihealth.kaurihealth.tinker.SampleApplicationLike;
import com.kaurihealth.mvplib.register_p.IRegisterPersonInfoView;
import com.kaurihealth.mvplib.register_p.RegisterPersonInfoPresenter;
import com.kaurihealth.utilslib.ValidatorUtils;
import com.kaurihealth.utilslib.constant.Global;
import com.kaurihealth.utilslib.widget.TextWatchClearError;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 注册信息完成
 */
public class RegisterPersonInfoActivity extends BaseActivity implements Validator.ValidationListener, IRegisterPersonInfoView {
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

    //性别:男
    @Bind(R.id.radio_male)
    RadioButton rbtMale;

    //性别:女
    @Bind(R.id.radio_female)
    RadioButton rbtFemale;

    //医院和科室
    @Bind(R.id.tv_organization)
    TextView tvOrganization;
    //医院和科室的相对布局
    @Bind(R.id.rlay_organization)
    RelativeLayout rlayOrganization;

    //完善更多信息
    @Bind(R.id.tv_more)
    TextView tvMore;

    //进入App的Button
    @Bind(R.id.tv_operate)
    TextView tvOperate;

    @Bind(R.id.ivDelete2)
    ImageView ivDelete2;
    @Bind(R.id.ivDelete3)
    ImageView ivDelete3;
    @Bind(R.id.ivDelete5)
    ImageView ivDelete5;

    @Bind(R.id.first_line)
    TextView first_line;
    @Bind(R.id.second_line)
    TextView second_line;
    @Bind(R.id.fourth_line)
    TextView fourth_line;
    @Bind(R.id.tv_line_gendar)
    TextView mTvLineGendar;

    /**
     * 表单验证
     */
    private Validator validator;

    private int departmentId;
    private String hospitalName;
    private String departmentName;

    @Override
    protected int getActivityLayoutID() {
        return R.layout.activity_register_personlnfo;
    }

    @Override
    protected void initPresenterAndView(Bundle savedInstanceState) {
        SampleApplicationLike.getApp().getComponent().inject(this);
        mPresenter.setPresenter(this);

        //第三方表单验证实例
        validator = new Validator(this);
        validator.setValidationListener(this);
    }

    @Override
    protected void initDelayedData() {
        tvLastName.addTextChangedListener(new TextWatchClearError(tvLastName, ivDelete2));  //姓
        tvFirstName.addTextChangedListener(new TextWatchClearError(tvFirstName, ivDelete5)); //名
        tvOrganization.addTextChangedListener(new TextWatchClearError(tvOrganization, ivDelete3));  //机构

        //对控件进行设置监听的操作,线条变化, 更换图标
        setListener(tvLastName, first_line, R.mipmap.name_icon, R.mipmap.name_icon_on);  //姓的监听
        setListener(tvFirstName, second_line, R.mipmap.name_icon, R.mipmap.name_icon_on);  //名的监听
        setTextListener(tvOrganization, fourth_line, R.mipmap.hospital_icon, R.mipmap.hospital_icon_on);//机构的监听
        rbtMale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mTvLineGendar.setBackgroundResource(R.color.color_enable_green);
            }
        });
        rbtFemale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mTvLineGendar.setBackgroundResource(R.color.color_enable_green);
            }
        });

        EventBus.getDefault().register(this);
    }

    /**
     * @deprecated 弃
     */
    private void initUserName() {
        TokenBean tokenBean = LocalData.getLocalData().getTokenBean();
        if (tokenBean != null) {
            UserBean userBean = tokenBean.getUser();
            tvLastName.setText(userBean.getLastName());
            tvFirstName.setText(userBean.getFirstName());
        }
    }

    /**
     * 控件设置监听
     */
    private void setListener(TextView editText, TextView tvLinePw, int greyIcon, int greenIcon) {
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    changeColorByFocus(greenIcon, editText, tvLinePw, true);
                } else if (editText.getText().toString().length() > 0) {
                    changeColorByFocus(greenIcon, editText, tvLinePw, true);
                } else {
                    changeColorByFocus(greyIcon, editText, tvLinePw, false);
                }
            }
        });
    }

    private void setTextListener(TextView editText, TextView tvLinePw, int greyIcon, int greenIcon) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (editText.getText().toString().length() > 0) {
                    changeColorByFocus(greenIcon, editText, tvLinePw, true);
                } else {
                    changeColorByFocus(greyIcon, editText, tvLinePw, false);
                }
            }
        });
    }

    private void changeColorByFocus(int greenIcon, TextView editText, TextView tvLinePw, boolean isFocus) {
        Drawable drawableLeft = getGreenDrawable(greenIcon);
        editText.setCompoundDrawables(drawableLeft, null, null, null);
        tvLinePw.setBackgroundResource(isFocus ? R.color.color_enable_green : R.color.color_text_gray);
    }

    @NonNull
    private Drawable getGreenDrawable(int greenIcon) {
        Drawable drawableLeft = getResources().getDrawable(greenIcon);
        drawableLeft.setBounds(0, 0, drawableLeft.getMinimumWidth(), drawableLeft.getMinimumHeight());
        return drawableLeft;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeStickyEvent(HospitalAndDepartmentEvent.class);
        EventBus.getDefault().unregister(this);
        mPresenter.unSubscribe();
    }

    /**
     * 事件
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void eventBusMain(HospitalAndDepartmentEvent event) {
        hospitalName = event.getHospitalName();
        departmentName = event.getDepartmentName();
        departmentId = event.getDepartmentId();

        tvOrganization.setText(hospitalName + "/" + departmentName);
    }

    /**
     * --------------------点击事件及表单验证-------------------------
     */
    @OnClick({R.id.tv_operate, R.id.tv_more})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_operate:
                validator.validate();
                break;
            case R.id.tv_more:   //点击认证信息
                skipToMineActivity();
                break;
            default:
                break;
        }
    }

    /**
     * 点击机构和科室的界面
     */
    @OnClick(R.id.rlay_organization)
    public void chooseOrganization(View view) {
        Bundle firstChosen = new Bundle();
        firstChosen.putString(Global.Environment.BUNDLE, tvOrganization.getText().toString().trim());
        skipToForResult(HospitalAndDepartmentActivity.class, firstChosen, Global.RequestCode.DEPARTMENT);
    }

    /**
     * 表单验证成功 注册
     */
    @Override
    public void onValidationSucceeded() {
        if (!rbtMale.isChecked() && !rbtFemale.isChecked()) {
            displayErrorDialog("请选择性别,此为必选项目");
        } else {
            mPresenter.onSubscribe();
        }
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
                    switchPageUI(Global.Jump.MineFragment);
                }
            });
        }
    }

    @Override
    public void switchPageUI(String className) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(Global.Environment.BUNDLE, 4);
        startActivity(intent);
    }

    /**
     * 进入App 请求Bean
     */
    @Override
    public MobileUpdateDoctorBean getMobileUpdateDoctorBean() {
        MobileUpdateDoctorBean mobileUpdateDoctorBean = new MobileUpdateDoctorBean();
        mobileUpdateDoctorBean.setFirstName(getTvValue(tvFirstName));  //名
        mobileUpdateDoctorBean.setLastName(getTvValue(tvLastName));   //姓

        mobileUpdateDoctorBean.setDepartmentId(departmentId); //科室id
        mobileUpdateDoctorBean.setHospitalName(hospitalName); //医院
        mobileUpdateDoctorBean.setDepartmentName(departmentName);  //科室姓名

        mobileUpdateDoctorBean.setGender(rbtMale.isChecked() ? "男" : "女");   //男人是否被选中
        return mobileUpdateDoctorBean;
    }

    /**
     * 认证页面
     */
    @Override
    public void skipToMineActivity() {
        Bundle bundle = new Bundle();
        String lastName = tvLastName.getText().toString().trim();
        bundle.putString("lastName", lastName);
        String firstName = tvFirstName.getText().toString().trim();
        bundle.putString("firstName", firstName);
        bundle.putString("sex", rbtMale.isChecked() ? "男" : "女");
        skipToBundle(DoctorDetailsActivity.class, bundle);

        EventBus.getDefault().postSticky(new HospitalAndDepartmentEvent(hospitalName, departmentName, departmentId));
    }


}
