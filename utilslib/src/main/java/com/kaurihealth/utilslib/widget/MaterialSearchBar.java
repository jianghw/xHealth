package com.kaurihealth.utilslib.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.TouchDelegate;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kaurihealth.utilslib.R;

/**
 * Created by jianghw on 2016/12/14.
 * <p/>
 * Describe: 动态搜索栏
 */

public class MaterialSearchBar extends RelativeLayout implements View.OnClickListener,
        Animation.AnimationListener, View.OnFocusChangeListener, TextView.OnEditorActionListener {

    private int mSearchIconRes;
    private String mHintContent;
    private boolean mSpeechMode;
    private int mHintColor;
    private int mTextColor;

    private ImageView imgBack;
    private LinearLayout inputContainer;
    private ImageView imgClear;
    private EditText searchEdit;
    private TextView searchTv;
    private RelativeLayout rootLayout;
    /**
     * 是否可搜索
     */
    private boolean searchEnabled;
    private OnSearchActionListener onSearchActionListener;

    public MaterialSearchBar(Context context) {
        this(context, null);
    }

    public MaterialSearchBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MaterialSearchBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initSearchView();
        initAttributeSet(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MaterialSearchBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initSearchView();
        initAttributeSet(attrs);
    }

    private void initSearchView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_search_bar, this, true);
        rootLayout = (RelativeLayout) view.findViewById(R.id.root);
        imgBack = (ImageView) view.findViewById(R.id.img_back);
        inputContainer = (LinearLayout) view.findViewById(R.id.inputContainer);
        imgClear = (ImageView) view.findViewById(R.id.img_clear);
        searchEdit = (EditText) view.findViewById(R.id.editText);
        searchTv = (TextView) view.findViewById(R.id.tv_search);

        effectOfClick(80, searchTv);
        effectOfClick(100, imgClear);
        effectOfClick(60, imgBack);

        imgBack.setOnClickListener(this);
        imgClear.setOnClickListener(this);
        rootLayout.setOnClickListener(this);

        rootLayout.setVisibility(VISIBLE);
        inputContainer.setVisibility(GONE);

        searchEdit.setOnFocusChangeListener(this);
        searchEdit.setOnEditorActionListener(this);
        searchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (searchEdit != null && searchEdit.getText().toString().trim().length() == 0) {
                    if (listenerExists() && inputContainer.getVisibility() == VISIBLE)
                        onSearchActionListener.onSearchDeleteBack();
                }
            }
        });
    }

    private void effectOfClick(int value, View textView) {
        Rect searchRect = new Rect();
        searchRect.left -= value;
        searchRect.top -= value;
        searchRect.right += value;
        searchRect.bottom += value;
        TouchDelegate touchDelegate = new TouchDelegate(searchRect, textView);
        if (View.class.isInstance(textView.getParent()))
            ((View) textView.getParent()).setTouchDelegate(touchDelegate);
    }

    private void initAttributeSet(AttributeSet attrs) {
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.MaterialSearchBar);
        mSearchIconRes = array.getResourceId(R.styleable.MaterialSearchBar_searchIconDrawable, -1);
        mHintContent = array.getString(R.styleable.MaterialSearchBar_hint);
        mSpeechMode = array.getBoolean(R.styleable.MaterialSearchBar_speechMode, false);
        mHintColor = array.getColor(R.styleable.MaterialSearchBar_hintColor, -1);
        mTextColor = array.getColor(R.styleable.MaterialSearchBar_textColor, -1);
        array.recycle();

        defaultSearchConfiguration();
    }

    private void defaultSearchConfiguration() {
        if (mSearchIconRes < 0) {
            mSearchIconRes = R.drawable.searchback_icon;
        }
        setSpeechMode(mSpeechMode);
        if (mHintContent != null) {
            searchEdit.setHint(mHintContent);
        }
        setupTextColors();
    }

    /**
     * true--查询图标
     * false--回退图标
     */
    public void setSpeechMode(boolean speechMode) {
        this.mSpeechMode = speechMode;
        imgBack.setImageResource(speechMode ? R.drawable.searchback_icon : mSearchIconRes);
        imgBack.setClickable(!speechMode);
    }

    private void setupTextColors() {
        if (mHintColor != -1)
            searchEdit.setHintTextColor(ContextCompat.getColor(getContext(), mHintColor));
        if (mTextColor != -1)
            searchEdit.setTextColor(ContextCompat.getColor(getContext(), mTextColor));
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.root) {
            enableSearch();
        } else if (id == R.id.img_back) {
            disableSearch();
        } else if (id == R.id.img_clear) {
            searchEdit.setText("");
        }
    }

    /**
     * 开始搜索
     */
    public void enableSearch() {
        if (searchTv.getVisibility() != VISIBLE) return;
        searchEnabled = true;

        Animation left_in = AnimationUtils.loadAnimation(getContext(), R.anim.search_in_left);
        Animation left_out = AnimationUtils.loadAnimation(getContext(), R.anim.search_out_left);
        left_in.setAnimationListener(this);
        inputContainer.setVisibility(VISIBLE);
        inputContainer.startAnimation(left_in);
        searchTv.startAnimation(left_out);
        if (listenerExists()) onSearchActionListener.onSearchStart();
    }

    /**
     * 退出搜索
     */
    public void disableSearch() {
        searchEnabled = false;
        Animation right_out = AnimationUtils.loadAnimation(getContext(), R.anim.search_out_right);
        Animation right_in = AnimationUtils.loadAnimation(getContext(), R.anim.search_in_right);
        right_out.setAnimationListener(this);
        searchTv.setVisibility(VISIBLE);
        inputContainer.startAnimation(right_out);
        searchTv.startAnimation(right_in);

        if (listenerExists()) onSearchActionListener.onSearchCompleteBack();
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if (!searchEnabled) {
            inputContainer.setVisibility(GONE);
            searchEdit.setText("");
        } else {
            searchTv.setVisibility(GONE);
            searchEdit.requestFocus();
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (hasFocus) {
            inputMethodManager.showSoftInput(v, InputMethodManager.SHOW_IMPLICIT);
        } else {
            inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

    /**
     * 搜索
     */
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (listenerExists()) onSearchActionListener.onSearchConfirmed(searchEdit.getText());
        return true;
    }

    public CharSequence getEditorTextContent() {
        return searchEdit.getText();
    }

    public void setOnSearchActionListener(OnSearchActionListener onSearchActionListener) {
        this.onSearchActionListener = onSearchActionListener;
    }

    private boolean listenerExists() {
        return onSearchActionListener != null;
    }

    public interface OnSearchActionListener {
        void onSearchConfirmed(CharSequence text);

        void onSearchStart();

        void onSearchCompleteBack();

        void onSearchDeleteBack();
    }
}
