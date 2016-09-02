package com.kaurihealth.kaurihealth.mine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;

import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.util.ExitApplication;
import com.kaurihealth.kaurihealth.util.Interface.IPutter;
import com.kaurihealth.kaurihealth.util.Putter;
import com.youyou.zllibrary.util.CommonActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 版权:    张磊
 * 作者:    张磊
 * 描述：
 * 修订日期:
 */
public class NameActivity extends CommonActivity {

    @Bind(R.id.edt_firstName)
    EditText edtFirstName;
    @Bind(R.id.edt_secondName)
    EditText secondName;
    private Bundle bundle;
    private IPutter putter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.name);
        ButterKnife.bind(this);
        init();
        ExitApplication.getInstance().addActivity(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 保存
     */
    @OnClick(R.id.tv_store)
    public void store() {
        final String firstNameStr = secondName.getText().toString().trim();
        final String secondNameStr = edtFirstName.getText().toString().trim();
        if (TextUtils.isEmpty(firstNameStr)) {
            return;
        }
        if (TextUtils.isEmpty(secondNameStr)) {
            return;
        }
        putter.setName(firstNameStr,secondNameStr);
        Intent intent = getIntent();
        bundle.putString("firstName", firstNameStr);
        bundle.putString("lastName", secondNameStr);
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finishCur();
    }

    /**
     * 删除
     */
    @OnClick(R.id.rlay_delete1)
    public void delete() {
        edtFirstName.setText("");
    }

    @OnClick(R.id.rlay_delete2)
    public void delete2() {
        secondName.setText("");
    }

    @Override
    public void init() {
        super.init();
        putter = new Putter(getApplicationContext());
        edtFirstName.setSelection(edtFirstName.getText().length());
        setBack(R.id.iv_back);
        bundle = getBundle();
        String firstName = bundle.getString("firstName");
        String lastName = bundle.getString("lastName");
        edtFirstName.setText(lastName);
        secondName.setText(firstName);
    }
}
