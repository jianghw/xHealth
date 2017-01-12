package com.kaurihealth.kaurihealth.add_record_v;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.kaurihealth.datalib.response_bean.PatientRecordDisplayDto;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.kaurihealth.manager_v.loading.LoadingErrorFactory;
import com.kaurihealth.kaurihealth.manager_v.record.MedicalRecordFactory;
import com.kaurihealth.kaurihealth.manager_v.ViewFactoryManager;
import com.kaurihealth.kaurihealth.tinker.SampleApplicationLike;
import com.kaurihealth.mvplib.history_record_p.ClinicalHistoryPresenter;
import com.kaurihealth.mvplib.history_record_p.IClinicalHistoryView;
import com.kaurihealth.utilslib.constant.Global;
import com.kaurihealth.utilslib.widget.ScrollChildSwipeRefreshLayout;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * Created by jianghw .
 * <p/>
 * Describle: 新增加门诊记录
 */
public class AddOutpatientActivity extends BaseActivity implements IClinicalHistoryView {
    @Inject
    ClinicalHistoryPresenter<IClinicalHistoryView> mPresenter;

    @Bind(R.id.tv_more)
    TextView mTvMore;
    @Bind(R.id.lay_content)
    LinearLayout mLayContent;
    @Bind(R.id.scrollView)
    ScrollView mScrollView;
    @Bind(R.id.swipe_refresh)
    ScrollChildSwipeRefreshLayout mSwipeRefresh;

    private int mPatientId;
    private String mCategory;
    private String mSubject;

    private MedicalRecordFactory viewFactory;
    private LoadingErrorFactory errorFactory;

    @Override
    protected int getActivityLayoutID() {
        return R.layout.activity_clinical_history;
    }

    @Override
    protected void initPresenterAndView(Bundle savedInstanceState) {
        SampleApplicationLike.getApp().getComponent().inject(this);
        mPresenter.setPresenter(this);

        Bundle bundle = getBundle();
        if (bundle != null) {
            mPatientId = bundle.getInt(Global.Bundle.CONVER_ITEM_PATIENT);
            mCategory = bundle.getString(Global.Bundle.BUNDLE_CATEGORY);
            mSubject = bundle.getString(Global.Bundle.BUNDLE_SUBJECT);
        }
        initBackBtn(mCategory);
    }

    private void initBackBtn(String category) {
        String title = "未知";
        switch (category) {
            case "0":
                title = "临床诊疗历史记录";
                break;
            case "1":
                title = "实验室检查历史记录";
                break;
            case "2":
                title = "辅助检查历史记录";
                break;
            case "3":
                title = "病理历史记录";
                break;
        }
        initNewBackBtn(title);
    }

    @Override
    protected void initDelayedData() {
        mPresenter.onSubscribe();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (viewFactory != null) viewFactory.unbindView();
        if (errorFactory != null) errorFactory.unbindView();
        mPresenter.unSubscribe();
    }

    @Override
    public void switchPageUI(String className) {
//TODO 暂时无
    }

    @Override
    public int getPatientId() {
        return mPatientId;
    }

    @Override
    public String getCategory() {
        return mCategory;
    }

    @Override
    public void loadingIndicator(boolean flag) {
        if (mSwipeRefresh == null) return;
        mSwipeRefresh.post(() -> mSwipeRefresh.setRefreshing(flag));
    }

    @Override
    public void loadAllPatientGenericRecordsSucceed(List<PatientRecordDisplayDto> recordDisplayDtoList) {
        if (recordDisplayDtoList == null || recordDisplayDtoList.isEmpty()) {
            loadAllPatientGenericRecordsError(null);
            return;
        }

//        if (errorFactory != null) errorFactory.hiddenLayout();
//
//        if (viewFactory == null) {
//            viewFactory = (MedicalRecordFactory) ViewFactoryManager.createViewFactory(this, mLayContent);
//        } else {
//            viewFactory.showLayout();
//        }
//
//        if (viewFactory != null) {
//            viewFactory.fillNewestData(recordDisplayDtoList);
//        }
//        mScrollView.scrollTo(0, 0);
    }

    @Override
    public void loadAllPatientGenericRecordsError(String message) {
        if (viewFactory != null) viewFactory.hiddenLayout();

        if (errorFactory == null) {
            errorFactory = (LoadingErrorFactory) ViewFactoryManager.createErrorFactory(this, mLayContent);
        } else {
            errorFactory.showLayout();
        }

        if (errorFactory != null) {
            errorFactory.fillNewestData(message,0);
        }
        mScrollView.scrollTo(0, 0);
    }

    @Override
    public void setClinicalOutpatient(List<PatientRecordDisplayDto> list) {

    }

    @Override
    public void setHospitalRecords(List<PatientRecordDisplayDto> list) {

    }

    @Override
    public void setRecordAccessoryData(List<PatientRecordDisplayDto> list, int position) {

    }

    @Override
    public void setPhologyData(List<PatientRecordDisplayDto> list) {

    }

    @Override
    public void setLobTestData(List<PatientRecordDisplayDto> list, int position) {

    }
}
