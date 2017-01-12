package com.kaurihealth.utilslib.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.kaurihealth.utilslib.R;
import com.kaurihealth.utilslib.image.GalleryUtil;
import com.kaurihealth.utilslib.image.HospitalizationDocumentsBean;
import com.kaurihealth.utilslib.image.RecordDocumentsBean;
import com.kaurihealth.utilslib.image.StringPathViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jianghw on 2016/8/23.
 * <p/>
 * 描述：查看图片布局
 */
public class ReviewImageLayout extends LinearLayout implements AdapterView.OnItemClickListener {
    /**
     * 默认值
     */
    private static final int DEFAULT_TITLE_COLOR = Color.parseColor("#ccd3d5");
    private static final int DEFAULT_CONTENT_COLOR = Color.parseColor("#333333");
    private static final int DEFAULT_COMPILE_TITLE_COLOR = Color.parseColor("#000b23");
    private static final int DEFAULT_COMPILE_CONTEN_COLOR = Color.parseColor("#293d47");
    /**
     * 布局
     */
    private TextView titleTv;
    private TagsGridview imageGv;
    /**
     * 设置值
     */
    private String mTitle;
    private int mTitleColor;
    private int mContentColor;
    private boolean mCompileMode;
    private ArrayList<String> paths = new ArrayList<>();   //“附件”图片的地址集合
    private StringPathViewAdapter adapter;
    private List<HospitalizationDocumentsBean> list = new ArrayList<>();

    public ReviewImageLayout(Context context) {
        this(context, null);
    }

    public ReviewImageLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ReviewImageLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initObtainAttributes(attrs);
        initLayoutView();
    }

    private void initObtainAttributes(AttributeSet attrs) {
        TypedArray arrayAttrs = getContext().obtainStyledAttributes(attrs, R.styleable.RecentlyRecordLayout);
        mTitle = arrayAttrs.getString(R.styleable.RecentlyRecordLayout_RecentlyTitle);
        String content = arrayAttrs.getString(R.styleable.RecentlyRecordLayout_RecentlyContent);

        mTitleColor = arrayAttrs.getColor(R.styleable.RecentlyRecordLayout_RecentlyTitleColor, DEFAULT_TITLE_COLOR);
        mContentColor = arrayAttrs.getColor(R.styleable.RecentlyRecordLayout_RecentlyContentColor, DEFAULT_CONTENT_COLOR);

        mCompileMode = arrayAttrs.getBoolean(R.styleable.RecentlyRecordLayout_RecentlyCompileMode, false);

        arrayAttrs.recycle();
    }

    private void initLayoutView() {
        View view = LayoutInflater.from(getContext().getApplicationContext()).inflate(R.layout.include_review_image, this, true);
        titleTv = (TextView) view.findViewById(R.id.tvTitle);
        imageGv = (TagsGridview) view.findViewById(R.id.gv_image);

        imageGv.setOnItemClickListener(this);

        if (mTitle != null) titleTv.setText(mTitle);
        if (mCompileMode) {//编辑
            titleTv.setTextColor(DEFAULT_COMPILE_TITLE_COLOR);
        } else {
            titleTv.setTextColor(mTitleColor);
        }

        adapter = new StringPathViewAdapter(getContext(), paths);
        imageGv.setAdapter(adapter);
    }

    public void titleText(String titleText) {
        if (titleTv != null) titleTv.setText(titleText);
    }

    public void setContentTitle(String titleText, String contentText) {
        titleText(titleText);
    }

    public void initHospitalDocumentPathAdapter(List<HospitalizationDocumentsBean> documents) {
        if (documents != null && !documents.isEmpty()) {
            paths.clear();
            for (HospitalizationDocumentsBean bean : documents) {
                if (!bean.isIsDeleted()) {
                    paths.add(bean.getDocumentUrl());
                }
            }
        }
        adapter.notifyDataSetChanged();
        gridViewHeigh(3, imageGv);
    }

    public void initDocumentPathAdapter(List<RecordDocumentsBean> documents) {
        if (documents != null && !documents.isEmpty()) {
            paths.clear();
            for (RecordDocumentsBean bean : documents) {
                if (!bean.isIsDeleted()) {
                    paths.add(bean.getDocumentUrl());
                }
            }
        }
        adapter.notifyDataSetChanged();
        gridViewHeigh(3, imageGv);
    }

    public void gridViewHeigh(int numColumns, GridView gridView) {
        ListAdapter listAdapter = gridView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) { // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, gridView);
            listItem.measure(0, 0); // 计算子项View 的宽高
            /**
             * i=0-->
             * i=1-->
             */
            if ((i + 1) % numColumns == 0) {
                totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
            }
            if ((i + 1) == len && (i + 1) % numColumns != 0) {
                totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
            }
        }
        ViewGroup.LayoutParams params = gridView.getLayoutParams();
        params.height = totalHeight;
        gridView.setLayoutParams(params);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        GalleryUtil.toGallery(getContext(), new GalleryUtil.GetUrlsInterface() {
            @Override
            public ArrayList<String> getUrls() {
                return paths;
            }
        }, position);
    }
}