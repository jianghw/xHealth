package com.kaurihealth.kaurihealth.patient_v.medical_records;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.kaurihealth.datalib.response_bean.DoctorPatientRelationshipBean;
import com.kaurihealth.datalib.response_bean.PatientRecordDisplayBean;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.adapter.MainFragmentAdapter;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.kaurihealth.eventbus.AddCommonMedicalRecordAddBeanEvent;
import com.kaurihealth.kaurihealth.eventbus.AddLobTestBeanEvent;
import com.kaurihealth.kaurihealth.eventbus.ClinicalMedcalFgtListEvent;
import com.kaurihealth.kaurihealth.eventbus.LobTestEvent;
import com.kaurihealth.kaurihealth.eventbus.MedicalRecordIdEvent;
import com.kaurihealth.kaurihealth.eventbus.OutpatientElectronicAddBeanEvent;
import com.kaurihealth.kaurihealth.eventbus.PathologyFragmentEvent;
import com.kaurihealth.kaurihealth.eventbus.SupplementTestEvent;
import com.kaurihealth.kaurihealth.patient_v.IGetMedicaHistoryRecord;
import com.kaurihealth.kaurihealth.patient_v.medical_records_only_read.AdmissionRecordOnlyReadActivity;
import com.kaurihealth.kaurihealth.patient_v.medical_records_only_read.CommonMedicalRecordToReadAcitivity;
import com.kaurihealth.kaurihealth.patient_v.medical_records_only_read.DischargeRecordOnlyReadActivity;
import com.kaurihealth.kaurihealth.patient_v.medical_records_only_read.LobTestOnlyReadActivtiy;
import com.kaurihealth.kaurihealth.patient_v.medical_records_only_read.NetWorkMedicalConsultationOnlyReadActivity;
import com.kaurihealth.kaurihealth.patient_v.medical_records_only_read.OutPatientPicturesOnlyReadActivity;
import com.kaurihealth.kaurihealth.patient_v.medical_records_only_read.OutpatientElectronicOnlyReadActivity;
import com.kaurihealth.kaurihealth.patient_v.medical_records_only_read.RemoteMedicalConsultationOnlyReadActivity;
import com.kaurihealth.kaurihealth.patient_v.medical_records_only_read.TreatMentRelatedRecordsOnlyreadActivity;
import com.kaurihealth.kaurihealth.tinker.SampleApplicationLike;
import com.kaurihealth.mvplib.patient_p.IMedicalRecordView;
import com.kaurihealth.mvplib.patient_p.MedicalRecordPresenter;
import com.kaurihealth.utilslib.OnClickUtils;
import com.kaurihealth.utilslib.constant.Global;
import com.kaurihealth.utilslib.dialog.DialogUtils;
import com.kaurihealth.utilslib.dialog.TimeDialog;
import com.kaurihealth.utilslib.log.LogUtils;
import com.kaurihealth.utilslib.widget.ScrollChildSwipeRefreshLayout;
import com.kaurihealth.utilslib.widget.UiTableBottom;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;


/**
 * 患者信息-->医疗记录
 */
public class MedicalRecordActivity extends BaseActivity implements IMedicalRecordView {

    @Inject
    MedicalRecordPresenter<IMedicalRecordView> mPresenter;

    @Bind(R.id.tv_more)
    TextView mTvMore;
    @Bind(R.id.ui_bottom)
    UiTableBottom mUiBottom;
    @Bind(R.id.vp_content_main)
    ViewPager mVpContentMain;
    @Bind(R.id.swipe_refresh)
    ScrollChildSwipeRefreshLayout mSwipeRefresh;

    private int curBottomItem = 0;
    List<Fragment> mFragmentList = new ArrayList<>();
    private int mPatientId;
    private DoctorPatientRelationshipBean mRelationshipBean;
    private boolean isAble;
    public static String SUPPLEMENTTEST = "SupplementTest";
    public static String PATHOLOGY = "Pathology";
    public static final int ADD = 11;

    List<PatientRecordDisplayBean> mClinicalList = new ArrayList<>();
    List<PatientRecordDisplayBean> mLobList = new ArrayList<>();
    List<PatientRecordDisplayBean> mAuxiliaryList = new ArrayList<>();
    List<PatientRecordDisplayBean> mPathologyList = new ArrayList<>();

    @Override
    protected int getActivityLayoutID() {
        return R.layout.activity_medical_record;
    }

    @Override
    protected void initPresenterAndView(Bundle savedInstanceState) {
        SampleApplicationLike.getApp().getComponent().inject(this);
        mPresenter.setPresenter(this);

        createCurrentFragment();
    }

