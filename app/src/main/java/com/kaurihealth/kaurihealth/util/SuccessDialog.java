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
import com.kaurihealth.kaurihealth.util.Interface.ISuccessDialog;
import com.kaurihealth.kaurihealth.util.bean.SuccessDialogMesBean;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 张磊 on 2016/7/15.
 * 介绍：
 */
public class SuccessDialog extends AlertDialog implements ISuccessDialog {
    SuccessDialogMesBean param = new SuccessDialogMesBean();
    @Bind(R.id.tvAge)
    TextView tvTitle;
    @Bind(R.id.tvMes)
    TextView tvMes;
    @Bind(R.id.tvConfirm)
    TextView tvConfirm;

    public SuccessDialog(@NonNull Context context) {
        super(context);
    }

    protected SuccessDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    protected SuccessDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        View view = LayoutInflater.from(getContext()).inflate(R.layout.success_dialog, null);
        setContentView(view);
        ButterKnife.bind(this, view);
        init();
    }

    private void init() {
        if (param.title != null) {
            tvTitle.setText(param.title);
        }
        if (param.mes != null) {
            tvMes.setText(param.mes);
        }
        if (param.btn != null) {
            tvConfirm.setText(param.btn);
        }
    }


    @Override
    public void setTitle(String title) {
        param.title = title;
        if (tvTitle != null) {
            tvTitle.setText(title);
        }
    }

    @Override
    public void setMes(String mes) {
        param.mes = mes;
        if (tvMes != null) {
            tvMes.setText(mes);
        }
    }

    @Override
    public void setConfirm(String btnConirm, View.OnClickListener onClickListener) {
        param.btn = btnConirm;
        param.onClickListener = onClickListener;
        if (tvConfirm != null) {
            tvConfirm.setText(btnConirm);
        }
    }

    @Override
    public void setConfirm(View.OnClickListener onClickListener) {
        param.onClickListener = onClickListener;
    }

    @OnClick(R.id.tvConfirm)
    public void onClick(View view) {
        if (param.onClickListener != null) {
            param.onClickListener.onClick(view);
        }
        dismiss();
    }
}
