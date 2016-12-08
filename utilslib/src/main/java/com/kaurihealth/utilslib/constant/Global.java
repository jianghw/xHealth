package com.kaurihealth.utilslib.constant;

/**
 * Created by jianghw on 2016/8/9.
 * <p>
 * 描述：全局常量
 */
public class Global {
    public static final class Url {
        public static final String DEVELOP_URL = "http://testapi.kaurihealth.com/";//开发
        public static final String TEST_URL = "http://internaltestapi.kaurihealth.com/";//测试
        public static final String PREVIEW_URL = "http://uatapi.kaurihealth.com/";//预览
    }

    public static final class Environment {
        public static final String ENVIRONMENT = "Environment";
        public static final String DEVELOP = "Develop";
        public static final String TEST = "Test";
        public static final String PREVIEW = "Preview";

        public static final String BLOOD = "血压";
        public static final String BLOOD_SHRINKAGE = "收缩压";
        public static final String BLOOD_DIASTOLIC = "舒张压";

        public static final String COMPILE = "compile";
        public static final String DELETE = "delete";

        public static final String CLINICAL = "临床诊疗记录";
        public static final String LOB = "实验室检查";
        public static final String AUXILIARY = "辅助检查";
        public static final String PATHOLOGY = "病理";

        public static final String BUNDLE = "bundle";
        public static final String CHOICE = "选择";
        public static final String UPDATE = "更新";

    }

    /**
     * 标记跳转页面
     */
    public static final class Jump {
        public static final String LoginActivity = "LoginActivity";
        public static final String MainActivity = "MainActivity";
        public static final String FindPasswordActivity = "FindPasswordActivity";
        public static final String RegisterPersonInfoActivity = "RegisterPersonInfoActivity";
        public static final String RegisterActivity = "RegisterActivity";
        public static final String SingleChatActivity = "SingleChatActivity";
        public static final String PatientInfoActivity = "PatientInfoActivity";
        public static final String CompileProgramActivity = "CompileProgramActivity";
        public static final String MedicalRecordActivity = "MedicalRecordActivity";
        public static final String DoctorInfoActivity = "DoctorInfoActivity";
        public static final String OutpatientElectronicActivity = "OutpatientElectronicActivity";
        public static final String AddCommonMedicalRecordActivity = "AddCommonMedicalRecordActivity";
        public static final String AddAndEditLobTestActivity = "AddAndEditLobTestActivity";

        public static final String HealthyRecordNewActivity = "HealthyRecordNewActivity";
        public static final String LongHealthyStandardActivity = "LongHealthyStandardActivity";
        public static final String PrescriptionActivity = "PrescriptionActivity";

        public static final String AddMonitorIndexActivity = "AddMonitorIndexActivity";
        public static final String ServiceSettingActivity = "ServiceSettingActivity";
        public static final String SelectDepartmentLevel2Activity = "SelectDepartmentLevel2Activity";

        public static final String OutpatientPicturesActivity = "OutpatientPicturesActivity";  //门诊记录图片存档
        public static final String RemoteMedicalConsultationActivity = "RemoteMedicalConsultationActivity";//远程医疗咨询
        public static final String NetworkMedicalConsultationActivity = "NetworkMedicalConsultationActivity";//网络医疗咨询
        public static final String AdmissionRecordActivity = "AdmissionRecordActivity";  //入院记录
        public static final String TreatmentRelatedRecordsActivity = "TreatmentRelatedRecordsActivity";  //院内治疗相关记录
        public static final String DischargeRecordActivity = "DischargeRecordActivity"; //出院记录
        public static final String VerificationActivity = "VerificationActivity"; //验证信息

