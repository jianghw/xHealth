package com.kaurihealth.utilslib.widget;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kaurihealth.utilslib.R;


/**
 * Created by jianghw on 2016/12/21.
 * <p/>
 * Describe:
 */

public class CollapsibleGroupView extends LinearLayout
        implements ValueAnimator.AnimatorUpdateListener, Animator.AnimatorListener, View.OnClickListener {
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
    private TextView mTvTitle;
    private LinearLayout mLayTitle;
    private ViewGroup mViewGroup;
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
     * 扩展布局接口
     */
    private ChildGroupView mChildGroupView;

    public CollapsibleGroupView(Context context) {
        this(context, null);
    }

    public CollapsibleGroupView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CollapsibleGroupView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initObtainAttributes(attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CollapsibleGroupView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initObtainAttributes(attrs);
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

    public void initInflaterView() {
        View root = LayoutInflater.from(getContext().getApplicationContext()).inflate(R.layout.collapsible_group_view, this, true);
        LinearLayout mLayBackground = (LinearLayout) root.findViewById(R.id.lay_background);
        mLayTitle = (LinearLayout) root.findViewById(R.id.lay_title);
        mTvTitle = (TextView) root.findViewById(R.id.tv_title);
        mTvHint = (TextView) root.findViewById(R.id.tv_hint);

        mTvTitle.setText(mTitle);
        mTvTitle.setTextColor(mTitleTextColor);
        mTvHint.setText(mCloseText);
        mLayTitle.setOnClickListener(this);

        if (mChildGroupView == null)
            throw new IllegalArgumentException("ChildGroupView must implements and set it");

        View mChildView = LayoutInflater.from(getContext().getApplicationContext()).inflate(mChildGroupView.getChildResLayoutId(), mLayBackground, true);
        mChildGroupView.onCreateChildView(mChildView);
    }

    private float dip2px(Context context, float height) {
        float scale = context.getResources().getDisplayMetrics().density;
        return height * scale + 0.5f;
    }

    public void setCollapsibleTitle(String collapsibleTitle) {
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
        if (mViewGroup == null)
            throw new IllegalArgumentException("must call notifyDataSetChanged(ViewGroup viewGroup)");

        int value = (int) animation.getAnimatedValue();
        ViewGroup.LayoutParams params = mViewGroup.getLayoutParams();
        params.height = value;
        mViewGroup.setLayoutParams(params);
    }

    @Override
    public void onAnimationStart(Animator animation) {
    }

    @Override
    public void onAnimationEnd(Animator animation) {
        isOpenAnimation = !isOpenAnimation;
        mTvHint.setText(isOpenAnimation ? mOpenText : mCloseText);
        setDrawable(isOpenAnimation);
    }

    /**
     * 设置收起跟关闭的图片
     *
     * @param isOpenAnimation
     */
    private void setDrawable(boolean isOpenAnimation) {
        Drawable drawableOpen = getResources().getDrawable(R.drawable.more_icon_up);
        drawableOpen.setBounds(0, 0, drawableOpen.getMinimumWidth(), drawableOpen.getMinimumHeight());//必须设置图片大小，否则不显示
        Drawable drawableClose = getResources().getDrawable(R.drawable.more_icon);
        drawableClose.setBounds(0, 0, drawableClose.getMinimumWidth(), drawableClose.getMinimumHeight());//必须设置图片大小，否则不显示
        mTvHint.setCompoundDrawables(
                null, null, isOpenAnimation ? drawableClose :
                        drawableOpen, null);

    }

    @Override
    public void onAnimationCancel(Animator animation) {
        isOpenAnimation = !isOpenAnimation;
        mTvHint.setText(isOpenAnimation ? mOpenText : mCloseText);
        setDrawable(isOpenAnimation);
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


    private void runItemAnimation() {
        ValueAnimator valueAnimator = isOpenAnimation ? createAnimators(0, mHeigth) : createAnimators(mHeigth, 0);
        valueAnimator.start();
    }

    public void setHintTextHidden(boolean b) {
        mTvHint.setVisibility(b ? INVISIBLE : VISIBLE);
        setDrawable(b);
    }

    public void notifyDataSetChanged(ViewGroup viewGroup) {
        viewGroup.measure(0, 0);
        int totalHeight = viewGroup.getMeasuredHeight();
        ViewGroup.LayoutParams params = viewGroup.getLayoutParams();
        params.height = totalHeight;
        viewGroup.setLayoutParams(params);

        mHeigth = totalHeight;
        mViewGroup = viewGroup;
    }

    public void setChildGroupViewBack(ChildGroupView childGroupView) {
        this.mChildGroupView = childGroupView;
    }

    public interface ChildGroupView {
        int getChildResLayoutId();

        void onCreateChildView(View view);
    }
}
