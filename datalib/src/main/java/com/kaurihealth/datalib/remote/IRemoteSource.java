package com.kaurihealth.datalib.remote;


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

import java.util.List;

import rx.Observable;

/**
 * Created by jianghw on 2016/8/12.
 * <p/>
 * 描述：
 */
public interface IRemoteSource {
    Observable<TokenBean> userLogin(LoginBean bean);

    Observable<RegisterResponse> userRegister(NewRegisterBean newRegisterBean);

    Observable<DoctorDisplayBean> loadDoctorDetail();

    Observable<DoctorDisplayBean> updateDoctor(DoctorDisplayBean doctorDisplayBean);

    Observable<InitiateVerificationResponse> InitiateVerification(InitiateVerificationBean bean);

    Observable<ResponseDisplayBean> UpdateDoctorUserInformation(DoctorUserBean bean);

    Observable<ResponseDisplayBean> findPassword(RequestResetPasswordDisplayDto bean);

    //"忘记密码"点击下一步：重置密码
    Observable<ResponseDisplayBean> resetPassword(ResetPasswordDisplayBean bean);

    //医生为患者快速注册
    Observable<RegisterResponse> openAnAccount(NewRegistByDoctorBean bean);

    //临床支持里面的search
    Observable<SearchResultBean> search(InitialiseSearchRequestBean bean);

    //为现医生查询所有医患关系
    Observable<List<DoctorPatientRelationshipBean>> loadDoctorPatientRelationshipForDoctor();

    //首页里的搜索：所有，医院，科室，医生
    Observable<SearchResultBean> keyWordsSearch(InitialiseSearchRequestBean bean);

    //首页里的搜索
    Observable<List<PatientDisplayBean>> SearchPatientByUserName(String content);

    //得到所有的患者信息
    Observable<List<MedicalLiteratureDisPlayBean>> LoadAllMedicalLitreaturesByType(String medicalLiteratureType);

    //通过临床id获取对应的临床信息
    Observable<MedicalLiteratureDisPlayBean> LoadMedicalLiteratureById(int medicalLiteratureId);

    //我的-->设置--> 修改密码
    Observable<ResponseDisplayBean> updateUserPassword(NewPasswordDisplayBean bean);

    //我的-->我的账户-->提现通道--> 获取用户所有的提现账号
    Observable<List<UserCashOutAccountDisplayBean>> getUserCashOutAccounts();

    //我的-->我的账户-->点击"提现"按钮-->点击"确定”按钮 -->发起提现请求
    Observable<ResponseDisplayBean> startNewCashOut(NewCashOutBean bean);

    //检查登陆用户是否一堆某一个文献点赞。
    Observable<ResponseDisplayBean> CheckMedicalLiteratureLike(NewMedicalLiteratureLikeDisplayBean newMedicalLiteratureLikeDisplayBean);

    //去除点赞
    Observable<ResponseDisplayBean> DisLikeMedicalLiterature(NewMedicalLiteratureLikeDisplayBean newMedicalLiteratureLikeDisplayBean);

    //根据临床id加载评论
    Observable<List<LiteratureCommentDisplayBean>> LoadLiteratureCommentsByMedicalLiteratureId(int medicalLiteratureId);

    //插入评论
    Observable<LiteratureCommentDisplayBean> InsertLiteratureComment(NewLiteratureCommentDisplayBean newLiteratureCommentDisplayBean);

    //临床支持点赞
    Observable<MedicalLiteratureLikeDisplayBean> LikeMedicalLiterature(NewMedicalLiteratureLikeDisplayBean newMedicalLiteratureLikeDisplayBean);

    //插入新的回复
    Observable<LiteratureReplyDisplayBean> InsertLiteratureReply(NewLiteratureReplyDisplayBean newLiteratureReplyDisplayBean);

    Observable<ResponseDisplayBean> updateDoctorProductPrice(PriceDisplayBean bean);

    Observable<ResponseDisplayBean> insertPrice(NewPriceBean bean);

    // 医生创建普通病人关系(复诊)
    Observable<DoctorPatientRelationshipBean> insertNewRelationshipByDoctor(int patientId);

    //通过患者ID查询长期监测 医生和患者的token都可以用
    Observable<List<LongTermMonitoringDisplayBean>> longTermMonitoringDisplay(int patientId);

    Observable<ResponseDisplayBean> insertLongtermMonitorings(List<NewLongtermMonitoringBean> beans);

    //更新多个长期监测 医生和患者的token都可以用
    Observable<List<LongTermMonitoringDisplayBean>> updateLongtermMonitorings(List<LongTermMonitoringDisplayBean> list);
}
