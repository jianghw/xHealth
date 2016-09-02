package com.kaurihealth.utilslib.constant;

/**
 * Created by jianghw on 2016/8/9.
 * <p/>
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
        public static final String BLOOD_DIASTOLIC= "舒张压";

        public static final String COMPILE= "compile";
        public static final String DELETE= "delete";

    }

    /**
     * 标记跳转页面
     */
    public static final class Jump {
        public static final String MainActivity = "MainActivity";
        public static final String RegisterPersonInfoActivity = "RegisterPersonInfoActivity";
        public static final String SingleChatActivity = "SingleChatActivity";
        public static final String PatientInfoActivity = "PatientInfoActivity";

        public static final String HealthyRecordNewActivity = "HealthyRecordNewActivity";
        public static final String LongHealthyStandardActivity = "LongHealthyStandardActivity";
        public static final String MedicaHistoryActivity = "MedicaHistoryActivity";
        public static final String PrescriptionActivity = "PrescriptionActivity";
        public static final String ExpertsActivity = "ExpertsActivity";
        public static final String AddMonitorIndexActivity = "AddMonitorIndexActivity";
        public static final String ServiceSettingActivity = "ServiceSettingActivity";
    }

    public static final class ForResult {

        public static final int ToOpenAccount = 9;
        public static final int ToSearch = 8;
    }

    public static final class Factory {
        public static final String NO_TOKEN = "NO_TOKEN";
        public static final String NO_TOKEN_EXECUTE = "NO_TOKEN_EXECUTE";
        public static final String DEFAULT_TOKEN = "DEFAULT_TOKEN";
    }

    public static final class Numerical {
        public static final int SWIPE_REFRESH = 300;

    }
}