    @Override
    protected void initDelayedData() {
        initNewBackBtn(getString(R.string.mr_tv_title));
        mTvMore.setText(getString(R.string.more_add));

        initViewPager();
        initBottomTable();
        mSwipeRefresh.setEnabled(false);
        mSwipeRefresh.setColorSchemeColors(
                ContextCompat.getColor(getApplicationContext(), R.color.welcome_bg_cl),
                ContextCompat.getColor(getApplicationContext(), R.color.welcome_bg_cl),
                ContextCompat.getColor(getApplicationContext(), R.color.welcome_bg_cl)
        );
        mSwipeRefresh.setScrollUpChild(mVpContentMain);
        mSwipeRefresh.setDistanceToTriggerSync(Global.Numerical.SWIPE_REFRESH);
        mSwipeRefresh.setOnRefreshListener(() -> mPresenter.loadingRemoteData(true));

        //注册事件
        EventBus.getDefault().register(this);
    }

    //给每个fragment添加一个刷新的接口
    IGetMedicaHistoryRecord medicaHistoryRecord = new IGetMedicaHistoryRecord() {
        @Override
        public void getDate() {
            mSwipeRefresh.setEnabled(true);
            mPresenter.loadingRemoteData(true);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!mFragmentList.isEmpty()) mFragmentList.clear();
        mPresenter.unSubscribe();
        //注销事件
        EventBus.getDefault().unregister(this);
    }

    //FragmentList 里面添加数据
    private void createCurrentFragment() {
        if (!mFragmentList.isEmpty()) mFragmentList.clear();

        ClinicalMedicalFragment medicalFragment = ClinicalMedicalFragment.newInstance();  //临床诊疗Fragment
        medicalFragment.setGetMedicaHistoryRecordListener(medicaHistoryRecord);
        SupplementaryTestFragment supplementaryTestFragment = SupplementaryTestFragment.newInstance(); //辅助检查Fragment
        supplementaryTestFragment.setGetMedicaHistoryRecordListener(medicaHistoryRecord);
        LobTestFragment lobTestFragment = LobTestFragment.newInstance();  //实验室检查Fragment
        lobTestFragment.setGetMedicaHistoryRecordListener(medicaHistoryRecord);
        PathologyFragment pathologyFragment = PathologyFragment.newInstance(); //病理Fragment
        pathologyFragment.setGetMedicaHistoryRecordListener(medicaHistoryRecord);
        mFragmentList.add(medicalFragment);
        mFragmentList.add(supplementaryTestFragment);
        mFragmentList.add(lobTestFragment);
        mFragmentList.add(pathologyFragment);
    }

