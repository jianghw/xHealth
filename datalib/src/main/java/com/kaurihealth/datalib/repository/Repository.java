package com.kaurihealth.datalib.repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.kaurihealth.datalib.local.ILocalSource;
import com.kaurihealth.datalib.remote.IRemoteSource;
import com.kaurihealth.datalib.request_bean.bean.CreditTransactionDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.DoctorUserBean;
import com.kaurihealth.datalib.request_bean.bean.HealthConditionDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.InitialiseSearchRequestBean;
import com.kaurihealth.datalib.request_bean.bean.InitiateVerificationBean;
import com.kaurihealth.datalib.request_bean.bean.LiteratureCommentDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.LiteratureReplyDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.LoginBean;
import com.kaurihealth.datalib.request_bean.bean.MedicalLiteratureDisPlayBean;
import com.kaurihealth.datalib.request_bean.bean.MedicalLiteratureLikeDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.MobileUpdateDoctorBean;
import com.kaurihealth.datalib.request_bean.bean.NewCashOutAccountBean;
import com.kaurihealth.datalib.request_bean.bean.NewCashOutBean;
import com.kaurihealth.datalib.request_bean.bean.NewDoctorRelationshipBean;
import com.kaurihealth.datalib.request_bean.bean.NewLabTestPatientRecordDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.NewLiteratureCommentDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.NewLiteratureReplyDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.NewLongtermMonitoringBean;
import com.kaurihealth.datalib.request_bean.bean.NewMedicalLiteratureLikeDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.NewPasswordDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.NewPathologyPatientRecordDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.NewPatientRecordDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.NewPrescriptionBean;
import com.kaurihealth.datalib.request_bean.bean.NewPriceBean;
import com.kaurihealth.datalib.request_bean.bean.NewRegistByDoctorBean;
import com.kaurihealth.datalib.request_bean.bean.NewRegisterBean;
import com.kaurihealth.datalib.request_bean.bean.NewSupplementaryTestPatientRecordDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.PatientRequestDecisionBean;
import com.kaurihealth.datalib.request_bean.bean.PatientRequestDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.PatientRequestReferralDoctorDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.PatientRequestReferralPatientDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.PrescriptionBean;
import com.kaurihealth.datalib.request_bean.bean.RequestResetPasswordDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.ResetPasswordDisplayBean;
import com.kaurihealth.datalib.response_bean.SearchResultBean;
import com.kaurihealth.datalib.response_bean.ContactUserDisplayBean;
import com.kaurihealth.datalib.response_bean.DepartmentDisplayBean;
import com.kaurihealth.datalib.response_bean.DoctorCooperationBean;
import com.kaurihealth.datalib.response_bean.DoctorDisplayBean;
import com.kaurihealth.datalib.response_bean.DoctorInformationDisplayBean;
import com.kaurihealth.datalib.response_bean.DoctorPatientRelationshipBean;
import com.kaurihealth.datalib.response_bean.DoctorRelationshipBean;
import com.kaurihealth.datalib.response_bean.DocumentDisplayBean;
import com.kaurihealth.datalib.response_bean.InitiateVerificationResponse;
import com.kaurihealth.datalib.response_bean.LongTermMonitoringDisplayBean;
import com.kaurihealth.datalib.response_bean.PatientDisplayBean;
import com.kaurihealth.datalib.response_bean.PatientRecordDisplayBean;
import com.kaurihealth.datalib.response_bean.PriceDisplayBean;
import com.kaurihealth.datalib.response_bean.RegisterResponse;
import com.kaurihealth.datalib.response_bean.ResponseDisplayBean;
import com.kaurihealth.datalib.response_bean.SoftwareInfo;
import com.kaurihealth.datalib.response_bean.TokenBean;
import com.kaurihealth.datalib.response_bean.UserCashOutAccountDisplayBean;
import com.kaurihealth.utilslib.bugtag.BugTagUtils;
import com.kaurihealth.utilslib.log.LogUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by jianghw on 2016/8/8.
 * <p>
 * 描述：数据来源管理者 仓库 支配着
 */

