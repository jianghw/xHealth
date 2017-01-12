package com.kaurihealth.kaurihealth.history_record_v;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.kaurihealth.datalib.local.LocalData;
import com.kaurihealth.datalib.response_bean.PatientRecordDisplayDto;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.kaurihealth.eventbus.AccessoryRecordAddEvent;
import com.kaurihealth.kaurihealth.eventbus.DataToRefreshEvent;
import com.kaurihealth.kaurihealth.eventbus.HospitalRecordAddEvent;
import com.kaurihealth.kaurihealth.eventbus.LobRecordAddEvent;
import com.kaurihealth.kaurihealth.eventbus.OutPatientRecordAddEvent;
import com.kaurihealth.kaurihealth.eventbus.RefreshEvent;
import com.kaurihealth.kaurihealth.manager_v.ViewFactoryManager;
import com.kaurihealth.kaurihealth.manager_v.history.ClinicalHistoryFactory;
import com.kaurihealth.kaurihealth.manager_v.history.LobTestHistoryFactory;
import com.kaurihealth.kaurihealth.manager_v.history.RecordAccessoryFactory;
import com.kaurihealth.kaurihealth.manager_v.history.RecordPhologyFactory;
import com.kaurihealth.kaurihealth.manager_v.loading.LoadingErrorFactory;
import com.kaurihealth.kaurihealth.save_record_v.AccessoryRecordCommonSaveActivity;
import com.kaurihealth.kaurihealth.save_record_v.HospitalRecordSaveActivity;
import com.kaurihealth.kaurihealth.save_record_v.LobRecordCommonSaveActivity;
import com.kaurihealth.kaurihealth.save_record_v.OutpatientRecordSaveActivity;
import com.kaurihealth.kaurihealth.tinker.SampleApplicationLike;
import com.kaurihealth.mvplib.history_record_p.ClinicalHistoryPresenter;
import com.kaurihealth.mvplib.history_record_p.IClinicalHistoryView;
import com.kaurihealth.utilslib.ColorUtils;
import com.kaurihealth.utilslib.constant.Global;
import com.kaurihealth.utilslib.widget.ScrollChildSwipeRefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by jianghw .
 * <p/>
 * Describle: 历史记录
 */