    private void initViewPager() {
        MainFragmentAdapter mainFragmentAdapter = new MainFragmentAdapter(getSupportFragmentManager(), mFragmentList);
        mVpContentMain.setAdapter(mainFragmentAdapter);
        mVpContentMain.setOffscreenPageLimit(mFragmentList.size() - 1);//设置预加载
        mVpContentMain.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                curBottomItem = position;
                mUiBottom.selectTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    //医疗记录顶部四个按钮
    private void initBottomTable() {
        HashMap<Integer, Integer[]> hashMap = new HashMap<>();
        hashMap.put(0, new Integer[]{R.mipmap.clinica_dj, R.mipmap.clinica_ct, R.string.mr_table_clinica});
        hashMap.put(1, new Integer[]{R.mipmap.supplementary_dj, R.mipmap.supplementary_ct, R.string.mr_table_supplementary});
        hashMap.put(2, new Integer[]{R.mipmap.lab_dj, R.mipmap.lab_ct, R.string.mr_table_lab});
        hashMap.put(3, new Integer[]{R.mipmap.pathology_dj, R.mipmap.pathology_ct, R.string.mr_table_pathology});

        mUiBottom.setUiViewPager(index -> mVpContentMain.setCurrentItem(index), curBottomItem, hashMap);
    }

    @OnClick(R.id.tv_more)
    public void addOnClick() {//添加新的医疗记录
        DialogUtils.showMedicalDialog(this, new TimeDialog.TimeSelect() {
            @Override
            public void selectTime(String date, String time) {
                OnClickUtils.onNoDoubleClick();
                mPresenter.currentlySelectedByDialog(date, time);
            }
        });
    }

    /**
     * ----------------------------订阅事件----------------------------------
     * 加载弹出框 显示项目
     **/
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void eventBusMain(MedicalRecordIdEvent event) {
        mRelationshipBean = event.getPatientRelationshipBean();
        mPatientId= event.getPatientId();
        this.isAble = event.getIsAble();

        offCurActivity(isAble);
        mPresenter.onSubscribe();
    }


    /**
     * 设置当前界面为不可编辑状态
     */
    private void offCurActivity(boolean isAble) {
        if (!isAble) {
            mTvMore.setVisibility(View.GONE);
        } else {
            mTvMore.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getPatientId() {
        return mPatientId;
    }

    /**
     * ----------------------------继承基础mvpView方法-----------------------------------
     */
    @Override
    public void switchPageUI(String className) {
        switch (className) {
            case Global.Jump.OutpatientElectronicActivity: //门诊记录电子病历
                skipToForResult(OutpatientElectronicActivity.class, null, ADD);
                break;
            case Global.Jump.AddCommonMedicalRecordActivity:
                skipToForResult(AddCommonMedicalRecordActivity.class, null, ADD);
                break;
            case Global.Jump.AddAndEditLobTestActivity:
                skipToForResult(AddAndEditLobTestActivity.class, null, ADD);
                break;
            case Global.Jump.OutpatientPicturesActivity://门诊记录图片存档
                skipToForResult(OutpatientPicturesActivity.class, null, ADD);
                break;
            case Global.Jump.RemoteMedicalConsultationActivity://远程医疗咨询
                skipToForResult(RemoteMedicalConsultationActivity.class, null, ADD);
                break;
            case Global.Jump.NetworkMedicalConsultationActivity://网络医疗咨询
                skipToForResult(NetworkMedicalConsultationActivity.class, null, ADD);
                break;
            case Global.Jump.AdmissionRecordActivity://入院记录
                skipToForResult(AdmissionRecordActivity.class, null, ADD);
                break;
            case Global.Jump.TreatmentRelatedRecordsActivity://院内治疗相关记录
                skipToForResult(TreatmentRelatedRecordsActivity.class, null, ADD);
                break;
            case Global.Jump.DischargeRecordActivity: //出院记录
                skipToForResult(DischargeRecordActivity.class, null, ADD);
                break;
            default:
                break;
        }
    }


    /**
     * 跳转到只读activity
     */
    public void switchPageUI(String className,String nullString) {
        switch (className) {
            case Global.Jump.CommonMedicalRecordToReadActivity:
                skipTo(CommonMedicalRecordToReadAcitivity.class);//只读辅助检查及病理类
                break;
            case Global.Jump.LobTestOnlyReadAcivity:
                skipTo(LobTestOnlyReadActivtiy.class);//只读实验室检查
                break;
            case Global.Jump.OutpatientElectronicOnlyReadActivity:
                skipTo(OutpatientElectronicOnlyReadActivity.class);//电子门诊病例 只可读
                break;
            case Global.Jump.OutPatientPicturesOnlyReadActivity:
                skipTo(OutPatientPicturesOnlyReadActivity.class);//门诊记录图片存档 只可读
                break;
            case Global.Jump.RemoteMedicalConsultationOnlyReadActivity:
                skipTo(RemoteMedicalConsultationOnlyReadActivity.class);//远程医疗 只可读
                break;
            case Global.Jump.NetWorkMedicalConsultationOnlyReadActivity:
                skipTo(NetWorkMedicalConsultationOnlyReadActivity.class);//网络医疗 只可读
                break;
            case Global.Jump.AdmissionRecordOnlyReadActivity:
                skipTo(AdmissionRecordOnlyReadActivity.class);//入院记录 只可读
                break;
            case Global.Jump.TreatMentRelatedRecordsOnlyreadActivity:
                skipTo(TreatMentRelatedRecordsOnlyreadActivity.class);//院内治疗记录 只可读
                break;
            case Global.Jump.DischargeRecordOnlyReadActivity:
                skipTo(DischargeRecordOnlyReadActivity.class);//出院记录 只可读
                break;
            default:
                break;
        }
    }


    //统一地放送eventbus
    @Override
    public void sendEventBusByString(String category, String subject) {
        switch (subject) {
            case "门诊记录电子病历":
                EventBus.getDefault().postSticky(new OutpatientElectronicAddBeanEvent(getShipBean(), category, subject));
                switchPageUI(Global.Jump.OutpatientElectronicActivity);
                break;
            case "门诊记录图片存档":
                EventBus.getDefault().postSticky(new OutpatientElectronicAddBeanEvent(getShipBean(), category, subject));
                switchPageUI(Global.Jump.OutpatientPicturesActivity);
                break;
            case "远程医疗咨询":
                EventBus.getDefault().postSticky(new OutpatientElectronicAddBeanEvent(getShipBean(), category, subject));
                switchPageUI(Global.Jump.RemoteMedicalConsultationActivity);
                break;
            case "网络医疗咨询":
                EventBus.getDefault().postSticky(new OutpatientElectronicAddBeanEvent(getShipBean(), category, subject));
                switchPageUI(Global.Jump.NetworkMedicalConsultationActivity);
                break;
            case "入院记录":
                EventBus.getDefault().postSticky(new OutpatientElectronicAddBeanEvent(getShipBean(), category, subject));
                switchPageUI(Global.Jump.AdmissionRecordActivity);
                break;
            case "院内治疗相关记录":
                EventBus.getDefault().postSticky(new OutpatientElectronicAddBeanEvent(getShipBean(), category, subject));
                switchPageUI(Global.Jump.TreatmentRelatedRecordsActivity);
                break;
            case "出院记录":
                EventBus.getDefault().postSticky(new OutpatientElectronicAddBeanEvent(getShipBean(), category, subject));
                switchPageUI(Global.Jump.DischargeRecordActivity);
                break;
            case "影像学检查":
                EventBus.getDefault().postSticky(new AddCommonMedicalRecordAddBeanEvent(getShipBean(), category, subject, SUPPLEMENTTEST));
                switchPageUI(Global.Jump.AddCommonMedicalRecordActivity);
                break;
            case "心血管系统相关检查":
                EventBus.getDefault().postSticky(new AddCommonMedicalRecordAddBeanEvent(getShipBean(), category, subject, SUPPLEMENTTEST));
                switchPageUI(Global.Jump.AddCommonMedicalRecordActivity);
                break;
            case "其它检查":
                EventBus.getDefault().postSticky(new AddCommonMedicalRecordAddBeanEvent(getShipBean(), category, subject, SUPPLEMENTTEST));
                switchPageUI(Global.Jump.AddCommonMedicalRecordActivity);
                break;
            case "病理":
                EventBus.getDefault().postSticky(new AddCommonMedicalRecordAddBeanEvent(getShipBean(), category, subject, PATHOLOGY));
                switchPageUI(Global.Jump.AddCommonMedicalRecordActivity);
                break;
            case "常规血液学检查":
                EventBus.getDefault().postSticky(new AddLobTestBeanEvent(getShipBean(), category, subject));
                switchPageUI(Global.Jump.AddAndEditLobTestActivity);
                break;
            case "常规尿液检查":
                EventBus.getDefault().postSticky(new AddLobTestBeanEvent(getShipBean(), category, subject));
                switchPageUI(Global.Jump.AddAndEditLobTestActivity);
                break;
            case "常规粪便检查":
                EventBus.getDefault().postSticky(new AddLobTestBeanEvent(getShipBean(), category, subject));
                switchPageUI(Global.Jump.AddAndEditLobTestActivity);
                break;
            case "特殊检查":
                EventBus.getDefault().postSticky(new AddLobTestBeanEvent(getShipBean(), category, subject));
                switchPageUI(Global.Jump.AddAndEditLobTestActivity);
                break;
            case "其它":
                EventBus.getDefault().postSticky(new AddLobTestBeanEvent(getShipBean(), category, subject));
                switchPageUI(Global.Jump.AddAndEditLobTestActivity);
                break;
            default:
                break;
        }
    }

    @Override
    public DoctorPatientRelationshipBean getShipBean() {
        return mRelationshipBean;
    }

    //临床
    @Override
    public void callBackClinical(List<PatientRecordDisplayBean> beanList) {
//        if (!mClinicalList.isEmpty()) mClinicalList.clear();
//        mClinicalList.addAll(beanList);
        EventBus.getDefault().postSticky(new ClinicalMedcalFgtListEvent(beanList));
        mSwipeRefresh.setEnabled(false);
    }

    //实验室
    @Override
    public void callBackLob(List<PatientRecordDisplayBean> beanList) {
//        if (!mLobList.isEmpty()) mLobList.clear();
//        mLobList.addAll(beanList);
        EventBus.getDefault().postSticky(new LobTestEvent(beanList));
        mSwipeRefresh.setEnabled(false);
    }

    //辅助
    @Override
    public void callBackAuxiliary(List<PatientRecordDisplayBean> beanList) {
//        if (!mAuxiliaryList.isEmpty()) mAuxiliaryList.clear();
//        mAuxiliaryList.addAll(beanList);
        EventBus.getDefault().postSticky(new SupplementTestEvent(beanList));
        mSwipeRefresh.setEnabled(false);
    }

    //病理
    @Override
    public void callBackPathology(List<PatientRecordDisplayBean> beanList) {
//        if (!mPathologyList.isEmpty()) mPathologyList.clear();
//        mPathologyList.addAll(beanList);
        EventBus.getDefault().postSticky(new PathologyFragmentEvent(beanList));
        mSwipeRefresh.setEnabled(false);
    }

    /**
     * 刷新数据指示器
     */
    public void loadingIndicator(boolean flag) {
        if (mSwipeRefresh == null) return;
        mSwipeRefresh.post(() -> mSwipeRefresh.setRefreshing(flag));
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtils.d(requestCode+"==============================="+resultCode+data);
        switch (requestCode) {
            case ADD:
                if (resultCode == RESULT_OK) {
                    setResult(RESULT_OK, data);
                }
        }
    }
}
