package com.kaurihealth.kaurihealth.mine.util;

import android.app.Activity;
import android.content.Context;

import com.kaurihealth.utilslib.date.DateUtils;
import com.example.commonlibrary.widget.util.LoadingUtil;
import com.kaurihealth.datalib.request_bean.bean.DepartmentDisplayBean;
import com.kaurihealth.datalib.response_bean.DoctorDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.PersonInfoBean;
import com.kaurihealth.datalib.service.IDepartmentService;
import com.kaurihealth.datalib.service.IDoctorService;
import com.kaurihealth.kaurihealth.mine.Interface.ResponseListener;
import com.kaurihealth.kaurihealth.services.ServiceFactory;
import com.kaurihealth.kaurihealth.util.Config;
import com.kaurihealth.kaurihealth.util.Enums.LoadingStatu;
import com.kaurihealth.kaurihealth.util.Getter;
import com.kaurihealth.kaurihealth.util.Interface.IGetter;
import com.kaurihealth.kaurihealth.util.Url;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 米平 on 2016/7/7.
 */
public class NewPersonInfoUtil {

    private static IGetter getter;

    private Context context;
    private static Config.Role role;
    LoadingUtil loadingUtil;

    private static IDoctorService doctorService;
    private static IDepartmentService departmentService;


    public NewPersonInfoUtil(Context context, Config.Role role) {
        this.context = context;
        this.role = role;
        loadingUtil = LoadingUtil.getInstance((Activity) context);
        getter = Getter.getInstance(context);


    }

    //获取个人信息
    public void getPersonInfoFromRetrofit(final ResponseListener<PersonInfoBean> success) {

        if (role == Config.Role.Doctor) {
            doctorService = new ServiceFactory(Url.prefix,context).getDoctorService();
            Call<DoctorDisplayBean> doctorDisplayBeanCall = doctorService.LoadDoctorDetail_out();
            doctorDisplayBeanCall.enqueue(new Callback<DoctorDisplayBean>() {
                @Override
                public void onResponse(Call<DoctorDisplayBean> call, Response<DoctorDisplayBean> response) {
                    if (response.isSuccessful()) {
                        DoctorDisplayBean doctorDisplayBean = response.body();
                        PersonInfoBean personInfoBean = new PersonInfoBean();
                        personInfoBean.avatar=doctorDisplayBean.avatar;
                        personInfoBean.doctorId = doctorDisplayBean.doctorId;
                        personInfoBean.area = doctorDisplayBean.avatar;
                        personInfoBean.fullName = doctorDisplayBean.fullName;
                        personInfoBean.firstName = doctorDisplayBean.firstName;
                        personInfoBean.lastName = doctorDisplayBean.lastName;
                        personInfoBean.jobTitle = doctorDisplayBean.hospitalTitle;
                        personInfoBean.gender = doctorDisplayBean.gender;
                        try {
                            personInfoBean.department = doctorDisplayBean.doctorInformations.department.departmentName;
                        } catch (NullPointerException e) {
                        }
                        try {
                            personInfoBean.dateOfBirth = DateUtils.GetDateText(doctorDisplayBean.dateOfBirth);
                        } catch (NullPointerException e) {
                        }
                        try {
                            personInfoBean.departmentId = doctorDisplayBean.doctorInformations.department.departmentId;
                        } catch (NullPointerException e) {
                            personInfoBean.departmentId=0;
                        }
                        try {
                            personInfoBean.organizationName = doctorDisplayBean.doctorInformations.hospitalName;
                        } catch (NullPointerException e) {
                        }
                        try {
                            personInfoBean.educationLevel = doctorDisplayBean.doctorEducation.educationLevel;
                        } catch (NullPointerException e) {
                        }
                        try {
                            personInfoBean.educationHistory = doctorDisplayBean.doctorEducation.educationHistory;
                        } catch (NullPointerException e) {
                        }
                        personInfoBean.certificationNumber = doctorDisplayBean.certificationNumber;
                        //  personInfoBean.area
                        personInfoBean.studyExperience = doctorDisplayBean.workingExperience;
                        personInfoBean.goodat = doctorDisplayBean.practiceField;
                        personInfoBean.EducationTitles = doctorDisplayBean.educationTitle;
                        personInfoBean.HospitalTitles = doctorDisplayBean.hospitalTitle;
                        personInfoBean.MentorshipTitles = doctorDisplayBean.mentorshipTitle;
                        success.success(personInfoBean);
                    }
                    loadingUtil.unregist();
                }

                @Override
                public void onFailure(Call<DoctorDisplayBean> call, Throwable t) {
                    success.erorr(LoadingStatu.NetError.value);
                    loadingUtil.unregist();
                }
            });
            loadingUtil.regist();
            loadingUtil.show();
        }
    }

    //通过retrofit返回科室信息
    public void getndDepartmentFromRetrofit(final ResponseListener<List<DepartmentDisplayBean>> success) {
        if (role == Config.Role.Doctor) {
            departmentService = new ServiceFactory(Url.prefix,context).getDepartmentService();
            Call<List<DepartmentDisplayBean>> listCall = departmentService.LoadAllDepartment();
            listCall.enqueue(new Callback<List<DepartmentDisplayBean>>() {
                @Override
                public void onResponse(Call<List<DepartmentDisplayBean>> call, Response<List<DepartmentDisplayBean>> response) {
                    if (response.isSuccessful()) {
                        success.success(response.body());
                    }
                    loadingUtil.unregist();
                }

                @Override
                public void onFailure(Call<List<DepartmentDisplayBean>> call, Throwable t) {
                    success.erorr(LoadingStatu.NetError.value);
                    loadingUtil.unregist();
                }
            });
            loadingUtil.regist();
            loadingUtil.show();
        }
    }
}
