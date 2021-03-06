package com.kaurihealth.mvplib.patient_p.medical_records;

import com.kaurihealth.datalib.repository.IDataSource;
import com.kaurihealth.datalib.repository.RepositoryFactory;
import com.kaurihealth.datalib.request_bean.bean.NewPrescriptionBean;
import com.kaurihealth.datalib.request_bean.bean.PrescriptionBean;
import com.kaurihealth.datalib.response_bean.DocumentDisplayBean;
import com.kaurihealth.mvplib.patient_p.IAddAndEditPrescriptionPresenter;
import com.kaurihealth.mvplib.patient_p.IAddAndEditPrescriptionView;
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
 * Created by mip on 2016/9/28.
 */
public class AddAndEditPrescriptionPresenter<V> implements IAddAndEditPrescriptionPresenter<V> {
    private final IDataSource mRepository;
    private CompositeSubscription mSubscriptions;
    private IAddAndEditPrescriptionView mActivity;

    @Inject
    public AddAndEditPrescriptionPresenter(IDataSource mRepository) {
        this.mRepository = mRepository;
        mSubscriptions = new CompositeSubscription();
    }
    @Override
    public void setPresenter(V view) {
        mActivity = (IAddAndEditPrescriptionView) view;
    }

    @Override
    public void onSubscribe() {
        uploadHandlingCases(new RepositoryFactory.UploadCaseSucceed() {
            @Override
            public void imageSucceed(List<DocumentDisplayBean> beanList,List<String> paths) {
                editDataProcessing(beanList, paths);
            }
        });
    }

    /**
     * 编辑操作
     */
    private void editDataProcessing(List<DocumentDisplayBean> beanList, List<String> paths) {
        PrescriptionBean prescriptionBean = mActivity.getRequestPrescriptionBean();
        List<PrescriptionBean.PrescriptionDocumentsEntity> list = prescriptionBean.getPrescriptionDocuments();
        //标记已删除的网络图片
        if (list != null && list.size() > 0)
            markDeleteImages(paths, prescriptionBean, list);
        //添加本地图片
        if (beanList.size() > 0)
            addDocumentDisplayBeanToList(beanList, prescriptionBean);
        //更新bean
        updatePatientRecord(prescriptionBean);
    }

    private void markDeleteImages(final List<String> paths, PrescriptionBean prescriptionBean, List<PrescriptionBean.PrescriptionDocumentsEntity> list) {
        Observable.from(list)
                .map(new Func1<PrescriptionBean.PrescriptionDocumentsEntity, PrescriptionBean.PrescriptionDocumentsEntity>() {
                    @Override
                    public PrescriptionBean.PrescriptionDocumentsEntity call(PrescriptionBean.PrescriptionDocumentsEntity recordDocumentsBean) {
                        if (!paths.contains(recordDocumentsBean.getdocumentUrl()))
                            recordDocumentsBean.setIsDeleted(true);
                        return recordDocumentsBean;
                    }
                })
                .toList()
                .subscribe(new Action1<List<PrescriptionBean.PrescriptionDocumentsEntity>>() {
                    @Override
                    public void call(List<PrescriptionBean.PrescriptionDocumentsEntity> recordDocumentsBeen) {
                        prescriptionBean.setPrescriptionDocuments(recordDocumentsBeen);
                    }
                });
    }

    /**
     * 构建编辑中添加图片对应bean id设置0
     */
    private void addDocumentDisplayBeanToList(List<DocumentDisplayBean> beanList, PrescriptionBean prescriptionBean) {
        List<PrescriptionBean.PrescriptionDocumentsEntity> list = prescriptionBean.getPrescriptionDocuments();
        if (list == null) list = new ArrayList<>();
        for (DocumentDisplayBean documentDisplayBean : beanList) {
            PrescriptionBean.PrescriptionDocumentsEntity prescriptionDocumentsEntity = new PrescriptionBean.PrescriptionDocumentsEntity();
            prescriptionDocumentsEntity.setPrescriptionDocumentId(0);
            prescriptionDocumentsEntity.setDocumentUrl(documentDisplayBean.getDocumentUrl());
            prescriptionDocumentsEntity.setFileName(documentDisplayBean.getFileName());
            prescriptionDocumentsEntity.setDocumentFormat(documentDisplayBean.getDocumentFormat());
            prescriptionDocumentsEntity.setDisplayName(documentDisplayBean.getDisplayName());
            prescriptionDocumentsEntity.setPrescriptionId(prescriptionBean.getPrescriptionId());
            prescriptionDocumentsEntity.setIsDeleted(false);
            list.add(prescriptionDocumentsEntity);
        }
    }

    /**
     * 提交更新
     */
    private void updatePatientRecord(PrescriptionBean prescriptionBean) {
        Subscription subscription = mRepository.UpdatePrescription(prescriptionBean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<PrescriptionBean>() {
                    @Override
                    public void onCompleted() {
                        mActivity.dismissInteractionDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mActivity.displayErrorDialog(e.getMessage());
                    }

                    @Override
                    public void onNext(PrescriptionBean prescriptionBean1) {
                        LogUtils.jsonDate(prescriptionBean1);
                        mActivity.updateSucceed(prescriptionBean1);
                        mActivity.showToast("修改成功");
                    }
                });
        mSubscriptions.add(subscription);
    }


    @Override
    public void unSubscribe() {
            mSubscriptions.clear();
    }

    public void addNewPatientRecord() {
        uploadHandlingCases(new RepositoryFactory.UploadCaseSucceed() {
            @Override
            public void imageSucceed(List<DocumentDisplayBean> beanList,List<String> paths) {
                addDataProcessing(beanList, paths);
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

    private void addDataProcessing(List<DocumentDisplayBean> beanList, List<String> paths) {
        NewPrescriptionBean newPrescriptionBean = mActivity.getNewPrescriptionBean();
        List<NewPrescriptionBean.PrescriptionDocumentsBean> list = new ArrayList<>();
        //添加本地图片
        if (beanList != null && beanList.size() > 0){
            for (DocumentDisplayBean documentDisplayBean : beanList) {
                NewPrescriptionBean.PrescriptionDocumentsBean newDocumentDisplayBean =
                        new NewPrescriptionBean.PrescriptionDocumentsBean();

                newDocumentDisplayBean.setDocumentUrl(documentDisplayBean.getDocumentUrl());
                newDocumentDisplayBean.setDocumentFormat(documentDisplayBean.getDocumentFormat());
                newDocumentDisplayBean.setDisplayName(documentDisplayBean.getDisplayName());
                newDocumentDisplayBean.setFileName(documentDisplayBean.getFileName());
                list.add(newDocumentDisplayBean);
            }
            newPrescriptionBean.setPrescriptionDocuments(list);
        }
        addNewRecord(newPrescriptionBean);
    }

    //添加新的处方记录
    private void addNewRecord(NewPrescriptionBean newPrescriptionBean) {
        Subscription subscription = mRepository.insertPrescription(newPrescriptionBean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<PrescriptionBean>() {
                    @Override
                    public void onCompleted() {
                        mActivity.dismissInteractionDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mActivity.dismissInteractionDialog();
                    }

                    @Override
                    public void onNext(PrescriptionBean prescriptionBean) {
                        mActivity.updateSucceed(prescriptionBean);
                        mActivity.showToast("添加成功");
                    }
                });
        mSubscriptions.add(subscription);
    }
}
