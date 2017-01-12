package com.kaurihealth.utilslib.widget;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.kaurihealth.utilslib.R;


/**
 * Created by jianghw on 2016/12/21.
 * <p/>
 * Describe:
 */

public class CollapsibleLinearLayout extends LinearLayout
        implements ValueAnimator.AnimatorUpdateListener, Animator.AnimatorListener, View.OnClickListener, AdapterView.OnItemClickListener {
    /**
     * 默认值集合处
     */
    private static final String DEFAULT_TITLE = "标题栏";
    private static final String DEFAULT_OPEN = "展开";
    private static final String DEFAULT_CLOSE = "收起";
    private static final float DEFAULT_HEIGHT = 40f;
    private static final int DEFAULT_DURATION = 400;
    private static final int DEFAULT_TITLE_COLOR = Color.parseColor("#ffffff");
    private static final int DEFAULT_TITLE_TEXT_COLOR = Color.parseColor("#122029");
    private static final float DEFAULT_RADIUS = 0;
    private static final int DEFAULT_CONTENT_COLOR = Color.parseColor("#ffffff");
    /**
     * 控件
     */
    private TextView mTvHint;
    private ListView mLvContent;
    private TextView mTvTitle;
    private LinearLayout mLayTitle;
    /**
     * 设置值
     */
    private int mHeigth;
    private int mDuration;
    private int mTitleColor;
    private int mTitleTextColor;
    private float mRadius;
    private int mContentColor;
    private String mTitle;
    private String mOpenText;
    private String mCloseText;
    /**
     * 是否展开动画
     */
    private boolean isOpenAnimation = false;
    /**
     * 点击接口
     */
    private ListViewItemClick mListViewItemClick;

    public CollapsibleLinearLayout(Context context) {
        this(context, null);
    }

    public CollapsibleLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CollapsibleLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initObtainAttributes(attrs);

        initInflaterView();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CollapsibleLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initObtainAttributes(attrs);
        initInflaterView();
    }

    private void initObtainAttributes(AttributeSet attrs) {
        TypedArray arrayAttrs = getContext().obtainStyledAttributes(attrs, R.styleable.CollapsibleLinearLayout);
        String title = arrayAttrs.getString(R.styleable.CollapsibleLinearLayout_CollapsibleTitle);
        if (title == null) title = DEFAULT_TITLE;
        mTitle = title;
        String open = arrayAttrs.getString(R.styleable.CollapsibleLinearLayout_CollapsibleHintOpen);
        if (open == null) open = DEFAULT_OPEN;
        mOpenText = open;
        String close = arrayAttrs.getString(R.styleable.CollapsibleLinearLayout_CollapsibleHintClose);
        if (close == null) close = DEFAULT_CLOSE;
        mCloseText = close;
        mHeigth = (int) arrayAttrs.getDimension(R.styleable.CollapsibleLinearLayout_CollapsibleHeight, dip2px(getContext(), DEFAULT_HEIGHT));
        mDuration = arrayAttrs.getInt(R.styleable.CollapsibleLinearLayout_CollapsibleDuration, DEFAULT_DURATION);
        mTitleColor = arrayAttrs.getColor(R.styleable.CollapsibleLinearLayout_CollapsibleTitleColor, DEFAULT_TITLE_COLOR);
        mTitleTextColor = arrayAttrs.getColor(R.styleable.CollapsibleLinearLayout_CollapsibleTitleTextColor, DEFAULT_TITLE_TEXT_COLOR);
        mRadius = arrayAttrs.getDimension(R.styleable.CollapsibleLinearLayout_CollapsibleCornerRadius, DEFAULT_RADIUS);
        mContentColor = arrayAttrs.getColor(R.styleable.CollapsibleLinearLayout_CollapsibleContentColor, DEFAULT_CONTENT_COLOR);
        arrayAttrs.recycle();
    }

    private void initInflaterView() {
        View view = LayoutInflater.from(getContext().getApplicationContext()).inflate(R.layout.collapsible_linear_layout, this, true);
        mLayTitle = (LinearLayout) view.findViewById(R.id.lay_title);
        mTvTitle = (TextView) view.findViewById(R.id.tv_title);
        mTvHint = (TextView) view.findViewById(R.id.tv_hint);
        mLvContent = (ListView) view.findViewById(R.id.lv_content);

        mLayTitle.setOnClickListener(this);
        mLvContent.setOnItemClickListener(this);

        mTvTitle.setText(mTitle);
        mTvTitle.setTextColor(mTitleTextColor);
        mTvHint.setText(mCloseText);
    }

    private float dip2px(Context context, float height) {
        float scale = context.getResources().getDisplayMetrics().density;
        return height * scale + 0.5f;
    }

    public void setCollapsibleTitle(String collapsibleTitle){
        mTvTitle.setText(collapsibleTitle);
    }

    /**
     * 背景
     */
    private void initBackground() {
        float[] outRadius = new float[8];
        for (int i = 0; i < outRadius.length; i++) {
            outRadius[i] = mRadius;
        }

        RoundRectShape rectShape = new RoundRectShape(outRadius, null, null);
        ShapeDrawable background = new ShapeDrawable(rectShape);
        background.getPaint().setColor(mContentColor);
        background.getPaint().setStyle(Paint.Style.FILL);
        findViewById(R.id.lay_background).setBackgroundDrawable(background);

        ShapeDrawable title = new ShapeDrawable(rectShape);
        title.getPaint().setColor(mTitleColor);
        title.getPaint().setStyle(Paint.Style.FILL);
        mLayTitle.setBackgroundDrawable(title);
    }

    private ValueAnimator createAnimators(int from, int height) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(from, height);
        valueAnimator.setDuration(mDuration);
        valueAnimator.addUpdateListener(this);
        valueAnimator.addListener(this);
        return valueAnimator;
    }

    /**
     * 动画
     */
    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        int value = (int) animation.getAnimatedValue();
        ViewGroup.LayoutParams params = mLvContent.getLayoutParams();
        params.height = value;
        mLvContent.setLayoutParams(params);
    }

    @Override
    public void onAnimationStart(Animator animation) {
    }

    @Override
    public void onAnimationEnd(Animator animation) {
        isOpenAnimation = !isOpenAnimation;
        mTvHint.setText(isOpenAnimation ? mOpenText : mCloseText);
    }

    @Override
    public void onAnimationCancel(Animator animation) {
        isOpenAnimation = !isOpenAnimation;
        mTvHint.setText(isOpenAnimation ? mOpenText : mCloseText);
    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }

    /**
     * 标题栏点击事件
     */
    @Override
    public void onClick(View v) {
        if (mTvHint.getVisibility() == VISIBLE) runItemAnimation();
    }

    /**
     * item 点击
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mListViewItemClick.onItemClick(parent, view, position, id);
    }

    private void runItemAnimation() {
        ValueAnimator valueAnimator = isOpenAnimation ? createAnimators(0, mHeigth) : createAnimators(mHeigth, 0);
        valueAnimator.start();
    }

    public void setViewAdapter(BaseAdapter adapter) {
        mLvContent.setAdapter(adapter);
    }


    public void setHintTextHidden(boolean b) {
        mTvHint.setVisibility(b ? INVISIBLE : VISIBLE);
    }

    public void notifyDataSetChanged() {
        ListAdapter mAdapter = mLvContent.getAdapter();
        int totalHeight = 0;
        for (int i = 0; i < mAdapter.getCount(); i++) {
            View listItem = mAdapter.getView(i, null, mLvContent);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = mLvContent.getLayoutParams();
        params.height = totalHeight + (mLvContent.getDividerHeight() * (mAdapter.getCount() - 1));
        mLvContent.setLayoutParams(params);

        mHeigth = params.height;
    }

    public void setListViewItemClick(ListViewItemClick listViewItemClick) {
        this.mListViewItemClick = listViewItemClick;
    }

    public interface ListViewItemClick {
        void onItemClick(AdapterView<?> parent, View view, int position, long id);
    }
}