        public static final String CommonMedicalRecordToReadActivity = "CommonMedicalRecordToReadActivity";//只读辅助检查、病理
        public static final String LobTestOnlyReadAcivity = "LobTestOnlyReadAcivity";//只读实验室检查
        public static final String OutpatientElectronicOnlyReadActivity = "OutpatientElectronicOnlyRead";//只读门诊记录电子病历
        public static final String OutPatientPicturesOnlyReadActivity = "OutPatientPicturesOnlyReadActivity";//只读门诊记录图片存档
        public static final String RemoteMedicalConsultationOnlyReadActivity = "RemoteMedicalConsultationOnlyReadActivity";//远程医疗只读
        public static final String NetWorkMedicalConsultationOnlyReadActivity = "NetWorkMedicalConsultationOnlyReadActivity";//网络医疗只读
        public static final String AdmissionRecordOnlyReadActivity = "AdmissionRecordOnlyReadActivity";//入院记录
        public static final String TreatMentRelatedRecordsOnlyreadActivity = "TreatMentRelatedRecordsOnlyreadActivity";//院内治疗相关记录只读
        public static final String DischargeRecordOnlyReadActivity = "DischargeRecordOnlyReadActivity";//出院记录  只读
        public static final String FamilyMembersActivity = "FamilyMembersActivity"; //家庭成员界面

        public static final String SystemMessageActivity = "SystemMessageActivity";//出院记录  只读
        public static final String ConversationGroupItemActivity = "ConversationGroupItemActivity";//出院记录  只读


    }

    public static final class RequestCode {
        public static final int REFERRAL = 100;
        public static final int ACCEPT = 200;
        public static final int REJECT = 400;

        public static final int REQUEST_DOCTOR = 300;
        public static final int REQUEST_PATIENT = 600;
        public static final int SEARCH_PATIENT = 700;
        public static final int ADD_VISIT = 800;

        public static final int REFERRAL_DOCTOR = 900;

        public static final int Group_CHAT_DOCTOR = 500;
        public static final int DIALOGUE_CHAT_ITEM = 501;
    }

    public static final class Bundle {
        public static final String SEARCH_BUNDLE = "search_bundle";
        public static final String SEARCH_DEFAULT = "default";
        public static final String SEARCH_HOSPITAL = "hospital";
        public static final String SEARCH_DEPARTMENT = "department";
        public static final String SEARCH_DOCTOR = "doctor";
        public static final String SEARCH_PATIENT = "patient";
        public static final String CONVER_ITEM_BUNDLE = "conver_item_bundle";
        public static final String CONVER_ITEM_DOCTOR = "conver_item_doctor";
        public static final String CONVER_ITEM_PATIENT = "conver_item_patient";

        public static final String REQUEST_DOCTOR = "request_doctor";
        public static final String FRIEND_DOCTOR = "friend_doctor";
        public static final String REQUEST_DOCTOR_WORK = "request_doctor_work";
        public static final String REQUEST_DOCTOR_PRACTICE = "request_doctor_practice";

        public static final String VISIT_PATIENT = "visit_patient";

        public static final String DOCTOR_TEAM = "doctor_team";
        public static final String LEANCLOUD_GROUP_NAME = "leancloud_group_name";
        public static final String LEANCLOUD_GROUP_CONID = "leancloud_group_conid";
        public static final String LEANCLOUD_GROUP_MEMBES = "leancloud_group_members";
    }

    public static final class Factory {
        public static final String NO_TOKEN = "NO_TOKEN";
        public static final String NO_TOKEN_EXECUTE = "NO_TOKEN_EXECUTE";
        public static final String DEFAULT_TOKEN = "DEFAULT_TOKEN";
        public static final String LoadAndroidVersionWithDoctor = "LoadAndroidVersionWithDoctor";
    }

    public static final class Numerical {
        public static final int SWIPE_REFRESH = 400;

    }

    public static final class BugtagsConstants {
        public static final String BugTagUserKey = "UserName";
        public static final String BugTagUserPassKey = "Password";
        public static final String BugTagPlatform = "Platform";
        public static final String BugTagDoctorId = "DoctorId";
    }


    public static final class LeanCloud {
        public static final String USER_TYPE_DOCTOR = "医生";
        public static final String USER_TYPE_PATIENT = "患者";
        public static final String ATTR = "attr";
        public static final String ATTR_TYPE = "type";
        public static final String ATTR_MEMBERS = "members";
    }
}
