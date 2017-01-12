package com.kaurihealth.kaurihealth.save_record_v;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.kaurihealth.datalib.local.LocalData;
import com.kaurihealth.datalib.request_bean.bean.NewPathologyPatientRecordDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.NewSupplementaryTestPatientRecordDisplayBean;
import com.kaurihealth.datalib.response_bean.DepartmentDisplayBean;
import com.kaurihealth.datalib.response_bean.DoctorDisplayBean;
import com.kaurihealth.datalib.response_bean.PatientRecordDisplayDto;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.kaurihealth.eventbus.AccessoryRecordAddEvent;
import com.kaurihealth.kaurihealth.eventbus.HospitalRecordCompileEvent;
import com.kaurihealth.kaurihealth.eventbus.RefreshEvent;
import com.kaurihealth.kaurihealth.manager_v.ViewFactoryManager;
import com.kaurihealth.kaurihealth.manager_v.loading.LoadingErrorFactory;
import com.kaurihealth.kaurihealth.manager_v.save.AccessoryRecordSaveFactory;
import com.kaurihealth.kaurihealth.tinker.SampleApplicationLike;
import com.kaurihealth.mvplib.save_record_p.AccessoryCommonSavePresenter;
import com.kaurihealth.mvplib.save_record_p.IAccessoryCommonSaveView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by mip .
 * <p/>
 * Describle: 保存通用辅助检查
 */
public class AccessoryRecordCommonSaveActivity extends BaseActivity implements IAccessoryCommonSaveView{

    @Bind(R.id.tv_more)
    TextView mTvMore;
    @Bind(R.id.lay_content)
    LinearLayout mLayContent;
    @Bind(R.id.scrollView)
    ScrollView mScrollView;

    @Inject
    AccessoryCommonSavePresenter<IAccessoryCommonSaveView> mPresenter;

    private AccessoryRecordSaveFactory viewFactory;
    private LoadingErrorFactory errorFactory;
    private String mAdd;

    @Override
    protected int getActivityLayoutID() {
        return R.layout.activity_hospital_compile;
    }

    @Override
    protected void initPresenterAndView(Bundle savedInstanceState) {
        SampleApplicationLike.getApp().getComponent().inject(this);
        mPresenter.setPresenter(this);

        initNewBackBtn(getBundle().getString("ICONOGRAPHYTEST"));
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
//            LogUtils.jsonDate(displayDto);
        } else {
            loadPatientRecordError("数据出错,请退出当前页面");
        }
    }

    /**
     * 添加
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void eventBusMain(AccessoryRecordAddEvent event) {
        mAdd = event.getAdd();
        PatientRecordDisplayDto displayDto= event.getPatientRecordDisplayDto();
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
        removeStickyEvent(AccessoryRecordAddEvent.class);
        EventBus.getDefault().unregister(this);
        mPresenter.unSubscribe();
    }

    /**
     * 成功布局
     */
    public void loadPatientRecordSucceed(PatientRecordDisplayDto displayDto) {
        if (errorFactory != null) errorFactory.hiddenLayout();

        if (viewFactory == null) {
            viewFactory = (AccessoryRecordSaveFactory) ViewFactoryManager.createRecordAccessoryViewFactory(this, mLayContent,getBundle().getString("ICONOGRAPHYTEST"));
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

        if (mAdd != null){
            if (mAdd.equals("病理")){
                //病理添加
                if (viewFactory.getBoolean() == true){
                    displayErrorDialog("没有填写完整");
                }else {
                    mPresenter.addNewPathologyRecord();
                }
            }else {
                //辅助添加
                if (viewFactory.getBoolean() == true){
                    displayErrorDialog("没有填写完整");
                }else {
                    mPresenter.addNewPatientRecord();
                }
            }
        }else {
            //辅助、病理编辑更新
            if (viewFactory.getBoolean() == true){
                displayErrorDialog("没有填写完整");
            }else {
                mPresenter.loadingRemoteData(true);
            }
        }
    }

    /**
     * 权限回调
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (viewFactory != null)   viewFactory.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (viewFactory != null)   viewFactory.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 得到图片集
     * @return
     */
    @Override
    public List<String> getImagePathsList() {
        return viewFactory != null ? viewFactory.getImagePathsList() : null;
    }

    /**
     * 得到编辑请求bean
     * @return
     */
    @Override
    public PatientRecordDisplayDto getRequestPatientRecordBean() {
        return viewFactory != null ? viewFactory.getRequestPatientRecordBean() : new PatientRecordDisplayDto();
    }

    /**
     * 得到新的插入辅助检查bean
     */
    @Override
    public NewSupplementaryTestPatientRecordDisplayBean getNewSupplementaryTestPatientRecordDisplayBean() {
        return viewFactory != null ? viewFactory.getNewSupplementaryTestPatientRecordDisplayBean() : new NewSupplementaryTestPatientRecordDisplayBean();
    }

    /**
     * 得到新的插入病理bean
     */
    @Override
    public NewPathologyPatientRecordDisplayBean getNewPathologyPatientRecordDisplayBean() {
        return viewFactory != null ? viewFactory.getNewPathologyPatientRecordDisplayBean() : new NewPathologyPatientRecordDisplayBean();
    }


    /**
     * 更新成功回调
     * @param bean
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
