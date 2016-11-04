package com.kaurihealth.datalib.repository;


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

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;

/**
 * Created by jianghw on 2016/8/12.
 * <p>
 * 描述：仓库层接口
 */
public interface IDataSource {
    /**
     * 标记手动刷新
     */
    void manuallyRefresh();

    Observable<TokenBean> userLogin(LoginBean bean);

    //数据库查询
    <T> Observable<List<T>> databaseQueryAll(Class<T> cla);

    Observable<RegisterResponse> userRegister(NewRegisterBean newRegisterBean);

    Observable<InitiateVerificationResponse> InitiateVerification(InitiateVerificationBean bean);

    Observable<ResponseDisplayBean> updateDoctorUserInformation(DoctorUserBean bean);

    Observable<DoctorDisplayBean> loadDoctorDetail();

    Observable<DoctorDisplayBean> updateDoctor(DoctorDisplayBean doctorDisplayBean);

    //忘记密码.找回密码
    Observable<ResponseDisplayBean> findPassword(RequestResetPasswordDisplayBean bean);

    //重置密码
    Observable<ResponseDisplayBean> resetPassword(ResetPasswordDisplayBean bean);

    //医生帮患者注册开户
    Observable<RegisterResponse> openAnAccount(NewRegistByDoctorBean bean);

    //所有的患者
    Observable<List<DoctorPatientRelationshipBean>> loadDoctorPatientRelationshipForDoctor();

    //首页搜索：所有， 医院， 科室，医生
    Observable<SearchResultBean> keyWordsSearch(InitialiseSearchRequestBean bean);

    //首页搜索: 通过手机号查找患者
    Observable<List<PatientDisplayBean>> SearchPatientByUserName(String name);

    //医生创建普通病人关系(复诊)请传入patientId
    Observable<DoctorPatientRelationshipBean> insertNewRelationshipByDoctor(int patientId);

    //得到所有的临床信息
    Observable<List<MedicalLiteratureDisPlayBean>> LoadAllMedicalLitreaturesByType(String medicalLiteratureType);

    //我的--> 设置-->修改密码
    Observable<ResponseDisplayBean> updateUserPassword(NewPasswordDisplayBean bean);

    //通过患者ID查询长期监测 医生和患者的token都可以用
    Observable<List<LongTermMonitoringDisplayBean>> longTermMonitoringDisplay(int patientId);

    //插入长期监测集合
    Observable<ResponseDisplayBean> insertLongtermMonitorings(List<NewLongtermMonitoringBean> beans);

    //通过临床id获取对应的临床信息
    Observable<MedicalLiteratureDisPlayBean> LoadMedicalLiteratureById(int medicalLiteratureId);

    //我的-->我的账户--> 提现通道-->获取用户所有的提现账号
    Observable<List<UserCashOutAccountDisplayBean>> getUserCashOutAccounts();

    Observable<List<DepartmentDisplayBean>> loadAllDepartment();

    /**
     * 描述：我的-->我的账户-->“提现”按钮-->“确定”按钮
     * Api说明：发送提现请求
     */
    Observable<ResponseDisplayBean> startNewCashOut(NewCashOutBean bean);

    //检查登陆用户是否一堆某一个文献点赞。
    Observable<ResponseDisplayBean> CheckMedicalLiteratureLike(NewMedicalLiteratureLikeDisplayBean newMedicalLiteratureLikeDisplayBean);

    //去除点赞
    Observable<ResponseDisplayBean> DisLikeMedicalLiterature(NewMedicalLiteratureLikeDisplayBean newMedicalLiteratureLikeDisplayBean);

    //根据临床id加载评论
    Observable<List<LiteratureCommentDisplayBean>> LoadLiteratureCommentsByMedicalLiteratureId(int medicalLiteratureId);

    //插入评论
    Observable<LiteratureCommentDisplayBean> InsertLiteratureComment(NewLiteratureCommentDisplayBean newLiteratureCommentDisplayBean);

    Observable<ResponseDisplayBean> updateDoctorProductPrice(PriceDisplayBean bean);

    Observable<ResponseDisplayBean> insertPrice(NewPriceBean bean);

    //临床支持点赞
    Observable<MedicalLiteratureLikeDisplayBean> LikeMedicalLiterature(NewMedicalLiteratureLikeDisplayBean newMedicalLiteratureLikeDisplayBean);

    //插入新的回复
    Observable<LiteratureReplyDisplayBean> InsertLiteratureReply(NewLiteratureReplyDisplayBean newLiteratureReplyDisplayBean);

    Observable<List<LongTermMonitoringDisplayBean>> updateLongtermMonitorings(List<LongTermMonitoringDisplayBean> list);

    //删除评论
    Observable<ResponseDisplayBean> DeleteLiteratureComment(int literatureCommentId);

    //得到所有的评论回复
    Observable<List<LiteratureReplyDisplayBean>> LoadLiteratureReplyByLiteratureCommentId(int literatureCommentId);


    //个人信息--> 机构--> 医院名称（string）数组
    Observable<List<String>> LoadAllHospitals();

    //加载所有的临床支持
    Observable<List<MedicalLiteratureDisPlayBean>> LoadAllMedicalLiteratures();

    //处方
    Observable<List<PrescriptionBean>> loadPrescriptionsByPatientId(int patientId);

    Observable<PrescriptionBean> insertPrescription(NewPrescriptionBean newPrescriptionBean);

    //健康记录信息
    Observable<List<HealthConditionDisplayBean>> LoadHealthConditionByPatientId(int patientId);

    //更新健康记录
    Observable<HealthConditionDisplayBean> UpdateHealthConditions(HealthConditionDisplayBean[] healthConditionDisplayBeans);

