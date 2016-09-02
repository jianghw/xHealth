package com.kaurihealth.datalib.remote;


import com.kaurihealth.datalib.local.LocalData;
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
import com.kaurihealth.datalib.service.IChangePasswordService;
import com.kaurihealth.datalib.service.IDoctorPatientRelationshipService;
import com.kaurihealth.datalib.service.IDoctorService;
import com.kaurihealth.datalib.service.ILiteratureCommentService;
import com.kaurihealth.datalib.service.ILiteratureReplyService;
import com.kaurihealth.datalib.service.ILoginService;
import com.kaurihealth.datalib.service.ILongtermMonitoringService;
import com.kaurihealth.datalib.service.IMedicalLiteratureLikeService;
import com.kaurihealth.datalib.service.IMedicalLiteratureService;
import com.kaurihealth.datalib.service.IPatientService;
import com.kaurihealth.datalib.service.IPriceService;
import com.kaurihealth.datalib.service.IRegisterService;
import com.kaurihealth.datalib.service.ISearchService;
import com.kaurihealth.datalib.service.IUserCashOutAccountService;
import com.kaurihealth.utilslib.constant.Global;

import java.util.List;

import javax.inject.Singleton;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by jianghw on 2016/8/8.
 * <p/>
 * 描述：网络操作
 */
@Singleton
public class RemoteData implements IRemoteSource {
    /**
     * 构建 retrofit
     *
     * @param requirements 标记
     * @return
     */
    public Retrofit getRetrofit(String requirements) {
        return RetrofitFactory.getInstance().createRetrofit(requirements);
    }

    /**
     * 构建 最多通用 retrofit
     *
     * @return
     */
    public Retrofit getDefaultRetrofit() {
        return RetrofitFactory.getInstance().createRetrofit(Global.Factory.DEFAULT_TOKEN);
    }

    /**
     * 登录
     */
    public Observable<TokenBean> userLogin(LoginBean bean) {
        String environment = LocalData.getLocalData().getEnvironment();
        if (environment.equals(Global.Environment.DEVELOP)) {
            return getRetrofit(Global.Factory.NO_TOKEN).create(ILoginService.class).LoginTest(bean);
        } else {
            return getRetrofit(Global.Factory.NO_TOKEN).create(ILoginService.class).Login(bean);
        }
    }

    /**
     * 注册
     */
    public Observable<RegisterResponse> userRegister(NewRegisterBean newRegisterBean) {
        return getRetrofit(Global.Factory.NO_TOKEN).create(IRegisterService.class).Regist(newRegisterBean);
    }

    @Override
    public Observable<DoctorDisplayBean> loadDoctorDetail() {
        return getDefaultRetrofit().create(IDoctorService.class).LoadDoctorDetail();
    }

    @Override
    public Observable<DoctorDisplayBean> updateDoctor(DoctorDisplayBean doctorDisplayBean) {
        return getDefaultRetrofit().create(IDoctorService.class).UpdateDoctor(doctorDisplayBean);
    }

    /**
     * 请求验证,要求短信中心发送验证码：找回密码+注册
     */
    public Observable<InitiateVerificationResponse> InitiateVerification(InitiateVerificationBean bean) {
        return getRetrofit(Global.Factory.NO_TOKEN).create(IRegisterService.class).InitiateVerification(bean);
    }


    /**
     * 找回密码 findpassword
     */
    @Override
    public Observable<ResponseDisplayBean> findPassword(RequestResetPasswordDisplayDto bean) {
        return getRetrofit(Global.Factory.NO_TOKEN).create(IChangePasswordService.class).RequestResetUserPassword(bean);
    }


    /**
     * 重置密码： resetPassword  无需token
     *
     * @param bean
     * @return
     */
    @Override
    public Observable<ResponseDisplayBean> resetPassword(ResetPasswordDisplayBean bean) {
        return getRetrofit(Global.Factory.NO_TOKEN).create(IChangePasswordService.class).ResetUserPassword(bean);
    }

