package com.kaurihealth.kaurihealth.history_record_v;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.kaurihealth.datalib.local.LocalData;
import com.kaurihealth.datalib.response_bean.PatientRecordDisplayDto;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.kaurihealth.eventbus.DataToRefreshEvent;
import com.kaurihealth.kaurihealth.eventbus.HospitalRecordCompileEvent;
import com.kaurihealth.kaurihealth.manager_v.ViewFactoryManager;
import com.kaurihealth.kaurihealth.manager_v.compile.OutpatientRecordCompileFactory;
import com.kaurihealth.kaurihealth.manager_v.loading.LoadingErrorFactory;
import com.kaurihealth.kaurihealth.save_record_v.OutpatientRecordSaveActivity;
import com.kaurihealth.utilslib.constant.Global;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by jianghw .
 * <p/>
 * Describle: 编辑门诊记录
 */
public class OutpatientRecordCompileActivity extends BaseActivity {

    @Bind(R.id.tv_more)
    TextView mTvMore;
    @Bind(R.id.lay_content)
    LinearLayout mLayContent;
    @Bind(R.id.scrollView)
    ScrollView mScrollView;

    private OutpatientRecordCompileFactory viewFactory;
    private LoadingErrorFactory errorFactory;

    @Override
    protected int getActivityLayoutID() {
        return R.layout.activity_hospital_compile;
    }

    @Override
    protected void initPresenterAndView(Bundle savedInstanceState) {
        initNewBackBtn("门诊记录");
        mTvMore.setText(getString(R.string.swipe_tv_compile));
        if (LocalData.getLocalData().getCurrentPatientShip()) mTvMore.setVisibility(View.GONE);
    }

    @Override
    protected void initDelayedData() {
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void eventBusMain(HospitalRecordCompileEvent event) {
        PatientRecordDisplayDto displayDto = event.getPatientRecordDisplayDto();
        if (displayDto != null) {
            loadPatientRecordSucceed(displayDto);
        } else {
            loadPatientRecordError("数据出错,请退出当前页面");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (viewFactory != null) viewFactory.unbindView();
        if (errorFactory != null) errorFactory.unbindView();
        removeStickyEvent(HospitalRecordCompileEvent.class);
        EventBus.getDefault().unregister(this);
    }

    /**
     * 成功布局
     */
    public void loadPatientRecordSucceed(PatientRecordDisplayDto displayDto) {
        if (errorFactory != null) errorFactory.hiddenLayout();

        if (viewFactory == null) {
            viewFactory = (OutpatientRecordCompileFactory) ViewFactoryManager.createViewFactory(this, mLayContent);
        } else {
            viewFactory.showLayout();
        }
        if (viewFactory != null) {
            viewFactory.fillNewestData(displayDto, 0);
        }
        mScrollView.scrollTo(0, 0);
    }

    /**
     * 失败布局
     */
    public void loadPatientRecordError(String message) {
        if (viewFactory != null) viewFactory.hiddenLayout();

        if (errorFactory == null) {
            errorFactory = (LoadingErrorFactory) ViewFactoryManager.createErrorFactory(this, mLayContent);
        } else {
            errorFactory.showLayout();
        }

        if (errorFactory != null) {
            errorFactory.fillNewestData(message, 0);
        }
        mScrollView.scrollTo(0, 0);
    }

    /**
     * 编辑
     */
    @OnClick(R.id.tv_more)
    public void addMoreClick() {
        skipToForResult(OutpatientRecordSaveActivity.class, null, Global.RequestCode.RECORD_COMPILE_OUTPATIENT);
    }

    /**
     * 数据回调
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Global.RequestCode.RECORD_COMPILE_OUTPATIENT && resultCode == RESULT_OK) {
            EventBus.getDefault().postSticky(new DataToRefreshEvent());//主界面刷新
            removeStickyEvent(HospitalRecordCompileEvent.class);
            finishCur();
        }
    }
}
