package com.kaurihealth.datalib.repository;


import com.avos.avoscloud.im.v2.AVIMConversation;
import com.example.chatlibrary.ChatFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.kaurihealth.datalib.local.ILocalSource;
import com.kaurihealth.datalib.remote.IRemoteSource;
import com.kaurihealth.datalib.request_bean.bean.DoctorUserBean;
import com.kaurihealth.datalib.request_bean.bean.InitialiseSearchRequestBean;
import com.kaurihealth.datalib.request_bean.bean.InitiateVerificationBean;
import com.kaurihealth.datalib.request_bean.bean.InitiateVerificationResponse;
import com.kaurihealth.datalib.request_bean.bean.LiteratureCommentDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.LiteratureReplyDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.LoginBean;
import com.kaurihealth.datalib.request_bean.bean.MedicalLiteratureDisPlayBean;
import com.kaurihealth.datalib.request_bean.bean.MedicalLiteratureLikeDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.NewCashOutBean;
import com.kaurihealth.datalib.request_bean.bean.NewLiteratureCommentDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.NewLiteratureReplyDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.NewLongtermMonitoringBean;
import com.kaurihealth.datalib.request_bean.bean.NewMedicalLiteratureLikeDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.NewPasswordDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.NewPriceBean;
import com.kaurihealth.datalib.request_bean.bean.NewRegistByDoctorBean;
import com.kaurihealth.datalib.request_bean.bean.NewRegisterBean;
import com.kaurihealth.datalib.request_bean.bean.PriceDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.RegisterResponse;
import com.kaurihealth.datalib.request_bean.bean.RequestResetPasswordDisplayDto;
import com.kaurihealth.datalib.request_bean.bean.ResetPasswordDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.SearchResultBean;
import com.kaurihealth.datalib.request_bean.bean.UserCashOutAccountDisplayBean;
import com.kaurihealth.datalib.response_bean.DoctorDisplayBean;
import com.kaurihealth.datalib.response_bean.DoctorPatientRelationshipBean;
import com.kaurihealth.datalib.response_bean.LongTermMonitoringDisplayBean;
import com.kaurihealth.datalib.response_bean.PatientDisplayBean;
import com.kaurihealth.datalib.response_bean.ResponseDisplayBean;
import com.kaurihealth.datalib.response_bean.TokenBean;
import com.kaurihealth.utilslib.bugtag.BugTagUtils;
import com.kaurihealth.utilslib.log.LogUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

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
     * 登录操作
     *
     * @param bean 提交信息
     * @return
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
        return errorForBugTag(persistentData(mRemoteData.userRegister(newRegisterBean)));
    }

    /**
     * 请求短信验证码
     *
     * @param bean
     * @return
     */
    public Observable<InitiateVerificationResponse> InitiateVerification(InitiateVerificationBean bean) {

        return mRemoteData.InitiateVerification(bean);
    }

    public Observable<ResponseDisplayBean> UpdateDoctorUserInformation(DoctorUserBean bean) {

        return mRemoteData.UpdateDoctorUserInformation(bean);
    }

    @Override
    public Observable<DoctorDisplayBean> loadDoctorDetail() {
        Observable<DoctorDisplayBean> doctorDisplayBeanObservable = mRemoteData.loadDoctorDetail();
        return errorForBugTag(persistentData(doctorDisplayBeanObservable));
    }

    @Override
    public Observable<DoctorDisplayBean> updateDoctor(DoctorDisplayBean doctorDisplayBean) {
        return mRemoteData.updateDoctor(doctorDisplayBean);
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
     * 找回密码
     *
     * @param bean
     * @return
     */
    public Observable<ResponseDisplayBean> findPassword(RequestResetPasswordDisplayDto bean) {
        return mRemoteData.findPassword(bean);
    }

    /**
     * 重置密码
     *
     * @param bean
     * @return
     */
    @Override
    public Observable<ResponseDisplayBean> resetPassword(ResetPasswordDisplayBean bean) {
        return mRemoteData.resetPassword(bean);
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
     *
     * @return
     */
    @Override
    public Observable<List<DoctorPatientRelationshipBean>> loadDoctorPatientRelationshipForDoctor() {
        return persistentListData(
                mRemoteData.loadDoctorPatientRelationshipForDoctor()
        );
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
     *
     * @param name
     * @return
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
        return  mRemoteData.insertLongtermMonitorings(beans);
    }

    /**
     * 根据类型加载所有的临床支持
     * @param medicalLiteratureType
     * @return
     */
    @Override
    public Observable<List<MedicalLiteratureDisPlayBean>> LoadAllMedicalLitreaturesByType(String medicalLiteratureType) {
        return mRemoteData.LoadAllMedicalLitreaturesByType(medicalLiteratureType);
    }

    /**
     * 根据临床id加载最新临床信息
     * @param medicalLiteratureId
     * @return
     */
    @Override
    public Observable<MedicalLiteratureDisPlayBean> LoadMedicalLiteratureById(int medicalLiteratureId) {
        return mRemoteData.LoadMedicalLiteratureById(medicalLiteratureId);
    }


    /**
     *描述:我的Fragment-->我的账户--> 提现通道-->获取用户所有的提现账号
     * @return
     *
     */
    @Override
    public Observable<List<UserCashOutAccountDisplayBean>> getUserCashOutAccounts() {
        return mRemoteData.getUserCashOutAccounts();
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


    /**
     * 下层数据持久化封装 保存数据库
     *
     * @param observable 网络层数据来源
     * @param <T>
     * @return
     */
    private <T> Observable<T> persistentData(Observable<T> observable) {
        return observable.doOnNext(new Action1<T>() {
            @Override
            public void call(T bean) {
                //TODO 保存数据库
                mLocalData.insertEnsureByOne(bean);
                LogUtils.json(new Gson().toJson(bean));
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
     * 聊天 所有记录对象
     *
     * @return
     */
    @Override
    public Observable<List<AVIMConversation>> chatAllConversations() {
        return Observable.create(new Observable.OnSubscribe<List<AVIMConversation>>() {
            @Override
            public void call(Subscriber<? super List<AVIMConversation>> subscriber) {
                ChatFactory.instance().getAllConversation(subscriber);
            }
        });
    }
}
