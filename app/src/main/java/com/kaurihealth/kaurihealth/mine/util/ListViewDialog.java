package com.kaurihealth.kaurihealth.mine.util;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.ArrayRes;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.commonlibrary.widget.util.LogFactory;
import com.example.commonlibrary.widget.util.LogUtilInterface;
import com.kaurihealth.utilslib.dialog.SuccessInterfaceM;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.mine.adapter.LvDialogAdapter;
import com.kaurihealth.kaurihealth.mine.bean.NumberDialogParam;
import com.kaurihealth.kaurihealth.mine.bean.SelectBean;

import java.util.Arrays;
import java.util.List;

/**
 * Created by 张磊 on 2016/7/13.
 * 介绍：
 */
public class ListViewDialog extends Dialog {
    private View view;
    private ListView numberPicker;

    public ListViewDialog(Context context) {
        super(context);
        init(context);
    }

    public ListViewDialog(Context context, int themeResId) {
        super(context, themeResId);
        init(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = this.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.BOTTOM);
        int screenWidth = window.getWindowManager().getDefaultDisplay().getWidth();
        view.setMinimumWidth(screenWidth);
        setContentView(view);
    }

    private void init(Context context) {
        view = LayoutInflater.from(context).inflate(R.layout.numberpicker_dialog, null);
        numberPicker = (ListView) view.findViewById(R.id.lv_content);
    }


    public static class Builder {
        private NumberDialogParam p = new NumberDialogParam();
        public ListViewDialog dialog;

        public Builder(@NonNull Context context, @StyleRes int style) {
            p.context = context;
            p.style = style;
        }

        public Builder(@NonNull Context context) {
            this(context, R.style.Dialog_Date);
        }


        public Builder setItems(String[] iteams) {
            p.iteams = iteams;
            return this;
        }

        public Builder setItems(List<String> iteams) {
            String[] iteamStrs = new String[iteams.size()];
            if (iteams != null) {
                for (int i = 0; i < iteams.size(); i++) {
                    iteamStrs[i] = iteams.get(i);
                }
            }
            p.iteams = iteamStrs;
            return this;
        }

        public Builder setItems(@ArrayRes int iteams) {
            return setItems(p.context.getResources().getStringArray(iteams));
        }

        public Builder setOnIteamClickListener(SuccessInterfaceM<SelectBean<String>> onIteamClickListener) {
            p.onIteamClickListener = onIteamClickListener;
            return this;
        }

        public Dialog create() {
            if (p.style != p.ErrorDefault) {
                dialog = new ListViewDialog(p.context, p.style);
            } else {
                dialog = new ListViewDialog(p.context);
            }
            if (p.iteams != null) {
                LvDialogAdapter adapter = new LvDialogAdapter(p.context, Arrays.asList(p.iteams));
                dialog.numberPicker.setAdapter(adapter);
            }
            dialog.numberPicker.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    SelectBean<String> selectBean = new SelectBean();
                    selectBean.position = position;
                    selectBean.t = p.iteams[position];
                    p.onIteamClickListener.success(selectBean);
                    dialog.dismiss();
                }
            });
            return dialog;
        }
    }

    LogUtilInterface logUtil = LogFactory.getSimpleLog(getClass().getName());
}
