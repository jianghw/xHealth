package com.kaurihealth.kaurihealth.save_record_v;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.kaurihealth.datalib.local.LocalData;
import com.kaurihealth.datalib.request_bean.bean.NewHospitalizationPatientRecordDisplayBean;
import com.kaurihealth.datalib.response_bean.DepartmentDisplayBean;
import com.kaurihealth.datalib.response_bean.DoctorDisplayBean;
import com.kaurihealth.datalib.response_bean.PatientRecordDisplayDto;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.kaurihealth.eventbus.HospitalRecordAddEvent;
import com.kaurihealth.kaurihealth.eventbus.HospitalRecordCompileEvent;
import com.kaurihealth.kaurihealth.eventbus.RefreshEvent;
import com.kaurihealth.kaurihealth.manager_v.ViewFactoryManager;
import com.kaurihealth.kaurihealth.manager_v.loading.LoadingErrorFactory;
import com.kaurihealth.kaurihealth.manager_v.save.HospitalRecordSaveFactory;
import com.kaurihealth.kaurihealth.tinker.SampleApplicationLike;
import com.kaurihealth.mvplib.save_record_p.HospitalRecordPresenter;
import com.kaurihealth.mvplib.save_record_p.IHospitalRecordView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by jianghw .
 * <p/>
 * Describle: 保存住院记录
 */
public class HospitalRecordSaveActivity extends BaseActivity implements IHospitalRecordView {

    @Bind(R.id.tv_more)
    TextView mTvMore;
    @Bind(R.id.lay_content)
    LinearLayout mLayContent;
    @Bind(R.id.scrollView)
    ScrollView mScrollView;

    private HospitalRecordSaveFactory viewFactory;
    private LoadingErrorFactory errorFactory;
    private String mAdd;

    @Inject
    HospitalRecordPresenter<IHospitalRecordView> mPresenter;

    @Override
    protected int getActivityLayoutID() {
        return R.layout.activity_hospital_compile;
    }

    @Override
    protected void initPresenterAndView(Bundle savedInstanceState) {
        SampleApplicationLike.getApp().getComponent().inject(this);
        mPresenter.setPresenter(this);

        initNewBackBtn("住院记录");
        mTvMore.setText(getString(R.string.title_save));
    }

    @Override
    protected void initDelayedData() {
        EventBus.getDefault().register(this);
    }

