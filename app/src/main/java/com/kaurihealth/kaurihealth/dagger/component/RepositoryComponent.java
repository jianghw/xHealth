package com.kaurihealth.kaurihealth.dagger.component;


import com.kaurihealth.datalib.dagger.ActivityScoped;
import com.kaurihealth.datalib.repository.IDataSource;
import com.kaurihealth.kaurihealth.add_record_v.AddOutpatientActivity;
import com.kaurihealth.kaurihealth.casehistory_v.AllMedicalRecordsFragment;
import com.kaurihealth.kaurihealth.casehistory_v.CaseHistoryFragment;
import com.kaurihealth.kaurihealth.casehistory_v.ProcessedRecordsFragment;
import com.kaurihealth.kaurihealth.casehistory_v.SearchPatientActivity;
import com.kaurihealth.kaurihealth.clinical_v.Fragment.DynamicFragment;
import com.kaurihealth.kaurihealth.clinical_v.Fragment.StudentFragment;
import com.kaurihealth.kaurihealth.clinical_v.Fragment.StudyFragment;
import com.kaurihealth.kaurihealth.clinical_v.Fragment.TreatmentFragment;
import com.kaurihealth.kaurihealth.clinical_v.activity.ClinicalListActivity;
import com.kaurihealth.kaurihealth.clinical_v.activity.ClinicalSearchActivity;
import com.kaurihealth.kaurihealth.clinical_v.activity.CommentActivity;
import com.kaurihealth.kaurihealth.clinical_v.activity.DynamicActivity;
import com.kaurihealth.kaurihealth.clinical_v.activity.DynamicActivityNew;
import com.kaurihealth.kaurihealth.clinical_v.activity.LiteratureCommentActivity;
import com.kaurihealth.kaurihealth.clinical_v.activity.MyClinicalListActivity;
import com.kaurihealth.kaurihealth.conversation_v.AddGroupChatActivity;
import com.kaurihealth.kaurihealth.conversation_v.ChangeGroupChatActivity;
import com.kaurihealth.kaurihealth.conversation_v.GroupConversationDetailsActivity;
import com.kaurihealth.kaurihealth.conversation_v.ItemConversationDetailsActivity;
import com.kaurihealth.kaurihealth.conversation_v.SystemMessageActivity;
import com.kaurihealth.kaurihealth.conversation_v.SystemMessageSettingActivity;
import com.kaurihealth.kaurihealth.dagger.module.ApplicationModule;
import com.kaurihealth.kaurihealth.dagger.module.RepositoryModule;
import com.kaurihealth.kaurihealth.department_v.SelectDepartmentLevel1Activity;
import com.kaurihealth.kaurihealth.department_v.SelectDepartmentLevel2Activity;
import com.kaurihealth.kaurihealth.doctor_new.DoctorFragmentNew;
import com.kaurihealth.kaurihealth.doctor_new.DoctorSearchActivity;
import com.kaurihealth.kaurihealth.doctor_new.PatientSearchActivity;
import com.kaurihealth.kaurihealth.doctor_v.DoctorFragment;
import com.kaurihealth.kaurihealth.doctor_v.DoctorInfoActivity;
import com.kaurihealth.kaurihealth.findpassword_v.FindPasswordActivity;
import com.kaurihealth.kaurihealth.history_record_v.ClinicalHistoryActivity;
import com.kaurihealth.kaurihealth.history_record_v.HospitalRecordCompileActivity;
import com.kaurihealth.kaurihealth.home_v.referral.AcceptReasonActivity;
import com.kaurihealth.kaurihealth.home_v.referral.ReferralPatientRequestActivity;
import com.kaurihealth.kaurihealth.home_v.referral.RejectReasonActivity;
import com.kaurihealth.kaurihealth.home_v.request.DoctorRequestActivity;
import com.kaurihealth.kaurihealth.home_v.request.FriendRequestsActivity;
import com.kaurihealth.kaurihealth.home_v.request.PatientRequestActivity;
import com.kaurihealth.kaurihealth.home_v.request.PatientRequestInfoActivity;
import com.kaurihealth.kaurihealth.hospital_v.HospitalNameActivity;
import com.kaurihealth.kaurihealth.login_v.LoginActivity;
import com.kaurihealth.kaurihealth.main_v.ClinicalFragment;
import com.kaurihealth.kaurihealth.main_v.HomeFragment;
import com.kaurihealth.kaurihealth.main_v.MainActivity;
import com.kaurihealth.kaurihealth.main_v.MessageFragment;
import com.kaurihealth.kaurihealth.main_v.MineFragment;
import com.kaurihealth.kaurihealth.main_v.PatientFragment;
import com.kaurihealth.kaurihealth.mine_v.AccountDetailsActivity;
import com.kaurihealth.kaurihealth.mine_v.ComeMoneyFragment;
import com.kaurihealth.kaurihealth.mine_v.GetMoneyOutFragment;
import com.kaurihealth.kaurihealth.mine_v.SetPasswordActivity;
import com.kaurihealth.kaurihealth.mine_v.SettingActivity;
import com.kaurihealth.kaurihealth.mine_v.account.AddBankCardActivity;
import com.kaurihealth.kaurihealth.mine_v.account.WithdrawActivity;
import com.kaurihealth.kaurihealth.mine_v.feedback.FeedbackActivity;
import com.kaurihealth.kaurihealth.mine_v.personal.DoctorCompileActivity;
import com.kaurihealth.kaurihealth.mine_v.personal.DoctorDetailsActivity;
import com.kaurihealth.kaurihealth.mine_v.personal.EnterCertificationNumberActivity;
import com.kaurihealth.kaurihealth.mine_v.personal.EnterGraduateSchoolActivity;
import com.kaurihealth.kaurihealth.mine_v.personal.EnterHospitalActivity;
import com.kaurihealth.kaurihealth.mine_v.personal.EnterPracticeFieldActivity;
import com.kaurihealth.kaurihealth.mine_v.personal.EnterStudyExperienceActivity;
import com.kaurihealth.kaurihealth.mine_v.personal.HospitalAndDepartmentActivity;
import com.kaurihealth.kaurihealth.mine_v.personal.SelectTitleActivity;
import com.kaurihealth.kaurihealth.mine_v.service.PersonalDoctorServiceSettingActivity;
import com.kaurihealth.kaurihealth.mine_v.service.RemoteDoctorServiceSettingActivity;
import com.kaurihealth.kaurihealth.open_account_v.OpenAnAccountActivity;
import com.kaurihealth.kaurihealth.patient_v.PatientInfoActivity;
import com.kaurihealth.kaurihealth.patient_v.doctor_team.DoctorTeamActivity;
import com.kaurihealth.kaurihealth.patient_v.family_members.AddFamilyMemberActivity;
import com.kaurihealth.kaurihealth.patient_v.family_members.FamilyMembersActivity;
import com.kaurihealth.kaurihealth.patient_v.health_condition.HealthConditionActivity;
import com.kaurihealth.kaurihealth.patient_v.long_monitoring.AddMonitorIndexActivity;
import com.kaurihealth.kaurihealth.patient_v.long_monitoring.CompileProgramActivity;
import com.kaurihealth.kaurihealth.patient_v.long_monitoring.LongHealthyStandardActivity;
import com.kaurihealth.kaurihealth.patient_v.medical_records.AddAndEditLobTestActivity;
import com.kaurihealth.kaurihealth.patient_v.medical_records.AddAndEditPrescriptionActivityNew;
import com.kaurihealth.kaurihealth.patient_v.medical_records.AddCommonMedicalRecordActivity;
import com.kaurihealth.kaurihealth.patient_v.medical_records.AdmissionRecordActivity;
import com.kaurihealth.kaurihealth.patient_v.medical_records.DischargeRecordActivity;
import com.kaurihealth.kaurihealth.patient_v.medical_records.MedicalRecordActivity;
import com.kaurihealth.kaurihealth.patient_v.medical_records.NetworkMedicalConsultationActivity;
import com.kaurihealth.kaurihealth.patient_v.medical_records.OutpatientElectronicActivity;
import com.kaurihealth.kaurihealth.patient_v.medical_records.OutpatientPicturesActivity;
import com.kaurihealth.kaurihealth.patient_v.medical_records.RemoteMedicalConsultationActivity;
import com.kaurihealth.kaurihealth.patient_v.medical_records.TreatmentRelatedRecordsActivity;
import com.kaurihealth.kaurihealth.patient_v.prescription.PrescriptionActivity;
import com.kaurihealth.kaurihealth.patient_v.subsequent_visit.AddVisitRecordActivity;
import com.kaurihealth.kaurihealth.patient_v.subsequent_visit.VisitRecordActivity;
import com.kaurihealth.kaurihealth.record_details_v.DoctorTeamFragment;
import com.kaurihealth.kaurihealth.record_details_v.FamilyMembersFragment;
import com.kaurihealth.kaurihealth.record_details_v.HealthConditionFragment;
import com.kaurihealth.kaurihealth.record_details_v.LongHealthyStandardFragment;
import com.kaurihealth.kaurihealth.record_details_v.NMedicalRecordFragment;
import com.kaurihealth.kaurihealth.record_details_v.PresctiptionFragment;
import com.kaurihealth.kaurihealth.referrals_v.ReferralDoctorActivity;
import com.kaurihealth.kaurihealth.referrals_v.ReferralPatientActivity;
import com.kaurihealth.kaurihealth.referrals_v.ReferralReasonActivity;
import com.kaurihealth.kaurihealth.register_v.RegisterActivity;
import com.kaurihealth.kaurihealth.register_v.RegisterPersonInfoActivity;
import com.kaurihealth.kaurihealth.resetpassword_v.ResetPasswordActivity;
import com.kaurihealth.kaurihealth.save_record_v.AccessoryRecordCommonSaveActivity;
import com.kaurihealth.kaurihealth.save_record_v.HospitalRecordSaveActivity;
import com.kaurihealth.kaurihealth.save_record_v.LobRecordCommonSaveActivity;
import com.kaurihealth.kaurihealth.save_record_v.OutpatientRecordSaveActivity;
import com.kaurihealth.kaurihealth.search_v.SearchActivity;
import com.kaurihealth.kaurihealth.search_v.SearchDepartmentFragment;
import com.kaurihealth.kaurihealth.search_v.SearchDoctorFragment;
import com.kaurihealth.kaurihealth.search_v.SearchFragment;
import com.kaurihealth.kaurihealth.search_v.SearchHospotalFragment;
import com.kaurihealth.kaurihealth.search_v.SearchPatientFragment;
import com.kaurihealth.kaurihealth.search_v.VerificationActivity;
import com.kaurihealth.kaurihealth.sickness_v.LoadAllSicknessActivity;
import com.kaurihealth.kaurihealth.tinker.SampleApplicationLike;
import com.kaurihealth.kaurihealth.welcome_v.WelcomeActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by jianghw on 2016/8/12.
 * <p>
 * 描述：注入器 @Inject和@Module的桥梁
 */
