package com.kaurihealth.kaurihealth.record_details_v;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.kaurihealth.datalib.request_bean.bean.HealthConditionDisplayBean;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.base_v.BaseFragment;
import com.kaurihealth.kaurihealth.eventbus.HealthConditionFragmentAddEvent;
import com.kaurihealth.kaurihealth.eventbus.HealthConditionFragmentEditEvent;
import com.kaurihealth.kaurihealth.patient_v.health_condition.AddHealothyRecordActivity;
import com.kaurihealth.kaurihealth.patient_v.health_condition.HealthyRecordHabitNewBean;
import com.kaurihealth.kaurihealth.patient_v.health_condition.HealthyRecordInterface;
import com.kaurihealth.kaurihealth.patient_v.health_condition.HealthyRecordNewMenstrualReproductive;
import com.kaurihealth.kaurihealth.patient_v.health_condition.HealthyRecordNormalNewBean;
import com.kaurihealth.kaurihealth.patient_v.health_condition.IHealthyRecordHabit;
import com.kaurihealth.kaurihealth.patient_v.health_condition.IHealthyRecordNewActivity;
import com.kaurihealth.kaurihealth.patient_v.health_condition.Mode;
import com.kaurihealth.kaurihealth.record_details_v.bean.HealthyRecordNewActivityBean;
import com.kaurihealth.kaurihealth.tinker.SampleApplicationLike;
import com.kaurihealth.mvplib.patient_p.HealthConditionPresenter;
import com.kaurihealth.mvplib.patient_p.IHealthConditionView;
import com.kaurihealth.utilslib.ColorUtils;
import com.kaurihealth.utilslib.TranslationAnim;
import com.kaurihealth.utilslib.constant.Global;
import com.kaurihealth.utilslib.widget.ScrollChildSwipeRefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.Bind;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by mip on 2016/12/19.
 * <p/>
 * Describe:
 */

public class HealthConditionFragment extends BaseFragment implements IHealthConditionView, RecordDetailsActivity.DeliverListener {
    @Inject
    HealthConditionPresenter<IHealthConditionView> mPresenter;

    @Bind(R.id.layContainer)
    LinearLayout layContainer;
    @Bind(R.id.tv_operate)
    TextView tvStore;
    @Bind(R.id.scrollView)
    ScrollView mScrollView;
    @Bind(R.id.swipe_refresh)
    ScrollChildSwipeRefreshLayout mSwipeRefresh;

    private int patientId;
    private final int count = 7;
    public static final String Key = "data";
    private final int Add = 1;
    private final int Edit = 2;

    private HealthyRecordHabitNewBean healthyRecordHabitNewBean;
    private IHealthyRecordHabit<HealthConditionDisplayBean> menstrualReproductive;
    private IHealthyRecordHabit<HealthConditionDisplayBean> habitInterface;

    Map<Integer, HealthyRecordInterface> map = new ArrayMap<>();

    private HealthConditionDisplayBean[] updataArray;
    private Integer[] deleteArray;
    private int times = 0;

    public static HealthConditionFragment getInstance() {
        return new HealthConditionFragment();
    }

    @Override
    protected int getFragmentLayoutID() {
        return R.layout.activity_health_condition;
    }

    @Override
    protected void initPresenterAndView(Bundle savedInstanceState) {
        SampleApplicationLike.getApp().getComponent().inject(this);
        mPresenter.setPresenter(this);

        mSwipeRefresh.setColorSchemeColors(
                ColorUtils.setSwipeRefreshColors(getContext())
        );
        mSwipeRefresh.setScrollUpChild(mScrollView);
        mSwipeRefresh.setSize(SwipeRefreshLayout.DEFAULT);
        mSwipeRefresh.setDistanceToTriggerSync(Global.Numerical.SWIPE_REFRESH);
        mSwipeRefresh.setOnRefreshListener(() -> mPresenter.loadingRemoteData(true));
    }

    @Override
    protected void initDelayedData() {
        EventBus.getDefault().register(this);
    }

    @Override
    protected void lazyLoadingData() {
        mPresenter.onSubscribe();

        ((RecordDetailsActivity) (getActivity())).setOnclickInterface(this);//注册监听
    }

    @Override
    public void loadNewPatientRecordCountsByPatientId(boolean isFriend) {
        patientId = getCurrentPatientId();

        Mode mode = isFriend ? Mode.EditAndRead : Mode.ReadOnly;

        Context context = getActivity().getApplicationContext();
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

        //个人习惯
        healthyRecordHabitNewBean = new HealthyRecordHabitNewBean(getActivity(), patientId, mode);
        habitInterface = healthyRecordHabitNewBean.getHealthyRecordHabitInterface();
        layContainer.addView(habitInterface.getView());
        //月经
        if (patientId != -1) {
            String gender = ((RecordDetailsActivity) getActivity()).getGender();
            if (gender != null && gender.equals("女")) {
                HealthyRecordNewMenstrualReproductive healthyRecordNewMenstrualReproductive = new HealthyRecordNewMenstrualReproductive(patientId, mode, getActivity());
                menstrualReproductive = healthyRecordNewMenstrualReproductive.getHealthyRecordHabitInterface();
                layContainer.addView(menstrualReproductive.getView());
            }
        }
    }