    //医生给患者快速注册  需要token
    @Override
    public Observable<RegisterResponse> openAnAccount(NewRegistByDoctorBean bean) {
        return getDefaultRetrofit().create(IRegisterService.class).RegistByDoctor(bean);
    }

    @Override
    public Observable<SearchResultBean> search(InitialiseSearchRequestBean bean) {
        return getDefaultRetrofit().create(ISearchService.class).KeywordSearch(bean);
    }

    @Override
    public Observable<List<DoctorPatientRelationshipBean>> loadDoctorPatientRelationshipForDoctor() {
        return getDefaultRetrofit().create(IDoctorPatientRelationshipService.class).loadDoctorPatientRelationshipForDoctor();
    }


    /**
     * 首页里搜索:所有，医院，科室，医生
     * @param bean
     * @return
     */
    @Override
    public Observable<SearchResultBean> keyWordsSearch(InitialiseSearchRequestBean bean) {
        return getDefaultRetrofit().create(ISearchService.class).KeywordSearch(bean);
    }


    /**
     * 首页里搜索:患者搜索
     * @param content
     * @return
     */
    @Override
    public Observable<List<PatientDisplayBean>> SearchPatientByUserName(String content) {
        return getDefaultRetrofit().create(IPatientService.class).SearchPatientByUserName(content);
    }

    /**
     *
     * @param bean
     * @return
     * 描述: 我的-->设置-->修改密码
     */
    //IRemoteSource接口重写
    @Override
    public Observable<ResponseDisplayBean> updateUserPassword(NewPasswordDisplayBean bean) {
        return getDefaultRetrofit().create(IChangePasswordService.class).UpdateUserPassword(bean);
    }

    /**
     * 需要:token
     * 描述: 我的-->我的账户--> 提现通道--> 获取用户所有的提现账号
     * @return
     */
    @Override
    public Observable<List<UserCashOutAccountDisplayBean>> getUserCashOutAccounts() {
        return getDefaultRetrofit().create(IUserCashOutAccountService.class).GetUserCashOutAccounts();
    }

    /**
     *发送提现请求
     * 需要：token
     */

    @Override
    public Observable<ResponseDisplayBean> startNewCashOut(NewCashOutBean bean) {
        return getDefaultRetrofit().create(IUserCashOutAccountService.class).StartNewCashOut(bean);
    }

    /**
     * 点赞
     * @param newMedicalLiteratureLikeDisplayBean
     * @return
     */
    @Override
    public Observable<ResponseDisplayBean> CheckMedicalLiteratureLike(NewMedicalLiteratureLikeDisplayBean newMedicalLiteratureLikeDisplayBean) {
        return getDefaultRetrofit().create(IMedicalLiteratureLikeService.class).CheckMedicalLiteratureLikeNew(newMedicalLiteratureLikeDisplayBean);
    }

    /**
     * 取消赞
     * @param newMedicalLiteratureLikeDisplayBean
     * @return
     */
    @Override
    public Observable<ResponseDisplayBean> DisLikeMedicalLiterature(NewMedicalLiteratureLikeDisplayBean newMedicalLiteratureLikeDisplayBean) {
        return getDefaultRetrofit().create(IMedicalLiteratureLikeService.class).DisLikeMedicalLiterature_new(newMedicalLiteratureLikeDisplayBean);
    }

    /**
     * 加载临床评论
     * @param medicalLiteratureId
     * @return
     */
    @Override
    public Observable<List<LiteratureCommentDisplayBean>> LoadLiteratureCommentsByMedicalLiteratureId(int medicalLiteratureId) {
        return getDefaultRetrofit().create(ILiteratureCommentService.class).LoadLiteratureCommentsByMedicalLiteratureId_new(medicalLiteratureId);
    }