    /**
     * 编辑
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void eventBusMain(HospitalRecordCompileEvent event) {
        PatientRecordDisplayDto displayDto = event.getPatientRecordDisplayDto();
        if (displayDto != null) {
            loadPatientRecordSucceed(displayDto);
        } else {
            loadPatientRecordError("数据出错,请退出当前页面");
        }
    }


    /**
     * 添加
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void eventBusMain(HospitalRecordAddEvent event) {
        mAdd = event.getAdd();
        PatientRecordDisplayDto displayDto= event.getPatientRecordDisplayDto();
        displayDto.mark = "mark";
        setMustItem(displayDto);
        loadPatientRecordSucceed(displayDto);
    }

    /**
     *  必填项
     */
    private void setMustItem(PatientRecordDisplayDto displayDto){
        PatientRecordDisplayDto recordDisplayDto = displayDto;
        DoctorDisplayBean myself = LocalData.getLocalData().getMyself();
        recordDisplayDto.setDoctor(myself.getFullName());
        if (myself.getDoctorInformations()!=null){
            if (myself.getDoctorInformations().getDepartment()!=null){
                DepartmentDisplayBean department = new DepartmentDisplayBean();
                department.setDepartmentName(myself.getDoctorInformations().getDepartment().getDepartmentName());
                recordDisplayDto.setDepartment(department);
            }
        }
        recordDisplayDto.setHospital(myself.getDoctorInformations().getHospitalName());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (viewFactory != null) viewFactory.unbindView();
        if (errorFactory != null) errorFactory.unbindView();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 成功布局
     */
    public void loadPatientRecordSucceed(PatientRecordDisplayDto displayDto) {
        if (errorFactory != null) errorFactory.hiddenLayout();

        if (viewFactory == null) {
            viewFactory = (HospitalRecordSaveFactory) ViewFactoryManager.createViewFactory(this, mLayContent);
        } else {
            viewFactory.showLayout();
        }
        if (viewFactory != null) {
            viewFactory.fillNewestData(displayDto, 0);
        }
        mScrollView.scrollTo(0, 0);
    }

    /**
     * 失败布局
     */
    public void loadPatientRecordError(String message) {
        if (viewFactory != null) viewFactory.hiddenLayout();

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
     * 保存
     */
    @OnClick(R.id.tv_more)
    public void addMoreClick() {
        if (mAdd != null) {
            //TODO 添加
            String isType = viewFactory.getBooleanString();
            if (isType.equals("入院")) {
                displayErrorDialog("没有填写完整入院记录");
            } else if (isType.equals("院内")) {
                showTypeInsertDialog("没有填写完整院内记录，确定只上传入院信息?", "入院");
            } else if (isType.equals("出院")) {
                showTypeInsertDialog("没有填写完整出院记录，确定只上传入院、院内信息?", "院内");
            } else {
                mPresenter.insertRecordToSave("出院");
            }
        } else {
            //TODO 编辑
            String isType = viewFactory.getBooleanString();
            if (isType.equals("入院")) {
                displayErrorDialog("没有填写完整入院记录");
            } else if (isType.equals("院内")) {
                showTypeDialog("没有填写完整院内记录，确定只上传入院信息?", "入院");
            } else if (isType.equals("出院")) {
                showTypeDialog("没有填写完整出院记录，确定只上传入院、院内信息?", "院内");
            } else {
                mPresenter.compileRecordToSave("出院");
            }
        }
    }

    private void showTypeDialog(String message, String mType) {
        SweetAlertDialog commitDataDialog = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("提示")
                .setContentText(message)
                .setConfirmText("确定")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                        mPresenter.compileRecordToSave(mType);
                    }
                })
                .setCancelText("取消")
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                    }
                });
        commitDataDialog.show();
    }

    /**
     * 插入
     */
    private void showTypeInsertDialog(String message, String mType) {
        SweetAlertDialog commitDataDialog = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("提示")
                .setContentText(message)
                .setConfirmText("确定")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                        mPresenter.insertRecordToSave(mType);
                    }
                })
                .setCancelText("取消")
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                    }
                });
        commitDataDialog.show();
    }

    /**
     * 权限回调
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (viewFactory != null) viewFactory.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (viewFactory != null) viewFactory.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 图片集合
     */
    @Override
    public List<String> getImagePathsList() {
        return null;
    }

    /**
     * 入院图片
     */
    @Override
    public List<String> getAdmissionImagePathsList() {
        return viewFactory != null ? viewFactory.getAdmissionImagePathsList() : new ArrayList<>();
    }

    /**
     * 院内图片
     */
    @Override
    public List<String> getCourtImagePathsList() {
        return viewFactory != null ? viewFactory.getCourtImagePathsList() : new ArrayList<>();
    }

    /**
     * 出院图片
     */
    @Override
    public List<String> getOutImagePathsList() {
        return viewFactory != null ? viewFactory.getOutImagePathsList() : new ArrayList<>();
    }

    /**
     * 入院编辑bean
     */
    @Override
    public PatientRecordDisplayDto getAdmissionRequestPatientRecordBean() {
        return viewFactory != null ? viewFactory.getAdmissionRequestPatientRecordBean() : new PatientRecordDisplayDto();
    }

    /**
     * 院内编辑bean
     */
    @Override
    public PatientRecordDisplayDto getCourtRequestPatientRecordBean() {
        return viewFactory != null ? viewFactory.getCourtRequestPatientRecordBean() : new PatientRecordDisplayDto();
    }

    /**
     * 出院编辑bean
     */
    @Override
    public PatientRecordDisplayDto getOutRequestPatientRecordBean() {
        return viewFactory != null ? viewFactory.getOutRequestPatientRecordBean() : new PatientRecordDisplayDto();
    }

    @Override
    public NewHospitalizationPatientRecordDisplayBean getAdmissionInsertRecordBean() {
        return viewFactory != null ? viewFactory.getAdmissionInsertRecordBean() : new NewHospitalizationPatientRecordDisplayBean();
    }

    @Override
    public NewHospitalizationPatientRecordDisplayBean getCourtInsertRecordBean() {
        return viewFactory != null ? viewFactory.getCourtInsertRecordBean() : new NewHospitalizationPatientRecordDisplayBean();
    }

    @Override
    public NewHospitalizationPatientRecordDisplayBean getOutInsertRecordBean() {
        return viewFactory != null ? viewFactory.getOutInsertRecordBean() : new NewHospitalizationPatientRecordDisplayBean();
    }


    /**
     * 更新成功回调
     */
    @Override
    public void updatePatientRecordSucceed(PatientRecordDisplayDto bean) {
        EventBus.getDefault().postSticky(new RefreshEvent());//刷新
        setResult(RESULT_OK);
        finishCur();
    }

    @Override
    public void switchPageUI(String className) {
    }
}
