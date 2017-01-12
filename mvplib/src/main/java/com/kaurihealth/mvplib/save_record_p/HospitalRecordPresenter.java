package com.kaurihealth.mvplib.save_record_p;

import com.kaurihealth.datalib.local.LocalData;
import com.kaurihealth.datalib.repository.IDataSource;
import com.kaurihealth.datalib.repository.RepositoryFactory;
import com.kaurihealth.datalib.request_bean.bean.NewHospitalizationPatientRecordDisplayBean;
import com.kaurihealth.datalib.response_bean.HospitalizationBean;
import com.kaurihealth.datalib.response_bean.PatientBean;
import com.kaurihealth.datalib.response_bean.PatientRecordDisplayDto;
import com.kaurihealth.utilslib.image.HospitalizationDocumentsBean;

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
 * Created by xxxx on 2017/1/5.
 * <p/>
 * Describe:
 */

public class HospitalRecordPresenter<V> implements IHospitalRecordPresenter<V> {
    private final IDataSource mRepository;
    private CompositeSubscription mSubscriptions;
    private IHospitalRecordView mActivity;
    private boolean mFirstLoad = true;

    @Inject
    public HospitalRecordPresenter(IDataSource mRepository) {
        this.mRepository = mRepository;
        mSubscriptions = new CompositeSubscription();

    }

    @Override
    public void setPresenter(V view) {
        mActivity = (IHospitalRecordView) view;
    }

    @Override
    public void onSubscribe() {
        if (mFirstLoad) loadingRemoteData(false);
    }

    public void loadingRemoteData(boolean isDirty) {
        if (isDirty) mRepository.manuallyRefresh();
    }

    /**
     * 入院编辑
     *
     * @param mTtype
     */
    @Override
    public void compileRecordToSave(String mTtype) {
        if (mTtype.equals("入院")) {
            admissionImageCases(new RepositoryFactory.UploadImageSucceed() {
                @Override
                public void imageSucceed(List<HospitalizationDocumentsBean> beanList, List<String> paths, boolean update) {
                    PatientRecordDisplayDto patientRecordDisplayBean = editDataProcessing(beanList, paths, mTtype);
                    //更新bean
                   updatePatientRecord(patientRecordDisplayBean);
                }
            }, true);
        } else if (mTtype.equals("院内")) {
            admissionImageCases(new RepositoryFactory.UploadImageSucceed() {
                @Override
                public void imageSucceed(List<HospitalizationDocumentsBean> beanList, List<String> paths, boolean update) {
                    editDataProcessing(beanList, paths, "入院");
                    courtImageCases(new RepositoryFactory.UploadImageSucceed() {
                        @Override
                        public void imageSucceed(List<HospitalizationDocumentsBean> beanList, List<String> paths, boolean update) {
                            PatientRecordDisplayDto patientRecordDisplayBean = editDataProcessing(beanList, paths, mTtype);
                            updatePatientRecord(patientRecordDisplayBean);
                        }
                    }, false);
                }
            }, true);
        } else if (mTtype.equals("出院")) {
            admissionImageCases(new RepositoryFactory.UploadImageSucceed() {
                @Override
                public void imageSucceed(List<HospitalizationDocumentsBean> beanList, List<String> paths, boolean update) {
                    editDataProcessing(beanList, paths, "入院");
                    courtImageCases(new RepositoryFactory.UploadImageSucceed() {
                        @Override
                        public void imageSucceed(List<HospitalizationDocumentsBean> beanList, List<String> paths, boolean update) {
                            editDataProcessing(beanList, paths, "院内");
                            outImageCases(new RepositoryFactory.UploadImageSucceed() {
                                @Override
                                public void imageSucceed(List<HospitalizationDocumentsBean> beanList, List<String> paths, boolean update) {
                                    PatientRecordDisplayDto patientRecordDisplayBean = editDataProcessing(beanList, paths, mTtype);
                                    updatePatientRecord(patientRecordDisplayBean);
                                }
                            }, false);
                        }
                    }, false);
                }
            }, true);
        }
    }