public class Repository implements IDataSource {

    private final IRemoteSource mRemoteData;
    private final ILocalSource mLocalData;

    private boolean isRefresh;//是否手动刷新

    @Inject
    public Repository(IRemoteSource remoteData, ILocalSource localData) {
        mRemoteData = remoteData;
        mLocalData = localData;
    }

    /**
     * 手动控制刷新
     */
    @Override
    public void manuallyRefresh() {
        isRefresh = true;
    }

    /**
     * 缓存上层数据 有缓存时显示缓存数据，
     *
     * @param clazz      响应bean
     * @param observable 远程数据获取
     * @param <T>
     * @return Observable
     */
    public <T> Observable<T> upperCacheData(final Class<T> clazz, Observable<T> observable) {
        String key = clazz.getSimpleName();
        if (isRefresh) {
            mLocalData.removeJsonCache(key);
            isRefresh = false;
        }

        if (mLocalData.getJsonCache(key) != null) {
            return Observable.create(new Observable.OnSubscribe<T>() {
                @Override
                public void call(Subscriber<? super T> subscriber) {
                    String json = mLocalData.getJsonCache(clazz.getSimpleName());
                    T bean = new Gson().fromJson(json, clazz);
                    subscriber.onNext(bean);
                    subscriber.onCompleted();
                }
            });
        }

        return observable;
    }

    /**
     * 缓存list结构的数据
     *
     * @param clazz
     * @param observable
     * @param <T>
     * @return
     */
    public <T> Observable<List<T>> upperListCacheData(final Class<T> clazz, Observable<List<T>> observable) {
        String key = clazz.getSimpleName();
        if (isRefresh) {
            mLocalData.removeJsonCache(key);
            isRefresh = false;
        }

        if (mLocalData.getJsonCache(key) != null) {
            return Observable.create(new Observable.OnSubscribe<List<T>>() {
                @Override
                public void call(Subscriber<? super List<T>> subscriber) {
                    String json = mLocalData.getJsonCache(clazz.getSimpleName());
                    Gson gson = new GsonBuilder()
                            .serializeNulls()
                            .create();
                    JsonArray array = new JsonParser().parse(json).getAsJsonArray();
                    List<T> mList = new ArrayList<>();
                    for (JsonElement jsonElement : array) {
                        mList.add(gson.fromJson(jsonElement, clazz));
                        LogUtils.json(gson.fromJson(jsonElement, clazz).toString());
                    }
                    subscriber.onNext(mList);
                    subscriber.onCompleted();
                }
            });
        }
        return observable;
    }

    public Observable<TokenBean> getLoginDatabaseBean() {
        return Observable.create(new Observable.OnSubscribe<TokenBean>() {
            @Override
            public void call(Subscriber<? super TokenBean> subscriber) {
                List<TokenBean> list = mLocalData.getQueryAll(TokenBean.class);
                int size = list.size();
                if (size == 1) {
                    subscriber.onNext(list.get(size - 1));
                    subscriber.onCompleted();
                } else {
                    subscriber.onError(new NullPointerException("is no ever login or error"));
                }
            }
        });
    }

    /**
     * 数据库查询
     */
    @Override
    public <T> Observable<List<T>> databaseQueryAll(final Class<T> cla) {
        return Observable.create(new Observable.OnSubscribe<List<T>>() {
            @Override
            public void call(Subscriber<? super List<T>> subscriber) {
                subscriber.onNext(mLocalData.getQueryAll(cla));
                subscriber.onCompleted();
            }
        });
    }

