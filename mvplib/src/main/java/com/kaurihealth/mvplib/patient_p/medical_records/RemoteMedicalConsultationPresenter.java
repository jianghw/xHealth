package com.kaurihealth.mvplib.patient_p.medical_records;

import com.kaurihealth.datalib.repository.IDataSource;
import com.kaurihealth.datalib.repository.RepositoryFactory;
import com.kaurihealth.datalib.request_bean.bean.NewDocumentDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.NewPatientRecordDisplayBean;
import com.kaurihealth.datalib.response_bean.DocumentDisplayBean;
import com.kaurihealth.datalib.response_bean.PatientRecordDisplayBean;
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
 * Created by Garnet_Wu on 2016/9/18.
 */
public class RemoteMedicalConsultationPresenter<V> implements IRemoteMedicalConsultationPresenter<V>{
    private final IDataSource mRepository;
    private  CompositeSubscription mSubsciptions;
    private  IRemoteMedicalConsultationView mActivity;

    @Inject
    RemoteMedicalConsultationPresenter(IDataSource repository){
        mRepository = repository;
        mSubsciptions = new CompositeSubscription();
    }

   //IMvpPresenter  初始化Presenter
    @Override
    public void setPresenter(V view) {
       mActivity = (IRemoteMedicalConsultationView)view;
    }

    //IMvpPresenter  编辑
    @Override
    public void onSubscribe() {
        uploadHandlingCases(new RepositoryFactory.UploadCaseSucceed() {
            @Override
            public void imageSucceed(List<DocumentDisplayBean> beanList, List<String> paths) {
                editDataProcessing(beanList,paths);  //编辑操作
            }
        });
    }

    /**
     * 编辑操作
     * @param beanList
     * @param paths
     */
    private void editDataProcessing(List<DocumentDisplayBean> beanList, List<String> paths) {
        PatientRecordDisplayBean requestPatientRecordBean = mActivity.getRequestPatientRecordBean();


    }

    //IMvpPresenter  解除绑定
    @Override
    public void unSubscribe() {

    }

    /**
     * 标记已经删除的图片, 改为true ,更新内部的bean
     * 1. 原list有地址集时,把paths中不存在相同的地址 标记为删除 true
     * 2. 转集合
     * 3.改原bean
     */
    @Override
    public void markDeleteImages(ArrayList<String> paths, PatientRecordDisplayBean patientRecordDisplayBean, List<RecordDocumentsBean> list) {
        Observable.from(list)
                .map(new Func1<RecordDocumentsBean, RecordDocumentsBean>() {
                    @Override
                    public RecordDocumentsBean call(RecordDocumentsBean recordDocumentsBean) {
                        if (!paths.contains(recordDocumentsBean.getDocumentUrl())) {
                            recordDocumentsBean.setIsDeleted(true);
                        }
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
     * 重写： IRemoteMedicalConsultationPresenter
     * 插入新的临床诊疗记录 医生和患者的token都可以
     */
    @Override
    public void addNewPatientRecord() {
        uploadHandlingCases(new RepositoryFactory.UploadCaseSucceed() {
            @Override
            public void imageSucceed(List<DocumentDisplayBean> beanList,List<String> paths) {
                addDataProcesing(beanList,paths);
            }


        });
    }


    private void addDataProcesing(List<DocumentDisplayBean> beanList, List<String> paths) {
        NewPatientRecordDisplayBean newPatientRecordDisplayBean = mActivity.getNewPatientRecordDisplayBean();
        //LogUtils看bean 内容
        LogUtils.jsonDate(newPatientRecordDisplayBean);
        ArrayList<NewDocumentDisplayBean> list = new ArrayList<>();
        //添加本地图片
        if (beanList != null && beanList.size() > 0) {
            for (DocumentDisplayBean documentDisplayBean: beanList) {
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
                        mActivity.dismissInteractionDialog();  //正在加载中....
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e(e.getMessage());
                        mActivity.displayErrorDialog(e.getMessage());
                    }

                    @Override
                    public void onNext(PatientRecordDisplayBean patientRecordDisplayBean) {
                        //查看patientRecordDisplayBean
                        LogUtils.jsonDate(patientRecordDisplayBean);
                        mActivity.updateSucceed(patientRecordDisplayBean);
                    }
                });
        mSubsciptions.add(subscription);
    }


    /**
     * 图片文件上传
     */
    private void uploadHandlingCases(RepositoryFactory.UploadCaseSucceed uploadSucceed){
        ArrayList<String> paths = mActivity.getPathsList();//编辑后的地址集合
        Subscription subscription = mRepository.getUploadListObservable(paths, mActivity.getKauriHealthId())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mActivity.dataInteractionDialog();  //正在加载中....
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

        mSubsciptions.add(subscription);
    }


}