    @Override
    public void insertRecordToSave(String mTtype) {
        if (mTtype.equals("入院")) {
            admissionImageCases(new RepositoryFactory.UploadImageSucceed() {
                @Override
                public void imageSucceed(List<HospitalizationDocumentsBean> beanList, List<String> paths, boolean update) {
                    NewHospitalizationPatientRecordDisplayBean hospitalizationPatientRecordDisplayBean = insertDataProcessing(beanList, paths, mTtype);
                    insertHospitalization(hospitalizationPatientRecordDisplayBean);
                }
            }, true);
        } else if (mTtype.equals("院内")) {
            admissionImageCases(new RepositoryFactory.UploadImageSucceed() {
                @Override
                public void imageSucceed(List<HospitalizationDocumentsBean> beanList, List<String> paths, boolean update) {
                    insertDataProcessing(beanList, paths, "入院");
                    courtImageCases(new RepositoryFactory.UploadImageSucceed() {
                        @Override
                        public void imageSucceed(List<HospitalizationDocumentsBean> beanList, List<String> paths, boolean update) {
                            NewHospitalizationPatientRecordDisplayBean hospitalizationPatientRecordDisplayBean = insertDataProcessing(beanList, paths, mTtype);
                            insertHospitalization(hospitalizationPatientRecordDisplayBean);
                        }
                    }, false);
                }
            }, true);
        } else if (mTtype.equals("出院")) {
            admissionImageCases(new RepositoryFactory.UploadImageSucceed() {
                @Override
                public void imageSucceed(List<HospitalizationDocumentsBean> beanList, List<String> paths, boolean update) {
                    insertDataProcessing(beanList, paths, "入院");
                    courtImageCases(new RepositoryFactory.UploadImageSucceed() {
                        @Override
                        public void imageSucceed(List<HospitalizationDocumentsBean> beanList, List<String> paths, boolean update) {
                            insertDataProcessing(beanList, paths, "院内");
                            outImageCases(new RepositoryFactory.UploadImageSucceed() {
                                @Override
                                public void imageSucceed(List<HospitalizationDocumentsBean> beanList, List<String> paths, boolean update) {
                                    NewHospitalizationPatientRecordDisplayBean hospitalizationPatientRecordDisplayBean = insertDataProcessing(beanList, paths, mTtype);
                                    insertHospitalization(hospitalizationPatientRecordDisplayBean);
                                }
                            }, false);
                        }
                    }, false);
                }
            }, true);
        }
    }

    private void admissionImageCases(RepositoryFactory.UploadImageSucceed uploadSucceed, boolean update) {
        List<String> paths = mActivity.getAdmissionImagePathsList();//入院
        updateImageType(uploadSucceed, paths, update);
    }

    private void courtImageCases(RepositoryFactory.UploadImageSucceed uploadSucceed, boolean update) {
        List<String> pathsList = mActivity.getCourtImagePathsList();
        updateImageType(uploadSucceed, pathsList, update);
    }

    private void outImageCases(RepositoryFactory.UploadImageSucceed uploadSucceed, boolean update) {
        List<String> imagePathsList = mActivity.getOutImagePathsList();
        updateImageType(uploadSucceed, imagePathsList, update);
    }


    /**
     * 图片文件上传
     */
    private void uploadHandlingCases(RepositoryFactory.UploadImageSucceed uploadSucceed, String mTtype) {
        if (mTtype.equals("入院")) {
            List<String> paths = mActivity.getAdmissionImagePathsList();//编辑后入院
            updateImageType(uploadSucceed, paths, true);
        } else if (mTtype.equals("院内")) {
            List<String> paths = mActivity.getAdmissionImagePathsList();
            updateImageType(uploadSucceed, paths, false);
            List<String> pathsList = mActivity.getCourtImagePathsList();
            updateImageType(uploadSucceed, pathsList, false);
        } else if (mTtype.equals("出院")) {
            List<String> paths = mActivity.getAdmissionImagePathsList();
            updateImageType(uploadSucceed, paths, false);
            List<String> pathsList = mActivity.getCourtImagePathsList();
            updateImageType(uploadSucceed, pathsList, false);
            List<String> imagePathsList = mActivity.getOutImagePathsList();
            updateImageType(uploadSucceed, imagePathsList, true);
        }
    }

