package com.kaurihealth.kaurihealth.mine.util;

import android.content.Context;

import com.android.volley.ServerError;
import com.example.commonlibrary.widget.util.ErrorInterfaceM;
import com.kaurihealth.datalib.request_bean.bean.MobileUpdateDoctorDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.PersonInfoBean;
import com.kaurihealth.datalib.response_bean.ResponseDisplayBean;
import com.kaurihealth.datalib.service.IDoctorService;
import com.kaurihealth.kaurihealth.services.IServiceFactory;
import com.kaurihealth.kaurihealth.services.ServiceFactory;
import com.kaurihealth.kaurihealth.util.Url;
import com.kaurihealth.utilslib.dialog.SuccessInterfaceM;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by 张磊 on 2016/5/16.
 * 介绍：
 */
public class PersonUtilNew {

    public static void updataPersoninfo(PersonInfoBean personInfoBean, final SuccessInterfaceM<ResponseDisplayBean> success, final ErrorInterfaceM<Throwable> error, Context context) {
        MobileUpdateDoctorDisplayBean personinfoBeanNew = new MobileUpdateDoctorDisplayBean();
        personinfoBeanNew.firstName = personInfoBean.firstName;
        personinfoBeanNew.lastName = personInfoBean.lastName;
        personinfoBeanNew.gender = personInfoBean.gender;
        personinfoBeanNew.dateOfBirth = personInfoBean.dateOfBirth;
        personinfoBeanNew.avatar = personInfoBean.avatar;
        personinfoBeanNew.educationTitle = personInfoBean.EducationTitles;
        personinfoBeanNew.mentorshipTitle = personInfoBean.MentorshipTitles;
        personinfoBeanNew.hospitalTitle = personInfoBean.HospitalTitles;
        personinfoBeanNew.certificationNumber = personInfoBean.certificationNumber;
        personinfoBeanNew.departmentId = personInfoBean.departmentId;
        personinfoBeanNew.departmentName = personInfoBean.department;
        personinfoBeanNew.hospitalName = personInfoBean.organizationName;
        personinfoBeanNew.workingExperience = personInfoBean.studyExperience;
        personinfoBeanNew.practiceField = personInfoBean.goodat;
        personinfoBeanNew.educationLevel = personInfoBean.educationLevel;
        personinfoBeanNew.educationHistory = personInfoBean.educationHistory;
        IServiceFactory serviceFactory = new ServiceFactory(Url.prefix,context);
        IDoctorService doctorService = serviceFactory.getDoctorService();
        Call<ResponseDisplayBean> responseDisplayBeanCall = doctorService.MobileUpdateDoctor(personinfoBeanNew);
        responseDisplayBeanCall.enqueue(new Callback<ResponseDisplayBean>() {
            @Override
            public void onResponse(Call<ResponseDisplayBean> call, retrofit2.Response<ResponseDisplayBean> response) {
                if (response.isSuccessful()) {
                        success.success(response.body());
                } else {
                    error.error(new ServerError());
                }
            }

            @Override
            public void onFailure(Call<ResponseDisplayBean> call, Throwable t) {
//                Bugtags.sendException(t);
                error.error(t);
            }
        });
    }
}
