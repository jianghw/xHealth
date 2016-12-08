package com.kaurihealth.datalib.remote;


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
import com.kaurihealth.datalib.request_bean.bean.NewFamilyMemberBean;
import com.kaurihealth.datalib.request_bean.bean.NewLabTestPatientRecordDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.NewLiteratureCommentDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.NewLiteratureReplyDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.NewLongtermMonitoringBean;
import com.kaurihealth.datalib.request_bean.bean.NewMedicalLiteratureLikeDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.NewNotifyConfigBean;
import com.kaurihealth.datalib.request_bean.bean.NewPasswordDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.NewPathologyPatientRecordDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.NewPatientRecordDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.NewPrescriptionBean;
import com.kaurihealth.datalib.request_bean.bean.NewPriceBean;
import com.kaurihealth.datalib.request_bean.bean.NewReferralMessageAlertBean;
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
import com.kaurihealth.datalib.response_bean.ContactUserDisplayBean;
import com.kaurihealth.datalib.response_bean.DepartmentDisplayBean;
import com.kaurihealth.datalib.response_bean.DoctorCooperationBean;
import com.kaurihealth.datalib.response_bean.DoctorDisplayBean;
import com.kaurihealth.datalib.response_bean.DoctorPatientRelationshipBean;
import com.kaurihealth.datalib.response_bean.DoctorRelationshipBean;
import com.kaurihealth.datalib.response_bean.DocumentDisplayBean;
import com.kaurihealth.datalib.response_bean.FamilyMemberBean;
import com.kaurihealth.datalib.response_bean.InitiateVerificationResponse;
import com.kaurihealth.datalib.response_bean.LongTermMonitoringDisplayBean;
import com.kaurihealth.datalib.response_bean.NotifyConfigDisplayBean;
import com.kaurihealth.datalib.response_bean.NotifyIsReadDisplayBean;
import com.kaurihealth.datalib.response_bean.PatientDisplayBean;
import com.kaurihealth.datalib.response_bean.PatientRecordDisplayBean;
import com.kaurihealth.datalib.response_bean.PriceDisplayBean;
import com.kaurihealth.datalib.response_bean.ReferralMessageAlertDisplayBean;
import com.kaurihealth.datalib.response_bean.RegisterResponse;
import com.kaurihealth.datalib.response_bean.ResponseDisplayBean;
import com.kaurihealth.datalib.response_bean.SearchResultBean;
import com.kaurihealth.datalib.response_bean.SoftwareInfo;
import com.kaurihealth.datalib.response_bean.TokenBean;
import com.kaurihealth.datalib.response_bean.UserCashOutAccountDisplayBean;
import com.kaurihealth.datalib.response_bean.UserNotifyDisplayBean;
import com.kaurihealth.datalib.service.IChangePasswordService;
import com.kaurihealth.datalib.service.ICheckVersionService;
import com.kaurihealth.datalib.service.IContacListService;
import com.kaurihealth.datalib.service.ICreditTransactionService;
import com.kaurihealth.datalib.service.IDepartmentService;
import com.kaurihealth.datalib.service.IDoctorCooperationService;
import com.kaurihealth.datalib.service.IDoctorPatientRelationshipService;
import com.kaurihealth.datalib.service.IDoctorRelationshipService;
import com.kaurihealth.datalib.service.IDoctorService;
import com.kaurihealth.datalib.service.IDocumentService;
import com.kaurihealth.datalib.service.IFamilyGroupService;
import com.kaurihealth.datalib.service.IHealthConditionService;
import com.kaurihealth.datalib.service.IHospitalsService;
import com.kaurihealth.datalib.service.ILabTestService;
import com.kaurihealth.datalib.service.ILiteratureCommentService;
import com.kaurihealth.datalib.service.ILiteratureReplyService;
import com.kaurihealth.datalib.service.ILoginService;
import com.kaurihealth.datalib.service.ILongtermMonitoringService;
import com.kaurihealth.datalib.service.IMedicalLiteratureLikeService;
import com.kaurihealth.datalib.service.IMedicalLiteratureService;
import com.kaurihealth.datalib.service.INotifyConfigService;
import com.kaurihealth.datalib.service.INotifyService;
import com.kaurihealth.datalib.service.IPathologyRecordService;
import com.kaurihealth.datalib.service.IPatientRecordService;
import com.kaurihealth.datalib.service.IPatientRequestService;
import com.kaurihealth.datalib.service.IPatientService;
import com.kaurihealth.datalib.service.IPrescriptionService;
import com.kaurihealth.datalib.service.IPriceService;
import com.kaurihealth.datalib.service.IPushNotificationDeviceService;
import com.kaurihealth.datalib.service.IRecordService;
import com.kaurihealth.datalib.service.IReferralMessageAlertService;
import com.kaurihealth.datalib.service.IRegisterService;
import com.kaurihealth.datalib.service.ISearchService;
import com.kaurihealth.datalib.service.ISupplementaryTestService;
import com.kaurihealth.datalib.service.IUserCashOutAccountService;
import com.kaurihealth.utilslib.constant.Global;

