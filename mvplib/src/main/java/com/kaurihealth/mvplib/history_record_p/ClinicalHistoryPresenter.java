package com.kaurihealth.mvplib.history_record_p;

import com.kaurihealth.datalib.repository.IDataSource;
import com.kaurihealth.datalib.response_bean.PatientRecordDisplayDto;
import com.kaurihealth.utilslib.constant.Global;
import com.kaurihealth.utilslib.log.LogUtils;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by jianghw on 2016/9/21.
 * <p/>
 * Describe:临床诊疗
 */
public class ClinicalHistoryPresenter<V> implements IClinicalHistoryPresenter<V> {
    private final IDataSource mRepository;
    private CompositeSubscription mSubscriptions;
    private IClinicalHistoryView mActivity;

    @Inject
    public ClinicalHistoryPresenter(IDataSource mRepository) {
        this.mRepository = mRepository;
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void setPresenter(V view) {
        mActivity = (IClinicalHistoryView) view;
    }

    @Override
    public void onSubscribe() {
        int mPatientId = mActivity.getPatientId();
        String mCategory = mActivity.getCategory();
        Subscription subscription = mRepository.loadAllPatientGenericRecordsBypatientIdAndCategory(mPatientId, mCategory)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(() -> {
                    mActivity.loadingIndicator(true);
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<PatientRecordDisplayDto>>() {
                    @Override
                    public void onCompleted() {
                        mActivity.loadingIndicator(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mActivity.loadingIndicator(false);
                        mActivity.loadAllPatientGenericRecordsError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<PatientRecordDisplayDto> bean) {
                        mActivity.loadAllPatientGenericRecordsSucceed(bean);
                        dataPacketBySubject(bean);
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public void dataPacketBySubject(List<PatientRecordDisplayDto> bean) {
        if (bean == null || bean.isEmpty()) {
            mActivity.loadAllPatientGenericRecordsError(null);
        } else {
            //TODO  稍后会做判断
            switch (mActivity.getCategory()) {
                case Global.Environment.CLINICAL:
                    //临床诊疗
                    filterClinicalOutpatient(bean);
                    filterHospitalRecords(bean);
                    break;
                case Global.Environment.LOB:
                    //实验室检查
                    filterHematology(bean);
                    filerUrine(bean);
                    filerFaces(bean);
                    filerSpecial(bean);
                    filerOther(bean);
                    break;
                case Global.Environment.AUXILIARY:
                    //辅助检查
                    filterIconographyTest(bean);
                    filerAngiocarpy(bean);
                    filerOtherTest(bean);
                    break;
                case Global.Environment.PATHOLOGY:
                    //病理
                    filerPhology(bean);
                    break;
            }
        }
    }

    /**
     * 其他
     */
    private void filerOther(List<PatientRecordDisplayDto> bean) {
        Subscription subscription = Observable.from(bean)
                .filter(new Func1<PatientRecordDisplayDto, Boolean>() {
                    @Override
                    public Boolean call(PatientRecordDisplayDto dto) {
                        return null != dto;
                    }
                })
                .filter(new Func1<PatientRecordDisplayDto, Boolean>() {
                    @Override
                    public Boolean call(PatientRecordDisplayDto dto) {
                        return dto.getCategory().equals(Global.Environment.LOB);//实验室检查
                    }
                })
                .filter(new Func1<PatientRecordDisplayDto, Boolean>() {
                    @Override
                    public Boolean call(PatientRecordDisplayDto dto) {
                        return dto.getSubject().equals("其它");
                    }
                })
                .toList()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<PatientRecordDisplayDto>>() {
                    @Override
                    public void call(List<PatientRecordDisplayDto> dtoList) {
                        mActivity.setLobTestData(dtoList,5);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        LogUtils.e(throwable.getMessage());
                    }
                });
        mSubscriptions.add(subscription);
    }

    /**
     * 特殊检查
     */
    private void filerSpecial(List<PatientRecordDisplayDto> bean) {
        Subscription subscription = Observable.from(bean)
                .filter(new Func1<PatientRecordDisplayDto, Boolean>() {
                    @Override
                    public Boolean call(PatientRecordDisplayDto dto) {
                        return null != dto;
                    }
                })
                .filter(new Func1<PatientRecordDisplayDto, Boolean>() {
                    @Override
                    public Boolean call(PatientRecordDisplayDto dto) {
                        return dto.getCategory().equals(Global.Environment.LOB);//实验室检查
                    }
                })
                .filter(new Func1<PatientRecordDisplayDto, Boolean>() {
                    @Override
                    public Boolean call(PatientRecordDisplayDto dto) {
                        return dto.getSubject().equals("特殊检查");
                    }
                })
                .toList()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<PatientRecordDisplayDto>>() {
                    @Override
                    public void call(List<PatientRecordDisplayDto> dtoList) {
                        mActivity.setLobTestData(dtoList,4);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        LogUtils.e(throwable.getMessage());
                    }
                });
        mSubscriptions.add(subscription);
    }

    /**
     * 常规粪便检查
     */
    private void filerFaces(List<PatientRecordDisplayDto> bean) {
        Subscription subscription = Observable.from(bean)
                .filter(new Func1<PatientRecordDisplayDto, Boolean>() {
                    @Override
                    public Boolean call(PatientRecordDisplayDto dto) {
                        return null != dto;
                    }
                })
                .filter(new Func1<PatientRecordDisplayDto, Boolean>() {
                    @Override
                    public Boolean call(PatientRecordDisplayDto dto) {
                        return dto.getCategory().equals(Global.Environment.LOB);//实验室检查
                    }
                })
                .filter(new Func1<PatientRecordDisplayDto, Boolean>() {
                    @Override
                    public Boolean call(PatientRecordDisplayDto dto) {
                        return dto.getSubject().equals("常规粪便检查");
                    }
                })
                .toList()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<PatientRecordDisplayDto>>() {
                    @Override
                    public void call(List<PatientRecordDisplayDto> dtoList) {
                        mActivity.setLobTestData(dtoList,3);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        LogUtils.e(throwable.getMessage());
                    }
                });
        mSubscriptions.add(subscription);
    }

    /**
     * 常规尿液检查
     */
    private void filerUrine(List<PatientRecordDisplayDto> bean) {
        Subscription subscription = Observable.from(bean)
                .filter(new Func1<PatientRecordDisplayDto, Boolean>() {
                    @Override
                    public Boolean call(PatientRecordDisplayDto dto) {
                        return null != dto;
                    }
                })
                .filter(new Func1<PatientRecordDisplayDto, Boolean>() {
                    @Override
                    public Boolean call(PatientRecordDisplayDto dto) {
                        return dto.getCategory().equals(Global.Environment.LOB);//实验室检查
                    }
                })
                .filter(new Func1<PatientRecordDisplayDto, Boolean>() {
                    @Override
                    public Boolean call(PatientRecordDisplayDto dto) {
                        return dto.getSubject().equals("常规尿液检查");
                    }
                })
                .toList()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<PatientRecordDisplayDto>>() {
                    @Override
                    public void call(List<PatientRecordDisplayDto> dtoList) {
                        mActivity.setLobTestData(dtoList,2);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        LogUtils.e(throwable.getMessage());
                    }
                });
        mSubscriptions.add(subscription);
    }

    /**
     * 常规血液学检查
     */
    private void filterHematology(List<PatientRecordDisplayDto> bean) {
        Subscription subscription = Observable.from(bean)
                .filter(new Func1<PatientRecordDisplayDto, Boolean>() {
                    @Override
                    public Boolean call(PatientRecordDisplayDto dto) {
                        return null != dto;
                    }
                })
                .filter(new Func1<PatientRecordDisplayDto, Boolean>() {
                    @Override
                    public Boolean call(PatientRecordDisplayDto dto) {
                        return dto.getCategory().equals(Global.Environment.LOB);//辅助
                    }
                })
                .filter(new Func1<PatientRecordDisplayDto, Boolean>() {
                    @Override
                    public Boolean call(PatientRecordDisplayDto dto) {
                        return dto.getSubject().equals("常规血液学检查");
                    }
                })
                .toList()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<PatientRecordDisplayDto>>() {
                    @Override
                    public void call(List<PatientRecordDisplayDto> dtoList) {
                        mActivity.setLobTestData(dtoList,1);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        LogUtils.e(throwable.getMessage());
                    }
                });
        mSubscriptions.add(subscription);
    }

    /**
     * 病理
     */
    private void filerPhology(List<PatientRecordDisplayDto> bean) {
        Subscription subscription = Observable.from(bean)
                .filter(new Func1<PatientRecordDisplayDto, Boolean>() {
                    @Override
                    public Boolean call(PatientRecordDisplayDto dto) {
                        return null != dto;
                    }
                })
                .filter(new Func1<PatientRecordDisplayDto, Boolean>() {
                    @Override
                    public Boolean call(PatientRecordDisplayDto dto) {
                        return dto.getCategory().equals(Global.Environment.PATHOLOGY);//辅助
                    }
                })
                .filter(new Func1<PatientRecordDisplayDto, Boolean>() {
                    @Override
                    public Boolean call(PatientRecordDisplayDto dto) {
                        return dto.getSubject().equals("病理");
                    }
                })
                .toList()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<PatientRecordDisplayDto>>() {
                    @Override
                    public void call(List<PatientRecordDisplayDto> dtoList) {
                        LogUtils.jsonDate(dtoList);
                        mActivity.setPhologyData(dtoList);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        LogUtils.e(throwable.getMessage());
                    }
                });
        mSubscriptions.add(subscription);
    }

    /**
     * 其他检查
     */
    private void filerOtherTest(List<PatientRecordDisplayDto> bean) {
        Subscription subscription = Observable.from(bean)
                .filter(new Func1<PatientRecordDisplayDto, Boolean>() {
                    @Override
                    public Boolean call(PatientRecordDisplayDto dto) {
                        return null != dto;
                    }
                })
                .filter(new Func1<PatientRecordDisplayDto, Boolean>() {
                    @Override
                    public Boolean call(PatientRecordDisplayDto dto) {
                        return dto.getCategory().equals(Global.Environment.AUXILIARY);//辅助
                    }
                })
                .filter(new Func1<PatientRecordDisplayDto, Boolean>() {
                    @Override
                    public Boolean call(PatientRecordDisplayDto dto) {
                        return dto.getSubject().equals("其它检查");
                    }
                })
                .toList()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<PatientRecordDisplayDto>>() {
                    @Override
                    public void call(List<PatientRecordDisplayDto> dtoList) {
                        LogUtils.jsonDate(dtoList);
                        mActivity.setRecordAccessoryData(dtoList,3);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        LogUtils.e(throwable.getMessage());
                    }
                });
        mSubscriptions.add(subscription);

    }