public class ClinicalHistoryActivity extends BaseActivity implements
        IClinicalHistoryView, PopupWindow.OnDismissListener, View.OnClickListener {
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

    /**
     * 临床历史记录
     */
    private ClinicalHistoryFactory viewFactory;
    /**
     * 辅助检查历史
     */
    private RecordAccessoryFactory accessoryFactory;
    /**
     * 病理
     */
    private RecordPhologyFactory phologyFactory;
    /**
     * 实验室检查
     */
    private LobTestHistoryFactory lobTestHistoryFactory;

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
        }
        initBackBtn(mCategory);
        mTvMore.setText(getString(R.string.more_add));

        mSwipeRefresh.setColorSchemeColors(
                ColorUtils.setSwipeRefreshColors(getApplicationContext())
        );
        mSwipeRefresh.setScrollUpChild(mScrollView);
        mSwipeRefresh.setSize(SwipeRefreshLayout.DEFAULT);
        mSwipeRefresh.setDistanceToTriggerSync(Global.Numerical.SWIPE_REFRESH);
        mSwipeRefresh.setOnRefreshListener(() -> mPresenter.onSubscribe());
    }

    private void initBackBtn(String category) {
        String title = "未知";
        switch (category) {
            case Global.Environment.CLINICAL:
                title = "临床诊疗历史记录";
                break;
            case Global.Environment.LOB:
                title = "实验室检查历史记录";
                break;
            case Global.Environment.AUXILIARY:
                title = "辅助检查历史记录";
                break;
            case Global.Environment.PATHOLOGY:
                title = "病理历史记录";
                break;
        }
        initNewBackBtn(title);

        if (LocalData.getLocalData().getCurrentPatientShip()) mTvMore.setVisibility(View.GONE);
    }

    @Override
    protected void initDelayedData() {
        mPresenter.onSubscribe();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeStickyEvent(RefreshEvent.class);
        removeStickyEvent(DataToRefreshEvent.class);
        EventBus.getDefault().unregister(this);

        if (viewFactory != null) viewFactory.unbindView();
        if (accessoryFactory != null) accessoryFactory.unbindView();
        if (phologyFactory != null) phologyFactory.unbindView();
        if (lobTestHistoryFactory != null) lobTestHistoryFactory.unbindView();
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

    /**
     * 成功布局
     */
    @Override
    public void loadAllPatientGenericRecordsSucceed(List<PatientRecordDisplayDto> recordDisplayDtoList) {
        if (errorFactory != null) errorFactory.hiddenLayout();

        if (viewFactory == null && accessoryFactory == null
                && phologyFactory == null && lobTestHistoryFactory == null) {
            //TODO 稍后会做判断
            switch (mCategory) {
                case Global.Environment.CLINICAL:
                    viewFactory = (ClinicalHistoryFactory) ViewFactoryManager.createViewFactory(this, mLayContent);
                    break;
                case Global.Environment.LOB:
                    lobTestHistoryFactory = (LobTestHistoryFactory) ViewFactoryManager.createRecordLobTestViewFactory(this, mLayContent,null);
                    break;
                case Global.Environment.AUXILIARY:
                    accessoryFactory = (RecordAccessoryFactory) ViewFactoryManager.createRecordAccessoryViewFactory(this, mLayContent, null);
                    break;
                case Global.Environment.PATHOLOGY:
                    phologyFactory = (RecordPhologyFactory) ViewFactoryManager.createPathologyViewFactory(this, mLayContent);
                    break;
            }
        } else {
            switch (mCategory) {
                case Global.Environment.CLINICAL:
                    if (viewFactory != null) viewFactory.showLayout();
                    break;
                case Global.Environment.LOB:
                    lobTestHistoryFactory.showLayout();
                    break;
                case Global.Environment.AUXILIARY:
                    if (accessoryFactory != null) accessoryFactory.showLayout();
                    break;
                case Global.Environment.PATHOLOGY:
                    if (phologyFactory != null) phologyFactory.showLayout();
                    break;
            }
        }
        mScrollView.scrollTo(0, 0);
    }



    /**
     * 失败布局
     */
    @Override
    public void loadAllPatientGenericRecordsError(String message) {
        if (viewFactory != null) viewFactory.hiddenLayout();
        if (lobTestHistoryFactory != null) lobTestHistoryFactory.hiddenLayout();
        if (accessoryFactory != null) accessoryFactory.hiddenLayout();
        if (phologyFactory != null) phologyFactory.hiddenLayout();

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
     * 门诊记录
     *
     * @param list
     */
    @Override
    public void setClinicalOutpatient(List<PatientRecordDisplayDto> list) {
        if (viewFactory != null) {
            viewFactory.fillNewestData(list, 1);
        }
    }

    /**
     * 住院记录
     *
     * @param list
     */
    @Override
    public void setHospitalRecords(List<PatientRecordDisplayDto> list) {
        if (viewFactory != null) {
            viewFactory.fillNewestData(list, 2);
        }
    }

    /**
     * 添加
     */
    @OnClick(R.id.tv_more)
    public void addMoreClick() {
    }

    /**
     * 辅助检查
     *
     * @param list
     */
    @Override
    public void setRecordAccessoryData(List<PatientRecordDisplayDto> list, int position) {
        if (accessoryFactory != null) {
            accessoryFactory.fillNewestData(list, position);
        }
    }

    /**
     * 病理
     *
     * @param list
     */
    @Override
    public void setPhologyData(List<PatientRecordDisplayDto> list) {
        if (phologyFactory != null) {
            phologyFactory.fillNewestData(list, 1);
        }
    }

    /**
     * 实验室检查
     *
     * @param list
     * @param position
     */
    @Override
    public void setLobTestData(List<PatientRecordDisplayDto> list, int position) {
        if (lobTestHistoryFactory != null) {
            lobTestHistoryFactory.fillNewestData(list, position);
        }
    }

    /**
     * 数据刷新事件
     */
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void eventBusMain(DataToRefreshEvent event) {
        mPresenter.onSubscribe();
    }

    /**
     * 添加点击事件  跳出弹窗
     * @param view
     */
    @OnClick(R.id.tv_more)
    public void onAddClick(View view){
        switch (mCategory) {
            case Global.Environment.CLINICAL:
                openPopupWindow(view);//临床弹框
                break;
            case Global.Environment.LOB:
                openPopupWindowLob(view);//实验室检查
                break;
            case Global.Environment.AUXILIARY:
                openPopupWindowAccessory(view);//辅助检查
                break;
            case Global.Environment.PATHOLOGY:
                openPopupWindowPathology(view);//病理
                break;
        }
    }


    private PopupWindow popupWindow;
    /**
     * 打开弹窗
     */
    public void openPopupWindow(View v) {
        //防止重复按按钮
        if (popupWindow != null && popupWindow.isShowing()) {
            return;
        }
        //设置PopupWindow的View
        View view = LayoutInflater.from(this).inflate(R.layout.clinical_dialog, null);
        popupWindow = new PopupWindow(view, RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        //设置背景,这个没什么效果，不添加会报错
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        //设置点击弹窗外隐藏自身
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        //设置动画
        popupWindow.setAnimationStyle(R.style.PopupWindow);
        //设置位置
        popupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 20);
        //设置消失监听
        popupWindow.setOnDismissListener(this);
        //设置PopupWindow的View点击事件
        setOnPopupViewClick(view);
        //设置背景色
        setBackgroundAlpha(0.5f);
    }

    private TextView tv_cancel_2,clinical_outpatient,clinical_remote;

    private void setOnPopupViewClick(View view) {
        tv_cancel_2 = (TextView) view.findViewById(R.id.tv_cancel);
        clinical_outpatient = (TextView) view.findViewById(R.id.clinical_outpatient);
        clinical_remote = (TextView) view.findViewById(R.id.clinical_remote);

        tv_cancel_2.setOnClickListener(this);
        clinical_outpatient.setOnClickListener(this);
        clinical_remote.setOnClickListener(this);
    }

    //设置屏幕背景透明效果
    public void setBackgroundAlpha(float alpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = alpha;
        if (alpha == 1) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//不移除该Flag的话,在有视频的页面上的视频会出现黑屏的bug
        } else {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//此行代码主要是解决在华为手机上半透明效果无效的bug
        }
        getWindow().setAttributes(lp);
    }

    @Override
    public void onDismiss() {
        setBackgroundAlpha(1);
    }

    public void openPopupWindowAccessory(View v) {
        //防止重复按按钮
        if (popupWindow != null && popupWindow.isShowing()) {
            return;
        }
        //设置PopupWindow的View
        View view = LayoutInflater.from(this).inflate(R.layout.clinical_supplement, null);
        popupWindow = new PopupWindow(view, RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        //设置背景,这个没什么效果，不添加会报错
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        //设置点击弹窗外隐藏自身
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        //设置动画
        popupWindow.setAnimationStyle(R.style.PopupWindow);
        //设置位置
        popupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 20);
        //设置消失监听
        popupWindow.setOnDismissListener(this);
        //设置PopupWindow的View点击事件
        setOnPopupAccessoryViewClick(view);
        //设置背景色
        setBackgroundAlpha(0.5f);
    }

    public void openPopupWindowLob(View v) {
        //防止重复按按钮
        if (popupWindow != null && popupWindow.isShowing()) {
            return;
        }
        //设置PopupWindow的View
        View view = LayoutInflater.from(this).inflate(R.layout.clinical_lobtest, null);
        popupWindow = new PopupWindow(view, RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        //设置背景,这个没什么效果，不添加会报错
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        //设置点击弹窗外隐藏自身
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        //设置动画
        popupWindow.setAnimationStyle(R.style.PopupWindow);
        //设置位置
        popupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 20);
        //设置消失监听
        popupWindow.setOnDismissListener(this);
        //设置PopupWindow的View点击事件
        setOnPopupLobViewClick(view);
        //设置背景色
        setBackgroundAlpha(0.5f);
    }

    private TextView tv_cancel_3,lob_one,lobtest_urine, lobtest_faeces,lobtest_special,
            lobtest_other;

    private void setOnPopupLobViewClick(View view) {
        tv_cancel_3 = (TextView) view.findViewById(R.id.tv_cancel);
        lob_one = (TextView) view.findViewById(R.id.lob_one);
        lobtest_urine = (TextView) view.findViewById(R.id.lobtest_urine);
        lobtest_faeces = (TextView) view.findViewById(R.id.lobtest_faeces);
        lobtest_special = (TextView) view.findViewById(R.id.lobtest_special);
        lobtest_other = (TextView) view.findViewById(R.id.lobtest_other);

        tv_cancel_3.setOnClickListener(this);
        lob_one.setOnClickListener(this);
        lobtest_urine.setOnClickListener(this);
        lobtest_faeces.setOnClickListener(this);
        lobtest_special.setOnClickListener(this);
        lobtest_other.setOnClickListener(this);
    }

    private TextView tv_cancel_1,supplement_one,supplement_blood_vessel,supplement_other;
    private void setOnPopupAccessoryViewClick(View view) {
        tv_cancel_1 = (TextView) view.findViewById(R.id.tv_cancel);
        supplement_one = (TextView) view.findViewById(R.id.supplement_one);
        supplement_blood_vessel = (TextView) view.findViewById(R.id.supplement_blood_vessel);
        supplement_other = (TextView) view.findViewById(R.id.supplement_other);

        tv_cancel_1.setOnClickListener(this);
        supplement_one.setOnClickListener(this);
        supplement_blood_vessel.setOnClickListener(this);
        supplement_other.setOnClickListener(this);

    }

    public void openPopupWindowPathology(View v) {
        //防止重复按按钮
        if (popupWindow != null && popupWindow.isShowing()) {
            return;
        }
        //设置PopupWindow的View
        View view = LayoutInflater.from(this).inflate(R.layout.clinical_pathology, null);
        popupWindow = new PopupWindow(view, RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        //设置背景,这个没什么效果，不添加会报错
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        //设置点击弹窗外隐藏自身
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        //设置动画
        popupWindow.setAnimationStyle(R.style.PopupWindow);
        //设置位置
        popupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 20);
        //设置消失监听
        popupWindow.setOnDismissListener(this);
        //设置PopupWindow的View点击事件
        setOnPopuppathologyViewClick(view);
        //设置背景色
        setBackgroundAlpha(0.5f);
    }

    private TextView tv_cancel,clinical_pathology;
    private void setOnPopuppathologyViewClick(View view) {
        tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
        clinical_pathology = (TextView) view.findViewById(R.id.clinical_pathology);

        tv_cancel.setOnClickListener(this);
        clinical_pathology.setOnClickListener(this);

    }

    /**
     * 弹出框的点击事件
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.clinical_outpatient://临床诊疗
                PatientRecordDisplayDto displayDto = new PatientRecordDisplayDto();
                displayDto.setPatientId(getPatientId());
                EventBus.getDefault().postSticky(new OutPatientRecordAddEvent(displayDto, clinical_outpatient.getText().toString().trim()));
                skipToForResult(OutpatientRecordSaveActivity.class, null, 0);
                popupWindow.dismiss();
            break;
            case R.id.clinical_remote://住院记录
                PatientRecordDisplayDto displayDto_6 = new PatientRecordDisplayDto();
                displayDto_6.setPatientId(getPatientId());
                EventBus.getDefault().postSticky(new HospitalRecordAddEvent(displayDto_6,clinical_remote.getText().toString()));
                skipToForResult(HospitalRecordSaveActivity.class,null,0);
                popupWindow.dismiss();
                break;
            case R.id.supplement_one://影像学检查
                Bundle bundle = new Bundle();
                bundle.putString("ICONOGRAPHYTEST", supplement_one.getText().toString());
                PatientRecordDisplayDto displayDto_1 = new PatientRecordDisplayDto();
                displayDto_1.setPatientId(getPatientId());
                EventBus.getDefault().postSticky(new AccessoryRecordAddEvent(displayDto_1, supplement_one.getText().toString()));
                skipToForResult(AccessoryRecordCommonSaveActivity.class, bundle, 0);
                popupWindow.dismiss();
                break;
            case R.id.supplement_blood_vessel://心血管检查
                Bundle bundle_5 = new Bundle();
                bundle_5.putString("ICONOGRAPHYTEST", supplement_blood_vessel.getText().toString());
                PatientRecordDisplayDto displayDto_2 = new PatientRecordDisplayDto();
                displayDto_2.setPatientId(getPatientId());
                EventBus.getDefault().postSticky(new AccessoryRecordAddEvent(displayDto_2, supplement_blood_vessel.getText().toString()));
                skipToForResult(AccessoryRecordCommonSaveActivity.class, bundle_5, 0);
                popupWindow.dismiss();
                break;
            case R.id.supplement_other://辅助检查其他检查
                Bundle bundle_6 = new Bundle();
                bundle_6.putString("ICONOGRAPHYTEST", supplement_other.getText().toString());
                PatientRecordDisplayDto displayDto_3 = new PatientRecordDisplayDto();
                displayDto_3.setPatientId(getPatientId());
                EventBus.getDefault().postSticky(new AccessoryRecordAddEvent(displayDto_3, supplement_other.getText().toString()));
                skipToForResult(AccessoryRecordCommonSaveActivity.class, bundle_6, 0);
                popupWindow.dismiss();
                break;
            case R.id.lob_one://常规血液检查
                Bundle bundle_1 = new Bundle();
                bundle_1.putString(Global.Bundle.ITEM_Lob, lob_one.getText().toString());
                PatientRecordDisplayDto recordDisplayDto = new PatientRecordDisplayDto();
                recordDisplayDto.setPatientId(getPatientId());
                EventBus.getDefault().postSticky(new LobRecordAddEvent(recordDisplayDto, lob_one.getText().toString()));
                skipToForResult(LobRecordCommonSaveActivity.class, bundle_1, 0);
                popupWindow.dismiss();
                break;
            case R.id.lobtest_urine://常规尿液检查
                Bundle bundle_7 = new Bundle();
                bundle_7.putString(Global.Bundle.ITEM_Lob, lobtest_urine.getText().toString());
                PatientRecordDisplayDto recordDisplayDto_1 = new PatientRecordDisplayDto();
                recordDisplayDto_1.setPatientId(getPatientId());
                EventBus.getDefault().postSticky(new LobRecordAddEvent(recordDisplayDto_1, lobtest_urine.getText().toString()));
                skipToForResult(LobRecordCommonSaveActivity.class, bundle_7, 0);
                popupWindow.dismiss();
                break;
            case R.id.lobtest_faeces://常规粪便检查
                Bundle bundle_2 = new Bundle();
                bundle_2.putString(Global.Bundle.ITEM_Lob, lobtest_faeces.getText().toString());
                PatientRecordDisplayDto recordDisplayDto_2 = new PatientRecordDisplayDto();
                recordDisplayDto_2.setPatientId(getPatientId());
                EventBus.getDefault().postSticky(new LobRecordAddEvent(recordDisplayDto_2, lobtest_faeces.getText().toString()));
                skipToForResult(LobRecordCommonSaveActivity.class, bundle_2, 0);
                popupWindow.dismiss();
                break;
            case R.id.lobtest_special://特殊检查
                Bundle bundle_3 = new Bundle();
                bundle_3.putString(Global.Bundle.ITEM_Lob, lobtest_special.getText().toString());
                PatientRecordDisplayDto recordDisplayDto_3 = new PatientRecordDisplayDto();
                recordDisplayDto_3.setPatientId(getPatientId());
                EventBus.getDefault().postSticky(new LobRecordAddEvent(recordDisplayDto_3, lobtest_special.getText().toString()));
                skipToForResult(LobRecordCommonSaveActivity.class, bundle_3, 0);
                popupWindow.dismiss();
                break;
            case R.id.lobtest_other://实验室检查其他
                Bundle bundle_4 = new Bundle();
                bundle_4.putString(Global.Bundle.ITEM_Lob, lobtest_other.getText().toString());
                PatientRecordDisplayDto recordDisplayDto_4 = new PatientRecordDisplayDto();
                recordDisplayDto_4.setPatientId(getPatientId());
                EventBus.getDefault().postSticky(new LobRecordAddEvent(recordDisplayDto_4, lobtest_other.getText().toString()));
                skipToForResult(LobRecordCommonSaveActivity.class, bundle_4, 0);
                popupWindow.dismiss();
                break;
            case R.id.clinical_pathology://病理
                Bundle bundle_8 = new Bundle();
                bundle_8.putString("ICONOGRAPHYTEST", clinical_pathology.getText().toString());
                PatientRecordDisplayDto displayDto_4 = new PatientRecordDisplayDto();
                displayDto_4.setPatientId(getPatientId());
                EventBus.getDefault().postSticky(new AccessoryRecordAddEvent(displayDto_4, clinical_pathology.getText().toString()));
                skipToForResult(AccessoryRecordCommonSaveActivity.class, bundle_8, 0);
                popupWindow.dismiss();
                break;
            case R.id.tv_cancel:
                popupWindow.dismiss();
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void eventBusMain(RefreshEvent event){
        mPresenter.onSubscribe();
    }

}