@ActivityScoped
@Singleton
@Component(modules = {ApplicationModule.class, RepositoryModule.class})
public interface RepositoryComponent {
    IDataSource getRepository();

    void inject(SampleApplicationLike application);

    void inject(WelcomeActivity activity);

    void inject(LoginActivity activity);

    void inject(RegisterActivity activity);

    void inject(RegisterPersonInfoActivity activity);

    void inject(FindPasswordActivity activity);

    void inject(MessageFragment fragment);

    void inject(MineFragment fragment);

    void inject(ClinicalFragment fragment);

    void inject(ResetPasswordActivity activity);

    void inject(OpenAnAccountActivity activity);

    void inject(SettingActivity activity);

    void inject(DoctorDetailsActivity activity);

    void inject(PersonalDoctorServiceSettingActivity activity);

    void inject(RemoteDoctorServiceSettingActivity activity);

    void inject(WithdrawActivity activity);

    void inject(SelectTitleActivity activity);

    void inject(EnterCertificationNumberActivity activity);

    void inject(EnterGraduateSchoolActivity activity);

    void inject(EnterPracticeFieldActivity activity);

    void inject(EnterStudyExperienceActivity activity);

    void inject(EnterHospitalActivity activity);

    void inject(CompileProgramActivity activity);

    void inject(PatientFragment fragment);

