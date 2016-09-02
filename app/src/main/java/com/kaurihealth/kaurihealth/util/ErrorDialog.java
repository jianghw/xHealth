package com.kaurihealth.kaurihealth.util;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.util.Interface.IErrorDialog;
import com.kaurihealth.kaurihealth.util.bean.ErrorDialogMesBean;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 张磊 on 2016/7/15.
 * 介绍：
 */
public class ErrorDialog extends AlertDialog implements IErrorDialog {
    ErrorDialogMesBean bean = new ErrorDialogMesBean();

    @Bind(R.id.tvMes)
    TextView tvMes;
    @Bind(R.id.tvReInput)
    TextView tvReInput;

    public ErrorDialog(@NonNull Context context) {
        super(context);
    }

    protected ErrorDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }


    protected ErrorDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        View errorDialogView = LayoutInflater.from(getContext()).inflate(R.layout.cash_error_dialog, null);
        setContentView(errorDialogView);
        ButterKnife.bind(this, errorDialogView);
        if (bean.mes != null) {
            tvMes.setText(bean.mes);
        }
        if (bean.btnStr != null) {
            tvReInput.setText(bean.btnStr);
        }
    }

    @Override
    public void setMes(String mes) {
        bean.mes = mes;
        if (tvMes != null) {
            tvMes.setText(mes);
        }
    }

    @Override
    public void setBtn(String btnStr) {
        bean.btnStr = btnStr;
        if (tvReInput != null) {
            tvReInput.setText(btnStr);
        }
    }

    @OnClick(R.id.tvReInput)
    public void onClick() {
        this.dismiss();
    }

    @Override
    public void show() {
        super.show();
    }
}
