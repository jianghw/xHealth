package com.kaurihealth.mvplib.save_record_p;

import com.kaurihealth.datalib.local.LocalData;
import com.kaurihealth.datalib.repository.IDataSource;
import com.kaurihealth.datalib.repository.RepositoryFactory;
import com.kaurihealth.datalib.request_bean.bean.NewDocumentDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.NewPatientRecordDisplayBean;
import com.kaurihealth.datalib.response_bean.DocumentDisplayBean;
import com.kaurihealth.datalib.response_bean.PatientBean;
import com.kaurihealth.datalib.response_bean.PatientRecordDisplayBean;
import com.kaurihealth.datalib.response_bean.PatientRecordDisplayDto;
import com.kaurihealth.utilslib.image.RecordDocumentsBean;
import com.kaurihealth.utilslib.log.LogUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by jianghw on 2016/9/21.
 * <p/>
 * Describe:门诊诊疗
 */
public class OutpatientRecordSavePresenter<V> implements IOutpatientRecordSavePresenter<V> {
    private final IDataSource mRepository;
    private CompositeSubscription mSubscriptions;
    private IOutpatientRecordSaveView mActivity;
    private boolean mFirstLoad = true;

    @Inject
    public OutpatientRecordSavePresenter(IDataSource mRepository) {
        this.mRepository = mRepository;
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void setPresenter(V view) {
        mActivity = (IOutpatientRecordSaveView) view;
    }

    @Override
    public void onSubscribe() {
        if (mFirstLoad) loadingRemoteData(false);
    }

    @Override
    public void loadingRemoteData(boolean isDirty) {
        if (isDirty) mRepository.manuallyRefresh();

        compileRecordToSave();
    }

    /**
     * 编辑
     */
    private void compileRecordToSave() {
        uploadHandlingCases(new RepositoryFactory.UploadCaseSucceed() {
            @Override
            public void imageSucceed(List<DocumentDisplayBean> beanList, List<String> paths) {
                editDataProcessing(beanList, paths);
            }
        });
    }

    /**
     * 图片文件上传
     */
    private void uploadHandlingCases(RepositoryFactory.UploadCaseSucceed uploadSucceed) {
        List<String> paths = mActivity.getImagePathsList();//编辑后的地址集合
        PatientBean currentPatient = LocalData.getLocalData().getCurrentPatient();
        Subscription subscription = mRepository.getUploadListObservable(paths, currentPatient != null ? currentPatient.getKauriHealthId() : "0")
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mActivity.dataInteractionDialog(); //正在加载中...
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<DocumentDisplayBean>>() {
                    @Override
                    public void call(List<DocumentDisplayBean> beanList) {
                        uploadSucceed.imageSucceed(beanList, paths);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mActivity.displayErrorDialog(throwable.getMessage());
                    }
                });
        mSubscriptions.add(subscription);
    }

    /**
     * 编辑操作
     */
    private void editDataProcessing(List<DocumentDisplayBean> beanList, List<String> paths) {
        PatientRecordDisplayDto patientRecordDisplayBean = mActivity.getRequestPatientRecordBean();
        List<RecordDocumentsBean> list = patientRecordDisplayBean.getRecordDocuments();
        //标记已删除的网络图片
        if (list != null && list.size() > 0)
            markDeleteImages(paths, patientRecordDisplayBean, list);
        //添加本地图片
        if (beanList.size() > 0)
            addDocumentDisplayBeanToList(beanList, patientRecordDisplayBean);
        //更新bean
        updatePatientRecord(patientRecordDisplayBean);
    }

    /**
     * 标记已经删除的图片，改为true，更新内部bean
     * 1、原list有地址集时，把paths中不存在相同的地址 标记为删除 true
     * 2、转集合
     * 3、改原bean
     */
    private void markDeleteImages(final List<String> paths, PatientRecordDisplayDto patientRecordDisplayBean, List<RecordDocumentsBean> list) {
        Observable.from(list)
                .map(new Func1<RecordDocumentsBean, RecordDocumentsBean>() {
                    @Override
                    public RecordDocumentsBean call(RecordDocumentsBean recordDocumentsBean) {
                        if (!paths.contains(recordDocumentsBean.getDocumentUrl()))
                            recordDocumentsBean.setIsDeleted(true);
                        return recordDocumentsBean;
                    }
                })
                .toList()
                .subscribe(new Action1<List<RecordDocumentsBean>>() {
                    @Override
                    public void call(List<RecordDocumentsBean> recordDocumentsBeen) {
                        patientRecordDisplayBean.setRecordDocuments(recordDocumentsBeen);
                    }
                });
    }