    private void updateImageType(final RepositoryFactory.UploadImageSucceed uploadSucceed, final List<String> paths, boolean update) {
        PatientBean currentPatient = LocalData.getLocalData().getCurrentPatient();
        Subscription subscription = mRepository.getUploadListObservableNew(paths, currentPatient != null ? currentPatient.getKauriHealthId() : "0")
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        if (update) mActivity.dataInteractionDialog(); //正在加载中...
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<HospitalizationDocumentsBean>>() {
                    @Override
                    public void call(List<HospitalizationDocumentsBean> beanList) {
                        uploadSucceed.imageSucceed(beanList, paths, update);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                        mActivity.displayErrorDialog(throwable.getMessage());
                    }
                });
        mSubscriptions.add(subscription);
    }

    /**
     * 编辑操作
     */
    private PatientRecordDisplayDto editDataProcessing(List<HospitalizationDocumentsBean> beanList, List<String> paths, String type) {
        PatientRecordDisplayDto patientRecordDisplayBean;
        List<HospitalizationBean> hospitalizationBeen;

        if (type.equals("入院")) {
            patientRecordDisplayBean = mActivity.getAdmissionRequestPatientRecordBean();
            hospitalizationBeen = patientRecordDisplayBean.getHospitalization();
        } else if (type.equals("院内")) {
            patientRecordDisplayBean = mActivity.getCourtRequestPatientRecordBean();
            hospitalizationBeen = patientRecordDisplayBean.getHospitalization();
        } else if (type.equals("出院")) {
            patientRecordDisplayBean = mActivity.getOutRequestPatientRecordBean();
            hospitalizationBeen = patientRecordDisplayBean.getHospitalization();
        } else {
            patientRecordDisplayBean = mActivity.getAdmissionRequestPatientRecordBean();
            hospitalizationBeen = patientRecordDisplayBean.getHospitalization();
        }
        addImageBeanByType(beanList, paths, type, patientRecordDisplayBean, hospitalizationBeen);

        return patientRecordDisplayBean;
    }

    private NewHospitalizationPatientRecordDisplayBean insertDataProcessing(List<HospitalizationDocumentsBean> beanList, List<String> paths, String type) {
        NewHospitalizationPatientRecordDisplayBean recordDisplayBean;
        List<HospitalizationBean> hospitalizationBeen;

        if (type.equals("入院")) {
            recordDisplayBean = mActivity.getAdmissionInsertRecordBean();
        } else if (type.equals("院内")) {
            recordDisplayBean = mActivity.getCourtInsertRecordBean();
        } else if (type.equals("出院")) {
            recordDisplayBean = mActivity.getOutInsertRecordBean();
        } else {
            recordDisplayBean = mActivity.getAdmissionInsertRecordBean();
        }

        hospitalizationBeen = recordDisplayBean.getHospitalization();
        addInsertImageBeanByType(beanList, paths, type, recordDisplayBean, hospitalizationBeen);

        return recordDisplayBean;
    }

    private void addImageBeanByType(List<HospitalizationDocumentsBean> beanList, List<String> paths, String type,
                                    PatientRecordDisplayDto patientRecordDisplayBean, List<HospitalizationBean> hospitalizationBeen) {
        List<HospitalizationDocumentsBean> list = new ArrayList<>();
        if (hospitalizationBeen != null && !hospitalizationBeen.isEmpty()) {
            for (HospitalizationBean bean : hospitalizationBeen) {
                if (bean.getHospitalizationType().contains(type)) {
                    if (bean.getHospitalizationDocuments() != null)
                        list.addAll(bean.getHospitalizationDocuments());
                }
            }
        }
        //标记已删除的网络图片
        if (!list.isEmpty()) markDeleteImages(paths, patientRecordDisplayBean, list, type);
        //添加本地图片
        if (beanList.size() > 0)
            addDocumentDisplayBeanToList(beanList, patientRecordDisplayBean, type);
    }

    private void addInsertImageBeanByType(List<HospitalizationDocumentsBean> beanList, List<String> paths, String type,
                                          NewHospitalizationPatientRecordDisplayBean patientRecordDisplayBean, List<HospitalizationBean> hospitalizationBeen) {
        List<HospitalizationDocumentsBean> list = new ArrayList<>();
        if (hospitalizationBeen != null && !hospitalizationBeen.isEmpty()) {
            for (HospitalizationBean bean : hospitalizationBeen) {
                if (bean.getHospitalizationType().contains(type)) {
                    if (bean.getHospitalizationDocuments() != null)
                        list.addAll(bean.getHospitalizationDocuments());
                }
            }
        }
        //标记已删除的网络图片
        if (!list.isEmpty()) markInsertDeleteImages(paths, patientRecordDisplayBean, list, type);
        //添加本地图片
        if (beanList.size() > 0)
            addInsertDocumentDisplayBeanToList(beanList, patientRecordDisplayBean, type);
    }

    /**
     * 标记已经删除的图片，改为true，更新内部bean
     * 1、原list有地址集时，把paths中不存在相同的地址 标记为删除 true
     * 2、转集合
     * 3、改原bean
     */
    private void markDeleteImages(final List<String> paths, PatientRecordDisplayDto patientRecordDisplayBean, List<HospitalizationDocumentsBean> list, String type) {
        Observable.from(list)
                .filter(new Func1<HospitalizationDocumentsBean, Boolean>() {
                    @Override
                    public Boolean call(HospitalizationDocumentsBean bean) {
                        return null != bean;
                    }
                })
                .filter(new Func1<HospitalizationDocumentsBean, Boolean>() {
                    @Override
                    public Boolean call(HospitalizationDocumentsBean bean) {
                        return bean.getDocumentUrl() != null;
                    }
                })
                .map(new Func1<HospitalizationDocumentsBean, HospitalizationDocumentsBean>() {
                    @Override
                    public HospitalizationDocumentsBean call(HospitalizationDocumentsBean recordDocumentsBean) {
                        if (!paths.contains(recordDocumentsBean.getDocumentUrl()))
                            recordDocumentsBean.setIsDeleted(true);
                        return recordDocumentsBean;
                    }
                })
                .toList()
                .subscribe(new Action1<List<HospitalizationDocumentsBean>>() {
                    @Override
                    public void call(List<HospitalizationDocumentsBean> recordDocumentsBeen) {
                        List<HospitalizationBean> hospitalization = patientRecordDisplayBean.getHospitalization();
                        if (hospitalization != null && !hospitalization.isEmpty()) {
                            for (HospitalizationBean bean : hospitalization) {
                                if (bean.getHospitalizationType().contains(type)) {
                                    bean.setHospitalizationDocuments(recordDocumentsBeen);
                                }
                            }
                        }
                    }
                });
    }

    private void markInsertDeleteImages(final List<String> paths, NewHospitalizationPatientRecordDisplayBean patientRecordDisplayBean, List<HospitalizationDocumentsBean> list, String type) {
        Observable.from(list)
                .filter(new Func1<HospitalizationDocumentsBean, Boolean>() {
                    @Override
                    public Boolean call(HospitalizationDocumentsBean bean) {
                        return null != bean;
                    }
                })
                .filter(new Func1<HospitalizationDocumentsBean, Boolean>() {
                    @Override
                    public Boolean call(HospitalizationDocumentsBean bean) {
                        return bean.getDocumentUrl() != null;
                    }
                })
                .map(new Func1<HospitalizationDocumentsBean, HospitalizationDocumentsBean>() {
                    @Override
                    public HospitalizationDocumentsBean call(HospitalizationDocumentsBean recordDocumentsBean) {
                        if (!paths.contains(recordDocumentsBean.getDocumentUrl()))
                            recordDocumentsBean.setIsDeleted(true);
                        return recordDocumentsBean;
                    }
                })
                .toList()
                .subscribe(new Action1<List<HospitalizationDocumentsBean>>() {
                    @Override
                    public void call(List<HospitalizationDocumentsBean> recordDocumentsBeen) {
                        List<HospitalizationBean> hospitalization = patientRecordDisplayBean.getHospitalization();
                        if (hospitalization != null && !hospitalization.isEmpty()) {
                            for (HospitalizationBean bean : hospitalization) {
                                if (bean.getHospitalizationType().contains(type)) {
                                    bean.setHospitalizationDocuments(recordDocumentsBeen);
                                }
                            }
                        }
                    }
                });
    }

    /**
     * 构建编辑中添加图片对应bean id设置0
     */
    private void addDocumentDisplayBeanToList(List<HospitalizationDocumentsBean> beanList, PatientRecordDisplayDto patientRecordDisplayBean, String type) {
        List<HospitalizationDocumentsBean> list = new ArrayList<>();
        HospitalizationBean hospitalizationBean = null;

        List<HospitalizationBean> hospitalization = patientRecordDisplayBean.getHospitalization();
        if (hospitalization != null && !hospitalization.isEmpty()) {
            for (HospitalizationBean bean : hospitalization) {
                if (bean.getHospitalizationType().contains(type)) {
                    if (bean.getHospitalizationDocuments() != null)
                        list.addAll(bean.getHospitalizationDocuments());
                    hospitalizationBean = bean;
                }
            }
        }

        for (HospitalizationDocumentsBean documentDisplayBean : beanList) {
            HospitalizationDocumentsBean recordDocumentsBean = new HospitalizationDocumentsBean();
            recordDocumentsBean.setHospitalizationDocumentId(0);
            recordDocumentsBean.setDocumentUrl(documentDisplayBean.getDocumentUrl());
            recordDocumentsBean.setFileName(documentDisplayBean.getFileName());
            recordDocumentsBean.setDocumentFormat(documentDisplayBean.getDocumentFormat());
            recordDocumentsBean.setDisplayName(documentDisplayBean.getDisplayName());
            recordDocumentsBean.setHospitalizationId(hospitalizationBean != null ? hospitalizationBean.getHospitalizationId() : 0);
            recordDocumentsBean.setIsDeleted(false);

            list.add(recordDocumentsBean);
        }

        if (hospitalizationBean != null) hospitalizationBean.setHospitalizationDocuments(list);
        patientRecordDisplayBean.setHospitalization(hospitalization);
    }

    private void addInsertDocumentDisplayBeanToList(List<HospitalizationDocumentsBean> beanList, NewHospitalizationPatientRecordDisplayBean patientRecordDisplayBean, String type) {
        List<HospitalizationDocumentsBean> list = new ArrayList<>();
        HospitalizationBean hospitalizationBean = null;

        List<HospitalizationBean> hospitalization = patientRecordDisplayBean.getHospitalization();
        if (hospitalization != null && !hospitalization.isEmpty()) {
            for (HospitalizationBean bean : hospitalization) {
                if (bean.getHospitalizationType().contains(type)) {
                    if (bean.getHospitalizationDocuments() != null)
                        list.addAll(bean.getHospitalizationDocuments());
                    hospitalizationBean = bean;
                }
            }
        }

        for (HospitalizationDocumentsBean documentDisplayBean : beanList) {
            HospitalizationDocumentsBean recordDocumentsBean = new HospitalizationDocumentsBean();
            recordDocumentsBean.setHospitalizationDocumentId(0);
            recordDocumentsBean.setDocumentUrl(documentDisplayBean.getDocumentUrl());
            recordDocumentsBean.setFileName(documentDisplayBean.getFileName());
            recordDocumentsBean.setDocumentFormat(documentDisplayBean.getDocumentFormat());
            recordDocumentsBean.setDisplayName(documentDisplayBean.getDisplayName());
            recordDocumentsBean.setHospitalizationId(hospitalizationBean != null ? hospitalizationBean.getHospitalizationId() : 0);
            recordDocumentsBean.setIsDeleted(false);

            list.add(recordDocumentsBean);
        }

        if (hospitalizationBean != null) hospitalizationBean.setHospitalizationDocuments(list);
        patientRecordDisplayBean.setHospitalization(hospitalization);
    }

    /**
     * 提交更新
     */
    private void updatePatientRecord(PatientRecordDisplayDto patientRecordDisplayBean) {
        Subscription subscription = mRepository.UpdateHospitalization(patientRecordDisplayBean)
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
                        mActivity.showToast("保存成功！");
                        mActivity.updatePatientRecordSucceed(patientBean);
                    }
                });
        mSubscriptions.add(subscription);
    }

    /**
     * 提交更新
     */
    private void insertHospitalization(NewHospitalizationPatientRecordDisplayBean patientRecordDisplayBean) {
        Subscription subscription = mRepository.insertHospitalization(patientRecordDisplayBean)
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
                        mActivity.showToast("插入成功！");
                        mActivity.updatePatientRecordSucceed(patientBean);
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public void unSubscribe() {
        if (mSubscriptions != null) mSubscriptions.clear();
        if (mActivity != null) mActivity = null;
    }
}