    void inject(DoctorFragment fragment);

    void inject(SetPasswordActivity activity);

    void inject(DoctorInfoActivity activity);

    //临床支持Fragment
    void inject(DynamicFragment fragment);

    void inject(StudentFragment fragment);

    void inject(StudyFragment fragment);

    void inject(TreatmentFragment fragment);

    void inject(DynamicActivity activity);

    void inject(CommentActivity activity);

    void inject(LiteratureCommentActivity activity);

    void inject(MyClinicalListActivity activity);

    void inject(ClinicalListActivity activity);

    void inject(ClinicalSearchActivity activity);

    //我的-->个人信息-->一级科室
    void inject(SelectDepartmentLevel1Activity activity);

    //我的-->个人信息-->二级科室
    void inject(SelectDepartmentLevel2Activity activity);

    void inject(PatientInfoActivity activity);

    void inject(LongHealthyStandardActivity activity);

    void inject(AddMonitorIndexActivity activity);

    void inject(DoctorTeamActivity activity);

    void inject(PrescriptionActivity activity);

    void inject(HealthConditionActivity activity);

    void inject(MedicalRecordActivity activity);

    void inject(HospitalNameActivity activity);

    //门诊记录电子病历
    void inject(OutpatientElectronicActivity activity);

    void inject(AddBankCardActivity activity);