import java.util.List;
import java.util.Map;

import javax.inject.Singleton;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by jianghw on 2016/8/8.
 * <p>
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
    private Retrofit getRetrofit(String requirements) {
        return RetrofitFactory.getInstance().createRetrofit(requirements);
    }

    /**
     * 构建 最多通用 retrofit
     *
     * @return
     */
    private Retrofit getDefaultRetrofit() {
        return RetrofitFactory.getInstance().createRetrofit(Global.Factory.DEFAULT_TOKEN);
    }

    /**
     * 构建 最多通用 retrofit
     *
     * @return
     */
    private Retrofit getCheckVersionRetrofit() {
        return RetrofitFactory.getInstance().createRetrofit(Global.Factory.LoadAndroidVersionWithDoctor);
    }

    /**
     * 登录
     */
    public Observable<TokenBean> userLogin(LoginBean bean) {
        return getRetrofit(Global.Factory.NO_TOKEN).create(ILoginService.class).Login(bean);
    }

    /**
     * 注册
     */
    public Observable<RegisterResponse> userRegister(NewRegisterBean newRegisterBean) {
        return getRetrofit(Global.Factory.NO_TOKEN).create(IRegisterService.class).Regist(newRegisterBean);
    }

    /**
     * 更新用户信息  需要token
     */
    public Observable<ResponseDisplayBean> updateDoctorUserInformation(DoctorUserBean bean) {
        return getDefaultRetrofit().create(IDoctorService.class).UpdateDoctorUserInformation(bean);
    }

    /**
     * 请求验证,要求短信中心发送验证码：找回密码+注册
     */
    public Observable<InitiateVerificationResponse> InitiateVerification(InitiateVerificationBean bean) {
        return getRetrofit(Global.Factory.NO_TOKEN).create(IRegisterService.class).InitiateVerification(bean);
    }

    /**
     * 找回密码 findpassword  --申请重置密码
     */
    @Override
    public Observable<ResponseDisplayBean> findPassword(RequestResetPasswordDisplayBean bean) {
        return getRetrofit(Global.Factory.NO_TOKEN).create(IChangePasswordService.class).RequestResetUserPassword(bean);
    }

    /**
     * 重置密码： resetPassword  无需token
     */
    @Override
    public Observable<ResponseDisplayBean> resetPassword(ResetPasswordDisplayBean bean) {
        return getRetrofit(Global.Factory.NO_TOKEN).create(IChangePasswordService.class).ResetUserPassword(bean);
    }


    //查询医生信息
    @Override
    public Observable<DoctorDisplayBean> loadDoctorDetail() {
        return getDefaultRetrofit().create(IDoctorService.class).loadDoctorDetail();
    }


    @Override
    public Observable<DoctorDisplayBean> updateDoctor(DoctorDisplayBean doctorDisplayBean) {
        return getDefaultRetrofit().create(IDoctorService.class).UpdateDoctor(doctorDisplayBean);
    }

    @Override
    public Observable<ResponseDisplayBean> UpdateDoctorUserInformation(DoctorUserBean bean) {
        return null;
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


    //为现医生查询所有医患关系
    @Override
    public Observable<List<DoctorPatientRelationshipBean>> loadDoctorPatientRelationshipForDoctor() {
        return getDefaultRetrofit().create(IDoctorPatientRelationshipService.class).loadDoctorPatientRelationshipForDoctor();
    }


    /**
     * 首页里搜索:所有，医院，科室，医生
     *
     * @param bean
     * @return
     */
    @Override
    public Observable<SearchResultBean> keyWordsSearch(InitialiseSearchRequestBean bean) {
        return getDefaultRetrofit().create(ISearchService.class).KeywordSearch(bean);
    }

    /**
     * 首页里搜索:患者搜索
     *
     * @param content
     * @return
     */
    @Override
    public Observable<List<PatientDisplayBean>> SearchPatientByUserName(String content) {
        return getDefaultRetrofit().create(IPatientService.class).SearchPatientByUserName(content);
    }

    /**
     * @param bean
     * @return 描述: 我的-->设置-->修改密码
     */
    //IRemoteSource接口重写
    @Override
    public Observable<ResponseDisplayBean> updateUserPassword(NewPasswordDisplayBean bean) {
        return getDefaultRetrofit().create(IChangePasswordService.class).UpdateUserPassword(bean);
    }

    /**
     * 需要:token
     * 描述: 我的-->我的账户--> 提现通道--> 获取用户所有的提现账号
     */
    @Override
    public Observable<List<UserCashOutAccountDisplayBean>> getUserCashOutAccounts() {
        return getDefaultRetrofit().create(IUserCashOutAccountService.class).GetUserCashOutAccounts();
    }

    /**
     * 发送提现请求
     * 需要：token
     */

    @Override
    public Observable<ResponseDisplayBean> startNewCashOut(NewCashOutBean bean) {
        return getDefaultRetrofit().create(IUserCashOutAccountService.class).StartNewCashOut(bean);
    }

    @Override
    public Observable<List<DepartmentDisplayBean>> loadAllDepartment() {
        return getDefaultRetrofit().create(IDepartmentService.class).LoadAllDepartment();
    }

    /**
     * 点赞
     *
     * @param newMedicalLiteratureLikeDisplayBean
     * @return
     */
    @Override
    public Observable<ResponseDisplayBean> CheckMedicalLiteratureLike(NewMedicalLiteratureLikeDisplayBean newMedicalLiteratureLikeDisplayBean) {
        return getDefaultRetrofit().create(IMedicalLiteratureLikeService.class).CheckMedicalLiteratureLikeNew(newMedicalLiteratureLikeDisplayBean);
    }

    /**
     * 取消赞
     *
     * @param newMedicalLiteratureLikeDisplayBean
     * @return
     */
    @Override
    public Observable<ResponseDisplayBean> DisLikeMedicalLiterature(NewMedicalLiteratureLikeDisplayBean newMedicalLiteratureLikeDisplayBean) {
        return getDefaultRetrofit().create(IMedicalLiteratureLikeService.class).DisLikeMedicalLiterature_new(newMedicalLiteratureLikeDisplayBean);
    }

    /**
     * 加载临床评论
     *
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

    //插入新的价格
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
     *
     * @param medicalLiteratureType
     * @return
     */
    @Override
    public Observable<List<MedicalLiteratureDisPlayBean>> LoadAllMedicalLitreaturesByType(String medicalLiteratureType) {
        return getDefaultRetrofit().create(IMedicalLiteratureService.class).LoadAllMedicalLitreaturesByType(medicalLiteratureType);
    }


    /**
     * 通過臨床id獲取數據
     *
     * @param medicalLiteratureId
     * @return
     */
    @Override
    public Observable<MedicalLiteratureDisPlayBean> LoadMedicalLiteratureById(int medicalLiteratureId) {
        return getDefaultRetrofit().create(IMedicalLiteratureService.class).LoadMedicalLiteratureById(medicalLiteratureId);
    }

    //科室： 加载所有的一级科室  需要token
    @Override
    public Observable<List<DepartmentDisplayBean>> loadDepartment() {
        return getDefaultRetrofit().create(IDepartmentService.class).LoadDepartment();
    }

    //科室： 加载二级子类科室  需要token
    @Override
    public Observable<List<DepartmentDisplayBean>> loadDepartmentName(int level, int parentDepartmentId) {
        return getDefaultRetrofit().create(IDepartmentService.class).LoadDepartmentName(level, parentDepartmentId);
    }

    /**
     * 加载健康记录信息
     *
     * @param patientId
     * @return
     */
    @Override
    public Observable<List<HealthConditionDisplayBean>> LoadHealthConditionByPatientId(int patientId) {
        return getDefaultRetrofit().create(IHealthConditionService.class).LoadHealthConditionByPatientId_new(patientId);
    }

    /**
     * 更新健康记录
     *
     * @param healthConditionDisplayBeans
     * @return
     */
    @Override
    public Observable<HealthConditionDisplayBean> UpdateHealthConditions(HealthConditionDisplayBean[] healthConditionDisplayBeans) {
        return getDefaultRetrofit().create(IHealthConditionService.class).UpdateHealthConditions_new(healthConditionDisplayBeans);
    }

    /**
     * 删除健康记录
     *
     * @param ids
     * @return
     */
    @Override
    public Observable<ResponseDisplayBean> DeleteHealthConditions(Integer[] ids) {
        return getDefaultRetrofit().create(IHealthConditionService.class).DeleteHealthConditions_new(ids);
    }

    /**
     * 医生创建普通病人关系(复诊)
     *
     * @param patientId
     * @return
     */
    @Override
    public Observable<DoctorPatientRelationshipBean> insertNewRelationshipByDoctor(int patientId) {
        return getDefaultRetrofit().create(IDoctorPatientRelationshipService.class).insertNewRelationshipByDoctor(patientId);
    }

    /**
     * 通过患者ID查询长期监测 医生和患者的token都可以用
     *
     * @param patientId
     * @return
     */
    @Override
    public Observable<List<LongTermMonitoringDisplayBean>> longTermMonitoringDisplay(int patientId) {
        return getDefaultRetrofit().create(ILongtermMonitoringService.class).loadLongtermMonitoringByPatientId(patientId);
    }

    /**
     * 添加多个长期监测
     */
    @Override
    public Observable<ResponseDisplayBean> insertLongtermMonitorings(List<NewLongtermMonitoringBean> beans) {
        return getDefaultRetrofit().create(ILongtermMonitoringService.class).insertLongtermMonitorings(beans);
    }

    /**
     * 编辑多个长期监测
     */
    @Override
    public Observable<List<LongTermMonitoringDisplayBean>> updateLongtermMonitorings(List<LongTermMonitoringDisplayBean> list) {
        return getDefaultRetrofit().create(ILongtermMonitoringService.class).updateLongtermMonitorings(list);
    }

    /**
     * 删除多个长期监测
     */
    @Override
    public Observable<ResponseDisplayBean> deleteLongtermMonitorings(List<Integer> bean) {
        return getDefaultRetrofit().create(ILongtermMonitoringService.class).deleteLongtermMonitorings(bean);
    }

    /**
     * 通过患者ID查询患者的医疗记录
     */
    @Override
    public Observable<List<PatientRecordDisplayBean>> loadAllPatientGenericRecordsBypatientId(int patientID) {
        return getDefaultRetrofit().create(IPatientRecordService.class).loadAllPatientGenericRecordsBypatientId(patientID);
    }


    @Override
    public Observable<PrescriptionBean> UpdatePrescription(PrescriptionBean newPrescriptionBean) {
        return getDefaultRetrofit().create(IPrescriptionService.class).UpdatePrescription(newPrescriptionBean);
    }

    @Override
    public Observable<ResponseDisplayBean> DeleteLiteratureComment(int literatureCommentId) {
        return getDefaultRetrofit().create(ILiteratureCommentService.class).DeleteLiteratureComment_new(literatureCommentId);
    }


    @Override
    public Observable<List<LiteratureReplyDisplayBean>> LoadLiteratureReplyByLiteratureCommentId(int literatureCommentId) {
        return getDefaultRetrofit().create(ILiteratureReplyService.class).LoadLiteratureReplyByLiteratureCommentId_new(literatureCommentId);
    }

    /**
     * 需要token
     * 个人信息--> 机构--> 医院名称（string）数组
     */
    @Override
    public Observable<List<String>> LoadAllHospitals() {
        return getDefaultRetrofit().create(IHospitalsService.class).loadAllHospitals();
    }

    @Override
    public Observable<List<MedicalLiteratureDisPlayBean>> LoadAllMedicalLiteratures() {
        return getDefaultRetrofit().create(IMedicalLiteratureService.class).LoadAllMedicalLiteratures_new();
    }

    @Override
    public Observable<List<PrescriptionBean>> loadPrescriptionsByPatientId(int patientId) {
        return getDefaultRetrofit().create(IPrescriptionService.class).LoadPrescriptionsByPatientId(patientId);
    }

    @Override
    public Observable<PrescriptionBean> insertPrescription(NewPrescriptionBean newPrescriptionBean) {
        return getDefaultRetrofit().create(IPrescriptionService.class).InsertPrescription(newPrescriptionBean);
    }

    //通过医生查询患者的请求
    @Override
    public Observable<List<PatientRequestDisplayBean>> LoadPatientRequestsByDoctor() {
        return getDefaultRetrofit().create(IPatientRequestService.class).LoadPatientRequestsByDoctor();
    }

    //移动端更新医生信息
    @Override
    public Observable<ResponseDisplayBean> mobileUpdateDoctor(MobileUpdateDoctorBean bean) {
        return getDefaultRetrofit().create(IDoctorService.class).MobileUpdateDoctor(bean);
    }

    //添加提现账号
    @Override
    public Observable<UserCashOutAccountDisplayBean> InsertUserCashOutAccount(NewCashOutAccountBean account) {
        return getDefaultRetrofit().create(IUserCashOutAccountService.class).InsertUserCashOutAccount_new(account);
    }

    //更新患者临床诊疗记录 医生和患者的token都可以
    @Override
    public Observable<PatientRecordDisplayBean> updatePatientRecord(PatientRecordDisplayBean patientRecordDisplayBean) {
        return getDefaultRetrofit().create(IRecordService.class).updatePatientRecord(patientRecordDisplayBean);
    }

    //插入图片
    @Override
    public Observable<List<DocumentDisplayBean>> uploadDocuments(RequestBody kauriHealthId, List<MultipartBody.Part> parts) {
        return getDefaultRetrofit().create(IDocumentService.class).uploadDocuments(kauriHealthId, parts);
    }

    //获得提现信息
    @Override
    public Observable<List<CreditTransactionDisplayBean>> CreditTransactions() {
        return getDefaultRetrofit().create(ICreditTransactionService.class).CreditTransactions_new();
    }

    //插入新的临床诊疗记录 医生和患者的token都可以
    @Override
    public Observable<PatientRecordDisplayBean> addNewPatientRecord(NewPatientRecordDisplayBean newPatientRecordDisplayBean) {
        return getDefaultRetrofit().create(IRecordService.class).addNewPatientRecord(newPatientRecordDisplayBean);
    }

    //查询用户医生关系的病例（不包含拒绝的） 医生使用的token
    @Override
    public Observable<List<DoctorRelationshipBean>> LoadDoctorRelationships() {
        return getDefaultRetrofit().create(IDoctorRelationshipService.class).loadDoctorRelationships();
    }

    //搜索关键字
    @Override
    public Observable<SearchResultBean> KeywordSearch(InitialiseSearchRequestBean initialiseSearchRequestBean) {
        return getDefaultRetrofit().create(ISearchService.class).KeywordSearch(initialiseSearchRequestBean);
    }

    //查询所有同意的协作医生
    @Override
    public Observable<List<DoctorCooperationBean>> loadAcceptedCooperationDoctors() {
        return getDefaultRetrofit().create(IDoctorCooperationService.class).loadAcceptedCooperationDoctors();
    }

    //开启新的医医关系
    @Override
    public Observable<ResponseDisplayBean> RequestNewDoctorRelationship(NewDoctorRelationshipBean bean) {
        return getDefaultRetrofit().create(IDoctorRelationshipService.class).requestNewDoctorRelationship(bean);
    }

    @Override
    public Observable<List<DoctorRelationshipBean>> LoadPendingDoctorRelationships() {
        return getDefaultRetrofit().create(IDoctorRelationshipService.class).LoadPendingDoctorRelationships();
    }

    //接受医生关系
    @Override
    public Observable<ResponseDisplayBean> AcceptDoctorRelationship(String DoctorRelationshipId) {
        return getDefaultRetrofit().create(IDoctorRelationshipService.class).AcceptDoctorRelationship(DoctorRelationshipId);
    }

    //拒绝医生关系
    @Override
    public Observable<ResponseDisplayBean> RejectDoctorRelationship(String DoctorRelationshipId) {
        return getDefaultRetrofit().create(IDoctorRelationshipService.class).RejectDoctorRelationship(DoctorRelationshipId);
    }

    //拒绝患者请求
    @Override
    public Observable<ResponseDisplayBean> RejectPatientRequest(PatientRequestDecisionBean patientRequestDecisionBean) {
        return getDefaultRetrofit().create(IPatientRequestService.class).RejectPatientRequest_new(patientRequestDecisionBean);
    }

    //接受患者请求
    @Override
    public Observable<ResponseDisplayBean> AcceptPatientRequest(PatientRequestDecisionBean patientRequestDecisionBean) {
        return getDefaultRetrofit().create(IPatientRequestService.class).AcceptPatientRequest_new(patientRequestDecisionBean);
    }

    //查询所有用户医生关系的病例（包含拒绝的） 医生使用的token
    @Override
    public Observable<List<DoctorRelationshipBean>> loadAllDoctorRelationships() {
        return getDefaultRetrofit().create(IDoctorRelationshipService.class).LoadAllDoctorRelationships();
    }

    //插入新的辅助检查
    @Override
    public Observable<PatientRecordDisplayBean> InsertSupplementaryTest(NewSupplementaryTestPatientRecordDisplayBean bean) {
        return getDefaultRetrofit().create(ISupplementaryTestService.class).InsertSupplementaryTest_new(bean);
    }

    //更新辅助检查
    @Override
    public Observable<PatientRecordDisplayBean> UpdateSupplementaryTest(PatientRecordDisplayBean bean) {
        return getDefaultRetrofit().create(ISupplementaryTestService.class).UpdateSupplementaryTest_new(bean);
    }

    //插入新的病理记录
    @Override
    public Observable<PatientRecordDisplayBean> InsertPathologyRecord(NewPathologyPatientRecordDisplayBean bean) {
        return getDefaultRetrofit().create(IPathologyRecordService.class).InsertPathologyRecord_new(bean);
    }

    //更新病理记录
    @Override
    public Observable<PatientRecordDisplayBean> UpdatePathologyRecord(PatientRecordDisplayBean bean) {
        return getDefaultRetrofit().create(IPathologyRecordService.class).UpdatePathologyRecord_new(bean);
    }

    //插入实验室记录
    @Override
    public Observable<PatientRecordDisplayBean> InsertLabTest(NewLabTestPatientRecordDisplayBean bean) {
        return getDefaultRetrofit().create(ILabTestService.class).InsertLabTest_new(bean);
    }

    //更新实验室检查
    @Override
    public Observable<PatientRecordDisplayBean> UpdateLabTest(PatientRecordDisplayBean bean) {
        return getDefaultRetrofit().create(ILabTestService.class).UpdateLabTest_new(bean);
    }

    //申请结束医患关系
    @Override
    public Observable<ResponseDisplayBean> RequestEndDoctorPatientRelationship(int doctorPatientReplationshipId) {
        return getDefaultRetrofit().create(IDoctorPatientRelationshipService.class).requestEndDoctorPatientRelationship(doctorPatientReplationshipId);
    }

    //根据医患id加载
    @Override
    public Observable<List<DoctorPatientRelationshipBean>> loadDoctorPatientRelationshipForPatientId(int patientId) {
        return getDefaultRetrofit().create(IDoctorPatientRelationshipService.class).loadDoctorPatientRelationshipForPatientId(patientId);
    }

    @Override
    public Observable<List<ContactUserDisplayBean>> loadContactListByDoctorId() {
        return getDefaultRetrofit().create(IContacListService.class).loadContactListByDoctorId();
    }

    //检查版本
    @Override
    public Observable<SoftwareInfo> CheckVersion(Map<String, String> options) {
        return getCheckVersionRetrofit().create(ICheckVersionService.class).CheckVersion(options);
    }

    //结束医生关联 医生使用的token
    @Override
    public Observable<ResponseDisplayBean> removeDoctorRelationship(int id) {
        return getDefaultRetrofit().create(IDoctorRelationshipService.class).loadContactListByDoctorId(id, "无");
    }

    //通过医生查询转诊状态为等待的请求
    @Override
    public Observable<List<PatientRequestDisplayBean>> LoadReferralsPatientRequestByDoctorId() {
        return getDefaultRetrofit().create(IPatientRecordService.class).LoadReferralsPatientRequestByDoctorId();
    }

    //向多个医生转诊患者
    @Override
    public Observable<ResponseDisplayBean> InsertPatientRequestReferralByDoctorList(PatientRequestReferralDoctorDisplayBean bean) {
        return getDefaultRetrofit().create(IPatientRequestService.class).InsertPatientRequestReferralByDoctorList(bean);
    }

    //向一个医生转诊多个患者
    @Override
    public Observable<ResponseDisplayBean> InsertPatientRequestReferralByPatientList(PatientRequestReferralPatientDisplayBean bean) {
        return getDefaultRetrofit().create(IPatientRequestService.class).InsertPatientRequestReferralByPatientList(bean);
    }

    //使用患者id查询医疗团队，查询者必须为医生   --> 医疗团队
    @Override
    public Observable<List<DoctorPatientRelationshipBean>> LoadDoctorTeamForPatient(int patientId) {
        return getDefaultRetrofit().create(IDoctorPatientRelationshipService.class).LoadDoctorTeamForPatient(patientId);
    }

    //根据医患关系ID加载复诊提醒信息
    @Override
    public Observable<List<ReferralMessageAlertDisplayBean>> loadReferralMessageAlert(int id) {
        return getDefaultRetrofit().create(IReferralMessageAlertService.class).loadReferralMessageAlert(id);
    }

    //插入新的复诊提醒信息
    @Override
    public Observable<ReferralMessageAlertDisplayBean> insertReferralMessageAlert(NewReferralMessageAlertBean bean) {
        return getDefaultRetrofit().create(IReferralMessageAlertService.class).insertReferralMessageAlert(bean);
    }

    //删除复诊提醒信息
    @Override
    public Observable<ReferralMessageAlertDisplayBean> deleteReferralMessageAlert(int id) {
        return getDefaultRetrofit().create(IReferralMessageAlertService.class).deleteReferralMessageAlert(id);
    }

    //患者信息--> 家庭成员 --> 添加家庭成员
    @Override
    public Observable<ResponseDisplayBean> addFamilyMemberByDoctor(NewFamilyMemberBean newFamilyMemberBean) {
        return getDefaultRetrofit().create(IFamilyGroupService.class).addFamilyMemberByDoctor(newFamilyMemberBean);
    }

    //查询该用户的所有系统消息
    @Override
    public Observable<List<UserNotifyDisplayBean>> loadUserAllNotify() {
        return getDefaultRetrofit().create(INotifyService.class).loadUserAllNotify();
    }

    //App和Web端 判断小红点是否显示时触发
    @Override
    public Observable<NotifyIsReadDisplayBean> isReadNotify() {
        return getDefaultRetrofit().create(INotifyService.class).isReadNotify();
    }

    //更新 已读 未读
    @Override
    public Observable<ResponseDisplayBean> updateUserNotifyIsRead(List<Integer> list) {
        return getDefaultRetrofit().create(INotifyService.class).updateUserNotifyIsRead(list);
    }

    //Web端和app 点击 “全部忽略”时触发
    @Override
    public Observable<ResponseDisplayBean> updateUserNotify() {
        return getDefaultRetrofit().create(INotifyService.class).updateUserNotify();
    }

    //患者信息--> 家庭成员(刷新列表)
    @Override
    public Observable<List<FamilyMemberBean>> loadAllFamilyMembers(int patientId) {
        return getDefaultRetrofit().create(IFamilyGroupService.class).loadAllFamilyMembers(patientId);
    }

    //插入推送设备 医生使用的token
    @Override
    public Observable<ResponseDisplayBean> insertNewPushNotificationDevice(String IdentityToken) {
        return getDefaultRetrofit().create(IPushNotificationDeviceService.class).insertNewPushNotificationDevice(IdentityToken, "Andorid");
    }

    //通过医生ID查询医生的信息
    @Override
    public Observable<DoctorDisplayBean> LoadDoctorDetailByDoctorId(int doctorId) {
        return getDefaultRetrofit().create(IDoctorService.class).LoadDoctorDetailByDoctorId(doctorId);
    }

    //查询该用户的系统消息设置
    @Override
    public Observable<NotifyConfigDisplayBean> loadNotifyConfig() {
        return getDefaultRetrofit().create(INotifyConfigService.class).loadNotifyConfig();
    }

    //更新系统消息设置
    @Override
    public Observable<ResponseDisplayBean> updateNotifyConfig(NewNotifyConfigBean newNotifyConfigBean) {
        return getDefaultRetrofit().create(INotifyConfigService.class).updateNotifyConfig(newNotifyConfigBean);
    }


}
