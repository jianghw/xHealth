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
import com.kaurihealth.kaurihealth.common.activity.EditCommonMedicalRecordActivityNew;
import com.kaurihealth.kaurihealth.common.adapter.ClinicalDiagnAndTreatAdapter;
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
 * 描述：辅助检查
 * 创建日期:2016/1/22
 * 修订日期:
 */
public class SupplementaryTestFragment extends CommonFragment implements FragmentData<PatientRecordDisplayBean>, FragmentControl {
    String[] titleKey = {"影像学检查", "心血管系统相关检查", "其它检查"};
    String[] titleValue = {"影像学检查", "心血管系统相关检查", "其它检查"};
    @Bind(R.id.animexpandableview_content_clinicatreatment)
    AnimatedExpandableListView expandableListView;
    @Bind(R.id.Supplementary_SR)
    SwipeRefreshLayout Supplementary_SR;
    private View view;
    ClinicalDiagnosisAndTreatmentGroupIteam imageologicalTest = new ClinicalDiagnosisAndTreatmentGroupIteam(titleKey[0]);
    ClinicalDiagnosisAndTreatmentGroupIteam cardiovascularSystem = new ClinicalDiagnosisAndTreatmentGroupIteam(titleKey[1]);
    ClinicalDiagnosisAndTreatmentGroupIteam other = new ClinicalDiagnosisAndTreatmentGroupIteam(titleKey[2]);
    List<PatientRecordDisplayBean> imageologicalTestList = new ArrayList<>();
    List<PatientRecordDisplayBean> cardiovascularSystemList = new ArrayList<>();
    List<PatientRecordDisplayBean> otherList = new ArrayList<>();
    private ClinicalDiagnAndTreatAdapter adapter;
    private IStrartActivity iStrartActivity;
    private CompatorUtil compatorUtil;
    private IGetMedicaHistoryRecord medicaHistoryRecord;

    public void setiStrartActivity(IStrartActivity iStrartActivity) {
        this.iStrartActivity = iStrartActivity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.assistcheckfragment, null);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    @Override
    public void setData(List<PatientRecordDisplayBean> list) {
        imageologicalTestList.clear();
        cardiovascularSystemList.clear();
        otherList.clear();
        compatorUtil = new CompatorUtil();
        PatientRecordDisplayBean[] jsonNodes = compatorUtil.handleData(list);
        for (PatientRecordDisplayBean iteam : jsonNodes) {
            String value = iteam.subject;
            if (value.equals(titleValue[0])) {
                imageologicalTestList.add(iteam);
            } else if (value.equals(titleValue[1])) {
                cardiovascularSystemList.add(iteam);
            } else if (value.equals(titleValue[2])) {
                otherList.add(iteam);
            }
        }
        if (adapter!=null) {
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
        super.init();
        final IBundleFactory bundleFactory = new BundleFactory();
        imageologicalTest.setList(imageologicalTestList);
        cardiovascularSystem.setList(cardiovascularSystemList);
        other.setList(otherList);
        List<ClinicalDiagnosisAndTreatmentGroupIteam> list = new ArrayList<>();
        list.add(imageologicalTest);
        list.add(cardiovascularSystem);
        list.add(other);
        adapter = new ClinicalDiagnAndTreatAdapter(getContext(), list);
        expandableListView.setAdapter(adapter);
        expandableListView.setGroupIndicator(null);
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                iteam = null;
                switch (groupPosition) {
                    case 0:
                        iteam = imageologicalTestList.get(childPosition);
                        break;
                    case 1:
                        iteam = cardiovascularSystemList.get(childPosition);
                        break;
                    case 2:
                        iteam = otherList.get(childPosition);
                        break;
                }
                if (iteam != null) {
                    bundle.putSerializable("data", iteam);
                    ISetBundleHealthyRecord iSetBundleHealthyRecord = bundleFactory.getISetBundleHealthyRecord(bundle);
                    iSetBundleHealthyRecord.setAble(isEditable);
                    iStrartActivity.startActivityForResult(EditCommonMedicalRecordActivityNew.class, bundle, 13);
                }
                return false;
            }
        });
        Supplementary_SR.setSize(SwipeRefreshLayout.DEFAULT);
        Supplementary_SR.setColorSchemeResources(R.color.holo_blue_light_new
                , R.color.holo_blue_light_new,
                R.color.holo_blue_light_new, R.color.holo_blue_light_new);
        Supplementary_SR.setProgressBackgroundColor(R.color.linelogin);
        Supplementary_SR.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                medicaHistoryRecord.getDate();
                Supplementary_SR.setRefreshing(false);

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
