package com.kaurihealth.kaurihealth.common.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.commonlibrary.widget.util.LoadingUtil;
import com.example.commonlibrary.widget.util.LogFactory;
import com.example.commonlibrary.widget.util.LogUtilInterface;
import com.kaurihealth.datalib.request_bean.bean.PatientRecordDisplayBean;
import com.kaurihealth.datalib.service.IPatientRecordService;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.common.Interface.IGetMedicaHistoryRecord;
import com.kaurihealth.kaurihealth.adapter.MainFragmentAdapter;
import com.kaurihealth.kaurihealth.common.fragment.ClinicalDiagnosisAndTreatmentFragment;
import com.kaurihealth.kaurihealth.common.util.FragmentControl;
import com.kaurihealth.kaurihealth.home.fragment.LabTestFragment;
import com.kaurihealth.kaurihealth.home.fragment.PathologyFragment;
import com.kaurihealth.kaurihealth.home.fragment.SupplementaryTestFragment;
import com.kaurihealth.kaurihealth.home.util.BundleFactory;
import com.kaurihealth.kaurihealth.home.util.Interface.IBundleFactory;
import com.kaurihealth.kaurihealth.home.util.Interface.IGetBundleHealthyRecord;
import com.kaurihealth.kaurihealth.services.IServiceFactory;
import com.kaurihealth.kaurihealth.services.ServiceFactory;
import com.kaurihealth.kaurihealth.util.Enums.LoadingStatu;
import com.kaurihealth.kaurihealth.util.ExitApplication;
import com.kaurihealth.kaurihealth.util.Interface.IStrartActivity;
import com.kaurihealth.kaurihealth.util.Url;
import com.youyou.zllibrary.Dialog.TimeDialog;
import com.youyou.zllibrary.util.CommonFragmentActivity;
import com.youyou.zllibrary.util.FragmentData;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/**
 * 版权:    张磊
 * 作者:    张磊
 * 描述：
 * 修订日期:
 */

/**
 * 医疗记录
 */
public class MedicaHistoryActivity extends CommonFragmentActivity {
    public static final int Add = 9;
    public static final int Update = 10;
    /**
     * 时间选择框内的选项
     */
    private String[] left;
    //临床诊疗
    private String[] right1;
    //辅助检查
    private String[] right2;
    //实验室检查
    private String[] right3;
    //病理
    private String[] right4;
    @Bind(R.id.vp_content_main)
    ViewPager medicalViewpager;
    //临床治疗
    @Bind(R.id.rbtn_clinical_treatment_medicalhistory)
    RadioButton rbtnClinicalTreatmentMedicalhistory;
    //辅助检查
    @Bind(R.id.rbt_assist_check_medicalhistory)
    RadioButton rbtAssistCheckMedicalhistory;
    //实验室检查
    @Bind(R.id.btn_labcheck_medicalhistory)
    RadioButton btnLabcheckMedicalhistory;
    //病理
    @Bind(R.id.rbtn_pathologycheck_medicalhistory)
    RadioButton rbtnPathologycheckMedicalhistory;
    @Bind(R.id.tv_operate)
    TextView tvStore;

