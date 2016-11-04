package com.kaurihealth.kaurihealth.patient_v.medical_records;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.kaurihealth.datalib.response_bean.PatientRecordDisplayBean;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.adapter.ClinicalMedicalBeanAdapter;
import com.kaurihealth.kaurihealth.adapter.ClinicalMedicalBeanItem;
import com.kaurihealth.kaurihealth.common.Interface.IGetMedicaHistoryRecord;
import com.kaurihealth.kaurihealth.eventbus.ClinicalMedcalFgtListEvent;
import com.kaurihealth.kaurihealth.eventbus.OutpatientElectronicBeanEvent;
import com.kaurihealth.utilslib.constant.Global;
import com.kaurihealth.utilslib.log.LogUtils;
import com.kaurihealth.utilslib.widget.AnimatedExpandableListView;
import com.kaurihealth.utilslib.widget.ScrollChildSwipeRefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.kaurihealth.kaurihealth.R.id.clinical_SR;

/**
 * 医患-->医疗记录-->临床诊疗Fragment
 */
public class ClinicalMedicalFragment extends Fragment implements ExpandableListView.OnChildClickListener {

    @Bind(R.id.ae_listView)
    AnimatedExpandableListView mAeListView;

    @Bind(R.id.tv_note)
    TextView mTvNote;

    @Bind(clinical_SR)
    ScrollChildSwipeRefreshLayout mSwipeRefresh;

    List<ClinicalMedicalBeanItem> mGroupIteams = new ArrayList<>();

    private ClinicalMedicalBeanAdapter adapter;
    private IGetMedicaHistoryRecord medicaHistoryRecord;

    public static ClinicalMedicalFragment newInstance() {
        return new ClinicalMedicalFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_clinical_medical, container, false);
        ButterKnife.bind(this, view);

        mSwipeRefresh.setColorSchemeColors(
                ContextCompat.getColor(getContext().getApplicationContext(), R.color.welcome_bg_cl),
                ContextCompat.getColor(getContext().getApplicationContext(), R.color.welcome_bg_cl),
                ContextCompat.getColor(getContext().getApplicationContext(), R.color.welcome_bg_cl)
        );
        mSwipeRefresh.setScrollUpChild(mAeListView);
        mSwipeRefresh.setDistanceToTriggerSync(Global.Numerical.SWIPE_REFRESH);
        mSwipeRefresh.setOnRefreshListener(() -> {
            medicaHistoryRecord.getDate();
            mSwipeRefresh.setRefreshing(false);
        });

        adapter = new ClinicalMedicalBeanAdapter(getContext(), mGroupIteams);

        mAeListView.setAdapter(adapter);
        mAeListView.setGroupIndicator(null);
        mAeListView.setOnChildClickListener(this);

        EventBus.getDefault().register(this);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }

    public void setGetMedicaHistoryRecordListener(IGetMedicaHistoryRecord medicaHistoryRecord) {
        this.medicaHistoryRecord = medicaHistoryRecord;
    }

    /**
     * 点击子item，跳转界面
     */
    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        PatientRecordDisplayBean patientRecordDisplayBean = mGroupIteams.get(groupPosition).getList().get(childPosition);
        if (patientRecordDisplayBean == null) return false;

        pointToActivityPage(patientRecordDisplayBean);
        return true;
    }

    /**
     * 页面跳转指向 编辑操作
     */
    private void pointToActivityPage(PatientRecordDisplayBean patientRecordDisplayBean) {
        MedicalRecordActivity activity = (MedicalRecordActivity) getActivity();
        EventBus.getDefault().postSticky(new OutpatientElectronicBeanEvent(patientRecordDisplayBean, activity.getShipBean()));
        //临床诊疗，点击条目界面跳转
        switch (patientRecordDisplayBean.getSubject()) {
            case "门诊记录电子病历":
                activity.switchPageUI(Global.Jump.OutpatientElectronicActivity);
                break;
            case "门诊记录图片存档":
                activity.switchPageUI(Global.Jump.OutpatientPicturesActivity);
                break;
            case "远程医疗咨询":
                activity.switchPageUI(Global.Jump.RemoteMedicalConsultationActivity);
                break;
            case "网络医疗咨询":
                activity.switchPageUI(Global.Jump.NetworkMedicalConsultationActivity);
                break;
            case "入院记录":
                activity.switchPageUI(Global.Jump.AdmissionRecordActivity);
                break;
            case "院内治疗相关记录":
                activity.switchPageUI(Global.Jump.TreatmentRelatedRecordsActivity);
                break;
            case "出院记录":
                activity.switchPageUI(Global.Jump.DischargeRecordActivity);
                break;
            default:break;
        }
    }

    /**
     * ----------------------------订阅事件----------------------------------
     **/
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void eventBusMain(ClinicalMedcalFgtListEvent event) {
        List<PatientRecordDisplayBean> list = event.getListsMessage();
        if (list.isEmpty()) return;
        dataPacketProcessing(list);
    }

    private void dataPacketProcessing(List<PatientRecordDisplayBean> list) {
        mTvNote.setVisibility(View.GONE);

        if (!mGroupIteams.isEmpty()) mGroupIteams.clear();
        String[] arrays = getResources().getStringArray(R.array.clinical_medical_diagnosis);
        adapterDataUpdate(list, arrays);
    }

    /**
     * 数据处理
     * 1、按照arrays标签分组
     * 2、对每组list数据过滤，按数据的subject来分组，对应到array的item中
     * 3、把每一个array中的数据排序，时间降序
     * 4、array中数据转一个list
     * 5、每组list转一个ClinicalMedicalBeanItem对象
     *
     * @param list
     * @param arrays
     */
    private void adapterDataUpdate(List<PatientRecordDisplayBean> list, String[] arrays) {
        Observable.from(arrays)
                .subscribeOn(Schedulers.computation())
                .flatMap(subject -> Observable.from(list)
                        .filter(patientRecordDisplayBean -> patientRecordDisplayBean.getSubject().equals(subject))
                        .toSortedList((bean, bean1) -> {
                            long time = bean.getRecordDate().getTime() - bean1.getRecordDate().getTime();
                            return time > 0 ? -1 : time < 0 ? 1 : 0;
                        })
                        .map(beanList -> new ClinicalMedicalBeanItem(subject, beanList)))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ClinicalMedicalBeanItem>() {
                    @Override
                    public void onCompleted() {
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e(e.getMessage());
                    }

                    @Override
                    public void onNext(ClinicalMedicalBeanItem clinicalMedicalBeanItem) {
                        mGroupIteams.add(clinicalMedicalBeanItem);
                    }
                });
    }
}
