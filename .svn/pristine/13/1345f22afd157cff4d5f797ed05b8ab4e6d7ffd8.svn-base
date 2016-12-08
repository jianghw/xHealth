package com.kaurihealth.mvplib.patient_p.medical_records;

import com.kaurihealth.datalib.repository.IDataSource;
import com.kaurihealth.datalib.repository.RepositoryFactory;
import com.kaurihealth.datalib.request_bean.bean.NewDocumentDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.NewPatientRecordDisplayBean;
import com.kaurihealth.datalib.response_bean.DocumentDisplayBean;
import com.kaurihealth.datalib.response_bean.PatientRecordDisplayBean;
import com.kaurihealth.datalib.response_bean.RecordDocumentsBean;
import com.kaurihealth.datalib.response_bean.ResponseDisplayBean;
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
 * Created by jianghw on 2016/9/14.
 * <p/>
 * 描述：
 */
public class OutpatientElectronicPresenter<V> implements IOutpatientElectronicPresenter<V> {

    private final IDataSource mRepository;
    private CompositeSubscription mSubscriptions;
    private IOutpatientElectronicView mActivity;

    @Inject
    OutpatientElectronicPresenter(IDataSource repository) {
        mRepository = repository;
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void setPresenter(V view) {
        mActivity = (IOutpatientElectronicView) view;
    }

    /**
     * 编辑
     */
    @Override
    public void onSubscribe() {
        uploadHandlingCases(new RepositoryFactory.UploadCaseSucceed() {
            @Override
            public void imageSucceed(List<DocumentDisplayBean> beanList, ArrayList<String> paths) {
                editDataProcessing(beanList, paths);
            }
        });
    }

    /**
     * 图片文件上传
     */
    private void uploadHandlingCases(RepositoryFactory.UploadCaseSucceed uploadSucceed) {
        ArrayList<String> paths = mActivity.getPathsList();//编辑后的地址集合
        Subscription subscription = mRepository.getUploadListObservable(paths, mActivity.getKauriHealthId())
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
    private void editDataProcessing(List<DocumentDisplayBean> beanList, ArrayList<String> paths) {
        PatientRecordDisplayBean patientRecordDisplayBean = mActivity.getRequestPatientRecordBean();
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
     * 构建编辑中添加图片对应bean id设置0
     */
    private void addDocumentDisplayBeanToList(List<DocumentDisplayBean> beanList, PatientRecordDisplayBean patientRecordDisplayBean) {
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
     * 标记已经删除的图片，改为true，更新内部bean
     * 1、原list有地址集时，把paths中不存在相同的地址 标记为删除 true
     * 2、转集合
     * 3、改原bean
     */
    private void markDeleteImages(final ArrayList<String> paths, PatientRecordDisplayBean patientRecordDisplayBean, List<RecordDocumentsBean> list) {
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
     * 提交更新
     */
    private void updatePatientRecord(PatientRecordDisplayBean patientRecordDisplayBean) {
        Subscription subscription = mRepository.updatePatientRecord(patientRecordDisplayBean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<PatientRecordDisplayBean>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        mActivity.displayErrorDialog(e.getMessage());
                    }

                    @Override
                    public void onNext(PatientRecordDisplayBean patientBean) {
                        LogUtils.jsonDate(patientBean);
                        mActivity.updateSucceed(patientBean);
                        mActivity.showToast("保存成功！");

                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public void unSubscribe() {
        mSubscriptions.clear();
        mActivity = null;
    }

    /**
     * 重写：IOutpatientElectronicPresenter
     * 插入新的临床诊疗记录 医生和患者的token都可以
     */
    @Override
    public void addNewPatientRecord() {
        uploadHandlingCases(new RepositoryFactory.UploadCaseSucceed() {
            @Override
            public void imageSucceed(List<DocumentDisplayBean> beanList, ArrayList<String> paths) {
                addDataProcessing(beanList, paths);
            }
        });
    }

    private void addDataProcessing(List<DocumentDisplayBean> beanList, ArrayList<String> paths) {
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
                        mActivity.showToast("保存成功！");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e(e.getMessage());
                        mActivity.displayErrorDialog(e.getMessage());
                    }

                    @Override
                    public void onNext(PatientRecordDisplayBean patientRecordDisplayBean) {
                        LogUtils.jsonDate(patientRecordDisplayBean);
                        mActivity.updateSucceed(patientRecordDisplayBean);
                    }
                });
        mSubscriptions.add(subscription);
    }

    public void RequestEndDoctorPatientRelationship() {
        int doctorPatientReplationshipId = mActivity.getDoctorPatientId();
        Subscription subscription = mRepository.RequestEndDoctorPatientRelationship(doctorPatientReplationshipId)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(() -> {
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseDisplayBean>() {
                    @Override
                    public void onCompleted() {
                        mActivity.dismissInteractionDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e(e.getMessage());
                        mActivity.displayErrorDialog(e.getMessage());
                    }

                    @Override
                    public void onNext(ResponseDisplayBean responseDisplayBean) {
                        mActivity.finishPage();
                    }
                });
        mSubscriptions.add(subscription);
    }
}