    /**
     * 健康记录的接口
     *
     * @return
     */
    private IHealthyRecordNewActivity getHealthyRecordNewActivityInterface() {
        IHealthyRecordNewActivity bean = new IHealthyRecordNewActivity() {
            @Override
            public void add(int id, String txtName) {
                HealthyRecordNewActivityBean bean1 = new HealthyRecordNewActivityBean(id, EditAddMode.Add, txtName);
                Bundle bundle = new Bundle();
                bundle.putSerializable(Key, bean1);
                bundle.putString("ADDOREDIT", "add");
                skipToForResult(AddHealothyRecordActivity.class, bundle, Add);
            }

            @Override
            public void edit(int id, int position, String txtName) {
                HealthyRecordNewActivityBean bean1 = new HealthyRecordNewActivityBean(id, EditAddMode.Edit, txtName);
                bean1.setValue(((HealthConditionDisplayBean) (map.get(id).getValueAt(position))).detail);
                bean1.setIndex(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable(Key, bean1);
                bundle.putString("ADDOREDIT", "edit");
                skipToForResult(AddHealothyRecordActivity.class, bundle, Edit);
            }
        };
        return bean;
    }

    /**
     * 接口
     *
     * @param position
     */
    @Override
    public void deliverPosition(int position) {
        if (position == 1) {
            SweetAlertDialog commitDataDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("提示")
                    .setContentText("确定修改吗?")
                    .setConfirmText("确定")
                    .setConfirmClickListener(sweetAlertDialog -> yeladey())  //延时处理
                    .setCancelText("取消")
                    .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {    //对话框选择"确定"/"取消"之后调用
                            sweetAlertDialog.dismiss();
                            TranslationAnim.zlFinish(getActivity());
                        }
                    });
            commitDataDialog.show();
        }
    }

    public enum EditAddMode {
        Add, Edit
    }

    private long mExitTime;

    //延时处理
    private void yeladey() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            updataHealthRecord();//更新健康记录
            mExitTime = System.currentTimeMillis();
        }
    }

    /**
     * 更新健康记录   后台有误
     */
    private void updataHealthRecord() {
        List<HealthConditionDisplayBean> updataList = new LinkedList<>();
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
        updataArray = updataList.toArray(new HealthConditionDisplayBean[updataList.size()]);
        mPresenter.updataHealthRecord();
    }

    /**
     * 删除健康记录
     */
    private void deleteHealthRecord() {
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
        deleteArray = listDelete.toArray(new Integer[listDelete.size()]);
        mPresenter.deleteHealthRecord();
    }

    /**
     * 得到当前的患者id
     */
    @Override
    public int getCurrentPatientId() {
        return ((RecordDetailsActivity) (getActivity())).getPatientId();
    }

    /**
     * 得到网络返回的数据
     *
     * @param healthConditionDisplayBeen
     */
    @Override
    public void getHealthCondition(List<HealthConditionDisplayBean> healthConditionDisplayBeen) {
        if (healthConditionDisplayBeen == null) return;
        distributeData(healthConditionDisplayBeen);
    }

    /**
     * 分发数据
     */
    private void distributeData(List<HealthConditionDisplayBean> healthConditionDisplayBeen) {
        for (HealthConditionDisplayBean item : healthConditionDisplayBeen) {
            for (int i = 0; i < count; i++) {
                HealthyRecordInterface healthyRecordInterface = map.get(i);
                if (healthyRecordInterface == null) return;
                if (healthyRecordInterface.isThisType(item)) {
                    healthyRecordInterface.add(item);
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

    /**
     * 得到需要更新的数据
     */
    @Override
    public HealthConditionDisplayBean[] getCurrentUpdataArray() {

        if (updataArray == null) {
            throw new IllegalStateException("updataArray must be not null");
        }
        return updataArray;
    }


    /**
     * 得到当前需要删除的数据
     */
    @Override
    public Integer[] getCurrentDeleteArray() {
        if (deleteArray == null) {
            throw new IllegalStateException("deleteArray must be not null");
        }
        return deleteArray;
    }

    /**
     * 得到请求返回,判断更新跟删除是否完成
     */
    @Override
    public void getRequestReponse() {
        ++times;
        if (times == 1) {
            deleteHealthRecord();//删除健康记录
        }

        if (times == 2) {
            times = 0;
            TranslationAnim.zlFinish(getActivity());
        }
    }

    /**
     * 添加界面返回
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void eventBusMain(HealthConditionFragmentAddEvent event) {
        HealthyRecordNewActivityBean bean = event.getActivityBean();
        setData(bean);
    }

    /**
     * 编辑界面返回
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void eventBusMain(HealthConditionFragmentEditEvent event) {
        HealthyRecordNewActivityBean bean = event.getActivityBean();
        setData(bean);
    }

    /**
     @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
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
     }**/

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

    @Override
    public void switchPageUI(String className) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        removeStickyEvent(HealthConditionFragmentAddEvent.class);
        removeStickyEvent(HealthConditionFragmentEditEvent.class);
    }
}
