package com.kaurihealth.kaurihealth.home.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.kaurihealth.datalib.request_bean.bean.ClinicalDiagnosisAndTreatmentGroupIteam;
import com.kaurihealth.datalib.request_bean.bean.PatientRecordDisplayBean;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.common.Interface.IGetMedicaHistoryRecord;
import com.kaurihealth.kaurihealth.common.activity.EditLabtestActivity;
import com.kaurihealth.kaurihealth.common.adapter.LabtestAdapter;
import com.kaurihealth.kaurihealth.common.util.FragmentControl;
import com.kaurihealth.kaurihealth.home.util.BundleFactory;
import com.kaurihealth.kaurihealth.home.util.CompatorUtil;
import com.kaurihealth.kaurihealth.home.util.Interface.IBundleFactory;
import com.kaurihealth.kaurihealth.home.util.Interface.ISetBundleHealthyRecord;
import com.kaurihealth.kaurihealth.util.Interface.IStrartActivity;
import com.youyou.zllibrary.util.CommonFragment;
import com.youyou.zllibrary.util.FragmentData;
import com.youyou.zllibrary.widget.AnimatedExpandableListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 版权:    张磊
 * 作者:    张磊
 * 描述：实验室检查
 * 创建日期:2016/1/22
 * 修订日期:
 */
public class LabTestFragment extends CommonFragment implements FragmentData<PatientRecordDisplayBean>, FragmentControl {
    String[] titleKey = {"常规血液学检查", "常规尿液检查", "常规粪便检查", "特殊检查", "其它"};
    String[] titleValue = {"常规血液学检查", "常规尿液检查", "常规粪便检查", "特殊检查", "其它"};
    @Bind(R.id.animexpandableview_content_clinicatreatment)
    AnimatedExpandableListView expandableListView;
    @Bind(R.id.LabTest_SR)
    SwipeRefreshLayout LabTest_SR;
    private View view;
    ClinicalDiagnosisAndTreatmentGroupIteam bloodTest = new ClinicalDiagnosisAndTreatmentGroupIteam(titleKey[0]);
    ClinicalDiagnosisAndTreatmentGroupIteam urineTest = new ClinicalDiagnosisAndTreatmentGroupIteam(titleKey[1]);
    ClinicalDiagnosisAndTreatmentGroupIteam faecesTest = new ClinicalDiagnosisAndTreatmentGroupIteam(titleKey[2]);
    ClinicalDiagnosisAndTreatmentGroupIteam specialTest = new ClinicalDiagnosisAndTreatmentGroupIteam(titleKey[3]);
    ClinicalDiagnosisAndTreatmentGroupIteam other = new ClinicalDiagnosisAndTreatmentGroupIteam(titleKey[4]);
    List<PatientRecordDisplayBean> bloodTestList = new ArrayList<>();
    List<PatientRecordDisplayBean> urineTestList = new ArrayList<>();
    List<PatientRecordDisplayBean> faecesTestList = new ArrayList<>();
    List<PatientRecordDisplayBean> specialTestList = new ArrayList<>();
    List<PatientRecordDisplayBean> otherList = new ArrayList<>();
    private LabtestAdapter adapter;
    private IStrartActivity iStrartActivity;
    private CompatorUtil compatorUtil;
    private IGetMedicaHistoryRecord medicaHistoryRecord;

    public void setiStrartActivity(IStrartActivity iStrartActivity) {
        this.iStrartActivity = iStrartActivity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.labcheckfragment, null);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    @Override
    public void setData(List<PatientRecordDisplayBean> list) {
        bloodTestList.clear();
        urineTestList.clear();
        faecesTestList.clear();
        specialTestList.clear();
        otherList.clear();
        compatorUtil = new CompatorUtil();
        PatientRecordDisplayBean[] jsonNodes = compatorUtil.handleData(list);
        for (PatientRecordDisplayBean iteam : jsonNodes) {
            String value = iteam.subject;
            if (value.equals(titleValue[0])) {
                bloodTestList.add(iteam);
            } else if (value.equals(titleValue[1])) {
                urineTestList.add(iteam);
            } else if (value.equals(titleValue[2])) {
                faecesTestList.add(iteam);
            } else if (value.equals(titleValue[3])) {
                specialTestList.add(iteam);
            } else if (value.equals(titleValue[4])) {
                otherList.add(iteam);
            }
        }
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }

    }

    private Bundle bundle;

    @Override
    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    @Override
    public void init() {
        final IBundleFactory bundleFactory = new BundleFactory();
        bloodTest.setList(bloodTestList);
        urineTest.setList(urineTestList);
        faecesTest.setList(faecesTestList);
        specialTest.setList(specialTestList);
        other.setList(otherList);
        List<ClinicalDiagnosisAndTreatmentGroupIteam> list = new ArrayList<>();
        list.add(bloodTest);
        list.add(urineTest);
        list.add(faecesTest);
        list.add(specialTest);
        list.add(other);
        adapter = new LabtestAdapter(getContext(), list);
        expandableListView.setAdapter(adapter);
        expandableListView.setGroupIndicator(null);
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                iteam = null;
                switch (groupPosition) {
                    case 0:
                        iteam = bloodTestList.get(childPosition);
                        break;
                    case 1:
                        iteam = urineTestList.get(childPosition);
                        break;
                    case 2:
                        iteam = faecesTestList.get(childPosition);
                        break;
                    case 3:
                        iteam = specialTestList.get(childPosition);
                        break;
                    case 4:
                        iteam = otherList.get(childPosition);
                        break;
                }
                if (iteam != null) {
                    bundle.putSerializable("data", iteam);
                    ISetBundleHealthyRecord iSetBundleHealthyRecord = bundleFactory.getISetBundleHealthyRecord(bundle);
                    iSetBundleHealthyRecord.setAble(isEditable);
                    iStrartActivity.startActivityForResult(EditLabtestActivity.class, bundle, 12);
                }
                return false;
            }
        });
        LabTest_SR.setSize(SwipeRefreshLayout.DEFAULT);
        LabTest_SR.setColorSchemeResources(R.color.holo_blue_light_new
                , R.color.holo_blue_light_new,
                R.color.holo_blue_light_new, R.color.holo_blue_light_new);
        LabTest_SR.setProgressBackgroundColor(R.color.linelogin);
        LabTest_SR.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                medicaHistoryRecord.getDate();
                LabTest_SR.setRefreshing(false);

            }
        });
    }

    private PatientRecordDisplayBean iteam;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    @Override
    public void setEditable(boolean isEditable) {
        this.isEditable = isEditable;
    }

    public void setGetMedicaHistoryRecordListener(IGetMedicaHistoryRecord medicaHistoryRecord){
        this.medicaHistoryRecord = medicaHistoryRecord;
    }

    boolean isEditable;
}
