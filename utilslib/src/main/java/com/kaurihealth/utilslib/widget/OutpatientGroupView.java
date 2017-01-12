package com.kaurihealth.utilslib.widget;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kaurihealth.utilslib.R;


/**
 * Created by jianghw on 2016/12/21.
 * <p/>
 * Describe:
 */

public class OutpatientGroupView extends LinearLayout
        implements ValueAnimator.AnimatorUpdateListener, Animator.AnimatorListener, View.OnClickListener {
    /**
     * 默认值集合处
     */
    private static final String DEFAULT_TITLE = "标题栏";
    private static final String DEFAULT_OPEN = "点击录入更多信息";
    private static final String DEFAULT_CLOSE = "点击收起";
    private static final float DEFAULT_HEIGHT = 40f;
    private static final int DEFAULT_DURATION = 400;
    private static final int DEFAULT_TITLE_COLOR = Color.parseColor("#ffffff");
    private static final int DEFAULT_TITLE_TEXT_COLOR = Color.parseColor("#122029");
    private static final float DEFAULT_RADIUS = 0;
    private static final int DEFAULT_CONTENT_COLOR = Color.parseColor("#ffffff");
    /**
     * 控件
     */
    private ImageView mIvDir;
    private TextView mTvTitle;
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

    public OutpatientGroupView(Context context) {
        this(context, null);
    }

    public OutpatientGroupView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OutpatientGroupView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initObtainAttributes(attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public OutpatientGroupView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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
        View root = LayoutInflater.from(getContext().getApplicationContext()).inflate(R.layout.outpatient_group_view, this, true);
        mTvTitle = (TextView) root.findViewById(R.id.tv_title);
        mIvDir = (ImageView) root.findViewById(R.id.iv_direction);
        mTvTitle.setOnClickListener(this);

        LinearLayout mLayBackground = (LinearLayout) root.findViewById(R.id.lay_background);

        if (mChildGroupView == null)
            throw new IllegalArgumentException("ChildGroupView must implements and set it");

        View mFixedView = LayoutInflater.from(getContext().getApplicationContext()).inflate(mChildGroupView.getFixedResLayoutId(), mLayBackground, true);
        mChildGroupView.onCreateFixedView(mFixedView);
        View mScalingView = LayoutInflater.from(getContext().getApplicationContext()).inflate(mChildGroupView.getScalingResLayoutId(), mLayBackground, true);
        mChildGroupView.onCreateScalingView(mScalingView);
    }

    private float dip2px(Context context, float height) {
        float scale = context.getResources().getDisplayMetrics().density;
        return height * scale + 0.5f;
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
        mTvTitle.setText(isOpenAnimation ? mOpenText : mCloseText);
    }

    @Override
    public void onAnimationCancel(Animator animation) {
        isOpenAnimation = !isOpenAnimation;
        mTvTitle.setText(isOpenAnimation ? mOpenText : mCloseText);
    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }

    /**
     * 标题栏点击事件
     */
    @Override
    public void onClick(View v) {
        if (mIvDir.getVisibility() == VISIBLE) runItemAnimation();
    }


    private void runItemAnimation() {
        ValueAnimator valueAnimator = isOpenAnimation ? createAnimators(0, mHeigth) : createAnimators(mHeigth, 0);
        valueAnimator.start();
    }

    public void setHintTextHidden(boolean b) {
        mIvDir.setVisibility(b ? INVISIBLE : VISIBLE);
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
        int getFixedResLayoutId();

        void onCreateFixedView(View view);

        int getScalingResLayoutId();

        void onCreateScalingView(View view);
    }
}