    void inject(AccountDetailsActivity activity);

    void inject(PatientRequestActivity activity);

    void inject(VerificationActivity activity);

    void inject(DoctorRequestActivity activity);

    void inject(HomeFragment fragment);

    void inject(FriendRequestsActivity activity);

    void inject(PatientRequestInfoActivity activity);

    void inject(RejectReasonActivity activity);

    void inject(AcceptReasonActivity activity);

    //远程医疗咨询
    void inject(RemoteMedicalConsultationActivity activity);

    //网络医疗咨询
    void inject(NetworkMedicalConsultationActivity activity);

    void inject(AddCommonMedicalRecordActivity activity);

    //临床诊疗-->院内治疗相关记录
    void inject(TreatmentRelatedRecordsActivity activity);

    void inject(AddAndEditPrescriptionActivityNew activity);

    //临床诊疗-->入院记录
    void inject(AdmissionRecordActivity activity);

    //临床诊疗-->出院记录
    void inject(DischargeRecordActivity activity);

    //临床诊疗-->门诊记录图片存档
    void inject(OutpatientPicturesActivity activity);

    void inject(AddAndEditLobTestActivity activity);

    void inject(MainActivity activity);

    void inject(ReferralPatientRequestActivity activity);

    void inject(ReferralDoctorActivity activity);

    void inject(ReferralReasonActivity activity);

    void inject(ReferralPatientActivity activity);

    void inject(SearchActivity activity);

    void inject(SearchFragment fragment);

    void inject(SearchDepartmentFragment fragment);

    void inject(SearchDoctorFragment fragment);

    void inject(SearchHospotalFragment fragment);

    void inject(SearchPatientFragment fragment);

    //患者信息 --> 家庭成员
    void inject(FamilyMembersActivity activity);

    void inject(VisitRecordActivity activity);

    void inject(AddVisitRecordActivity activity);

    //患者信息 -->添加家庭成员
    void inject(AddFamilyMemberActivity activity);

    void inject(SystemMessageActivity activity);

    void inject(SystemMessageSettingActivity activity);

    void inject(ComeMoneyFragment fragment);

    void inject(GetMoneyOutFragment fragment);

    void inject(DynamicActivityNew activity);

    //设置--> 意见反馈
    void inject(FeedbackActivity activity);

    void inject(AddGroupChatActivity activity);

    void inject(ChangeGroupChatActivity activity);

    void inject(GroupConversationDetailsActivity activity);

    void inject(ItemConversationDetailsActivity activity);

    void inject(DoctorFragmentNew fragment);

    void inject(AllMedicalRecordsFragment fragment);

    void inject(ProcessedRecordsFragment fragment);

    void inject(SearchPatientActivity activity);

    void inject(NMedicalRecordFragment fragment);

    void inject(DoctorSearchActivity activity);

    void inject(HealthConditionFragment fragment);

    void inject(PresctiptionFragment fragment);

    void inject(LongHealthyStandardFragment fragment);

    void inject(DoctorTeamFragment fragment);

    void inject(FamilyMembersFragment fragment);

    void inject(ClinicalHistoryActivity activity);

    void inject(AddOutpatientActivity activity);

    void inject(HospitalRecordCompileActivity activity);

    void inject(OutpatientRecordSaveActivity activity);

    //医院和科室的共同界面
    void inject(HospitalAndDepartmentActivity activity);

    void inject(LoadAllSicknessActivity activity);

    void inject(AccessoryRecordCommonSaveActivity activity);

    void inject(LobRecordCommonSaveActivity activity);

    //病人搜索
    void inject(PatientSearchActivity activity);

    void inject(HospitalRecordSaveActivity activity);

    void inject(DoctorCompileActivity activity);

    void inject(CaseHistoryFragment fragment);
}