    /**
     * 构建编辑中添加图片对应bean id设置0
     */
    private void addDocumentDisplayBeanToList(List<DocumentDisplayBean> beanList, PatientRecordDisplayDto patientRecordDisplayBean) {
        List<RecordDocumentsBean> list = patientRecordDisplayBean.getRecordDocuments();
        if (list == null) list = new ArrayList<>();
        for (DocumentDisplayBean documentDisplayBean : beanList) {
            RecordDocumentsBean recordDocumentsBean = new RecordDocumentsBean();
            recordDocumentsBean.setRecordDocumentId(0);
            recordDocumentsBean.setDocumentUrl(documentDisplayBean.getDocumentUrl());
            recordDocumentsBean.setFileName(documentDisplayBean.getFileName());
            recordDocumentsBean.setDocumentFormat(documentDisplayBean.getDocumentFormat());
            recordDocumentsBean.setDisplayName(documentDisplayBean.getDisplayName());
            recordDocumentsBean.setPatientRecordId(patientRecordDisplayBean.getPatientRecordId());
            recordDocumentsBean.setIsDeleted(false);
            list.add(recordDocumentsBean);
        }
        patientRecordDisplayBean.setRecordDocuments(list);
    }

    /**
     * 提交更新
     */
    private void updatePatientRecord(PatientRecordDisplayDto patientRecordDisplayBean) {
        Subscription subscription = mRepository.updatePatientRecord(patientRecordDisplayBean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<PatientRecordDisplayDto>() {
                    @Override
                    public void onCompleted() {
                        mActivity.dismissInteractionDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mActivity.displayErrorDialog(e.getMessage());
                    }

                    @Override
                    public void onNext(PatientRecordDisplayDto patientBean) {
                        LogUtils.jsonDate(patientBean);
                        mActivity.updatePatientRecordSucceed(patientBean);
                        mActivity.showToast("保存成功！");
                    }
                });
        mSubscriptions.add(subscription);
    }

    /**************门诊记录  添加**************/
    public void addNewPatientRecord() {
        uploadHandlingCases(new RepositoryFactory.UploadCaseSucceed() {
            @Override
            public void imageSucceed(List<DocumentDisplayBean> beanList,List<String> paths) {
                addDataProcessing(beanList, paths);
            }
        });
    }

    private void addDataProcessing(List<DocumentDisplayBean> beanList, List<String> paths) {
        NewPatientRecordDisplayBean newPatientRecordDisplayBean = mActivity.getNewPatientRecordDisplayBean();
        LogUtils.jsonDate(newPatientRecordDisplayBean);
        List<NewDocumentDisplayBean> list = new ArrayList<>();
        //添加本地图片
        if (beanList != null && beanList.size() > 0){
            for (DocumentDisplayBean documentDisplayBean : beanList) {
                NewDocumentDisplayBean newDocumentDisplayBean = new NewDocumentDisplayBean();

                newDocumentDisplayBean.setDocumentUrl(documentDisplayBean.getDocumentUrl());
                newDocumentDisplayBean.setDocumentFormat(documentDisplayBean.getDocumentFormat());
                newDocumentDisplayBean.setDisplayName(documentDisplayBean.getDisplayName());
                newDocumentDisplayBean.setFileName(documentDisplayBean.getFileName());
                list.add(newDocumentDisplayBean);
            }
            newPatientRecordDisplayBean.setRecordDocuments(list);
        }
        addNewRecord(newPatientRecordDisplayBean);
    }

    private void addNewRecord(NewPatientRecordDisplayBean newPatientRecordDisplayBean) {
        Subscription subscription = mRepository.addNewPatientRecord(newPatientRecordDisplayBean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<PatientRecordDisplayBean>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e(e.getMessage());
                        mActivity.displayErrorDialog(e.getMessage());
                    }

                    @Override
                    public void onNext(PatientRecordDisplayBean patientRecordDisplayBean) {
                        LogUtils.jsonDate(patientRecordDisplayBean);
                        mActivity.updatePatientRecordSucceed(null);
                        mActivity.showToast("插入成功！");
                    }
                });
        mSubscriptions.add(subscription);
    }



    @Override
    public void unSubscribe() {
        if (mActivity != null) mActivity.dismissInteractionDialog();
        mSubscriptions.clear();
        mActivity = null;
    }
}
