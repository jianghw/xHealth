package com.kaurihealth.kaurihealth.manager_v.loading;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.utilslib.controller.AbstractViewController;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jianghw on 2016/12/15.
 * <p/>
 * Describe: 失败页面
 */

class LoadingErrorView extends AbstractViewController<String> implements View.OnClickListener {

    @Bind(R.id.tv_error_note)
    TextView mTvErrorNote;
    @Bind(R.id.lay_error)
    RelativeLayout mLayError;

    LoadingErrorView(Context context) {
        super(context);
    }

    @Override
    protected int getResLayoutId() {
        return R.layout.include_loading_error;
    }

    @Override
    protected void onCreateView(View view) {
        ButterKnife.bind(this, view);
    }

    @Override
    protected void bindViewData(String data) {
        if (data != null) {
            mTvErrorNote.setText(data);
        } else {
            defaultViewData();
        }
    }

    @Override
    protected void defaultViewData() {
        mTvErrorNote.setText("暂无相关数据,请下拉刷新");
    }

    @Override
    protected void unbindView() {
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.tv_error_note})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_error_note:

                break;
            default:
                break;
        }
    }

    @Override
    protected void showLayout() {
        mLayError.setVisibility(View.VISIBLE);
    }

    @Override
    protected void hiddenLayout() {
        mLayError.setVisibility(View.GONE);
    }
}