    //心血管系统相关检查
    private void filerAngiocarpy(List<PatientRecordDisplayDto> bean) {
        Subscription subscription = Observable.from(bean)
                .filter(new Func1<PatientRecordDisplayDto, Boolean>() {
                    @Override
                    public Boolean call(PatientRecordDisplayDto dto) {
                        return null != dto;
                    }
                })
                .filter(new Func1<PatientRecordDisplayDto, Boolean>() {
                    @Override
                    public Boolean call(PatientRecordDisplayDto dto) {
                        return dto.getCategory().equals(Global.Environment.AUXILIARY);//辅助
                    }
                })
                .filter(new Func1<PatientRecordDisplayDto, Boolean>() {
                    @Override
                    public Boolean call(PatientRecordDisplayDto dto) {
                        return dto.getSubject().equals("心血管系统相关检查");//心血管系统相关检查
                    }
                })
                .toList()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<PatientRecordDisplayDto>>() {
                    @Override
                    public void call(List<PatientRecordDisplayDto> dtoList) {
                        LogUtils.jsonDate(dtoList);
                        mActivity.setRecordAccessoryData(dtoList,2);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        LogUtils.e(throwable.getMessage());
                    }
                });
        mSubscriptions.add(subscription);
    }

    //过滤影像学检查
    private void filterIconographyTest(List<PatientRecordDisplayDto> bean) {
        Subscription subscription = Observable.from(bean)
                .filter(new Func1<PatientRecordDisplayDto, Boolean>() {
                    @Override
                    public Boolean call(PatientRecordDisplayDto dto) {
                        return null != dto;
                    }
                })
                .filter(new Func1<PatientRecordDisplayDto, Boolean>() {
                    @Override
                    public Boolean call(PatientRecordDisplayDto dto) {
                        return dto.getCategory().equals(Global.Environment.AUXILIARY);//临床诊疗记录
                    }
                })
                .filter(new Func1<PatientRecordDisplayDto, Boolean>() {
                    @Override
                    public Boolean call(PatientRecordDisplayDto dto) {
                        return dto.getSubject().equals("影像学检查");
                    }
                })
                .toList()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<PatientRecordDisplayDto>>() {
                    @Override
                    public void call(List<PatientRecordDisplayDto> dtoList) {
                        mActivity.setRecordAccessoryData(dtoList,1);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        LogUtils.e(throwable.getMessage());
                    }
                });
        mSubscriptions.add(subscription);
    }

    private void filterClinicalOutpatient(List<PatientRecordDisplayDto> bean) {
        Subscription subscription = Observable.from(bean)
                .filter(new Func1<PatientRecordDisplayDto, Boolean>() {
                    @Override
                    public Boolean call(PatientRecordDisplayDto dto) {
                        return null != dto;
                    }
                })
                .filter(new Func1<PatientRecordDisplayDto, Boolean>() {
                    @Override
                    public Boolean call(PatientRecordDisplayDto dto) {
                        return dto.getCategory().equals(Global.Environment.CLINICAL);//临床诊疗记录
                    }
                })
                .filter(new Func1<PatientRecordDisplayDto, Boolean>() {
                    @Override
                    public Boolean call(PatientRecordDisplayDto dto) {
                        return dto.getSubject().equals("门诊记录电子病历") || dto.getSubject().equals("门诊记录图片存档")
                        ||dto.getSubject().equals("出院记录")||dto.getSubject().equals("入院记录")||dto.getSubject().equals("远程医疗咨询")
                                ||dto.getSubject().equals("院内治疗相关记录")||dto.getSubject().equals("网络医疗咨询");
                    }
                })
                .toList()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<PatientRecordDisplayDto>>() {
                    @Override
                    public void call(List<PatientRecordDisplayDto> dtoList) {
                        mActivity.setClinicalOutpatient(dtoList);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        LogUtils.e(throwable.getMessage());
                    }
                });
        mSubscriptions.add(subscription);
    }

    private void filterHospitalRecords(List<PatientRecordDisplayDto> bean) {
        Subscription subscription = Observable.from(bean)
                .filter(new Func1<PatientRecordDisplayDto, Boolean>() {
                    @Override
                    public Boolean call(PatientRecordDisplayDto dto) {
                        return null != dto;
                    }
                })
                .filter(new Func1<PatientRecordDisplayDto, Boolean>() {
                    @Override
                    public Boolean call(PatientRecordDisplayDto dto) {
                        return dto.getCategory().equals("住院记录");//住院记录
                    }
                })
                .filter(new Func1<PatientRecordDisplayDto, Boolean>() {
                    @Override
                    public Boolean call(PatientRecordDisplayDto dto) {
                        return dto.getSubject().equals("住院记录");
                    }
                })
                .toList()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<PatientRecordDisplayDto>>() {
                    @Override
                    public void call(List<PatientRecordDisplayDto> dtoList) {
                        mActivity.setHospitalRecords(dtoList);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        LogUtils.e(throwable.getMessage());
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public void unSubscribe() {
        if (mActivity != null) mActivity.loadingIndicator(false);
        mSubscriptions.clear();
        mActivity = null;
    }
}
