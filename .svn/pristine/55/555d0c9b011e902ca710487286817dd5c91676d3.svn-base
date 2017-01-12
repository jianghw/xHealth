package com.kaurihealth.kaurihealth.manager_v.history;

import android.content.Context;
import android.view.ViewGroup;

import com.kaurihealth.datalib.response_bean.PatientRecordDisplayDto;
import com.kaurihealth.utilslib.controller.IViewFactory;

import java.util.List;

/**
 * Created by mip on 2016/12/26.
 *
 * Descride:实验室检查组装
 */

public class LobTestHistoryFactory implements IViewFactory<List<PatientRecordDisplayDto>>{

    private final Context mContext;

    /**
     * 常规血液学检查
     */
    private RoutineHematologyTestView hematologyTestView;

    /**
     * 常规尿液检查
     */
    private RoutineUrineTestView urineTestView;

    /**
     * 常规粪便检查
     */
    private NormalFecesTestView fecesTestView;

    /**
     * 特殊检查
     */
    private SpecialTestView specialTestView;

    /**
     * 其他
     */
    private OtherOfLabTestView otherOfLabTestView;

    public LobTestHistoryFactory(Context context){
        this.mContext = context;
    }


    @Override
    public void createIncludeViews() {
        hematologyTestView = new RoutineHematologyTestView(mContext);
        urineTestView = new RoutineUrineTestView(mContext);
        fecesTestView = new NormalFecesTestView(mContext);
        specialTestView = new SpecialTestView(mContext);
        otherOfLabTestView = new OtherOfLabTestView(mContext);
    }

    @Override
    public void attachRootView(ViewGroup layContent) {
        hematologyTestView.attachRoot(layContent);
        urineTestView.attachRoot(layContent);
        fecesTestView.attachRoot(layContent);
        specialTestView.attachRoot(layContent);
        otherOfLabTestView.attachRoot(layContent);
    }

    @Override
    public void fillNewestData(List<PatientRecordDisplayDto> beanData, int position) {
        switch (position){
            case 1:
                hematologyTestView.fillData(beanData);
                break;
            case 2:
                urineTestView.fillData(beanData);
                break;
            case 3:
                fecesTestView.fillData(beanData);
                break;
            case 4:
                specialTestView.fillData(beanData);
                break;
            case 5:
                otherOfLabTestView.fillData(beanData);
                break;
            default:
        }


    }

    @Override
    public void unbindView() {
        hematologyTestView.unbindView();
        urineTestView.unbindView();
        fecesTestView.unbindView();
        specialTestView.unbindView();
        otherOfLabTestView.unbindView();
    }

    @Override
    public void showLayout() {
        hematologyTestView.showLayout();
        urineTestView.showLayout();
        fecesTestView.showLayout();
        specialTestView.showLayout();
        otherOfLabTestView.showLayout();
    }

    @Override
    public void hiddenLayout() {
        hematologyTestView.hiddenLayout();
        urineTestView.hiddenLayout();
        fecesTestView.hiddenLayout();
        specialTestView.hiddenLayout();
        otherOfLabTestView.hiddenLayout();
    }
}