    //删除健康记录
    Observable<ResponseDisplayBean> DeleteHealthConditions(Integer[] ids);

    //科室： 加载所有一级科室
    Observable<List<DepartmentDisplayBean>> loadDepartment();

    //个人信息--> 加载二级子类科室
    Observable<List<DepartmentDisplayBean>> loadDepartmentName(int level, int parentDepartmentId);

    //删除多个长期监测
    Observable<ResponseDisplayBean> deleteLongtermMonitorings(List<Integer> bean);

    //通过患者ID查询所有的患者的病例 医生和患者的token都可以用
    Observable<List<PatientRecordDisplayBean>> loadAllPatientGenericRecordsBypatientId(int patientID);

    //通过医生查询患者的请求
    Observable<List<PatientRequestDisplayBean>> LoadPatientRequestsByDoctor();

    //更新处方
    Observable<PrescriptionBean> UpdatePrescription(PrescriptionBean newPrescriptionBean);

    //移动端更新医生信息
    Observable<ResponseDisplayBean> mobileUpdateDoctor(MobileUpdateDoctorBean bean);

    //添加提现账号
    Observable<UserCashOutAccountDisplayBean> InsertUserCashOutAccount(NewCashOutAccountBean account);

    //更新患者临床诊疗记录 医生和患者的token都可以
    Observable<PatientRecordDisplayBean> updatePatientRecord(PatientRecordDisplayBean patientRecordDisplayBean);

    //上传图片处理
    Observable<List<DocumentDisplayBean>> getUploadListObservable(List<String> paths, String kauriHealthId);

    //插入图片
    Observable<List<DocumentDisplayBean>> uploadDocuments(RequestBody kauriHealthId, List<MultipartBody.Part> parts);

    //提现详细
    Observable<List<CreditTransactionDisplayBean>> CreditTransactions();

    //插入新的临床诊疗记录 医生和患者的token都可以
    Observable<PatientRecordDisplayBean> addNewPatientRecord(NewPatientRecordDisplayBean newPatientRecordDisplayBean);

    //为现医生查询所有的医患关系
    Observable<List<DoctorPatientRelationshipBean>> LoadDoctorPatientRelationshipForDoctor();

    //为现医生加载所有医生关系
    Observable<List<DoctorRelationshipBean>> LoadDoctorRelationships();

    //搜索关键字
    Observable<SearchResultBean> KeywordSearch(InitialiseSearchRequestBean initialiseSearchRequestBean);

    //开启新的医医关系
    Observable<ResponseDisplayBean> RequestNewDoctorRelationship(NewDoctorRelationshipBean bean);

    //加载所有挂机的医生关系
    Observable<List<DoctorRelationshipBean>> LoadPendingDoctorRelationships();

    //接受医生关系
    Observable<ResponseDisplayBean> AcceptDoctorRelationship(String DoctorRelationshipId);

    //拒绝医生关系
    Observable<ResponseDisplayBean> RejectDoctorRelationship(String DoctorRelationshipId);


    //查询所有同意的协作医生
    Observable<List<DoctorCooperationBean>> loadAcceptedCooperationDoctors();

    //拒绝患者请求
    Observable<ResponseDisplayBean> RejectPatientRequest(PatientRequestDecisionBean patientRequestDecisionBean);

    //接受患者请求
    Observable<ResponseDisplayBean> AcceptPatientRequest(PatientRequestDecisionBean patientRequestDecisionBean);

    //查询所有用户医生关系的病例（包含拒绝的） 医生使用的token
    Observable<List<DoctorRelationshipBean>> loadAllDoctorRelationships();

    //插入新的辅助检查
    Observable<PatientRecordDisplayBean> InsertSupplementaryTest(NewSupplementaryTestPatientRecordDisplayBean bean);

    //更新辅助检查
    Observable<PatientRecordDisplayBean> UpdateSupplementaryTest(PatientRecordDisplayBean bean);

    //插入新的病理记录
    Observable<PatientRecordDisplayBean> InsertPathologyRecord(NewPathologyPatientRecordDisplayBean bean);

    //更新病理记录
    Observable<PatientRecordDisplayBean> UpdatePathologyRecord(PatientRecordDisplayBean bean);

    //插入实验室记录
    Observable<PatientRecordDisplayBean> InsertLabTest(NewLabTestPatientRecordDisplayBean bean);

    //更新实验室检查
    Observable<PatientRecordDisplayBean> UpdateLabTest(PatientRecordDisplayBean bean);

    //申请结束医患关系
    Observable<ResponseDisplayBean> RequestEndDoctorPatientRelationship(int doctorPatientReplationshipId);

    //根据医患id加载
    Observable<List<DoctorPatientRelationshipBean>> LoadDoctorPatientRelationshipForPatientId(int patientId);

    //读取医生相关好友列表
    Observable<List<ContactUserDisplayBean>> loadContactListByDoctorId();
    //医生端更新
    Observable<SoftwareInfo> CheckVersion(Map<String,String> options);

    Observable<ResponseDisplayBean> removeDoctorRelationship(int id);

    //通过医生查询转诊状态为等待的请求
    Observable<List<PatientRequestDisplayBean>> LoadReferralsPatientRequestByDoctorId();
    //向多个医生转诊患者
    Observable<ResponseDisplayBean> InsertPatientRequestReferralByDoctorList(PatientRequestReferralDoctorDisplayBean bean);
    //想一个医生转诊多个患者
    Observable<ResponseDisplayBean> InsertPatientRequestReferralByPatientList(PatientRequestReferralPatientDisplayBean bean);

    //使用患者id查询医疗，查询者必须为医生-->医疗团队
    Observable<List<DoctorPatientRelationshipBean>> loadDoctorTeamForPatient(int patientId);

}
