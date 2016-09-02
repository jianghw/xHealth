package com.kaurihealth.kaurihealth.patientwithdoctor.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.commonlibrary.widget.util.LoadingUtil;
import com.example.commonlibrary.widget.util.SuccessInterface;
import com.kaurihealth.datalib.request_bean.bean.DoctorPatientRelationshipBean;
import com.kaurihealth.datalib.request_bean.bean.HealthConditionDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.PatientDisplayBean;
import com.kaurihealth.datalib.response_bean.ResponseDisplayBean;
import com.kaurihealth.datalib.service.IHealthConditionService;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.common.BundleKey;
import com.kaurihealth.kaurihealth.common.bean.MesBean;
import com.kaurihealth.kaurihealth.home.util.BundleFactory;
import com.kaurihealth.kaurihealth.home.util.Interface.IBundleFactory;
import com.kaurihealth.kaurihealth.home.util.Interface.IGetBundleHealthyRecord;
import com.kaurihealth.kaurihealth.patientwithdoctor.bean.EditAddMode;
import com.kaurihealth.kaurihealth.patientwithdoctor.bean.HealthyRecordHabitNewBean;
import com.kaurihealth.kaurihealth.patientwithdoctor.bean.HealthyRecordNewActivityBean;
import com.kaurihealth.kaurihealth.patientwithdoctor.bean.HealthyRecordNewMenstrualReproductive;
import com.kaurihealth.kaurihealth.patientwithdoctor.bean.HealthyRecordNormalNewBean;
import com.kaurihealth.kaurihealth.patientwithdoctor.util.FriendNetUtil;
import com.kaurihealth.kaurihealth.patientwithdoctor.util.HealthyRecordInterface;
import com.kaurihealth.kaurihealth.patientwithdoctor.util.Interface.IHealthyRecordHabit;
import com.kaurihealth.kaurihealth.patientwithdoctor.util.Interface.IHealthyRecordNewActivity;
import com.kaurihealth.kaurihealth.patientwithdoctor.util.Mode;
import com.kaurihealth.kaurihealth.services.IServiceFactory;
import com.kaurihealth.kaurihealth.services.ServiceFactory;
import com.kaurihealth.kaurihealth.util.Url;
import com.youyou.zllibrary.httputil.Success;
import com.youyou.zllibrary.util.CommonActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HealthyRecordNewActivity extends CommonActivity {
    @Bind(R.id.layContainer)
    LinearLayout layContainer;
    @Bind(R.id.tv_operate)
    TextView tvStore;
    private int patientId;
    private final int count = 7;
    public static final String Key = "data";
    private final int Add = 1;
    private final int Edit = 2;
    private HealthyRecordHabitNewBean healthyRecordHabitNewBean;
    private Mode mode;
    private DoctorPatientRelationshipBean currentDoctorPatientRelationship;
    //月经与生育史
    private HealthyRecordNewMenstrualReproductive healthyRecordNewMenstrualReproductive;
    private IHealthyRecordHabit<HealthConditionDisplayBean> menstrualReproductive;
    private IHealthyRecordHabit<HealthConditionDisplayBean> habitInterface;
    private IGetBundleHealthyRecord iGetBundleHealthyRecord;
    private Bundle bundle;
    private IHealthConditionService healthConditionService;
    private SweetAlertDialog commitDataDialog;

    private PatientDisplayBean currentPatient;
    private long mExitTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.healthy_record_new);
        ButterKnife.bind(this);
        init();
    }

    Map<Integer, HealthyRecordInterface> map = new HashMap<>();

    @Override
    public void init() {
        super.init();
        setBack(R.id.iv_back);
        bundle = getBundle();

        IBundleFactory bundleFactory = new BundleFactory();
        iGetBundleHealthyRecord = bundleFactory.getIGetBundleHealthyRecord(bundle);

        patientId = bundle.getInt(BundleKey.CurrentPatientId);
        currentDoctorPatientRelationship = (DoctorPatientRelationshipBean) bundle.getSerializable(BundleKey.CurrentDoctorPatientRelationship);
        //currentPatient =  (PatientDisplayBean) bundle.getSerializable(BundleKey.CurrentPatient);

        offCurActivity();
        Context context = getApplicationContext();
        IHealthyRecordNewActivity bean = getHealthyRecordNewActivityInterface();

        HealthyRecordNormalNewBean mainHealthyProblemBean = new HealthyRecordNormalNewBean(0, context, "目前主要健康问题", bean, patientId, mode);
        layContainer.addView(mainHealthyProblemBean.getParent());
        map.put(0, mainHealthyProblemBean.getHealthyRecordInterface());

        HealthyRecordNormalNewBean previousIlnessProblemBean = new HealthyRecordNormalNewBean(1, context, "既往疾病与健康问题", bean, patientId, mode);
        layContainer.addView(previousIlnessProblemBean.getParent());
        map.put(1, previousIlnessProblemBean.getHealthyRecordInterface());

        HealthyRecordNormalNewBean currentDrugProblemBean = new HealthyRecordNormalNewBean(2, context, "目前应用药物", bean, patientId, mode);
        layContainer.addView(currentDrugProblemBean.getParent());
        map.put(2, currentDrugProblemBean.getHealthyRecordInterface());

        HealthyRecordNormalNewBean allergyHistoryBean = new HealthyRecordNormalNewBean(3, context, "药物及其它过敏史", bean, patientId, mode);
        layContainer.addView(allergyHistoryBean.getParent());
        map.put(3, allergyHistoryBean.getHealthyRecordInterface());

        HealthyRecordNormalNewBean otherThreatmentBean = new HealthyRecordNormalNewBean(4, context, "既往手术或其它既往治疗", bean, patientId, mode);
        layContainer.addView(otherThreatmentBean.getParent());
        map.put(4, otherThreatmentBean.getHealthyRecordInterface());

        HealthyRecordNormalNewBean familyHistoryBean = new HealthyRecordNormalNewBean(5, context, "家族病史", bean, patientId, mode);
        layContainer.addView(familyHistoryBean.getParent());
        map.put(5, familyHistoryBean.getHealthyRecordInterface());

        HealthyRecordNormalNewBean precautionBean = new HealthyRecordNormalNewBean(6, context, "预防接种史", bean, patientId, mode);
        layContainer.addView(precautionBean.getParent());
        map.put(6, precautionBean.getHealthyRecordInterface());
        for (int i = 0; i < count; i++) {
            map.get(i).setListRes();
        }
        healthyRecordHabitNewBean = new HealthyRecordHabitNewBean(this, patientId, mode);
        habitInterface = healthyRecordHabitNewBean.getHealthyRecordHabitInterface();
        layContainer.addView(habitInterface.getView());
        if (currentDoctorPatientRelationship != null) {
            if (currentDoctorPatientRelationship.patient.gender.equals("女")) {
                healthyRecordNewMenstrualReproductive = new HealthyRecordNewMenstrualReproductive(patientId, mode, this);
                menstrualReproductive = healthyRecordNewMenstrualReproductive.getHealthyRecordHabitInterface();
                layContainer.addView(menstrualReproductive.getView());
            }
        } else {
            if (patientId != -1) {
                String gender = iGetBundleHealthyRecord.getGender();
                if (gender != null) {
                    if (gender.equals("女")) {
                        healthyRecordNewMenstrualReproductive = new HealthyRecordNewMenstrualReproductive(patientId, mode, this);
                        menstrualReproductive = healthyRecordNewMenstrualReproductive.getHealthyRecordHabitInterface();
                        layContainer.addView(menstrualReproductive.getView());
                    }
                }
            }
        }

        getData(patientId);
        IServiceFactory serviceFactory = new ServiceFactory(Url.prefix,getApplicationContext());
        healthConditionService = serviceFactory.getHealthConditionService();
        commitDataDialog = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE).setTitleText("提示").setContentText("确定修改吗?").setConfirmText("确定").setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {

                yeladey();
            }
        }).setCancelText("取消").setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                finishCur();
            }
        });
    }

    protected void getData(int patientId) {
        FriendNetUtil friendNetUtil = new FriendNetUtil(this);
        friendNetUtil.getHealthyRecord(patientId, this, new Success<Object>() {
            @Override
            public void success(Object s) throws Exception {
                List<HealthConditionDisplayBean> healthySmallIteamBeen = (List<HealthConditionDisplayBean>) s;
                distributeData(healthySmallIteamBeen);

            }
        });
    }

    /**
     * 分发数据
     */
    private void distributeData(List<HealthConditionDisplayBean> healthySmallIteamBeen) {
        for (HealthConditionDisplayBean item : healthySmallIteamBeen) {
            for (int i = 0; i < count; i++) {
                if (map.get(i).isThisType(item)) {
                    map.get(i).add(item);
                    break;
                }
            }
            if (habitInterface.isThisType(item)) {
                habitInterface.add(item);
                continue;
            }
            if (menstrualReproductive != null) {
                if (menstrualReproductive.isThisType(item)) {
                    menstrualReproductive.add(item);
                }
            }
        }
    }

    //延时处理
    private void yeladey(){
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            store();
            mExitTime = System.currentTimeMillis();
        }else {
            return;
        }
    }

    private IHealthyRecordNewActivity getHealthyRecordNewActivityInterface() {
        IHealthyRecordNewActivity bean = new IHealthyRecordNewActivity() {
            @Override
            public void add(int id, String txtName) {
                HealthyRecordNewActivityBean bean1 = new HealthyRecordNewActivityBean(id, EditAddMode.Add, txtName);
                Bundle bundle = new Bundle();
                bundle.putSerializable(Key, bean1);
                skipToForResult(AddHealothyRecordActivity.class, bundle, Add);
            }

            @Override
            public void edit(int id, int position, String txtName) {
                HealthyRecordNewActivityBean bean1 = new HealthyRecordNewActivityBean(id, EditAddMode.Edit, txtName);
                bean1.setValue(((HealthConditionDisplayBean) (map.get(id).getValueAt(position))).detail);
                bean1.setIndex(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable(Key, bean1);
                skipToForResult(AddHealothyRecordActivity.class, bundle, Edit);
            }
        };
        return bean;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Add:
                if (resultCode == RESULT_OK) {
                    HealthyRecordNewActivityBean serializableBean = (HealthyRecordNewActivityBean) data.getExtras().getSerializable(Key);
                    setData(serializableBean);
                }
                break;
            case Edit:
                if (resultCode == RESULT_OK) {
                    HealthyRecordNewActivityBean serializableBean = (HealthyRecordNewActivityBean) data.getExtras().getSerializable(Key);
                    setData(serializableBean);
                }
                break;
        }
    }

    /**
     * 将添加操作或编辑操作获取到的数据，加载到界面中
     *
     * @param bean
     */
    private void setData(HealthyRecordNewActivityBean bean) {
        switch (bean.getMode()) {
            case Edit:
                int id = bean.getId();
                int index = bean.getIndex();
                String value = bean.getValue();
                map.get(id).edit(index, value);
                break;
            case Add:
                int idAdd = bean.getId();
                String valueAdd = bean.getValue();
                map.get(idAdd).addNewOne(valueAdd);
                break;
        }
    }

    @OnClick({R.id.tv_operate, R.id.iv_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_operate:
                commitDataDialog.show();
                break;
//            case R.id.iv_back:
//                commitDataDialog.show();
//                break;
        }
    }


    /**
     * 弹出框，确认是否保存
     */
    private void store() {
        final MesBean mesBean = new MesBean();
        mesBean.isSuccess = true;
        final LoadingUtil loadingUtil = LoadingUtil.getInstance(this);
        loadingUtil.setDismissListener(new SuccessInterface() {
            @Override
            public void success() {
                if (mesBean.isSuccess) {
                    showToast("更新医疗记录成功");
                }
                finishCur();
            }
        });
        loadingUtil.regist();
        updataHealthRecord(new Callback<HealthConditionDisplayBean>() {
            @Override
            public void onResponse(Call<HealthConditionDisplayBean> call, Response<HealthConditionDisplayBean> response) {
                mesBean.isSuccess &= response.isSuccessful();
                loadingUtil.unregist();
            }

            @Override
            public void onFailure(Call<HealthConditionDisplayBean> call, Throwable t) {
                loadingUtil.unregist();
            }
        });
        loadingUtil.regist();
        deleteHealthRecord(new Callback<ResponseDisplayBean>() {
            @Override
            public void onResponse(Call<ResponseDisplayBean> call, Response<ResponseDisplayBean> response) {
                mesBean.isSuccess &= response.isSuccessful();
                loadingUtil.unregist();
            }

            @Override
            public void onFailure(Call<ResponseDisplayBean> call, Throwable t) {
                loadingUtil.unregist();
            }
        });
    }

    /**
     * 设置当前界面为不可编辑状态
     */
    private void offCurActivity() {
        boolean IsAble = iGetBundleHealthyRecord.isAble();
        if (!IsAble) {
            tvStore.setVisibility(View.GONE);
            mode = Mode.ReadOnly;
        } else {
            mode = Mode.EditAndRead;
        }
    }

    /**
     * 更新健康记录   后台有误
     */
    private void updataHealthRecord(Callback<HealthConditionDisplayBean> callback) {
        final List<HealthConditionDisplayBean> updataList = new LinkedList<>();
        for (int i = 0; i < count; i++) {
            HealthyRecordInterface iHealthyRecord = map.get(i);
            if (iHealthyRecord.getAll() != null) {
                updataList.addAll(iHealthyRecord.getAll());
            }
        }
        updataList.addAll(healthyRecordHabitNewBean.getHealthyRecordHabitInterface().getAll());
        if (menstrualReproductive != null) {
            updataList.addAll(menstrualReproductive.getAll());
        }
        Iterator<HealthConditionDisplayBean> iterator = updataList.iterator();
        while (iterator.hasNext()) {
            if (iterator.next() == null) {
                iterator.remove();
            }
        }
        HealthConditionDisplayBean[] updataArray = updataList.toArray(new HealthConditionDisplayBean[updataList.size()]);
        Call<HealthConditionDisplayBean> healthConditionDisplayBeanCall = healthConditionService.UpdateHealthConditions(updataArray);
        healthConditionDisplayBeanCall.enqueue(callback);
    }

    /**
     * 删除健康记录
     */
    private void deleteHealthRecord(Callback<ResponseDisplayBean> callback) {
        final List<HealthConditionDisplayBean> deleteList = new LinkedList<>();
        for (int i = 0; i < count; i++) {
            HealthyRecordInterface iHealthyRecord = map.get(i);
            if (iHealthyRecord.getListNeedToDelete() != null) {
                deleteList.addAll(iHealthyRecord.getListNeedToDelete());
            }
        }
        List<Integer> listDelete = new ArrayList();
        for (HealthConditionDisplayBean iteam : deleteList) {
            listDelete.add(iteam.healthConditionId);
        }
        Integer[] deleteArray = listDelete.toArray(new Integer[listDelete.size()]);
        Call<ResponseDisplayBean> responseDisplayBeanCall = healthConditionService.DeleteHealthConditions(deleteArray);
        responseDisplayBeanCall.enqueue(callback);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (event.getAction()) {
            case KeyEvent.KEYCODE_BACK:
                commitDataDialog.show();
                return true;
            default:
                return super.onKeyDown(keyCode, event);
        }
    }
}

