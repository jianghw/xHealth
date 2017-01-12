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
import com.kaurihealth.kaurihealth.manager_v.compile.LobRecordCompileFactory;
import com.kaurihealth.kaurihealth.manager_v.loading.LoadingErrorFactory;
import com.kaurihealth.kaurihealth.save_record_v.LobRecordCommonSaveActivity;
import com.kaurihealth.utilslib.constant.Global;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by mip on 2017/1/3.
 * Decribe: 实验室检查完成界面
 */

public class LobRecordCompileActivity extends BaseActivity {
    @Bind(R.id.tv_more)
    TextView mTvMore;
    @Bind(R.id.lay_content)
    LinearLayout mLayContent;
    @Bind(R.id.scrollView)
    ScrollView mScrollView;


    private LobRecordCompileFactory viewFactory;
    private LoadingErrorFactory errorFactory;
    private String mTitle;
    private PatientRecordDisplayDto displayDto;

    @Override
    protected int getActivityLayoutID() {
        return R.layout.activity_hospital_compile;
    }

    @Override
    protected void initPresenterAndView(Bundle savedInstanceState) {

        if (getBundle().getString(Global.Bundle.ITEM_Lob) != null) {//动态的设置title
            mTitle = getBundle().getString(Global.Bundle.ITEM_Lob);
        }

        initNewBackBtn(mTitle);
        mTvMore.setText(getString(R.string.swipe_tv_compile));
        if (LocalData.getLocalData().getCurrentPatientShip()) mTvMore.setVisibility(View.GONE);
    }

    @Override
    protected void initDelayedData() {
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void eventBusMain(HospitalRecordCompileEvent event) {
        displayDto = event.getPatientRecordDisplayDto();
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
            viewFactory = (LobRecordCompileFactory) ViewFactoryManager.createRecordLobTestViewFactory(this, mLayContent, mTitle);
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
        Bundle bundle = new Bundle();
        bundle.putString(Global.Bundle.ITEM_Lob, mTitle);
        skipToForResult(LobRecordCommonSaveActivity.class, bundle, Global.RequestCode.RECORD_COMPILE_LOB);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Global.RequestCode.RECORD_COMPILE_LOB && resultCode == RESULT_OK) {
            removeStickyEvent(HospitalRecordCompileEvent.class);
            EventBus.getDefault().postSticky(new DataToRefreshEvent());//启动刷新
                finishCur();
        }
    }
}
