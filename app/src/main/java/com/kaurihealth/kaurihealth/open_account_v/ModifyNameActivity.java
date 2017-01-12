package com.kaurihealth.kaurihealth.open_account_v;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.utilslib.ValidatorUtils;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 修改姓名
 */
public class ModifyNameActivity extends BaseActivity implements Validator.ValidationListener {

    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.tv_more)
    TextView mTvMore;
    @NotEmpty(message = "名不能为空")
    @Bind(R.id.tvFirstName)
    EditText mTvFirstName;
    @Bind(R.id.tv_02)
    TextView mTv02;
    @NotEmpty(message = "姓不能为空")
    @Bind(R.id.tvLastName)
    EditText mTvLastName;
    @Bind(R.id.tv_03)
    TextView mTv03;

    private Validator validator;

    @Override
    protected int getActivityLayoutID() {
        return R.layout.activity_modify_name;
    }

    @Override
    protected void initPresenterAndView(Bundle savedInstanceState) {
        initNewBackBtn("修改用户姓名");
        mTvMore.setText("修改");

        //初始化validator
        validator = new Validator(this);
        validator.setValidationListener(this);

        setListener(mTvFirstName, mTv02, R.mipmap.name_icon, R.mipmap.name_icon_on);
        setListener(mTvLastName, mTv03, R.mipmap.name_icon, R.mipmap.name_icon_on);
    }

    @Override
    protected void initDelayedData() {
        Bundle bundle = getBundle();
        String lastName = bundle.getString("lastName");
        String firstName = bundle.getString("firstName");
        mTvLastName.setText(lastName);
        mTvFirstName.setText(firstName);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @OnClick(R.id.tv_more)
    public void onClick() {
        validator.validate();
    }

    @Override
    public void onValidationSucceeded() {
        Intent intent = getIntent();
        Bundle bundle = new Bundle();
        bundle.putString("lastName", mTvLastName.getText().toString().trim());
        bundle.putString("firstName", mTvFirstName.getText().toString().trim());
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finishCur();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        String message = ValidatorUtils.validationErrorMessage(getApplicationContext(), errors);
        displayErrorDialog(message);
    }

    private void setListener(EditText editText, TextView tvLinePw, int greyIcon, int greenIcon) {
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

    private void changeColorByFocus(int greenIcon, EditText editText, TextView tvLinePw, boolean isFocus) {
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
}
