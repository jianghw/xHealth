package com.kaurihealth.kaurihealth.patient_v.medical_records;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.kaurihealth.datalib.response_bean.PatientRecordDisplayBean;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.adapter.ClinicalMedicalBeanAdapter;
import com.kaurihealth.kaurihealth.adapter.ClinicalMedicalBeanItem;
import com.kaurihealth.kaurihealth.patient_v.IGetMedicaHistoryRecord;
import com.kaurihealth.kaurihealth.eventbus.CommonMedicalRecordToReadEvent;
import com.kaurihealth.kaurihealth.eventbus.PathologyFragmentEvent;
import com.kaurihealth.utilslib.ColorUtils;
import com.kaurihealth.utilslib.constant.Global;
import com.kaurihealth.utilslib.log.LogUtils;
import com.kaurihealth.utilslib.widget.AnimatedExpandableListView;

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

/**
 * Created by mip on 2016/9/28.
 */
    //医患-->医疗记录-->病理Fragment
public class PathologyFragment extends Fragment implements ExpandableListView.OnChildClickListener {

    @Bind(R.id.ae_listView)
    AnimatedExpandableListView mAeListView;

    @Bind(R.id.tv_note)
    TextView mTvNote;

    @Bind(R.id.clinical_SR)
    SwipeRefreshLayout clinical_SR;

    List<ClinicalMedicalBeanItem> mGroupIteams = new ArrayList<>();
    private ClinicalMedicalBeanAdapter adapter;

    private IGetMedicaHistoryRecord medicaHistoryRecord;
    public static PathologyFragment newInstance(){
        return new PathologyFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_clinical_medical,container,false);
        ButterKnife.bind(this,view);

        clinical_SR.setSize(SwipeRefreshLayout.DEFAULT);
        clinical_SR.setColorSchemeColors(ColorUtils.setSwipeRefreshColors(getContext()));
        clinical_SR.setProgressBackgroundColor(R.color.linelogin);
        clinical_SR.setOnRefreshListener(() -> {
            medicaHistoryRecord.getDate();
            clinical_SR.setRefreshing(false);

        });

        adapter = new ClinicalMedicalBeanAdapter(getContext(),mGroupIteams);
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


    /**
     * 点击子item，跳转界面  MS No Useages  onChildClick
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
        EventBus.getDefault().postSticky(new CommonMedicalRecordToReadEvent(patientRecordDisplayBean, activity.getShipBean(),MedicalRecordActivity.PATHOLOGY));
        //TODO
        switch (patientRecordDisplayBean.getSubject()) {
            case "病理":
                activity.switchPageUI(Global.Jump.CommonMedicalRecordToReadActivity,null);
                break;
        }
    }

    public void setGetMedicaHistoryRecordListener(IGetMedicaHistoryRecord medicaHistoryRecord){
        this.medicaHistoryRecord = medicaHistoryRecord;
    }

    /**
     * ----------------------------订阅事件----------------------------------
     **/
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void eventBusMain(PathologyFragmentEvent event) {
        List<PatientRecordDisplayBean> list = event.getListsMessage();
        if (list.isEmpty()) return;
        dataPacketProcessing(list);
    }

    private void dataPacketProcessing(List<PatientRecordDisplayBean> list) {
        mTvNote.setVisibility(list.size() > 0 ? View.GONE : View.VISIBLE);

        if (!mGroupIteams.isEmpty()) mGroupIteams.clear();
        String[] arrays = getResources().getStringArray(R.array.Pathology);
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