    @Bind(R.id.medica_swipe)
    SwipeRefreshLayout medica_swipe;
    private int patientId;
    private List<Fragment> fragmentList;
    private TimeDialog popupDialog;
    private Bundle bundle;
    private List<PatientRecordDisplayBean> clinicalDiagnosisAndTreatmentList;
    private List<PatientRecordDisplayBean> supplementaryTestList;
    private List<PatientRecordDisplayBean> labTestList;
    private List<PatientRecordDisplayBean> pathologyList;
    private String[][] array;
    private StrartActivity skipToUtil;
    private IServiceFactory serviceFactory;
    private IGetBundleHealthyRecord iGetBundleHealthyRecord;
    private String category;
    private String subject;
    private LoadingUtil loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.medica_history);
        ButterKnife.bind(this);
        init();
        ExitApplication.getInstance().addActivity(this);
    }
    //给每个fragment添加一个刷新的接口
    IGetMedicaHistoryRecord medicaHistoryRecord = new IGetMedicaHistoryRecord() {
        @Override
        public void getDate() {
            getData();
        }
    };
    @Override
    public void init() {
        super.init();
        skipToUtil = new StrartActivity();
        bundle = getBundle();
        IBundleFactory iBundleFactory = new BundleFactory();
        iGetBundleHealthyRecord = iBundleFactory.getIGetBundleHealthyRecord(bundle);
        left = getResources().getStringArray(R.array.medicalrecod);
        right1 = getResources().getStringArray(R.array.ClinicalDiagnosisAndTreatment);
        right2 = getResources().getStringArray(R.array.SupplementaryTest);
        right3 = getResources().getStringArray(R.array.LabTest);
        right4 = getResources().getStringArray(R.array.Pathology);
        array = new String[][]{
                right1, right2, right3, right4
        };
        setBack(R.id.iv_back);
        patientId = iGetBundleHealthyRecord.getPatientId();
        ClinicalDiagnosisAndTreatmentFragment ClinicalTreatment = new ClinicalDiagnosisAndTreatmentFragment();//1
        ClinicalTreatment.setiStrartActivity(skipToUtil);
        ClinicalTreatment.setGetMedicaHistoryRecordListener(medicaHistoryRecord);
        SupplementaryTestFragment supplementaryTestFragmentFragment = new SupplementaryTestFragment();//1
        supplementaryTestFragmentFragment.setiStrartActivity(skipToUtil);
        supplementaryTestFragmentFragment.setGetMedicaHistoryRecordListener(medicaHistoryRecord);
        LabTestFragment labcheckFragment = new LabTestFragment();//3
        labcheckFragment.setiStrartActivity(skipToUtil);
        labcheckFragment.setGetMedicaHistoryRecordListener(medicaHistoryRecord);
        PathologyFragment pathologycheckFragment = new PathologyFragment();//4
        pathologycheckFragment.setiStrartActivity(skipToUtil);
        pathologycheckFragment.setGetMedicaHistoryRecordListener(medicaHistoryRecord);
        fragmentList = new ArrayList<>();
        fragmentList.add(ClinicalTreatment);
        fragmentList.add(supplementaryTestFragmentFragment);
        fragmentList.add(labcheckFragment);
        fragmentList.add(pathologycheckFragment);
        offCurActivity(bundle);

        medica_swipe.setSize(SwipeRefreshLayout.DEFAULT);
        medica_swipe.setColorSchemeResources(R.color.holo_blue_light_new
                , R.color.holo_blue_light_new,
                R.color.holo_blue_light_new, R.color.holo_blue_light_new);
        medica_swipe.setProgressBackgroundColor(R.color.linelogin);
        medica_swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();

            }
        });

        medica_swipe.setEnabled(false);
        MainFragmentAdapter mainFragmentAdapter = new MainFragmentAdapter(getSupportFragmentManager(), fragmentList);
        medicalViewpager.setAdapter(mainFragmentAdapter);
        medicalViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setOneChecked(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {
//                enableDisableSwipeRefresh(state == ViewPager.SCROLL_STATE_IDLE);
            }
//            private void enableDisableSwipeRefresh(boolean b) {
//
//                if (medica_swipe != null) {
//                    medica_swipe.setEnabled(b);
//                }
//            }
        });
        if (popupDialog == null) {
            popupDialog = new TimeDialog(this);
            popupDialog.setLeft(left);
            popupDialog.setRight(right1);
            popupDialog.numpick_left.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                    switch (newVal) {
                        case 0:
                            popupDialog.setRight(right1);
                            break;
                        case 1:
                            popupDialog.setRight(right2);
                            break;
                        case 2:
                            popupDialog.setRight(right3);
                            break;
                        case 3:
                            popupDialog.setRight(right4);
                            break;
                        default:
                            break;
                    }
                }
            });
            popupDialog.btnConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int left = popupDialog.numpick_left.getValue();
                    int right = popupDialog.numpick_right.getValue();
                    popupwindowSelect(left, right);
                    popupDialog.dismiss();
                }
            });
        }
        clinicalDiagnosisAndTreatmentList = new ArrayList<>();
        supplementaryTestList = new ArrayList<>();
        labTestList = new ArrayList<>();
        pathologyList = new ArrayList<>();
        handler.sendEmptyMessage(GetData);
    }

    private int count = 0;
    private final int GetData = 1;

    boolean open = true;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GetData:
                    if (open) {
                        getData();
                    }
                    open = false;
                    break;
            }
        }
    };


    private void getData() {
        logUtil.i("第" + count + "次");
        count++;
//        ClinicalDiagnosisAndTreatment:临床诊疗 SupplementaryTest:辅助检查 LabTest:实验室检查 Pathology:病理
        clinicalDiagnosisAndTreatmentList.clear();
        supplementaryTestList.clear();
        labTestList.clear();
        pathologyList.clear();
        loading = LoadingUtil.getInstance(this);
        if (serviceFactory == null) {
            serviceFactory = new ServiceFactory(Url.prefix, getApplicationContext());
        }
//        loading.show();
        medica_swipe.setRefreshing(true);
        IPatientRecordService patientRecordService = serviceFactory.getPatientRecordService();
        Call<List<PatientRecordDisplayBean>> listCall = patientRecordService.LoadAllPatientGenericRecordsBypatientId(patientId);
        listCall.enqueue(new Callback<List<PatientRecordDisplayBean>>() {
            @Override
            public void onResponse(Call<List<PatientRecordDisplayBean>> call, Response<List<PatientRecordDisplayBean>> response) {
                if (response.isSuccessful()) {
                    List<PatientRecordDisplayBean> body = response.body();
                    for (int i = 0; i < body.size(); i++) {
                        switch (body.get(i).category) {
                            case "临床诊疗记录":
                                clinicalDiagnosisAndTreatmentList.add(body.get(i));
                                break;
                            case "实验室检查":
                                labTestList.add(body.get(i));
                                break;
                            case "辅助检查":
                                supplementaryTestList.add(body.get(i));
                                break;
                            case "病理":
                                pathologyList.add(body.get(i));
                                break;
                            default:
                                break;
                        }
                    }
                    FragmentData fragmentData1 = (FragmentData) (fragmentList.get(0));
                    fragmentData1.setData(clinicalDiagnosisAndTreatmentList);
                    fragmentData1.setBundle(bundle);

                    FragmentData fragmentData2 = (FragmentData) (fragmentList.get(1));
                    fragmentData2.setData(supplementaryTestList);
                    fragmentData2.setBundle(bundle);

                    FragmentData fragmentData3 = (FragmentData) (fragmentList.get(2));
                    fragmentData3.setData(labTestList);
                    fragmentData3.setBundle(bundle);

                    FragmentData fragmentData4 = (FragmentData) (fragmentList.get(3));
                    fragmentData4.setData(pathologyList);
                    fragmentData4.setBundle(bundle);
//                    loading.dismiss("获取数据成功");
                    medica_swipe.setRefreshing(false);
                } else {
                    loading.dismiss("获取数据失败");
                }
                open = true;
            }

            @Override
            public void onFailure(Call<List<PatientRecordDisplayBean>> call, Throwable t) {
                loading.dismiss(LoadingStatu.NetError.value);
                open = true;
            }
        });
    }


    /**
     * 点击添加按钮弹出框选择
     */
    private void popupwindowSelect(int left, int right) {
        switch (left) {
            case 0:
                switch (right) {
                    //门诊记录
                    case 0:
                        skipToForResult(AddOutpatientRecordActivity.class, bundle, Add);
                        break;
                    //远程
                    case 1:
                        skipToForResult(AddRemoteConsultationRecordActivity.class, bundle, Add);
                        break;
                    //网络
                    case 2:
                        skipToForResult(AddOnlineConsultationRecordActivity.class, bundle, Add);
                        break;
                    default:
                        String category = this.left[left];
                        String subject = array[left][right];
                        bundle.putString("category", category);
                        bundle.putString("subject", subject);
                        skipToForResult(AddCommonMedicalRecordActivityNew.class, bundle, Add);
                        break;
                }
                break;
            case 1:
            case 3:
                category = this.left[left];
                subject = array[left][right];
                bundle.putString("category", category);
                bundle.putString("subject", subject);
                skipToForResult(AddCommonMedicalRecordActivityNew.class, bundle, Add);
                break;
            case 2:
                category = this.left[left];
                subject = array[left][right];
                bundle.putString("category", category);
                bundle.putString("subject", subject);
                skipToForResult(AddLabTestActivity.class, bundle, Add);
                break;
            default:
                break;
        }
    }


    /**
     * 点击添加按钮
     */
    @OnClick(R.id.tv_operate)
    public void addHistory() {
        popupDialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    /**
     * 临床诊疗
     */
    @OnClick(R.id.rbtn_clinical_treatment_medicalhistory)
    public void clinical_treatment() {
        medicalViewpager.setCurrentItem(0);
    }

    /**
     * 辅助检查
     */
    @OnClick(R.id.rbt_assist_check_medicalhistory)
    public void assist_check() {
        medicalViewpager.setCurrentItem(1);
    }

    /**
     * 实验室检查
     */
    @OnClick(R.id.btn_labcheck_medicalhistory)
    public void labcheck() {
        medicalViewpager.setCurrentItem(2);
    }

    /**
     * 病理检查
     */
    @OnClick(R.id.rbtn_pathologycheck_medicalhistory)
    public void pathologycheck() {
        medicalViewpager.setCurrentItem(3);
    }

    private void setOneChecked(int index) {
        switch (index) {
            case 0:
                rbtnClinicalTreatmentMedicalhistory.setChecked(true);
                break;
            case 1:
                rbtAssistCheckMedicalhistory.setChecked(true);
                break;
            case 2:
                btnLabcheckMedicalhistory.setChecked(true);
                break;
            case 3:
                rbtnPathologycheckMedicalhistory.setChecked(true);
                break;
        }
    }

    LogUtilInterface logUtil = LogFactory.getSimpleLog(getClass().getName());

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        logUtil.i("requestCode" + requestCode + " onActivityResult:" + resultCode);
        switch (requestCode) {
            case Add:
                if (resultCode == RESULT_OK) {
                    handler.sendEmptyMessage(GetData);
                    if (data != null) {
                        setResult(RESULT_OK, data);
                    } else {
                        setResult(Activity.RESULT_CANCELED);
                    }
                    return;
                }
        }
        if (resultCode == Update) {
//            logUtil.i("更新了");
            handler.sendEmptyMessage(GetData);
        }
    }


    /**
     * 设置当前界面为不可编辑状态
     */
    private void offCurActivity(Bundle bundle) {
        boolean IsAble = iGetBundleHealthyRecord.isAble();
        if (IsAble) {
            tvStore.setVisibility(View.VISIBLE);
        } else {
            tvStore.setVisibility(View.GONE);
        }
        for (int i = 0; i < fragmentList.size(); i++) {
            FragmentControl control = (FragmentControl) fragmentList.get(i);
            control.setEditable(IsAble);
        }

    }

    private class StrartActivity implements IStrartActivity {
        @Override
        public void startActivityForResult(Class<? extends Activity> purpose, Bundle bundle, int requestCode) {
            skipToForResult(purpose, bundle, requestCode);
        }
    }
}
