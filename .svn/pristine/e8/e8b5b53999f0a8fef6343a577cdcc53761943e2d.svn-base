package com.kaurihealth.kaurihealth.mine_v;

import android.app.Activity;
import android.app.Dialog;
import android.content.res.Resources;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.utilslib.dialog.DialogUtils;
import com.kaurihealth.utilslib.SysUtil;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 张磊 on 2016/6/14.
 * 介绍：
 */
public class HealthyRecordDialog implements IControlHealthyRecordDialog<String> {
    @Bind(R.id.tvAge)
    TextView tvTitle;
    @Bind(R.id.lv_content)
    LinearLayout lvContent;
    @Bind(R.id.tvCancle)
    TextView tvCancle;
    private List<String> data;
    private Activity activity;
    private IHealthyRecordSelect selectListener;
    private final View view;
    private final Dialog dialog;
    private final Resources resources;

    public HealthyRecordDialog(Activity activity) {
        this.activity = activity;
        resources = activity.getResources();
        view = LayoutInflater.from(activity.getApplicationContext()).inflate(R.layout.healthyrecordselects, null);
        ButterKnife.bind(this, view);
        dialog = DialogUtils.createDialogNew(view, activity, R.style.Dialog_Date);
        init();
    }

    private void init() {
        tvCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    public void setOnSelectListener(IHealthyRecordSelect selectListener) {
        this.selectListener = selectListener;
    }

    public void setData(List<String> data) {
        this.data = data;
        lvContent.removeAllViews();
        final Map<View, Integer> textviews = new LinkedHashMap<>();
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (HealthyRecordDialog.this.selectListener != null) {
                    if (textviews.size() != 0) {
                        Integer position = textviews.get(v);
                        if (position != null) {
                            HealthyRecordDialog.this.selectListener.onSelect(position);
                        }
                    }
                }
                dialog.dismiss();
            }
        };
        for (int i = 0; i < data.size(); i++) {
            TextView textView = createTextView(data.get(i));
            textviews.put(textView, i);
            textView.setOnClickListener(onClickListener);
        }
        Iterator<Map.Entry<View, Integer>> iterator = textviews.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<View, Integer> next = iterator.next();
            View key = next.getKey();
            lvContent.addView(key);
            lvContent.addView(createLine());
        }
    }

    @Override
    public void setData(String[] data) {
        List<String> list = Arrays.asList(data);
        setData(list);
    }

    public List<String> getData() {
        return data;
    }

    public void touch() {
        if (dialog.isShowing()) {
            dialog.dismiss();
        } else {
            dialog.show();
        }
    }

    public void dismiss() {
        dialog.dismiss();
    }

    public Dialog getDialog() {
        return dialog;
    }

    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    private TextView createTextView(String content) {
        TextView textView = new TextView(activity.getApplicationContext());
        textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        textView.setTextColor(resources.getColor(R.color.colorPrimary));
        textView.setGravity(Gravity.CENTER);
        textView.setText(content);
        textView.setBackgroundColor(resources.getColor(android.R.color.white));
        int paddingInPix = SysUtil.dp2px(6, activity.getApplicationContext());
        textView.setPadding(paddingInPix, paddingInPix, paddingInPix, paddingInPix);
        return textView;
    }

    private View createLine() {
        TextView tvLine = new TextView(activity.getApplicationContext());
        tvLine.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, SysUtil.dp2px(1, activity.getApplicationContext())));
        tvLine.setBackgroundColor(resources.getColor(R.color.color_gray));
        return tvLine;
    }
}