    //加载临床评论
    @Override
    public Observable<LiteratureCommentDisplayBean> InsertLiteratureComment(NewLiteratureCommentDisplayBean newLiteratureCommentDisplayBean) {
        return getDefaultRetrofit().create(ILiteratureCommentService.class).InsertLiteratureComment_new(newLiteratureCommentDisplayBean);
    }

    @Override
    public Observable<ResponseDisplayBean> updateDoctorProductPrice(PriceDisplayBean bean) {
        return getDefaultRetrofit().create(IPriceService.class).UpdateDoctorProductPrice(bean);
    }

    @Override
    public Observable<ResponseDisplayBean> insertPrice(NewPriceBean bean) {
        return getDefaultRetrofit().create(IPriceService.class).InsertPrice(bean);
    }

    //临床支持点赞
    @Override
    public Observable<MedicalLiteratureLikeDisplayBean> LikeMedicalLiterature(NewMedicalLiteratureLikeDisplayBean newMedicalLiteratureLikeDisplayBean) {
        return getDefaultRetrofit().create(IMedicalLiteratureLikeService.class).LikeMedicalLiterature_new(newMedicalLiteratureLikeDisplayBean);
    }

    //插入新的回复
    @Override
    public Observable<LiteratureReplyDisplayBean> InsertLiteratureReply(NewLiteratureReplyDisplayBean newLiteratureReplyDisplayBean) {
        return getDefaultRetrofit().create(ILiteratureReplyService.class).InsertLiteratureReply_new(newLiteratureReplyDisplayBean);
    }

    /**
     * 根据临床支持类型获得数据
     * @param medicalLiteratureType
     * @return
     */
    @Override
    public Observable<List<MedicalLiteratureDisPlayBean>> LoadAllMedicalLitreaturesByType(String medicalLiteratureType) {
        return getDefaultRetrofit().create(IMedicalLiteratureService.class).LoadAllMedicalLitreaturesByType(medicalLiteratureType);
    }

    /**
     * 通過臨床id獲取數據
     * @param medicalLiteratureId
     * @return
     */
    @Override
    public Observable<MedicalLiteratureDisPlayBean> LoadMedicalLiteratureById(int medicalLiteratureId) {
        return getDefaultRetrofit().create(IMedicalLiteratureService.class).LoadMedicalLiteratureById(medicalLiteratureId);
    }


    /**
     * 更新用户信息  需要token
     */
    public Observable<ResponseDisplayBean> UpdateDoctorUserInformation(DoctorUserBean bean) {
        return getDefaultRetrofit().create(IDoctorService.class).UpdateDoctorUserInformation(bean);
    }

    /**
     *  医生创建普通病人关系(复诊)
     * @param patientId
     * @return
     */
    @Override
    public Observable<DoctorPatientRelationshipBean> insertNewRelationshipByDoctor(int patientId) {
        return getDefaultRetrofit().create(IDoctorPatientRelationshipService.class).insertNewRelationshipByDoctor(patientId);
    }

    /**
     * 通过患者ID查询长期监测 医生和患者的token都可以用
     * @param patientId
     * @return
     */
    @Override
    public Observable<List<LongTermMonitoringDisplayBean>> longTermMonitoringDisplay(int patientId) {
        return getDefaultRetrofit().create(ILongtermMonitoringService.class).longTermMonitoringDisplay(patientId);
    }

    @Override
    public Observable<ResponseDisplayBean> insertLongtermMonitorings(List<NewLongtermMonitoringBean> beans) {
        return getDefaultRetrofit().create(ILongtermMonitoringService.class).insertLongtermMonitorings(beans);
    }

    @Override
    public Observable<List<LongTermMonitoringDisplayBean>> updateLongtermMonitorings(List<LongTermMonitoringDisplayBean> list) {
        return getDefaultRetrofit().create(ILongtermMonitoringService.class).updateLongtermMonitorings(list);
    }

}
