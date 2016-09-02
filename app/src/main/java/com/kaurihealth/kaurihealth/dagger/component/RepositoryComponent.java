package com.kaurihealth.kaurihealth.dagger.component;


import com.kaurihealth.datalib.dagger.ActivityScoped;
import com.kaurihealth.datalib.repository.IDataSource;
import com.kaurihealth.kaurihealth.MyApplication;
import com.kaurihealth.kaurihealth.clinical_v.CommentActivity;
import com.kaurihealth.kaurihealth.clinical_v.DynamicActivity;
import com.kaurihealth.kaurihealth.clinical_v.DynamicFragment;
import com.kaurihealth.kaurihealth.clinical_v.StudentFragment;
import com.kaurihealth.kaurihealth.clinical_v.StudyFragment;
import com.kaurihealth.kaurihealth.clinical_v.TreatmentFragment;
import com.kaurihealth.kaurihealth.clinical_v.activity.LiteratureCommentActivity;
import com.kaurihealth.kaurihealth.dagger.module.ApplicationModule;
import com.kaurihealth.kaurihealth.dagger.module.RepositoryModule;
import com.kaurihealth.kaurihealth.department_v.SelectDepartmentLevel1Activity;
import com.kaurihealth.kaurihealth.findpassword_v.FindPasswordActivity;
import com.kaurihealth.kaurihealth.login_v.LoginActivity;
import com.kaurihealth.kaurihealth.main_v.ClinicalFragment;
import com.kaurihealth.kaurihealth.main_v.MessageFragment;
import com.kaurihealth.kaurihealth.main_v.MineFragment;
import com.kaurihealth.kaurihealth.main_v.PatientFragment;
import com.kaurihealth.kaurihealth.mine_v.DoctorDetailsActivity;
import com.kaurihealth.kaurihealth.mine_v.EnterCertificationNumberActivity;
import com.kaurihealth.kaurihealth.mine_v.EnterGraduateSchoolActivity;
import com.kaurihealth.kaurihealth.mine_v.EnterHospitalActivity;
import com.kaurihealth.kaurihealth.mine_v.EnterPracticeFieldActivity;
import com.kaurihealth.kaurihealth.mine_v.EnterStudyExperienceActivity;
import com.kaurihealth.kaurihealth.mine_v.MyAccountActivity;
import com.kaurihealth.kaurihealth.mine_v.PersonalDoctorServiceSettingActivity;
import com.kaurihealth.kaurihealth.mine_v.RemoteDoctorServiceSettingActivity;
import com.kaurihealth.kaurihealth.mine_v.SelectTitleActivity;
import com.kaurihealth.kaurihealth.mine_v.ServiceSettingActivity;
import com.kaurihealth.kaurihealth.mine_v.SetPasswordActivity;
import com.kaurihealth.kaurihealth.mine_v.SettingActivity;
import com.kaurihealth.kaurihealth.mine_v.WithdrawActivity;
import com.kaurihealth.kaurihealth.open_an_account_v.OpenAnAccountActivity;
import com.kaurihealth.kaurihealth.patient_v.AddMonitorIndexActivity;
import com.kaurihealth.kaurihealth.patient_v.CompileProgramActivity;
import com.kaurihealth.kaurihealth.patient_v.LongHealthyStandardActivity;
import com.kaurihealth.kaurihealth.patient_v.PatientInfoActivity;
import com.kaurihealth.kaurihealth.register_v.RegisterActivity;
import com.kaurihealth.kaurihealth.register_v.RegisterPersonInfoActivity;
import com.kaurihealth.kaurihealth.resetpassword_v.ResetPasswordActivity;
import com.kaurihealth.kaurihealth.search_v.BlankAllFragment;
import com.kaurihealth.kaurihealth.search_v.BlankDoctorFragment;
import com.kaurihealth.kaurihealth.search_v.BlankHospitalFragment;
import com.kaurihealth.kaurihealth.search_v.BlankMedicineFragment;
import com.kaurihealth.kaurihealth.search_v.BlankPatientFragment;
import com.kaurihealth.kaurihealth.search_v.SearchActivity;
import com.kaurihealth.kaurihealth.welcome_v.WelcomeActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by jianghw on 2016/8/12.
 * <p/>
 * 描述：注入器 @Inject和@Module的桥梁
 */
@ActivityScoped
@Singleton
@Component(modules = {ApplicationModule.class, RepositoryModule.class})
public interface RepositoryComponent {
    IDataSource getRepository();

    void inject(MyApplication application);

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

    void inject(SearchActivity activity);

    void inject(SettingActivity activity);

    void inject(ServiceSettingActivity activity);

    void inject(MyAccountActivity activity);

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

    void inject(PatientFragment fragment);

    void inject(SetPasswordActivity activity);

    //临床支持Fragment
    void inject(DynamicFragment fragment);

    void inject(StudentFragment fragment);

    void inject(StudyFragment fragment);

    void inject(TreatmentFragment fragment);

    void inject(DynamicActivity activity);

    void inject(CommentActivity activity);

    void inject(LiteratureCommentActivity actuvity);

    //首页搜索Fragment'
    void inject(BlankAllFragment fragment);

    void inject(BlankDoctorFragment fragment);

    void inject(BlankHospitalFragment fragment);

    void inject(BlankMedicineFragment fragment);

    void inject(BlankPatientFragment fragment);

    //我的-->个人信息-->科室
    void inject(SelectDepartmentLevel1Activity activity);

    void inject(PatientInfoActivity activity);


    void inject(LongHealthyStandardActivity activity);

    void inject(AddMonitorIndexActivity activity);

    void inject(CompileProgramActivity activity);

}
