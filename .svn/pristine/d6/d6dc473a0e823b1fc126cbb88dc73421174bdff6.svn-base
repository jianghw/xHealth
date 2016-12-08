package com.kaurihealth.kaurihealth.clinical_v.Utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.kaurihealth.kaurihealth.R;

import butterknife.ButterKnife;

/**
 * Created by mip on 2016/8/15.
 */
public class MyListView extends ListView {
    public MyListView(Context context) {
        this(context,null);
    }

    public MyListView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = LayoutInflater.from(context).inflate(R.layout.activity_dynamic_listview_head,null);
        ButterKnife.bind(view);
        addHeaderView(view);
    }


}