    /**
     * 下层数据持久化封装 保存数据库
     *
     * @param observable 网络层数据来源
     * @param <T>
     * @return
     */
    public <T> Observable<T> persistentData(Observable<T> observable) {
        return observable.doOnNext(new Action1<T>() {
            @Override
            public void call(T bean) {
                //TODO 保存数据库
                mLocalData.insertEnsureByOne(bean);
                LogUtils.jsonDate(bean);
            }
        });
    }

    /**
     * 下层数据持久化封装 保存数据库and缓存
     *
     * @param observable 网络层数据来源
     * @param <T>
     * @return
     */
    private <T> Observable<T> persistentDataAndCache(Observable<T> observable) {
        return observable.doOnNext(new Action1<T>() {
            @Override
            public void call(T bean) {
                //TODO 缓存
                String gson = new Gson().toJson(bean);
                mLocalData.addJsonCache(bean.getClass().getSimpleName(), gson);
                //TODO 保存数据库
                mLocalData.insertEnsureByOne(bean);
            }
        });
    }

    /**
     * 存list结构数据
     *
     * @param observable
     * @param <T>
     * @return
     */
    private <T> Observable<List<T>> persistentListData(Observable<List<T>> observable) {
        return observable.doOnNext(new Action1<List<T>>() {
            @Override
            public void call(List<T> list) {
                //TODO 保存数据库
                mLocalData.insertEnsureByOneList(list);
            }
        });
    }

