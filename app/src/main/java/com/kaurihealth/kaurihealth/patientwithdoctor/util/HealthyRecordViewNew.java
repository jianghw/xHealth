package com.kaurihealth.kaurihealth.patientwithdoctor.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by 张磊 on 2016/4/21.
 * 介绍：
 */
public abstract class HealthyRecordViewNew<T> {
    Context context;
    private final LayoutInflater inflater;
    private View contentView;
    protected List<T> list = new ArrayList<T>();

    public HealthyRecordViewNew(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    protected void setTitle(int titleId, String title) {
        TextView titleView = (TextView) getView().findViewById(titleId);
        titleView.setText(title);
    }

    public void setView(int resId) {
        contentView = inflater.inflate(resId, null);
        ButterKnife.bind(this, getView());
        if (isEditable()) {
            editAbleView();
        } else {
            uneditAbleView();
        }
    }

    public View getView() {
        return contentView;
    }

    public void setData(T t) {
        list.add(t);
    }

    public List<T> getData() {
        return list;
    }

    public abstract void setMode(Mode mode);

    public abstract boolean isEditable();

    public abstract void editAbleView();

    public abstract void uneditAbleView();

}