    /**
     * 存list结构数据 和缓存
     *
     * @param observable
     * @param <T>
     * @return
     */
    private <T> Observable<List<T>> persistentListDataAndCache(Observable<List<T>> observable) {
        return observable.doOnNext(new Action1<List<T>>() {
            @Override
            public void call(List<T> list) {
                //TODO 缓存
                Gson gson = new GsonBuilder()
                        .serializeNulls()
                        .create();
                String json = null;
                try {
                    json = gson.toJson(list);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mLocalData.addJsonCache(list.getClass().getSimpleName(), json);
                //TODO 保存数据库
                mLocalData.insertAll(list);
            }
        });
    }

    /**
     * 统一错误提交Bugtag层级封装
     *
     * @param observable
     * @param <T>
     * @return
     */
    private <T> Observable<T> errorForBugTag(Observable<T> observable) {
        return observable.doOnError(new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                BugTagUtils.sendException(throwable);
                LogUtils.e(throwable.getMessage());
            }
        });
    }

    /**
     * 登录信息
     */
    public Observable<TokenBean> userLogin(LoginBean bean) {
        return errorForBugTag(persistentData(mRemoteData.userLogin(bean)));
    }

    /**
     * 注册操作
     *
     * @param newRegisterBean 注册请求信息
     * @return
     */
    public Observable<RegisterResponse> userRegister(NewRegisterBean newRegisterBean) {
        return errorForBugTag(mRemoteData.userRegister(newRegisterBean));
    }

    /**
     * 请求短信验证码
     */
    public Observable<InitiateVerificationResponse> InitiateVerification(InitiateVerificationBean bean) {
        return mRemoteData.InitiateVerification(bean);
    }


    /**
     * 更新用户信息  需要token
     */
    public Observable<ResponseDisplayBean> updateDoctorUserInformation(DoctorUserBean bean) {
        return mRemoteData.updateDoctorUserInformation(bean);
    }

    /**
     * 找回密码--重置用户密码
     */
    public Observable<ResponseDisplayBean> findPassword(RequestResetPasswordDisplayBean bean) {
        return mRemoteData.findPassword(bean);
    }

    /**
     * 重置密码
     */
    @Override
    public Observable<ResponseDisplayBean> resetPassword(ResetPasswordDisplayBean bean) {
        return mRemoteData.resetPassword(bean);
    }

    /**
     * 加载挂起的医生关系
     */
    @Override
    public Observable<List<DoctorRelationshipBean>> LoadPendingDoctorRelationships() {
        return mRemoteData.LoadPendingDoctorRelationships();
    }

    /**
     * 通过医生查询患者的请求
     */
    @Override
    public Observable<List<PatientRequestDisplayBean>> LoadPatientRequestsByDoctor() {
        return mRemoteData.LoadPatientRequestsByDoctor();
    }

    //查询医生信息
    @Override
    public Observable<DoctorDisplayBean> loadDoctorDetail() {
        Observable<DoctorDisplayBean> doctorDisplayBeanObservable = mRemoteData.loadDoctorDetail();
        return errorForBugTag(persistentData(doctorDisplayBeanObservable));
    }

    //更新医生的信息
    @Override
    public Observable<DoctorDisplayBean> updateDoctor(DoctorDisplayBean doctorDisplayBean) {
        DoctorInformationDisplayBean doctorInformations = doctorDisplayBean.getDoctorInformations();
        if (doctorInformations != null) {
            if (doctorInformations.getDepartmentId() == 0) {
                doctorDisplayBean.setDoctorInformations(null);
            }
            if (doctorInformations.getDoctorId() == 0 || doctorInformations.getDoctorInformationId() == 0) {
                doctorInformations.setDoctorId(doctorDisplayBean.getDoctorId());
                doctorInformations.setDoctorInformationId(doctorDisplayBean.getDoctorId());
            }
        }
        Observable<DoctorDisplayBean> doctorDisplay = mRemoteData.updateDoctor(doctorDisplayBean);
        return errorForBugTag(persistentData(doctorDisplay));
    }

    /**
     * 医生为患者开户
     *
     * @param bean
     * @return
     */
    @Override
    public Observable<RegisterResponse> openAnAccount(NewRegistByDoctorBean bean) {
        return mRemoteData.openAnAccount(bean);
    }

    /**
     * 为现医生查询所有医患关系
     */
    @Override
    public Observable<List<DoctorPatientRelationshipBean>> loadDoctorPatientRelationshipForDoctor() {
        return mRemoteData.loadDoctorPatientRelationshipForDoctor();
    }

    /**
     * 搜索首页:所有，医院，科室，医生的bean
     *
     * @param bean
     * @return
     */
    @Override
    public Observable<SearchResultBean> keyWordsSearch(InitialiseSearchRequestBean bean) {
        return mRemoteData.keyWordsSearch(bean);
    }

    /**
     * 搜索首页: 搜索患者
     */
    @Override
    public Observable<List<PatientDisplayBean>> SearchPatientByUserName(String name) {
        return mRemoteData.SearchPatientByUserName(name);
    }

    @Override
    public Observable<DoctorPatientRelationshipBean> insertNewRelationshipByDoctor(int patientId) {
        return mRemoteData.insertNewRelationshipByDoctor(patientId);
    }


    @Override
    public Observable<List<LongTermMonitoringDisplayBean>> longTermMonitoringDisplay(int patientId) {
        return mRemoteData.longTermMonitoringDisplay(patientId);
    }

    @Override
    public Observable<ResponseDisplayBean> insertLongtermMonitorings(List<NewLongtermMonitoringBean> beans) {
        return mRemoteData.insertLongtermMonitorings(beans);
    }


    /**
     * 根据类型加载所有的临床支持
     *
     * @param medicalLiteratureType
     * @return
     */
    @Override
    public Observable<List<MedicalLiteratureDisPlayBean>> LoadAllMedicalLitreaturesByType(String medicalLiteratureType) {
        return mRemoteData.LoadAllMedicalLitreaturesByType(medicalLiteratureType);
    }

    /**
     * 根据临床id加载最新临床信息
     *
     * @param medicalLiteratureId
     * @return
     */
    @Override
    public Observable<MedicalLiteratureDisPlayBean> LoadMedicalLiteratureById(int medicalLiteratureId) {
        return mRemoteData.LoadMedicalLiteratureById(medicalLiteratureId);
    }

    /**
     * 描述:我的Fragment-->我的账户--> 提现通道-->获取用户所有的提现账号
     */
    @Override
    public Observable<List<UserCashOutAccountDisplayBean>> getUserCashOutAccounts() {
        return mRemoteData.getUserCashOutAccounts();
    }

    //个人信息--> 科室： 加载一级科室
    @Override
    public Observable<List<DepartmentDisplayBean>> loadAllDepartment() {
        return mRemoteData.loadAllDepartment();
    }

    //发起提现请求 IDataSource
    @Override
    public Observable<ResponseDisplayBean> startNewCashOut(NewCashOutBean bean) {
        return mRemoteData.startNewCashOut(bean);
    }

    //检查登陆用户是否一堆某一个文献点赞。
    @Override
    public Observable<ResponseDisplayBean> CheckMedicalLiteratureLike(NewMedicalLiteratureLikeDisplayBean newMedicalLiteratureLikeDisplayBean) {
        return mRemoteData.CheckMedicalLiteratureLike(newMedicalLiteratureLikeDisplayBean);
    }

    //去除点赞
    @Override
    public Observable<ResponseDisplayBean> DisLikeMedicalLiterature(NewMedicalLiteratureLikeDisplayBean newMedicalLiteratureLikeDisplayBean) {
        return mRemoteData.DisLikeMedicalLiterature(newMedicalLiteratureLikeDisplayBean);
    }

    //根据id加载临床评论
    @Override
    public Observable<List<LiteratureCommentDisplayBean>> LoadLiteratureCommentsByMedicalLiteratureId(int medicalLiteratureId) {
        return mRemoteData.LoadLiteratureCommentsByMedicalLiteratureId(medicalLiteratureId);
    }

    //插入临床评论
    @Override
    public Observable<LiteratureCommentDisplayBean> InsertLiteratureComment(NewLiteratureCommentDisplayBean newLiteratureCommentDisplayBean) {
        return mRemoteData.InsertLiteratureComment(newLiteratureCommentDisplayBean);
    }

    @Override
    public Observable<ResponseDisplayBean> updateDoctorProductPrice(PriceDisplayBean bean) {
        return mRemoteData.updateDoctorProductPrice(bean);
    }

    @Override
    public Observable<ResponseDisplayBean> insertPrice(NewPriceBean bean) {
        return mRemoteData.insertPrice(bean);
    }

    //临床支持点赞
    @Override
    public Observable<MedicalLiteratureLikeDisplayBean> LikeMedicalLiterature(NewMedicalLiteratureLikeDisplayBean newMedicalLiteratureLikeDisplayBean) {
        return mRemoteData.LikeMedicalLiterature(newMedicalLiteratureLikeDisplayBean);
    }

    @Override
    public Observable<LiteratureReplyDisplayBean> InsertLiteratureReply(NewLiteratureReplyDisplayBean newLiteratureReplyDisplayBean) {
        return mRemoteData.InsertLiteratureReply(newLiteratureReplyDisplayBean);
    }

    @Override
    public Observable<ResponseDisplayBean> DeleteLiteratureComment(int literatureCommentId) {
        return mRemoteData.DeleteLiteratureComment(literatureCommentId);
    }


    @Override
    public Observable<List<LiteratureReplyDisplayBean>> LoadLiteratureReplyByLiteratureCommentId(int literatureCommentId) {
        return mRemoteData.LoadLiteratureReplyByLiteratureCommentId(literatureCommentId);
    }

    //加载所有的临床支持
    @Override
    public Observable<List<MedicalLiteratureDisPlayBean>> LoadAllMedicalLiteratures() {
        return mRemoteData.LoadAllMedicalLiteratures();
    }

    @Override
    public Observable<List<PrescriptionBean>> loadPrescriptionsByPatientId(int patientId) {
        return mRemoteData.loadPrescriptionsByPatientId(patientId);
    }

    @Override
    public Observable<PrescriptionBean> insertPrescription(NewPrescriptionBean newPrescriptionBean) {
        return mRemoteData.insertPrescription(newPrescriptionBean);
    }

    //删除多个长期监测
    @Override
    public Observable<ResponseDisplayBean> deleteLongtermMonitorings(List<Integer> bean) {
        return mRemoteData.deleteLongtermMonitorings(bean);
    }


    @Override
    public Observable<List<HealthConditionDisplayBean>> LoadHealthConditionByPatientId(int patientId) {
        return mRemoteData.LoadHealthConditionByPatientId(patientId);
    }

    //更新的健康记录
    @Override
    public Observable<HealthConditionDisplayBean> UpdateHealthConditions(HealthConditionDisplayBean[] healthConditionDisplayBeans) {
        return mRemoteData.UpdateHealthConditions(healthConditionDisplayBeans);
    }


    @Override
    public Observable<ResponseDisplayBean> DeleteHealthConditions(Integer[] ids) {
        return mRemoteData.DeleteHealthConditions(ids);
    }

    //通过患者ID查询所有的患者的病例 医生和患者的token都可以用
    @Override
    public Observable<List<PatientRecordDisplayBean>> loadAllPatientGenericRecordsBypatientId(int patientID) {
        return mRemoteData.loadAllPatientGenericRecordsBypatientId(patientID);
    }

    @Override
    public Observable<PrescriptionBean> UpdatePrescription(PrescriptionBean newPrescriptionBean) {
        return mRemoteData.UpdatePrescription(newPrescriptionBean);
    }

    @Override
    public Observable<List<LongTermMonitoringDisplayBean>> updateLongtermMonitorings(List<LongTermMonitoringDisplayBean> list) {
        return mRemoteData.updateLongtermMonitorings(list);
    }

    //我的--> 设置--> 修改密码
    @Override
    //IDataSource 重写的方法
    public Observable<ResponseDisplayBean> updateUserPassword(NewPasswordDisplayBean bean) {
        //重写IRemoteSource里的updateUserPassword
        return mRemoteData.updateUserPassword(bean);
    }

    //个人信息--> 机构--> 医院名称（string）数组
    @Override
    public Observable<List<String>> LoadAllHospitals() {
        return mRemoteData.LoadAllHospitals();
    }

    //个人信息--> 科室-->一级大科系
    @Override
    public Observable<List<DepartmentDisplayBean>> loadDepartment() {
        return mRemoteData.loadDepartment();
    }

    //个人信息--> 科室： 加载二级子类科室
    @Override
    public Observable<List<DepartmentDisplayBean>> loadDepartmentName(int level, int parentDepartmentId) {
        return mRemoteData.loadDepartmentName(level, parentDepartmentId);
    }

    //移动端更新医生信息
    @Override
    public Observable<ResponseDisplayBean> mobileUpdateDoctor(MobileUpdateDoctorBean bean) {
        return mRemoteData.mobileUpdateDoctor(bean);
    }

    //添加新的提现账号
    @Override
    public Observable<UserCashOutAccountDisplayBean> InsertUserCashOutAccount(NewCashOutAccountBean account) {
        return mRemoteData.InsertUserCashOutAccount(account);
    }

    @Override
    public Observable<PatientRecordDisplayBean> updatePatientRecord(PatientRecordDisplayBean patientRecordDisplayBean) {
        return mRemoteData.updatePatientRecord(patientRecordDisplayBean);
    }

    /**
     * 图片文件上传
     * 图片地址转MultipartBody.Part
     * 需要或不需要时
     */
    @Override
    public Observable<List<DocumentDisplayBean>> getUploadListObservable(List<String> paths, final String kauriHealthId) {
        return Observable.from(paths)
                .subscribeOn(Schedulers.io())
                .filter(new Func1<String, Boolean>() {
                    @Override
                    public Boolean call(String url) {
                        return !url.contains("http");
                    }
                })
                .map(new Func1<String, MultipartBody.Part>() {
                    @Override
                    public MultipartBody.Part call(String path) {
                        File file = new File(path);
                        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
                        return part;
                    }
                })
                .toList()
                .flatMap(new Func1<List<MultipartBody.Part>, Observable<List<DocumentDisplayBean>>>() {
                    @Override
                    public Observable<List<DocumentDisplayBean>> call(List<MultipartBody.Part> parts) {
                        RequestBody kauriId = RequestBody.create(MediaType.parse("text/plain"), kauriHealthId);
                        if (parts.size() > 0) return uploadDocuments(kauriId, parts);
                        return Observable.create(new Observable.OnSubscribe<List<DocumentDisplayBean>>() {
                            @Override
                            public void call(Subscriber<? super List<DocumentDisplayBean>> subscriber) {
                                subscriber.onNext(new ArrayList<DocumentDisplayBean>());
                                subscriber.onCompleted();
                            }
                        });
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    //上传文件病例图像
    @Override
    public Observable<List<DocumentDisplayBean>> uploadDocuments(RequestBody kauriHealthId, List<MultipartBody.Part> parts) {
        return mRemoteData.uploadDocuments(kauriHealthId, parts);
    }

    //提现信息
    @Override
    public Observable<List<CreditTransactionDisplayBean>> CreditTransactions() {
        return mRemoteData.CreditTransactions();
    }

    //插入新的临床诊疗记录 医生和患者的token都可以
    @Override
    public Observable<PatientRecordDisplayBean> addNewPatientRecord(NewPatientRecordDisplayBean newPatientRecordDisplayBean) {
        return mRemoteData.addNewPatientRecord(newPatientRecordDisplayBean);
    }

    @Override
    public Observable<List<DoctorPatientRelationshipBean>> LoadDoctorPatientRelationshipForDoctor
            () {
        return mRemoteData.LoadDoctorPatientRelationshipForDoctor();
    }

    //查询所有用户医生关系的病例（包含拒绝的） 医生使用的token
    @Override
    public Observable<List<DoctorRelationshipBean>> LoadDoctorRelationships() {
        return mRemoteData.LoadDoctorRelationships();
    }

    //搜索关键字
    @Override
    public Observable<SearchResultBean> KeywordSearch(InitialiseSearchRequestBean initialiseSearchRequestBean) {
        return mRemoteData.keyWordsSearch(initialiseSearchRequestBean);
    }

    //开启新的医医关系
    @Override
    public Observable<ResponseDisplayBean> RequestNewDoctorRelationship
    (NewDoctorRelationshipBean bean) {
        return mRemoteData.RequestNewDoctorRelationship(bean);
    }

    //接受医生关系
    @Override
    public Observable<ResponseDisplayBean> AcceptDoctorRelationship(String DoctorRelationshipId) {
        return mRemoteData.AcceptDoctorRelationship(DoctorRelationshipId);
    }

    //拒绝医生关系
    @Override
    public Observable<ResponseDisplayBean> RejectDoctorRelationship(String DoctorRelationshipId) {
        return mRemoteData.RejectDoctorRelationship(DoctorRelationshipId);
    }

    //查询所有同意的协作医生
    @Override
    public Observable<List<DoctorCooperationBean>> loadAcceptedCooperationDoctors() {
        return mRemoteData.loadAcceptedCooperationDoctors();
    }

    //拒绝患者请求
    @Override
    public Observable<ResponseDisplayBean> RejectPatientRequest(PatientRequestDecisionBean patientRequestDecisionBean) {
        return mRemoteData.RejectPatientRequest(patientRequestDecisionBean);
    }

    //接受患者请求
    @Override
    public Observable<ResponseDisplayBean> AcceptPatientRequest(PatientRequestDecisionBean patientRequestDecisionBean) {
        return mRemoteData.AcceptPatientRequest(patientRequestDecisionBean);
    }

    //插入新的辅助检查
    @Override
    public Observable<PatientRecordDisplayBean> InsertSupplementaryTest
    (NewSupplementaryTestPatientRecordDisplayBean bean) {
        return mRemoteData.InsertSupplementaryTest(bean);
    }

    //更新辅助检查
    @Override
    public Observable<PatientRecordDisplayBean> UpdateSupplementaryTest
    (PatientRecordDisplayBean bean) {
        return mRemoteData.UpdateSupplementaryTest(bean);
    }

    //查询所有用户医生关系的病例（包含拒绝的） 医生使用的token
    @Override
    public Observable<List<DoctorRelationshipBean>> loadAllDoctorRelationships() {
        return mRemoteData.loadAllDoctorRelationships();
    }

    //插入新的病例记录
    @Override
    public Observable<PatientRecordDisplayBean> InsertPathologyRecord(NewPathologyPatientRecordDisplayBean bean) {
        return mRemoteData.InsertPathologyRecord(bean);
    }

    //更新病理记录
    @Override
    public Observable<PatientRecordDisplayBean> UpdatePathologyRecord(PatientRecordDisplayBean bean) {
        return mRemoteData.UpdatePathologyRecord(bean);
    }

    //插入实验室记录
    @Override
    public Observable<PatientRecordDisplayBean> InsertLabTest(NewLabTestPatientRecordDisplayBean bean) {
        return mRemoteData.InsertLabTest(bean);
    }

    //更新实验室检查
    @Override
    public Observable<PatientRecordDisplayBean> UpdateLabTest(PatientRecordDisplayBean bean) {
        return mRemoteData.UpdateLabTest(bean);
    }

    //申请结束医患关系
    @Override
    public Observable<ResponseDisplayBean> RequestEndDoctorPatientRelationship(int doctorPatientReplationshipId) {
        return mRemoteData.RequestEndDoctorPatientRelationship(doctorPatientReplationshipId);
    }

    //读取医生相关好友列表
    @Override
    public Observable<List<ContactUserDisplayBean>> loadContactListByDoctorId() {
        return mRemoteData.loadContactListByDoctorId();
    }

    @Override
    public Observable<SoftwareInfo> CheckVersion(Map<String, String> options) {
        return mRemoteData.CheckVersion(options);
    }

    //结束医生关联 医生使用的token
    @Override
    public Observable<ResponseDisplayBean> removeDoctorRelationship(int id) {
        return mRemoteData.removeDoctorRelationship(id);
    }
    //通过医生查询转诊状态为等待的请求
    @Override
    public Observable<List<PatientRequestDisplayBean>> LoadReferralsPatientRequestByDoctorId() {
        return mRemoteData.LoadReferralsPatientRequestByDoctorId();
    }
    //向多个医生转诊医生
    @Override
    public Observable<ResponseDisplayBean> InsertPatientRequestReferralByDoctorList(PatientRequestReferralDoctorDisplayBean bean) {
        return mRemoteData.InsertPatientRequestReferralByDoctorList(bean);
    }

    //向一个医生转诊多个患者
    @Override
    public Observable<ResponseDisplayBean> InsertPatientRequestReferralByPatientList(PatientRequestReferralPatientDisplayBean bean) {
        return mRemoteData.InsertPatientRequestReferralByPatientList(bean);
    }

    //使用患者id查询医疗团队，查询者必须为医生  -->医疗团队
    @Override
    public Observable<List<DoctorPatientRelationshipBean>> loadDoctorTeamForPatient(int patientId) {
        return mRemoteData.LoadDoctorTeamForPatient(patientId);
    }


    //根据患者id查询医患关系
    @Override
    public Observable<List<DoctorPatientRelationshipBean>> LoadDoctorPatientRelationshipForPatientId(int patientId) {
        return mRemoteData.LoadDoctorPatientRelationshipForPatientId(patientId);
    }


}
